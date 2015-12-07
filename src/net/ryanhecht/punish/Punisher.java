package net.ryanhecht.punish;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import net.ryanhecht.util.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Punisher implements CommandExecutor {

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
		ResultSet reasonsq;
		//gets possible punish reasons. I'm sure there's a more efficient way, but yolo
				String strreasons="";
				ArrayList<String> reasons = new ArrayList<String>();
				try {
					reasonsq = Main.statement.executeQuery("SHOW COLUMNS FROM Punishments LIKE 'Reason'");
						reasonsq.next();
						strreasons=reasonsq.getString("Type").substring(reasonsq.getString("Type").indexOf("enum")+5);
						strreasons=strreasons.replace("'", "");
						strreasons=strreasons.replace(")", "");
						for(String s : strreasons.split(",")) {
							reasons.add(s);
						}
					
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				
				for(String s : reasons) {
					if(potreason.equalsIgnoreCase(s)) {
						return true;
					}
				}
				return false;
	}
	public void addRecord(String user, String punisher, String reason) throws SQLException {
		try {
			Main.statement.executeUpdate("INSERT INTO Punishments VALUES ('" + user +"' , '" +punisher+"' , '" + reason + "' , " + "CURRENT_TIMESTAMP" + ", '" + getUUID.get(user.toString()) + "', null )");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
