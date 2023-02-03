
public class MiThread extends Thread {
	private char miletra;
	
	public MiThread(char letra) {
		// recibe los valores para inicializar las variables internas
		miletra = letra;
	}
	
	public void run() {
		//implementar el comportamiento
		for(int i = 0 ; i< 10; i++) {
			System.out.println("MiHebra "+ miletra);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Thread.yield(); // estoy interesada en salir del procesador pero seguir en estado ejecutable
		}
	}

}
