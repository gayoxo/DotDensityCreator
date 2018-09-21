package fdi.dot.creator;

import java.util.Random;

public class ClaseInicial {
	
	public enum Densidad {d25,d50,d75};
	public enum Estrutura {horizontal,cuadrada,vertical};
	
	
	 public static void main(String[] args) {
		Densidad densidad=Densidad.d50;
		Estrutura estrutura=Estrutura.cuadrada;
		Random c=new Random(System.nanoTime());
		int TamanVal = 10;
		byte[][] array;
		switch (estrutura) {
		case cuadrada:
			 array=new byte[TamanVal][TamanVal];
			break;
		case horizontal:
			 array=new byte[TamanVal][TamanVal*2];
			break;
		case vertical:
			 array=new byte[TamanVal][TamanVal/2];
			break;
		default:
			array=new byte[TamanVal][TamanVal];
			break;
		}
		
		for (int i = 0; i < array.length; i++) {
			byte[] dento = array[i];
			for (int j = 0; j < dento.length; j++) {
				array[i][j]=(byte)c.nextInt(1);
			}
		}
		
		System.out.println("Acabe");
		
	}
}
