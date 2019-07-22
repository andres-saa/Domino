package domino;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.applet.AudioClip;
import java.util.TimeZone;;

public class Domino extends JFrame {
	/**
	 *Se define el escucha para para el mouse 
	 */
	private Escucha escucharMouse;
	/**
	 * Se guardan las fichas del jugador 1.
	 */
	ArrayList<Ficha> juego1 = new ArrayList<Ficha>();
	/**
	 * Se guardan las fichas del jugador 2.
	 */
	ArrayList<Ficha> juego2 = new ArrayList<Ficha>();
	/**
	 * Este valor se usará para tomar una ficha aleatorimente.
	 */
	Random PrimeraFicha = new Random();
	Random aleatorio = new Random();
	Jugador jugador = new Jugador(juego1);
	Jugador casa = new Jugador(juego2);
	Baraja baraja = new Baraja();
	/**
	 * Se establece el fondo del tablero, donde se colocarán las fichas.
	 */
	JLabel imagenDeFondo = new JLabel(new ImageIcon("src/imagenes/fondo.jpg"));
	boolean escogiendoFichaInicial = true;
	/**
	 * Se guardan todas las fichas del dominó.
	 */
	ArrayList<Ficha> mesa = new ArrayList<Ficha>();
	/**
	 * Se establece el panel donde se coloca la imágen de fondo.
	 */
	JPanel panel = new JPanel();
	Jugador turno = new Jugador(null);
	Ficha ultimaFichaIzq;
	Ficha ultimaFichaDer;
	int ultimaFichaIzqLadoLibre;
	int ultimaFichaDerLadoLibre;

	public Domino() {
		try {
			initGUI();
			escogiendoFichaInicial = true;
			// Default window config.
			this.setUndecorated(false);
			pack();
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setVisible(true);
			this.setSize(1280, 720);
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		}

		catch (Exception e) {
			// TODO: handle expection
			JOptionPane.showMessageDialog(null, "No se ha encontrado la imagen de fondo");
		}
	}

	private void initGUI() {

		crearMesa();
		this.getContentPane().add(panel);
		panel.setLayout(null);
	}

	public void crearMesa() {
		revolverMesa();
		escucharMouse = new Escucha();
		imagenDeFondo.setBounds(0, 0, 1280, 720);
		panel.add(imagenDeFondo);

		for (int cual = 0; cual < 28; cual++) {
			baraja.getBaraja().get(cual).addMouseListener(escucharMouse);
			imagenDeFondo.add(baraja.getBaraja().get(cual));
		}
		for (int cual = 0; cual < baraja.getBaraja().size(); cual++) {
			taparFicha(baraja.getBaraja().get(cual));
		}

		establecerPosicionAlasFichas();

	}

	public void revolverMesa() {

		Baraja barajaAuxiliar = new Baraja();
		for (int i = 0; i < barajaAuxiliar.getBaraja().size(); i++)
			barajaAuxiliar.getBaraja().remove(i);

		for (int i = 0; i < baraja.getBaraja().size(); i++) {
			int cual = aleatorio.nextInt(baraja.getBaraja().size());
			barajaAuxiliar.getBaraja().add(baraja.getBaraja().get(cual));
			baraja.getBaraja().remove(cual);

		}

		for (int i = 0; i < barajaAuxiliar.getBaraja().size(); i++) {
			int cual = aleatorio.nextInt(barajaAuxiliar.getBaraja().size());
			System.currentTimeMillis();
			baraja.getBaraja().add(barajaAuxiliar.getBaraja().get(cual));
			barajaAuxiliar.getBaraja().remove(cual);
		}

	}

