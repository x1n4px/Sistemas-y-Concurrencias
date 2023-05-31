package paquete;

import java.util.Random;

public class Principal {
	//Numero conductores en la empresa
	private static int NC = 2;
	//Numero de clientes que van a ir a entregar su paquete.
	private static int NP = 4;

	public static void main(String[] args) {
		Random r = new Random();

		//Indicamos el numero de conductores y el umbral sobre la capacidad a partir de la que pueden salir a repartir.
		Gestor gestor = new Gestor(NC, 0.7f); 
		Conductor[] con = new Conductor[NC];
		for (int i=0; i<con.length; i++){
			con[i] = new Conductor(gestor,i);
			con[i].setName("Conductor " + i);
		}

		Cliente[] paq = new Cliente[NP];
		for (int i=0; i<paq.length; i++){
			paq[i] = new Cliente(gestor,i);
			paq[i].setName("Paquete " + i);
		}
		for (int i=0; i<paq.length; i++)
			paq[i].start();
		for (int i=0; i<con.length; i++)
			con[i].start();

		//Simulamos que está abierto de 5 a 15 segundos y se cierra.
		try {
			Thread.sleep(r.nextInt(10000)+5000);
			gestor.fin();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
