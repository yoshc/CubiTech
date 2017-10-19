package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CubiTechCommandSethead extends CubiTechCommand {

	public CubiTechCommandSethead(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {

		if (args.length == 0) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				ItemStack inHand = p.getItemInHand();
				try {
					p.getInventory().setHelmet(inHand);
				} catch (Exception ex) {
					sender.sendMessage(ChatColor.RED + "Fehler. Der Kopf konnte nicht gesetzt werden.");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Fehler. Du musst ein Spieler sein.");
			}
		} else if (args.length == 1) {
			Player p = Bukkit.getPlayer(args[0]);
			if (p != null) {
				ItemStack inHand = p.getItemInHand();
				try {
					p.getInventory().setHelmet(inHand);
				} catch (Exception ex) {
					sender.sendMessage(ChatColor.RED + "Fehler. Der Kopf konnte nicht gesetzt werden.");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Fehler. Der Spieler " + ChatColor.GOLD + args[0] + ChatColor.RED + " ist nicht online.");
			}
		}

	}
}
