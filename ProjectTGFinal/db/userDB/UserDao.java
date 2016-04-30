package userDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import connect.ConnectDB;

public class UserDao {
	// DB에서 할 작업들을 다 메서드로 구현 시켜둔다.
	// DB에 회원 가입 시키기 (create 작업)

	public int createOne(UserDto dto) throws Exception {
		int r = 0;
		
			System.out.println("dao0");

			Connection conn = ConnectDB.open();
			System.out.println("dao1");
			String sql = "insert into test values(?,?)"; // 한 유저 정보를 추과하는 db
															// 실행문.
			PreparedStatement ps = (PreparedStatement) conn
					.prepareStatement(sql);
			System.out.println("dao2");

			ps.setString(1, dto.getId()); // 첫번째 물음표에 들어갈 정보
			ps.setString(2, dto.getPw()); // 두번째 물음표에 들어갈 정보
			System.out.println("dao3");

			r = ps.executeUpdate(); // 업데이트후 리턴값을 r에 저장.(1or0)
			System.out.println("dao4");

			ps.close(); // PreparedStatement를 닫
			conn.close(); // 커넥션을 끝는다.

		return r;
	}

	// id와 매칭되는 한 유저 불러오기.
	public UserDto readOne(String key) {
		UserDto dto = null;
		try {
			Connection conn = ConnectDB.open();

			String sql = "select * from test where id = ?"; // ? 저장된 정보를 다
																// 불러온다.
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setString(1, key);// ?가 키값이 된다. 

			ResultSet rs = ps.executeQuery(); 

			if (rs.next()) {
				dto = new UserDto();
				dto.setId(rs.getString("id"));// 불러온 id값 dto 저장.
				dto.setPw(rs.getString("pw"));// 불러온 pw값 dto 저장. 
			}
			rs.close();// 받은값 통로 닫기.
			ps.close();//보낸 통로 닫기.
			System.out.println("DAO DONE!");
			conn.close();// 커넥션 끈기.
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	// 모든 유저 목록 불러오
	public ArrayList<UserDto> readAll() {
		ArrayList<UserDto> list = new ArrayList<UserDto>(); // 유저들을 db에서 읽어와 저장할
															// 리스트.

		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
