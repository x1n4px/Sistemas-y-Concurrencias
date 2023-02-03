package Prod_Cons_Monitores;

public class ProdCons {

	public static void main(String[] args) {
		Buffer b = new Buffer();
		Productor p = new Productor(b, 30);
		Consumidor c = new Consumidor(b,30);
		
		p.start();
		c.start();
	}
	
	
}
