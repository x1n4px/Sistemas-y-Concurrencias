package AmigosPublish;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Panel extends JPanel{
	public static final String CALCULAR = "calcular";
	public static final String CANCELAR = "cancelar";

	private JTextField longitud = new JTextField(5);
	private JButton cancelB = new JButton();
	private JTextArea areaNumeros = new JTextArea(20,40);
	private JScrollPane scroll = new JScrollPane(areaNumeros); 
	private JLabel bottonLabel = new JLabel("");
	public Panel(){
		this.setLayout(new BorderLayout());
		//PAGE_START
		JPanel sup = new JPanel(); //Por defecto usa FlowLayout y todos lo elementos se organizan en linea
		sup.add(new JLabel("¿Cuantos amigos quieres?"));
		sup.add(longitud);
		cancelB.setText("Cancelar");
		sup.add(cancelB);
		this.add(sup, BorderLayout.PAGE_START);
				
		//CENTER
		this.add(scroll, BorderLayout.CENTER);
		
		this.add(bottonLabel, BorderLayout.PAGE_END);
	}
	
	
	public void setControlador(ActionListener ctrl){
		longitud.addActionListener(ctrl);
		longitud.setActionCommand(CALCULAR);
		
		cancelB.addActionListener(ctrl);
		cancelB.setActionCommand(CANCELAR);
	}
	
	
	public int getLongitud(){
		return Integer.parseInt(longitud.getText());
	}
	
	public void mensajeFin() {
		bottonLabel.setText("numeros amigos calculados");
	}
	public void mensajeCancelar() {
		bottonLabel.setText("operacion cancelada");
	}
	public void limpiar(){
		areaNumeros.setText("");
	}
	
	public void escribirLista(List<Amigos> amigos){
		for (int i=0; i<amigos.size();i++){
			areaNumeros.append(amigos.get(i).toString()+ " ");
			if((amigos.get(i).pos()+1)%5==0){
				areaNumeros.append("\n");
			}
		}
	}
}