	public void organizar(Jugador quien) {

		int inicialjugador = 132;
		int inicialcasa = 132;

		if (quien == jugador) {
			for (int i = 0; i < jugador.getJuego().size(); i++) {
				jugador.getJuego().get(i).setBounds(inicialjugador, 611, 42, 85);
				inicialjugador += 46;
				actualizar();
			}
		} else if (quien == casa) {
			for (int i = 0; i < casa.getJuego().size(); i++) {
				casa.getJuego().get(i).setBounds(inicialcasa, 21, 42, 85);
				inicialcasa += 46;
				actualizar();
			}
		}

	}

	public void establecerPosicionAlasFichas() {

		int posicionInicialx = 200;
		int posicionInicialy = 180;
		int permutador = 0;
		for (int i = 0; i < 4; i++) {
			for (int cual = permutador; cual < permutador + 7; cual++) {
				baraja.getBaraja().get(cual).setBounds(posicionInicialx, posicionInicialy, 43, 85);
				posicionInicialx += 48;
			}
			posicionInicialx = 200;
			permutador += 7;
			posicionInicialy += 89;
		}

	}
	
	public void rotar(Ficha cual, String orientacion) {
		cual.setBounds(cual.getBounds().x, cual.getBounds().y, cual.getBounds().height, cual.getBounds().width);
		if (orientacion == "izq") {
			cual.setIcon(new ImageIcon("src/fichasLeft/" + cual.getLado1() + cual.getLado2() + ".png"));
			cual.setRotadaHacia("izq");
		}
		if (orientacion == "der") {
			cual.setIcon(new ImageIcon("src/fichasRight/" + cual.getLado1() + cual.getLado2() + ".png"));
			cual.setRotadaHacia("der");
		}
		actualizar();
	}

	public void actualizar() {

		imagenDeFondo.removeAll();
		for (int i = 0; i < baraja.getBaraja().size(); i++) {
			imagenDeFondo.add(baraja.getBaraja().get(i));
		}

		for (int i = 0; i < casa.getJuego().size(); i++) {
			imagenDeFondo.add(casa.getJuego().get(i));
		}
		for (int i = 0; i < jugador.getJuego().size(); i++) {
			imagenDeFondo.add(jugador.getJuego().get(i));
		}
		for (int i = 0; i < mesa.size(); i++) {
			imagenDeFondo.add(mesa.get(i));
		}

	}

	public void organizarFichasParaComer() {
		int posicionInicialx = 1085;
		int posicionInicialy = 25;
		int permutador = 0;
		for (int i = 0; i < baraja.getBaraja().size() / 2; i++) {
			for (int cual = permutador; cual < permutador + baraja.getBaraja().size() / 7; cual++) {
				baraja.getBaraja().get(cual).setBounds(posicionInicialx, posicionInicialy, 43, 85);
				posicionInicialx += 48;
			}
			posicionInicialx = 1085;
			permutador += 2;
			posicionInicialy += 89;
		}

	}

	public void iniciarPartida() {
		escogiendoFichaInicial = false;

		taparFichas(casa);
		verFichas(jugador);
		actualizar();
		organizarFichasParaComer();

	}

	public void extrategiaDeLaCasa() {

		actualizar();
		while (turno == casa) {

			if (puedeJugar(casa, "izq")) {
				if (fichaQueCasaPuedeJugar("izq") != null) {
					jugar(casa, fichaQueCasaPuedeJugar("izq"), "izq");
					turno = jugador;
				}
			} else if (puedeJugar(casa, "der")) {
				if (fichaQueCasaPuedeJugar("der") != null) {
					jugar(casa, fichaQueCasaPuedeJugar("der"), "der");
					turno = jugador;
				}
			} else if (puedeComer(casa)) {
				comer(casa, baraja.getBaraja().get(baraja.getBaraja().size() - 1));
				extrategiaDeLaCasa();
			} else
				turno = jugador;
		}
		detenerSegundos(2);
		actualizar();
		if (casa.getJuego().isEmpty() && !mesa.isEmpty()) {
			JOptionPane.showMessageDialog(null, "MESA GANA!");
		}

	}

