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
         "October", "Nomember", "December" }; //달의 이름들을 스트링 배열로

   JPanel up = new JPanel();
   JPanel down = new JPanel();
   JPanel center = new JPanel(); //위 아래 중간에 넣을 패널

   private DayviewFrame dayview; //DayviewFrame class를 선언
   private DayviewDialog daydia; //DayviewDialog class를 선언
   public int Month = 6; //이번 달
   public int Year = 2016; //올해
   private int Day = 1; // 달의 첫날
   private static int timetableIndex = 0; //파일을 읽을 때 필요

   
   public String SelectMonth = ""; 
   public String SelectDay = ""; //다른 클래스에서 쓸 변수들이다
   public JButton[] buttemp = new JButton[42]; //달력에 넣을 버튼들이다
   
   JLabel mon = new JLabel("Mon", JLabel.CENTER);
   JLabel tue = new JLabel("Tue", JLabel.CENTER);
   JLabel wed = new JLabel("Wed", JLabel.CENTER);
   JLabel thu = new JLabel("Thu", JLabel.CENTER);
   JLabel fri = new JLabel("Fri", JLabel.CENTER);
   JLabel sat = new JLabel("Sat", JLabel.CENTER);
   JLabel sun = new JLabel("Sun", JLabel.CENTER); //각 요일의 라벨

   GregorianCalendar cal = new GregorianCalendar(Year, Month - 1, 1);
   //GregorianCalendar(년도,달-1,1)로 클래스 선언
   int numberDayInMonth = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
   //해당하는 달의 마지막날을 구할 수 있다
   int firstDayOfTheWeek = cal.get(GregorianCalendar.DAY_OF_WEEK);
   // 첫날의 요일을 숫자를 통해 알 수 있다.

   private JComboBox comboBox = new JComboBox(monthname);
   private JComboBox comboBox_1 = new JComboBox();
   //ppt에서는  버튼과 라벨로 달력을 넘길 수 있었지만 우리는 combobox로 해보기로 했다.
   public ArrayList<String> yeararray = new ArrayList<String>();
   //연도 arraylist
   public Calender() {
      
      comboBox.setSize(50, 5);
      comboBox.setSelectedItem(monthname[Month - 1]);
      // 달의 이름들을 선택
      for (int i = 2000; i <= 2100; i++) {
         yeararray.add(String.valueOf(i));
      } //연도 arraylist에 2000~2100년까지 추가
      comboBox_1 = new JComboBox(yeararray.toArray(new String[yeararray.size()]));
      comboBox_1.setSelectedIndex(Year - 2000);
      //연도를 선택

      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setTitle("Calendars");
      getContentPane().setLayout(new BorderLayout());//borderLayout 사용
      for (int i = 0; i < 42; i++) {
         buttemp[i] = new JButton("");//버튼 초기화
         buttemp[i].setVerticalAlignment(JButton.TOP);
         buttemp[i].setHorizontalAlignment(JButton.RIGHT); //달력의 숫자를 오른쪽 위로 하기 위해
         if (i < firstDayOfTheWeek - 1 || i > firstDayOfTheWeek + numberDayInMonth - 2){
         // GregorianCalendar에서 선언한 firstDayOfTheWeek와 numberDayInMonth 이용하여 버튼달력배열을 만들 수 있다.
            buttemp[i].setText("");
            buttemp[i].setEnabled(false);
         } else {
            buttemp[i].setText(String.valueOf(Day));
            Day++;
         }
         if (i % 7 == 0) { //일요일은 빨갛게
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
      center.add(sat); //라벨을 위에 넣음
      
      for (int i = 0; i < 42; i++) { //버튼을 패널안에다 넣음
         center.add(buttemp[i]);
        }

      getContentPane().add(up, BorderLayout.NORTH);
      getContentPane().add(down, BorderLayout.SOUTH);
      getContentPane().add(center, BorderLayout.CENTER); //패널을 위 가운데 아래에 넣음

      center.setLayout(new GridLayout(7, 7)); //GridLayout으로 달력모양 만들기
      up.setLayout(new GridLayout(1, 2));

      comboBox.addActionListener(new ActionListener() {
         //combobox의 actionlistener를 통해 combobox(달) handling
         public void actionPerformed(ActionEvent e) {//event가 발생 
            Month = comboBox.getSelectedIndex() + 1; //Month를 설정
            cal.set(Year, Month - 1, 1);

            numberDayInMonth = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
            firstDayOfTheWeek = cal.get(GregorianCalendar.DAY_OF_WEEK);
          //GregorianCalendar를 새롭게 초기화

            Day = 1;

            for (int i = 0; i < 42; i++) {
               if (i < firstDayOfTheWeek - 1 || i > firstDayOfTheWeek + numberDayInMonth - 2) {
                  buttemp[i].setText("");
                  buttemp[i].setEnabled(false); //버튼 기능 상실
               } else {
                  buttemp[i].setEnabled(true);
                  buttemp[i].setText("" + Day);
                  Day++;
               } //버튼 1일씩 증가하면서 달력처럼 만듬
            }
         }
      });

      comboBox_1.addActionListener(new ActionListener() {
       //combobox의 actionlistener를 통해 combobox_1(연도) handling
         public void actionPerformed(ActionEvent e) { //event발생

            Year = Integer.parseInt(String.valueOf(comboBox_1.getSelectedItem()));
            cal.set(Year, Month - 1, 1);

            numberDayInMonth = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
            firstDayOfTheWeek = cal.get(GregorianCalendar.DAY_OF_WEEK);
          //GregorianCalendar를 새롭게 초기화

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
      up.add(comboBox_1); //콤보박스를 넣음

      this.setVisible(true);
      this.setSize(500, 500);
   }

   public void actionPerformed(ActionEvent e) {
      String s = e.getActionCommand();//버튼의 actioncommand를 얻음

      if (s.compareToIgnoreCase("0") >= 0 && s.compareToIgnoreCase("99") <= 0) {
         //compareToIgnoreCase로 두자리수의 text를 가진 버튼들을 활성화
         SelectDay = s; 
         SelectMonth = monthname[Month - 1];//다른 클래스에 쓸 변수 저장
         dayview = new DayviewFrame(Calender.this, daydia); //DayviewFrame생성
         dayview.setVisible(true);
      }
      try {
            FileWriter fw = new FileWriter("ToDo.txt", true);//파일 생성
            BufferedWriter bw = new BufferedWriter(fw); //파일에 적음
            FileReader fr;
            fr = new FileReader("ToDo.txt");
            BufferedReader br = new BufferedReader(fr); //파일 읽음
            String sCurrentLine; //현재 라인을 나타냄

            while ((sCurrentLine = br.readLine()) != null) { //파일을 라인마다 읽음
               Object[] inputStr = new Object[4];
               String[] Str = sCurrentLine.split("[;]");//;로 데이터를 나눠 가져옴

               if (Str[0].equals(String.valueOf(this.Year + "_" + this.SelectMonth + "_" + this.SelectDay))) {
                  boolean checkval = false; 
                  System.out.println(Str[1]);
                  if (Str[1].equals("true")) {
                     checkval = true; //DayviewFrame 체크박스 핸들링
                  }
                  inputStr[0] = checkval; //체크박스 상태
                  inputStr[1] = Str[2]; //시간대
                  inputStr[2] = Str[3]; //일
                  dayview.defaultTableModel.addRow(inputStr);
                  //테이블모델에 넣음
                  
                  for(int i =0 ;i<42;i++){
                  if(buttemp[i].getText()==this.SelectDay){
                     buttemp[i].setText("<html>"+buttemp[i].getText()+"<br/>"+inputStr[2]+"</html>");
                  }
               } //일이 버튼을 누를때 달력의 버튼에 나타남
                  
                  String[] Time = Str[2].split("[:]"); 
                  String[] FromTime = Time[0].split("[.]");
                  String[] ToTime = Time[1].split("[.]");
                  //시간대를 :와 .으로 나눔
                  
                  int inputFromHour, inputToHour;
                  inputFromHour = Integer.parseInt(FromTime[0]);//시작 hour
                  inputToHour = Integer.parseInt(ToTime[0]); //끝 hour
                  int inputFromMinute, inputToMinute; 
                  inputFromMinute = Integer.parseInt(FromTime[1]); //시작 minute
                  inputToMinute = Integer.parseInt(ToTime[1]); //끝minute

                  dayview.timetable[timetableIndex].setBounds(70 * timetableIndex, inputFromHour * 60 + inputFromMinute,
                        70, (inputToHour - inputFromHour) * 60 + inputToMinute - inputFromMinute);
                  //setBounds를 통해 시간표 막대의 위치 지정

                  dayview.timetable[timetableIndex].setText(Str[3]);
                  Random random = new Random();
                  int t = random.nextInt(15) % 5;
                  int[] temp = new int[5];
                  for (int j = 0; j < 5; j++) {
                     temp[j] = j;
                  }
                  if (temp[t] == 0) { //색깔을 랜덤으로 설정하여 시간표 막대의 background color가 됨
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
                  //layeredPane위에 시간표막대를 추가. 1은 위에 있는 정도
                  //다른 클래스에서 table이 0으로 설정되어 테이블위에 올라옴

                  timetableIndex = timetableIndex + 1;

               }

            }
         } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();//file을 찾지 못했을때,쓰지 못했을때
         } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace(); //읽어들이는거에 실패할때
         }

   }

   DefaultTableCellRenderer dcr = new DefaultTableCellRenderer() { 
      //테이블에 체크박스를 넣기 위한 메소드
      public Component getTableCellRendererComponent // 셀렌더러
      (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
         JCheckBox box = new JCheckBox();
         box.setSelected(((Boolean) value).booleanValue());
         box.setHorizontalAlignment(JLabel.CENTER);
         return box;
      }
   };

   public static void main(String[] args) {
      Calender cd = new Calender(); //생성자 발동
   }
}