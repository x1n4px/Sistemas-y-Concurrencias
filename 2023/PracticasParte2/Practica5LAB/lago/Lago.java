package lago;

public class Lago {
	private volatile int nivelAgua = 0;
	//peterson para rios
	private volatile boolean fR0 = false, fR1 = false;
	private volatile int turnoRio = 0;
	//peterson para presas
	private volatile boolean fP0 = false, fP1 = false;
	private volatile int turnoPresa = 0;
	//peterson para rios y presas
	private volatile boolean fRio = false, fPresa = false;
	private volatile int turno = 0;

	public Lago(int valorInicial) {
		nivelAgua = valorInicial;
	}

	// f0IncDec, f0Inc
	public void incRio0() {
		fR0 = true;
		turnoRio = 1;
		while (fR1 && turnoRio == 1){
			Thread.yield();
		}
		fRio = true;
		turno = 1;
		while(fPresa && turno == 1){
			Thread.yield();
		}
		nivelAgua++;
		fRio = false;
		fR0 = false;
	}

	// f0IncDec, f1Inc
	public void incRio1() {

		fR1 = true;
		turnoRio = 0;
		while (fR0 && turnoRio == 0){
			Thread.yield();
		}
		fRio = true;
		turno = 1;
		while(fPresa && turno == 1){
			Thread.yield();
		}
		nivelAgua++;
		fRio = false;
		fR1 = false;

	}

	// f1IncDec, f0Dec
	public void decPresa0() {
		fP0 = true;
		turnoPresa = 1;
		while (fP1 && turnoPresa == 1){
			Thread.yield();
		}
		while(nivelAgua == 0){
			Thread.yield();
		}
		fPresa = true;
		turno = 0;
		while(fRio && turno == 0){
			Thread.yield();
		}
		nivelAgua--;
		fPresa = false;
		fP0 = false;
	}

	// f1IncDec, f1Dec
	public void decPresa1() {
		fP1 = true;
		turnoPresa = 0;
		while (fP0 && turnoPresa == 0){
			Thread.yield();
		}
		while(nivelAgua == 0){
			Thread.yield();
		}
		fPresa = true;
		turno = 0;
		while(fRio && turno == 0){
			Thread.yield();
		}
		nivelAgua--;
		fPresa = false;
		fP1 = false;
	}

	public int nivel() {
		return nivelAgua;
	}
}
