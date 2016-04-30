package Client_GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import Client.Client;
import Client_PopupPanel.SoundSetting_Popup;
import autoLogin.AutoLogin_Client;

public class Setting_Function implements ActionListener{
	// SettingGUITest
	Client cl;
	MainGUI newGUI;
	AutoLogin_Client alc;
	
		
		Setting_Function(Client cl,MainGUI newGUI, AutoLogin_Client alc){
			this.cl = cl;
			this.newGUI = newGUI;
			this.alc = alc;
			newGUI.getBtnLogout().addActionListener(this);
			newGUI.getBtnSetting().addActionListener(this);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == newGUI.getBtnLogout()) {
				try {
					cl.getOs().close();
					cl.getIs().close();
					cl.getDos().close();
					cl.getDis().close();
					cl.getSocket().close();
					newGUI.getGUIframe().dispose();
					alc.getLoginDto().setId("null");
					alc.getLoginDto().setPw("null");
					alc.getLoginDto().setAutoLogin(false);
					alc.getDoAutoLogin().createCookie(alc.getLoginDto());
					new Client();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (e.getSource() == newGUI.getBtnSetting()) {
			 new SoundSetting_Popup(cl);
			 
			}
		}
}
