package userDB;

public class UserDto {
	private String id;
	private String pw;

	public String getId() {
		return id;
		
	}

	public void setId(String id) {
		System.out.println("setID:" + id);

		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		System.out.println("setPW:" + pw);
		this.pw = pw;
	}

}
