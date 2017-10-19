package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CubiTechCommandPaket extends CubiTechCommand {

	public CubiTechCommandPaket(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (args.length > 0) {
				Player target = Bukkit.getPlayer(args[0]);
				if (target == null) {
					p.sendMessage(ChatColor.RED + "Fehler. Der Spieler " + ChatColor.GOLD + args[0] + ChatColor.RED + " ist nicht online.");
				} else {
					Inventory i = plugin.getServer().createInventory(null, 18, "Paket an " + target.getName());
					p.openInventory(i);
				}
			} else {
				p.sendMessage(ChatColor.RED + "Fehler. Du musst einen Spieler angeben.");
			}
		}
	}
}
