package Ejercicio4;

public class Principal {

	public static void main(String[] args) {
		int numCan = 10;
		IOlla olla = new Olla1();
		Cocinero cocinero = new Cocinero(olla);
		Canibal[] canibal = new Canibal[numCan];
		for (int i = 0; i < canibal.length; i++) {
			canibal[i] = new Canibal(olla, i);
		}
		cocinero.start();
		for (int i = 0; i < canibal.length; i++) {
			canibal[i].start();
		}
		
	}
	
	
	
}
