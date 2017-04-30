package de.tf.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permission;

import de.tf.main.Main;
import de.tf.manager.TeamManager;

public class JoinListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
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
