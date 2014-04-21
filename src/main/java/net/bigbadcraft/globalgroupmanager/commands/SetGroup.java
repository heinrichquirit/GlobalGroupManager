package main.java.net.bigbadcraft.globalgroupmanager.commands;

import main.java.net.bigbadcraft.globalgroupmanager.GlobalGroupManager;
import main.java.net.bigbadcraft.globalgroupmanager.PlayerData;
import main.java.net.bigbadcraft.globalgroupmanager.utils.Methods;
import main.java.net.bigbadcraft.globalgroupmanager.utils.Permission;
import main.java.net.bigbadcraft.globalgroupmanager.utils.Syntax;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetGroup extends BaseCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!Methods.checkPermission(((Player) sender), Permission.GROUP_SET)) return;
		if (args.length < 3) {
			Methods.makeMessage(sender, ChatColor.RED + "Incorrect syntax, usage: " + Syntax.GROUP_SET);
			return;
		}
		else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("setgroup")) {
				PlayerData target = new PlayerData(args[1]);
				String group = args[2];
				if (target.getPlayer() == null) {
					Methods.makeMessage(sender, ChatColor.RED + "You cannot modify the group of an offline user.");
					return;
				}
				if (!groupExists(group)) {
					Methods.makeMessage(sender, ChatColor.RED + group + " group does not exist.");
					return;
				}
				target.setGroup(group);
				String avail_worlds = GlobalGroupManager.getInstance().getAvailableWorlds();
				Methods.makeMessage(sender, ChatColor.GREEN + "Moved " + target.getPlayer().getName() + " to " 
						+ group + ", in worlds: " + avail_worlds
				);
				if (target.getPlayer().getName().equals(sender.getName())) return;
				Methods.makeMessage(target.getPlayer(), ChatColor.GREEN + sender.getName() + " has moved you to "
						+ group + ", in worlds: " + avail_worlds
				);
			}
		}
	}
	
	private boolean groupExists(String group) {
		for (String group_name : GlobalGroupManager.getInstance().getVaultPerm().getGroups()) {
			if (group_name.equalsIgnoreCase(group)) {
				return true;
			}
		}
		return false;
	}

}
