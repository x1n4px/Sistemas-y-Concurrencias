package esqueleto;

import java.util.Random;

public class Producer extends Thread {
	
	private int id;
	private Table t;
	private Random rnd = new Random();

	public Producer(int i, Table t) {
		this.id = i;
		this.t = t;
	}
	
	@Override
	public void run() {
		while (true) try {
			Thread.sleep(rnd.nextInt(200)+50);
			int data = rnd.nextInt(90)+10;
			t.put(id, data);
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

}
