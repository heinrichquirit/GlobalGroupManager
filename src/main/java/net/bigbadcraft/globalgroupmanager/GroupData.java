package main.java.net.bigbadcraft.globalgroupmanager;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class GroupData {
	
	private String group;
	
	public GroupData(String group) {
		setGroup(group);
	}
	
	public void setGroup(String group) {
		this.group = group;
	}
	
	public String getGroup() {
		return group;
	}
	
	public void addPermission(String permission) {
		for (World worlds : Bukkit.getWorlds()) {
			String world_names = worlds.getName();
			if (!GlobalGroupManager.getInstance().getBlackList().contains(world_names)) {
				GlobalGroupManager.getInstance().getVaultPerm().groupAdd(world_names, group, permission);
			}
		}
	}
	
	public void removePermission(String permission) {
		for (World worlds : Bukkit.getWorlds()) {
			String world_names = worlds.getName();
			if (!GlobalGroupManager.getInstance().getBlackList().contains(world_names)) {
				GlobalGroupManager.getInstance().getVaultPerm().groupRemove(world_names, group, permission);
			}
		}
	}
	
	public boolean hasPermission(String permission) {
		for (World worlds : Bukkit.getWorlds()) {
			String world_names = worlds.getName();
			if (!GlobalGroupManager.getInstance().getBlackList().contains(world_names)) {
				boolean has_perm = GlobalGroupManager.getInstance().getVaultPerm().groupHas(world_names, group, permission);
				return has_perm;
			}
		}
		return false;
	}
	
	public boolean groupExists() {
		for (String group_name : GlobalGroupManager.getInstance().getVaultPerm().getGroups()) {
			if (group_name.equalsIgnoreCase(group)) {
				return true;
			}
		}
		return false;
	}
}
