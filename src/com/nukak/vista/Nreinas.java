/**
 * 
 */
package com.nukak.vista;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.border.TitledBorder;

import com.nukak.control.CodificadorGenotipo;
import com.nukak.control.Genetico;

import javax.swing.JScrollPane;

import org.jgap.Gene;
import org.jgap.impl.IntegerGene;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.UIManager;


/**
 * @author FTSTECNOLOGIA
 *
 */
public final class Nreinas implements Observer {

	private JFrame frame;
	private JTabbedPane tabbedPane;
	private JPanel datos;
	private JPanel tablero;
	private JPanel panelControles;
	private JPanel panelMensajes;
	private JSpinner numeroReinasJSpinner;
	private JSpinner poblacionInicialJSpinner;
	private JSpinner probabilidadCruceJSpinner;
	private JSpinner probabilidadMutacionJSpinner;
	private JLabel lblNewLabel;
	private JLabel lblPoblacinInicial;
	private JLabel lblProbabilidadCruce;
	private JLabel lblProbabilidadMutacin;
	private JTextArea mensajeJtextArea;
	private JButton btnNewButton;
	
	  private static boolean corriendo_algoritmo = false;
	    private static int cantidad_evoluciones;
	    /*parametros */
	    private static int numeroReinas ;
	    private static boolean activar_iteraciones = false;
	    private static int iteraciones = 100;
	    /* parametros */
	    private static long tiempo_inicial, tiempo_final;
	    private JScrollPane scrollPane;
	    private JPanel grafico;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Nreinas window = new Nreinas();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Nreinas() {
		initialize();
	}
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 816, 722);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(getTabbedPane(), BorderLayout.CENTER);
	}

	private JTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.addTab("Datos", null, getDatos(), null);
			tabbedPane.addTab("Tablero", null, getTablero(), null);
		}
		return tabbedPane;
	}
	private JPanel getDatos() {
		if (datos == null) {
			datos = new JPanel();
			datos.setLayout(new BoxLayout(datos, BoxLayout.X_AXIS));
			datos.add(getPanelControles());
			datos.add(getPanelMensajes());
		}
		return datos;
	}
	private JPanel getTablero() {
		if (tablero == null) {
			tablero = new JPanel();
			tablero.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Solucion Graficada", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			tablero.setLayout(null);
			tablero.add(getGrafico());
			
		}
		return tablero;
	}
	private JPanel getPanelControles() {
		if (panelControles == null) {
			panelControles = new JPanel();
			panelControles.setBorder(new TitledBorder(null, "Configuraci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelControles.setLayout(null);
			panelControles.add(getNumeroReinas());
			panelControles.add(getPoblacionInicial());
			panelControles.add(getProbabilidadCruce());
			panelControles.add(getProbabilidadMutacion());
			panelControles.add(getLblNewLabel());
			panelControles.add(getLblPoblacinInicial());
			panelControles.add(getLblProbabilidadCruce());
			panelControles.add(getLblProbabilidadMutacin());
			panelControles.add(getBtnNewButton());
		}
		return panelControles;
	}
	private JPanel getPanelMensajes() {
		if (panelMensajes == null) {
			panelMensajes = new JPanel();
			panelMensajes.setBorder(new TitledBorder(null, "Mejores Soluciones", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelMensajes.setLayout(null);
			panelMensajes.add(getScrollPane());
			
		}
		return panelMensajes;
	}
	private JSpinner getNumeroReinas() {
		if (numeroReinasJSpinner == null) {
			numeroReinasJSpinner = new JSpinner();
			numeroReinasJSpinner.setModel(new SpinnerNumberModel(2, 1, 10000, 1));
			numeroReinasJSpinner.setBounds(202, 107, 107, 20);
		}
		return numeroReinasJSpinner;
	}
	private JSpinner getPoblacionInicial() {
		if (poblacionInicialJSpinner == null) {
			poblacionInicialJSpinner = new JSpinner();
			poblacionInicialJSpinner.setModel(new SpinnerNumberModel(100, 0, 10000, 1));
			poblacionInicialJSpinner.setBounds(202, 138, 107, 20);
		}
		return poblacionInicialJSpinner;
	}
	private JSpinner getProbabilidadCruce() {
		if (probabilidadCruceJSpinner == null) {
			probabilidadCruceJSpinner = new JSpinner();
			probabilidadCruceJSpinner.setModel(new SpinnerNumberModel(new Float(0.80), new Float(0.00), new Float(1.00), new Float(0.01)));
			probabilidadCruceJSpinner.setBounds(202, 169, 107, 20);
		}
		return probabilidadCruceJSpinner;
	}
	private JSpinner getProbabilidadMutacion() {
		if (probabilidadMutacionJSpinner == null) {
			probabilidadMutacionJSpinner = new JSpinner();
			probabilidadMutacionJSpinner.setModel(new SpinnerNumberModel(new Float(0.20), new Float(0.00), new Float(1.00), new Float(0.01)));
			probabilidadMutacionJSpinner.setBounds(202, 200, 107, 20);
		}
		return probabilidadMutacionJSpinner;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("N\u00FAmero Reinas");
			lblNewLabel.setBounds(46, 109, 124, 17);
		}
		return lblNewLabel;
	}
	private JLabel getLblPoblacinInicial() {
		if (lblPoblacinInicial == null) {
			lblPoblacinInicial = new JLabel("Poblaci\u00F3n Inicial");
			lblPoblacinInicial.setBounds(46, 141, 124, 17);
		}
		return lblPoblacinInicial;
	}
	private JLabel getLblProbabilidadCruce() {
		if (lblProbabilidadCruce == null) {
			lblProbabilidadCruce = new JLabel("Probabilidad Cruce");
			lblProbabilidadCruce.setBounds(46, 172, 124, 17);
		}
		return lblProbabilidadCruce;
	}
	private JLabel getLblProbabilidadMutacin() {
		if (lblProbabilidadMutacin == null) {
			lblProbabilidadMutacin = new JLabel("Probabilidad Mutaci\u00F3n");
			lblProbabilidadMutacin.setBounds(46, 202, 124, 17);
		}
		return lblProbabilidadMutacin;
	}
	private JTextArea getMensajeJtextArea() {
		if (mensajeJtextArea == null) {
			mensajeJtextArea = new JTextArea();
		}
		return mensajeJtextArea;
	}
	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Buscar Soluci\u00F3n");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println(" buscar ");
				      cantidad_evoluciones++;
				        if (!corriendo_algoritmo) {
				            if (sonParametrosValidos()) {
				                corriendo_algoritmo = true;
				                mostrarInformacion(" ");
				                mostrarInformacion("**** Inicio busqueda por evolución " + cantidad_evoluciones + " ****");

				                CodificadorGenotipo.obtenerInstancia().definirParametros(((Integer) getPoblacionInicial().getValue()).intValue(),
				                															((Integer)getNumeroReinas().getValue()).intValue(), 
				                															((Float) getProbabilidadCruce().getValue()).floatValue()/100, 
				                															((Float) getProbabilidadMutacion().getValue()).floatValue()/100,
				                															activar_iteraciones, iteraciones);
				                // Agrego a la interfaz como observador del thread del algoritmo.
				                CodificadorGenotipo.obtenerInstancia().obtenerAlgoritmo().addObserver(Nreinas.this);
				                // Creo el thread.
				                Thread t = new Thread(CodificadorGenotipo.obtenerInstancia().obtenerAlgoritmo());
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
			});
			btnNewButton.setBounds(121, 302, 173, 30);
		}
		return btnNewButton;
	}

	/**
	 * 
	 */
	protected void finalizarAlgoritmo() {
		corriendo_algoritmo = false;        
        CodificadorGenotipo.obtenerInstancia().detenerEvolucion();
		
	}

	/**
	 * @param string
	 */
	protected void mostrarInformacion(String mensaje) {
		getMensajeJtextArea().setText(mensaje + "\n"+getMensajeJtextArea().getText());      
		
	}

	/**
	 * @return
	 */
	protected boolean sonParametrosValidos() {
		// TODO Auto-generated method stub
		return true;
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
					+ " Mejor Individuo: "
					+ CodificadorGenotipo.obtenerInstancia()
							.obtenerMejorSolucionString() + "\n"
					+ " Generacion número: " + ag.obtenerNumEvoluciones()
					+ "\n" + " Tiempo: "
					+ ((tiempo_final - tiempo_inicial) ) + " Milisegundos";
			mostrarInformacion(informacion);
			
			// Actualizo la barra de progreso.
			// actualizarBarraProgreso();
			if (ag.evolucionLimitada()
					&& (ag.obtenerNumEvoluciones() + 1 == ag
							.obtenerMaxCantEvolucion())) {
			
				finalizarAlgoritmo();
				
			}
		} else {
			// colocarBufferArchivo(ag);
			finalizarAlgoritmo();
			// mensaje de finalizacion 
			  mostrarInformacion("****  Fin de la busqueda  ****");
			// pintar el tablero de reinas
			pintarTablero();
		}
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 22, 377, 621);
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPane.setViewportView(getMensajeJtextArea());
			
			
			
		}
		return scrollPane;
	}
	
	private void pintarTablero() {
		numeroReinas =((Integer)getNumeroReinas().getValue()).intValue();
		getGrafico().removeAll();
		getGrafico().setLayout((new GridLayout(numeroReinas,numeroReinas,10,10)));
					System.out.println("pintando tablero");
					 final ChessBoard chesBoard =new ChessBoard();
					 Runnable r = new Runnable() {

				            @Override
				            public void run() {
				            	
				            	
				            	System.out.println(CodificadorGenotipo.obtenerInstancia()
								.obtenerMejorSolucionArray());
				            	Gene[] genes =CodificadorGenotipo.obtenerInstancia()
										.obtenerMejorSolucionArray();
				            	 String fila = "";
				            	for (int j = 0; j < numeroReinas; j++) {
									
				            	boolean blackSquare = false;
				            	boolean fin = false;
				                int columna;
				                for (columna = 0; columna < genes.length && !fin; columna++){
				                	
				                    int genValue = ((IntegerGene)genes[columna]).intValue();
				                    if (genValue == j)
				                        fin = true;
				                }
				                columna--;
				                for (int i = 0; i < columna; i++){
				                    fila += "[ ] ";
				                   
				                    chesBoard.addColoredUnicodeCharToContainer(
				                            "", getGrafico(),
				                            new Color(203,203,197),
				                            Color.DARK_GRAY,
				                            false);
				                          
				                }
				               
				                fila += "[@] ";
				                chesBoard.addColoredUnicodeCharToContainer(
			                            "\u2654", getGrafico(),
			                            new Color(203,203,197),
			                            Color.DARK_GRAY,
			                            true);
			                           
				                columna++;
				               
				                for (int i = columna; i < genes.length; i++){
				                    fila += "[ ] ";
				                    
				                    chesBoard.addColoredUnicodeCharToContainer(
				                            "", getGrafico(),
				                            new Color(203,203,197),
				                            Color.DARK_GRAY,
				                            false);
				                            
				                }
				                System.out.println(fila);
				                fila="";
				            	}
				               
				          			            	 
				            	}
				           
				        };
				        SwingUtilities.invokeLater(r);
		
	}
	private JPanel getGrafico() {
		if (grafico == null) {
			grafico = new JPanel();
			grafico.setBounds(10, 29, 355, 349);
		}
		return grafico;
	}
}
