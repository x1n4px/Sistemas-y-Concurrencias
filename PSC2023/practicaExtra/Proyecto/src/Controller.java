import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Controller implements ActionListener {
    private MainFrame mainFrame;
    private int[] numeros;
    private Lock lock;

    private boolean esCancelado = false;
    public Controller(MainFrame mainFrame){
        this.mainFrame = mainFrame;
        lock = new ReentrantLock();
        mainFrame.getCreateButton().addActionListener(this);
        mainFrame.getSortButton().addActionListener(this);
        mainFrame.getCancelButton().addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == mainFrame.getCreateButton()) {
            mainFrame.getTextPane1().setText("");
            mainFrame.getTextPane2().setText("");
            mainFrame.getTextPane3().setText("");
            mainFrame.getTiempoBurbuja().setText("");
            mainFrame.getSelectionSortTime().setText("");
            if(Integer.parseInt(mainFrame.getListInput().getText()) > 60000 || Integer.parseInt(mainFrame.getListInput().getText()) < 1){
                mainFrame.getMensajeError().setText("   enter a number between 1 and 60000");
            }else {
                mainFrame.getMensajeError().setText("   number correct");
            }
            numeros = generador(Integer.parseInt(mainFrame.getListInput().getText()));
            StringBuilder texto = new StringBuilder();
            for(int numero: numeros){
                texto.append(numero).append(", ");
            }
            mainFrame.getTextPane1().setText(texto.toString());
            mainFrame.getSortButton().setEnabled(true);
            mainFrame.getCancelButton().setEnabled(true);
        }else if(actionEvent.getSource() == mainFrame.getSortButton()) {
            mainFrame.getSortButton().setEnabled(false);

            lock.lock();
            try{
                WorkerSelection workerSelection = new WorkerSelection(this, mainFrame);
                Thread thread = new Thread(workerSelection);
                thread.start();
            }finally {
                lock.unlock();
            }
            lock.lock();
            try{
                WorkerBubble workerBubble = new WorkerBubble(mainFrame,this);
                Thread thread = new Thread(workerBubble);
                thread.start();
            }finally {

                lock.unlock();
            }


        }else if(actionEvent.getSource() == mainFrame.getCancelButton()) {
            mainFrame.getCancelButton().setEnabled(false);
            mainFrame.getSortButton().setEnabled(false);
            mainFrame.getCreateButton().setEnabled(true);

            esCancelado = true;
        }
    }


    public int[] generador(int cantidad) {
        int min = 1;
        int max = 60000;
        StringBuilder texto = new StringBuilder();

        int[] numerosAleatorios = new int[cantidad];
        Random random = new Random();
        Set<Integer> numerosGenerados = new HashSet<>();

        for (int i = 0; i < cantidad; i++) {
            int numeroAleatorio;
            do {
                numeroAleatorio = random.nextInt(max - min + 1) + min;
             } while (numerosGenerados.contains(numeroAleatorio));

            numerosAleatorios[i] = numeroAleatorio;
            numerosGenerados.add(numeroAleatorio);

            mainFrame.getMensajeError().setText("List created");

        }

        return numerosAleatorios;
    }




    public int[] getNumeros() {
        return numeros;
    }

    public boolean isEsCancelado() {
        return esCancelado;
    }
}
