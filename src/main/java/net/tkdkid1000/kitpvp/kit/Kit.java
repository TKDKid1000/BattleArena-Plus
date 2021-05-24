package net.tkdkid1000.kitpvp.kit;

import java.util.List;

import org.birdproductions.core.command.Command;
import org.birdproductions.core.command.CommandError;
import org.birdproductions.core.gui.Gui;
import org.birdproductions.core.gui.GuiItem;
import org.birdproductions.core.gui.GuiSize;
import org.birdproductions.core.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import net.tkdkid1000.kitpvp.KitPvP;

public class Kit {
	
	@Command(description = "Kit general commands", label = "kit")
	public void onKitCommand(CommandSender cs, String[] args, CommandError error, Command cmd) {
		ConfigurationSection kits = KitPvP.getInstance().getConfig().getConfigurationSection("kits");
		if (cs instanceof Player) {
			Player player = (Player) cs;
			if (args.length == 0) {
				Gui kitGui = new Gui("&6&lKits", GuiSize.ROW6, KitPvP.getInstance()) {

					@Override
					public void onOpen(Player player) {
						
					}

					@Override
					public void onClose(Player player) {
						
					}
					
				};
				List<String> lore = kits.getStringList("lore");
				for (int x=0; x<lore.size(); x++) {
					lore.set(x, ChatColor.translateAlternateColorCodes('&', lore.get(x)));
				}
				kitGui.addItem(new GuiItem(new ItemBuilder(Material.matchMaterial(kits.getString("item")), 1)
						.setName(ChatColor.translateAlternateColorCodes('&', kits.getString("name")))
						.setLore(lore)
						.build(), 0) {

					@Override
					public void onClick(Player player, ItemStack item) {
						
					}

					@Override
					public ItemStack onReload(Player player, ItemStack item) {
						return item;
					}
					
				});
				kitGui.open(player);
			}
		}
	}	
}
