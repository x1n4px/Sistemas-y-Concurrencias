package paquete;

import java.util.*;
public class Conductor extends Thread{

	private Gestor gestor;
	private int id;
	public Conductor(Gestor g, int id) {
		this.gestor = g;
		this.id = id;
	}
	private Random r = new Random();
	public void run() {
		try {
			gestor.puedoTransportar(id,r.nextInt(50)+ 50);
			while (!gestor.estaCerrado()) {
					gestor.cargarPaquetes(id);
					gestor.repartir(id);
					Thread.sleep(r.nextInt(1000));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
