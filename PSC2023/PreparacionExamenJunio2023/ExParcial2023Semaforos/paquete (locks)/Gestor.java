package paquete;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Gestor {
	private int[] pesoSoportado;
	private int[] cargaActual;
	private int[] numeroPaquetes;
	private Lock lock;
	private boolean cierreInstalaciones;
	private int numConductores;
	private float capacidad;
	private Condition[] esperoEntregado;
	private Condition[] puedoRepartir;

	public Gestor(int numConductores, float capacidad) {
		this.numConductores = numConductores;
		this.capacidad = capacidad;
		pesoSoportado = new int[numConductores];
		cargaActual = new int[numConductores];
		numeroPaquetes = new int[numConductores];
		cierreInstalaciones = false;
		lock = new ReentrantLock();
		esperoEntregado = new Condition[numConductores];
		puedoRepartir = new Condition[numConductores];
		for (int i = 0; i < numConductores; i++) {
			esperoEntregado[i] = lock.newCondition();
			puedoRepartir[i] = lock.newCondition();
		}
	}

	public EstadoPaquete entregaPaquete(int id, int peso) throws InterruptedException {
		int i = 0;
		lock.lock();
		try {
			if (!cierreInstalaciones) {
				while (i < numConductores && (peso + cargaActual[i]) > pesoSoportado[i]) {
					i++;
				}

				if (i < numConductores) {
					cargaActual[i] += peso;
					numeroPaquetes[i]++;
					int carga = pesoSoportado[i] - cargaActual[i];
					System.out.println("Paquete " + id + " con peso " + peso + " reparte conductor " + i + ", tiene carga disp." + carga);

					if (cargaActual[i] > pesoSoportado[i] * capacidad) {
						System.out.println("Conductor " + i + " está cargado");
						cargaActual[i] = pesoSoportado[i];
						puedoRepartir[i].signal();
					}

					esperoEntregado[i].await();
					System.out.println("    Paquete " + id + " ha sido entregado");
					numeroPaquetes[i]--;
					if (numeroPaquetes[i] > 0) {
						esperoEntregado[i].signal();
					} else {
						if (!cierreInstalaciones) {
							cargaActual[i] = 0;
						}
					}
					return EstadoPaquete.ENTREGADO;
				} else {
					System.out.println("    No hay hueco " + id + " con peso " + peso);
					return EstadoPaquete.REINTENTAR;
				}
			} else {
				System.out.println("Cerrado el negocio, no se puede entregar " + id + " con peso " + peso);
				return EstadoPaquete.SINSERVICIO;
			}
		} finally {
			lock.unlock();
		}
	}

	public void puedoTransportar(int id, int peso) throws InterruptedException {
		lock.lock();
		try {
			pesoSoportado[id] = peso;
			System.out.println("Nuevo conductor " + id + ", puede transportar " + peso + " kilos.");
		} finally {
			lock.unlock();
		}
	}

	public void cargarPaquetes(int id) throws InterruptedException {
		lock.lock();
		try {
			puedoRepartir[id].await();
			System.out.println("Conductor " + id + " sale de las instalaciones");
		} finally {
			lock.unlock();
		}
	}

	public void repartir(int id) {
		System.out.println("Conductor " + id + " reparte");
		lock.lock();
		try {
			esperoEntregado[id].signal();
		} finally {
			lock.unlock();
		}
	}

	public void fin() throws InterruptedException {
		lock.lock();
		try {
			System.out.println("Las instalaciones se cierran");
			cierreInstalaciones = true;
			for (int i = 0; i < numConductores; i++) {
				if (cargaActual[i] > 0 && (cargaActual[i] <= pesoSoportado[i] * capacidad)) {
					System.out.println("Conductor " + i + " sale en último reparto");
					puedoRepartir[i].signal();
				}
			}
		} finally {
			lock.unlock();
		}
	}

	public boolean estaCerrado() {
		lock.lock();
		try {
			return cierreInstalaciones;
		} finally {
			lock.unlock();
		}
	}
}
