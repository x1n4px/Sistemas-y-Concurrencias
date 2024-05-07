package molecula;

import java.util.concurrent.Semaphore;

public class GestorAgua {

	//oxigeno = productor
	//hidrogeno = productor
	//Acceso a los contadores = exclusion mutua
	
	private static final int ESPERA_H = 5;
	private static final int ESPERA_O = 10;
	
	private int nH;
	private int mol;
	private boolean O;
	
	
	private Semaphore mutex;//protoge nH y O
	private Semaphore entraO;//cerrado sii hay 2 H en la molecula
	private Semaphore entraH;
	private Semaphore moleculaLista;//cerrado sii falta algun atomo en la molecula
	
	public GestorAgua() {
		nH = 0;
		mol = 0;
		O = false;
		mutex = new Semaphore(1,true);
		entraH = new Semaphore(1,true);
		entraO = new Semaphore(1,true);
		moleculaLista = new Semaphore(0,true);//inicialmente faltan elementos de la molecula
	}
	
	
	public void hListo(int id)throws InterruptedException{
		//CS
		entraH.acquire();
		mutex.acquire();
		//sc
		nH++;
		mol++;
		System.out.println("Hidrogeno "+id+" en molecula. nH "+nH);
		if(!(nH==2&&O)) {
			if(nH<2) {
				entraH.release();
			}
			mutex.release();
			moleculaLista.acquire();
			mutex.acquire();
		}
		mol--;
		nH--;
		if(mol>0) {
			moleculaLista.release();
		}
		if(mol==0) {
			System.out.println("\t\t Molecula creada!!");
			O = false;
			nH = 0;
			entraH.release();//deja pasar nuevos H
			entraO.release();//deja pasar nuevos O
		}
		mutex.release();
		
	}
	
	
	public void oListo(int id)throws InterruptedException{
		//CS
		entraO.acquire();
		//SC
		mutex.acquire();
		O = true;
		mol++;
		System.out.println("Oxigeno "+id+" en molecula");
		//CS2:Hay que esperar al resto de atomos H y cuando esten todos se crea la molecula
		
		if(nH<2) {//falta algun atomo de H, el atomo O tiene que esperar
			mutex.release();
			moleculaLista.acquire();
			mutex.acquire();
		}
		mol--;
		O=false;
		if(mol>0) {
			moleculaLista.release();
		}
		if(mol==0) {
			System.out.println("\t\t Molecula creada!!");
			entraH.release();//deja pasar nuevos H
			entraO.release();//deja pasar nuevos O
		}
		mutex.release();
	}
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		GestorAgua g = new GestorAgua();
		Oxigeno[] oxygen = new Oxigeno[5];
		Hidrogeno[] hydro = new Hidrogeno[5];
		
		for (int i = 0; i < oxygen.length; i++) {
			oxygen[i] = new Oxigeno(g,i);
			hydro[i] = new Hidrogeno(g,i);
		}
		
		for (int i = 0; i < oxygen.length; i++) {
			oxygen[i].start();
			hydro[i].start();
		}
		
		
	}
	
}
