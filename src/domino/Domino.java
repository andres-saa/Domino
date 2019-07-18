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

	private Escucha escucharMouse;
	ArrayList<Ficha> juego1 = new ArrayList<Ficha>();
	ArrayList<Ficha> juego2 = new ArrayList<Ficha>();
	Random PrimeraFicha = new Random();
	Random aleatorio = new Random();	
	Jugador jugador = new Jugador(juego1);
	Jugador casa = new Jugador(juego2);
	Baraja baraja = new Baraja();
	JLabel imagenDeFondo = new JLabel(new ImageIcon("src/imagenes/fondo.jpg"));
	boolean escogiendoFichaInicial=true;
	boolean comiendo=true;
	ArrayList<Ficha> mesa = new ArrayList<Ficha>();
	JPanel panel = new JPanel();

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
		escucharMouse= new Escucha();
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
		Baraja barajaAuxiliar=new Baraja();
		
		for (int i=0;i<baraja.getBaraja().size();i++ ){
			int cual = aleatorio.nextInt(baraja.getBaraja().size());
			barajaAuxiliar.getBaraja().add(baraja.getBaraja().get(cual));
			baraja.getBaraja().remove(cual);
		}
		
		//ArrayList<Ficha> barajaAuxiliar=new ArrayList<Ficha>();
		for (int i=0;i<barajaAuxiliar.getBaraja().size();i++ ){
			int cual = aleatorio.nextInt(barajaAuxiliar.getBaraja().size());
			System.currentTimeMillis();
			baraja.getBaraja().add(barajaAuxiliar.getBaraja().get(cual));
			barajaAuxiliar.getBaraja().remove(cual);
		}
	}

 	public void establecerPosicionAlasFichas() {
		if (escogiendoFichaInicial||comiendo==false) {
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

	}
 	public void actualizar() {
 		establecerPosicionAlasFichas();
 		imagenDeFondo.removeAll();
 		for (int i=0;i<baraja.getBaraja().size();i++) {
 			imagenDeFondo.add(baraja.getBaraja().get(i));
 		}
 		
 		for (int i=0;i<casa.getJuego().size();i++) {
 			imagenDeFondo.add(casa.getJuego().get(i));
 		}
 		for (int i=0;i<jugador.getJuego().size();i++) {
 			imagenDeFondo.add(jugador.getJuego().get(i));
 		}
 		establecerPosicionAlasFichas();
 	}

 	public void organizarFichasParaComer(){
 		int posicionInicialx = 850;
		int posicionInicialy = 42;
		int permutador = 0;
		for (int i = 0; i < 6; i++) {
			for (int cual = permutador; cual < permutador + 3; cual++) {
				baraja.getBaraja().get(cual).setBounds(posicionInicialx, posicionInicialy, 43, 85);
				posicionInicialx += 48;
			}
			posicionInicialx = 850;
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
		if (estaEn(cual,baraja)) {
		comiendo=true;
		cual.setBounds(200,555, 43, 85);
		quien.getJuego().add(cual);
		baraja.getBaraja().remove(cual);
		cual.setBounds(200,555, 43, 85);
		actualizar();
	}}
	
	public boolean estaEn(Ficha cual,Baraja baraj) {
		boolean veredicto=false;
		for (int i=0;i<baraj.getBaraja().size();i++) {
			if (cual==baraja.getBaraja().get(i)) {
				veredicto=true;
			}
		}
		return veredicto;
			
	}
	public void repartir(Jugador player) {
		int inicial = 132;
		
		if (player == jugador) {
			int cual;
			
			for (int i = 0; i < 7; i++) {
				cual=aleatorio.nextInt(baraja.getBaraja().size());
				baraja.getBaraja().get(cual).setBounds(inicial, 613, 42, 85);
				inicial += 46;
				jugador.getJuego().add(baraja.getBaraja().get(cual));
				baraja.getBaraja().remove(cual);
				
			}
		} else if (player == casa) {
			int cual;
			for (int i = 0; i < 7; i++) {
				cual = aleatorio.nextInt(baraja.getBaraja().size());
				baraja.getBaraja().get(cual).setBounds(inicial, 21, 42, 85);
				inicial += 46;
				casa.getJuego().add(baraja.getBaraja().get(cual));
				baraja.getBaraja().remove(cual);
				
				}}
		
	}
	public void taparFicha(Ficha cual) {
				cual.setIcon(new ImageIcon("src/fichas/alr.png"));
				cual.setDestapada(false);
	}
	
	public void taparFichas(Jugador quien) {
		for (int i=0;i<quien.getJuego().size();i++) {
			taparFicha(quien.getJuego().get(i));
		}
	}
	public void verFicha(Ficha cual) {
		cual.setIcon(new ImageIcon("src/fichas/"+cual.getLado1()+cual.getLado2()+".png"));
		cual.setDestapada(true);
		
}
	public void verFichas(Jugador quien) {
		for (int i=0;i<quien.getJuego().size();i++) {
			verFicha(quien.getJuego().get(i));
		}
		
}

	public void detenerSegudos(int segundos) {
		try {
			Thread.sleep(segundos*200);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
					Ficha fichaCasa=baraja.getBaraja().get(aleatorio.nextInt(baraja.getBaraja().size()));
					verFicha(fichaJugador);
					
				
					do {
						fichaCasa=baraja.getBaraja().get(aleatorio.nextInt(baraja.getBaraja().size()));
					}while(fichaCasa==fichaJugador||fichaCasa.isDestapada()||fichaCasa.getValor()==fichaJugador.getValor());
					
					verFicha(fichaCasa);
					if (fichaCasa.getValor()>fichaJugador.getValor()) 
					JOptionPane.showMessageDialog(null, "INICIA LA CASA");
					else JOptionPane.showMessageDialog(null, "INICIAS TU");
					escogiendoFichaInicial=false;
					iniciarPartida();
					taparFicha(fichaJugador);
					taparFicha(fichaCasa);
					actualizar();
					
					
				}
				else comer(jugador,fichaJugador);
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
