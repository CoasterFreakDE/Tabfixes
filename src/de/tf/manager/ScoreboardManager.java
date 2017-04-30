package de.tf.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.scoreboard.Scoreboard;

import de.tf.main.Main;

public class ScoreboardManager {

	public Scoreboard sc;
	
	public ScoreboardManager() {
		sc = Bukkit.getScoreboardManager().getNewScoreboard();
		
		for(String team : TeamManager.getTeams()) {
			sc.registerNewTeam(team);
		}
		
		Bukkit.getScheduler().runTaskLaterAsynchronously(Main.INSTANCE, new Runnable() {
			
			@Override
			public void run() {
				TeamManager.setFixes();	
			}
		}, 20);
		
	}

	@SuppressWarnings("deprecation")
	public void setTeam(Player p, String team) {
		sc.getTeam(team).addPlayer(p);
		p.setPlayerListName(sc.getTeam(team).getPrefix() + p.getDisplayName() + sc.getTeam(team).getSuffix());
		
		for(Player all: Bukkit.getOnlinePlayers()) {
		   all.setScoreboard(sc);
	    }
	}

	
	public void setAllTeams() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(TeamManager.usePerms()) {
				boolean isSet = false;
				for(String group : TeamManager.getTeams()) {
					if(p.hasPermission(new Permission("Tabfixes." + group))) {
						Main.INSTANCE.sbm.setTeam(p, group);
						isSet = true;
						break;
					}
				}
				
				if(!isSet) {
					Main.INSTANCE.sbm.setTeam(p, TeamManager.getDefaultGroup());
				}
			}
		}
	}
}
