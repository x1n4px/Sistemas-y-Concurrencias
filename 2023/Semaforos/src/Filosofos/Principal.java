package Filosofos;

public class Principal {

	
	
	
	public static void main(String[] args) {
		int N=5;
		Sillas sillas = new Sillas();
		Tenedor[] t = new Tenedor[N];
		for (int i = 0; i < t.length; i++) {
			t[i] = new Tenedor(i);
		}
		
		Filosofos[] f = new Filosofos[N];
		for (int i = 0; i < f.length-1; i++) {
			f[i] = new Filosofos(i,t[i],t[(i+1)%N],sillas);
		}
		int i = f.length-1;
		f[f.length-1] = new Filosofos(i, t[(i+1)%N], t[i], sillas);
		
		
		for (int j = 0; j < f.length; j++) {
			f[j].start();
		}
		
	}
	
}
