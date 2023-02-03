package doapck1;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.MessageFormat;
public final class a implements ActionListener, PropertyChangeListener{
	private MainFrame a;
	   private d b = null;

	   public a(MainFrame var1) {
	      this.a = var1;
	   }

	   public final void propertyChange(PropertyChangeEvent var1) {
	      if (var1.getPropertyName().equals("progress")) {
	         int var2 = (Integer)var1.getNewValue();
	         this.a.a(var2);
	         if (var2 >= 100) {
	            this.b = null;
	         }
	      }

	   }

	   public final void actionPerformed(ActionEvent var1) {
	      if (var1.getActionCommand().equals("START")) {
	         int var3;
	         try {
	            var3 = Integer.parseInt(this.a.a());
	         } catch (Exception var2) {
	            this.a.a(MainFrame.a.getString("status_invalid_number"));
	            return;
	         }

	         if (var3 > 0 && var3 <= 1000) {
	            this.a.b().setEnabled(false);
	            this.a.c().setEnabled(true);
	            this.a.a(0);
	            this.a.d();
	            this.a.a(MainFrame.a.getString("status_working"));
	            this.b = new d(this.a, this, var3);
	            this.b.execute();
	         } else {
	            this.a.a((new MessageFormat(MainFrame.a.getString("status_range_error"))).format(1000));
	         }
	      } else {
	         if (var1.getActionCommand().equals("CANCEL")) {
	            this.a.a(MainFrame.a.getString("status_cancelling"));
	            if (this.b != null) {
	               this.b.cancel(true);
	            }
	         }

	      }
	   }
}
