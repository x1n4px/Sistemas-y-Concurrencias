import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainFrame extends JFrame{

    private JButton createButton;
    private JButton sortButton;
    private JButton cancelButton;
    private JTextField listInput;
    private JPanel progressBarArray;
    private JPanel progressBarSeletionSort;
    private JPanel ProgressBarBubbleSort;
    private JPanel mainPanel;
    private JPanel space2;
    private JProgressBar progressBarArraySort;
    private JProgressBar progressBarSelectionSort;
    private JProgressBar progressBarBubbleSortB;
    private JTextPane textPane1;
    private JTextPane textPane2;
    private JLabel SelectionSortTime;
    private JLabel tiempoBurbuja;
    private JTextPane textPane3;
    private JLabel MensajeError;
    private JLabel CreateLabel;
    private int[] numeros;
    private int[] numerosOrdenacion;
    private int[] numerosOrdenacionB;
     public MainFrame() {
        Controller controller = new Controller(this);


        createButton.addActionListener(controller);
        setContentPane(mainPanel);
        setTitle("Práctica Sistemas y Concurrencia");
        setSize(800, 350);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);


    }


    public void actualizar(int valor){
        System.out.println(valor);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                progressBarSelectionSort.setValue(valor);            }
        });

        // progressBarSelectionSort.repaint();
    }
    public JLabel getCreateLabel() {
        return CreateLabel;
    }


    public JButton getSortButton() {
        return sortButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JTextField getListInput() {
        return listInput;
    }

    public JPanel getSpace2() {
        return space2;
    }

    public JProgressBar getProgressBarArraySort() {
        return progressBarArraySort;
    }

    public JProgressBar getProgressBarSelectionSort() {
        return progressBarSelectionSort;
    }

    public JLabel getSelectionSortTime() {
        return SelectionSortTime;
    }

    public JLabel getTiempoBurbuja() {
        return tiempoBurbuja;
    }

    public JLabel getMensajeError() {
        return MensajeError;
    }

    public int[] getNumeros() {
        return numeros;
    }

    public int[] getNumerosOrdenacion() {
        return numerosOrdenacion;
    }

    public int[] getNumerosOrdenacionB() {
        return numerosOrdenacionB;
    }

    public JButton getCreateButton() {
        return createButton;
    }

    public JPanel getProgressBarArray() {
        return progressBarArray;
    }

    public JPanel getProgressBarSeletionSort() {
        return progressBarSeletionSort;
    }

    public JPanel getProgressBarBubbleSort() {
        return ProgressBarBubbleSort;
    }

    public JProgressBar getProgressBarBubbleSortB() {
        return progressBarBubbleSortB;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JTextPane getTextPane1() {
        return textPane1;
    }

    public JTextPane getTextPane2() {
        return textPane2;
    }

    public JTextPane getTextPane3() {
        return textPane3;
    }

    public static void main(String[] args) {
        MainFrame myFrame = new MainFrame();
    }
}
