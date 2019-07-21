package domino;

import javax.swing.ImageIcon;

public class Ficha {
	
	private int lado1,lado2;
	private String idFicha;
	
	ImageIcon imagen = new ImageIcon();
	
	public Ficha(String idFicha, int lado1,int lado2,ImageIcon imagen) {
		this.lado1=lado1;
		this.lado2=lado2;
		this.idFicha=idFicha;
		setImagen(imagen);
	}

	public String getIdFicha() {
		return idFicha;
	}
	
	public void setIdFicha(String idFcha){
		this.idFicha=idFicha;
	}
	public int getLado1() {
		return lado1;
	}

	public int getLado2() {
		return lado2;
	}
	
	public int  getValor() {
		return lado1+lado2;
	}

	public ImageIcon getImagen() {
		return imagen;
	}

	public void setImagen(ImageIcon imagen) {
		this.imagen = imagen;
	}
}
