package pr7.recursos;

import java.util.*;
public class Control {
	private int NUM;//numero total de recursos
	private int numRec;//rec actuales
	private List<Integer> listaEspera = new LinkedList<Integer>();
	private int procEsperando=0;
	private int turno = -1;
	
	public Control(int num){
		this.NUM = num;
		this.numRec = num;
	}
	public synchronized void qRecursos(int id,int num) throws InterruptedException{
		procEsperando++;
		if(procEsperando>1) {
			listaEspera.add(id);
			System.out.println("El proceso "+id+" se mete en la cola de espera.");
			while(turno!=id)
				wait();
		}
		//si no hay recursos disponibles suficientes nos bloqueamos
		while(num>numRec) {
			System.out.println("El proceso "+id+" espera recursos, num: "+ num + ": numRec: "+numRec);
			wait();
		}
		numRec-=num;
		System.out.println("El proceso "+id+" ha cogido "+num +" recursos");
		//restaura estado inicial
		procEsperando--;
		if(procEsperando>0) {
			turno=listaEspera.remove(0);
			notifyAll();			
		}else {
			turno=-1;
		}
	}


	
	public synchronized void libRecursos(int id,int num){
		numRec+=num;
		System.out.println("El proceso "+id+" ha liberado "+num+" recursos.");
		System.out.println("Recursos actuales: "+numRec);
		notifyAll();
	}
}
//CS-1: un proceso tiene que esperar su turno para coger los recursos
//CS-2: cuando es su turno el proceso debe esperar hasta haya recursos suficiente
//para él 
