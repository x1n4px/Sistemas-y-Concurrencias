
public class Principal {

	public static void main(String[] args) {
		
		MiThread thSleep = new MiThread('A'	, true);
		MiThread thYield = new MiThread('B', false);
		//MiDemonio d1 = new MiDemonio(1);
		//MiDemonio d2 = new MiDemonio(2);
		
		//d1.setDaemon(true); // se comporta como demonio
		// d2 se comporta como una hebra infinita
		
		//d1.start();
		//d2.start();
		
		MiHebraParametro h = new MiHebraParametro(thSleep);
		h.start();
		
		thSleep.start();
		thYield.start();
		
		try {
			Thread.sleep(50);
			
			System.out.println("Vamos a interrumpir");
			thSleep.interrupt();
			thYield.interrupt();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Fin del principal");
		
	}

}
