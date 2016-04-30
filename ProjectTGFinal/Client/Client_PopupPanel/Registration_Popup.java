package Client_PopupPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ImgProcess.ImgProcess;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import userDB.UserDao;
import userDB.UserDto;

public class Registration_Popup {
	private JFrame frame;
	private JLabel registrationLable;
	private JLabel idLabel;
	private JLabel pwLabel;
	private JLabel pwConfirmLabel;
	private JTextField idField;
	private JPasswordField pwField;
	private JPasswordField pwConfirmField;
	private JButton idCheckButton;
	private JButton registerButton;
	private UserDao dao;
	private UserDto dto;
	ImgProcess img = new ImgProcess();
	
	
		
	
	public Registration_Popup() {
		// dao 생성해 주기.
		dao = new UserDao();
		// GUI 왼쪽(BorderLayout.WEST)에 들어가는 라벨들.
		registrationLable = new JLabel(" Registration");
		idLabel = new JLabel(" ID");
		pwLabel = new JLabel(" Password");
		pwConfirmLabel = new JLabel(" Confirm Password");
		// GUI 가운데(BorderLayout.CENTER)에 들어가는 필드들.
		idField = new JTextField();
		pwField = new JPasswordField();
		pwConfirmField = new JPasswordField();
		idCheckButton = new JButton("Check");// 아이디 중복 검 버튼.
		idCheckButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dao = new UserDao();
				dto = new UserDto();
				dto = dao.readOne(idField.getText()); //dao에서 입력한 ID를 key값으로 이용하여 정보를 불러와 dto에 저장.
				try {
					if (dto.getId().equals(idField.getText())) { //불러온 정보가 있으면 입력한 ID값과 불러온 값을 비교한다.
						// 값이 같으면 사용중인 아이디임을 보여준다.
						System.out.println("익셉션 ㄴㄴ");
						JOptionPane.showMessageDialog(null,
								"사용중인 ID입니다. 다른 ID를 사용해주세요.");
					} 
				} catch (NullPointerException ex) { // 불러온 값이 없으면 비교할때 NullpointerException이 뜬다. 
					//그 뜻은 db에 입력받은 id와 같은 key값이 없다는 뜻으로 사용가능한 아이디임을 뜻한다. 
					System.out.println("익셉션 발생!!!!!!");
					JOptionPane.showMessageDialog(null, "사용 가능한 ID입니다.");
				}

			}
		});
		registerButton = new JButton("Submit"); // 회원 가입 버튼.
		registerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					System.out.println("3");
					String id = idField.getText();
					String pw = retrievePassword(pwField);
					String confirmedPw = retrievePassword(pwConfirmField);
					int minPwLength = 4;
					if (pw.length() < minPwLength) {
						new JOptionPane().showMessageDialog(null, "비밀번호가 짧습니다."
								+ " 4글자 이상으로 설정해 주세요.");
					} else {
						if (pw.equals(confirmedPw)) {
							System.out.println("pw checked");
							UserDto dto = new UserDto();
							dto.setId(id);
							dto.setPw(pw);
							dao.createOne(dto);
							new JOptionPane().showMessageDialog(null,
									"회원 가입을 축하드립니다!");
						} else {
							System.out.println("password NOT MATCHING");
							new JOptionPane().showMessageDialog(null,
									"비밀번호를 다시 한번 체크해주세요.");
						}
					}
				} catch (MySQLIntegrityConstraintViolationException duplicateID) {
					JOptionPane.showMessageDialog(null,
							"사용중인 ID 입니다! 다른 ID를 입력후 중복 여부를 확인해 주세요!");

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		initialize();
		
	}
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 370, 255);
		frame.getContentPane().setLayout(null);
		img.MyPanel_Registeration();
		img.setBounds(0, 0, 370, 255);
		
		
		JLabel lblRegisteration = new JLabel("Registeration");
		lblRegisteration.setFont(new Font("Nexa Light", Font.BOLD, 13));
		lblRegisteration.setBounds(136, 6, 91, 16);
		lblRegisteration.setForeground(Color.white);
		frame.getContentPane().add(lblRegisteration);
		
		JLabel lblId = new JLabel("ID");
		lblId.setForeground(Color.white);
		lblId.setFont(new Font("Nexa Light", Font.PLAIN, 13));
		lblId.setHorizontalAlignment(SwingConstants.CENTER);
		lblId.setBounds(40, 49, 61, 16);
		frame.getContentPane().add(lblId);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.white);
		lblPassword.setFont(new Font("Nexa Light", Font.PLAIN, 13));
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setBounds(40, 104, 61, 16);
		frame.getContentPane().add(lblPassword);
		
		JLabel lblConfirmPassword = new JLabel("Confirm");
		lblConfirmPassword.setForeground(Color.white);
		lblConfirmPassword.setFont(new Font("Nexa Light", Font.PLAIN, 13));
		lblConfirmPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfirmPassword.setBounds(6, 160, 128, 16);
		frame.getContentPane().add(lblConfirmPassword);
		
		idField = new JTextField();
		idField.setBounds(120, 43, 160, 28);
		frame.getContentPane().add(idField);
		idField.setColumns(10);
		
		pwField = new JPasswordField();
		pwField.setColumns(10);
		pwField.setBounds(120, 98, 160, 28);
		frame.getContentPane().add(pwField);
		
		pwConfirmField = new JPasswordField();
		pwConfirmField.setColumns(10);
		pwConfirmField.setBounds(120, 160, 160, 28);
		frame.getContentPane().add(pwConfirmField);
		
		idCheckButton.setFont(new Font("Nexa Light", Font.PLAIN, 13));
		idCheckButton.setBounds(292, 45, 61, 21);
		frame.getContentPane().add(idCheckButton);
		
		registerButton.setFont(new Font("Nexa Light", Font.BOLD, 13));
		registerButton.setBounds(156, 200, 71, 21);
		frame.getContentPane().add(registerButton);
		
		JLabel label = new JLabel("Password");
		label.setForeground(Color.white);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Nexa Light", Font.PLAIN, 13));
		label.setBounds(40, 177, 61, 16);
		frame.getContentPane().add(label);
		frame.getContentPane().add(img);
	}
	

	public static String retrievePassword(JPasswordField pwField) {
		String pw = "";
		char[] pwArray = pwField.getPassword();
		for (char ch : pwArray) {
			pw = pw.concat(ch + "");
		}
		return pw;
	}
}
