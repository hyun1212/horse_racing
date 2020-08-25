package com.AKL;

import java.sql.*;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;


public class AKLGame extends JFrame implements ActionListener {
	public String[] horse = { "bet", "trojan", "horseback", "pegasus" }; 
	final int bet = 1;
	final int trojan = 2;
	final int horseback = 3;
	final int pegasus = 4;
	JPanel p1, p2, p3, p4, p5 ,p6;
	JLabel l1, l2, l3, balance ;
	JTextField betting = new JTextField(5);
	JRadioButton[] runner = new JRadioButton[4];
	JButton b1,b2,b3;
	ImageIcon[] icon = new ImageIcon[4];
	JLabel[] iconlabel = new JLabel[4];
	JLabel[] ranklabel = new JLabel[4];
	String[] rank = new String[4];
	int tmp;
	int win;
	int money = 10000;
	int bet_money = 0;
	Connection conn  = Jake();
	String OpenSql = "select win from HORSE_DB where HORSE_ID = ?";
	String SaveSql;
	
	// DB connection
	public static Connection Jake() {
		String user = "kms";
		String pw = "1234";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String driver = "oracle.jdbc.driver.OracleDriver";
		
		Connection conn = null;
		
		try {
			Class.forName(driver);
			System.out.println("오라클 드라이버 적재 성공");
			conn = DriverManager.getConnection(url,user,pw);
			System.out.println("디비 연동 성공");
		}
		catch(SQLException e) {
			System.out.println("SQL Error");
		}
		catch(ClassNotFoundException e) {
			System.out.println("Class Error");
		}
//		if(conn != null) {
//		      try {
//		         conn.close();
//		      }
//		      catch(SQLException e) { 
//		         e.printStackTrace();
//		      }
//		}
		return conn;
	}
	
	public AKLGame() {
		// Panel Factory
		p1 = new JPanel(); p2 = new JPanel(); p3 = new JPanel();
		//p6 = new JPanel(); p6 = new JPanel();
		b1 = new JButton("Enter"); b2 = new JButton("Close");
		l1 = new JLabel("Your balance ->",SwingConstants.CENTER);
		l2 = new JLabel("Your Money",SwingConstants.CENTER);
		l3 = new JLabel("How much do you wanna bet?",SwingConstants.CENTER);
		balance = new JLabel("10000",SwingConstants.CENTER);
		
		p3.setLayout(new GridLayout(1,2));
		p1.setLayout(new GridLayout(2,4));
		p2.setLayout(new GridLayout(2,2));
		ButtonGroup group = new ButtonGroup();
	
		for(int i=0;i<4;i++) {
			runner[i] = new JRadioButton(horse[i]);
			iconlabel[i] = new JLabel();
			icon[i] = new ImageIcon(horse[i]+".png");
			group.add(runner[i]);
			p1.add(runner[i]);
			iconlabel[i].setIcon(icon[i]);
			p1.add(iconlabel[i]);
			runner[i].addActionListener(this);
		}
		
		Border border3 = BorderFactory.createTitledBorder("Info");
		p3.setBorder(border3);
		Border border1 = BorderFactory.createTitledBorder("Horses");
		p1.setBorder(border1);
		Border border2 = BorderFactory.createTitledBorder("Menu");
		p2.setBorder(border2);
		
		b1.addActionListener(this);
		b2.addActionListener(this);
		
		p3.add(l1); p3.add(balance);
		p2.add(l3); p2.add(betting);
		p2.add(b1); p2.add(b2);
		add(BorderLayout.NORTH,p3);
		add(BorderLayout.CENTER,p1);
		add(BorderLayout.SOUTH,p2);
		
		setBounds(100,100,500,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		// DB connection
		new AKLGame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==b2) {	// 종료하는 조건문
			System.exit(1);
		}
		
		for(int i=0;i<4;i++) {
			if(e.getSource()==runner[i]) 
			{
				tmp = i;	// 액션리스너가 라디오버튼값과 그냥 버튼값을 동시에 못받기때문에..
			}
		}
		System.out.println(tmp);
		if(e.getSource()==b1 && tmp != 999)  // 게임을 시작하는 조건문
		{	
			new Screen();
		}	
	}
	
	// 플레이화면
	public class Screen {
		
