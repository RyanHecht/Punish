package net.ryanhecht.punish;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtility {
	static String URL,USER,PW;
	public static Connection connect() throws SQLException {
		if(URL.isEmpty() || USER.isEmpty() || PW.isEmpty()) return null;
		try { return DriverManager.getConnection(URL, USER, PW); } 
		catch (SQLException e) {
		throw e;
		}
    }
	public static void setVals(String url, String user, String pw) {
		URL=url;
		USER=user;
		PW=pw;
	}
	
	
}

