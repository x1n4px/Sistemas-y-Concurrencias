package BarberoDormilon;

public class Principal {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Barberia b = new Barberia();
		Barbero barbero = new Barbero(b);
		Entorno entorno = new Entorno(b);
		barbero.start();
		entorno.start();
		
		
		
	}

}
