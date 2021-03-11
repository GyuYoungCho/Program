package teamProject;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.ScrollPaneConstants;

public class DayviewFrame extends JFrame {

	private Calender cal; //CalenderŬ����
	   private DayviewDialog dvd; 
	   private DayviewDialog DayDlg; //DayviewDialog����

	   private JPanel contentPane;
	   private JScrollPane scrollPane; //��ũ�ѹ�
	   private JPanel panel;
	   private JButton btnAdd;
	   private JButton btnEdit;
	   private JButton btnDelete;
	   private JButton btnSave; //��ư��

	   public JTable table;
	   private String header[] = { "Complete", "Time", "To do" }; //table���
	   private Object schedule[][] = {}; //������
	   private JCheckBox box;
	   public int row; //table�� row��

	   private JSplitPane splitPane; //���̺� ������ ����
	   public JTable table_2; //������Ѻ��� �������̺� �� �ð��밡 ������ ���̺�
	   public JLayeredPane layeredPane; //�ð�ǥ�� �׸��� ����
	   public JTable table_3; //�ð�ǥ�� �׷����� ���̺�
	   public JButton[] timetable = new JButton[100]; //��ư 100�� ���� ����

	   public DefaultTableModel defaultTableModel = new DefaultTableModel(schedule, header);
	   //���̺� �� ����
	   final JScrollPane scrollPane_1 = new JScrollPane(table); //��ũ�ѿ� ���̺� ����

	   public String buttonname;//��ư�̸��� ���� �ٸ� ��� ����

