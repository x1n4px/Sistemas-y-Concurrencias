package Piedra_Papel_Tijera;

public class Principal {

	public static void main(String[] args) {
		final int NJ = 3;
		
		Mesa mesa = new Mesa();
		
		Jugador[] jug = new Jugador[NJ];
		for (int i = 0; i < jug.length; i++) {
			jug[i] = new Jugador(i, mesa);
		}
		for (int i = 0; i < jug.length; i++) {
			jug[i].start();
		}
		for (int i = 0; i < jug.length; i++) {
			try {
				jug[i].join();
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Fin del programa");
		
	}
	
	
	
	
}
