package domino;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
public class Baraja {

	ArrayList<Ficha> baraja = new ArrayList();
	Random aleatorio =new Random();

	/**
	 * arma una baraja para jugar haciendo uso de un for doble
	 */
	public void armarBaraja() {
		//para que las fichas no se repitan ya que ficha(a,b)=ficha(b,a)
		// vamos a crear las fichas con un bucle
		for (int lado1 = 0; lado1 < 7; lado1++) {
			for (int lado2 = lado1; lado2 < 7; lado2++) {
			baraja.add(new Ficha(lado1,lado2));
			}
		}
	}
	
	//reparte 7 fichjas a un jugador


	public ArrayList<Ficha> getBaraja() {
		return baraja;
	};
	
}
