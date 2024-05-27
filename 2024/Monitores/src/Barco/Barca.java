package Barco;



public class Barca extends Thread{
	
	private int iPhoneBarca;
	private int AndroidBarca;
	private boolean viajando;
	final int capadidad = 4;
	
	
	
	public Barca(){
		iPhoneBarca = 0;
		AndroidBarca = 0;
		viajando = false;
	}
	
	
	/**
	 * Un estudiante con mÃ³vil android llama a este mÃ©todo 
	 * cuando quiere cruzar el rÃ­o. Debe esperarse a montarse en la
	 * barca de forma segura, y a llegar a la otra orilla del antes de salir del
	 * mÃ©todo
	 * @param id del estudiante android que llama al mÃ©todo
	 * @throws InterruptedException
	 */
	public synchronized void android(int id) throws InterruptedException{
		/*
		 * Si:
		 * hay 3 android ya en la barca, no me meto
		 * su hay 1 android en la barca y 2 iphones, tampoco( el ultimo asiento disponible solo lo puede tener una)
		 * Si la barca esta navegando
		 */
		while(AndroidBarca+iPhoneBarca == 4|| (AndroidBarca==2 && iPhoneBarca==1)||viajando) {
			System.out.println("El estudiante con Android de id "+id+" No puede meterse en la barca ahora, no hay sitio para el");
			wait();
		}
		//CS, en caso de que no haya 3 android en la barca o 1 android y 2 iphone
		iPhoneBarca++;
		System.out.println("El estudiante con Android de id "+id+" Se ha metido en la barca");
		
		if((iPhoneBarca+AndroidBarca)==capadidad) {//si ya esta el barco completo, el ultimo ha sido uno de iphone que es el encargado de partir
			System.out.println("Android "+id+" conduciendo barca...");
			System.out.println("Conduciendo...");
			viajando = true;
			notifyAll();
		}
		
		while(!viajando) {
			wait();
		}
		//En este punto se puede bajar
		AndroidBarca--;
		System.out.println("Android "+id+" se baja de la barca; numAndroid= "+AndroidBarca);
		if(AndroidBarca+iPhoneBarca == 0) {//Es el ultimo en bajarse
			viajando =false;
			notifyAll();
		}
	}
	
	/**
	 * Un estudiante con mÃ³vil android llama a este mÃ©todo 
	 * cuando quiere cruzar el rÃ­o. Debe esperarse a montarse en la
	 * barca de forma segura, y a llegar a la otra orilla del antes de salir del
	 * mÃ©todo
	 * @param id del estudiante android que llama al mÃ©todo
	 * @throws InterruptedException
	 */

	public synchronized void iphone(int id) throws InterruptedException{
		/*
		 * Si:
		 * si hay ya 3 de iphone en la barca, se tiene que meter 1 de iphone, asi que nos bloqueamos
		 * si hay 2 de android y 1 de iphone, se tiene que meter 1 de iphone, asi que nos bloqueamos
		 */
		while(AndroidBarca+iPhoneBarca == 4|| (AndroidBarca==1 && iPhoneBarca==2)||viajando) {
			System.out.println("El estudiante con Iphone de id "+id+" No puede meterse en la barca ahora, no hay sitio para el");
			wait();
		}
		//CS, en caso de que no haya 3 android en la barca o 1 android y 2 iphone
		iPhoneBarca++;
		System.out.println("El estudiante con Iphone de id "+id+" Se ha metido en la barca");
		
		if((iPhoneBarca+AndroidBarca)==capadidad) {//si ya esta el barco completo, el ultimo ha sido uno de iphone que es el encargado de partir
			System.out.println("Iphone "+id+" conduciendo barca...");
			System.out.println("Conduciendo...");
			viajando = true;
			notifyAll();
		}
		
		while(!viajando) {
			wait();
		}
		//En este punto se puede bajar
		AndroidBarca--;
		System.out.println("Iphone "+id+" se baja de la barca; numIphone= "+iPhoneBarca);
		if(AndroidBarca+iPhoneBarca == 0) {//Es el ultimo en bajarse
			viajando =false;
			notifyAll();
		}
		
		
	}
	
	
	
	

}