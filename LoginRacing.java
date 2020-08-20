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
		 // ȸ������
		
		// ��ư �̺�Ʈ ó��
		
		// �α��� ��ư�� ������ tf_id �� ���� �������� ��ȸ �ش� ȸ���� tf_pw�� �Էµ� ���� ��.
		// db ���� �ҷ��ͼ� ��ġ�ϸ� �÷���ȭ�� (�� ���� ȭ��)���� �̵�
		Btn_Login.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				String input_id = Tf_id.getText();
				String input_pw = Tf_pw.getText(); // ���߿� getPassword�� ����.
				
				String sql = "SELECT pw FROM player WHERE id = ?";
				
				Connection conn = Jake();
				
				try {
					
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, input_id);
					
					rs = stmt.executeQuery();
					rs.next();
					String out_pw = rs.getString("pw");
					System.out.println(out_pw);

					// db���� �ҷ��� pw�� Tf_pw�� ������ �α���

					if (input_pw.equals(out_pw)) {
						System.out.println("�α��� ����");
						new RacingGame().setLocationRelativeTo(null);
						dispose();
					}
					else {
						System.out.println("���̵�� ��й�ȣ�� Ȯ���ϼ���");
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
				String input_pw = Tf_pw.getText(); // ���߿� getPassword�� ����.
				
				String sql = "INSERT INTO player VALUES(?,?,?)";
				
				Connection conn = Jake();
				
				try {
					
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, input_id);
					
					rs = stmt.executeQuery();
					rs.next();
					String out_pw = rs.getString("pw");
					System.out.println(out_pw);

					// db���� �ҷ��� pw�� Tf_pw�� ������ �α���

					if (input_pw.equals(out_pw)) {
						System.out.println("�α��� ����");
						new RacingGame().setLocationRelativeTo(null);
						dispose();
					}
					else {
						System.out.println("���̵�� ��й�ȣ�� Ȯ���ϼ���");
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
		this.setTitle("�α���â");
		
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
