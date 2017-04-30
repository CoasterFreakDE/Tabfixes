package de.tf.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.tf.commands.TabfixesCommand;
import de.tf.listeners.JoinListener;
import de.tf.manager.ScoreboardManager;

public class Main extends JavaPlugin {

	public static Main INSTANCE;
	public static String PREFIX;
	
	public ScoreboardManager sbm;
	
	
	@Override
	public void onEnable() {
		INSTANCE = this;
		PREFIX = "§6§l┃ §eTabfixes §7§o";
		
		sbm = new ScoreboardManager();
		
		registerListener();
		sbm.setAllTeams();
		
		Bukkit.getPluginCommand("tabfixes").setExecutor(new TabfixesCommand());
		
		Bukkit.getConsoleSender().sendMessage(PREFIX + "§a§oPlugin enabled.");
	}
	
	
	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(PREFIX + "§c§oPlugin disabled.");
	}

	
	public void registerListener() {
		PluginManager pm = Bukkit.getPluginManager();
		
		pm.registerEvents(new JoinListener(), this);
	}


	public void reload() {
		sbm = new ScoreboardManager();
		sbm.setAllTeams();
	}
	
	
}
