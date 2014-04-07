package main.java.net.bigbadcraft.globalgroupmanager.commands;

import java.util.ArrayList;
import java.util.List;

import main.java.net.bigbadcraft.globalgroupmanager.GlobalGroupManager;
import main.java.net.bigbadcraft.globalgroupmanager.utils.Methods;
import main.java.net.bigbadcraft.globalgroupmanager.utils.Permission;
import main.java.net.bigbadcraft.globalgroupmanager.utils.Syntax;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.base.Joiner;

public class RemovePerm extends BaseCommand {

	private GlobalGroupManager p;
	public RemovePerm(GlobalGroupManager instance) {
		p = instance;
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!Methods.checkPermission(((Player) sender), Permission.REMOVE_PERM_USER)) return;
		if (args.length < 3) {
			Methods.makeMessage(sender, ChatColor.RED + "Incorrect syntax, usage: " + Syntax.REMOVE_PERM_USER);
		}
		else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("removeperm")) {
				Player target = Bukkit.getPlayer(args[1]);
				String permission = args[2];
				if (target == null) {
					Methods.makeMessage(sender, ChatColor.RED + "You cannot remove permission from an offline user.");
					return;
				}
				if (target.isOp() || target.hasPermission("*")) {
					Methods.makeMessage(sender, ChatColor.RED + "Cannot remove permission from an OP or a user that"
							+ " inherits the '*' permission node."
					);
					return;
				}
				if (!target.hasPermission(permission)) {
					Methods.makeMessage(sender, ChatColor.RED + target.getName() + " does not have permission: " + permission);
					return;
				}
				List<String> list_world_names = new ArrayList<String>();
				for (World worlds : Bukkit.getWorlds()) {
					String world_names = worlds.getName();
					if (!p.getBlackList().contains(world_names)) {
						p.getPermMgr().playerRemove(world_names, target.getName(), permission);
						list_world_names.add(world_names);
					}
				}
				String nice_list = Joiner.on(", ").join(list_world_names) + ".";
				Methods.makeMessage(sender, ChatColor.GREEN + "Removed permission: " 
						+ permission + " for " + target.getName() 
						+ ", in worlds: " + nice_list
				);
			}
		}
	}

}
