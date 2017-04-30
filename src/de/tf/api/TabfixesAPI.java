package de.tf.api;

import org.bukkit.scoreboard.Scoreboard;

import de.tf.main.Main;
import de.tf.manager.ScoreboardManager;

public class TabfixesAPI {

	/**
	 * @return The used Scoreboard
	 */
	public static Scoreboard getScoreboard() {
		return Main.INSTANCE.sbm.sc;
	}

	/**
	 * @return The hole Manager Class (Add Players)
	 */
	public static ScoreboardManager getManager() {
		return Main.INSTANCE.sbm;
	}
}
