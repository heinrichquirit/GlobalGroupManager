package main.java.net.bigbadcraft.globalgroupmanager.commands;

import main.java.net.bigbadcraft.globalgroupmanager.GlobalGroupManager;
import main.java.net.bigbadcraft.globalgroupmanager.PlayerData;
import main.java.net.bigbadcraft.globalgroupmanager.utils.Methods;
import main.java.net.bigbadcraft.globalgroupmanager.utils.Permission;
import main.java.net.bigbadcraft.globalgroupmanager.utils.Syntax;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddPerm extends BaseCommand {
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!Methods.checkPermission(((Player) sender), Permission.ADD_PERM_USER)) return;
		if (args.length < 3) {
			Methods.makeMessage(sender, ChatColor.RED + "Incorrect syntax, usage: " + Syntax.ADD_PERM_USER);
		}
		else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("addperm")) {
				PlayerData target = new PlayerData(args[1]);
				String permission = args[2].toLowerCase();
				if (target.getPlayer() == null) {
					Methods.makeMessage(sender, ChatColor.RED + "You cannot add permission to an offline user.");
					return;
				}
				if (target.getPlayer().hasPermission(permission)) {
					Methods.makeMessage(sender, ChatColor.RED + target.getPlayer().getName() 
							+ " is either OP or has the following permissions: *, " + permission
					);
					return;
				}
				target.addPermission(permission);
				Methods.makeMessage(sender, ChatColor.GREEN + "Added permission: " 
						+ permission + " for " + target.getPlayer().getName() 
						+ ", in worlds: " + GlobalGroupManager.getInstance().getAvailableWorlds()
				);
			}
		}
	}

}
