package Client_GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JOptionPane;

import Client.Client;

public class Friends_Function implements ActionListener,MouseListener {
	// FriendsGUI 변수

		private Vector ListVector_InFriends = new Vector();
		public Vector getListVector_InFriends() {
			return ListVector_InFriends;
		}

		public void setListVector_InFriends(Vector listPanel_InFriends) {
			ListVector_InFriends = listPanel_InFriends;
		}

		private JList List_InFriends = new JList(ListVector_InFriends);
		public JList getList_InFriends() {
			return List_InFriends;
		}

		public void setList_InFriends(JList list_InFriends) {
			List_InFriends = list_InFriends;
		}

		Client cl;
		MainGUI newGUI;
		public Friends_Function(Client cl,MainGUI newGUI){
			this.cl = cl;
			this.newGUI = newGUI;
		
			newGUI.btnFind.addActionListener(this);
			List_InFriends.setFixedCellHeight(30);
			List_InFriends.setCellRenderer(new DefaultListCellRenderer() {
				public int getHorizontalAlignment() {
					return CENTER;
				}
			});
			List_InFriends.addMouseListener(this);
		}
		Friends_Function(){
			
		}
		

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == newGUI.btnFind) {
				System.out.println("findFriend버튼 누름");
				String friend = newGUI.FriendstextField.getText().trim();
				System.out.println(friend);
				for (int i = 0; i < ListVector_InFriends.size(); i++) {
					System.out.println(ListVector_InFriends.elementAt(i));
					if (friend.equals(ListVector_InFriends.elementAt(i))) {
						List_InFriends.setSelectedIndex(i);
					}
				}
			}			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {

				String friend = (String) List_InFriends.getSelectedValue();
				
				cl.setFriend(friend); 
				
				System.out.println(2);
				String note = JOptionPane.showInputDialog(friend + "에게 보낼메세지");
				System.out.println(3);
				cl.send_Message("Note/" + friend + "/" + note);
				System.out.println("도대체 보내는거임?");
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
}
