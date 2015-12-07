package net.ryanhecht.punish;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Punisher implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		Player player;
		ResultSet reasonsq;
		String reasons="";
		try {
			reasonsq = Main.statement.executeQuery("SHOW COLUMNS FROM Punishments LIKE 'Reason'");
				reasonsq.next();
				reasons=reasonsq.getString("Type");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		if(sender instanceof Player) {
			player = (Player) sender;
		}
		else {
			return false;
		}
		/*
		String reason,reason2,reason3;
		String user = args[0];
		reason = args[1];
		if(args[2].equalsIgnoreCase("+")) {
			reason2 = args[2];
			if(args[3].equalsIgnoreCase("+")) {
				reason3 = args[3];
			}
		}
		
		*/
		player.sendMessage(reasons);
		
		
		return false;
	}

}