		public Screen() {
			JFrame game = new JFrame("<Game Screen>");
		
			for(int i=0;i<4;i++) {	// 경주마들 랭킹 초기화
				rank[i] = null;
			}
			
			p4 = new JPanel(); // 경마게임스크린 패널
			p5 = new JPanel();
			p4.setLayout(new GridLayout(4,1));
			p5.setLayout(new GridLayout(3,4));
			b3 = new JButton("Start");
			p4.add(new Play("bet",15,15,"1번마",0));
			p4.add(new Play("trojan",15,15,"2번마",1));
			p4.add(new Play("horseback",15,15,"3번마",2));
			p4.add(new Play("pegasus",15,15,"4번마",3));
			
			ImageIcon question = new ImageIcon("question.png");
			
			for(int i=0;i<4;i++) {
				p5.add(new JLabel("Rank<"+(i+1)+">",SwingConstants.CENTER));
			}
		
			for(int i=0;i<4;i++) {
				ranklabel[i] = new JLabel("",SwingConstants.CENTER);
				ranklabel[i].setIcon(question);
				p5.add(ranklabel[i]);
			}
			for(int i=0;i<4;i++) {
				p5.add(new JLabel("  ### 광고문의는 010-8813-2935 ###"));
			}
		
			game.add(BorderLayout.CENTER,p4);
			game.add(BorderLayout.SOUTH,p5);
			game.repaint();
			game.pack();
			game.setBounds(100,100,1200,600);
			game.setVisible(true);
		}
	}
	
	// 동작코드
	public class Play extends JPanel {
		BufferedImage img = null;
		public int img_x = 0, img_y = 0;
		
		public Play(String h_name, int x, int y, String lane, int h_num) {
			
			try {
				img = ImageIO.read(new File(h_name+".png"));
			} catch (IOException e) {
				System.out.println("no Images");
				System.exit(1);
			}
			img_x = x;
			img_y = y;
			Border border = BorderFactory.createTitledBorder(lane);
			this.setBorder(border);
			MyThread tt = new MyThread(h_name,h_num);
			tt.start();	//	쓰레드 사용
			
		}
		
		public class MyThread extends Thread {
			String hname;
			int hnum;
		    // Thread로 동작할 내용을 동작할 메서드
			MyThread(String h_name, int h_num){
				hname = h_name;
				hnum = h_num;
			}
		    public void run() {
		        // 0.5초 마다 숫자를 하나씩 증가시키고 숫자와 Thread의 이름을 출력한다
		        while(img_x < 1200){
		        	img_x += (int)(Math.random()*80);
		        	repaint();
		            try{
		                Thread.sleep(500);
		                
		            }catch(Exception e){
		                System.out.println(e.getMessage());
		            }
		        }
		        
		        for(int i=0;i<4;i++) {	// 순위를 매겨주는 조건 & 반복문
		        	if(rank[i] == null) {
		        		rank[i] = hname;
		        		ranklabel[i].setIcon(icon[hnum]); // 랭크란에 넣는 코드
		        		
		        		System.out.println(rank[i]);
		        		// 계산
		    			if(rank[3] != null) {
		    				// open DB
		    				try {
		        				PreparedStatement stmt = conn.prepareStatement(OpenSql);
		        				if ("bet" == rank[0])
		        					stmt.setString(1, "1");
		        				else if("trojan" == rank [0])
		        					stmt.setString(1, "2");
		        				else if("horseback" == rank [0])
		        					stmt.setString(1, "3");
		        				else
		        					stmt.setString(1, "4");
		        				ResultSet rs = stmt.executeQuery();
		        				while(rs.next()) {
		        					win = rs.getInt("WIN");
		        					win++;
		        				}
		        				System.out.println(win);
		        			}catch(SQLException e) {
		        				e.getStackTrace();
		        			}
		    				bet_money = Integer.parseInt(betting.getText());
			    			money -= bet_money;
		    				if(horse[tmp] == rank[0]) {
		    					bet_money *= 4;
		    					money += bet_money;
		    					balance.setText(Integer.toString(money));
		    					System.out.println("축하 ");
		    				}	
		    				else {
		    					bet_money = 0;
		    					balance.setText(Integer.toString(money));
		    					System.out.println("실패 ");  
		    				}
		    	
		    				// DB에 다시 win값을 넣어서 업데이트!!
			    			try {
			    				SaveSql = "update HORSE_DB set win = "+win+" where HORSE_ID = ?";
			    				PreparedStatement stmt = conn.prepareStatement(SaveSql);
			    				
			    				if ("bet" == rank[0])
			    					stmt.setString(1, "1");
			    				else if("trojan" == rank [0])
			    					stmt.setString(1, "2");
			    				else if("horseback" == rank [0])
			    					stmt.setString(1, "3");
			    				else
			    					stmt.setString(1, "4");
			    				int k = stmt.executeUpdate();
			    				if(k==1) {
			    					System.out.println("Success");
			    				}
			    				else {
			    					System.out.println("Fail");
			    				}
			    			}catch(SQLException e) {
			    				e.getStackTrace();
			    			}
		    			}
		    			
		    			
		        		break;
		        	}
		        }
    		    
		    }
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, img_x, img_y, null);
		}
	}
	
}





