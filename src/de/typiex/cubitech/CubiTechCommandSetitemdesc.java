package de.typiex.cubitech;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CubiTechCommandSetitemdesc extends CubiTechCommand {

	public CubiTechCommandSetitemdesc(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {

		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Fehler. Du musst einen Text angeben.");
		} else {
			if (sender instanceof Player) {
				try {
					Player p = (Player) sender;
					ArrayList<String> name = new ArrayList<String>();
					String current = "";
					for (int i = 0; i < args.length; i++) {
						if (args[i].equalsIgnoreCase("newline")) {
							name.add(current);
							current = "";
						} else {
							current += args[i];
							current += " ";
							current = ChatColor.translateAlternateColorCodes('&', current);
						}
					}
					name.add(current);

					for (String s : name) {
						s = ChatColor.translateAlternateColorCodes('&', s);
					}

					ItemStack is = p.getItemInHand();
					ItemMeta am = (ItemMeta) is.getItemMeta();
					am.setLore(name);
					is.setItemMeta(am);
					p.getInventory().setItemInHand(is);
				} catch (Exception ex) {
					sender.sendMessage(ChatColor.RED + "Fehler. Der Name konnte nicht gesetzt werden.");
				}
			}
		}

	}
}
