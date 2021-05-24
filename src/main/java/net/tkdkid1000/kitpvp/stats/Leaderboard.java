package net.tkdkid1000.kitpvp.stats;

import java.util.HashMap;
import java.util.Map;

import org.birdproductions.core.command.Command;
import org.birdproductions.core.command.CommandError;
import org.bukkit.command.CommandSender;

import net.tkdkid1000.kitpvp.KitPvP;

public class Leaderboard {

	@Command(description = "Shows leaderboard", label = "leaderboard")
	public void onLeaderboardCommand(CommandSender cs, String[] args, CommandError error, Command cmd) {
		Map<String, Integer> data = new HashMap<String, Integer>();
		KitPvP.getInstance().getStats().getStorage().getConfig().getKeys(false).forEach(key -> {
			data.put(key, null);
		});
	}
}
