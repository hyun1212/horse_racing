package homework;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class LoginRacing extends JFrame{
	JPanel p1,p2;
	JButton Btn_Login,Btn_Cancel, Btn_SignUp;
	JLabel l1, l2;
	JTextField Tf_id;
	JPasswordField Tf_pw;
	ResultSet rs;
	PreparedStatement stmt;
	
	public LoginRacing () {
		p1 = new JPanel();
		p1.setLayout(new GridLayout(0,2));
		
		// p2 = new JPanel();
		Btn_Login = new JButton("Login");
		Btn_Cancel = new JButton("Close");
		Btn_SignUp = new JButton("SignUp");
		Tf_id = new JTextField();
		Tf_pw = new JPasswordField();
		
		Tf_id.selectAll(); Tf_pw.selectAll();
		Tf_pw.setEchoChar('*');
		
		l1 = new JLabel("id", JLabel.CENTER);
		l2 = new JLabel("pw", JLabel.CENTER);
	
		p1.add(l1);	p1.add(Tf_id);
		p1.add(l2);	p1.add(Tf_pw);
		p1.add(Btn_SignUp); p1.add(Btn_Login);
		 // 회원가입
		
		// 버튼 이벤트 처리
		
		// 로그인 버튼을 누르면 tf_id 의 값을 조건으로 조회 해당 회원의 tf_pw의 입력된 값과 비교.
		// db 정보 불러와서 일치하면 플레이화면 (말 선택 화면)으로 이동
		Btn_Login.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				String input_id = Tf_id.getText();
				String input_pw = Tf_pw.getText(); // 나중에 getPassword로 보완.
				
				String sql = "SELECT pw FROM player WHERE id = ?";
				
				Connection conn = Jake();
				
				try {
					
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, input_id);
					
					rs = stmt.executeQuery();
					rs.next();
					String out_pw = rs.getString("pw");
					System.out.println(out_pw);

					// db에서 불러온 pw와 Tf_pw와 같으면 로그인

					if (input_pw.equals(out_pw)) {
						System.out.println("로그인 성공");
						new RacingGame().setLocationRelativeTo(null);
						dispose();
					}
					else {
						System.out.println("아이디와 비밀번호를 확인하세요");
						sql = "UPDATE player SET money = 20000 WHERE id = ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, input_id);
						
						rs = stmt.executeQuery();
					}
					
					
				} catch (SQLException e1) {
				
					e1.printStackTrace();
				}
				
			}
			
		});
		
		Btn_SignUp.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				String input_id = Tf_id.getText();
				String input_pw = Tf_pw.getText(); // 나중에 getPassword로 보완.
				
				String sql = "INSERT INTO player VALUES(?,?,?)";
				
				Connection conn = Jake();
				
				try {
					
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, input_id);
					
					rs = stmt.executeQuery();
					rs.next();
					String out_pw = rs.getString("pw");
					System.out.println(out_pw);

					// db에서 불러온 pw와 Tf_pw와 같으면 로그인

					if (input_pw.equals(out_pw)) {
						System.out.println("로그인 성공");
						new RacingGame().setLocationRelativeTo(null);
						dispose();
					}
					else {
						System.out.println("아이디와 비밀번호를 확인하세요");
						sql = "UPDATE player SET money = 20000 WHERE id = ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, input_id);
						
						rs = stmt.executeQuery();
					}
					
					
				} catch (SQLException e1) {
				
					e1.printStackTrace();
				}
				
			}
			
		});
		
		

		add(p1);		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setSize(350,200);
		this.setTitle("로그인창");
		
	}
 
	public static Connection Jake() {
		Connection conn = null;
		String user = "ahc";
		String pw = "1234";
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String driver = "oracle.jdbc.driver.OracleDriver";
		
		
	try {
		Class.forName(driver);
		conn = DriverManager.getConnection(url, user, pw);
		
	}
	
	catch(Exception e) {
		System.out.println(e.toString());
		
	}

	return conn;
}
	
	public static void main(String[] args) {
		new LoginRacing();

	}

}
