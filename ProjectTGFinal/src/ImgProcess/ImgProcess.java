package ImgProcess;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Client.Client;

public class ImgProcess extends JPanel {
	BufferedImage img_InLogin ;//= cl.getBackImg_InLogin();
	BufferedImage img_InChatting;// = cl.getBackImg_InSetting();
	BufferedImage img_Inbg;// = cl.getBackImg_InSetting();
	BufferedImage img_SettingSound;
	BufferedImage img_Registeration;
	public void MyPanel_InLogin(){
		try {
			img_InLogin = ImageIO.read(new File("LoginBG.png"));
		} catch (IOException e) {
			System.out.println("이미지구현실패");
			System.exit(0);
		}
	}
	public void MyPanel_InChatting(){
		try {
			img_InChatting = ImageIO.read(new File("ChattingRoom.png"));
		} catch (IOException e) {
			System.out.println("이미지구현실패");
			System.exit(0);
		}
	}
	public void MyPanel_bg(){
		try {
			img_Inbg = ImageIO.read(new File("MainBG.png"));
		} catch (IOException e) {
			System.out.println("이미지구현실패");
			System.exit(0);
		}
	}
	public void MyPanel_SettingSound(){
		try {
			img_SettingSound = ImageIO.read(new File("SettingSound.png"));
		} catch (IOException e) {
			System.out.println("이미지구현실패");
			System.exit(0);
		}
	}
	public void MyPanel_Registeration(){
		try {
			img_SettingSound = ImageIO.read(new File("Registeration.png"));
		} catch (IOException e) {
			System.out.println("이미지구현실패");
			System.exit(0);
		}
	}

	
	public void paint(Graphics g) { // 이 JPanel에다 그림을 그리겠다고 하는 메서드
		g.drawImage(img_InLogin, 0, 0, null); // 저장해둔이미지를 가져와서 가로0세로0
													// 이렇게 넣어준다
		g.drawImage(img_InChatting, 0, 0, null);
		
		g.drawImage(img_Inbg, 0, 0, null);
		
		g.drawImage(img_SettingSound, 0, 0, null);
		
		g.drawImage(img_Registeration, 0, 0, null);
		
	}
		
}
