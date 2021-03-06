/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.ppgi.unirio.marlon.smc.solution.algorithm.heuristic.lns.methods;

import br.com.ppgi.unirio.marlon.smc.mdg.ClusterMetrics;
import br.com.ppgi.unirio.marlon.smc.solution.algorithm.heuristic.lns.LNSConfiguration;
import random.number.generator.RandomWrapper;

/**
 *
 * @author Marlon Monçores
 */
public class RepairGreedyBestImprovementRandom extends ARepairSolution{

    public RepairGreedyBestImprovementRandom(LNSConfiguration config) {
        super("RGBIR", config);
    }
    
    protected RepairGreedyBestImprovementRandom(String name, LNSConfiguration config) {
        super(name, config);
    }

    @Override
    public void repair(ClusterMetrics cm) {
        int mdgSize = cm.getMdg().getSize();
        int[] checkOrder = RandomWrapper.createMixedArray(0,mdgSize-1);//verifica os módulos em uma ordem aleatória
        for (int i=0;i<mdgSize;i++){//para todos os módulos
            int currentModule = checkOrder[i];
            int testCluster = cm.getSolution()[currentModule];
            if(testCluster != -1){
                continue;//não precisa avaliar
            }
            //avaliar em qual cluster o módulo ficará melhor
            int bestCluster = -1;
            double bestDelta = Integer.MIN_VALUE;
            
            
            for(int j=0;j<=cm.getTotalClusteres()&&cm.getTotalClusteres()<cm.getMdg().getSize();j++){//testa um cluster novo se puder criar
                int currentCluster = cm.convertToClusterNumber(j);
                double currentDelta = cm.calculateMovimentDelta(currentModule, currentCluster);

                if(currentDelta > bestDelta){
                    bestDelta = currentDelta;
                    bestCluster = currentCluster;
                }
            }
            cm.makeMoviment(currentModule, bestCluster);//faz o melhor movimento
        }
    }
    
}
