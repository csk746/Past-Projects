package Client_GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JOptionPane;

import Client.Client;

public class Talk_Function implements ActionListener{
	// TalkGUI 변수
		private Client cl;
		private MainGUI newGUI;
		private Vector ListVector_InTalk = new Vector();
		public Vector getListVector_InTalk() {
			return ListVector_InTalk;
		}

		public void setListVector_InTalk(Vector listPanel_InTalk) {
			ListVector_InTalk = listPanel_InTalk;
		}

		private JList List_InTalk = new JList(ListVector_InTalk);
		public JList getList_InTalk() {
			return List_InTalk;
		}

		public void setList_InTalk(JList list_InTalk) {
			List_InTalk = list_InTalk;
		}

	

		
		/** ----------톡GUI 메소드------------------ */
		public Talk_Function(Client cl, MainGUI newGUI) {
			this.cl = cl;
			this.newGUI = newGUI;
			newGUI.btnAddRoom.addActionListener(this);
			newGUI.btnJoinRoom.addActionListener(this);


			// 컴포넌트에 ActionEventListener장착
	
			List_InTalk.setFixedCellHeight(30);
			List_InTalk.setCellRenderer(new DefaultListCellRenderer() {
				public int getHorizontalAlignment() {
					return CENTER;
				}
			});
		}
	

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == newGUI.btnJoinRoom) {
				String JoinRoom = (String) List_InTalk.getSelectedValue();
				cl.send_Message("JoinRoom/" + JoinRoom);

				System.out.println("방 참여 버튼 클릭");
			} else if (e.getSource() == newGUI.btnAddRoom) {
				System.out.println("방추가버튼 클릭");
				String roomname = JOptionPane.showInputDialog("방이름");

				if (roomname != null) {
					cl.send_Message("CreateRoom/" + roomname);
				}
				System.out.println("방 만들기 버튼 클릭");
			}
			
		}
}
