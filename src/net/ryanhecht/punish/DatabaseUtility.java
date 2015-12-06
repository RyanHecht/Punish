package net.ryanhecht.punish;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtility {
	public static Connection connect(String url, String user, String pw) throws SQLException {
		try { return DriverManager.getConnection(url, user, pw); } 
		catch (SQLException e) {
		throw e;
		}
    }
	
	
}

