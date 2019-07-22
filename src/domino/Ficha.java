package domino;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Ficha extends JButton {

	/**
	 * lado 1 y dos de la ficha
	 */
	private int lado1, lado2;
	/**
	 * si destapada es true esto quiere decir que la ficha puede verse, es decir esta
	 * boca arriba en caso de ser false esta estara boca abajo
	 */
	private boolean destapada = false;
	/**
	 * nos guarda haca que lado ha sido rotada una ficha
	 */
	String rotadaHacia;

	public Ficha(int lado1, int lado2) {
		this.lado1 = lado1;
		this.lado2 = lado2;
		/**
		 * le establece una imagen a la ficha basandose en sus valores y el nombre de
		 * dichos archivos;1
		 */
		this.setIcon(new ImageIcon("src/fichas/" + lado1 + lado2 + ".png"));
	}

	/**
	 * 
	 * @return el lado al cual se ha rotado la ficha
	 */
	public String getRotadaHacia() {
		return rotadaHacia;
	}

	/**
	 * 
	 * @param rotadaHacia "izq" si la ficha fue rotada a la izquierda o "der" si por
	 *                    el contrario lo hizo a la derecha;
	 */
	public void setRotadaHacia(String rotadaHacia) {
		this.rotadaHacia = rotadaHacia;
	}

	/**
	 * 
	 * @param destapada le avisa a la ficha que fue destapada y ella lo guarda en
	 *                  dicha variable
	 */

	public void setDestapada(boolean destapada) {
		this.destapada = destapada;
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

	/**
	 * 
	 * @return true si esta destapada y false en caso contrario
	 */
	public boolean isDestapada() {
		return destapada;
	}

}
