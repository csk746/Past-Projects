package Client_PopupPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import soundControl.SoundPlayer;
import soundControl.SoundRecoder;
import Client.Client;
import ImgProcess.ImgProcess;


public class SoundSetting_Popup implements ActionListener {
	Client cl;
	ImgProcess img = new ImgProcess();
	private JFrame frame;
	private JTextField textField;
	JButton confirmAlarm = new JButton("알람 확인");		
	JButton recordAlarm = new JButton("알람 녹음");
	JLabel label = new JLabel("저장경로");
	JButton generalAlarm = new JButton("일반알람");
	JButton myAlarm = new JButton("내 알람");

	public SoundSetting_Popup(Client cl) {
		this.cl = cl;
		System.out.println("SoundSetting의id :"+cl.getId());
		initialize();
		frame.setVisible(true);
		recordAlarm.addActionListener(this);
		confirmAlarm.addActionListener(this);
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		img.MyPanel_SettingSound();
		img.setBounds(0, 0, 265, 138);
		frame = new JFrame();
		frame.setBounds(100, 100, 265, 138);
		frame.getContentPane().setLayout(null); 
		
		confirmAlarm.setBounds(137, 6, 88, 29);
		frame.getContentPane().add(confirmAlarm);
		
		recordAlarm.setBounds(24, 6, 88, 29);
		frame.getContentPane().add(recordAlarm);
		
		textField = new JTextField();
		textField.setBounds(91, 70, 134, 28);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		label.setBounds(39, 76, 61, 16);
		frame.getContentPane().add(label);
		
		generalAlarm.setBounds(24, 35, 88, 29);
		frame.getContentPane().add(generalAlarm);
		
		myAlarm.setBounds(137, 35, 88, 29);
		frame.getContentPane().add(myAlarm);
		frame.getContentPane().add(img);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == recordAlarm){
			 new SoundRecoder(cl.getId());
		}else if (e.getSource()==confirmAlarm) {
			try {
				System.out.println("컨펌알람버튼 먹음?");
				new SoundPlayer(cl.getId());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
