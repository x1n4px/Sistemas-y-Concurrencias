package doapck1;

import java.awt.EventQueue;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainFrame extends JFrame {
   public static final ResourceBundle a = ResourceBundle.getBundle("i18n.galvez.uma.es.MessagesBundle", new Locale("es", "ES"));
   private JPanel b;
   private JTextField c;
   private JLabel d;
   private JLabel e;
   private JProgressBar f;
   private JLabel g;
   private JLabel h;
   private JScrollPane i;
   private JTextPane j;
   private JButton k;
   private JButton l;

   public static void main(String[] var0) {
      EventQueue.invokeLater(new c());
   }

   public MainFrame() {
      this.setTitle(a.getString("title"));
      this.setDefaultCloseOperation(3);
      this.setBounds(100, 100, 450, 300);
      this.b = new JPanel();
      this.b.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.setContentPane(this.b);
      this.b.setLayout((LayoutManager)null);
      this.d = new JLabel(a.getString("quantity"));
      this.d.setBounds(35, 9, 54, 14);
      this.d.setToolTipText(a.getString("quantity_tooltip"));
      this.b.add(this.d);
      this.c = new JTextField();
      this.c.setBounds(94, 6, 86, 20);
      this.c.setActionCommand("START");
      this.b.add(this.c);
      this.c.setColumns(10);
      this.c.setText("10");
      this.k = new JButton(a.getString("start"));
      this.k.setBounds(200, 5, 80, 23);
      this.k.setActionCommand("START");
      this.k.setToolTipText(a.getString("start_tooltip"));
      this.b.add(this.k);
      this.l = new JButton(a.getString("cancel"));
      this.l.setBounds(295, 5, 100, 23);
      this.l.setActionCommand("CANCEL");
      this.l.setToolTipText(a.getString("cancel_tooltip"));
      this.l.setEnabled(false);
      this.b.add(this.l);
      this.h = new JLabel(a.getString("results"));
      this.h.setBounds(35, 33, 70, 14);
      this.b.add(this.h);
      this.i = new JScrollPane();
      this.i.setBounds(35, 52, 359, 147);
      this.i.setVerticalScrollBarPolicy(22);
      this.b.add(this.i);
      this.j = new JTextPane();
      this.j.setToolTipText(a.getString("results_tooltip"));
      this.i.setViewportView(this.j);
      this.g = new JLabel(a.getString("progress"));
      this.g.setBounds(35, 205, 74, 14);
      this.b.add(this.g);
      this.f = new JProgressBar();
      this.f.setBounds(107, 204, 287, 17);
      this.f.setStringPainted(true);
      this.b.add(this.f);
      this.e = new JLabel(a.getString("status"));
      this.e.setBounds(5, 242, 419, 14);
      this.e.setHorizontalAlignment(2);
      this.b.add(this.e);
   }

   public final void a(a var1) {
      this.k.addActionListener(var1);
      this.c.addActionListener(var1);
      this.l.addActionListener(var1);
   }

   public final String a() {
      return this.c.getText();
   }

   public final void a(String var1) {
      this.e.setText(a.getString("status") + " " + var1);
   }

   public final JButton b() {
      return this.k;
   }

   public final JButton c() {
      return this.l;
   }

   public final void a(int var1) {
      this.f.setValue(var1);
   }

   public final void d() {
      this.j.setText("");
   }

   public final void b(String var1) {
      this.j.setText(this.j.getText() + var1);
   }
}