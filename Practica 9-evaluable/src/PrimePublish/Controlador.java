package PrimePublish;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Controlador implements ActionListener, PropertyChangeListener{
    
    private Panel panel;
    private WorkerPublish worker=null;
    public Controlador (Panel panel){
        this.panel=panel;
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(Panel.CALCULAR)){
            worker = new WorkerPublish(panel.getLongitud(), this.panel);
            worker.addPropertyChangeListener(this);
            worker.execute();
        }else if(e.getActionCommand().equals(Panel.CANCELAR)){
            if(worker!=null){
                worker.cancel(false); 
            }
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("progress")){
			panel.progreso((Integer) evt.getNewValue());
		}
	}
}
