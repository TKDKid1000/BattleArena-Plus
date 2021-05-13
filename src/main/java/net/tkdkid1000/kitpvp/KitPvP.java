package net.tkdkid1000.kitpvp;

import org.birdproductions.core.BirdCore;
import org.bukkit.plugin.java.JavaPlugin;

public class KitPvP extends JavaPlugin {

	private static KitPvP kitpvp;
	private BirdCore core;
	private CombatLog combatLog;
	
	public static KitPvP getInstance() {
		return kitpvp;
	}
	
	@Override
	public void onEnable() {
		kitpvp = this;
		setCore((BirdCore) getServer().getPluginManager().getPlugin("BirdCore"));
		getCore().getRegistry().register(this);
		saveDefaultConfig();
		setCombatLog(new CombatLog(this));
		new Sidebar().subscribe();
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
}