	public void comer(Jugador quien, Ficha cual) {


			if (quien == jugador) {
				if (puedeComer(jugador) && !baraja.getBaraja().isEmpty()&& mesa.size()>1  && estaEn(cual,baraja.getBaraja())) {
					
					cual.setBounds(jugador.getJuego().get(jugador.getJuego().size() - 1).getBounds().x + 46,
							jugador.getJuego().get(jugador.getJuego().size() - 1).getBounds().y, 43, 85);
					quien.getJuego().add(cual);
					baraja.getBaraja().remove(cual);
					organizar(jugador);
					
					actualizar();

				}
				verFicha(cual);
			} else if (quien == casa) {
				if (puedeComer(casa) && !baraja.getBaraja().isEmpty() && mesa.size()>1 && estaEn(cual,baraja.getBaraja())) {
					cual.setBounds(casa.getJuego().get(casa.getJuego().size() - 1).getBounds().x + 46,
							casa.getJuego().get(jugador.getJuego().size() - 1).getBounds().y, 43, 85);
					
					quien.getJuego().add(cual);
					baraja.getBaraja().remove(cual);
					
					organizar(casa);
					actualizar();
				}
			}
	}

	public boolean estaEn(Ficha cual, ArrayList<Ficha> donde) {
		boolean veredicto = donde.contains(cual);
		return veredicto;
	}

	public void repartir(Jugador player) {

		if (player == jugador) {

			for (int i = 0; i < 7; i++) {
				int cual = aleatorio.nextInt(baraja.getBaraja().size());
				jugador.getJuego().add(baraja.getBaraja().get(cual));
				baraja.getBaraja().remove(cual);
				organizar(jugador);

			}
		} else if (player == casa) {

			for (int i = 0; i < 7; i++) {
				int cual = aleatorio.nextInt(baraja.getBaraja().size());
				// baraja.getBaraja().get(cual).setBounds(inicial, 21, 42, 85);
				casa.getJuego().add(baraja.getBaraja().get(cual));
				baraja.getBaraja().remove(cual);
				organizar(casa);

			}
		}

		taparFichas(casa);
		taparMesa();
		verFichas(jugador);

	}

	public Ficha fichaQueCasaPuedeJugar(String lado) {
		Ficha cual = null;

		if (lado == "izq") {

			for (int i = 0; i < casa.getJuego().size(); i++) {

				if (casa.getJuego().get(i).getLado1() == ultimaFichaIzqLadoLibre
						|| casa.getJuego().get(i).getLado2() == ultimaFichaIzqLadoLibre) {
					cual = casa.getJuego().get(i);
					break;
				}

			}
		}

		if (lado == "der") {

			for (int i = 0; i < casa.getJuego().size(); i++) {

				if (casa.getJuego().get(i).getLado1() == ultimaFichaDerLadoLibre
						|| casa.getJuego().get(i).getLado2() == ultimaFichaDerLadoLibre) {
					cual = casa.getJuego().get(i);
					break;
				}

			}
		}

		return cual;
	}

	public void taparFicha(Ficha cual) {

		cual.setIcon(new ImageIcon("src/fichas/alr.png"));
		cual.setDestapada(false);
	}

	public void taparFichas(Jugador quien) {
		for (int i = 0; i < quien.getJuego().size(); i++) {
			taparFicha(quien.getJuego().get(i));
		}
	}

	public boolean puedeComer(Jugador quien) {
		boolean veredicto = true;
		if (turno == quien && mesa.size()>1) {
			for (int i = 0; i < quien.getJuego().size(); i++) {

				if      (  quien.getJuego().get(i).getLado1() == ultimaFichaIzqLadoLibre
						|| quien.getJuego().get(i).getLado2() == ultimaFichaIzqLadoLibre
						|| quien.getJuego().get(i).getLado1() == ultimaFichaDerLadoLibre
						|| quien.getJuego().get(i).getLado2() == ultimaFichaDerLadoLibre) {
					
					veredicto = false;
				}
			}
		} 
		return veredicto;
	}

