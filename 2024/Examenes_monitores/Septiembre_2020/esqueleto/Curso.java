package esqueleto;

import java.util.concurrent.Semaphore;

public class Curso {
	private final int MAX_ALUMNOS_INI = 10;
	private final int ALUMNOS_AV = 3;
	private boolean esperaIniciacion = false, turno = false, grupoformado = false, grupoAcaba = false;
	private int cntIniciacion = 0, cntGrupo = 0, salen = 0;

	// El alumno tendra que esperar si ya hay 10 alumnos cursando la parte de
	// iniciacion
	public synchronized void esperaPlazaIniciacion(int id) throws InterruptedException {
		while (esperaIniciacion) {
			wait();
		}

		cntIniciacion++;
		if (cntIniciacion == MAX_ALUMNOS_INI) {
			esperaIniciacion = true;
		}
		System.out.println("Alumno " + id + " entra en la parte de iniciacion");
	}

	// El alumno informa que ya ha terminado de cursar la parte de iniciacion
	public synchronized void finIniciacion(int id) throws InterruptedException {
		cntIniciacion--;
		System.out.println("Alumno " + id + " ha terminado la parte de iniciacion");
		esperaIniciacion = false;
		notifyAll();
	}

	/*
	 * El alumno tendra que esperar:
	 * - si ya hay un grupo realizando la parte avanzada
	 * - si todavia no estan los tres miembros del grupo conectados
	 */
	public synchronized void esperaPlazaAvanzado(int id) throws InterruptedException {
		while (turno) {
			wait();
		}
		if (cntGrupo == 3) {
			grupoformado = true;
			turno = true;
			grupoAcaba = false;
			notifyAll();
		}
		while (!grupoformado) {
			System.out.println("Alumno " + id + " espera a que se forme el grupo");
			wait();
		}
		System.out.println("Alumno " + id + " entra en la parte avanzada");
	}

	/*
	 * El alumno:
	 * - informa que ya ha terminado de cursar la parte avanzada
	 * - espera hasta que los tres miembros del grupo hayan terminado su parte
	 */
	public synchronized void finAvanzado(int id) throws InterruptedException {
		cntGrupo--;
		if (cntGrupo == 0) {
			System.out.println("Alumno " + id + " ha terminado la parte avanzada");
			grupoAcaba = true;
			notifyAll();
		}
		while(!grupoAcaba) {
			System.out.println("Alumno " + id + " espera a que terminen los demas miembros del grupo");
			wait();
		}
		salen++;
		if(salen == 3) {
			grupoformado = false;
			turno = false;
			salen = 0;
			notifyAll();
		}
	}
}
