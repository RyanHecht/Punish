package net.ryanhecht.punish;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import net.ryanhecht.util.*;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Punisher implements CommandExecutor {
public static ArrayList<String> reasons = new ArrayList<String>();
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		Player player;

		
		if(sender instanceof Player) {
			player = (Player) sender;
		}
		else {
			return false;
		}
		
		
		if(args.length < 2) {
			return false;
		}
		
		String reason,reason2,reason3;
		String user = args[0];
		reason = args[1];
		if(!isReason(reason)) {
			player.sendMessage(ChatColor.RED + "That is not a valid reason. Try 'other'");
			return false;
		}
		if(args.length > 2) {
		if(args[2].equalsIgnoreCase("+")) {
			reason2 = args[2];
			if(args.length > 3) {
			if(args[3].equalsIgnoreCase("+")) {
				reason3 = args[3];
			}
			}
		}
		}
		try {
			addRecord(user,player.getName(),reason);
			player.sendMessage("Added successfully.");
		} catch (SQLException e) {
			player.sendMessage("Failure.");
			e.printStackTrace();
		}
		
		
		return false;
	}
	public boolean isReason(String potreason) {

				
				for(String s : reasons) {
					if(potreason.equalsIgnoreCase(s)) {
						return true;
					}
				}
				return false;
	}
	public void addRecord(String user, String punisher, String reason) throws SQLException {
		Connection c;
		Statement s;
		try {
			c=DatabaseUtility.connect();
		}
		catch (SQLException e) {
			throw e;
		}
		try {
			s=c.createStatement();
		}
		catch (SQLException e) {
			throw e;
		}
		try {
			s.executeUpdate("INSERT INTO Punishments VALUES ('" + user +"' , '" +punisher+"' , '" + reason + "' , " + "CURRENT_TIMESTAMP" + ", '" + getUUID.get(user.toString()) + "', null )");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