	public void verFicha(Ficha cual) {

		if (cual.getRotadaHacia() == "der") {

			cual.setIcon(new ImageIcon("src/fichasRight/" + cual.getLado1() + cual.getLado2() + ".png"));
			cual.setDestapada(true);
		} else if (cual.getRotadaHacia() == "izq") {

			cual.setIcon(new ImageIcon("src/fichasLeft/" + cual.getLado1() + cual.getLado2() + ".png"));
			cual.setDestapada(true);
		} else
			cual.setIcon(new ImageIcon("src/fichas/" + cual.getLado1() + cual.getLado2() + ".png"));
		cual.setDestapada(true);

	}

	public boolean puedeJugar(Jugador quien, String lado) {
		boolean veredicto = false;

		if (turno == quien) {
			if (lado == "izq") {
				for (int i = 0; i < quien.getJuego().size(); i++) {
					if (quien.getJuego().get(i).getLado1() == ultimaFichaIzqLadoLibre
							|| quien.getJuego().get(i).getLado2() == ultimaFichaIzqLadoLibre || mesa.isEmpty()) {
						veredicto = true;
					}
				}
			}

			if (lado == "der") {
				for (int i = 0; i < quien.getJuego().size(); i++) {
					if (quien.getJuego().get(i).getLado1() == ultimaFichaDerLadoLibre
							|| quien.getJuego().get(i).getLado2() == ultimaFichaDerLadoLibre || mesa.isEmpty()) {
						veredicto = true;
					}
				}
			}
		} else {
			veredicto = false;
		}

		return veredicto;
	}

	public void verFichas(Jugador quien) {
		for (int i = 0; i < quien.getJuego().size(); i++) {
			verFicha(quien.getJuego().get(i));
		}

	}

	public void taparMesa() {
		for (int i = 0; i < baraja.getBaraja().size(); i++) {
			taparFicha(baraja.getBaraja().get(i));
		}
	}

	public void detenerSegundos(int segundos) {

	}

