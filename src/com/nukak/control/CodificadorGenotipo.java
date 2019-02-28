
package com.nukak.control;

import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.impl.IntegerGene;

public class CodificadorGenotipo {

    private static CodificadorGenotipo instancia;
    private Genetico algoritmo;
    private IChromosome mejorIndividuo;
    private String solucionString;

    private CodificadorGenotipo(){}

    // Metodos Get.

    public Genetico obtenerAlgoritmo(){
        return algoritmo;
    }
    // Retorna el cromosoma con la mejor solucion.
    public IChromosome obtenerMejorSolucion(){
        return mejorIndividuo;
    }
    // Retorna una representacion del tablero para una fila.
    public String obternerFilaString(int numero_fila){
        if (mejorIndividuo != null){
            String fila = "";
            Gene[] genes = mejorIndividuo.getGenes();
            boolean fin = false;
            int columna;
            for (columna = 0; columna < genes.length && !fin; columna++){
                int genValue = ((IntegerGene)genes[columna]).intValue();
                if (genValue == numero_fila)
                    fin = true;
            }
            columna--;
            for (int i = 0; i < columna; i++){
                fila += "[ ] ";
            }
            fila += "[D] ";
            columna++;
            for (int i = columna; i < genes.length; i++){
                fila += "[ ] ";
            }
            return fila;
        }
        return "";
    }
    // Retorna la solución de la evolucion como string.
    public String obtenerMejorSolucionString(){
        solucionString = "";
        Gene[] genes = mejorIndividuo.getGenes();
        for (int i = 0; i < genes.length - 1; i++){
            int valor_gen = ((IntegerGene)genes[i]).intValue();
            solucionString += valor_gen + ", ";
        }
        int valor_gen = ((IntegerGene)genes[genes.length - 1]).intValue();
        solucionString += valor_gen;
        return solucionString;
    }
    
    // Retorna la solución de la evolucion como array.
    public Gene[] obtenerMejorSolucionArray(){
    	 if (mejorIndividuo != null){
             String fila = "";
             Gene[] genes = mejorIndividuo.getGenes();
            
         return genes;
    	 }else{
    		 return null;
    	 }
     }
    
        
    // Metodos Set.

    public void cambiarSolucion(IChromosome solution){
        mejorIndividuo = solution;
    }

    // Metodos generales.

    // Retorno una unica instancia de esta clase.
    public static CodificadorGenotipo obtenerInstancia(){
        if(instancia == null)
            instancia = new CodificadorGenotipo();
        return instancia;
    }
    // Inicializa los parametros del algoritmo.
    public void definirParametros(int poblacion, int numero_damas, float prob_cruce, float prob_mutacion, boolean activar_iteraciones, int iteraciones){
        algoritmo = new Genetico(numero_damas);
        algoritmo.cambiarMaxCantPoblacion(poblacion);
        algoritmo.cambiarProbMutacion(prob_mutacion);
        algoritmo.cambiarProbCruce(prob_cruce);
        if (activar_iteraciones){
            algoritmo.cambiarEstadoEvolIterada(true);
            algoritmo.cambiarMaxCantGeneraciones(iteraciones);
        }        
    }
    // Permite parar la evolucion que esta corriendo actualmente.
    public void detenerEvolucion(){
        algoritmo.detenerEvolucion();
    }
}
