package net.ryanhecht.punish;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;
import java.util.ArrayList;

public class Main extends JavaPlugin {
	public File config = new File(getDataFolder(), "config.yml");
	Connection dbconnect;
	Statement statement;
	public void onEnable() {
		getCommand("punish").setExecutor(new Punisher());
		//getCommand("punishutil").setExecutor(new PunishUtilCommands());
		//make config///////////////////////////////////////////////////////////
		if(!config.exists()) {
			getLogger().info("Config file does not exist. Creating it now.");
			try { config.createNewFile();
			getLogger().info("Config file created successfully.");
			}
			catch (IOException e1) {e1.printStackTrace();}
		}
		//load data from config/////////////////////////////////////////////////
		else {
		YamlConfiguration configy = YamlConfiguration.loadConfiguration(config);
		String Url = "jdbc:mysql://" + configy.getString("IP") + "/" + configy.getString("DatabaseName");
		String user = configy.getString("user");
		String pw = configy.getString("password");
		//connect to db//////////////////////////////////////////////////////
		DatabaseUtility.setVals(Url, user, pw);
		try { dbconnect = DatabaseUtility.connect();
		getLogger().info("Connected to database successfully.");
		} 
		catch (SQLException e) {e.printStackTrace();}
		}
		//make statement/////////////////////////////////////////////////////
		try {
			statement = dbconnect.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//create table////////////////////////////////////////////////
		try {
			statement.execute("CREATE TABLE IF NOT EXISTS Punishments("
									+ "Player varchar(16), "
									+ "Punisher varchar(16), "
									+ "Reason enum('grief1','grief2','grief3','grief4','grief5','hacks','permex','disrespect','spam','lang','other'), "
									+ "Time timestamp, "
									+ "UUID varchar(50), "
									+ "OtherNames varchar(100) )");
		getLogger().info("Created Punishments table successfully.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			statement.executeQuery("CREATE TABLE IF NOT EXISTS Reports("
									+ "Player varchar(16),"
									+ "Reporter varchar(16),"
									+ "Reason varchar(100), "
									+ "Time timestamp )");
			getLogger().info("Created Reports table successfully.");
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		//gets possible punish reason enums to store in ArrayList to check submitted reasons. I'm sure there's a more efficient way, but yolo
		ResultSet reasonsq;		
		String strreasons="";
		try {
			reasonsq = statement.executeQuery("SHOW COLUMNS FROM Punishments LIKE 'Reason'");
			reasonsq.next();
			strreasons=reasonsq.getString("Type").substring(reasonsq.getString("Type").indexOf("enum")+5);
			strreasons=strreasons.replace("'", "");
			strreasons=strreasons.replace(")", "");
			for(String s : strreasons.split(",")) {
				Punisher.reasons.add(s);
			}
					
		} catch (SQLException e) {	
			e.printStackTrace();
		}
		finally {
			
		}
	}
	public void onDisable() {
		
	}
}