	public void jugar(Jugador quien, Ficha cual, String lado) {

		String mensaje = null;
		// si estamos empezando
		if (estaEn(cual, quien.getJuego()) == true && turno == quien) {
			actualizar();
			if (mesa.isEmpty())
				lado = "centro";
			if (mesa.isEmpty() && lado == "centro") {
				if (cual.getLado1() != cual.getLado2()) {
					rotar(cual, "izq");
					cual.setBounds(500, 310, cual.getBounds().width, cual.getBounds().height);

					ultimaFichaIzq = cual;
					ultimaFichaDer = cual;
					ultimaFichaIzqLadoLibre = cual.getLado2();
					ultimaFichaDerLadoLibre = cual.getLado1();
				} else if (cual.getLado1() == cual.getLado2()) {
					cual.setBounds(515, 310, cual.getBounds().width, cual.getBounds().height);
					ultimaFichaIzq = cual;
					ultimaFichaDer = cual;
					ultimaFichaIzqLadoLibre = cual.getLado1();
					ultimaFichaDerLadoLibre = cual.getLado1();
				}
			}

			// jugar por la izquierda
			if (lado == "izq") {

				if (cual.getLado1() == ultimaFichaIzqLadoLibre || cual.getLado2() == ultimaFichaIzqLadoLibre) {

					if (ultimaFichaIzq.getBounds().width == cual.getBounds().width) {

						if (cual.getLado1() == ultimaFichaIzqLadoLibre)
							rotar(cual, "izq");
						else if (cual.getLado2() == ultimaFichaIzqLadoLibre)
							rotar(cual, "der");

						cual.setBounds(ultimaFichaIzq.getBounds().x - (cual.getBounds().width + 1),
								ultimaFichaIzq.getBounds().y + 21, cual.getBounds().width, cual.getBounds().height);
						ultimaFichaIzq = cual;

						if (cual.getLado1() == ultimaFichaIzqLadoLibre)
							ultimaFichaIzqLadoLibre = cual.getLado2();
						else if (cual.getLado2() == ultimaFichaIzqLadoLibre)
							ultimaFichaIzqLadoLibre = cual.getLado1();

					}
					if (ultimaFichaIzq.getBounds().width == cual.getBounds().height
							&& cual.getLado1() != cual.getLado2()) {

						if (cual.getLado1() == ultimaFichaIzqLadoLibre)
							rotar(cual, "izq");
						else if (cual.getLado2() == ultimaFichaIzqLadoLibre)
							rotar(cual, "der");

						cual.setBounds(ultimaFichaIzq.getBounds().x - (cual.getBounds().width + 1),
								ultimaFichaIzq.getBounds().y, cual.getBounds().width, cual.getBounds().height);
						ultimaFichaIzq = cual;

						if (cual.getLado1() == ultimaFichaIzqLadoLibre)
							ultimaFichaIzqLadoLibre = cual.getLado2();
						else if (cual.getLado2() == ultimaFichaIzqLadoLibre)
							ultimaFichaIzqLadoLibre = cual.getLado1();

					}
					if (ultimaFichaIzq.getBounds().width == cual.getBounds().height
							&& cual.getLado1() == cual.getLado2()) {

						cual.setBounds(ultimaFichaIzq.getBounds().x - (cual.getBounds().width + 1),
								ultimaFichaIzq.getBounds().y - 20, cual.getBounds().width, cual.getBounds().height);
						ultimaFichaIzq = cual;

						if (cual.getLado1() == ultimaFichaIzqLadoLibre)
							ultimaFichaIzqLadoLibre = cual.getLado2();
						else if (cual.getLado2() == ultimaFichaIzqLadoLibre)
							ultimaFichaIzqLadoLibre = cual.getLado1();
					}

				} else if (quien == jugador) {
				JOptionPane.showMessageDialog(null, "no puese jugar esa ficha por la izquierda");
				mensaje = "no puese jugar esa ficha por la izquierda";
			}
		}

			// jugarPorLa derecha

			if (lado == "der") {
				if (cual.getLado1() == ultimaFichaDerLadoLibre || cual.getLado2() == ultimaFichaDerLadoLibre) {

					// si las fichas tienen la misma orientacion

					if (ultimaFichaDer.getBounds().width == cual.getBounds().width) {

						if (cual.getLado1() == ultimaFichaDerLadoLibre)
							rotar(cual, "der");
						else if (cual.getLado2() == ultimaFichaDerLadoLibre)
							rotar(cual, "izq");

						cual.setBounds(ultimaFichaDer.getBounds().x + (cual.getBounds().height + 1),
								ultimaFichaDer.getBounds().y + 21, cual.getBounds().width, cual.getBounds().height);
						ultimaFichaDer = cual;

						if (cual.getLado1() == ultimaFichaDerLadoLibre)
							ultimaFichaDerLadoLibre = cual.getLado2();
						else if (cual.getLado2() == ultimaFichaDerLadoLibre)
							ultimaFichaDerLadoLibre = cual.getLado1();

					}
					if (ultimaFichaDer.getBounds().width == cual.getBounds().height
							&& cual.getLado1() != cual.getLado2()) {

						if (cual.getLado1() == ultimaFichaDerLadoLibre)
							rotar(cual, "der");
						else if (cual.getLado2() == ultimaFichaDerLadoLibre)
							rotar(cual, "izq");

						cual.setBounds(ultimaFichaDer.getBounds().x + (cual.getBounds().width + 1),
								ultimaFichaDer.getBounds().y, cual.getBounds().width, cual.getBounds().height);
						ultimaFichaDer = cual;

						if (cual.getLado1() == ultimaFichaDerLadoLibre)
							ultimaFichaDerLadoLibre = cual.getLado2();
						else if (cual.getLado2() == ultimaFichaDerLadoLibre)
							ultimaFichaDerLadoLibre = cual.getLado1();

					}

					if (ultimaFichaDer.getBounds().width == cual.getBounds().height
							&& cual.getLado1() == cual.getLado2()) {

						cual.setBounds(ultimaFichaDer.getBounds().x + (cual.getBounds().height + 1),
								ultimaFichaDer.getBounds().y - 20, cual.getBounds().width, cual.getBounds().height);
						ultimaFichaDer = cual;

						if (cual.getLado1() == ultimaFichaDerLadoLibre)
							ultimaFichaDerLadoLibre = cual.getLado2();
						else if (cual.getLado2() == ultimaFichaDerLadoLibre)
							ultimaFichaDerLadoLibre = cual.getLado1();
					}

				} 
			else if (quien == jugador) {
				JOptionPane.showMessageDialog(null, "no puese jugar esa ficha por la derecha");
				mensaje = "no puese jugar esa ficha por la derecha";
			}

		}
	}

		if (mensaje != "no puese jugar esa ficha por la izquierda"
				&& mensaje != "no puese jugar esa ficha por la derecha") {
			
			mesa.add(cual);
			quien.getJuego().remove(cual);
			organizar(quien);
			verFicha(cual);
			actualizar();
			
			if (turno==jugador)
				turno=casa;
			else if (turno==casa)
				turno = jugador;

		}

	}

