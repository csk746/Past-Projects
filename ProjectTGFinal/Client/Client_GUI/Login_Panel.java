package Client_GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import userDB.UserDao;
import userDB.UserDto;
import Client.Client;
import Client_PopupPanel.Registration_Popup;
import ImgProcess.ImgProcess;
import autoLogin.AutoLoginCookie;
import autoLogin.AutoLoginDto;
import autoLogin.AutoLogin_Client;

public class Login_Panel extends JFrame implements ActionListener{
	// LoginGUI변수
		private JTextField idField;
		private JPasswordField passwordField;
		private String retrievedPw;
		private JButton Login;
		
		private JCheckBox autoLogin;
		private JButton forgotPasswordButton;
		private JButton registerButton;
		private JTextField GongBack;
		private JTextField Line;
		private JLayeredPane cont_InLogInGUI = new JLayeredPane();
		private UserDao dao;
		private UserDto dto;
		
		private ImgProcess Img =new ImgProcess();
		Client cl;
		AutoLogin_Client alc;
		
		public Login_Panel(Client cl, AutoLogin_Client alc){
			this.alc = alc;
			this.cl = cl;
			}

		public void CallLoginGUI(){
			LoginGUI();
			}
		
		/** ----------LoginGUI 메소드 끝---------------- */
		public void LoginGUI() {
			setSize(300, 600);
			setResizable(false);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setLayout(null);

			Img.MyPanel_InLogin();

			cont_InLogInGUI.setBounds(0, 0, 300, 600);
			Img.setBounds(0, 0, 300, 600);

			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			idField = new JTextField(20);
			idField.setBounds(40, 230, 200, 40);
			cont_InLogInGUI.add(idField);
			// 비밀번호입력창
			passwordField = new JPasswordField(12); // 12자리까지
			passwordField.setBounds(40, 270, 200, 40);
			cont_InLogInGUI.add(passwordField);
			// 로그인버튼
			Login = new JButton("로그인");
			Login.setBounds(40, 314, 200, 40);
			Login.addActionListener(this);
			cont_InLogInGUI.add(Login);
			// 자동로그인 체크박스
			autoLogin = new JCheckBox("Auto-Login");
			autoLogin.setBounds(40, 360, 200, 20);
		//	autoLogin.setForeground(Color.white);
			autoLogin.setOpaque(false); // 투명으로 설정하게해준다
			cont_InLogInGUI.add(autoLogin);
		
			
			// 회원가입
			registerButton = new JButton("회원가입");
			registerButton.setBounds(68, 420, 150, 30);
			registerButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					new Registration_Popup();

				}
			});
			cont_InLogInGUI.add(registerButton);

			// 비밀번호찾기
			forgotPasswordButton = new JButton("비밀번호 찾기");
			forgotPasswordButton.setBounds(68, 450, 150, 30);
			forgotPasswordButton.setContentAreaFilled(false);// 버튼투명처리
			cont_InLogInGUI.add(forgotPasswordButton);
			// 마지막추가
			cont_InLogInGUI.add(Img);
			add(cont_InLogInGUI); // 기본레이아웃위에 JLayeredPane을 추가
			setVisible(true);
		}
		/** ----------Loginfunction 메소드 ---------------- */
		public void loginMethod(String id, String pw) {
			System.out.println("1.1 LoginMethod");
			dao = new UserDao();
			dto = new UserDto();
			try {
				dto = dao.readOne(id);
				System.out.println("DTO ID: " + id);
				System.out.println("DTO PW: " + dto.getPw());
				if (dto.getPw().equals(cl.getPassword())) {
					System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					System.out.println("DataBase PW -> " + dto.getPw());
					System.out.println("INPUT PW -> " + cl.getPassword());
					System.out.println("test3");
					System.out.println(alc.isViaLoginGUI());
					if (alc.isViaLoginGUI() == true) {
						if (autoLogin.isSelected() == true) {
							// 자동로그인!!!!!
							AutoLoginDto loginDto = new AutoLoginDto();
							AutoLoginCookie loginCookie = new AutoLoginCookie();

							loginDto.setId(id);
							loginDto.setPw(cl.getPassword());
							loginDto.setAutoLogin(autoLogin.isSelected());

							loginCookie.createCookie(loginDto);

						}
					}

					cl.Network();
					
				}

				System.out.println("1.1 LoginMethod -> DONE");
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "id와 비밀번호를 다시 한번 확인해 주세요.");

				System.out.println("1.1 LoginMethod -> 아이디와 비번 확인");
			}

		}
		@Override
		public void actionPerformed(ActionEvent e) {
			boolean exid = false;
			if (e.getSource() == Login) {
				System.out.println("로그인 버튼 클릭");

				if (idField.getText().length() == 0) {
					idField.setText("Id를 입력해주세요");
					cl.setExistId(false);

				} 
//				else {
//					System.out.println(ff.getListVector_InFriends().size());
//					for(int i = 0 ; i<ff.getListVector_InFriends().size(); i++){
//					
//						System.out.println("벡터에 있는 아이디 " +ff.getListVector_InFriends().elementAt(i));
//						System.out.println("입력받은 아이디" +idField.getText().trim());
//						
//						if(ff.getListVector_InFriends().elementAt(i)==idField.getText().trim()){
//						JOptionPane.showMessageDialog(null, "이미 존재하는 Id입니다.");
//						exid = true;
//						break;
//					}
//					exid = false;
//					}	
//					if(!exid){
//						System.out.println("여기는 됨?");
					
					cl.setId( idField.getText().trim());
						cl.setExistId(true);
//					}
//					}

				retrievedPw = "";
				retrievedPw = Registration_Popup.retrievePassword(passwordField);

				if (retrievedPw.length() == 0) {
					passwordField.setText("Password를 입력해주세요");
					cl.setExistPw(false);

				} else {
					cl.setPassword(retrievedPw.trim());
					cl.setExistPw(true);
				}

				if (cl.isExistId() == false && cl.isExistPw() == false) {
					idField.requestFocus();
				} else if (cl.isExistId() == false && cl.isExistPw() == true) {
					idField.requestFocus();

				} else if (cl.isExistId() == true && cl.isExistPw() == false) {
					passwordField.requestFocus();
				} else {
					System.out.println("저장된 비밀번호 : " +alc.getLoginDto().getPw());
					loginMethod(cl.getId(), alc.getLoginDto().getPw());
				}
			}
			
		}
}
