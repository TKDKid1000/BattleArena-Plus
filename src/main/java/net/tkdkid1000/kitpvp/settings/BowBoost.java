package net.tkdkid1000.kitpvp.settings;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import net.tkdkid1000.kitpvp.KitPvP;

public class BowBoost implements Listener {

	public BowBoost(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Arrow && event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			Arrow arrow = (Arrow) event.getDamager();
			if (arrow.getShooter() != null) {
				if (arrow.getShooter().equals(player)) {
					double bowBoost = KitPvP.getInstance().getConfig().getDouble("bowboost");
					if (bowBoost == 0) {
						return;
					} else if (bowBoost == -1) {
						player.setVelocity(new Vector(0, 0, 0));
					} else {
						player.setVelocity(player.getVelocity().multiply(bowBoost));						
					}
				}
			}
		}
	}
}
