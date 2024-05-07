package lock;
import java.util.Random;

public class Cliente extends Thread {
	private AseoLock aseo;
	private int id;
	private Random r;
	
	public Cliente(AseoLock aseo, int id) {
		this.aseo = aseo;
		this.id = id;
		this.r = new Random();
	}
	
	public void run() {
		try {
			while(true) {
				Thread.sleep(r.nextInt(50)); //Da un paseo por el centro comercial
				aseo.entraCLiente(id);
				Thread.sleep(r.nextInt(50)); //Pasa un tiempo en el aseo
				aseo.entraCLiente(id);
			}
		} catch(InterruptedException e) {
			e.getStackTrace();
		}
	}
}
