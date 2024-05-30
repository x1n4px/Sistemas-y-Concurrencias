package Septiembre_2023;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TableroJuegoMonitores {
    private int cont, numDescansando;
    private boolean maestroMueve;
    private ReentrantLock lock = new ReentrantLock();
    private Condition esperaJugador = lock.newCondition();
    private Condition esperaMaestro = lock.newCondition();
    private Condition esperaMoverMaestro = lock.newCondition();

    public TableroJuegoMonitores(int numJugadores) {
        super(numJugadores);
        cont = 0;
        numDescansando = 0;
        maestroMueve = false;
    }

@Override
 public void mueveJugador(int id) throws InterruptedException {
 lock.lock();

 try {
 while (id != cont || maestroMueve || numDescansando > 0) {
 if (numDescansando > 0) {
 System.out.println("Jugador " + id + " no puede jugar. Hay otrosdescansando");
 }
 esperaJugador.await();
 }
 cont++;
 System.out.println("Jugador " + id + " hace su movimiento");
 if (cont == numJugadores) {
 maestroMueve = true;
 esperaMoverMaestro.signal();
 } else {
 esperaJugador.signalAll();
 }

 } finally {
 lock.unlock();
 }
 


}
