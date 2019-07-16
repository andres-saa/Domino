package domino;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Domino extends JFrame {
	
	public static final String rutaFondo = "src/imagenes/fondo.jpg";
	private BufferedImage bufferFondo = null;
	private JLabel centralLabel;
	
	public Domino() {
		try {
			bufferFondo = ImageIO.read(new File(rutaFondo));
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
		
		//Componentes gráficos
		centralLabel = new JLabel(new ImageIcon(bufferFondo));
		centralLabel.setLayout(new GridBagLayout());
		add(centralLabel);
	}
}
