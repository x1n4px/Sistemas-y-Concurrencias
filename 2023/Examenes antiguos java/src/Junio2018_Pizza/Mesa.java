package Junio2018_Pizza;

public class Mesa {
	
	private final int RACIONES = 5;
	private int numRac  = 0;
	private boolean pizzeroLlamado = false;
	private boolean pagado = false;
	private boolean nuevoPedido = false;
	
	/**
	 * 
	 * @param id
	 * El estudiante id quiere una ración de pizza. 
	 * Si hay una ración la coge y sigue estudiante.
	 * Si no hay y es el primero que se da cuenta de que la mesa está vacía
	 * llama al pizzero y
	 * espera hasta que traiga una nueva pizza. Cuando el pizzero trae la pizza
	 * espera hasta que el estudiante que le ha llamado le pague.
	 * Si no hay pizza y no es el primer que se da cuenta de que la mesa está vacía
	 * espera hasta que haya un trozo para él.
	 * @throws InterruptedException 
	 * 
	 */
	
	
	
	
	
	
	public synchronized void nuevaRacion(int id) throws InterruptedException{
	while(pizzeroLlamado) {
		wait();
	}
	if(numRac == 0) {
		nuevoPedido = true;
		pizzeroLlamado = true;
		notifyAll();
		System.out.println("Estudiante "+id+" encuentra la mesa vacia "+numRac);
		while(numRac == 0) {
			wait();
		}
		pagado = true;
		System.out.println("Estudiando "+id+" paga al pizzero "+numRac);
		notifyAll();
	}
	numRac--;
	System.out.println("Estudiante "+id+" coge una racion "+numRac);
	}


	/**
	 * El pizzero entrega la pizza y espera hasta que le paguen para irse
	 * @throws InterruptedException 
	 */
	public synchronized void nuevoCliente() throws InterruptedException{
		while(!nuevoPedido) {
			wait();
		}
		nuevoPedido = false;
		System.out.println("Pizzero llamado... cocina pizza");
		
		
		
	}
	

/**
	 * El pizzero espera hasta que algún cliente le llama para hacer una pizza y
	 * llevársela a domicilio
	 * @throws InterruptedException 
	 */
	public synchronized void nuevaPizza() throws InterruptedException{
		numRac = RACIONES;
		notifyAll();
		System.out.println("Pizzero entrega pizza!!!");
		while(!pagado) {
			wait();
		}
		pagado = false;
		System.out.println("Pizzero cobra!!!");
		pizzeroLlamado = false;
		notifyAll();
		}

}