	   public DayviewFrame(final Calender cal, final DayviewDialog dvd) {
	      for (int i = 0; i < 100; i++) {
	         timetable[i] = new JButton();
	         timetable[i].setFont(new Font("����ü", Font.BOLD, 9));
	      } //��ư�� text����

	      this.cal = cal;
	      this.dvd = dvd; //�ٸ� Ŭ������ �ʵ�� �޼ҵ带 ����ϱ� ����
	      setTitle(cal.SelectMonth + " - " + cal.SelectDay);
	      setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	      setBounds(100, 100, 660, 457);
	      contentPane = new JPanel();
	      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	      setContentPane(contentPane);
	      contentPane.setLayout(null);

	      scrollPane = new JScrollPane();
	      scrollPane.setBounds(0, 10, 335, 399);
	      contentPane.add(scrollPane);//�гο� ��ũ�ѹ�

	      splitPane = new JSplitPane();
	      splitPane.setDividerSize(1);
	      scrollPane.setViewportView(splitPane);
	      //��ũ�ѹٿ� splitpane����

	      table_2 = new JTable();

	      table_2.setRowHeight(60);
	      table_2.setModel(
	            new DefaultTableModel( //���̺� �ð��� ����
	                  new Object[][] { { "00.00" }, { "01.00" }, { "02.00" }, { "03.00" }, { "04.00" }, { "05.00" },
	                        { "06.00" }, { "07.00" }, { "08.00" }, { "09.00" }, { "10.00" }, { "11.00" },
	                        { "12.00" }, { "13.00" }, { "14.00" }, { "15.00" }, { "16.00" }, { "17.00" },
	                        { "18.00" }, { "19.00" }, { "20.00" }, { "21.00" }, { "22.00" }, { "23.00" }, },
	                  new String[] { "New column" }));
	      table_2.getColumnModel().getColumn(0).setPreferredWidth(60);
	      splitPane.setLeftComponent(table_2); //splitpane�� ���ʿ� �ð���table ����

	      layeredPane = new JLayeredPane();//JLayeredPane�ʱ�ȭ
	      layeredPane.setLayout(null);

	      splitPane.setRightComponent(layeredPane); //splitPane�����ʿ� layeredPane����

	      table_3 = new JTable();
	      table_3.setShowVerticalLines(false);
	      table_3.setModel(new DefaultTableModel( //�ð��� ���̺� �ʱ�ȭ
	            new Object[][] { { null, null, null, null }, { null, null, null, null }, { null, null, null, null },
	                  { null, null, null, null }, { null, null, null, null }, { null, null, null, null },
	                  { null, null, null, null }, { null, null, null, null }, { null, null, null, null },
	                  { null, null, null, null }, { null, null, null, null }, { null, null, null, null },
	                  { null, null, null, null }, { null, null, null, null }, { null, null, null, null },
	                  { null, null, null, null }, { null, null, null, null }, { null, null, null, null },
	                  { null, null, null, null }, { null, null, null, null }, { null, null, null, null },
	                  { null, null, null, null }, { null, null, null, null }, { null, null, null, null },
	                  { null, null, null, null }, { null, null, null, null }, { null, null, null, null },
	                  { null, null, null, null }, { null, null, null, null }, { null, null, null, null },
	                  { null, null, null, null }, { null, null, null, null }, { null, null, null, null },
	                  { null, null, null, null }, { null, null, null, null }, { null, null, null, null },
	                  { null, null, null, null }, { null, null, null, null }, { null, null, null, null },
	                  { null, null, null, null }, { null, null, null, null }, { null, null, null, null },
	                  { null, null, null, null }, { null, null, null, null }, { null, null, null, null },
	                  { null, null, null, null }, { null, null, null, null }, { null, null, null, null }, },
	            new String[] { "New column", "New column", "New column", "New column" }));
	      table_3.setRowHeight(30);
	      table_3.setBounds(0, 0, 280, 1440);

	      layeredPane.add(table_3, 0, -1);//layeredPane�� �ð�ǥ ���̺� ����

	      panel = new JPanel();
	      panel.setBounds(335, 10, 309, 399);
	      contentPane.add(panel);
	      panel.setLayout(null);

	      table = new JTable(defaultTableModel); //���� üũ, �ð���, ���� ������ �����ִ� ���̺�
	      scrollPane_1.setViewportView(table);
	      table.getColumn("Complete").setCellRenderer(dcr);//�� �������
	      table.getRowSelectionAllowed();

	      box = new JCheckBox();
	      box.setHorizontalAlignment(JLabel.CENTER); 
	      box.addActionListener(new ActionListener() {

	         @Override
	         public void actionPerformed(ActionEvent e) {
	            // TODO Auto-generated method stub
	            if (box.isSelected() == true) { //üũ�ڽ� �� �ڵ鸵
	               JOptionPane.showMessageDialog(null, "Congratulaion! You finish plan!");
	               try {
	                  FileReader fr = new FileReader("ToDo.txt"); //��������
	                  BufferedReader br = new BufferedReader(fr);
	                  String sCurrentLine;
	                  String dummy = ""; //���ϳ����� ������ ����
	                  // DayDlg.inputStr[0]=box.isSelected():
	                  while ((sCurrentLine = br.readLine()) != null) {//������ ��� ����
	                     String[] Str = sCurrentLine.split("[;]"); //;�� ����

	                     if (Str[0].equals(String.valueOf(cal.Year + "_" + cal.SelectMonth + "_" + cal.SelectDay))
	                           && Str[2].equals(table.getValueAt(table.getSelectedRow(), 1))
	                           && Str[3].equals(table.getValueAt(table.getSelectedRow(), 2))) {//������ ������
	                        dummy += String.valueOf(cal.Year) + "_" + cal.SelectMonth + "_" + cal.SelectDay + ";"
	                              + DayDlg.inputStr[0] + ";" + DayDlg.inputStr[1] + ";" + DayDlg.inputStr[2]
	                              + "\n";
	                        //���̿� ���� ���·� ������ ����
	                     } else {
	                        dummy += sCurrentLine + "\n"; //���̿� ���� ������ ����
	                     }

	                  }

	                  FileWriter fw = new FileWriter("ToDo.txt"); //���� ��
	                  BufferedWriter bw = new BufferedWriter(fw); 
	                  bw.write(dummy);
	                  // ���б�ȣ ;
	                  // [�⵵_��_��];�ϷῩ��;�ð�:�ð�;����\n
	                  bw.flush(); //���۸� ����
	                  bw.close(); 

	                  br.close(); //��� ����

	               } catch (Exception e1) {//����ó��
	                  e1.printStackTrace();
	               }

	            } else if (box.isSelected() == false) {//���õ��� ������

	               try {

	                  FileReader fr = new FileReader("ToDo.txt");
	                  BufferedReader br = new BufferedReader(fr); //��������
	                  String sCurrentLine;
	                  String dummy = "";
	                  DayDlg.inputStr[0] = box.isSelected(); //�ڽ������� ����
	                  while ((sCurrentLine = br.readLine()) != null) {//�������� 
	                     String[] Str = sCurrentLine.split("[;]");

	                     if (Str[0].equals(String.valueOf(cal.Year + "_" + cal.SelectMonth + "_" + cal.SelectDay))
	                           && Str[2].equals(table.getValueAt(table.getSelectedRow(), 1))
	                           && Str[3].equals(table.getValueAt(table.getSelectedRow(), 2))) 
	                     { //������ ������
	                        dummy += String.valueOf(cal.Year) + "_" + cal.SelectMonth + "_" + cal.SelectDay + ";"
	                              + DayDlg.inputStr[0] + ";" + DayDlg.inputStr[1] + ";" + DayDlg.inputStr[2]
	                              + "\n"; //������ ���� ����
	                     } else {
	                        dummy += sCurrentLine + "\n"; //���̿� ������ ����
	                     }

	                  }

	                  FileWriter fw = new FileWriter("ToDo.txt");
	                  BufferedWriter bw = new BufferedWriter(fw);
	                  bw.write(dummy);
	                  // ���б�ȣ ;
	                  // [�⵵_��_��];�ϷῩ��;�ð�:�ð�;����\n
	                  bw.flush();//���۸� ���
	                  bw.close();

	                  br.close();//����

	               } catch (Exception e1) { //����ó��
	                  e1.printStackTrace();
	               }

	            }
	         }

	      });
	      table.getColumn("Complete").setCellEditor(new DefaultCellEditor(box));
	      //���� üũ�� �� �ʿ��� üũ�ڽ� ���
	      btnAdd = new JButton("Add");//ADD��ư
	      btnAdd.addActionListener(new ActionListener() {//������
	         public void actionPerformed(ActionEvent arg0) {
	            buttonname = btnAdd.getActionCommand();//��ư �̸��� ����
	            DayDlg = new DayviewDialog(cal, DayviewFrame.this);//DayviewDialog ����
	            DayDlg.setVisible(true);
	         }
	      });

	      btnAdd.setBounds(44, 10, 90, 40);
	      panel.add(btnAdd);//�гο� ��ư����

	      btnEdit = new JButton("Edit"); //Edit��ư
	      btnEdit.addActionListener(new ActionListener() {//�ҷ�����
	         public void actionPerformed(ActionEvent e) {
	            try {
	               if (table.isRowSelected(table.getSelectedRow()) == false) {
	                  throw new DoNotSelectException();
	                  //���̺��� ������ �������� �ʾ����� ����ó�� �ۿ�
	               }
	               DayDlg = new DayviewDialog(cal, DayviewFrame.this);//DayviewDialog����
	               DayDlg.setVisible(true);
	               row = table.getSelectedRow();
	               buttonname = btnEdit.getActionCommand(); //��ư �̸��� ����
	               DayDlg.textEvent.setText(String.valueOf(table.getValueAt(row, 2)));
	               //������ event�� ������ 
	               String[] divideTime = String.valueOf(table.getValueAt(row, 1)).split("[:]");
	               //������ �ð��븦 ������
	               DayDlg.textFrom.setText(divideTime[0]);
	               DayDlg.textTo.setText(divideTime[1]);
	            } catch (DoNotSelectException ee) {
	               JOptionPane.showMessageDialog(null, "select event", "do not be selected",
	                     JOptionPane.ERROR_MESSAGE); //����ó��
	            }
         }
      });
      btnEdit.setBounds(170, 10, 90, 40);
      panel.add(btnEdit);// Edit ��ư�� panel �� �Ӵ´�

      btnDelete = new JButton("delete");// ������ư ����
      btnDelete.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {

            try {
               if (table.isRowSelected(table.getSelectedRow()) == false) {// ���õ� ���� ������ ����ó��
                  throw new DoNotSelectException();
               }
               int reply = JOptionPane.showConfirmDialog(null, "Would delete this event?", "Delete Event",
                     JOptionPane.YES_NO_OPTION);// ���� ������ ������ ��Ȯ�� �ϴ� �޼��� ���
               if (reply == JOptionPane.YES_OPTION) {
            	   try {

                       FileReader fr = new FileReader("ToDo.txt");
                       BufferedReader br = new BufferedReader(fr);// �б���� �ؽ�Ʈ ���� ����
                       String sCurrentLine;//������ ������ ������ �о�� ����
                       String dummy = ""; // ������ ������ ��ƿ� String ���� ����
                       while ((sCurrentLine = br.readLine()) != null) {//������ ������ ���������� �ݺ�

                          String[] Str = sCurrentLine.split("[;]");//���� ���ڿ� �°� ��¥�� �Ϸ� ����, �ð��� ����� �и��Ͽ� ��´�. 

                          if (Str[0]
                                .equals(String.valueOf(cal.Year + "_" + cal.SelectMonth + "_" + cal.SelectDay))
                                && Str[2].equals(table.getValueAt(table.getSelectedRow(), 1))
                                && Str[3].equals(table.getValueAt(table.getSelectedRow(), 2))) {

                      // �ش��ϴ� ������ ������ �ش��ϴ� line�� �����Ͽ� dummy�� ����
                          } else {
                             dummy += sCurrentLine + "\n"; // �ش��ϴ� ���� ������ ������ ����  dummy�� �״�� ����
                          }

                       }

                       FileWriter fw = new FileWriter("ToDo.txt");
                       BufferedWriter bw = new BufferedWriter(fw);
                       bw.write(dummy); // ������ ������ ���Ͽ� ����.
                       // ���б�ȣ ;
                       // [�⵵_��_��];�ϷῩ��;�ð�:�ð�;����\n
                       bw.flush();//���۸� ����
                       bw.close();//���� �ݱ�

                       br.close();
                    } catch (Exception e1) {
                       e1.printStackTrace();
                    }//����ó��


                  int row = table.getSelectedRow();
                  defaultTableModel = (DefaultTableModel) table.getModel();
                  defaultTableModel.removeRow(row);
                  timetable[row].setEnabled(false);
                  timetable[row].setVisible(false);
               }
            } catch (DoNotSelectException ee) {
               JOptionPane.showMessageDialog(null, "select event", "do not be selected",
                     JOptionPane.ERROR_MESSAGE);

            }//����ó��
         }
      });
      btnDelete.setBounds(44, 64, 90, 40);
      panel.add(btnDelete);// ������ư�� ��ο� �ִ´�.

      scrollPane_1.setBounds(29, 127, 265, 199);
      panel.add(scrollPane_1); // ��ũ�� �г� ����

      btnSave = new JButton("Save"); // Save ��ư ����
      btnSave.addActionListener(new ActionListener() {// ActionLisner
         public void actionPerformed(ActionEvent e) {	//�׼� ����
            if (table.getValueAt(0, 2).equals(null)) {
               JOptionPane.showMessageDialog(null, "no data", "no data", JOptionPane.ERROR_MESSAGE); // �����޼��� ���
            } else {
               if (table.getRowCount() == 1) { //low ������ 1�̸�
                  for (int i = 0; i < 42; i++) {
                     if (cal.buttemp[i].getText().equals(cal.SelectDay)) {
                        cal.buttemp[i].setText("<html>" + cal.buttemp[i].getText() + "<br/>"
                              + table.getValueAt(0, 2) + "</html>");// Calender�� �ش� ��¥�� ��ư�� ���������� ����
                        break;
                     }
                  }

               } else if (table.getRowCount() > 1) {
                  for (int i = 0; i < 42; i++) {
                     if (cal.buttemp[i].getText().equals(cal.SelectDay)) { // Calender ��ư�� ��¥�� ���õ� ��¥�� ������
                        cal.buttemp[i].setText("<html>" + cal.buttemp[i].getText() + "<br/>"
                              + table.getValueAt(0, 2) + "<br/>More...</html>");	// Calender�� �ش� ��¥�� ��ư�� ���������� ����
                        JOptionPane.showMessageDialog(null, "Save at Calendar!");	// calecdar�� ����Ǿ��ٴ� Ȯ�� �޼��� ���
                        break;
                     }
                  }
               }
            }
         }
      });

      btnSave.setBounds(170, 64, 90, 40);
      panel.add(btnSave); // Save ��ư�� panel �� �߰�

   }

   DefaultTableCellRenderer dcr = new DefaultTableCellRenderer() {// table ������ checkbox�� �ٷ�µ� ���
	      public Component getTableCellRendererComponent // ��������, table������ ����ڰ� ���ϴ� cell�� component�� �ִ´�.
	      (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	         JCheckBox box = new JCheckBox(); // checkbox ����
	         box.setSelected(((Boolean) value).booleanValue());
	         box.setHorizontalAlignment(JLabel.CENTER);// ��� �迭
	         return box;	
	      }
	   };

   public class DoNotSelectException extends Exception {

   }
}