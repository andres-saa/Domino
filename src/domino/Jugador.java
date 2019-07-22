package domino;

import java.util.ArrayList;
import java.util.Random;

public class Jugador {

	public String nombre;
	//ArrayList con el juego del jugador
	private ArrayList<Ficha> juego=new ArrayList();
	Random aleatorio=new Random();
	
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
