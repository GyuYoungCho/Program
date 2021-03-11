package teamProject;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Random;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JComboBox;

public class Calender extends JFrame implements ActionListener {

   String[] monthname = { "January", "Feburuary", "March", "April", "May", "June", "July", "August", "September",
         "October", "Nomember", "December" }; //���� �̸����� ��Ʈ�� �迭��

   JPanel up = new JPanel();
   JPanel down = new JPanel();
   JPanel center = new JPanel(); //�� �Ʒ� �߰��� ���� �г�

   private DayviewFrame dayview; //DayviewFrame class�� ����
   private DayviewDialog daydia; //DayviewDialog class�� ����
   public int Month = 6; //�̹� ��
   public int Year = 2016; //����
   private int Day = 1; // ���� ù��
   private static int timetableIndex = 0; //������ ���� �� �ʿ�

   
   public String SelectMonth = ""; 
   public String SelectDay = ""; //�ٸ� Ŭ�������� �� �������̴�
   public JButton[] buttemp = new JButton[42]; //�޷¿� ���� ��ư���̴�
   
   JLabel mon = new JLabel("Mon", JLabel.CENTER);
   JLabel tue = new JLabel("Tue", JLabel.CENTER);
   JLabel wed = new JLabel("Wed", JLabel.CENTER);
   JLabel thu = new JLabel("Thu", JLabel.CENTER);
   JLabel fri = new JLabel("Fri", JLabel.CENTER);
   JLabel sat = new JLabel("Sat", JLabel.CENTER);
   JLabel sun = new JLabel("Sun", JLabel.CENTER); //�� ������ ��

   GregorianCalendar cal = new GregorianCalendar(Year, Month - 1, 1);
   //GregorianCalendar(�⵵,��-1,1)�� Ŭ���� ����
   int numberDayInMonth = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
   //�ش��ϴ� ���� ���������� ���� �� �ִ�
   int firstDayOfTheWeek = cal.get(GregorianCalendar.DAY_OF_WEEK);
   // ù���� ������ ���ڸ� ���� �� �� �ִ�.

