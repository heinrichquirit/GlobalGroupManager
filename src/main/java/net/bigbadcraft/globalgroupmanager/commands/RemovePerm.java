package main.java.net.bigbadcraft.globalgroupmanager.commands;

import main.java.net.bigbadcraft.globalgroupmanager.GlobalGroupManager;
import main.java.net.bigbadcraft.globalgroupmanager.PlayerData;
import main.java.net.bigbadcraft.globalgroupmanager.utils.Methods;
import main.java.net.bigbadcraft.globalgroupmanager.utils.Permission;
import main.java.net.bigbadcraft.globalgroupmanager.utils.Syntax;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemovePerm extends BaseCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!Methods.checkPermission(((Player) sender), Permission.REMOVE_PERM_USER)) return;
		if (args.length < 3) {
			Methods.makeMessage(sender, ChatColor.RED + "Incorrect syntax, usage: " + Syntax.REMOVE_PERM_USER);
		}
		else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("removeperm")) {
				PlayerData target = new PlayerData(args[1]);
				String permission = args[2];
				if (target.getPlayer() == null) {
					Methods.makeMessage(sender, ChatColor.RED + "You cannot remove permission from an offline user.");
					return;
				}
				if (target.getPlayer().isOp() || target.getPlayer().hasPermission("*")) {
					Methods.makeMessage(sender, ChatColor.RED + "Cannot remove permission from an OP or a user that"
							+ " inherits the '*' permission node."
					);
					return;
				}
				if (!target.getPlayer().hasPermission(permission)) {
					Methods.makeMessage(sender, ChatColor.RED + target.getPlayer().getName() + " does not have permission: " + permission);
					return;
				}
				target.removePermission(permission);
				Methods.makeMessage(sender, ChatColor.GREEN + "Removed permission: " 
						+ permission + " for " + target.getPlayer().getName() 
						+ ", in worlds: " + GlobalGroupManager.getInstance().getAvailableWorlds()
				);
			}
		}
	}

}
