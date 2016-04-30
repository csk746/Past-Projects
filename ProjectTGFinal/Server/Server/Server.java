package Server;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Server extends JFrame implements ActionListener {
	private JPanel contentPane;
	private JTextField port_tf;
	public JTextArea textArea = new JTextArea();
	private JButton start_btn = new JButton("서버 실행");
	private JButton stop_btn = new JButton("서버 중지");
	Server sv;
	// Network 자원
	private ServerSocket server_socket;
	private Socket socket;
	private int port;
	public Vector user_vc = new Vector();
	public Vector room_vc = new Vector();
	private StringTokenizer st;
	private String Nickname;
	public String getNickname() {
		return Nickname;
	}

	public void setNickname(String nickname) {
		Nickname = nickname;
	}

	// 서버 생성자
	Server() {
		init(this);// 화면 생성하는 메소드
		btn_listener(); // 리스너 설정 메소
	}

	// 버튼에 엑션리스너 붙이기
	public void btn_listener() {
		start_btn.addActionListener(this);
		stop_btn.addActionListener(this);
	}

	// 서버창 GUI
	private void init(Server sv) {
		this.sv = sv;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 362, 459);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(19, 6, 324, 322);
		contentPane.add(scrollPane);

		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
		JLabel lblNewLabel = new JLabel("포트번호");
		lblNewLabel.setBounds(24, 357, 61, 16);
		contentPane.add(lblNewLabel);

		port_tf = new JTextField("9999");
		port_tf.setBounds(77, 351, 243, 28);
		contentPane.add(port_tf);
		port_tf.setColumns(10);

		start_btn.setBounds(34, 385, 117, 29);
		contentPane.add(start_btn);

		stop_btn.setBounds(203, 385, 117, 29);
		contentPane.add(stop_btn);
		stop_btn.setEnabled(false);
		this.setVisible(true);
	}

	private void Server_start() {
		try {
			// 받아온 포트로 서버소켓 생성
			server_socket = new ServerSocket(9999);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "이미 사용중인 포트", "알림",
					JOptionPane.ERROR_MESSAGE);
		}
		// 정상적으로 포트가 열렸을경우 연결
		if (server_socket != null) {
			Connection();
		}
	}

	private void Connection() {
		Thread th = new Thread(new Runnable() {

			@Override
			// 스레드에서 처리할 일을 기재한다.
			public void run() {

				while (true) {

					try {
						textArea.append("사용자 접속 대기중  \n");
						// 사용자 접속 대기 무한대기
						// 사용자를 받아서 소켓에 저장.
						socket = server_socket.accept();
						textArea.append("사용자 접속!!\n");

						// 소켓을 넣어 유저인포 생성
						UserInfo user = new UserInfo(socket,sv);
						// 객체의 쓰레드 실행
						user.start();

					} catch (IOException e) {

						break;
					}
				}// while문 끝

			}
		});
		th.start();
	}


	/*------------------actionPerformed------------------ */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == start_btn) {
			System.out.println("서버 실행 버튼 클릭");
			// port = Integer.parseInt(port_tf.getText().trim());
			Server_start(); // 소켓 생성 및 네트워크 대기
			start_btn.setEnabled(false);
			port_tf.setEditable(false);
			stop_btn.setEnabled(true);
		} else if (e.getSource() == stop_btn) {
			stop_btn.setEnabled(false);
			start_btn.setEnabled(true);
			port_tf.setEditable(true);
			try {
				server_socket.close();
				user_vc.removeAllElements();
				room_vc.removeAllElements();
			} catch (IOException e1) {

			}
			System.out.println("서버 중지 버튼 클릭");
		}
	} // 엑션 이벤트 끝

	public static void main(String[] args) {
		new Server();
	}
}