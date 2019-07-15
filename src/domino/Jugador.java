package domino;

import java.util.ArrayList;

public class Jugador {

	public String nombre;
	//ArrayList con el juego del jugador
	private ArrayList<Ficha> juego=new ArrayList();
	
	public Jugador(ArrayList<Ficha> juego) {
		this.juego=juego;
	}

	public String getNombre() {
		return nombre;
	}

	public ArrayList<Ficha> getJuego() {
		return juego;   
	}
}
