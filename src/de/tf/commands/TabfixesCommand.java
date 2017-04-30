package de.tf.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import de.tf.main.Main;
import de.tf.manager.TeamManager;

public class TabfixesCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String string, String[] args) {
		
		if(s instanceof Player) {
			Player p = (Player)s;
			
			if(!p.isOp() && !p.hasPermission(new Permission("Tabfixes.reload"))) {
				p.sendMessage(Main.PREFIX + "§c§oYou don't have enought permissions. §eTabfixes.reload");
				return true;
			}
		}
		
		if(args.length == 1) {
			String sub = args[0];
			
			if(sub.equalsIgnoreCase("list")) {
				s.sendMessage(Main.PREFIX + "§7§l§oGROUPS");
				for(String team : TeamManager.getTeams()) {
					if(team.equalsIgnoreCase(TeamManager.getDefaultGroup())) {
						s.sendMessage(" §9§o" + team + " §7Prefix: " +Main.INSTANCE.sbm.sc.getTeam(team).getPrefix() + " §7Suffix: " + TeamManager.convertStrings(Main.INSTANCE.sbm.sc.getTeam(team).getSuffix()));
					}
					else {
						s.sendMessage(" §9" + team + " §7Prefix: " + Main.INSTANCE.sbm.sc.getTeam(team).getPrefix() + " §7Suffix: " + TeamManager.convertStrings(Main.INSTANCE.sbm.sc.getTeam(team).getSuffix()));
					}
				}
			}
			else if(sub.equalsIgnoreCase("reload")){
				Main.INSTANCE.reload();
				s.sendMessage(Main.PREFIX + "§7§oEverything reloaded.");
			}
			else {
				sendHelp(s);
			}
		}
		else if(args.length == 2) {
			String sub = args[0];
			String group = args[1];
			
			if(sub.equalsIgnoreCase("setDefaultGroup")) {
				if(TeamManager.getGroup(group) != null) {
					String groupS = TeamManager.getGroup(group);
					TeamManager.setDefaultGroup(groupS);
					s.sendMessage(Main.PREFIX + "§a§oGroup §e" + groupS + " §a§ois now defaultGroup.");
					Main.INSTANCE.reload();
				}
				else {
					s.sendMessage(Main.PREFIX + "§c§oSorry, I don't know a group called §e" + group);
				}
			}
			else {
				sendHelp(s);
			}
		}
		else if(args.length == 3) {
			String sub = args[0];
			String group = args[1];
			String create = args[2];
			
			if(sub.equalsIgnoreCase("group")) {
				if(create.equalsIgnoreCase("create")) {
					TeamManager.setFix(group, "", true);
					TeamManager.setFix(group, "", false);
					s.sendMessage(Main.PREFIX + "§a§oGroup created.");
					s.sendMessage(Main.PREFIX + "§7§oStart adding Prefix and Suffix.");
					Main.INSTANCE.reload();
				}
				else if(create.equalsIgnoreCase("delete")) {
					if(TeamManager.getGroup(group) != null) {
						String groupS = TeamManager.getGroup(group);
						TeamManager.deleteGroup(groupS);
						s.sendMessage(Main.PREFIX + "§a§oGroup deleted.");
						Main.INSTANCE.reload();
					}
					else {
						s.sendMessage(Main.PREFIX + "§c§oSorry, I don't know a group called §e" + group);
					}
				}
				else {
					sendHelp(s);
				}
			}
			else {
				sendHelp(s);
			}
		}
		else if(args.length == 4) {
			String sub = args[0];
			
			if(sub.equalsIgnoreCase("group")) {
				String groupS = args[1];
				String pre = args[2];
				String prefix = args[3].replaceAll("_", " ");
				
				if(TeamManager.getGroup(groupS) != null) {
					String group = TeamManager.getGroup(groupS);
					if(pre.equalsIgnoreCase("setPrefix")) {
						Main.INSTANCE.sbm.sc.getTeam(group).setPrefix(TeamManager.convertStrings(prefix));
						TeamManager.setFix(group, prefix, (pre.equalsIgnoreCase("setPrefix")));
						s.sendMessage(Main.PREFIX + "§a§oPrefix set. " + TeamManager.convertStrings(prefix));
					}
					else if(pre.equalsIgnoreCase("setSuffix")) {
						Main.INSTANCE.sbm.sc.getTeam(group).setSuffix(TeamManager.convertStrings(prefix));
						TeamManager.setFix(group, prefix, (pre.equalsIgnoreCase("setPrefix")));
						s.sendMessage(Main.PREFIX + "§a§oSuffix set. " + TeamManager.convertStrings(prefix));
					}
					else {
						sendHelp(s);
					}
				}
				else {
					s.sendMessage(Main.PREFIX + "§c§oSorry, I don't know a group called §e" + groupS);
				}
			}
			else {
				sendHelp(s);
			}
		}
		else {
			sendHelp(s);
		}
		
		return true;
	}

	
	public void sendHelp(CommandSender p) {
		p.sendMessage(Main.PREFIX + "§7§oHelp for Tabfixes");
		p.sendMessage(Main.PREFIX + "§7§o/tf reload - Reload everything.");
		p.sendMessage(Main.PREFIX + "§7§o/tf list - List all Groups with pre/suffix");
		p.sendMessage(Main.PREFIX + "§7§o/tf group [group] setPrefix/setSuffix [pre/suffix]");
		p.sendMessage(Main.PREFIX + "§7§o/tf group [group] create");
		p.sendMessage(Main.PREFIX + "§7§o/tf group [group] delete");
		p.sendMessage(Main.PREFIX + "§7§o/tf setDefaultGroup [group]");
	}
}
