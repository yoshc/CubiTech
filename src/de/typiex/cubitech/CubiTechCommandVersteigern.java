package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandVersteigern extends CubiTechCommand {

	public CubiTechCommandVersteigern(CubiTech c, String cmd, boolean permissionNeeded) {
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
				if (CubiTechUtil.auction != null) {
					p.sendMessage("§cEs laeuft schon eine Auktion!");
				} else {
					if (CubiTechUtil.getPlayerMoney(p) < 20) {
						p.sendMessage("§cDu hast nicht genug Geld, um 20 Cubi Auktionsgebuehren zu bezahlen!");
					} else {
						try {
							int startPrice = Integer.valueOf(args[0]);
							CubiTechUtil.subtractPlayerMoney(p, 20);
							CubiTechUtil.auction = new CubiTechUtil.Auction(p, p.getItemInHand(), startPrice, 60);
							Bukkit.broadcastMessage("§3Auktion: §f["
									+ CubiTechUtil.auction.item.getAmount()
									+ "x"
									+ (CubiTechUtil.auction.item.getItemMeta().getDisplayName() != null ? CubiTechUtil.auction.item.getItemMeta().getDisplayName() + ChatColor.WHITE
											+ "(" + CubiTechUtil.auction.item.getType().name().toLowerCase() + ChatColor.WHITE + ")" : CubiTechUtil.auction.item.getType().name()
											.toLowerCase()) + "§f] von §e" + CubiTechUtil.auction.player.getName());
							Bukkit.broadcastMessage("§3Auktion wurde §bgestartet§3!");
							Bukkit.broadcastMessage("§3Startpreis: §b" + startPrice);
						} catch (Exception ex) {
							p.sendMessage("§cDu musst einen Startpreis angeben!");
							return;
						}
					}
				}
			}
		}

	}
}
