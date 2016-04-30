package Server;

import java.util.Vector;

class RoomInfo {
	Server server;
	public String Room_name;
	public String getRoom_name() {
		return Room_name;
	}

	public void setRoom_name(String room_name) {
		Room_name = room_name;
	}

	Vector Room_user_vc = new Vector();

	public RoomInfo(String str, UserInfo u, Server server) {
		this.server = server;
		this.Room_name = str;
		// this.Room_user_vc.add(u);
	}

	// 현재 방의 모든 사람에게 알린다.
	void BroadCast_Room(String str) {
		for (int i = 0; i < Room_user_vc.size(); i++) {
			UserInfo u = (UserInfo) Room_user_vc.elementAt(i);
			u.send_Message(str);
		}
	}

	void Add_User(UserInfo u) {
		this.Room_user_vc.add(u);
	}

	void Delete_User(UserInfo u) {
		System.out.println(7);
		System.out.println(this.Room_user_vc.indexOf(u));
		this.Room_user_vc.remove(u);
		System.out.println(8);
		System.out.println(Room_name + "에 있는 멤버의 수 : "
				+ Room_user_vc.size());
		System.out.println(9);
		System.out.println("기존 접속된 사용자 수 : " + server.user_vc.size());

	}
}