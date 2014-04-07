package main.java.net.bigbadcraft.globalgroupmanager.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.java.net.bigbadcraft.globalgroupmanager.GlobalGroupManager;
import main.java.net.bigbadcraft.globalgroupmanager.utils.Methods;
import main.java.net.bigbadcraft.globalgroupmanager.utils.Syntax;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.google.common.base.Joiner;

public class CommandHandler implements CommandExecutor {

	private List<String> command_syntaxes = new ArrayList<String>();
	private String nice_list;
	private HashMap<String, BaseCommand> commands = new HashMap<String, BaseCommand>();
	
	private GlobalGroupManager p;
	public CommandHandler(GlobalGroupManager instance) {
		p = instance;
		loadCommands();
		loadCmdSyntaxes();
		nice_list = Joiner.on(", ").join(command_syntaxes) + ".";
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (cmd.getName().equalsIgnoreCase("ggm")) {
			if (args.length == 0) {
				Methods.makeMessage(sender, ChatColor.GREEN + "Commands: " + nice_list);
				return true;
			}
			String sub = args[0];
			if (!commands.containsKey(sub)) {
				Methods.makeMessage(sender, ChatColor.RED + sub + " command does not exist!");
				return true;
			}
			commands.get(sub).execute(sender, args);
		}
		return true;
	}
	
	private void loadCommands() {
		commands.put("setgroup", new SetGroup(p));
		commands.put("addperm", new AddPerm(p));
		commands.put("removeperm", new RemovePerm(p));
		commands.put("version", new Version(p));
	}
	
	private void loadCmdSyntaxes() {
		command_syntaxes.add(Syntax.ADD_PERM_USER);
		command_syntaxes.add(Syntax.REMOVE_PERM_USER);
		command_syntaxes.add(Syntax.GROUP_SET);
		command_syntaxes.add(Syntax.VERSION);
	}
	
}
