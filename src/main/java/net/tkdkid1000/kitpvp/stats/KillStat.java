package net.tkdkid1000.kitpvp.stats;

import java.math.BigDecimal;

import org.birdproductions.core.util.NumberUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

import com.earth2me.essentials.Essentials;

import net.ess3.api.MaxMoneyException;
import net.md_5.bungee.api.ChatColor;
import net.tkdkid1000.kitpvp.KitPvP;

public class KillStat implements Listener {

	private Essentials ess = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
	
	public KillStat(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onKill(PlayerDeathEvent event) {
		Stats stats = KitPvP.getInstance().getStats();
		Player target = event.getEntity();
		if (target.getKiller() != null) {
			if (target.getKiller() instanceof Player) {
				Player killer = target.getKiller();
				ConfigurationSection killerSection = stats.getUser(killer.getUniqueId().toString());
				killerSection.set("kills", killerSection.getInt("kills")+1);
				killerSection.set("killstreak", killerSection.getInt("killstreak")+1);
				if (killerSection.getInt("killstreak") > killerSection.getInt("killstreakmax")) {
					killerSection.set("killstreakmax", killerSection.getInt("killstreak"));
				}
				ConfigurationSection targetSection = stats.getUser(target.getUniqueId().toString());
				targetSection.set("killstreak", 0);
				targetSection.set("deaths", targetSection.getInt("deaths")+1);
				stats.save();
				FileConfiguration config = KitPvP.getInstance().getConfig();
				double money = NumberUtil.getRandomNumberRange(config.getInt("kills.min"), config.getInt("kills.max"));
				killer.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("kills.message")).replace("{money}", ""+money).replace("{player}", target.getDisplayName()));
				try {
					ess.getUser(killer).setMoney(BigDecimal.valueOf(ess.getUser(killer).getMoney().doubleValue()+money));
				} catch (MaxMoneyException e) {
					e.printStackTrace();
				}
			}
		}
		if (KitPvP.getInstance().getConfig().getBoolean("settings.instantrespawn") && target.isDead()) {
//			event.getEntity().spigot().respawn();
		}
	}

}
