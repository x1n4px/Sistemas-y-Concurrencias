package mon;

public class AseoMon {

	private int numClientes;
	private boolean limpiando; //true si equipo de limpieza esta dentro
	//justa
	private boolean tocaLimpiar;//true si equipo quiere limpiar
	
	
	public AseoMon() {
		numClientes = 0;
	//	limpiando = false;
		tocaLimpiar = false;
	}
	
	public synchronized void entraCliente(int id)throws InterruptedException{
		//Cond Sincronizacion: No pueden entrar si estan limpiando (+justicia: toca limpiar)
		while(tocaLimpiar) {
			wait();
		}
		numClientes++;
		System.out.println("Cliente: "+id+" entra.Total: "+numClientes);
		
	}
	
	
	public synchronized void saleCliente(int id)throws InterruptedException{
		numClientes--;
		
		if(numClientes == 0) {
			notifyAll(); //desbloquear el equipo limpieza + clientes
		}
		System.out.println("\t\t Cliente: "+id+" Sale.Total: "+numClientes);
	}
	
	
	public synchronized void entraLimpieza()throws InterruptedException{
		tocaLimpiar = true;
		System.out.println("TOCA LIMPIAR");
		//CS
		while(numClientes>0) {
			wait();
		}
		
		limpiando = true;
		System.out.println("Equipo de limpieza dentro");
	}
	
	
	public synchronized void saleLimpieza(){
		System.out.println("Equipo limpieza fuera");
		//limpiando = false;
		tocaLimpiar = false;
		notifyAll();
	}
	
	
	
	public static void main(String[] args) {
		AseoMon aseo = new AseoMon();
		Limpieza limpieza = new Limpieza(aseo);
		Cliente clientes[] = new Cliente [10];
		for(int i=0;i<10;i++) {
			clientes[i] = new Cliente(aseo,i);
		}
		limpieza.start();
		for(int i = 0; i< 10; i++) {
			clientes[i].start();
		}
	}
	
	
	
}
