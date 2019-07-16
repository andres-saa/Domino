package domino;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Domino extends JFrame {

	private Escucha escucha;
	private boolean cara = false;
	ArrayList<Ficha> juego1 = new ArrayList<Ficha>();
	ArrayList<Ficha> juego2 = new ArrayList<Ficha>();
	// Ficha primeraFichaJugador1=new Ficha(0,0,0,new ImageIcon());
	// Ficha primeraFichaJugador2=new Ficha(0,0,0,new ImageIcon());
	Random PrimeraFicha = new Random();
	Jugador jugador=new Jugador(juego1);
	Jugador casa=new Jugador(juego2);
	Baraja baraja=new Baraja();
	JLabel imagenDeFondo = new JLabel();
			

	ArrayList<JButton> mesa = new ArrayList<JButton>();
	JPanel panel = new JPanel();

	// private BufferedImage bufferFondo = null;
	// private JLabel centralLabel;

	public Domino() {
		try {

			// fichas.repartir(jugador1);
			// fichas.repartir(jugador2);

			initGUI();

			// Default window config.
			this.setUndecorated(false);
			pack();
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setVisible(true);
			this.setSize(1280, 720);
		}

		catch (Exception e) {
			// TODO: handle expection
			JOptionPane.showMessageDialog(null, "No se ha encontrado la imagen de fondo");
		}
	}

	private void initGUI() {
		JLabel imagenDeFondo = new JLabel(new ImageIcon("src/imagenes/fondo.jpg"));
		crearMesa();
		// quienEmpieza();
		this.getContentPane().add(panel);
		panel.setLayout(null);
		imagenDeFondo.setBounds(0, 0, 1280, 720);
		panel.add(imagenDeFondo);

		// añadimos las 28 fichas al panel;
		for (int i = 0; i < 28; i++) {
			imagenDeFondo.add(mesa.get(i));
		}

	}

	public void crearMesa() {

		// para que las fichas no se repitan ya que ficha(a,b)=ficha(b,a)
		// vamos a crear los labels de las fichas y las agrega al array de la mesa.
		escucha = new Escucha();
		
		
		for (int lado1 = 0; lado1 < 28; lado1++) {
			mesa.add(new JButton(new ImageIcon("src/fichas/alr.png")));

			mesa.get(lado1).addMouseListener(escucha);
		}
		establecerPosicionAlasFichas();
	}

	public void establecerPosicionAlasFichas() {
		int posicionInicialx = 200;
		int posicionInicialy = 180;
		int permutador = 0;
		for (int i = 0; i < 4; i++) {
			for (int cual = permutador; cual < permutador + 7; cual++) {
				mesa.get(cual).setBounds(posicionInicialx, posicionInicialy, 43, 85);
				posicionInicialx += 48;
			}
			posicionInicialx = 200;
			permutador += 7;
			posicionInicialy += 89;
		}

	}

	private void actualizarFondo() {
		imagenDeFondo.removeAll();
		for (int i = 0; i < 28; i++) {
			imagenDeFondo.add(mesa.get(i));
		}
	}

	
	public void iniciarPartida() {
		baraja.repartir(jugador);
		baraja.repartir(casa);
		imagenDeFondo.removeAll();
	}
	/*public void graficarFichas(Jugador quien) {
		for (int cual=0;cual<quien.getJuego().size();cual++) {
			int posicionInicialx = 200;
			int posicionInicialy = 200;
			int permutador = 0;
			for (int i = 0; i < 4; i++) {
				for (int cual = permutador; cual < permutador + 7; cual++) {
					mesa.get(cual).setBounds(posicionInicialx, posicionInicialy, 43, 85);
					posicionInicialx += 48;
				}
				posicionInicialx = 200;
				permutador += 7;
				posicionInicialy += 89;
			}
		}
	}*/
	
	
	private class Escucha implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
		
			if (e.getButton() == 1) {
				cara = true;

				// panel.repaint();
				JButton laFicha = (JButton) e.getSource();
				int valorJugador1, valorJugador2;
				Random aleatorio = new Random();

				int numero1Jugador1 = aleatorio.nextInt(7);
				int numero2Jugador1 = aleatorio.nextInt(7);

				int numero1Jugador2, numero2Jugador2;
				JButton fichaMaquina;
				do {

					numero1Jugador2 = aleatorio.nextInt(7);
					numero2Jugador2 = aleatorio.nextInt(7);
					fichaMaquina =(JButton) laFicha.getParent().getComponents()[aleatorio.nextInt(25)];
				} while (numero1Jugador1 == numero1Jugador2 && numero2Jugador1 == numero2Jugador2 && fichaMaquina.equals(laFicha));

				
				
				if (numero1Jugador1 < numero2Jugador1) {
					laFicha.setIcon(new ImageIcon("src/fichas/" + numero1Jugador1 + "" + numero2Jugador1 + ".png"));
				} else {
					laFicha.setIcon(new ImageIcon("src/fichas/" + numero2Jugador1 + "" + numero1Jugador1 + ".png"));
				}
				
				if (numero1Jugador2 < numero2Jugador2) {
					fichaMaquina.setIcon(new ImageIcon("src/fichas/" + numero1Jugador2 + "" + numero2Jugador2 + ".png"));
				} else {
					fichaMaquina.setIcon(new ImageIcon("src/fichas/" + numero2Jugador2 + "" + numero1Jugador2 + ".png"));
				}
				
				if (numero1Jugador1+numero2Jugador1>numero1Jugador2+numero2Jugador2) {
					JOptionPane.showMessageDialog(null,"EMPIEZAS TU");
					
				}else {
					JOptionPane.showMessageDialog(null,"EMPIEZA LA CASA");
				}

			
				actualizarFondo();
				iniciarPartida();
			}
			// crearMesa();
			// mesa.get(0).add(new ImageIcon(""));
		}
		

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}
	/*
	 * public Jugador quienEmpieza() { Jugador jugadorQueEmpieza=new Jugador(null);;
	 * if (primeraFichaJugador1.getValor()>primeraFichaJugador2.getValor())
	 * jugadorQueEmpieza=jugador1; else jugadorQueEmpieza=jugador2;
	 * 
	 * return jugadorQueEmpieza; };
	 */
}
