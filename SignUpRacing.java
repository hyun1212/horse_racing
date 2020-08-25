package hosre_racing;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;


public class SignUpRacing extends LoginRacing{
	JPanel p;
	JButton btn_Cancel, btn_SignUp;
	
	String[] label_name = {"id", "pw", "money"};
	JLabel[] l = new JLabel[label_name.length];
	JLabel lbl_ck;
	JTextField tf_money;
	
	ResultSet rs;
	PreparedStatement stmt;
	
	public SignUpRacing () {
		p = new JPanel();
		p2 = new JPanel();
		p.setLayout(new GridLayout(0,2));
		
		for (int i = 0; i < l.length; i++) {
			l[i] = new JLabel(""+label_name[i], JLabel.CENTER);
		}
		
		tf_id = new JTextField(); tf_money = new JTextField();
		pf_pw = new JPasswordField(); lbl_ck = new JLabel();
		
		btn_SignUp = new JButton("가입");
		btn_Cancel = new JButton("종료");
		
		p.add(l[0]); p.add(tf_id);
		p.add(l[1]); p.add(pf_pw);
		p.add(l[2]); p.add(tf_money);
		p.add(btn_SignUp); p.add(btn_Cancel);
		p.add(lbl_ck, BorderLayout.NORTH);
		
		btn_SignUp.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				String sql = "INSERT INTO player VALUES(?,?,?)";
				Connection conn = Jake();
				
				String input_pw = "";
				char[] secret_pw = pf_pw.getPassword();
				for (char cha : secret_pw) {
					input_pw += Character.toString(cha);
				}
				
				try {
					
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, tf_id.getText());
				stmt.setString(2, input_pw);
				stmt.setString(3, tf_money.getText());
				
				rs = stmt.executeQuery();
				lbl_ck.setText("회원가입 완료");
				
				}
				catch (Exception ex) {
					lbl_ck.setText("아이디를 다시 입력해주세요.");
					System.out.println(ex.toString());

				}
			}
			
		});
		
		btn_Cancel.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
			
		});
		
		
		add(p);		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setSize(350,300);
		this.setTitle("회원가입 창");
	}
	
	public static void main(String[] args) {
		new SignUpRacing();

	}

}
