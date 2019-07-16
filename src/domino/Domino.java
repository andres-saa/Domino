package domino;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Domino extends JFrame {
	
	ImageIcon fondo = new ImageIcon("src/imagenes/fondo.jpg");
	private BufferedImage bufferFondo = null;
	private JLabel centralLabel;
	
	public Domino() {
		try {
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
		
		//establecer el layout
		//Container contanier = getContentPane();
		//contanier.setLayout(new FlowLayout());
		
		/*Componentes gráficos
		centralLabel = new JLabel(new ImageIcon(bufferFondo));
		centralLabel.setLayout(new GridBagLayout());
		add(centralLabel);*/
		
		JLabel imagenDeFondo=new JLabel(fondo);
		JPanel panel = new JPanel(); 
		this.getContentPane().add(panel);
		this.getContentPane().add(imagenDeFondo);
		
		panel.setBackground(Color.BLACK);
	}
	
}
