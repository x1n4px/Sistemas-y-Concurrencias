package Prime;


import java.awt.event.ActionListener;
import java.lang.ModuleLayer.Controller;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class Panel extends JPanel {
	public final static ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", new Locale("es","ES"));

	private JTextField tfldQuantity;
	private JLabel lblNewLabel;
	private JLabel lblStatus;
	private JProgressBar progressBar;
	private JLabel lblProgress;
	private JLabel lblResult;
	private JScrollPane scrollPane;
	private JTextPane tpanelResults;
	private JButton btnStart;
	private JButton btnCancel;

	/**
	 * Create the frame.
	 */
	public Panel() {
	
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		
		lblNewLabel = new JLabel(messages.getString("quantity"));
		lblNewLabel.setBounds(35, 9, 54, 14);
		lblNewLabel.setToolTipText(messages.getString("quantity_tooltip"));
		add(lblNewLabel);
		
		tfldQuantity = new JTextField();
		tfldQuantity.setBounds(94, 6, 86, 20);
		tfldQuantity.setActionCommand("START");
		add(tfldQuantity);
		tfldQuantity.setColumns(10);
		tfldQuantity.setText("10");
		
		btnStart = new JButton(messages.getString("start"));
		btnStart.setBounds(200, 5, 80, 23);
		btnStart.setActionCommand("START");
		btnStart.setToolTipText(messages.getString("start_tooltip"));
		add(btnStart);
		
		btnCancel = new JButton(messages.getString("cancel"));
		btnCancel.setBounds(295, 5, 100, 23);
		btnCancel.setActionCommand("CANCEL");
		btnCancel.setToolTipText(messages.getString("cancel_tooltip"));
		btnCancel.setEnabled(false);
		add(btnCancel);
		
		lblResult = new JLabel(messages.getString("results"));
		lblResult.setBounds(35, 33, 70, 14);
		add(lblResult);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(35, 52, 359, 147);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane);
		
		tpanelResults = new JTextPane();
		tpanelResults.setToolTipText(messages.getString("results_tooltip"));
		scrollPane.setViewportView(tpanelResults);
		
		lblProgress = new JLabel(messages.getString("progress"));
		lblProgress.setBounds(35, 205, 74, 14);
		add(lblProgress);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(107, 204, 287, 17);
		progressBar.setStringPainted(true);
		add(progressBar);
		
		lblStatus = new JLabel(messages.getString("status"));
		lblStatus.setBounds(5, 242, 419, 14);
		lblStatus.setHorizontalAlignment(SwingConstants.LEFT);
		add(lblStatus);
	}
	
	
   public void setController(ActionListener c) {
	   
	   btnStart.addActionListener(c);
	   tfldQuantity.addActionListener(c);
	   btnCancel.addActionListener(c);
   }
	
	public String getQuantityText() {
		return tfldQuantity.getText();
	}
	public void setStatus(String str) {
		lblStatus.setText(messages.getString("status")+" "+str);
	}
	public JButton getBtnStart() {
		return btnStart;
	}
	public JButton getBtnCancel() {
		return btnCancel;
	}   
	public void setMyProgress(int n){
		   progressBar.setValue(n);
	}

	public void clearLog() {
		tpanelResults.setText("");
	}

	public void appendLog(String aText) {
		tpanelResults.setText(tpanelResults.getText()+aText);
	}
}
