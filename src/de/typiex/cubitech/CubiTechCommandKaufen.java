package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.typiex.cubitech.CubiTechUtil.Sell;

public class CubiTechCommandKaufen extends CubiTechCommand {

	public CubiTechCommandKaufen(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;

			if (args.length == 0) {
				p.sendMessage(ChatColor.RED + "Fehler. Du musst einen Spieler angeben.");
			} else {
				Player target = Bukkit.getPlayer(args[0]);
				if (target == null) {
					p.sendMessage(ChatColor.RED + "Fehler. Der Spieler " + ChatColor.GOLD + args[0] + ChatColor.RED + " ist nicht online.");
					return;
				}

				if (CubiTechUtil.playerSells.containsKey(target)) {
					Sell sell = CubiTechUtil.playerSells.get(target);
					ItemStack item = sell.item;
					int price = sell.price;
					if(!target.getInventory().contains(item)) {
						p.sendMessage(ChatColor.RED + "Fehler. Der Spieler " + ChatColor.GOLD + target.getName() + ChatColor.RED + " besitzt das Item nicht mehr.");
						return;
					}
					if (CubiTechUtil.getPlayerMoney(p) >= price) {
						CubiTechUtil.setPlayerMoney(p, CubiTechUtil.getPlayerMoney(p)-price);
						CubiTechUtil.setPlayerMoney(target, CubiTechUtil.getPlayerMoney(target)+price);
						p.getInventory().addItem(item);
						target.getInventory().removeItem(item);
						CubiTechUtil.playerSells.remove(target);
						Bukkit.broadcastMessage(ChatColor.AQUA + p.getName() + ChatColor.DARK_AQUA + " hat " + ChatColor.WHITE + "[" + item.getAmount() + "x" + (item.getItemMeta().getDisplayName() != null ? item.getItemMeta().getDisplayName() + ChatColor.WHITE + "(" + item.getType().name().toLowerCase() + ChatColor.WHITE + ")" : item.getType().name().toLowerCase()) + ChatColor.WHITE + "]" + ChatColor.DARK_AQUA + " von " + ChatColor.BLUE + target.getName() + ChatColor.DARK_AQUA + " fuer " + ChatColor.RED + price + " Cubi" + ChatColor.DARK_AQUA + " gekauft.");
					} else {
						p.sendMessage(ChatColor.RED + "Du hast nicht genug Geld");
					}
				} else {
					p.sendMessage(ChatColor.RED + "Der Spieler verkauft derzeit nichts.");
				}

			}

		}
	}
}
