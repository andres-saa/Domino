package domino;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
public class Baraja {

	ArrayList<Ficha> baraja = new ArrayList();
	Random aleatorio =new Random();
	public Baraja () {
		armarBaraja();		
	}

	/**
	 * arma una baraja para jugar haciendo uso de un for doble
	 * arma una baraja para jugar haciendo uso de un for doble y mete las fichas creadas al array de la baraja
	 */
	public void armarBaraja() {

		for (int lado1 = 0; lado1 < 7; lado1++) {
			for (int lado2 = lado1; lado2 < 7; lado2++) {
			baraja.add(new Ficha(lado1,lado2));
			}
		}
	}
	
	/**
	 * 
	 * @return reparte 7 fichjas a un jugador.
	 * @return la baraja creada 
	 */
	public ArrayList<Ficha> getBaraja() {
		return baraja;
	};
	
}
