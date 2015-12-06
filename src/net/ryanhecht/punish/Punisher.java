package net.ryanhecht.punish;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Punisher implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
		}
		else {
			return false;
		}
		
		String user = args[0];
		
		
		
		return false;
	}

}
