package barca;

import java.util.concurrent.Semaphore;

public class Barca {
    int max = 3, pasajeros = 0, orilla = 0;
    boolean puedeSubir = true, empiezaViaje = false, finViaje = false, puedenBajar = false;

    // Método para que un pasajero suba a la barca
    public void subir(int id, int pos) throws InterruptedException {
        while (!puedeSubir || pos != orilla) {
            wait();
        }
        pasajeros++;
        System.out.println("Pasajero " + id + " sube a la barca");
        if (pasajeros == 3) {
            puedeSubir = false;
            empiezaViaje = true;
            notifyAll();
        }
    }

    // Método para que un pasajero baje de la barca
    public int bajar(int id) throws InterruptedException {
        while(!puedenBajar){
            wait();
        }
        pasajeros--;
        if(pasajeros == 0){
            puedeSubir = true;
            notifyAll();
            puedenBajar = false;
            orilla = (orilla+1)%2;
        }
        return orilla;
    }

    // Método para que el capitán espere a que se suban 3 pasajeros para comenzar el
    // viaje
    public void esperoSuban() throws InterruptedException {
        while (!empiezaViaje) {
            wait();
        }
        System.out.println("Capitán comienza el viaje");
        finViaje = true;
        empiezaViaje = false;
        notifyAll();
    }

    // Método para que el capitán indique que el viaje ha terminado y los pasajeros
    // deben bajar
    public void finViaje() throws InterruptedException {
        while(!finViaje){
            wait();
        }
        System.out.println("Capitán indica que el viaje ha terminado");
        puedenBajar = true;
        finViaje = false;
        notifyAll();
    }

}
