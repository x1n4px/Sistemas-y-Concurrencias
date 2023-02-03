
public class MiHebraParametro extends Thread {

	private Thread remota;
	
	public MiHebraParametro(MiThread h) {
		remota = h;
	}
	
	public void run() {
		if(remota != null) {
			while(remota.getState() != Thread.State.TERMINATED) {
				System.out.println("Estado hembra remota "+remota.getState());
			}//for
		}//if
		
	}
	
	
}
