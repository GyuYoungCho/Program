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
// 다이얼로그 -> DayviewFrame 에서 Add 와 Edit 을 눌렀을 때, 일정에 추가시키거나 수정시키는 다이얼로그 
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
      this.dvf = dvf;	// 다른 클래스 내의 메소드를 이용하기 위함
      setTitle(cal.SelectMonth + " - " + cal.SelectDay); //제목 : 달 - 일
      setBounds(100, 100, 300, 300);// 다이얼로그 크기 지정
      setDefaultCloseOperation(HIDE_ON_CLOSE);
      getContentPane().setLayout(new BorderLayout());
      contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
      getContentPane().add(contentPanel, BorderLayout.CENTER);
      contentPanel.setLayout(null);// contentPanel 설정

      lblNewLabel = new JLabel("Event"); // Event lable 추가하여 각 텍스트 필드창 구별
      lblNewLabel.setBounds(12, 56, 40, 15);
      contentPanel.add(lblNewLabel); 
      {
         JLabel lblNewLabel_1 = new JLabel("From");// From lable 추가하여 각 텍스트 필드창 구별
         lblNewLabel_1.setBounds(12, 93, 40, 15);
         contentPanel.add(lblNewLabel_1);	
      }
      {
         JLabel lblNewLabel_2 = new JLabel("To");// To lable 추가하여 각 텍스트 필드창 구별
         lblNewLabel_2.setBounds(12, 132, 40, 15);
         contentPanel.add(lblNewLabel_2); // 일정 끝나는 시간 입력창
      }

      textEvent = new JTextField();
      textEvent.setBounds(53, 53, 200, 21);
      contentPanel.add(textEvent);
      textEvent.setColumns(10);
   // event 일정내용을  입력하는 textfield 창 생성

      textFrom = new JTextField();
      textFrom.setBounds(53, 90, 200, 21);
      contentPanel.add(textFrom);
      textFrom.setColumns(10);
   // 일정 시작 시간을  입력하는 textfield 창 생성
      
      textTo = new JTextField();
      textTo.setBounds(53, 129, 200, 21);
      contentPanel.add(textTo);
      textTo.setColumns(10);
   // 일정 끝나는 시간을  입력하는 textfield 창 생성
      
      lblNewLabel_3 = new JLabel("Please input time xx.xx"); // Lable 창을 통해서 시간을 입력하는 형식을 알려준다.
      lblNewLabel_3.setBounds(12, 183, 189, 15);
      contentPanel.add(lblNewLabel_3);
      {
         JPanel buttonPane = new JPanel();	// buttons 들을 모아넣을 panel 생성 OK 버튼과 Cancel버튼이 있다.
         buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER)); // FlowLayout 설정(버튼을 가운데로)
         getContentPane().add(buttonPane, BorderLayout.SOUTH); // contentPanel 아래에 buttonpanel 넣기
         {
            JButton okButton = new JButton("OK");// OK 버튼 생성
            okButton.addActionListener(this); // addActionListener를 통해서 ok button handling
            okButton.setActionCommand("OK"); //액션 커맨드 설정
            buttonPane.add(okButton);// 패널에 버튼 추가
            getRootPane().setDefaultButton(okButton);
         }
         {
            JButton cancelButton = new JButton("Cancel");// Cancel 버튼 생성
            cancelButton.addActionListener(new ActionListener() {// addActionListener를 통해서 Cancel button handling
               public void actionPerformed(ActionEvent e) { 
                  setVisible(false);	//다이알로그를 없애준다.
               }
            });
            cancelButton.setActionCommand("Cancel");//액션 커맨드 설정
            buttonPane.add(cancelButton);// 패널에 버튼 추가
         }
      }

   }

   DefaultTableCellRenderer dcr = new DefaultTableCellRenderer() {// table 내에서 checkbox를 다루는데 사용
	      public Component getTableCellRendererComponent // 셀렌더러, table내에서 사용자가 원하는 cell에 component를 넣는다.
	      (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	         JCheckBox box = new JCheckBox(); // checkbox 생성
	         box.setSelected(((Boolean) value).booleanValue());
	         box.setHorizontalAlignment(JLabel.CENTER);// 가운데 배열
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
         strEvent = textEvent.getText();// 일정 내용을 입력한 event 텍스트필드창에 입력한 문자를 copy
         strFrom = textFrom.getText();//시작시간 입력하는 텍스트필드창에 입력한 문자를 copy
         strTo = textTo.getText();//끝나는 시간 입력하는 텍스트필드창에 입력한 문자를 copy
         String[] FromTime = textFrom.getText().split("[.]");// 시작시간의 시각 과 분 부분을 분리하여 저장
         String[] ToTime = textTo.getText().split("[.]");// 끝나는 시간의 시각 과 분 부분을 분리하여 저장

         try {
            if (textEvent.getText() == "" || textFrom.getText() == "" || textTo.getText() == "") {
               throw new NotEventException();
            }
            if ((Integer.parseInt(FromTime[0]) > Integer.parseInt(ToTime[0]))
                  || ((Integer.parseInt(FromTime[0]) == Integer.parseInt(ToTime[0]))
                        && (Integer.parseInt(FromTime[1]) > Integer.parseInt(ToTime[1])))) {
               throw new TimeCollideException();//시작시간이 끝나는 시간보다 나중일때 예외처리
            }

            if (Integer.parseInt(FromTime[0]) > 24 || Integer.parseInt(ToTime[0]) > 24
                  || Integer.parseInt(FromTime[1]) > 60 || Integer.parseInt(ToTime[1]) > 60) {
               throw new TimeOverException(); // 시간의 형식이 맞지 않을 때 exeption처리
            }
            if (strEvent == null || strFrom == null || strTo == null) {
               throw new NoInputException();
            }// 각각의 exeption 처리

            inputStr[1] = strFrom + ":" + strTo;//시작하는 시간과 끝나는 시간을 :로 구분한 String type의 변수를 Object[] 객체에 저장
            inputStr[2] = strEvent;// event를 담은  String type의 변수를 Object[] 객체에 저장

            if (dvf.buttonname == "Add") {//일정 추가 버튼
               inputStr[0] = false;//check 박스의 값을 false로 저장
               dvf.defaultTableModel.addRow(inputStr); //defaultTableModel을 이용해 table 에 내용 저장 

               try {
                  FileWriter fw = new FileWriter("ToDo.txt", true);
                  BufferedWriter bw = new BufferedWriter(fw);//파일 입력 쓰기용, append 모드로 열기
                  bw.write(String.valueOf(cal.Year) + "_" + cal.SelectMonth + "_" + cal.SelectDay + ";"
                        + inputStr[0] + ";" + inputStr[1] + ";" + inputStr[2] + "\n");            
                  // 구분기호 ;
                  // 년도_월_일;완료여부;시간:시간;할일\n 을 파일에 입력한다.
                  bw.flush();//버퍼를 비운다.
                  bw.close();//파일 버퍼 닫기

               } catch (Exception e1) {
                  e1.printStackTrace();// 예외처리 출력
               }

               int inputFromHour, inputToHour;
               inputFromHour = Integer.parseInt(FromTime[0]);
               inputToHour = Integer.parseInt(ToTime[0]);
               int inputFromMinute, inputToMinute;
               inputFromMinute = Integer.parseInt(FromTime[1]);
               inputToMinute = Integer.parseInt(ToTime[1]);
               //시간에서 시각과 분을 인트형으로 저장
              
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
               //layeredpanel에 시간표 막대를 추가, 1은 위에 있는 정도  
               timetableIndex = timetableIndex + 1;
               setVisible(false);
                
            } else if (dvf.buttonname == "Edit") {//눌러진 버튼이 Edit 일때
               try {

                  FileReader fr = new FileReader("ToDo.txt");// 파일 읽기용으로 열기
                  BufferedReader br = new BufferedReader(fr);
                  String sCurrentLine;// 현재 라인
                  String dummy = "";//파일에서 내용을 받아 줄 string 변수 생성
                  while ((sCurrentLine = br.readLine()) != null) {// 파일에서 한 줄을 받아 준다// 끝까기 갈때까지 계속 반복

                     String[] Str = sCurrentLine.split("[;]");// 구분문자 ; 로 나누어 저장
                     if (Str[0].equals(String.valueOf(cal.Year + "_" + cal.SelectMonth + "_" + cal.SelectDay))
                           && Str[2].equals(dvf.table.getValueAt(dvf.table.getSelectedRow(), 1))
                           && Str[3].equals(dvf.table.getValueAt(dvf.table.getSelectedRow(), 2))) {
                        dummy += String.valueOf(cal.Year) + "_" + cal.SelectMonth + "_" + cal.SelectDay + ";"
                              + inputStr[0] + ";" + inputStr[1] + ";" + inputStr[2] + "\n";
                     }//일정 내용이 일치하면 수정할 내용을 바꿔서 파일에 저장
                     else {
                        dummy += sCurrentLine + "\n"; // 나머지는 그대로 저장
                     }

                  }

                  FileWriter fw = new FileWriter("ToDo.txt"); // 쓰기용으로 파일을 열기
                  BufferedWriter bw = new BufferedWriter(fw);
                  bw.write(dummy); // 수저된 내용을 파일에 저장
                  // 구분기호 ;
                  // [년도_월_일];완료여부;시간:시간;할일\n
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
               // 시간표의 막대를 수정된 내용에 맞게 다시 그린다.
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
         }// 각각의 예외 처리

      }
      
   }

}