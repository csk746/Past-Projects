package autoLogin;

public class AutoLoginDto {
	private String id;
	private String pw;
	private boolean autoLogin;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		System.out.println("저장할 아이디값: "+ id);
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public boolean isAutoLogin() {
		return autoLogin;
	}

	public void setAutoLogin(boolean autoLogin) {
		this.autoLogin = autoLogin;
	}

}
