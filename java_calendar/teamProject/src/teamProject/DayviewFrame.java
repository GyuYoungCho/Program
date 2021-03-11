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

	private Calender cal; //Calender클래스
	   private DayviewDialog dvd; 
	   private DayviewDialog DayDlg; //DayviewDialog설정

	   private JPanel contentPane;
	   private JScrollPane scrollPane; //스크롤바
	   private JPanel panel;
	   private JButton btnAdd;
	   private JButton btnEdit;
	   private JButton btnDelete;
	   private JButton btnSave; //버튼들

	   public JTable table;
	   private String header[] = { "Complete", "Time", "To do" }; //table헤더
	   private Object schedule[][] = {}; //스케줄
	   private JCheckBox box;
	   public int row; //table의 row값

	   private JSplitPane splitPane; //테이블 나누기 위해
	   public JTable table_2; //실행시켜보면 왼쪽테이블 중 시간대가 나오는 테이블
	   public JLayeredPane layeredPane; //시간표를 그리기 위해
	   public JTable table_3; //시간표가 그려지는 테이블
	   public JButton[] timetable = new JButton[100]; //버튼 100개 정도 설정

	   public DefaultTableModel defaultTableModel = new DefaultTableModel(schedule, header);
	   //테이블 모델 설정
	   final JScrollPane scrollPane_1 = new JScrollPane(table); //스크롤에 테이블 넣음

	   public String buttonname;//버튼이름에 따라 다른 기능 실행

	   public DayviewFrame(final Calender cal, final DayviewDialog dvd) {
	      for (int i = 0; i < 100; i++) {
	         timetable[i] = new JButton();
	         timetable[i].setFont(new Font("바탕체", Font.BOLD, 9));
	      } //버튼의 text설정

	      this.cal = cal;
	      this.dvd = dvd; //다른 클래스의 필드와 메소드를 사용하기 위함
	      setTitle(cal.SelectMonth + " - " + cal.SelectDay);
	      setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	      setBounds(100, 100, 660, 457);
	      contentPane = new JPanel();
	      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	      setContentPane(contentPane);
	      contentPane.setLayout(null);

	      scrollPane = new JScrollPane();
	      scrollPane.setBounds(0, 10, 335, 399);
	      contentPane.add(scrollPane);//패널에 스크롤바

	      splitPane = new JSplitPane();
	      splitPane.setDividerSize(1);
	      scrollPane.setViewportView(splitPane);
	      //스크롤바에 splitpane넣음

	      table_2 = new JTable();

	      table_2.setRowHeight(60);
	      table_2.setModel(
	            new DefaultTableModel( //테이블 시간대 설정
	                  new Object[][] { { "00.00" }, { "01.00" }, { "02.00" }, { "03.00" }, { "04.00" }, { "05.00" },
	                        { "06.00" }, { "07.00" }, { "08.00" }, { "09.00" }, { "10.00" }, { "11.00" },
	                        { "12.00" }, { "13.00" }, { "14.00" }, { "15.00" }, { "16.00" }, { "17.00" },
	                        { "18.00" }, { "19.00" }, { "20.00" }, { "21.00" }, { "22.00" }, { "23.00" }, },
	                  new String[] { "New column" }));
	      table_2.getColumnModel().getColumn(0).setPreferredWidth(60);
	      splitPane.setLeftComponent(table_2); //splitpane의 왼쪽에 시간대table 넣음

	      layeredPane = new JLayeredPane();//JLayeredPane초기화
	      layeredPane.setLayout(null);

	      splitPane.setRightComponent(layeredPane); //splitPane오른쪽에 layeredPane넣음

	      table_3 = new JTable();
	      table_3.setShowVerticalLines(false);
	      table_3.setModel(new DefaultTableModel( //시간대 테이블 초기화
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

	      layeredPane.add(table_3, 0, -1);//layeredPane에 시간표 테이블 넣음

	      panel = new JPanel();
	      panel.setBounds(335, 10, 309, 399);
	      contentPane.add(panel);
	      panel.setLayout(null);

	      table = new JTable(defaultTableModel); //일의 체크, 시간대, 일의 내용이 나와있는 테이블
	      scrollPane_1.setViewportView(table);
	      table.getColumn("Complete").setCellRenderer(dcr);//셀 런더사용
	      table.getRowSelectionAllowed();

	      box = new JCheckBox();
	      box.setHorizontalAlignment(JLabel.CENTER); 
	      box.addActionListener(new ActionListener() {

	         @Override
	         public void actionPerformed(ActionEvent e) {
	            // TODO Auto-generated method stub
	            if (box.isSelected() == true) { //체크박스 의 핸들링
	               JOptionPane.showMessageDialog(null, "Congratulaion! You finish plan!");
	               try {
	                  FileReader fr = new FileReader("ToDo.txt"); //파일읽음
	                  BufferedReader br = new BufferedReader(fr);
	                  String sCurrentLine;
	                  String dummy = ""; //파일내용을 가져올 변수
	                  // DayDlg.inputStr[0]=box.isSelected():
	                  while ((sCurrentLine = br.readLine()) != null) {//라인을 계속 읽음
	                     String[] Str = sCurrentLine.split("[;]"); //;로 나눔

	                     if (Str[0].equals(String.valueOf(cal.Year + "_" + cal.SelectMonth + "_" + cal.SelectDay))
	                           && Str[2].equals(table.getValueAt(table.getSelectedRow(), 1))
	                           && Str[3].equals(table.getValueAt(table.getSelectedRow(), 2))) {//내용이 맞으면
	                        dummy += String.valueOf(cal.Year) + "_" + cal.SelectMonth + "_" + cal.SelectDay + ";"
	                              + DayDlg.inputStr[0] + ";" + DayDlg.inputStr[1] + ";" + DayDlg.inputStr[2]
	                              + "\n";
	                        //더미에 위의 형태로 내용을 저장
	                     } else {
	                        dummy += sCurrentLine + "\n"; //더미에 현재 라인을 저장
	                     }

	                  }

	                  FileWriter fw = new FileWriter("ToDo.txt"); //파일 씀
	                  BufferedWriter bw = new BufferedWriter(fw); 
	                  bw.write(dummy);
	                  // 구분기호 ;
	                  // [년도_월_일];완료여부;시간:시간;할일\n
	                  bw.flush(); //버퍼를 비운다
	                  bw.close(); 

	                  br.close(); //모두 담음

	               } catch (Exception e1) {//예외처리
	                  e1.printStackTrace();
	               }

	            } else if (box.isSelected() == false) {//선택되지 않을때

	               try {

	                  FileReader fr = new FileReader("ToDo.txt");
	                  BufferedReader br = new BufferedReader(fr); //파일읽음
	                  String sCurrentLine;
	                  String dummy = "";
	                  DayDlg.inputStr[0] = box.isSelected(); //박스선택형 변수
	                  while ((sCurrentLine = br.readLine()) != null) {//파일읽음 
	                     String[] Str = sCurrentLine.split("[;]");

	                     if (Str[0].equals(String.valueOf(cal.Year + "_" + cal.SelectMonth + "_" + cal.SelectDay))
	                           && Str[2].equals(table.getValueAt(table.getSelectedRow(), 1))
	                           && Str[3].equals(table.getValueAt(table.getSelectedRow(), 2))) 
	                     { //내용이 맞으면
	                        dummy += String.valueOf(cal.Year) + "_" + cal.SelectMonth + "_" + cal.SelectDay + ";"
	                              + DayDlg.inputStr[0] + ";" + DayDlg.inputStr[1] + ";" + DayDlg.inputStr[2]
	                              + "\n"; //다음과 같이 저장
	                     } else {
	                        dummy += sCurrentLine + "\n"; //더미에 라인을 저장
	                     }

	                  }

	                  FileWriter fw = new FileWriter("ToDo.txt");
	                  BufferedWriter bw = new BufferedWriter(fw);
	                  bw.write(dummy);
	                  // 구분기호 ;
	                  // [년도_월_일];완료여부;시간:시간;할일\n
	                  bw.flush();//버퍼를 비움
	                  bw.close();

	                  br.close();//닫음

	               } catch (Exception e1) { //예외처리
	                  e1.printStackTrace();
	               }

	            }
	         }

	      });
	      table.getColumn("Complete").setCellEditor(new DefaultCellEditor(box));
	      //일을 체크할 때 필요한 체크박스 헤더
	      btnAdd = new JButton("Add");//ADD버튼
	      btnAdd.addActionListener(new ActionListener() {//누를때
	         public void actionPerformed(ActionEvent arg0) {
	            buttonname = btnAdd.getActionCommand();//버튼 이름을 얻음
	            DayDlg = new DayviewDialog(cal, DayviewFrame.this);//DayviewDialog 생성
	            DayDlg.setVisible(true);
	         }
	      });

	      btnAdd.setBounds(44, 10, 90, 40);
	      panel.add(btnAdd);//패널에 버튼넣음

	      btnEdit = new JButton("Edit"); //Edit버튼
	      btnEdit.addActionListener(new ActionListener() {//불렀을때
	         public void actionPerformed(ActionEvent e) {
	            try {
	               if (table.isRowSelected(table.getSelectedRow()) == false) {
	                  throw new DoNotSelectException();
	                  //테이블의 내용을 선택하지 않았을때 예외처리 작용
	               }
	               DayDlg = new DayviewDialog(cal, DayviewFrame.this);//DayviewDialog생성
	               DayDlg.setVisible(true);
	               row = table.getSelectedRow();
	               buttonname = btnEdit.getActionCommand(); //버튼 이름을 얻음
	               DayDlg.textEvent.setText(String.valueOf(table.getValueAt(row, 2)));
	               //수정할 event를 가져옴 
	               String[] divideTime = String.valueOf(table.getValueAt(row, 1)).split("[:]");
	               //수정할 시간대를 가져옴
	               DayDlg.textFrom.setText(divideTime[0]);
	               DayDlg.textTo.setText(divideTime[1]);
	            } catch (DoNotSelectException ee) {
	               JOptionPane.showMessageDialog(null, "select event", "do not be selected",
	                     JOptionPane.ERROR_MESSAGE); //예외처리
	            }
         }
      });
      btnEdit.setBounds(170, 10, 90, 40);
      panel.add(btnEdit);// Edit 버튼을 panel 에 넛는다

      btnDelete = new JButton("delete");// 삭제버튼 생성
      btnDelete.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {

            try {
               if (table.isRowSelected(table.getSelectedRow()) == false) {// 선택된 열이 없을때 예외처리
                  throw new DoNotSelectException();
               }
               int reply = JOptionPane.showConfirmDialog(null, "Would delete this event?", "Delete Event",
                     JOptionPane.YES_NO_OPTION);// 정말 삭제할 것인지 재확인 하는 메세지 출력
               if (reply == JOptionPane.YES_OPTION) {
            	   try {

                       FileReader fr = new FileReader("ToDo.txt");
                       BufferedReader br = new BufferedReader(fr);// 읽기용을 텍스트 파일 열기
                       String sCurrentLine;//파일의 한줄의 내용일 읽어올 변수
                       String dummy = ""; // 파일의 내용을 담아올 String 변수 생성
                       while ((sCurrentLine = br.readLine()) != null) {//파일을 끝까지 읽을때까지 반복

                          String[] Str = sCurrentLine.split("[;]");//구분 문자에 맞게 날짜와 완료 상태, 시간과 사건을 분리하여 담는다. 

                          if (Str[0]
                                .equals(String.valueOf(cal.Year + "_" + cal.SelectMonth + "_" + cal.SelectDay))
                                && Str[2].equals(table.getValueAt(table.getSelectedRow(), 1))
                                && Str[3].equals(table.getValueAt(table.getSelectedRow(), 2))) {

                      // 해당하는 파일의 내용의 해당하는 line을 수정하여 dummy에 저장
                          } else {
                             dummy += sCurrentLine + "\n"; // 해당하는 줄을 제외한 나머지 줄을  dummy에 그대로 저장
                          }

                       }

                       FileWriter fw = new FileWriter("ToDo.txt");
                       BufferedWriter bw = new BufferedWriter(fw);
                       bw.write(dummy); // 수정된 내용을 파일에 쓴다.
                       // 구분기호 ;
                       // [년도_월_일];완료여부;시간:시간;할일\n
                       bw.flush();//버퍼를 비운다
                       bw.close();//버퍼 닫기

                       br.close();
                    } catch (Exception e1) {
                       e1.printStackTrace();
                    }//예외처리


                  int row = table.getSelectedRow();
                  defaultTableModel = (DefaultTableModel) table.getModel();
                  defaultTableModel.removeRow(row);
                  timetable[row].setEnabled(false);
                  timetable[row].setVisible(false);
               }
            } catch (DoNotSelectException ee) {
               JOptionPane.showMessageDialog(null, "select event", "do not be selected",
                     JOptionPane.ERROR_MESSAGE);

            }//예외처리
         }
      });
      btnDelete.setBounds(44, 64, 90, 40);
      panel.add(btnDelete);// 삭제버튼을 페널에 넣는다.

      scrollPane_1.setBounds(29, 127, 265, 199);
      panel.add(scrollPane_1); // 스크롤 패널 생성

      btnSave = new JButton("Save"); // Save 버튼 생성
      btnSave.addActionListener(new ActionListener() {// ActionLisner
         public void actionPerformed(ActionEvent e) {	//액션 수행
            if (table.getValueAt(0, 2).equals(null)) {
               JOptionPane.showMessageDialog(null, "no data", "no data", JOptionPane.ERROR_MESSAGE); // 에러메세지 출력
            } else {
               if (table.getRowCount() == 1) { //low 갯수가 1이면
                  for (int i = 0; i < 42; i++) {
                     if (cal.buttemp[i].getText().equals(cal.SelectDay)) {
                        cal.buttemp[i].setText("<html>" + cal.buttemp[i].getText() + "<br/>"
                              + table.getValueAt(0, 2) + "</html>");// Calender의 해당 날짜의 버튼에 일정내용을 쓰기
                        break;
                     }
                  }

               } else if (table.getRowCount() > 1) {
                  for (int i = 0; i < 42; i++) {
                     if (cal.buttemp[i].getText().equals(cal.SelectDay)) { // Calender 버튼의 날짜와 선택된 날짜가 같으면
                        cal.buttemp[i].setText("<html>" + cal.buttemp[i].getText() + "<br/>"
                              + table.getValueAt(0, 2) + "<br/>More...</html>");	// Calender의 해당 날짜의 버튼에 일정내용을 쓰기
                        JOptionPane.showMessageDialog(null, "Save at Calendar!");	// calecdar에 저장되었다는 확인 메세지 출력
                        break;
                     }
                  }
               }
            }
         }
      });

      btnSave.setBounds(170, 64, 90, 40);
      panel.add(btnSave); // Save 버튼을 panel 에 추가

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

   public class DoNotSelectException extends Exception {

   }
}