package domino;

import java.awt.EventQueue;

import javax.swing.UIManager;

public class PrincipalDomino {
	public static void main(String[] args) {
		try {
			String className = UIManager.getCrossPlatformLookAndFeelClassName();
			UIManager.setLookAndFeel(className);
		} catch (Exception e) {
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Domino myWIndow = new Domino();
			}
		});
	}
}
