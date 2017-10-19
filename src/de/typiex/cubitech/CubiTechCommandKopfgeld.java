package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.typiex.cubitech.CubiTechUtil.HeadBounty;

public class CubiTechCommandKopfgeld extends CubiTechCommand {

	public CubiTechCommandKopfgeld(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (CubiTechUtil.getPlayerLevel(p) < 25) {
				p.sendMessage(ChatColor.RED + "Dafuer musst Du mind. Level 25 sein.");
				return;
			}
			if (args.length < 2) {
				if (CubiTechUtil.headBounties.size() == 0) {
					p.sendMessage(ChatColor.RED + "Derzeit ist auf keine Spieler Kopfgeld ausgesetzt.");
				} else {
					p.sendMessage(ChatColor.RED + "Es ist derzeit auf folgende Spieler Kopfgeld ausgesetzt: ");
					for (HeadBounty hb : CubiTechUtil.headBounties) {
						p.sendMessage(ChatColor.RED + "- " + ChatColor.DARK_RED + hb.target.getName() + ChatColor.GOLD + " : " + ChatColor.DARK_GREEN
								+ hb.price + " Cubi" + ChatColor.RED + " von " + hb.player.getName());
					}
				}
			} else {
				Player target = Bukkit.getPlayer(args[0]);
				if (target == null) {
					p.sendMessage(ChatColor.RED + "Fehler. Der Spieler " + ChatColor.GOLD + args[0] + ChatColor.RED + " ist nicht online.");
				} else {
					String sPrice = args[1];
					try {
						int price = Integer.valueOf(sPrice);
						if (CubiTechUtil.getPlayerMoney(p) >= price) {
							CubiTechUtil.headBounties.add(new HeadBounty(p, target, price));
							Bukkit.broadcastMessage("§a" + p.getName() + " §ehat §6" + price + " Cubi §eKopfgeld auf §c" + target.getName() + "§e ausgesetzt.");
						} else {
							p.sendMessage(ChatColor.RED + "Dafuer hast Du nicht genug Geld!");
						}

					} catch (Exception ex) {
						p.sendMessage(ChatColor.RED + "Fehler. Du musst einen Spieler und einen Preis angeben.");
					}

				}
			}
		}
	}
}
