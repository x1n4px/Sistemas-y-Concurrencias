package clase_14042021;


public class principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hola desde la hebra main");
	/*
		MiThread th1 = new MiThread('A');
		
		th1.start(); //Ejecuta de forma concurrente el código de run()
		//th1.run(); // ejecuta de forma secuencial en la hebra main
		*/
		MiRunnable r1 = new MiRunnable(9);
		Thread thr1 = new Thread(r1);
		
		thr1.start(); //ejecuta de forma concurrente el codigo run del objeto r1
		for(int i =0;i<10;i++) {
			System.out.println("Adios desde la hebra main "+i);
		}
		
		
		
		
		
	}

	
	
}
