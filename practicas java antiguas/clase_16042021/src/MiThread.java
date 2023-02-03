
public class MiThread extends Thread {
	private char miletra;
	private boolean sleep;
	
	public MiThread(char letra,boolean sleep) {
		// recibe los valores para inicializar las variables internas
		miletra = letra;
		this.sleep = sleep;
	}
	
	public void run() {
		//implementar el comportamiento
		for(int i = 0 ; i< 100; i++) {
			System.out.println("MiHebra "+ miletra);
			
			if(sleep) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println("Sleep interrumpido");
					break;
				}
			}else {
				Thread.yield();	 // estoy interesada en salir del procesador pero seguir en estado ejecutable
				if(Thread.interrupted()) {
					System.out.println("yield interrumpido");
					break;
				}
			}
		}
		System.out.println("Fin hebra "+Thread.currentThread().getName());
	}

}
