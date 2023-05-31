package esqueleto;


import java.util.concurrent.Semaphore;

public class Table {
	private int[] buffer;
	private Semaphore[] producerSemaphore;
	private Semaphore consumerSemaphore;
	private int nextProducer;
	private int nextConsumer;
	private static int numProd;


	public Table(int numProducers) {
		buffer = new int[numProducers];
		producerSemaphore = new Semaphore[numProducers];
		for (int i = 0; i < numProducers; i++) {
			producerSemaphore[i] = new Semaphore(1);
		}
		consumerSemaphore = new Semaphore(1);
		nextConsumer = 0;
		nextProducer = 0;
		numProd = numProducers;
	}

	
	public void put(int id, int datum) {
		try{
			producerSemaphore[id].acquire();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		buffer[id] = datum;
		System.out.println("Productor " + id + " pone " + datum);
		nextProducer = (nextProducer + 1) % buffer.length;
		consumerSemaphore.release();
	}

	public synchronized int get() throws InterruptedException {

		consumerSemaphore.acquire();
		int datum = buffer[nextConsumer];
		System.out.println("		Consumidor consume " + datum + " de " + nextConsumer);
		buffer[nextConsumer] = 0;
		nextConsumer = (nextConsumer + 1) % buffer.length;
		producerSemaphore[nextConsumer].release();
		return datum;
	}

}
