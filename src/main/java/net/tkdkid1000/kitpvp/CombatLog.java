package net.tkdkid1000.kitpvp;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

public class CombatLog implements Listener {

	private Map<UUID, Integer> log;
	
	public CombatLog(Plugin plugin) {
		setLog(new HashMap<UUID, Integer>());
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		new BukkitRunnable() {

			@Override
			public void run() {
				Map<UUID, Integer> iterator = new HashMap<UUID, Integer>();
				iterator.putAll(getLog());
				iterator.forEach((uuid, time) -> {
					if (time > 1) {
						int t = time;
						t--;
						getLog().replace(uuid, t);
					} else {
						getLog().remove(uuid);
						Bukkit.getPlayer(uuid).sendMessage(ChatColor.translateAlternateColorCodes('&', KitPvP.getInstance().getConfig().getString("combatlog.nocombatmessage")));
					}
				});
			}
			
		}.runTaskTimer(plugin, 1, 1);
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
			Player damager = (Player) event.getDamager();
			Player target = (Player) event.getEntity();
			if (!getLog().containsKey(damager.getUniqueId())) {
				damager.sendMessage(ChatColor.translateAlternateColorCodes('&', KitPvP.getInstance().getConfig().getString("combatlog.message").replace("{player}", target.getDisplayName())));				
			}
			log.put(damager.getUniqueId(), KitPvP.getInstance().getConfig().getInt("combatlog.time")*20);
			if (!getLog().containsKey(target.getUniqueId())) {
				target.sendMessage(ChatColor.translateAlternateColorCodes('&', KitPvP.getInstance().getConfig().getString("combatlog.message").replace("{player}", damager.getDisplayName())));				
			}
			log.put(target.getUniqueId(), KitPvP.getInstance().getConfig().getInt("combatlog.time")*20);
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		if (log.containsKey(event.getPlayer().getUniqueId())) {
			log.remove(event.getPlayer().getUniqueId());
			event.getPlayer().setHealth(0);
			event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', KitPvP.getInstance().getConfig().getString("combatlog.logoutmessage")));
		}
	}

	/**
	 * @return the log
	 */
	public Map<UUID, Integer> getLog() {
		return log;
	}

	/**
	 * @param log the log to set
	 */
	public void setLog(Map<UUID, Integer> log) {
		this.log = log;
	}

}
