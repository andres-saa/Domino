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

public class Domino extends JFrame {

	private Escucha escucharMouse;
	ArrayList<Ficha> juego1 = new ArrayList<Ficha>();
	ArrayList<Ficha> juego2 = new ArrayList<Ficha>();
	Random PrimeraFicha = new Random();
	Random aleatorio = new Random();
	Jugador jugador = new Jugador(juego1);
	Jugador casa = new Jugador(juego2);
	Baraja baraja = new Baraja();
	JLabel imagenDeFondo = new JLabel(new ImageIcon("src/imagenes/fondo.jpg"));
	boolean escogiendoFichaInicial = true;
	boolean puedeComerJugador = false;
	boolean puedeComerCasa = false;
	boolean puedeJugarJugador = false;
	boolean puedeJugarCasa = false;
	boolean comiendo = false;
	ArrayList<Ficha> mesa = new ArrayList<Ficha>();
	JPanel panel = new JPanel();
	int colocarComidaxjugador = 46;
	int colocarComidaxcasa = 46;
	Jugador turno =new Jugador(null);
	Ficha ultimaFichaIzq;
	Ficha ultimaFichaDer;
	int ultimaFichaIzqLadoLibre;
	int ultimaFichaDerLadoLibre;

	// private BufferedImage bufferFondo = null;
	// private JLabel centralLabel;

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
				baraja.getBaraja().get(cual).setPosx(posicionInicialx);
				baraja.getBaraja().get(cual).setPosy(posicionInicialy);
			}
			posicionInicialx = 200;
			permutador += 7;
			posicionInicialy += 89;
		}

	}
	public void rotar(Ficha cual, String orientacion) {
		cual.setBounds(cual.getBounds().x, cual.getBounds().y, cual.getBounds().height,cual.getBounds().width);
		if (orientacion == "izq") {
			cual.setIcon(new ImageIcon("src/fichasLeft/" + cual.getLado1() + cual.getLado2() + ".png"));
		}
		if (orientacion == "der") {
			cual.setIcon(new ImageIcon("src/fichasRight/"+ cual.getLado1() + cual.getLado2() + ".png"));
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
		repartir(jugador);
		repartir(casa);
		taparFichas(casa);
		verFichas(jugador);
		actualizar();
		organizarFichasParaComer();
		

	}

	public void comer(Jugador quien, Ficha cual) {

		if (estaEn(cual, baraja.getBaraja()) && turno==quien) {
			cual.setBounds(200, 555, 43, 85);
			quien.getJuego().add(cual);
			baraja.getBaraja().remove(cual);

			if (quien == jugador) {
				cual.setBounds(jugador.getJuego().get(6).getBounds().x + colocarComidaxjugador,
						jugador.getJuego().get(6).getBounds().y, 43, 85);
				verFicha(cual);
				colocarComidaxjugador += 46;
				organizar(jugador);
			} else if (quien == casa) {
				cual.setBounds(casa.getJuego().get(6).getBounds().x + colocarComidaxcasa,
						casa.getJuego().get(6).getBounds().y, 43, 85);
				colocarComidaxcasa += 46;
				organizar(casa);
			}

			// cual.getBounds();
			actualizar();
			// organizarFichasParaComer();
			// comiendo=false;
		}
	}

	public boolean estaEn(Ficha cual, ArrayList<Ficha> donde) {
		boolean veredicto=donde.contains(cual);
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

	public void taparFicha(Ficha cual) {
		cual.setIcon(new ImageIcon("src/fichas/alr.png"));
		cual.setDestapada(false);
	}

	public void taparFichas(Jugador quien) {
		for (int i = 0; i < quien.getJuego().size(); i++) {
			taparFicha(quien.getJuego().get(i));
		}
	}

	public void verFicha(Ficha cual) {
		cual.setIcon(new ImageIcon("src/fichas/" + cual.getLado1() + cual.getLado2() + ".png"));
		cual.setDestapada(true);

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

	public void detenerSegudos(int segundos) {
		try {
			Thread.sleep(segundos * 200);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
					cual.setBounds(450, 300, cual.getBounds().width, cual.getBounds().height);
					ultimaFichaIzq = cual;
					ultimaFichaDer = cual;
					ultimaFichaIzqLadoLibre = cual.getLado2();
					ultimaFichaDerLadoLibre = cual.getLado1();
				} else if (cual.getLado1() == cual.getLado2()) {
					cual.setBounds(450, 300, cual.getBounds().width, cual.getBounds().height);
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

				} else {

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
							rotar(cual, "ider");
						else if (cual.getLado2() == ultimaFichaDerLadoLibre)
							rotar(cual, "izq");

						cual.setBounds(ultimaFichaDer.getBounds().x + (cual.getBounds().width + 1),
								ultimaFichaDer.getBounds().y, cual.getBounds().height, cual.getBounds().width);
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

				} else {

					JOptionPane.showMessageDialog(null, "no puese jugar esa ficha por la derecha");
					mensaje = "no puese jugar esa ficha por la derecha";

				}

			}
		}
		

		if (mensaje!="no puese jugar esa ficha por la izquierda"&& mensaje!="no puese jugar esa ficha por la derecha") {
		mesa.add(cual);
		quien.getJuego().remove(cual);
		organizar(quien);
		actualizar();
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
						turno=jugador;
					}
					else {
						JOptionPane.showMessageDialog(null, "INICIAS TU");
						turno=jugador;
					}
					escogiendoFichaInicial = false;
					iniciarPartida();
					actualizar();

				}

				else if (turno==jugador) {
					jugar(jugador, fichaJugador,"izq");
				actualizar();
				
				}
			}
			
			if (e.getButton() == 3 && !escogiendoFichaInicial) {

				Ficha fichaJugador = (Ficha) e.getSource();
				jugar(jugador, fichaJugador,"der");
				
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
