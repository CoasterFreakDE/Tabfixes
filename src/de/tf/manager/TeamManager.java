package de.tf.manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import de.tf.main.Main;
import de.tf.utils.FileConfig;

public class TeamManager {

	
	public static List<String> getTeams() {
		FileConfig groups = new FileConfig("Tabfixes", "groups.yml");
		
		if(groups.getKeys(false).isEmpty()) {
			/*
			 * Create Standard groups
			 */
			
			groups.set("defaultgroup", "0003Player");
			groups.set("#UseFull", "Character Limit for each is 16!");
			
			groups.set("0000Owner.prefix", "&4&l&oOWNER &7");
			groups.set("0000Owner.suffix", "&7");
			
			groups.set("0001Admin.prefix", "&c&l&oADMIN &7");
			groups.set("0001Admin.suffix", "&7");
			
			groups.set("0002Moderator.prefix", "&9&l&oMOD &7");
			groups.set("0002Moderator.suffix", "&7");
			
			groups.set("0003Player.prefix", "&9&l&oPLAYER &7");
			groups.set("0003Player.suffix", "&7");
			
			groups.SaveConfig();
		}
		
		List<String> groupList = new ArrayList<String>();
		
		
		for(String group : groups.getKeys(false)) {
			if(!group.equalsIgnoreCase("defaultgroup") && !group.equalsIgnoreCase("#UseFull")) {
				groupList.add(group);
			}
		}
		
		return groupList;
	}
	
	public static boolean usePerms() {
		FileConfig groups = new FileConfig("Tabfixes", "config.yml");
		
		if(groups.contains("usePermissions")) {
			return groups.getBoolean("usePermissions");
		}
		else {
			groups.set("usePermissions", true);
			groups.SaveConfig();
		}
		
		return true;
	}
	
	public static String getDefaultGroup() {
		FileConfig groups = new FileConfig("Tabfixes", "groups.yml");
		
		return groups.getString("defaultgroup");
	}
	
	public static void setDefaultGroup(String group) {
		FileConfig groups = new FileConfig("Tabfixes", "groups.yml");
		
		groups.set("defaultgroup", group);
		groups.SaveConfig();
	}
	
	public static void setFixes() {
		FileConfig groups = new FileConfig("Tabfixes", "groups.yml");
		
		for(String group : groups.getKeys(false)) {
			if(!group.equalsIgnoreCase("defaultgroup") && !group.equalsIgnoreCase("#UseFull")) {
				String prefix = groups.getString(group + ".prefix");
				String suffix = groups.getString(group + ".suffix");
				
//				System.out.println(group + " > " + prefix + " " + suffix);
				
				Main.INSTANCE.sbm.sc.getTeam(group)
										.setPrefix(convertStrings(prefix));
				Main.INSTANCE.sbm.sc.getTeam(group)
										.setSuffix(convertStrings(suffix));
			}
		}
	}
	
	public static void setFix(String group, String fix, boolean pre) {
		FileConfig groups = new FileConfig("Tabfixes", "groups.yml");
		
		if(pre) {
			groups.set(group + ".prefix", fix);
		}
		else {
			groups.set(group + ".suffix", fix);
		}
		
		groups.SaveConfig();
	}
	
	public static void deleteGroup(String group) {
		FileConfig groups = new FileConfig("Tabfixes", "groups.yml");
		groups.getConfigurationSection("").set(group, null);
		groups.SaveConfig();
		
		System.out.println(group + " deleted!");
	}
	
	public static String convertStrings(String s) {
		
		s = s.replace("ae", "ä");
		s = s.replace("oe", "ö");
		s = s.replace("ue", "ü");
		s = s.replace("AE", "Ä");
		s = s.replace("OE", "Ö");
		s = s.replace("UE", "Ü");
		
		s = s.replace("a*e", "ae");
		s = s.replace("o*e", "oe");
		s = s.replace("u*e", "ue");
		s = s.replace("A*E", "AE");
		s = s.replace("O*E", "OE");
		s = s.replace("U*E", "UE");
		
		s = s.replace("{online}", "" + Bukkit.getOnlinePlayers().size());

		
		s = ChatColor.translateAlternateColorCodes('&', s);
		
		return s;
	}
	
	public static String getGroup(String group) {
		for(String s : getTeams()) {
			if(s.contains(group))
				return s;
		}
		return null;
	}
}
