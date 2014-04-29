package main.java.net.bigbadcraft.globalgroupmanager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class PlayerData {
	
	private Player player;
	
	public PlayerData(String player) {
		this.player = Bukkit.getPlayer(player);
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setGroup(String group) {
		for (World worlds : Bukkit.getWorlds()) {
			String world_names = worlds.getName();
			if (!GlobalGroupManager.getInstance().getBlackList().contains(world_names)) {
				String[] old_groups = GlobalGroupManager.getInstance().getVaultPerm().getPlayerGroups(world_names, player.getName());
				for (String s : old_groups) {
					GlobalGroupManager.getInstance().getVaultPerm().playerRemoveGroup(world_names, player.getName(), s);
				}
				GlobalGroupManager.getInstance().getVaultPerm().playerAddGroup(world_names, player.getName(), group);
			}
		}
	}
	
	public String getCurrentGroup() {
		for (World worlds : Bukkit.getWorlds()) {
			String world_names = worlds.getName();
			if (!GlobalGroupManager.getInstance().getBlackList().contains(world_names)) {
				return GlobalGroupManager.getInstance().getVaultPerm().getPrimaryGroup(world_names, player.getName());
			}
		}
		return ChatColor.RED + "no group found";
	}
	
	public void removeGroup(String group) {
		for (World worlds : Bukkit.getWorlds()) {
			String world_names = worlds.getName();
			if (!GlobalGroupManager.getInstance().getBlackList().contains(world_names)) {
				GlobalGroupManager.getInstance().getVaultPerm().playerRemoveGroup(world_names, player.getName(), group);
			}
		}
	}
	
	public void addPermission(String permission) {
		for (World worlds : Bukkit.getWorlds()) {
			String world_names = worlds.getName();
			if (!GlobalGroupManager.getInstance().getBlackList().contains(world_names)) {
				GlobalGroupManager.getInstance().getVaultPerm().playerAdd(world_names, player.getName(), permission);
			}
		}
	}
	
	public void removePermission(String permission) {
		for (World worlds : Bukkit.getWorlds()) {
			String world_names = worlds.getName();
			if (!GlobalGroupManager.getInstance().getBlackList().contains(world_names)) {
				GlobalGroupManager.getInstance().getVaultPerm().playerRemove(world_names, player.getName(), permission);
			}
		}
	}
	
	public boolean hasPermission(String permission) {
		for (World worlds : Bukkit.getWorlds()) {
			String world_names = worlds.getName();
			if (!GlobalGroupManager.getInstance().getBlackList().contains(world_names)) {
				boolean has_perm = GlobalGroupManager.getInstance().getVaultPerm().playerHas(world_names, player.getName(), permission);
				return has_perm;
			}
		}
		return false;
	}
	
	public boolean groupHasPermission(String permission) {
		for (World worlds : Bukkit.getWorlds()) {
			String world_names = worlds.getName();
			if (!GlobalGroupManager.getInstance().getBlackList().contains(world_names)) {
				if (playerHasGroup()) {
					String group = GlobalGroupManager.getInstance().getVaultChat().getPrimaryGroup(player);
					if (GlobalGroupManager.getInstance().getVaultPerm().groupHas(world_names, group, permission)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean playerHasGroup() {
		for (String s : GlobalGroupManager.getInstance().getVaultChat().getGroups()) {
			if (GlobalGroupManager.getInstance().getVaultChat().getPrimaryGroup(player).equalsIgnoreCase(s)) {
				return true;
			}
		}
		return false;
	}
	
}