   private JComboBox comboBox = new JComboBox(monthname);
   private JComboBox comboBox_1 = new JComboBox();
   //ppt������  ��ư�� �󺧷� �޷��� �ѱ� �� �־����� �츮�� combobox�� �غ���� �ߴ�.
   public ArrayList<String> yeararray = new ArrayList<String>();
   //���� arraylist
   public Calender() {
      
      comboBox.setSize(50, 5);
      comboBox.setSelectedItem(monthname[Month - 1]);
      // ���� �̸����� ����
      for (int i = 2000; i <= 2100; i++) {
         yeararray.add(String.valueOf(i));
      } //���� arraylist�� 2000~2100����� �߰�
      comboBox_1 = new JComboBox(yeararray.toArray(new String[yeararray.size()]));
      comboBox_1.setSelectedIndex(Year - 2000);
      //������ ����

      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setTitle("Calendars");
      getContentPane().setLayout(new BorderLayout());//borderLayout ���
      for (int i = 0; i < 42; i++) {
         buttemp[i] = new JButton("");//��ư �ʱ�ȭ
         buttemp[i].setVerticalAlignment(JButton.TOP);
         buttemp[i].setHorizontalAlignment(JButton.RIGHT); //�޷��� ���ڸ� ������ ���� �ϱ� ����
         if (i < firstDayOfTheWeek - 1 || i > firstDayOfTheWeek + numberDayInMonth - 2){
         // GregorianCalendar���� ������ firstDayOfTheWeek�� numberDayInMonth �̿��Ͽ� ��ư�޷¹迭�� ���� �� �ִ�.
            buttemp[i].setText("");
            buttemp[i].setEnabled(false);
         } else {
            buttemp[i].setText(String.valueOf(Day));
            Day++;
         }
         if (i % 7 == 0) { //�Ͽ����� ������
            buttemp[i].setBackground(Color.red.brighter());
         }
         buttemp[i].addActionListener(this);
      }

      sun.setForeground(Color.RED);
      center.add(sun);
      center.add(mon);
      center.add(tue);
      center.add(wed);
      center.add(thu);
      center.add(fri);
      center.add(sat); //���� ���� ����
      
      for (int i = 0; i < 42; i++) { //��ư�� �гξȿ��� ����
         center.add(buttemp[i]);
        }

      getContentPane().add(up, BorderLayout.NORTH);
      getContentPane().add(down, BorderLayout.SOUTH);
      getContentPane().add(center, BorderLayout.CENTER); //�г��� �� ��� �Ʒ��� ����

      center.setLayout(new GridLayout(7, 7)); //GridLayout���� �޷¸�� �����
      up.setLayout(new GridLayout(1, 2));

      comboBox.addActionListener(new ActionListener() {
         //combobox�� actionlistener�� ���� combobox(��) handling
         public void actionPerformed(ActionEvent e) {//event�� �߻� 
            Month = comboBox.getSelectedIndex() + 1; //Month�� ����
            cal.set(Year, Month - 1, 1);

            numberDayInMonth = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
            firstDayOfTheWeek = cal.get(GregorianCalendar.DAY_OF_WEEK);
          //GregorianCalendar�� ���Ӱ� �ʱ�ȭ

            Day = 1;

            for (int i = 0; i < 42; i++) {
               if (i < firstDayOfTheWeek - 1 || i > firstDayOfTheWeek + numberDayInMonth - 2) {
                  buttemp[i].setText("");
                  buttemp[i].setEnabled(false); //��ư ��� ���
               } else {
                  buttemp[i].setEnabled(true);
                  buttemp[i].setText("" + Day);
                  Day++;
               } //��ư 1�Ͼ� �����ϸ鼭 �޷�ó�� ����
            }
         }
      });

      comboBox_1.addActionListener(new ActionListener() {
       //combobox�� actionlistener�� ���� combobox_1(����) handling
         public void actionPerformed(ActionEvent e) { //event�߻�

            Year = Integer.parseInt(String.valueOf(comboBox_1.getSelectedItem()));
            cal.set(Year, Month - 1, 1);

            numberDayInMonth = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
            firstDayOfTheWeek = cal.get(GregorianCalendar.DAY_OF_WEEK);
          //GregorianCalendar�� ���Ӱ� �ʱ�ȭ

            Day = 1;

            for (int i = 0; i < 42; i++) {
               if (i < firstDayOfTheWeek - 1 || i > firstDayOfTheWeek + numberDayInMonth - 2) {
                  buttemp[i].setText("");
                  buttemp[i].setEnabled(false);
               } else {
                  buttemp[i].setEnabled(true);
                  buttemp[i].setText("" + Day);
                  Day++;
               }
            }
         }
      });

      up.add(comboBox);
      up.add(comboBox_1); //�޺��ڽ��� ����

      this.setVisible(true);
      this.setSize(500, 500);
   }

