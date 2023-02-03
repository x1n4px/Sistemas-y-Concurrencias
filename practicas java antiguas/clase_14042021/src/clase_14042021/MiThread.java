package clase_14042021;

public class MiThread extends Thread{
	private char miletra;
	
	public MiThread(char letra) {
	//recibe los valores para inicializar las variables internas
		miletra = letra;
	}
	public void run() {
		//implementar el comportamiento
		for(int i=0;i<10;i++) {
			System.out.println("MiHebra "+ miletra);
		}
		
		
	}
	
}
