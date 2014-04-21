package main.java.net.bigbadcraft.globalgroupmanager.commands;

import main.java.net.bigbadcraft.globalgroupmanager.GlobalGroupManager;
import main.java.net.bigbadcraft.globalgroupmanager.utils.Methods;
import main.java.net.bigbadcraft.globalgroupmanager.utils.Permission;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Version extends BaseCommand {
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!Methods.checkPermission(((Player) sender), Permission.VERSION)) return;
		if (args.length == 1) {
			Methods.makeMessage(sender, ChatColor.GREEN + "You are running version v" 
					+ GlobalGroupManager.getInstance().getDescription().getVersion()
			);
		}
	}

}
