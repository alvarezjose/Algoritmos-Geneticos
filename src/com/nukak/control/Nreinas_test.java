/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nukak.control;

import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author FTSTECNOLOGIA
 */
public final class Nreinas_test implements Observer {

	private static boolean corriendo_algoritmo;
	private static int cantidad_evoluciones;
	/* parametros */
	private static int poblacion = 100;
	private static int numero_damas = 4;
	private static float prob_cruce = 0.9f;
	private static float prob_mutacion = 0.2f;
	private static boolean activar_iteraciones = false;
	private static int iteraciones = 100;
	/* parametros */
	private static long tiempo_inicial, tiempo_final;

	// Chequea que los parametros ingresados sean validos.
	private boolean sonParametrosValidos() {
		// if((((Double)jSpinner4.getValue()).floatValue() != 0.00) &&
		// (((Integer)jSpinner1.getValue()).intValue() != 0) &&
		// (((Double)jSpinner3.getValue()).floatValue())!= 0.00)
		return true;
		// return false;
	}

	public static void main(String[] args) {
		Nreinas_test nreinas = new Nreinas_test();
		cantidad_evoluciones++;
		if (!corriendo_algoritmo) {
			if (nreinas.sonParametrosValidos()) {
				corriendo_algoritmo = true;
				nreinas.mostrarInformacion(" ");
				nreinas.mostrarInformacion("**** Evolución  "
						+ cantidad_evoluciones + " ****");

				CodificadorGenotipo.obtenerInstancia().definirParametros(
						poblacion, numero_damas, prob_cruce, prob_mutacion,
						activar_iteraciones, iteraciones);
				// Agrego a la interfaz como observador del thread del
				// algoritmo.
				CodificadorGenotipo.obtenerInstancia().obtenerAlgoritmo()
						.addObserver(nreinas);
				// Creo el thread.
				Thread t = new Thread(CodificadorGenotipo
						.obtenerInstancia().obtenerAlgoritmo());
				// Seteo el tiempo justo antes de empezar a correr el algoritmo.
				tiempo_inicial = System.currentTimeMillis();
				// Corro el Thread y este invoca el metodo run de Genetico.
				t.start();
			}
		} else {
			// Finaliza el algoritmo cuando se presiona Stop.
			finalizarAlgoritmo();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		Genetico ag = (Genetico) o;
		double valor_fitness = ((Double) arg).doubleValue();
		if (ag.activadaEvolucion()) {
			// Actualizo el tiempo en que termina la n-esima generacion.
			tiempo_final = System.currentTimeMillis();
			// Actializo la grafica.
			// grafico_lineal.actualizarSerie(ag.obtenerNumEvoluciones(),valor_fitness);
			// Muestro la información de la solucion.
			String informacion = "\n"
					+ " Mejor solución: "
					+ CodificadorGenotipo.obtenerInstancia()
							.obtenerMejorSolucionString() + "\n"
					+ " Número de generaciones: " + ag.obtenerNumEvoluciones()
					+ "\n" + " Tiempo: "
					+ ((tiempo_final - tiempo_inicial) / 1000) + " segundos";
			mostrarInformacion(informacion);
			// Actualizo la barra de progreso.
			// actualizarBarraProgreso();
			if (ag.evolucionLimitada()
					&& (ag.obtenerNumEvoluciones() + 1 == ag
							.obtenerMaxCantEvolucion())) {
				// colocarBufferArchivo(ag);
				finalizarAlgoritmo();
			}
		} else {
			// colocarBufferArchivo(ag);
			finalizarAlgoritmo();
		}
	}

	private static void finalizarAlgoritmo() {
		corriendo_algoritmo = false;
		CodificadorGenotipo.obtenerInstancia().detenerEvolucion();
	}

	private void mostrarInformacion(String mensaje) {
		System.out.println(mensaje);
	}

}
