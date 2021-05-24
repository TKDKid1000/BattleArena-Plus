package net.tkdkid1000.kitpvp.stats;

import org.birdproductions.core.util.YamlConfig;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

public class Stats {

	private YamlConfig storage;
	
	public Stats(Plugin plugin) {
		setStorage(new YamlConfig(plugin.getDataFolder(), "playerData"));
		getStorage().createConfig();
	}
	
	public ConfigurationSection getUser(String uuid) {
		return getStorage().getConfig().getConfigurationSection(uuid);
	}
	
	public void save() {
		getStorage().save();
		getStorage().reload();
	}

	/**
	 * @return the storage
	 */
	public YamlConfig getStorage() {
		return storage;
	}

	/**
	 * @param storage the storage to set
	 */
	public void setStorage(YamlConfig storage) {
		this.storage = storage;
	}

}
