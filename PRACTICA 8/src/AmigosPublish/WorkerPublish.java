package AmigosPublish;
import java.util.List;

import javax.swing.SwingWorker;

public class WorkerPublish extends SwingWorker<Void, Amigos> {
	//TODO: completar las clases que devuelven el metodo doInBackground / publish
	
	private int longitud; // numero de amigos que quereemos generar
	private Panel panel;
	public WorkerPublish(int longitud, Panel panel)
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
	protected Void doInBackground() {
		int numAmigos=0;
		long amigo = 2;
		while(numAmigos<longitud && !this.isCancelled()){
			long sumDiv= sumaDivisores(amigo);
			if(sumDiv>=amigo && div1Igual2(sumDiv, amigo)){
				publish(new Amigos(amigo, sumDiv,numAmigos));
				numAmigos++;
				this.setProgress(100/(this.longitud-numAmigos+1));
			}
			amigo++;
		}
		return null;
	}
	public void process (List<Amigos> amigos)
	{
		try {
			panel.escribirLista(amigos);
			panel.mensajeFin();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			panel.limpiar();
			panel.mensajeCancelar();
		}
	}

}
