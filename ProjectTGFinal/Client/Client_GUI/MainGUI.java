package Client_GUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Client.Client;
import ImgProcess.ImgProcess;
import autoLogin.AutoLogin_Client;

public class MainGUI implements ActionListener{

	ImgProcess img = new ImgProcess();
	private JFrame GUIframe;
	public JFrame getGUIframe() {
		return GUIframe;
	}
	public void setGUIframe(JFrame gUIframe) {
		GUIframe = gUIframe;
	}

	public JTextField FriendstextField;
	public JTextField getFriendstextField() {
		return FriendstextField;
	}
	
	public void setFriendstextField(JTextField friendstextField) {
		FriendstextField = friendstextField;
	}
	
	JLayeredPane bg = new JLayeredPane();
	JPanel cont = new JPanel();
	JButton btnFriends_main = new JButton("");
	JButton btnTalk_main = new JButton("");
	JButton btnSetting_main = new JButton("");
	
	JLayeredPane FriendsPanel = new JLayeredPane();
	public JButton btnFind = new JButton("");

	JLayeredPane TalkPanel = new JLayeredPane();
	public JButton btnAddRoom = new JButton("");
	public JButton btnJoinRoom = new JButton("");
	
	private JButton btnSetting = new JButton("");
	public JButton getBtnSetting() {
		return btnSetting;
	}
	public void setBtnSetting(JButton btnSetting) {
		this.btnSetting = btnSetting;
	}

	JLayeredPane SettingPanel = new JLayeredPane();
	JButton btnDevinfo = new JButton("");
	private JButton btnLogout = new JButton("");
	public JButton getBtnLogout() {
		return btnLogout;
	}
	public void setBtnLogout(JButton btnLogout) {
		this.btnLogout = btnLogout;
	}

	CardLayout cardlayout = new CardLayout();
	
	private Client cl;
	private Setting_Function sGUI;
	public Setting_Function getsGUI() {
		return sGUI;
	}
	public void setsGUI(Setting_Function sGUI) {
		this.sGUI = sGUI;
	}

	private Talk_Function tGUI ;
	public Talk_Function gettGUI() {
		return tGUI;
		
	}
	public void settGUI(Talk_Function tGUI) {
		this.tGUI = tGUI;
	}

	private Friends_Function fGUI ;
	public Friends_Function getfGUI() {
		return fGUI;
	}
	public void setfGUI(Friends_Function fGUI) {
		this.fGUI = fGUI;
	}
	public MainGUI(Client cl, AutoLogin_Client alc){
		this.cl = cl;
		sGUI=new Setting_Function(cl,this,alc);
		tGUI = new Talk_Function(cl,this);
		fGUI = new Friends_Function(cl,this);
		init();
	}

	public void init() {
		GUIframe = new JFrame();
		
		GUIframe.getContentPane().setForeground(Color.WHITE);
		GUIframe.setBounds(0, 0, 300, 600);
		GUIframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUIframe.getContentPane().setLayout(null);
		
		cont.setBounds(100, 6, 195, 566);
		
		img.MyPanel_bg();
		img.setBounds(0, 0, 300, 600);
		
		JLabel lblFriends = new JLabel("F r i e n d s");
		lblFriends.setFont(new Font("Nexa Light", Font.BOLD, 15));
		lblFriends.setHorizontalAlignment(SwingConstants.CENTER);
		lblFriends.setBounds(50, 6, 102, 16);
		FriendsPanel.add(lblFriends);
		
		FriendstextField = new JTextField();
		FriendstextField.setBounds(6, 34, 116, 28);
		FriendsPanel.add(FriendstextField);
		FriendstextField.setColumns(10);
		
		btnFind.setBounds(121, 34, 65, 25);
		FriendsPanel.add(btnFind);
		btnFind.setIcon(new ImageIcon("Find.png"));
		JScrollPane scrollPane_InFriends = new JScrollPane(fGUI.getList_InFriends());
		scrollPane_InFriends.setBounds(6, 74, 185, 474);
		FriendsPanel.add(scrollPane_InFriends);
		
		btnAddRoom.setBounds(6, 34, 90, 30);
		TalkPanel.add(btnAddRoom);
		btnAddRoom.setIcon(new ImageIcon("AddRoom.png"));
		
		btnJoinRoom.setBounds(100, 34, 92, 29);
		TalkPanel.add(btnJoinRoom);
		
		btnJoinRoom.setIcon(new ImageIcon("JoinRoom.png"));
		JScrollPane scrollPane_InTalk = new JScrollPane(tGUI.getList_InTalk());
		scrollPane_InTalk.setBounds(6, 74, 185, 474);
		TalkPanel.add(scrollPane_InTalk);
		
		JLabel lblTAL = new JLabel("T a l k");
		lblTAL.setFont(new Font("Nexa Light", Font.BOLD, 15));
		lblTAL.setHorizontalAlignment(SwingConstants.CENTER);
		lblTAL.setBounds(70, 6, 61, 16);
		TalkPanel.add(lblTAL);
		
		btnSetting.setBounds(41, 153, 110, 70);
		SettingPanel.add(btnSetting);
		btnSetting.setIcon(new ImageIcon("Setting.png"));
		btnDevinfo.setBounds(41, 232, 110, 70);
		SettingPanel.add(btnDevinfo);
		btnDevinfo.setIcon(new ImageIcon("DevInfo.png"));
		btnLogout.setBounds(41, 314, 110, 70);
		SettingPanel.add(btnLogout);
		btnLogout.setIcon(new ImageIcon("Logout.png"));
		
		JLabel lblSET = new JLabel("S e t t i n g");
		lblSET.setFont(new Font("Nexa Light", Font.BOLD, 15));
		lblSET.setHorizontalAlignment(SwingConstants.CENTER);
		lblSET.setBounds(52, 6, 87, 16);
		SettingPanel.add(lblSET);
		
		btnFriends_main.setBounds(12, 135, 80, 70);
		btnFriends_main.setIcon(new ImageIcon("FriendButton.png"));
		GUIframe.getContentPane().add(btnFriends_main);
		
		btnTalk_main.setBounds(12, 237, 80, 70);
		btnTalk_main.setIcon(new ImageIcon("TalkButton.png"));
		GUIframe.getContentPane().add(btnTalk_main);
		
		btnSetting_main.setBounds(12, 339, 80, 70);
		btnSetting_main.setIcon(new ImageIcon("SettingButton.png"));
		GUIframe.getContentPane().add(btnSetting_main);
		
		cont.setLayout(cardlayout);
		cont.add("Friends",FriendsPanel);
		cont.add("Talk",TalkPanel);
		cont.add("Setting",SettingPanel);
		cont.setOpaque(false);
		btnFriends_main.addActionListener(this);
		btnTalk_main.addActionListener(this);
		btnSetting_main.addActionListener(this);
		
		cont.setBounds(100, 6, 195, 566);
		GUIframe.getContentPane().add(cont);
		GUIframe.getContentPane().add(img);
		GUIframe.setVisible(true);
	}
	public CardLayout getCardLayout(){
		return cardlayout;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnFriends_main) {
			getCardLayout().show(cont, "Friends");

		} else if (e.getSource() == btnTalk_main) {
			getCardLayout().show(cont, "Talk");

		} else if (e.getSource() == btnSetting_main) {
			getCardLayout().show(cont, "Setting");
		}
		
	}
}
