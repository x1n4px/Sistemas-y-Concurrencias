package LectoresMonitores;
public class Principal {

	final static int NL = 4;
	final static int NE = 2; 
	public static void main(String[] args) {
		GestorBD bd = new GestorBD();
		Lector lectores[]= new Lector[NL];
		Escritor escritores[] = new Escritor[NE];
		for(int i =0; i< NL; i++)
			lectores[i]= new Lector(i, bd);
		
		for(int i =0; i< NE; i++)
			escritores[i]= new Escritor(i, bd);
		
		for(int i =0; i< NL; i++)
			lectores[i].start();
		
		for(int i =0; i< NE; i++)
			escritores[i].start();
	}

}
