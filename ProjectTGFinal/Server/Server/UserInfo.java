package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

class UserInfo extends Thread {
	private OutputStream os;
	private InputStream is;
	private DataOutputStream dos;
	private DataInputStream dis;
	private Socket user_socket;
	private String Nickname = "";
	private boolean RoomCh = true;
	private int count =0;
	private Server server;
	private StringTokenizer st;
	UserInfo(Socket soc, Server server) {
		this.user_socket = soc;
		this.server = server;
		UserNetwork();
	}

	// 네트워크 자원 설정
	/*------------------UserNetwork--------------------- */
	private void UserNetwork() {
		try {
			is = user_socket.getInputStream();
			dis = new DataInputStream(is);

			os = user_socket.getOutputStream();
			dos = new DataOutputStream(os);

			Nickname = dis.readUTF(); // 사용자의 닉네임을 받는다.
			server.setNickname(Nickname);
				server.textArea.append(Nickname + " : 사용자 접속!\n");

				// 기존 사용자들에게 새로운 사용자를 알림
			System.out.println("기존 접속된 사용자 수 : " + server.user_vc.size());
			
			// 기존의 사용자에게 자신을 알린다.
			BroadCast("NewUser/" + Nickname);
			
			// 자신에게 기존 사용자를 받아오는 부분
			for (int i = 0; i < server.user_vc.size(); i++) {
				UserInfo u = (UserInfo) server.user_vc.elementAt(i);
				send_Message("OldUser/" + u.Nickname);
			}
			// 자신에게 기존 방 목록을 받아오는 부분
			for (int i = 0; i < server.room_vc.size(); i++) {
				RoomInfo r = (RoomInfo) server.room_vc.elementAt(i);
				send_Message("OldRoom/" + r.Room_name);
			}
			send_Message("room_list_update/ ");
			// 기존 사용자들에게 알린 후 Vector에 자신(UserInfo)을 추가
			server.user_vc.add(this);
			BroadCast("user_list_update/ ");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Stream설정 에러", "알림",
					JOptionPane.ERROR_MESSAGE);
		}
	
			
	}

	// Thread에서 처리할 내용
	public void run() {
		while (true) {
			try {
				String msg = dis.readUTF();
				System.out.println(1);
				server.textArea.append(Nickname + "사용자로부터 들어온 메세지" + msg + "\n");
				System.out.println(2);
				receive_Message(msg);
			} catch (IOException e) {
				server.textArea.append(Nickname + " : 사용자 접속 끊어짐\n");
				try {
					dos.close();
					dis.close();
					user_socket.close();
					server.user_vc.remove(this);
					BroadCast("User_Out/" + Nickname);
					BroadCast("user_list_update/ ");
				} catch (IOException e1) {
				}
				break;
			} // 메세지 수신
		}
	}// run 메소드 끝

	// 클라이언트로부터 들어오는 메세지 처리

	private void receive_Message(String str) {
		st = new StringTokenizer(str, "/");
		System.out.println("서버야 메세지 받고있음?");
		String protocol = st.nextToken();
		String message = st.nextToken();

		System.out.println("프로토콜 : " + protocol);
		System.out.println("메세지 : " + message);
		/** -------------SoundPlay------------------ */
		if (protocol.equals("SoundPlay")) {
			String receiver = message;
			String sender = st.nextToken();
			for (int i = 0; i < server.user_vc.size(); i++) {
				UserInfo u = (UserInfo) server.user_vc.elementAt(i);
				if (u.Nickname.equals(receiver)) {
					System.out.println("수신자를 찾아서");
					u.send_Message("SoundPlay/" + sender);
					System.out.println("발신자이름의 파일을 재생하라함");
				}
			}
		}
		/** -------------TransferFile------------------ */
		if (protocol.equals("TransferFile")) {
			for (int i = 0; i < server.user_vc.size(); i++) {
				UserInfo u = (UserInfo) server.user_vc.elementAt(i);
				if (u.Nickname.equals(message)) {

					u.send_Message("TransferFile/" + message);
					System.out.println("발신자를 찾아서 파일보내라함");
				}
			}
		}
		
		/** -------------NOTE------------------ */
		if (protocol.equals("Note")) {
			/*
			 * protocol = Note message = user note = 받는내용
			 */
			String note = st.nextToken();
			System.out.println("받는사람 : " + message);
			System.out.println("보낼내용 : " + note);

			// 벡터에서 해당 사용자를 찾아서 메세지 전송
			for (int i = 0; i < server.user_vc.size(); i++) {
				UserInfo u = (UserInfo) server.user_vc.elementAt(i);
				if (u.Nickname.equals(message)) {
					u.count++;
					u.send_Message("Note/" + Nickname + "/" + note);
					// Note/User1/~~~~
				}
			}
			/** ------------CreateRoom-- ---------------- */
		} else if (protocol.equals("CreateRoom")) {
			/*
			 * 1. 현재 같은 방이 존재 하는지 확인한다.
			 */
			for (int i = 0; i < server.room_vc.size(); i++) {
				RoomInfo r = (RoomInfo) server.room_vc.elementAt(i);
				// 만들고자 하는 방이 이미 존재할때
				if (r.Room_name.equals(message)) {
					send_Message("CreateRoomFail/ok");
					RoomCh = false;
					break;
				}

			}// for문 끝
				// 방을 만들 수 있을때
			if (RoomCh) {
				System.out.println("서버야 방 만들어짐?");
				RoomInfo new_room = new RoomInfo(message, this, server);
				server.room_vc.add(new_room); // 전체 방 벡터에 방을 추가
				System.out.println("서버야 방 만들어짐?2");
				send_Message("CreateRoom/" + message);
				BroadCast("New_Room/" + message);
			}
			RoomCh = true;
		}// esle if문 끝
		/** ------------Chatting--------------------- **/
		else if (protocol.equals("Chatting")) {
			String Room_name = message;
			try {
				System.out.println("서버야 채팅메세지 받음?");
				System.out.println(protocol);
				System.out.println(Room_name);
				String msg = st.nextToken();
				System.out.println(msg);
				for (int i = 0; i < server.room_vc.size(); i++) {
					RoomInfo r = (RoomInfo) server.room_vc.elementAt(i);
					// 해당 방을 찾았을때
					if (r.Room_name.equals(message)) {
						System.out.println("ChattingRoom쪽 브로드캐스트룸");
						r.BroadCast_Room("Chatting/" + Room_name + "/" + Nickname + "/" + msg);
					}
				}
			} catch (NoSuchElementException e) {
				// JOptionPane.showMessageDialog(null,
				// "채팅쪽에 문제있음","알림",JOptionPane.ERROR_MESSAGE);

			}
		}// esle if
		/** ------------JoinRoom--------------------- **/
		else if (protocol.equals("JoinRoom")) {
			String Room_name = message;
			System.out.println(3);
			int count =0;
			for (int i = 0; i < server.room_vc.size(); i++) {					//방의 개수만큼 포문을 돌린다.	
				RoomInfo r = (RoomInfo) server.room_vc.elementAt(i);//하나씩 뽑아낸다.
				
				if (r.Room_name.equals(Room_name)) {				//방이름이 유저가 선택한 방이름과 같다면,(방선택)
					System.out.println(4);
					System.out.println("지금 방안의 있는수 : "+ r.Room_user_vc.size());	//방안의 인원수 체크
				
					if(r.Room_user_vc.size()==0){							//방 안에 아무도 없다면
					r.BroadCast_Room("Chatting/"+Room_name+"/"+"알림/******" + Nickname+ "님이 입장하셨습니다*****"); //입장메세지보내고
					r.Add_User(this);												//자신을 추가한다.	
					System.out.println("앞지금 방안의 있는수 : "+ r.Room_user_vc.size());	//방안에 있는수 체크
					send_Message("JoinRoom/" + Room_name);	//클라이언트로 프로토콜 전송.
					break;
					}
					
						for(int k=0; k<r.Room_user_vc.size(); k++){	//방안의 유저수만큼 포문을 돌린다.
							System.out.println(r.Room_user_vc.size());
							System.out.println(5);
							UserInfo user =  (UserInfo) r.Room_user_vc.elementAt(k);	//유저를 한명씩 뽑아낸뒤  user에 저장한다.
							System.out.println(6);
							System.out.println(user.Nickname);
							System.out.println(this.Nickname);
						
							if(user.Nickname.equals(this.Nickname)){ //방에서 뽑아낸 유저와 자신의 이름이 같다면
								System.out.println(user.Nickname);
								System.out.println(this.Nickname);
								System.out.println(7);
								send_Message("AlreadyRoom/"+Room_name);//이미 들어간 방이라고 알린다.
								System.out.println(8);
							}else{
								count++;
								System.out.println(count);
							}
				
							if(count==r.Room_user_vc.size()){
								r.BroadCast_Room("Chatting/"+Room_name+"/"+"알림/******" + Nickname+ "님이 입장하셨습니다*****");//내가 입장했다는 메세지 보내고
								System.out.println(10);
								r.Add_User(this);											//나자신을 추가한다
								System.out.println("뒤지금 방안의 있는수 : "+ r.Room_user_vc.size());
								send_Message("JoinRoom/" + Room_name);//클라이언트로 프로토콜 전송
								break;
							}
							}
						
					}
				}//for문 끝
			}
		/*------------------OutChattingRoom--------------------- */
		else if (protocol.equals("OutChattingRoom")) {
			String Room_name = message;
			for (int i = 0; i < server.room_vc.size(); i++) {
				RoomInfo r = (RoomInfo) server.room_vc.elementAt(i);
				if (r.Room_name.equals(Room_name)) {
					System.out.println(6);
					r.Delete_User(this);
					System.out.println(10);
					r.BroadCast_Room("Chatting/"+Room_name+"/"+"알림/******" + Nickname+ "님이 퇴장하셨습니다*****");
					System.out.println(11);
					send_Message("OutChattingRoom/" + Room_name);
					if(r.Room_user_vc.size()==0){
						server.room_vc.remove(r);
						System.out.println("방 삭제메세지 보냄");
						BroadCast("DeleteRoom/"+Room_name);
						System.out.println("DeleteRoom/"+Room_name);
						BroadCast("room_list_update/"+Room_name);
					}
				}
			}
		}
	}

	/*-------------------BroadCast----------------------- */
	// 전체 사용자에게 메세지 보내는 부분
	private void BroadCast(String str) {

		for (int i = 0; i < server.user_vc.size(); i++) {
			UserInfo u = (UserInfo) server.user_vc.elementAt(i);
			u.send_Message(str);
		}
	}

	/*------------------send_Message--------------------- */
	// 문자열을 받아서 전송
	public void send_Message(String str) {
		try {
			dos.writeUTF(str);
		} catch (IOException e) {
		}
	}

}// UserInfo class끝