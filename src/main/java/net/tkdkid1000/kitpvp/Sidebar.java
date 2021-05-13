package net.tkdkid1000.kitpvp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;

import com.earth2me.essentials.Essentials;

import me.lucko.helper.Schedulers;
import me.lucko.helper.Services;
import me.lucko.helper.metadata.Metadata;
import me.lucko.helper.metadata.MetadataKey;
import me.lucko.helper.metadata.MetadataMap;
import me.lucko.helper.scoreboard.Scoreboard;
import me.lucko.helper.scoreboard.ScoreboardObjective;
import me.lucko.helper.scoreboard.ScoreboardProvider;
import net.md_5.bungee.api.ChatColor;

public class Sidebar {

	private Essentials ess = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
	private FileConfiguration config = KitPvP.getInstance().getConfig();
	
	public void subscribe() {
		MetadataKey<ScoreboardObjective> SCOREBOARD_KEY = MetadataKey.create("economy", ScoreboardObjective.class);
		BiConsumer<Player, ScoreboardObjective> updater = (p, obj) -> {
			obj.setDisplayName(config.getString("sidebar.title"));
			List<String> lines = new ArrayList<String>();
			if (config.getBoolean("sidebar.date")) {
				lines.add("&7" + new java.sql.Date(System.currentTimeMillis()).toString().replace("-", "/"));
				lines.add("");
			}
			if (KitPvP.getInstance().getCombatLog().getLog().containsKey(p.getUniqueId())) {
				lines.add(config.getString("sidebar.combatlog").replace("{time}", ""+KitPvP.getInstance().getCombatLog().getLog().get(p.getUniqueId())/20));
				lines.add("");
			}
			lines.add(config.getString("sidebar.money").replace("{balance}", ""+ess.getUser(p).getMoney().doubleValue()));
			lines.add("");
			lines.add(config.getString("sidebar.website"));
			obj.applyLines(lines);
		};

		Scoreboard sb = Services.load(ScoreboardProvider.class).getScoreboard();

		me.lucko.helper.Events.subscribe(PlayerJoinEvent.class)
				.handler(e -> {
					ScoreboardObjective obj = sb.createPlayerObjective(e.getPlayer(), "null", DisplaySlot.SIDEBAR);
					Metadata.provideForPlayer(e.getPlayer()).put(SCOREBOARD_KEY, obj);
					updater.accept(e.getPlayer(), obj);
				});

		Schedulers.async().runRepeating(() -> {
			for (Player player : Bukkit.getOnlinePlayers()) {
				MetadataMap metadata = Metadata.provideForPlayer(player);
				ScoreboardObjective obj = metadata.getOrNull(SCOREBOARD_KEY);
				if (obj != null) {
					updater.accept(player, obj);
					updater.accept(player, obj);
				}
			}
		}, 3L, 3L);
	}

}
