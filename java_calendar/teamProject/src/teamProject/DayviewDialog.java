package teamProject;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class DayviewDialog extends JDialog implements ActionListener {
// ���̾�α� -> DayviewFrame ���� Add �� Edit �� ������ ��, ������ �߰���Ű�ų� ������Ű�� ���̾�α� 
   private final JPanel contentPanel = new JPanel();
   private JLabel lblNewLabel;
   public JTextField textEvent;
   public JTextField textFrom;
   public JTextField textTo;
   private Calender cal;
   private DayviewFrame dvf;

   public String strEvent;
   public String strTo;
   public String strFrom;
   private JLabel lblNewLabel_3;
   public Object[] inputStr = new Object[3];

   private static int timetableIndex = 0;
   private static int totaloverlapcount = 0;

   public DayviewDialog(final Calender cal, final DayviewFrame dvf) {

      this.cal = cal;
      this.dvf = dvf;	// �ٸ� Ŭ���� ���� �޼ҵ带 �̿��ϱ� ����
      setTitle(cal.SelectMonth + " - " + cal.SelectDay); //���� : �� - ��
      setBounds(100, 100, 300, 300);// ���̾�α� ũ�� ����
      setDefaultCloseOperation(HIDE_ON_CLOSE);
      getContentPane().setLayout(new BorderLayout());
      contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
      getContentPane().add(contentPanel, BorderLayout.CENTER);
      contentPanel.setLayout(null);// contentPanel ����

      lblNewLabel = new JLabel("Event"); // Event lable �߰��Ͽ� �� �ؽ�Ʈ �ʵ�â ����
      lblNewLabel.setBounds(12, 56, 40, 15);
      contentPanel.add(lblNewLabel); 
      {
         JLabel lblNewLabel_1 = new JLabel("From");// From lable �߰��Ͽ� �� �ؽ�Ʈ �ʵ�â ����
         lblNewLabel_1.setBounds(12, 93, 40, 15);
         contentPanel.add(lblNewLabel_1);	
      }
      {
         JLabel lblNewLabel_2 = new JLabel("To");// To lable �߰��Ͽ� �� �ؽ�Ʈ �ʵ�â ����
         lblNewLabel_2.setBounds(12, 132, 40, 15);
         contentPanel.add(lblNewLabel_2); // ���� ������ �ð� �Է�â
      }

      textEvent = new JTextField();
      textEvent.setBounds(53, 53, 200, 21);
      contentPanel.add(textEvent);
      textEvent.setColumns(10);
   // event ����������  �Է��ϴ� textfield â ����

      textFrom = new JTextField();
      textFrom.setBounds(53, 90, 200, 21);
      contentPanel.add(textFrom);
      textFrom.setColumns(10);
   // ���� ���� �ð���  �Է��ϴ� textfield â ����
      
      textTo = new JTextField();
      textTo.setBounds(53, 129, 200, 21);
      contentPanel.add(textTo);
      textTo.setColumns(10);
   // ���� ������ �ð���  �Է��ϴ� textfield â ����
      
      lblNewLabel_3 = new JLabel("Please input time xx.xx"); // Lable â�� ���ؼ� �ð��� �Է��ϴ� ������ �˷��ش�.
      lblNewLabel_3.setBounds(12, 183, 189, 15);
      contentPanel.add(lblNewLabel_3);
      {
         JPanel buttonPane = new JPanel();	// buttons ���� ��Ƴ��� panel ���� OK ��ư�� Cancel��ư�� �ִ�.
         buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER)); // FlowLayout ����(��ư�� �����)
         getContentPane().add(buttonPane, BorderLayout.SOUTH); // contentPanel �Ʒ��� buttonpanel �ֱ�
         {
            JButton okButton = new JButton("OK");// OK ��ư ����
            okButton.addActionListener(this); // addActionListener�� ���ؼ� ok button handling
            okButton.setActionCommand("OK"); //�׼� Ŀ�ǵ� ����
            buttonPane.add(okButton);// �гο� ��ư �߰�
            getRootPane().setDefaultButton(okButton);
         }
         {
            JButton cancelButton = new JButton("Cancel");// Cancel ��ư ����
            cancelButton.addActionListener(new ActionListener() {// addActionListener�� ���ؼ� Cancel button handling
               public void actionPerformed(ActionEvent e) { 
                  setVisible(false);	//���̾˷α׸� �����ش�.
               }
            });
            cancelButton.setActionCommand("Cancel");//�׼� Ŀ�ǵ� ����
            buttonPane.add(cancelButton);// �гο� ��ư �߰�
         }
      }

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

   public class NotEventException extends Exception {

   }

   public class TimeCollideException extends Exception {

   }

   public class TimeOverException extends Exception {

   }

   public class NoInputException extends Exception {

   }//exeption handling

   public void actionPerformed(ActionEvent e) {
      // TODO Auto-generated method stub
      String s = e.getActionCommand();
      
      {
         strEvent = textEvent.getText();// ���� ������ �Է��� event �ؽ�Ʈ�ʵ�â�� �Է��� ���ڸ� copy
         strFrom = textFrom.getText();//���۽ð� �Է��ϴ� �ؽ�Ʈ�ʵ�â�� �Է��� ���ڸ� copy
         strTo = textTo.getText();//������ �ð� �Է��ϴ� �ؽ�Ʈ�ʵ�â�� �Է��� ���ڸ� copy
         String[] FromTime = textFrom.getText().split("[.]");// ���۽ð��� �ð� �� �� �κ��� �и��Ͽ� ����
         String[] ToTime = textTo.getText().split("[.]");// ������ �ð��� �ð� �� �� �κ��� �и��Ͽ� ����

         try {
            if (textEvent.getText() == "" || textFrom.getText() == "" || textTo.getText() == "") {
               throw new NotEventException();
            }
            if ((Integer.parseInt(FromTime[0]) > Integer.parseInt(ToTime[0]))
                  || ((Integer.parseInt(FromTime[0]) == Integer.parseInt(ToTime[0]))
                        && (Integer.parseInt(FromTime[1]) > Integer.parseInt(ToTime[1])))) {
               throw new TimeCollideException();//���۽ð��� ������ �ð����� �����϶� ����ó��
            }

            if (Integer.parseInt(FromTime[0]) > 24 || Integer.parseInt(ToTime[0]) > 24
                  || Integer.parseInt(FromTime[1]) > 60 || Integer.parseInt(ToTime[1]) > 60) {
               throw new TimeOverException(); // �ð��� ������ ���� ���� �� exeptionó��
            }
            if (strEvent == null || strFrom == null || strTo == null) {
               throw new NoInputException();
            }// ������ exeption ó��

            inputStr[1] = strFrom + ":" + strTo;//�����ϴ� �ð��� ������ �ð��� :�� ������ String type�� ������ Object[] ��ü�� ����
            inputStr[2] = strEvent;// event�� ����  String type�� ������ Object[] ��ü�� ����

            if (dvf.buttonname == "Add") {//���� �߰� ��ư
               inputStr[0] = false;//check �ڽ��� ���� false�� ����
               dvf.defaultTableModel.addRow(inputStr); //defaultTableModel�� �̿��� table �� ���� ���� 

               try {
                  FileWriter fw = new FileWriter("ToDo.txt", true);
                  BufferedWriter bw = new BufferedWriter(fw);//���� �Է� �����, append ���� ����
                  bw.write(String.valueOf(cal.Year) + "_" + cal.SelectMonth + "_" + cal.SelectDay + ";"
                        + inputStr[0] + ";" + inputStr[1] + ";" + inputStr[2] + "\n");            
                  // ���б�ȣ ;
                  // �⵵_��_��;�ϷῩ��;�ð�:�ð�;����\n �� ���Ͽ� �Է��Ѵ�.
                  bw.flush();//���۸� ����.
                  bw.close();//���� ���� �ݱ�

               } catch (Exception e1) {
                  e1.printStackTrace();// ����ó�� ���
               }

               int inputFromHour, inputToHour;
               inputFromHour = Integer.parseInt(FromTime[0]);
               inputToHour = Integer.parseInt(ToTime[0]);
               int inputFromMinute, inputToMinute;
               inputFromMinute = Integer.parseInt(FromTime[1]);
               inputToMinute = Integer.parseInt(ToTime[1]);
               //�ð����� �ð��� ���� ��Ʈ������ ����
              
               dvf.timetable[timetableIndex].setBounds(70 * timetableIndex, inputFromHour * 60 + inputFromMinute,
                     70, (inputToHour - inputFromHour) * 60 + inputToMinute - inputFromMinute);

               dvf.timetable[timetableIndex].setText(strEvent);
               Random random = new Random();
               int t = random.nextInt(15) % 5;
               int[] temp = new int[5];
               for (int j = 0; j < 5; j++) {
                  temp[j] = j;
               }         
               if (temp[t] == 0) {
                  dvf.timetable[timetableIndex].setBackground(Color.cyan);
               } else if (temp[t] == 1) {
                  dvf.timetable[timetableIndex].setBackground(Color.pink);
               } else if (temp[t] == 2) {
                  dvf.timetable[timetableIndex].setBackground(Color.yellow);
               } else if (temp[t] == 3) {
                  dvf.timetable[timetableIndex].setBackground(Color.gray.brighter());
               } else if (temp[t] == 4) {
                  dvf.timetable[timetableIndex].setBackground(Color.orange);
               }
               dvf.layeredPane.add(dvf.timetable[timetableIndex], 1, -1);
               //layeredpanel�� �ð�ǥ ���븦 �߰�, 1�� ���� �ִ� ����  
               timetableIndex = timetableIndex + 1;
               setVisible(false);
                
            } else if (dvf.buttonname == "Edit") {//������ ��ư�� Edit �϶�
               try {

                  FileReader fr = new FileReader("ToDo.txt");// ���� �б������ ����
                  BufferedReader br = new BufferedReader(fr);
                  String sCurrentLine;// ���� ����
                  String dummy = "";//���Ͽ��� ������ �޾� �� string ���� ����
                  while ((sCurrentLine = br.readLine()) != null) {// ���Ͽ��� �� ���� �޾� �ش�// ����� �������� ��� �ݺ�

                     String[] Str = sCurrentLine.split("[;]");// ���й��� ; �� ������ ����
                     if (Str[0].equals(String.valueOf(cal.Year + "_" + cal.SelectMonth + "_" + cal.SelectDay))
                           && Str[2].equals(dvf.table.getValueAt(dvf.table.getSelectedRow(), 1))
                           && Str[3].equals(dvf.table.getValueAt(dvf.table.getSelectedRow(), 2))) {
                        dummy += String.valueOf(cal.Year) + "_" + cal.SelectMonth + "_" + cal.SelectDay + ";"
                              + inputStr[0] + ";" + inputStr[1] + ";" + inputStr[2] + "\n";
                     }//���� ������ ��ġ�ϸ� ������ ������ �ٲ㼭 ���Ͽ� ����
                     else {
                        dummy += sCurrentLine + "\n"; // �������� �״�� ����
                     }

                  }

                  FileWriter fw = new FileWriter("ToDo.txt"); // ��������� ������ ����
                  BufferedWriter bw = new BufferedWriter(fw);
                  bw.write(dummy); // ������ ������ ���Ͽ� ����
                  // ���б�ȣ ;
                  // [�⵵_��_��];�ϷῩ��;�ð�:�ð�;����\n
                  bw.flush();
                  bw.close();

                  br.close();

                  dvf.defaultTableModel.setValueAt(inputStr[1], dvf.row, 1);
                  dvf.defaultTableModel.setValueAt(inputStr[2], dvf.row, 2);
                  dvf.timetable[dvf.row].setText(strEvent);

                  int inputFromHour, inputToHour;
                  inputFromHour = Integer.parseInt(FromTime[0]);
                  inputToHour = Integer.parseInt(ToTime[0]);
                  int inputFromMinute, inputToMinute;
                  inputFromMinute = Integer.parseInt(FromTime[1]);
                  inputToMinute = Integer.parseInt(ToTime[1]);

                  dvf.timetable[dvf.row].setBounds(70 * dvf.row, inputFromHour * 60 + inputFromMinute, 70,
                        (inputToHour - inputFromHour) * 60 + inputToMinute - inputFromMinute);
               } catch (Exception e1) {
                  e1.printStackTrace();
               }
               dvf.table.revalidate();
               dvf.scrollPane_1.setViewportView(dvf.table);
               // �ð�ǥ�� ���븦 ������ ���뿡 �°� �ٽ� �׸���.
               setVisible(false);

            }
         } catch (NotEventException ee) {
            JOptionPane.showMessageDialog(null, "input Event or Time", "time trouble", JOptionPane.ERROR_MESSAGE);
         } catch (TimeCollideException ee) {
            JOptionPane.showMessageDialog(null, "time is invalid", "time trouble", JOptionPane.ERROR_MESSAGE);
         } catch (TimeOverException ee) {
            JOptionPane.showMessageDialog(null, "time is over", "time trouble", JOptionPane.ERROR_MESSAGE);
         } catch (NoInputException ee) {
            JOptionPane.showMessageDialog(null, "input event or time", "No input", JOptionPane.ERROR_MESSAGE);
         } catch (Exception ee) {
            JOptionPane.showMessageDialog(null, "input as right way", "input error", JOptionPane.ERROR_MESSAGE);
         }// ������ ���� ó��

      }
      
   }

}