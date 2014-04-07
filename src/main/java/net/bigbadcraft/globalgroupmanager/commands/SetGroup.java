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

public class SetGroup extends BaseCommand {

	private GlobalGroupManager p;
	public SetGroup(GlobalGroupManager instance) {
		p = instance;
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!Methods.checkPermission(((Player) sender), Permission.GROUP_SET)) return;
		if (args.length < 3) {
			Methods.makeMessage(sender, ChatColor.RED + "Incorrect syntax, usage: " + Syntax.GROUP_SET);
			return;
		}
		else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("setgroup")) {
				Player target = Bukkit.getPlayer(args[1]);
				String group = args[2];
				if (target == null) {
					Methods.makeMessage(sender, ChatColor.RED + "You cannot modify the rank of an offline user.");
					return;
				}
				if (!groupExists(group)) {
					Methods.makeMessage(sender, ChatColor.RED + group + " group does not exist.");
					return;
				}
				/*
				if (p.getChatMgr().playerInGroup(target, group)) {
					Methods.makeMessage(sender, ChatColor.RED + target.getName() + " is already in " + group + ".");
					return;
				}
				*/
				List<String> list_world_names = new ArrayList<String>();
				for (World worlds : Bukkit.getWorlds()) {
					String world_names = worlds.getName();
					String[] groups = p.getPermMgr().getPlayerGroups(worlds, target.getName());
					String previous_group = groups[0];
					if (!p.getBlackList().contains(world_names)) {
						p.getPermMgr().playerRemove(worlds, target.getName(), previous_group);
						p.getPermMgr().playerAdd(worlds, target.getName(), group);
						list_world_names.add(world_names);
					}
				}
				String nice_list = Joiner.on(", ").join(list_world_names) + ".";
				Methods.makeMessage(sender, ChatColor.GREEN + "Moved " + target.getName() + " to " 
						+ group + ", in worlds: " + nice_list
				);
				Methods.makeMessage(target, ChatColor.GREEN + sender.getName() + " has moved you to "
						+ group + ", in worlds: " + nice_list
				);
			}
		}
	}
	
	private boolean groupExists(String group) {
		for (String group_name : p.getChatMgr().getGroups()) {
			if (group_name.equalsIgnoreCase(group)) {
				return true;
			}
		}
		return false;
	}

}
