package main.java.net.bigbadcraft.globalgroupmanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import main.java.net.bigbadcraft.globalgroupmanager.commands.BaseCommand;
import main.java.net.bigbadcraft.globalgroupmanager.commands.CommandHandler;
import main.java.net.bigbadcraft.globalgroupmanager.utils.Methods;
import net.milkbowl.vault.Metrics;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class GlobalGroupManager extends JavaPlugin {
	
	private Chat chat = null;
	private Permission permission = null;
	
	ArrayList<BaseCommand> a = new ArrayList<BaseCommand>();
	
	/* Configuration settings */
	private boolean useMetrics;
	private List<String> worlds_blacklist;
	
	private String cmd_starter = "/ggm";
	private String trimmed_cmd = cmd_starter.substring(1, cmd_starter.length());
	
	public void onEnable() {
		saveDefaultConfig();
		useMetrics = getConfig().getBoolean("use-metrics");
		worlds_blacklist = getConfig().getStringList("worlds");
		getCommand(trimmed_cmd).setExecutor(new CommandHandler(this));
		setupChat();
		setupPermissions();
		if (useMetrics == false) return;
		try {
		    Metrics metrics = new Metrics(this);
		    metrics.start();
		    Methods.log(Level.INFO, "Started Metrics for data submission.");
		} catch (IOException e) {
		    // Failed to submit the stats :-(
			Methods.log(Level.SEVERE, "Metrics failed to submit data!");
		}
	}
	
	public List<String> getBlackList() {
		return worlds_blacklist;
	}
	
	private boolean setupChat() {
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }
        return (chat != null);
    }
	
	public Chat getChatMgr() {
		return chat;
	}
	
	private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }
	
	public Permission getPermMgr() {
		return permission;
	}
	
}
