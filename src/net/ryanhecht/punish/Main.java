package net.ryanhecht.punish;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;

public class Main extends JavaPlugin {
	public File config = new File(getDataFolder(), "config.yml");
	public static Connection dbconnect;
	public static Statement statement;
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
		try { dbconnect = DatabaseUtility.connect(Url, user, pw);
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
		getLogger().info("Created table successfully.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void onDisable() {
		
	}
}
