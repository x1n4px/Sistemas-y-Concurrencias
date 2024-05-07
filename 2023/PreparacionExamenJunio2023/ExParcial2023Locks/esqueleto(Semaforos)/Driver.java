package esqueleto;

public class Driver {
	private static final int NUM_PRODUCERS = 5;
	public static void main(String[] args) {
		Table t = new Table(NUM_PRODUCERS);
		Consumer c = new Consumer(t);
		Producer [] p = new Producer[NUM_PRODUCERS];
		c.setName("Consumer");
		c.start();
		for (int i=0; i<NUM_PRODUCERS; i++){
			p[i]=new Producer(i, t);
			p[i].setName("Producer " + i);
			p[i].start();
		}
	}

}
