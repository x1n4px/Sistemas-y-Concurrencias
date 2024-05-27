

import java.util.concurrent.Semaphore;

public class Guarderia {

    private int nBebes = 0;
    private int nAdultos = 0;
    private Semaphore mutex = new Semaphore(1, true);
    private Semaphore entraBebes = new Semaphore(0, true);
    private Semaphore salirAdultos = new Semaphore(0, true);

    public void entraBebe(int id) throws InterruptedException {
        mutex.acquire();
        
        if(nBebes +1 <= 3* nAdultos) {
            entraBebes.release();
        }

        mutex.release();

        entraBebes.acquire();
        mutex.acquire();
        nBebes++;
        System.out.println("El bebe " + id + " entra a la guarderia. Hay " + nBebes + " bebes y " + nAdultos + " adultos.");
        mutex.release();
    }
    
    public void saleBebe(int id)throws InterruptedException {
        mutex.acquire();
        nBebes--;
        System.out.println("El bebe " + id + " sale de la guarderia. Hay " + nBebes + " bebes y " + nAdultos + " adultos.");
        mutex.release();
    }

    public void entraAdulto(int id)throws InterruptedException {
        mutex.acquire();
        nAdultos++;
        System.out.println("El adulto " + id + " entra a la guarderia. Hay " + nBebes + " bebes y " + nAdultos + " adultos.");
        mutex.release();
    }

    public void saleAdulto(int id)throws InterruptedException {
        mutex.acquire();
        if(nBebes <= 3*(nAdultos-1)) {
            salirAdultos.release();
        }
        mutex.release();
        salirAdultos.acquire();
        mutex.acquire();
        nAdultos--;
        System.out.println("El adulto " + id + " sale de la guarderia. Hay " + nBebes + " bebes y " + nAdultos + " adultos.");
       
        mutex.release();
    }
}
