package domino;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Ficha extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int lado1, lado2;
	private String idFicha;
	private boolean destapada=false;

	public Ficha(int lado1, int lado2) {
		this.lado1 = lado1;
		this.lado2 = lado2;
		this.idFicha = (""+lado1+lado2);
		//this.setIcon(new ImageIcon("src/fichas/" + lado1 + lado2 + ".png"));
	}

	public void setDestapada(boolean destapada) {
		this.destapada = destapada;
	}

	public String getIdFicha() {
		return idFicha;
	}

	public int getLado1() {
		return lado1;
	}

	public int getLado2() {
		return lado2;
	}

	public int getValor() {
		return lado1 + lado2;
	}

	public boolean isDestapada() {
		return destapada;
	}
	
	

}
