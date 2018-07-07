package git.janek79.javaeese.eese.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hibernate.cfg.Configuration;

public class JDBCConnectionTest {
	private String url;
	private String user;
	private String password;
	private String driverClass;
	
	public JDBCConnectionTest() {
		this("jdbc:mysql://db4free.net:3306/sql5vlp5dhq78?useSSL=false", "sqlfbatpp0u82", "hiberjava", "com.mysql.jdbc.Driver");
	}
	
	public JDBCConnectionTest(String url, String user, String password, String driverClass) {
		this.url = url;
		this.user = user;
		this.password = password;
		this.driverClass = driverClass;
	}



	public void testConnection() {
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e1) {
			System.out.println("Driver class not found");
		}
		
		try {
			DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println("Connetction with database failed");
		}
		
		System.out.println("Succesfull connection with database!");
	}
}
