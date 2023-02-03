package doapck1;

public class c implements Runnable{
	 public final void run() {
	      try {
	         MainFrame var1 = new MainFrame();
	         a var2 = new a(var1);
	         var1.a(var2);
	         var1.setVisible(true);
	      } catch (Exception var3) {
	         var3.printStackTrace();
	      }
	   }
}
