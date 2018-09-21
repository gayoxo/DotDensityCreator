package fdi.dot.creator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Random;

public class ClaseInicial {
	
	
	
	
	public enum Densidad {d0(0),d5(5),d10(10),d25(25-13),d50(50-13),d75(75-13);
		
		public int valores;
		
		Densidad(int valores)
		{
		this.valores=valores;
		}
		
		public int getValores() {
			return valores;
		}
	};
	public enum Estrutura {horizontal,cuadrada,vertical};
	
	
	 public static void main(String[] args) {
		
		Densidad densidad=Densidad.d25;
		Estrutura estrutura=Estrutura.vertical;
		int TamanVal = 1000;
		boolean debugD =true;
		boolean debugP =false;
		
		if (args.length>2)
		{
			try {
				Integer den=Integer.parseInt(args[0]);
				switch (den) {
				case 0:
					densidad=Densidad.d0;
					break;
				case 1:
					densidad=Densidad.d5;
					break;
				case 2:
					densidad=Densidad.d10;
					break;
				case 3:
					densidad=Densidad.d25;
					break;
				case 4:
					densidad=Densidad.d50;
					break;
				case 5:
					densidad=Densidad.d75;
					break;
				default:
					densidad=Densidad.d0;
					break;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				Integer den=Integer.parseInt(args[1]);
				switch (den) {
				case 0:
					estrutura=Estrutura.horizontal;
					break;
				case 1:
					estrutura=Estrutura.cuadrada;
					break;
				case 2:
					estrutura=Estrutura.vertical;
					break;
				default:
					estrutura=Estrutura.cuadrada;
					break;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				Integer den=Integer.parseInt(args[2]);
				TamanVal=den;
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		 
		Random c=new Random(System.nanoTime());
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
		
		
		int Valor=c.nextInt(25);
		float BuscadoSup=50.0f;
		
		switch (densidad) {
		case d25:
		case d50:
		case d75:
			BuscadoSup=densidad.getValores()+Valor;
			break;
		case d0:
			BuscadoSup=c.nextInt(5)+1;
			break;
		case d5:
			BuscadoSup=c.nextInt(5)+5;
			break;
		case d10:
			BuscadoSup=c.nextInt(5)+10;
			break;
		default:
			BuscadoSup=densidad.getValores()+Valor;
			break;
		}
		
		
		
		LinkedList<Par> Unos=new LinkedList<Par>(); 
		LinkedList<Par> Ceros=new LinkedList<Par>();
		for (int i = 0; i < array.length; i++) {
			byte[] dento = array[i];
			
			if (debugD)
				System.out.println("D:"+i);
			for (int j = 0; j < dento.length; j++) {
				Byte ValorCU = (byte)c.nextInt(2);
				if (ValorCU==0)
					Ceros.add(new Par(i, j));
				else
					Unos.add(new Par(i, j));
					
				array[i][j]=ValorCU;
			}
			
			
			if (i!=0&&i%100==0)
			{
				float porcentaje=((float)Unos.size()/((float)(Unos.size()+Ceros.size())))*100;
				while (porcentaje<BuscadoSup-1||porcentaje>BuscadoSup+1)
				{
					if (debugP)
						System.out.println("P:"+porcentaje);
					if (porcentaje<BuscadoSup-1)
					{
						Par valorPar =Ceros.remove(c.nextInt(Ceros.size()));
						array[valorPar.i][valorPar.j]=1;
						Unos.add(valorPar);
					}
					else
					{
						Par valorPar =Unos.remove(c.nextInt(Unos.size()));
						array[valorPar.i][valorPar.j]=0;
						Ceros.add(valorPar);
					}
					porcentaje=((float)Unos.size()/((float)(Unos.size()+Ceros.size())))*100;
				}
				Unos=new LinkedList<Par>(); 
				Ceros=new LinkedList<Par>();
			}
		}
		
		
		float porcentaje=((float)Unos.size()/((float)(Unos.size()+Ceros.size())))*100;
		while (porcentaje<BuscadoSup-1||porcentaje>BuscadoSup+1)
		{
			if (debugP)
				System.out.println("P:"+porcentaje);
			if (porcentaje<BuscadoSup-1)
			{
				Par valorPar =Ceros.remove(c.nextInt(Ceros.size()));
				array[valorPar.i][valorPar.j]=1;
				Unos.add(valorPar);
			}
			else
			{
				Par valorPar =Unos.remove(c.nextInt(Unos.size()));
				array[valorPar.i][valorPar.j]=0;
				Ceros.add(valorPar);
			}
			porcentaje=((float)Unos.size()/((float)(Unos.size()+Ceros.size())))*100;
		}
		
		
		
		
		File f;
		f = new File(System.nanoTime()+"_"+estrutura.toString()+"_d"+Math.round(BuscadoSup)+"_"+TamanVal+".dot");

		try{
		FileWriter w = new FileWriter(f);
		BufferedWriter bw = new BufferedWriter(w);
		PrintWriter wr = new PrintWriter(bw);
		
		for (int k = 0; k < array.length; k++) {
			byte[] linea = array[k];
			
//			boolean valorAlguno=false;
//			for (int i = 0; i < linea.length; i++)
//				if (linea[i]==1)
//					{
//					valorAlguno=true;
//					break;
//					}
//			
//			if (valorAlguno)
//			{
			for (int i = 0; i < linea.length; i++) {
				if (linea[i]==1)
					wr.append(i+" ");
			}
			wr.append(System.getProperty("line.separator"));
//			}
		}
		
		
//		wr.write("Esta es una linea de codigo");//escribimos en el archivo 
//		wr.append(" - y aqui continua"); //concatenamos en el archivo sin borrar lo existente
//		              //ahora cerramos los flujos de canales de datos, al cerrarlos el archivo quedará guardado con información escrita
//		              //de no hacerlo no se escribirá nada en el archivo
//		
//		
		
		wr.close();
		bw.close();
		}catch(IOException e){};
		       
		
		System.out.println("Acabe");
		
	}
}
