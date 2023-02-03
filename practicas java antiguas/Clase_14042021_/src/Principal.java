public class Principal {

	public static void main(String[] args) {
		System.out.println("Hola desde la hebra main");
		
		MiThread th1 = new MiThread('A');
		
		th1.start(); // ejecuta de forma concurrent el codigo de run()

		//th1.run(); // ejecuta de forma secuencia en la hebra main
		  
		 
		
		MiRunnable r1 = new MiRunnable(9);
		Thread th2 = new Thread(r1);
		
		th2.start(); //ejecuta de forma concurrente el codigo run del objeto r1
		
		for(int i = 0; i < 10; i++) {
			System.out.println("Adios desde la hebra main" + i);
		}
		
		/* No es la mejor manera de dectectar que ha muerto
		 * porque consume ciclos del procesador*/
		/*
		while(th1.isAlive()) {
			System.out.println("MiHebra hija esta viva");
		}
		*/
		
		try {
			th1.join(); //Bloquea hasta que termina la ejecucion de th1
			th2.join(); //Bloquea hasta que termina la ejecucion de th2
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		System.out.println("th1 y th2 han terminado");
	}

}
