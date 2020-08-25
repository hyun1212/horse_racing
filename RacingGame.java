package horse;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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


public class RacingGame extends JFrame implements ActionListener {
	public String[] horse = { "bet", "trojan", "horseback", "pegasus" };
	String[][] hstring = { {"    Nation:  Japan", "       Age:  7", "  Ability:  Merong", "    Win:  5"},
			   {"    Nation:  Greece",  "       Age:  4000", "  Ability:  Toy", "    Win:  9999"},
			   {"    Nation:  Korea", "       Age:  5", "  Ability:  Fast", "    Win:  7"},
			   {"    Nation:  Roma", "       Age:  8000", "  Ability:  Wings", "    Win:  1"}
	};
	JPanel p1, p2, p3, p4, p5 ,p6, center, inf;
	JLabel l1, l2, l3, balance;
	JTextField betting = new JTextField(5);
	JRadioButton[] runner = new JRadioButton[4];
	JButton b1,b2,b3;
	ImageIcon[] icon = new ImageIcon[4];
	JLabel[] iconlabel = new JLabel[4];
	String[] rank = new String[4];
	JLabel[] hinf = new JLabel[4]; // 말 정보를 담은 레이블 생성
	int tmp;
	
	int money = 10000;
	int bet_money = 0;
	
	public RacingGame() {
		// Panel Factory
		p1 = new JPanel(); p2 = new JPanel(); p3 = new JPanel(); center = new JPanel(); inf = new JPanel();
		//p5 = new JPanel(); p6 = new JPanel(); p6 = new JPanel();
		b1 = new JButton("Enter"); b2 = new JButton("Close");
		l1 = new JLabel("Your balance ->",SwingConstants.CENTER);
		l2 = new JLabel("Your Money",SwingConstants.CENTER);
		l3 = new JLabel("How much do you wanna bet?",SwingConstants.CENTER);
		balance = new JLabel("10000",SwingConstants.CENTER);
		p3.setLayout(new GridLayout(1,2));
		p1.setLayout(new GridLayout(2,4));
		p2.setLayout(new GridLayout(2,2));
		center.setLayout(new BorderLayout());
		inf.setLayout(new GridLayout(1, 4));
		ButtonGroup group = new ButtonGroup();
		information info = new information(); // information 클래스 불러올 info.
		
		for(int i=0;i<4;i++) {
			runner[i] = new JRadioButton(horse[i]);
			iconlabel[i] = new JLabel();
			icon[i] = new ImageIcon(horse[i]+".png");
			group.add(runner[i]);
			p1.add(runner[i]);
			iconlabel[i].setIcon(icon[i]);
			p1.add(iconlabel[i]);
			hinf[i] = new JLabel();
			runner[i].addActionListener(this);
		}
		
		Border border3 = BorderFactory.createTitledBorder("Info");
		p3.setBorder(border3);
		Border border1 = BorderFactory.createTitledBorder("Horses");
		p1.setBorder(border1);
		Border border4 = BorderFactory.createTitledBorder("Horse Info"); // 정보 border 생성
		inf.setBorder(border4); // inf에 border 설정
		Border border2 = BorderFactory.createTitledBorder("Menu");
		p2.setBorder(border2);
		
		b1.addActionListener(this);
		b2.addActionListener(this);
		
		p3.add(l1); p3.add(balance);
		p2.add(l3); p2.add(betting);
		p2.add(b1); p2.add(b2);
		
		hinf[0].setText("    Nation: ");
		hinf[1].setText("       Age: ");
		hinf[2].setText("  Ability: ");
		hinf[3].setText("    win: ");
		
		inf.add(hinf[0]); inf.add(hinf[1]);
		inf.add(hinf[2]); inf.add(hinf[3]); // 정보 레이블을 정보 패널을 프레임에 추가
		
		center.add(BorderLayout.NORTH, p1); // info의 center에 p1 올려놓음.
		center.add(BorderLayout.SOUTH, inf);
		add(BorderLayout.NORTH,p3);
		add(BorderLayout.CENTER,center); // 센터에 말 선택과 정보 올라간 패널
		add(BorderLayout.SOUTH,p2);
				
		setBounds(100,200,500,385);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new RacingGame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==b2) {	// 종료하는 조건문
			System.exit(1);
		}
		
		for(int i=0;i<4;i++) {
			if(e.getSource() == runner[i]) 
			{
				tmp = i;	// 액션리스너가 라디오버튼값과 그냥 버튼값을 동시에 못받기때문에..
				hinf[0].setText(hstring[i][0]);
				hinf[1].setText(hstring[i][1]);
				hinf[2].setText(hstring[i][2]);
				hinf[3].setText(hstring[i][3]);
			}
		}
		System.out.println(tmp);
		if(e.getSource()==b1 && tmp != 999)  // 게임을 시작하는 조건문
		{	
			new Game();
		}	
	}
	
	public class Game {
		
		public Game() {
			JFrame game = new JFrame("<Game Screen>");
			for(int i=0;i<4;i++) {	// 경주마들 랭킹 초기화
				rank[i] = null;
			}
			p4 = new JPanel(); // 경마게임스크린 패널
			p4.setLayout(new GridLayout(4,1));
			b3 = new JButton("Start");
			p4.add(new Screen("bet",15,15,"1번마"));
			p4.add(new Screen("trojan",15,15,"2번마"));
			p4.add(new Screen("horseback",15,15,"3번마"));
			p4.add(new Screen("pegasus",15,15,"4번마"));
		
			game.add(p4);
			game.repaint();
			game.pack();
			game.setBounds(100,200,1200,600);
			game.setVisible(true);
		}
	}
	public class Screen extends JPanel {
		BufferedImage img = null;
		public int img_x = 0, img_y = 0;

		
		public Screen(String h_name, int x, int y, String lane) {
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
			MyThread tt = new MyThread(h_name);
			tt.start();	//	쓰레드 사용
		}
		public class MyThread extends Thread {
			String hname;
		    // Thread로 동작할 내용을 동작할 메서드
			MyThread(String h_name){
				hname = h_name;
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
		        		System.out.println(rank[i]);
		        		// 계산
		    			if(rank[3] != null) {
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