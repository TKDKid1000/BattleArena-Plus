package net.tkdkid1000.kitpvp;

import org.birdproductions.core.BirdCore;
import org.birdproductions.core.command.Command;
import org.birdproductions.core.command.CommandError;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.tkdkid1000.kitpvp.settings.BowBoost;
import net.tkdkid1000.kitpvp.stats.KillStat;
import net.tkdkid1000.kitpvp.stats.Stats;

public class KitPvP extends JavaPlugin {

	private static KitPvP kitpvp;
	private BirdCore core;
	private CombatLog combatLog;
	private Stats stats;
	
	public static KitPvP getInstance() {
		return kitpvp;
	}
	
	@Override
	public void onEnable() {
		kitpvp = this;
		setCore((BirdCore) getServer().getPluginManager().getPlugin("BirdCore"));
		getCore().getRegistry().register(this);
		saveDefaultConfig();
		saveConfig();
		setCombatLog(new CombatLog(this));
		new Sidebar().subscribe();
		new BowBoost(this);
		new HealthTags(this);
		setStats(new Stats(this));
		new KillStat(this);
		getServer().getPluginManager().registerEvents(new Listener() {
			@EventHandler
			public void onJoin(PlayerJoinEvent event) {
				String uuid = event.getPlayer().getUniqueId().toString();
				if (!getStats().getStorage().getConfig().isSet(uuid)) {
					getStats().getStorage().getConfig().set(uuid+".kills", 0);
					getStats().getStorage().getConfig().set(uuid+".killstreak", 0);
					getStats().getStorage().getConfig().set(uuid+".killstreakmax", 0);
					getStats().getStorage().getConfig().set(uuid+".deaths", 0);
				}
				getStats().save();
//				event.getPlayer().setMaximumNoDamageTicks(getConfig().getInt("settings.nodamageticks"));
			}
		}, core);
	}
	
	@Command(description = "relaod config", label = "kitpvpconfig")
	public void reloadKitpvpConfig(CommandSender cs, String[] args, CommandError error, Command cmd) {
		reloadConfig();
		cs.sendMessage("Reloading config...");
	}
	
	@Override
	public void onDisable() {
		
	}

	/**
	 * @return the core
	 */
	public BirdCore getCore() {
		return core;
	}

	/**
	 * @param core the core to set
	 */
	public void setCore(BirdCore core) {
		this.core = core;
	}

	/**
	 * @return the combatLog
	 */
	public CombatLog getCombatLog() {
		return combatLog;
	}

	/**
	 * @param combatLog the combatLog to set
	 */
	public void setCombatLog(CombatLog combatLog) {
		this.combatLog = combatLog;
	}

	/**
	 * @return the stats
	 */
	public Stats getStats() {
		return stats;
	}

	/**
	 * @param stats the stats to set
	 */
	public void setStats(Stats stats) {
		this.stats = stats;
	}
}
