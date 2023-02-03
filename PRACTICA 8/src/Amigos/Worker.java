package Amigos;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

public class Worker extends SwingWorker<List<Amigos>, Void> {
	//TODO: completar las clases que devuelven el metodo doInBackground / publish
	
	private int longitud; // numero de amigos que quereemos generar
	private Panel panel;
	public Worker(int longitud, Panel panel)
	{
		this.longitud=longitud;
		this.panel=panel;
	}
	/*
	 * Metodos para calcular los numeros amigos 
	 */
	private boolean div1Igual2(long a, long b)
	{
		long suma = 1;
		int i =2;
		while(i<a && suma<=b)
		{
			if(a%i == 0) {suma+=i;}
			i++;
		}		
		return suma == b;
	}
	
	private long sumaDivisores(long a)
	{
		long suma =1;
		int i =2;
		while(i<a)
		{
			if(a%i == 0) {suma+=i;}
			i++;
		}
		return suma;
	}
	
	/*
	 * Metodos de SwingWorker
	 */
	 
	@Override
	protected List<Amigos> doInBackground() {
		List <Amigos> res= new ArrayList<Amigos>();
		
		int numAmigos=0;
		long amigo = 2;
		while(numAmigos<longitud && !this.isCancelled()){
			long sumDiv= sumaDivisores(amigo);
			if(sumDiv>=amigo && div1Igual2(sumDiv, amigo)){
				res.add(new Amigos(amigo, sumDiv,numAmigos));
				this.setProgress(100/(longitud-numAmigos+1));
				numAmigos++;
			}
			amigo++;
		}
		return res;
	}
	@Override
	public void done()
	{
		try {
			panel.limpiar();
			panel.escribirLista(get());
			panel.mensajeFin();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			panel.mensajeCancelar();
		} catch (Exception e) {
			panel.limpiar();
			// TODO Auto-generated catch block
			panel.mensajeCancelar();
		}
	}
}
