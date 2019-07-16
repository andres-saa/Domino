package domino;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Domino extends JFrame {
	
	
	Baraja fichas= new Baraja();
	ArrayList<Ficha> juego1=new ArrayList<Ficha>();
	ArrayList<Ficha> juego2=new ArrayList<Ficha>();
	Jugador jugador1 = new Jugador(juego1);
	Jugador jugador2 = new Jugador(juego2);
	ArrayList<JButton> mesa = new ArrayList<JButton>();
	JPanel panel = new JPanel(); 
	

	//private BufferedImage bufferFondo = null;
	//private JLabel centralLabel;
	
	public Domino() {
		try {
			
		//	fichas.repartir(jugador1);
		//	fichas.repartir(jugador2);
			
			initGUI();
			
			//Default window config.
			this.setUndecorated(false);
			pack();
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setVisible(true);
			this.setSize(1280,720);		}
		
		catch (Exception e) {
			//TODO: handle expection
			JOptionPane.showMessageDialog(null, "No se ha encontrado la imagen de fondo");
		}
	}
	
	private void initGUI() {
		JLabel imagenDeFondo=new JLabel(new ImageIcon("src/imagenes/fondo.jpg"));
		crearMesa();
		
		this.getContentPane().add(panel);
		panel.setLayout(null);
		imagenDeFondo.setBounds(0,0,1280,720);
		panel.add(imagenDeFondo);
		
		for (int i=0;i<8;i++) {
		imagenDeFondo.add(mesa.get(i));
		}
		
	}

	
	public void crearMesa() {


		// para que las fichas no se repitan ya que ficha(a,b)=ficha(b,a)
		int contador = 0;
		// vamos a crear los labels de las fichas y las agrega al array de la mesa.
		for (int lado1 = 0; lado1 < 7; lado1++) {
			for (int lado2 = contador; lado2 < 7; lado2++) {
				mesa.add(new JButton(new ImageIcon("src/fichas/"+lado1+""+lado2+".png")));
			}
		}
		establecerPosicionAlasFichas();
	}


	public void establecerPosicionAlasFichas() {
		int posicionInicialx=200;
		int posicionInicialy=200;
		
		for(int cual=0;cual<mesa.size();cual++) {
			mesa.get(cual).setBounds(posicionInicialx,posicionInicialy,55,85);
			mesa.get(cual).setBorderPainted(false);
			mesa.get(cual).setContentAreaFilled(false);
			mesa.get(cual).setFocusPainted(false);
			mesa.get(cual).setOpaque(false);
			posicionInicialx+=48;
		}
	}

}



