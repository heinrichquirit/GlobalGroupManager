package main.java.net.bigbadcraft.globalgroupmanager.commands;

import main.java.net.bigbadcraft.globalgroupmanager.GlobalGroupManager;
import main.java.net.bigbadcraft.globalgroupmanager.PlayerData;
import main.java.net.bigbadcraft.globalgroupmanager.utils.Methods;
import main.java.net.bigbadcraft.globalgroupmanager.utils.Permission;
import main.java.net.bigbadcraft.globalgroupmanager.utils.Syntax;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetGroup extends BaseCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!Methods.checkPermission(((Player) sender), Permission.GET_GROUP)) return;
		if (args.length < 2) {
			Methods.makeMessage(sender, ChatColor.RED + "Incorrect syntax, usage: " + Syntax.GET_GROUP);
			return;
		}
		else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("getgroup")) {
				PlayerData target = new PlayerData(args[1]);
				if (target.getPlayer() == null) {
					Methods.makeMessage(sender, ChatColor.RED + "You cannot view the group of an offline user.");
					return;
				}
				Methods.makeMessage(sender, ChatColor.GREEN + target.getPlayer().getName() 
						+ " is in group " + target.getCurrentGroup() 
						+ ", in worlds: " + GlobalGroupManager.getInstance().getAvailableWorlds()
				);
			}
		}
	}

}
