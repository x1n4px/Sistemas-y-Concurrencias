package doapck1;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;
public class d extends SwingWorker{
	 private static final Random a = new Random();
	   private MainFrame b;
	   private int c;

	   public d(MainFrame var1, a var2, int var3) {
	      this.b = var1;
	      this.c = var3;
	      this.addPropertyChangeListener(var2);
	   }

	   protected final void process(List var1) {
	      Iterator var2 = var1.iterator();

	      while(var2.hasNext()) {
	         String var3 = (String)var2.next();
	         this.b.b(var3);
	      }

	   }

	   protected final void done() {
	      try {
	         if (this.isCancelled()) {
	            throw new CancellationException();
	         }

	         List var1 = (List)this.get();
	         this.b.d();
	         this.b.b(MainFrame.a.getString("results_log"));
	         Iterator var2 = var1.iterator();

	         while(var2.hasNext()) {
	            String var4 = (String)var2.next();
	            this.b.b(var4);
	         }

	         this.b.a(MainFrame.a.getString("status_finished"));
	      } catch (ExecutionException | CancellationException | InterruptedException var3) {
	         this.b.a(MainFrame.a.getString("status_cancelled"));
	      }

	      this.b.b().setEnabled(true);
	      this.b.c().setEnabled(false);
	   }

	   // $FF: synthetic method
	   protected final Object doInBackground() {
	      d var1 = this;
	      ArrayList var2 = new ArrayList();
	      int var3 = 0;

	      while(var3 < var1.c) {
	         b[] var4 = new b[2];

	         int var5;
	         for(var5 = 0; var5 < 2; ++var5) {
	            int var6 = 2 + a.nextInt(999998);
	            var4[var5] = new b(var6);
	            var4[var5].start();
	         }

	         for(var5 = 0; var5 < 2; ++var5) {
	            var4[var5].join();
	         }

	         if (var1.isCancelled()) {
	            break;
	         }

	         var5 = b.a(var4[0].a, var4[1].a);
	         String var7 = var4[0].a() + ", " + var4[1].a() + "... " + var5 + "\n";
	         var2.add(var7);
	         var1.publish(new String[]{var7});
	         ++var3;
	         var1.setProgress(var3 * 100 / var1.c);
	      }

	      return var2;
	   }
}
