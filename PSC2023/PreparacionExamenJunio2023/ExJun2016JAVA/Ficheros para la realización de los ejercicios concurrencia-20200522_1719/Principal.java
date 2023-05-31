package aseos;

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int NUM_CLIENTES = 20;
		
		AseosSemaforo aseo = new AseosSemaforo();
		//AseosLocks aseo = new AseosLocks();
		Cliente[] cliente = new Cliente[NUM_CLIENTES];
		for (int i = 0; i<cliente.length; i++){
			cliente[i] = new Cliente(i,aseo);
		}
		EquipoLimpieza equipo = new EquipoLimpieza(aseo);
		equipo.start();
		for (int i = 0; i<cliente.length; i++){
			cliente[i].start();
		}
	}

}
