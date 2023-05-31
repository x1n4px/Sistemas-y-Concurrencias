package esqueleto;


import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Table {
	/*
	Esta clase modela los recursos compartidos, es decir, el Buffer. Hay una celda por
	Productor y la celda en la posición id solo puede ser tocada por el Consumidor y la id del
	Productor. En esta clase deebes programar estas dos funciones:
	 */

	private Lock lock;
	private int[] buffer;
	private Condition[] producerCondition;
	private Condition consumerCondition;
	private int nextProducer;
	private int nextConsumer;
	private static int numProd;
	public Table(int numProducers) {
		buffer = new int[numProducers];
		lock = new ReentrantLock();
		producerCondition = new Condition[numProducers];
		for (int i=0; i<numProducers;i++){
			producerCondition[i] = lock.newCondition();
		}
		consumerCondition = lock.newCondition();
		nextConsumer = 0;
		nextProducer = 0;
		numProd = numProducers;
 	}

	/*
	el productor con identificación id usa este método
	para producir un número entero en Table, en la posición id. El Productor debe esperar
	si la celda no está vacía, es decir, si el Consumidor aún no ha consumido el entero
	anterior
	 */
	public void put(int id, int datum) {
		lock.lock();
		try{
			while(id != nextProducer) {
 				try{
					producerCondition[id].await();
				}catch (InterruptedException e){
					e.printStackTrace();
				}
			}
			buffer[id] = datum;
			System.out.println("Productor " + id + " pone " + datum);
			nextProducer = (nextProducer + 1) % buffer.length;
			consumerCondition.signal();
		}finally {
			lock.unlock();
		}
	}


	/*
	el consumidor usa este método para consumir un número entero
	disponible de Table. Si no hay datos disponibles, entonces tiene que esperar. Los datos
	deben consumirse en el mismo orden en que se generan. Cuando el Consumidor
	consume el número entero en la celda id, el productor de esa celda puede colocar otro
	número entero en esa posición. Por supuesto, el consumidor debe consumir el mismo
	entero solo una vez.
	 */
	public int get() {
		lock.lock();
		try {
		/*	while (buffer[nextConsumer] == 0) {
				try {
					consumerCondition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}*/
			int productor = calcularProductor(nextConsumer);
			int datum = buffer[nextConsumer];
			System.out.println("		Consumidor consume " + datum + " de " + productor);
			buffer[nextConsumer] = 0;
			nextConsumer = (nextConsumer + 1) % buffer.length;
			producerCondition[nextConsumer].signal();
			return datum;
		} finally {
			lock.unlock();
		}
	}

	private int calcularProductor(int dato){
		int result = dato - 1;
		if(result < 0){
			result = numProd;
		}
		return result;
	}
}
