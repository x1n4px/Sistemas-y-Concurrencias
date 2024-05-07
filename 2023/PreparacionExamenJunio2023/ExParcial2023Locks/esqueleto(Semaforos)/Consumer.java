package esqueleto;

import java.util.Random;

public class Consumer extends Thread {

	private Table t;
	private Random rnd = new Random();

	public Consumer(Table t) {
		this.t = t;
	}
	
	@Override
	public void run() {
		while (true) try {
			int data = t.get();
			Thread.sleep(rnd.nextInt(100)+25);
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
	
}
