package main.java.net.bigbadcraft.globalgroupmanager.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Methods {
	
	public static void a(String message) {
		Bukkit.broadcastMessage(message);
	}
	
	public static void makeMessage(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.DARK_RED + "[GGM] " + ChatColor.RESET + message);
	}
	
	public static void makeMessage(Player player, String message) {
		player.sendMessage(ChatColor.DARK_RED + "[GGM] " + ChatColor.RESET + message);
	}

	public static void log(Level level, String message) {
		Bukkit.getLogger().log(level, "[GGM] " + message);
	}
	
	public static boolean checkPermission(Player player, String permission) {
		if (player.isOp() || player.hasPermission("*") 
				|| player.hasPermission("globalgroupmanager.*") || player.hasPermission(permission)) {
			return true;
		}
		makeMessage(((CommandSender)player), ChatColor.RED + "You do not have permission: " + permission);
		return false;
	}
	
	public static boolean makeFile(File file) {
		if (!file.exists()) {
			try {
				file.createNewFile();
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log(Level.SEVERE, file.getName() + " could not be created.");
				e.printStackTrace();
				return false;
			}
		}
		return false; // File exists, no need to create
	}
	
}
