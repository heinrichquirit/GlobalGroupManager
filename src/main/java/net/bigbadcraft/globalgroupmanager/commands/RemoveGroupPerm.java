package main.java.net.bigbadcraft.globalgroupmanager.commands;

import main.java.net.bigbadcraft.globalgroupmanager.GlobalGroupManager;
import main.java.net.bigbadcraft.globalgroupmanager.GroupData;
import main.java.net.bigbadcraft.globalgroupmanager.utils.Methods;
import main.java.net.bigbadcraft.globalgroupmanager.utils.Permission;
import main.java.net.bigbadcraft.globalgroupmanager.utils.Syntax;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveGroupPerm extends BaseCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!Methods.checkPermission(((Player) sender), Permission.REMOVE_PERM_GROUP)) return;
		if (args.length < 3) {
			Methods.makeMessage(sender, ChatColor.RED + "Incorrect syntax, usage: " + Syntax.REMOVE_PERM_GROUP);
		}
		else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("removegperm")) {
				GroupData group_data = new GroupData(args[1]);
				String permission = args[2];
				if (!group_data.groupExists()) {
					Methods.makeMessage(sender, ChatColor.RED + "You cannot remove permission from a non existing group.");
					return;
				}
				if (!group_data.hasPermission(permission)) {
					Methods.makeMessage(sender, ChatColor.RED + group_data.getGroup() + " does not have permission: " + permission);
					return;
				}
				group_data.removePermission(permission);
				Methods.makeMessage(sender, ChatColor.GREEN + "Removed permission: " 
						+ permission + " from " + group_data.getGroup()
						+ ", in worlds: " + GlobalGroupManager.getInstance().getAvailableWorlds()
				);
			}
		}
	}

}
