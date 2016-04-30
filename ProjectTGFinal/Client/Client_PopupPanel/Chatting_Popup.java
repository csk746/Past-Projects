package Client_PopupPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Client.Client;
import ImgProcess.ImgProcess;


public class Chatting_Popup implements ActionListener, KeyListener {
		ImgProcess img = new ImgProcess();
		Calendar cal = Calendar.getInstance();
		public JFrame frmChatting = new JFrame();
		JButton outChattingRoom = new JButton("Out");
		JTextArea Condition = new JTextArea();
		JTextArea Today = new JTextArea("Date : " + cal.get(Calendar.YEAR)
				+ ". " + (cal.get(Calendar.MONTH) + 1) + ". "
				+ cal.get(Calendar.DATE));
		public JTextArea ChattingScreen = new JTextArea();
		JTextField Chattinginput = new JTextField("");
		JButton Send = new JButton("Send");
		JScrollBar WScroll; // 가로스크롤
		// 추가하기위한 레이아웃
		public String Room_name;
		JScrollPane js = new JScrollPane(ChattingScreen);
		Client cl;

		public Chatting_Popup(String Room_name, Client cl) {
			this.cl = cl;
			this.Room_name = Room_name;
			Condition.append("Room_name : " + Room_name);
			init();
			outChattingRoom.addActionListener(this);
			Chattinginput.addKeyListener(this);
			Send.addActionListener(this);

		}

		public void init() {
			img.MyPanel_InChatting();
			img.setBounds(0, 0, 300, 600);
			
			frmChatting = new JFrame();
			frmChatting.setBounds(100, 100, 300, 600);
			frmChatting.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frmChatting.getContentPane().setLayout(null);
			
			js.setOpaque(false);
			js.setBorder(javax.swing.BorderFactory.createEmptyBorder());
			js.setBounds(21, 61, 256, 458);
			frmChatting.getContentPane().add(js);
			
			Chattinginput = new JTextField();
			Chattinginput.setBounds(21, 531, 206, 36);
			frmChatting.getContentPane().add(Chattinginput);
			Chattinginput.setColumns(10);
			
			Send.setBounds(231, 531, 46, 36);
			frmChatting.getContentPane().add(Send);
			
			outChattingRoom.setBounds(216, 27, 61, 30);
			frmChatting.getContentPane().add(outChattingRoom);
			
			Today.setBounds(25, 12, 225, 15);
			Today.setFont(new Font("Nexa Light", Font.PLAIN, 15));
			frmChatting.getContentPane().add(Today);
			Today.setEditable(false);
			Today.setForeground(Color.white);
			Condition.setEditable(false);
			Today.setOpaque(false);
			Today.setBorder(javax.swing.BorderFactory.createEmptyBorder());
			Condition.setOpaque(false);
			Condition.setForeground(Color.white);
			Condition.setBorder(javax.swing.BorderFactory.createEmptyBorder());
			
			ChattingScreen.setLineWrap(true);
			Condition.setBounds(24, 33, 180, 20);
			Condition.setFont(new Font("Nexa Light", Font.PLAIN, 15));
			frmChatting.getContentPane().add(Condition);
			WScroll = new JScrollBar(Scrollbar.HORIZONTAL); 
			js.setVisible(true);
			js.getVerticalScrollBar().addAdjustmentListener(
					new AdjustmentListener() {
						// 스크롤바만 가져온다.
						@Override
						// 그 스크롤바가 움직이
						public void adjustmentValueChanged(AdjustmentEvent e) {
							JScrollBar jsb = (JScrollBar) e.getSource();
							jsb.setValue(jsb.getMaximum());
						}
					});
			
			frmChatting.setVisible(true);
			frmChatting.getContentPane().add(img);
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == Send) {
				System.out.println("Chatting/" + Room_name);
				cl.send_Message("Chatting/" + Room_name + "/"
						+ Chattinginput.getText().trim());
				System.out.println("전송버튼 누르면 보내는 메세지 :" + "Chatting/"
						+ Room_name + "/" + Chattinginput.getText().trim());
				// Chatting + 방이름 + 내용
				Chattinginput.setText("");
				Chattinginput.requestFocus();
				System.out.println("채팅 전송 버튼 클릭");
			} else if (e.getSource() == outChattingRoom) {
				cl.send_Message("OutChattingRoom/" + Room_name);
				System.out.println("방 나가기버튼 클릭");
			}
		}
		
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == 10) {
				cl.send_Message("Chatting/" + Room_name + "/"
						+ Chattinginput.getText().trim());
				System.out.println("전송버튼 누르면 보내는 메세지 :" + "Chatting/"
						+ Room_name + "/" + Chattinginput.getText().trim());
				// Chatting + 방이름 + 내용
				Chattinginput.setText("");
				Chattinginput.requestFocus();
			}

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}
		
	}