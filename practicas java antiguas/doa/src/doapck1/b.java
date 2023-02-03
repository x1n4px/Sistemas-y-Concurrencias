package doapck1;
import java.util.ArrayList;
import java.util.List;
public final class b extends Thread{
	public List a = new ArrayList();
	   private final int b;

	   public b(int var1) {
	      this.b = var1;
	   }

	   public final void run() {
	      int var1 = this.b;
	      int var2 = 0;

	      while(var1 > 1) {
	         try {
	            if (var1 % PrimeGenerator.a(var2) == 0) {
	               this.a.add(PrimeGenerator.a(var2));
	               var1 /= PrimeGenerator.a(var2);
	            } else {
	               ++var2;
	            }
	         } catch (Exception var4) {
	            var4.printStackTrace();
	         }
	      }

	   }

	   public final int a() {
	      return this.b;
	   }

	   public static int a(List var0, List var1) {
	      int var2;
	      int var3;
	      for(var2 = 1; !var0.isEmpty(); var0.remove(var3)) {
	         var3 = (Integer)var0.get(0);
	         if (var1.remove(var3)) {
	            var2 *= var3;
	         }
	      }

	      return var2;
	   }
}
