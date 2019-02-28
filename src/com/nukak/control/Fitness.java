
package com.nukak.control;

import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;

public class Fitness extends FitnessFunction {

    private final Genetico algoritmo;

    public Fitness(final Genetico algoritmo){
        this.algoritmo = algoritmo;
    }
    // Retorna el valor de la funcion de fitness. De acuerdo a lo planeado, retorna
    // la suma de conflictos entre las reinas en sus respectivas diagonales. Cuanto menor
    // sea la cantidad de conflictos mejor sera.
    protected double evaluate(final IChromosome cromosoma){
        double s = 0;
        Gene[] genes = cromosoma.getGenes();
        int max = genes.length;
        for (int i = 0; i < max - 1; i++) {
            for (int j = i + 1; j < max; j++) {
                if((i!=j) && algoritmo.conflictosDiagonal(genes[i], genes[j], i, j))
                    s ++;
            }
        }
        return Math.max(1, algoritmo.maxCruzamiento() - s);
    }
}