	private class Escucha implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {

			// TODO Auto-generated method stub

			if (e.getButton() == 1) {

				Ficha fichaJugador = (Ficha) e.getSource();

				if (escogiendoFichaInicial) {

					// panel.repaint();

					Random aleatorio = new Random();
					Ficha fichaCasa = baraja.getBaraja().get(aleatorio.nextInt(baraja.getBaraja().size()));
					verFicha(fichaJugador);

					do {
						fichaCasa = baraja.getBaraja().get(aleatorio.nextInt(baraja.getBaraja().size()));
					} while (fichaCasa == fichaJugador || fichaCasa.isDestapada()
							|| fichaCasa.getValor() == fichaJugador.getValor());

					verFicha(fichaCasa);
					if (fichaCasa.getValor() > fichaJugador.getValor()) {
						JOptionPane.showMessageDialog(null, "INICIA LA CASA");
						turno = casa;
						repartir(jugador);
						repartir(casa);

						actualizar();
						extrategiaDeLaCasa();
						iniciarPartida();

					} else {
						JOptionPane.showMessageDialog(null, "INICIAS TU");
						turno = jugador;
						repartir(jugador);
						repartir(casa);
						iniciarPartida();
						actualizar();
					}
					escogiendoFichaInicial = false;
					iniciarPartida();
					actualizar();

				}

				else if (turno == jugador) {
					if (puedeJugar(jugador, "izq")) {
						jugar(jugador, fichaJugador, "izq");
						turno = casa;
						extrategiaDeLaCasa();
						
					} else if (puedeComer(jugador)) {
						comer(jugador, fichaJugador);
						actualizar();
					}else JOptionPane.showMessageDialog(null, "NO PUEDES COMER, TIRNES FICHAS PARA JUGAR");

					if (jugador.getJuego().isEmpty() && !mesa.isEmpty()) {
						JOptionPane.showMessageDialog(null, "GANASTE!");
					}

				}
			}

			if (e.getButton() == 3 && !escogiendoFichaInicial) {

				Ficha fichaJugador = (Ficha) e.getSource();

				if (puedeJugar(jugador, "der")) {
					jugar(jugador, fichaJugador, "der");
					turno = casa;
					extrategiaDeLaCasa();
				}

				if (jugador.getJuego().isEmpty() && !mesa.isEmpty()) {
					JOptionPane.showMessageDialog(null, "GANASTE!");
				}

				actualizar();

			}
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
}
