
public class MiDemonio extends Thread{

	private int num;
	public MiDemonio(int n) {
		num = n;
	}
	
	public void run() {
		
		while(true) {
			//Dar servicio a otras hebras
			System.out.println("MiDemonio "+ num);
						
		}
		
	}
	
	
}
