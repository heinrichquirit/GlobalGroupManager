package main.java.net.bigbadcraft.globalgroupmanager.commands;

import main.java.net.bigbadcraft.globalgroupmanager.GlobalGroupManager;
import main.java.net.bigbadcraft.globalgroupmanager.GroupData;
import main.java.net.bigbadcraft.globalgroupmanager.utils.Methods;
import main.java.net.bigbadcraft.globalgroupmanager.utils.Permission;
import main.java.net.bigbadcraft.globalgroupmanager.utils.Syntax;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddGroupPerm extends BaseCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!Methods.checkPermission(((Player) sender), Permission.ADD_PERM_GROUP)) return;
		if (args.length < 3) {
			Methods.makeMessage(sender, ChatColor.RED + "Incorrect syntax, usage: " + Syntax.ADD_PERM_GROUP);
		}
		else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("addgperm")) {
				GroupData group_data = new GroupData(args[1]);
				String permission = args[2].toLowerCase();
				if (!group_data.groupExists()) {
					Methods.makeMessage(sender, ChatColor.RED + "You cannot add permission to a non existing group.");
					return;
				}
				if (group_data.hasPermission(permission)) {
					Methods.makeMessage(sender, ChatColor.RED + group_data.getGroup() + " has either permission: *, " + permission
					);
					return;
				}
				group_data.addPermission(permission);
				Methods.makeMessage(sender, ChatColor.GREEN + "Added permission: " 
						+ permission + " to " + group_data.getGroup()
						+ ", in worlds: " + GlobalGroupManager.getInstance().getAvailableWorlds()
				);
			}
		}
	}

}
