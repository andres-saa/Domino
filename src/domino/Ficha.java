package domino;

public class Ficha {
	private int lado1,lado2;
	
	public Ficha(int lado1,int lado2) {
		this.lado1=lado1;
		this.lado2=lado2;
	}

	public int getLado1() {
		return lado1;
	}

	public int getLado2() {
		return lado2;
	}
	
	public int  getValor() {
		int valor = lado1+lado2;
		return valor;
	}
	
}
