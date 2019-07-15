package domino;

import java.util.ArrayList;

public class Jugador {

	public String nombre;
	//ArrayList con el juego del jugador
	private ArrayList<Ficha> juego=new ArrayList();
	
	public Jugador(ArrayList<Ficha> juego) {
		this.juego=juego;
	}

	
	public Ficha dameTufichaMasAlta(Jugador jugador) {
		Ficha fichaMasAlta = juego.get(0);

		for (int cual = 0; cual < juego.size(); cual++) {

			if (fichaMasAlta.getValor() < juego.get(cual).getValor())
				fichaMasAlta = juego.get(cual);
		}
		return fichaMasAlta;
	}
	
	
	
	public String getNombre() {
		return nombre;
	}

	public ArrayList<Ficha> getJuego() {
		return juego;   
	}
}