   public void actionPerformed(ActionEvent e) {
      String s = e.getActionCommand();//��ư�� actioncommand�� ����

      if (s.compareToIgnoreCase("0") >= 0 && s.compareToIgnoreCase("99") <= 0) {
         //compareToIgnoreCase�� ���ڸ����� text�� ���� ��ư���� Ȱ��ȭ
         SelectDay = s; 
         SelectMonth = monthname[Month - 1];//�ٸ� Ŭ������ �� ���� ����
         dayview = new DayviewFrame(Calender.this, daydia); //DayviewFrame����
         dayview.setVisible(true);
      }
      try {
            FileWriter fw = new FileWriter("ToDo.txt", true);//���� ����
            BufferedWriter bw = new BufferedWriter(fw); //���Ͽ� ����
            FileReader fr;
            fr = new FileReader("ToDo.txt");
            BufferedReader br = new BufferedReader(fr); //���� ����
            String sCurrentLine; //���� ������ ��Ÿ��

            while ((sCurrentLine = br.readLine()) != null) { //������ ���θ��� ����
               Object[] inputStr = new Object[4];
               String[] Str = sCurrentLine.split("[;]");//;�� �����͸� ���� ������

               if (Str[0].equals(String.valueOf(this.Year + "_" + this.SelectMonth + "_" + this.SelectDay))) {
                  boolean checkval = false; 
                  System.out.println(Str[1]);
                  if (Str[1].equals("true")) {
                     checkval = true; //DayviewFrame üũ�ڽ� �ڵ鸵
                  }
                  inputStr[0] = checkval; //üũ�ڽ� ����
                  inputStr[1] = Str[2]; //�ð���
                  inputStr[2] = Str[3]; //��
                  dayview.defaultTableModel.addRow(inputStr);
                  //���̺�𵨿� ����
                  
                  for(int i =0 ;i<42;i++){
                  if(buttemp[i].getText()==this.SelectDay){
                     buttemp[i].setText("<html>"+buttemp[i].getText()+"<br/>"+inputStr[2]+"</html>");
                  }
               } //���� ��ư�� ������ �޷��� ��ư�� ��Ÿ��
                  
                  String[] Time = Str[2].split("[:]"); 
                  String[] FromTime = Time[0].split("[.]");
                  String[] ToTime = Time[1].split("[.]");
                  //�ð��븦 :�� .���� ����
                  
                  int inputFromHour, inputToHour;
                  inputFromHour = Integer.parseInt(FromTime[0]);//���� hour
                  inputToHour = Integer.parseInt(ToTime[0]); //�� hour
                  int inputFromMinute, inputToMinute; 
                  inputFromMinute = Integer.parseInt(FromTime[1]); //���� minute
                  inputToMinute = Integer.parseInt(ToTime[1]); //��minute

                  dayview.timetable[timetableIndex].setBounds(70 * timetableIndex, inputFromHour * 60 + inputFromMinute,
                        70, (inputToHour - inputFromHour) * 60 + inputToMinute - inputFromMinute);
                  //setBounds�� ���� �ð�ǥ ������ ��ġ ����

                  dayview.timetable[timetableIndex].setText(Str[3]);
                  Random random = new Random();
                  int t = random.nextInt(15) % 5;
                  int[] temp = new int[5];
                  for (int j = 0; j < 5; j++) {
                     temp[j] = j;
                  }
                  if (temp[t] == 0) { //������ �������� �����Ͽ� �ð�ǥ ������ background color�� ��
                     dayview.timetable[timetableIndex].setBackground(Color.cyan);
                  } else if (temp[t] == 1) {
                     dayview.timetable[timetableIndex].setBackground(Color.pink);
                  } else if (temp[t] == 2) {
                     dayview.timetable[timetableIndex].setBackground(Color.yellow);
                  } else if (temp[t] == 3) {
                     dayview.timetable[timetableIndex].setBackground(Color.gray.brighter());
                  } else if (temp[t] == 4) {
                     dayview.timetable[timetableIndex].setBackground(Color.orange);
                  }
                  dayview.layeredPane.add(dayview.timetable[timetableIndex], 1, -1);
                  //layeredPane���� �ð�ǥ���븦 �߰�. 1�� ���� �ִ� ����
                  //�ٸ� Ŭ�������� table�� 0���� �����Ǿ� ���̺����� �ö��

                  timetableIndex = timetableIndex + 1;

               }

            }
         } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();//file�� ã�� ��������,���� ��������
         } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace(); //�о���̴°ſ� �����Ҷ�
         }

   }

   DefaultTableCellRenderer dcr = new DefaultTableCellRenderer() { 
      //���̺� üũ�ڽ��� �ֱ� ���� �޼ҵ�
      public Component getTableCellRendererComponent // ��������
      (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
         JCheckBox box = new JCheckBox();
         box.setSelected(((Boolean) value).booleanValue());
         box.setHorizontalAlignment(JLabel.CENTER);
         return box;
      }
   };

   public static void main(String[] args) {
      Calender cd = new Calender(); //������ �ߵ�
   }
}