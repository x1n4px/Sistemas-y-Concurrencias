package Junio_2023.maleta;

import barca.Pasajero;

public class Driver {
    public static void main(String[] args) {
        Cinta cinta = new Cinta();
        Generador g = new Generador(cinta);
        g.start();

        Pasajero pasajero[] = new Pasajero[10];
        for (int i = 0; i < 10; i++) {
            pasajero[i] = new Pasajero(i, cinta, i%2==0);
        
        }

        for (int i = 0; i < 10; i++) {
            pasajero[i].start();
        }
    }
}
