package AmigosPublish;
import java.awt.event.*;

public class Controlador implements ActionListener{
    
    private Panel panel;
    private WorkerPublish worker=null;
    public Controlador (Panel panel){
        this.panel=panel;
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(Panel.CALCULAR)){
            worker = new WorkerPublish(panel.getLongitud(), this.panel);
            worker.execute();
        }else if(e.getActionCommand().equals(Panel.CANCELAR)){
            if(worker!=null){
                worker.cancel(false); 
            }
        }
    }
}
