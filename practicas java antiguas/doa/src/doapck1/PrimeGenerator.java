package doapck1;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Semaphore;


public class PrimeGenerator {
	private static List a = new ArrayList();
	   private static Semaphore b = new Semaphore(1, true);

	   static {
	      a.add(2);
	      a.add(3);
	   }

	   public static int a(int var0) throws Exception {
	      if (var0 < 0) {
	         throw new Exception(MainFrame.a.getString("getPrime_exception"));
	      } else {
	         b.acquire();

	         while(var0 >= a.size()) {
	            a();
	         }

	         var0 = (Integer)a.get(var0);
	         b.release();
	         return var0;
	      }
	   }

	   private static int a() {
	      List var10000 = a;
	      int var0 = (Integer)var10000.get(var10000.size() - 1);

	      boolean var4;
	      label22:
	      do {
	         var0 += 2;
	         int var1 = var0;
	         Iterator var3 = a.iterator();

	         while(var3.hasNext()) {
	            int var2 = (Integer)var3.next();
	            if (var1 % var2 == 0) {
	               var4 = false;
	               continue label22;
	            }
	         }

	         var4 = true;
	      } while(!var4);

	      a.add(var0);
	      return var0;
	   }

	   public static void main(String[] var0) throws Exception {
	      for(int var1 = 0; var1 < 100; ++var1) {
	         System.out.println(a(var1));
	      }

	   }
}
