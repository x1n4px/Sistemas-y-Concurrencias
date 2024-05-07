package Lectores_Escritores_justo;

public class Principal {

	public static void main(String[] args) {

		final int NE = 2;
		final int NL = 4;
		GestorBD gestor = new GestorBD();
		Escritor[] esc = new Escritor[NE];
		Lector[] lec = new Lector[NL];
		
		for (int i = 0; i < esc.length; i++) {
			esc[i] = new Escritor(i,gestor);
		}
		
		for (int i = 0; i < lec.length; i++) {
			lec[i] = new Lector(i,gestor);
		}
		
		for (int i = 0; i < esc.length; i++) {
			esc[i].start();
		}
		
		for (int i = 0; i < lec.length; i++) {
			lec[i].start();
		}
		
		
		
	}

}
