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

	//arma una baraja para jugar haciendo uso de un for doble
	public void armarBaraja() {
		//para que las fichas no se repitan ya que ficha(a,b)=ficha(b,a)
		// vamos a crear las fichas con un buble de creacion;
		for (int lado1 = 0; lado1 < 7; lado1++) {
			for (int lado2 = lado1; lado2 < 7; lado2++) {
			baraja.add(new Ficha(lado1+""+lado2, lado1, lado2, new ImageIcon("src/fichas/"+lado1+lado2+".png")));
			}
		}
	}
	
	//reparte 7 fichjas a un jugador
	public void repartir(Jugador jugador) {

		for (int cuantas = 0; cuantas < 7; cuantas++) {
			comer(jugador);
		}
	}

	public void comer(Jugador jugador) {

		int cualFicha = aleatorio.nextInt(baraja.size());
		jugador.getJuego().add(baraja.get(cualFicha));
		baraja.remove(cualFicha);

	}

	public ArrayList<Ficha> getBaraja() {
		return baraja;
	};
	
}
