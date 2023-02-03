package Lectores_Escritores_justo;

import java.util.concurrent.*;


public class GestorBD {

	private int dato;
	private int nLectores; //num de lectores que quiere entrar a la BD
	private Semaphore mutex1 = new Semaphore(1,true); //exclusion mutua sobre nLectores
	
	private Semaphore escribiendo = new Semaphore(1,true);
	//escribiendo es 1: la BD esta vacia
	//escribiendo es 0: hay alguien en la BD(1 escritor o 1 o mas lectores
	
	private int nEscritores; //num escritores que quieren acceder a la BD
	private Semaphore mutex2 = new Semaphore(1,true); //exclusion mutua a nEscritores
	
	private Semaphore leyendo = new Semaphore(1,true);
	//leyendo es 1: la BD esta vacia
	//leyendo es 0: hay escritores esperando para entrar(nEscritores > 0)
	
	private Semaphore mutex3 = new Semaphore(1,true); //Exclusion mutua de entraLector
	
	public void escribir(int dato) throws InterruptedException{
		//preprotocolo - entraEscritor
		mutex2.acquire();
		nEscritores++;
		if(nEscritores==1) {
			leyendo.acquire(); //el escritor no debe bloquearse
		}
		mutex2.release();
		escribiendo.acquire();
		
		//SC
		this.dato = dato;
		System.out.println("Escritor en BD, num lectores dentro "+nLectores+"\n num escritores que quieren entrar "+nEscritores);
		
		//PostProtocolo - saleEscritor
		mutex2.acquire();
		nEscritores--;
		//si no hay mas escritores esperando
		//se deja pasar a los lectores
		if(nEscritores == 0) {
			leyendo.release();
		}
		mutex2.release();
		escribiendo.release();
		
	}
	
	
	
	
	public int leer()throws InterruptedException{
		int datoLocal;
		//preprotocolo - entraLector
		mutex3.acquire();
		leyendo.acquire();
		mutex1.acquire();
		nLectores++;
		//si es el primer lector le cierra el paso a los escritores o espera a que un escritor le deje pasar
		
		if(nLectores == 1) {
			escribiendo.acquire();
		}
		mutex1.release();
		leyendo.release(); //Para dejar pasar a un escritor o a otro lector
		mutex3.release();
		
		//SC
		System.out.println("Lector en BD, numero de lectores dentro "+nLectores+"\n num escritores que quieren entrar "+nEscritores);
		datoLocal = dato;
		
		//Postprotocolo
		mutex1.acquire();
		nLectores--;
		if(nLectores==0) {
			escribiendo.release();
		}
		mutex1.release();
		
		return datoLocal;
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
