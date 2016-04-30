package autoLogin;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

public class AutoLoginCookie {
	private StringTokenizer infoDivider;
	private AutoLoginDto loginDto;

	public AutoLoginCookie() {
		loginDto = new AutoLoginDto();
	}

	// public AutoLoginCookie(String id, String pw, boolean autoLogin) {
	// this.id = id;
	// this.pw = pw;
	// this.autoLogin = autoLogin;
	//
	// createCookie(id, pw, autoLogin);
	// }

	// 자동 로그인 쿠키를 만드는 메서드.
	public void createCookie(AutoLoginDto loginDto) {
		String autoCookie = "TG Auto Login Cookie.txt";
		try {
			System.out.println("2.createCookie");
			PrintWriter outputStream = new PrintWriter(autoCookie);
			outputStream.println(loginDto.getId() + "/");
			outputStream.println(loginDto.getPw() + "/");
			outputStream.println(loginDto.isAutoLogin());
			outputStream.close();
			System.out.println("2.DONE!");

		} catch (FileNotFoundException e) {
			e.printStackTrace();

			System.out.println("2.createCookie ->Exception");
		}
	}

	// 자동 로그인 쿠키 정보를 읽는 메서드.
	public AutoLoginDto readCookie() {
		String cookieDetail = "";
		try {
			System.out.println("3.readeCookie");
			FileReader file = new FileReader("TG Auto Login Cookie.txt");
			BufferedReader reader = new BufferedReader(file);

			String line = reader.readLine();
			while (line != null) {
				cookieDetail += line;
				line = reader.readLine();
			}
			System.out.println(cookieDetail);
			System.out.println("3.readCookie -> Done");

		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//			new JOptionPane().showMessageDialog(null,
//					"쿠키 파일:'TG Auto Login Cookie.txt'가 없습니다.",
//					"FileNotFoundException Occured", JOptionPane.ERROR_MESSAGE);

			System.out.println("3.readCookie -> File NOT FOUND Error");
		} catch (IOException e) {
			e.printStackTrace();
			new JOptionPane().showMessageDialog(null,
					"쿠키 파일을 읽어오는중 I/O 에러가 발생 하였습니다.", "IOException Occured",
					JOptionPane.ERROR_MESSAGE);

			System.out.println("3.readCookie -> IO Error");
		}

		loginDto = retrieveLoginInfo(cookieDetail);

		return loginDto;
	}

	private AutoLoginDto retrieveLoginInfo(String cookieDetail) {
		try {
			System.out.println("4.retrieveLoginInfo" + "    " + cookieDetail);

			infoDivider = new StringTokenizer(cookieDetail, "/");

			loginDto.setId(infoDivider.nextToken());
			loginDto.setPw(infoDivider.nextToken());
			String autoLoginStatus = infoDivider.nextToken();

			if (autoLoginStatus.equals("true")) {
				loginDto.setAutoLogin(true);
			} else {
				loginDto.setAutoLogin(false);
			}
			System.out.println("4.retrieveLoginInfo -> DONE");
		} catch (NullPointerException e) {
			e.printStackTrace();
			new JOptionPane().showMessageDialog(null, "읽어온 파일에 로그인 정보가 없습니다.",
					"로그인 정보 없음", JOptionPane.ERROR_MESSAGE);
			System.out.println("4.retrieveLoginInfo -> Null point Error");
		}

		return loginDto;
	}

}
