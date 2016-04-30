package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDB {
	public static Connection open() throws Exception{
		//1.JDBC Driver Loading
		Class.forName("com.mysql.jdbc.Driver"); //JDBC Driver
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		
		try{
			String jdbcDriverUrl = "jdbc:mysql://localhost:3306/test?"
		+ "useUnicode=true&characterEncoding=utf8"; //JDBC URL
			String dbUser = "root"; //User ID
			String dbPw = "7467"; //User Password
			
			//2.Create DB Connection
			conn = DriverManager.getConnection(jdbcDriverUrl, dbUser, dbPw);
			
		}catch(SQLException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		System.out.println("DB CONNECTION SUCCESSED!");
		return conn; //dao에서 Connection을 쓸 수 있게 리턴
	}
	
	
}
