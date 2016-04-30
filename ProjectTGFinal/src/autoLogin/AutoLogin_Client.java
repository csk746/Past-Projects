package autoLogin;

import autoLogin.AutoLoginCookie;
import autoLogin.AutoLoginDto;

public class AutoLogin_Client {
	// AutoLogin관련
	
		private AutoLoginCookie doAutoLogin = new AutoLoginCookie();
		public AutoLoginCookie getDoAutoLogin() {
			return doAutoLogin;
		}
		public void setDoAutoLogin(AutoLoginCookie doAutoLogin) {
			this.doAutoLogin = doAutoLogin;
		}

		private AutoLoginDto loginDto = new AutoLoginDto();
		public AutoLoginDto getLoginDto() {
			return loginDto;
		}
		public void setLoginDto(AutoLoginDto loginDto) {
			this.loginDto = loginDto;
		}

		private boolean viaLoginGUI;
		public boolean isViaLoginGUI() {
			return viaLoginGUI;
		}
		public void setViaLoginGUI(boolean viaLoginGUI) {
			this.viaLoginGUI = viaLoginGUI;
		}
		
}
