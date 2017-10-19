package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.typiex.cubitech.CubiTechUtil.Sell;

public class CubiTechCommandVerkaufen extends CubiTechCommand {

	public CubiTechCommandVerkaufen(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;

			if (args.length == 0) {
				p.sendMessage(ChatColor.RED + "Fehler. Du musst einen Preis angeben.");
			} else {
				int price = 0;
				try {
					price = Integer.valueOf(args[0]);
				} catch (Exception ex) {
					p.sendMessage(ChatColor.RED + "Fehler. Du musst einen Preis angeben.");
					return;
				}
				
				if(p.getItemInHand().getType() == Material.AIR || p.getItemInHand() == null) {
					p.sendMessage(ChatColor.RED + "Fehler. Du musst ein Item in der Hand halten.");
					return;
				}
				
				ItemStack item = p.getItemInHand();
				CubiTechUtil.playerSells.put(p, new Sell(item, price));
				Bukkit.broadcastMessage(ChatColor.AQUA + p.getName() + ChatColor.DARK_AQUA + " verkauft " + ChatColor.WHITE + "[" + item.getAmount() + "x" + (item.getItemMeta().getDisplayName() != null ? item.getItemMeta().getDisplayName() + ChatColor.WHITE + "(" + item.getType().name().toLowerCase() + ChatColor.WHITE + ")" : item.getType().name().toLowerCase()) + ChatColor.WHITE + "]" + ChatColor.DARK_AQUA + " fuer " + ChatColor.RED + price + " Cubi" + ChatColor.DARK_AQUA + ".");
				Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "Schreibe " + ChatColor.AQUA + "/kaufen " + p.getName() + ChatColor.DARK_AQUA + " um es zu kaufen.");
				
				
			}

		}
	}
}
