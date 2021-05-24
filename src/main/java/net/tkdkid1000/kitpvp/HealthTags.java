package net.tkdkid1000.kitpvp;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import net.md_5.bungee.api.ChatColor;

public class HealthTags {

	public HealthTags(Plugin plugin) {
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		
		Objective obj = board.registerNewObjective("healthtags", "health");
		obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
		obj.setDisplayName(ChatColor.RED + "\u2665");
		new BukkitRunnable() {

			@Override
			public void run() {
				Bukkit.getOnlinePlayers().forEach(player -> {
					player.setScoreboard(board);
					player.setHealth(player.getHealth());
				});
			}
			
		}.runTaskTimer(plugin, 20, 20);
	}
}
