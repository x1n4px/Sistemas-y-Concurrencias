package barca;

import java.util.concurrent.Semaphore;

public class Barca {
    // Definición de constantes y variables de instancia
    static final int MAXCAPACIDAD = 3;
    int numPasajeros; // Número actual de pasajeros en la barca
    int orilla; // Indica la orilla en la que se encuentra la barca (0 o 1)

    // Semáforos para garantizar la exclusión mutua y la sincronización entre hilos
    Semaphore mutex; // Semáforo para controlar el acceso a la variable numPasajeros
    Semaphore puedeSubirOrilla0; // Semáforo para permitir que los pasajeros suban en la orilla 0
    Semaphore puedeSubirOrilla1; // Semáforo para permitir que los pasajeros suban en la orilla 1
    Semaphore puedeComenzarViaje; // Semáforo para indicar que se pueden comenzar el viaje
    Semaphore puedeBajar; // Semáforo para permitir que los pasajeros bajen de la barca

    // Constructor de la clase Barca
    public Barca() {
        numPasajeros = 0; // Inicializa el número de pasajeros en 0
        orilla = 0; // Inicializa la orilla en 0 (por defecto)
        // Inicializa los semáforos
        mutex = new Semaphore(1); // Mutex para controlar el acceso a numPasajeros
        puedeSubirOrilla0 = new Semaphore(1); // Inicialmente se puede subir en la orilla 0
        puedeSubirOrilla1 = new Semaphore(0); // Inicialmente no se puede subir en la orilla 1
        puedeComenzarViaje = new Semaphore(0); // Inicialmente no se puede comenzar el viaje
        puedeBajar = new Semaphore(0); // Inicialmente no se puede bajar de la barca
    }

    // Método para que un pasajero suba a la barca
    public void subir(int id, int pos) throws InterruptedException {
        // Verifica en qué orilla está el pasajero y adquiere el semáforo correspondiente
        if (pos == 0) {
            puedeSubirOrilla0.acquire(); // El pasajero espera si no puede subir en la orilla 0
        } else {
            puedeSubirOrilla1.acquire(); // El pasajero espera si no puede subir en la orilla 1
        }
        mutex.acquire(); // Adquiere el mutex para modificar numPasajeros
        numPasajeros++; // Incrementa el número de pasajeros en la barca
        // Imprime un mensaje indicando que el pasajero ha subido a la barca
        System.out.println("El pasajero " + id + " sube a la barca en la orilla " + pos + ". Hay " + numPasajeros
                + " pasajeros en la barca");
        // Si aún hay espacio en la barca, libera el semáforo correspondiente para permitir que otros pasajeros suban
        if (numPasajeros < MAXCAPACIDAD) {
            if (orilla == 0) {
                puedeSubirOrilla0.release(); // Libera el semáforo para subir en la orilla 0
            } else {
                puedeSubirOrilla1.release(); // Libera el semáforo para subir en la orilla 1
            }
        }
        // Si la barca está llena, libera el semáforo para indicar que se puede comenzar el viaje
        if (numPasajeros == MAXCAPACIDAD) {
            puedeComenzarViaje.release(); // Libera el semáforo para comenzar el viaje
        }
        mutex.release(); // Libera el mutex
    }

    // Método para que un pasajero baje de la barca
    public int bajar(int id) throws InterruptedException {
        puedeBajar.acquire(); // El pasajero espera hasta que se le permita bajar de la barca
        mutex.acquire(); // Adquiere el mutex para modificar numPasajeros
        numPasajeros--; // Decrementa el número de pasajeros en la barca
        // Imprime un mensaje indicando que el pasajero ha bajado de la barca
        System.out.println("\tEl pasajero " + id + " se baja de la barca en la orilla " + orilla + ". Hay "
                + numPasajeros + " pasajeros en la barca");
        // Si aún quedan pasajeros en la barca, libera el semáforo para permitir que otros pasajeros bajen
        if (numPasajeros > 0) {
            puedeBajar.release(); // Libera el semáforo para bajar de la barca
        }
        // Si todos los pasajeros han bajado, libera el semáforo correspondiente para permitir que otros suban
        if (numPasajeros == 0) {
            if (orilla == 0) {
                puedeSubirOrilla0.release(); // Libera el semáforo para subir en la orilla 0
            } else {
                puedeSubirOrilla1.release(); // Libera el semáforo para subir en la orilla 1
            }
        }
        mutex.release(); // Libera el mutex
        return orilla; // Retorna la orilla en la que ha bajado el pasajero
    }

    // Método para que el capitán espere a que se suban 3 pasajeros para comenzar el viaje
    public void esperoSuban() throws InterruptedException {
        puedeComenzarViaje.acquire(); // El capitán espera hasta que se permita comenzar el viaje
        // Imprime un mensaje indicando que el capitán comienza el viaje desde la orilla actual
        System.out.println("\t\tEl capitan comienza el viaje desde la orilla " + orilla);
    }

    // Método para que el capitán indique que el viaje ha terminado y los pasajeros deben bajar
    public void finViaje() throws InterruptedException {
        orilla = (orilla + 1) % 2; // Cambia la orilla actual
        // Imprime un mensaje indicando que el capitán ha llegado a la nueva orilla
        System.out.println("\t\t\tEl capitan llega a la orilla " + orilla);
        puedeBajar.release(); // Libera el semáforo para permitir que los pasajeros bajen de la barca
    }

}
