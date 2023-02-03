
public class MiRunnable implements Runnable {

	private int minumero;
	public MiRunnable(int numero) {
		minumero = numero;
	}
	
	@Override
	public void run() {
		for(int i = 0; i < 10; i++) {
			System.out.println("MiRunnable "+minumero);
			
		}

	}

}
