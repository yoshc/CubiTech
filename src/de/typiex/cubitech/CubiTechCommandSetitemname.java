package de.typiex.cubitech;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CubiTechCommandSetitemname extends CubiTechCommand {

	public CubiTechCommandSetitemname(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {

		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Fehler. Du musst einen Text angeben.");
		} else {
			if (sender instanceof Player) {
				try {
					Player p = (Player) sender;
					String name = "";
					for (int i = 0; i < args.length; i++) {
						name += args[i];
						name += " ";
					}
					name = ChatColor.translateAlternateColorCodes('&', name);

					ItemStack is = p.getItemInHand();
					ItemMeta am = (ItemMeta) is.getItemMeta();
					am.setDisplayName(name);
					is.setItemMeta(am);
					p.getInventory().setItemInHand(is);
				} catch (Exception ex) {
					sender.sendMessage(ChatColor.RED + "Fehler. Der Name konnte nicht gesetzt werden.");
				}
			}
		}

	}
}
