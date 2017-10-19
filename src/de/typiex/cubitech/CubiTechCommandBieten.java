package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandBieten extends CubiTechCommand {

	public CubiTechCommandBieten(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {

		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (args.length == 0) {
				p.sendMessage("§a--- §2Auktion §a---");
				p.sendMessage("§6/versteigern <Startpreis>");
				p.sendMessage("§a=> Versteigere den Gegenstand in deiner Hand");
				p.sendMessage("§a=> §e20 Cubi §aAuktionsgebuehren");
				p.sendMessage("§6/bieten <Preis>");
				p.sendMessage("§a=> Biete fuer einen versteigerten Gegenstand");
				p.sendMessage("§a--- §2Auktion §a---");
			} else if (args.length == 1) {
				try {
					int price = Integer.valueOf(args[0]);
					
					if(CubiTechUtil.auction == null) {
						p.sendMessage("§cDerzeit laeuft keine Auktion.");
						return;
					} else {
						if(price > CubiTechUtil.auction.currentPrice) {
							if(price > CubiTechUtil.getPlayerMoney(p)) {
								p.sendMessage("§cDu hast nicht genug Geld!");
								return;
							} else {
								CubiTechUtil.auction.currentPrice = price;
								CubiTechUtil.auction.highestBidder = p;
								Bukkit.broadcastMessage("§3Auktion: §f["
										+ CubiTechUtil.auction.item.getAmount()
										+ "x"
										+ (CubiTechUtil.auction.item.getItemMeta().getDisplayName() != null ? CubiTechUtil.auction.item.getItemMeta().getDisplayName() + ChatColor.WHITE
												+ "(" + CubiTechUtil.auction.item.getType().name().toLowerCase() + ChatColor.WHITE + ")" : CubiTechUtil.auction.item.getType().name()
												.toLowerCase()) + "§f] von §e" + CubiTechUtil.auction.player.getName());
								Bukkit.broadcastMessage("§6" + p.getName() + " §bbietet §e" + price + " Cubi§b.");
							}
						} else {
							p.sendMessage("§cDu musst mindestens " + (CubiTechUtil.auction.currentPrice+1) + " Cubi bieten!");
							return;
						}
					}
					
				} catch (Exception ex) {
					p.sendMessage("§cDu musst einen Preis angeben.");
					return;
				}
			}

		}

	}
}
