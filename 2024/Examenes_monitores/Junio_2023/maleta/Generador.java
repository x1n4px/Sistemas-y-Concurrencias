package Junio_2023.maleta;

import java.util.Random;

public class Generador extends Thread{
    Cinta c;
    Random r;

    public Generador(Cinta c){
        this.c = c;
        r = new Random();
    }

    public void run(){
        while(true){
            try {
                
                c.poner(r.nextBoolean());
                Thread.sleep(r.nextInt(500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
