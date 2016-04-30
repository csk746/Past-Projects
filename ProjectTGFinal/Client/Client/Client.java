
package Client;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JOptionPane;

import soundControl.SoundPlayer;
import soundTransfer.FileReceiver;
import soundTransfer.FileSender;
import Client_GUI.Login_Panel;
import Client_GUI.MainGUI;
import Client_PopupPanel.Chatting_Popup;
import autoLogin.AutoLogin_Client;

public class Client 
 {	
	Vector room_vc = new Vector();
	Vector chat_vc = new Vector();
	
	// 네트워크를 위한 자원 변수
	private Socket socket;
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	private String ip; // 자기자신
	
	private String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	private String password;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	private InputStream is;
	public InputStream getIs() {
		return is;
	}
	public void setIs(InputStream is) {
		this.is = is;
	}

	private OutputStream os;
	public OutputStream getOs() {
		return os;
	}
	public void setOs(OutputStream os) {
		this.os = os;
	}

	private DataInputStream dis;
	public DataInputStream getDis() {
		return dis;
	}
	public void setDis(DataInputStream dis) {
		this.dis = dis;
	}

	private DataOutputStream dos;
	public DataOutputStream getDos() {
		return dos;
	}
	public void setDos(DataOutputStream dos) {
		this.dos = dos;
	}

	private StringTokenizer st;
	private boolean existId;
	public boolean isExistId() {
		return existId;
	}
	public void setExistId(boolean existId) {
		this.existId = existId;
	}

	private boolean existPw;
	public boolean isExistPw() {
		return existPw;
	}
	public void setExistPw(boolean existPw) {
		this.existPw = existPw;
	}

	private boolean isLogin = false;

	// 파일전송을 위한 변수
	private ServerSocket serverSock;
	private Socket cltSock;
	private DataInputStream dis_FileReceive;
	private FileOutputStream fos;
	private BufferedOutputStream bos;
	private String friend;
	public String getFriend() {
		return friend;
	}
	public void setFriend(String friend) {
		this.friend = friend;
	}

	private String note;

	private String My_Room; // 내가있는 방이름
	MainGUI newGUI;
	AutoLogin_Client alc = new AutoLogin_Client();
	Login_Panel lg = new Login_Panel(this, alc);



	/** ----------네트워크 시작---------------- */
	// 생성자
	public Client() {
		try {
		
			System.out.println("0.3ClientConstructor Prior to if()");
			alc.setLoginDto(alc.getDoAutoLogin().readCookie());
			System.out.println("0.4ClientConstructor Prior to if()");

			if (alc.getLoginDto().isAutoLogin() == true) {
				alc.setViaLoginGUI(false);
				System.out.println("1.Client Constructor");
				setId(alc.getLoginDto().getId());
				setPassword(alc.getLoginDto().getPw());
				lg.loginMethod(id, password);
			} else {
				alc.setViaLoginGUI(true);
				System.out
						.println("1.ClientConstructor -> No Autologin setted");
			lg.CallLoginGUI();
			}
		} catch (Exception e) {
			alc.setViaLoginGUI(true);
			System.out
					.println("1.ClientConstructor -> 이상한 정보 들어옴.. 오토 로그인이 false true가 아님. ");
			lg.CallLoginGUI();
		}

	}
	
	public void Network() {
		try {
			socket = new Socket("127.0.0.1", 9999);
			/*
			 * 정상적으로 소켓이 연결 되었을경우 커넥션 메소드로 넘어간다
			 */
			if (socket != null) {
				Connection();
			}
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "연결 실패", "알림",
					JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "연결 실패", "알림",
					JOptionPane.ERROR_MESSAGE);
		}

		send_Message(id);
		newGUI.getfGUI().getListVector_InFriends().add(id);
		System.out.println("벡터안에있는수 : "+newGUI.getfGUI().getListVector_InFriends().size());
		newGUI.getfGUI().getList_InFriends().setListData(newGUI.getfGUI().getListVector_InFriends());

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						String msg = dis.readUTF();
						System.out.println("서버로부터 수신된 메세지 :" + msg);

						receive_Message(msg);

					} catch (IOException e) {
						try {
							os.close();
							is.close();
							dos.close();
							dis.close();
							socket.close();
							JOptionPane.showMessageDialog(null, "로그아웃되었습니다.");

						} catch (IOException e1) {
						}
						break;
					} // 메세지 수신
				}
			}
		});
		th.start();
	}

	// 실제적인 메소드 연결부분.
	private void Connection() {
		try {
			is = socket.getInputStream();
			dis = new DataInputStream(is);

			os = socket.getOutputStream();
			dos = new DataOutputStream(os);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "연결 실패", "알림",
					JOptionPane.ERROR_MESSAGE);

		}// Stream설정 끝
		lg.setVisible(false);
		//newGUI = new ButtonPanelGUI(this,alc);
		newGUI = new MainGUI(this, alc);
		isLogin = true;
	}

	public void send_Message(String str) {
		try {
			dos.writeUTF(str);
		} catch (IOException e) {
		}
	}

	// 서버로부터 들어오는 모든 메세지
	private void receive_Message(String str) {

		st = new StringTokenizer(str, "/");

		String protocol = st.nextToken();
		String Message = st.nextToken();
		
		System.out.println("프로토콜 : " + protocol);
		System.out.println("내용 : " + Message);
		// 새로운 접속자
		/** -------------NewUser------------------ */
		if (protocol.equals("NewUser")) {

			newGUI.getfGUI().getListVector_InFriends().add(Message);
			newGUI.getfGUI().getList_InFriends().setListData(newGUI.getfGUI().getListVector_InFriends());
		}
			/** -------------Note------------------ */
			else if (protocol.equals("Note")) {
			String note = st.nextToken();
			this.note = note;
			new FileReceiver(Message);
			System.out.println("수신자가 파일리시버를 염");
			System.out.println(Message + "사용자로부터 온 쪽지" + note);
			send_Message("TransferFile/" + Message);
			System.out.println("발진자에게 파일전송하라고 서버로 보냄");
			
			/** -------------TransferFile------------------ */
		} else if (protocol.equals("TransferFile")) {
			new FileSender(Message);
			System.out.println("발신자가 파일센더를 염");
			send_Message("SoundPlay/" + friend + "/" + Message);
			System.out.println("파일 보냈으니 수신자에게 발신자이름 파일 재생하라고함");
			/** -------------SoundPlay------------------ */
		} else if (protocol.equals("SoundPlay")) {
			try {
				new SoundPlayer(Message);
				System.out.println("발신자이름파일 재생");
				JOptionPane.showMessageDialog(null, note, Message + "님으로 부터 쪽지",
						JOptionPane.CLOSED_OPTION);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/** -------------OldUser------------------ */
		} else if (protocol.equals("OldUser")) {
			newGUI.getfGUI().getListVector_InFriends().add(Message);
			newGUI.getfGUI().getList_InFriends().setListData(newGUI.getfGUI().getListVector_InFriends());
		}
			/** -------------user_list_update------------------ */
		  else if (protocol.equals("user_list_update")) {
			  newGUI.getfGUI().getList_InFriends().setListData(newGUI.getfGUI().getListVector_InFriends());
			
			/** -------------CreateRoom------------------ */
			// 방을 만들었을때
			System.out.println("방 만들었음?");
		} else if (protocol.equals("CreateRoom")) {
			// RoomInfo r = new RoomInfo(Message);
			// room_vc.add(r);
			
			/** -------------CreateRoomFail------------------ */
			// 실패했을경우
		} else if (protocol.equals("CreateRoomFail")) {
			JOptionPane.showMessageDialog(null, "방만들기 실패", "알림",
					JOptionPane.ERROR_MESSAGE);
			// 새로운 방을 만들었을때
			
			/** -------------New_Room------------------ */
		} else if (protocol.equals("New_Room")) {
		newGUI.gettGUI().getListVector_InTalk().add(Message);
			newGUI.gettGUI().getList_InTalk().setListData(newGUI.gettGUI().getListVector_InTalk());
			
			/** -------------JoinRoom------------------ */
		} else if (protocol.equals("JoinRoom")) {
			My_Room = Message;
			System.out.println("조인룸 프로토콜");
			JOptionPane.showMessageDialog(null, "채팅방에 입장했습니다", "알림",
					JOptionPane.INFORMATION_MESSAGE);

			Chatting_Popup ch = new Chatting_Popup(My_Room,this);
			chat_vc.add(ch);

		}
		if (protocol.equals("Chatting")) {
			String Room_name = Message;
			String Nickname = st.nextToken();
			String msg = st.nextToken();
			System.out.println("채팅 프로토콜");
			for (int i = 0; i < chat_vc.size(); i++) {
				Chatting_Popup ch = (Chatting_Popup) chat_vc.elementAt(i);
				// 만들고자 하는 방이 이미 존재할때
				if (ch.Room_name.equals(Room_name)) {
					System.out.println("에러 여기?");
					System.out.println(Nickname + " : " + msg);
					ch.ChattingScreen.append(Nickname + " : " + msg + "\n");
					break;
				}
			}
		} else if (protocol.equals("AlreadyRoom")) {
			JOptionPane.showMessageDialog(null, "이미 접속해있는 방입니다.", "알림",
					JOptionPane.ERROR_MESSAGE);
		}

		if (protocol.equals("OldRoom")) {
			newGUI.gettGUI().getListVector_InTalk().add(Message);
		} else if (protocol.equals("room_list_update")) {
			
			newGUI.gettGUI().getList_InTalk().setListData(newGUI.gettGUI().getListVector_InTalk());
		} else if (protocol.equals("User_Out")) {
			newGUI.getfGUI().getListVector_InFriends().remove(Message);
			
		} else if (protocol.equals("OutChattingRoom")) {
			String Room_name = Message;
			System.out.println(12);
			// ch.frmChatting.dispose();
			for (int i = 0; i < chat_vc.size(); i++) {
				Chatting_Popup ch = (Chatting_Popup) chat_vc.elementAt(i);
				// 만들고자 하는 방이 이미 존재할때
				if (ch.Room_name.equals(Room_name)) {
					System.out.println("에러 여기?");
					ch.frmChatting.dispose();
					System.out.println("방지움?");
					chat_vc.remove(ch);
					break;
				}
			}
		} else if (protocol.equals("DeleteRoom")) {
			String Room_name = Message;
			newGUI.gettGUI().getListVector_InTalk().remove(Room_name);
			System.out.println("방 리스트에서 뺌");
		}

	}



	/** -----------------class Roominfo------------------- */
	class RoomInfo {
		private String Room_name;

		public RoomInfo(String str) {
			this.Room_name = str;
		}

	}

	public static void main(String[] args) {
		Client cl =new Client();

	}



}
