package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommand1vs1 extends CubiTechCommand {

	public CubiTechCommand1vs1(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		CubiTechUtil.reload1vs1();
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				p.sendMessage(ChatColor.AQUA + "-- 1vs1 --");
				p.sendMessage(ChatColor.GOLD + " Siege: " + ChatColor.YELLOW + CubiTechUtil.player1vs1Wins.get(p));
				p.sendMessage(ChatColor.GOLD + " Verluste: " + ChatColor.YELLOW + CubiTechUtil.player1vs1Fails.get(p));
				if (CubiTechUtil.players1vs1Fighting[0] != null && CubiTechUtil.players1vs1Fighting[1] != null) {
					p.sendMessage(ChatColor.YELLOW + "Derzeitiger Kampf: " + ChatColor.GOLD + CubiTechUtil.players1vs1Fighting[0].getName() + ChatColor.YELLOW + " gegen " + ChatColor.GOLD + CubiTechUtil.players1vs1Fighting[1].getName() + ChatColor.YELLOW + ".");
				}

				p.sendMessage(ChatColor.GOLD + " Spieler in der Warteschlange: " + ChatColor.YELLOW + CubiTechUtil.players1vs1Waiting.size());
				int count = 1;
				for (Player q : CubiTechUtil.players1vs1Waiting) {
					p.sendMessage(ChatColor.GOLD + "  " + count + ": " + ChatColor.YELLOW + " " + q.getName());
					count++;
				}
				p.sendMessage(ChatColor.GOLD + " /1vs1" + ChatColor.YELLOW + " - Zeige Informationen ueber 1vs1.");
				p.sendMessage(ChatColor.GOLD + " /1vs1 betreten" + ChatColor.YELLOW + " - Betritt die Warteschlange der Arena.");
				p.sendMessage(ChatColor.GOLD + " /1vs1 verlassen" + ChatColor.YELLOW + " - Verlasse die Warteschlange der Arena.");
				p.sendMessage(ChatColor.AQUA + "-- 1vs1 --");
			}
		} else if (args.length == 1) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (args[0].equalsIgnoreCase("setLocation1")) {
					if (CubiTechUtil.isPlayerAdmin(p)) {
						CubiTechUtil.set1vs1Location1(p.getLocation());
						CubiTechUtil.reload1vs1();
						p.sendMessage("1vs1 Location 1 wurde gesetzt!");
					}
				} else if (args[0].equalsIgnoreCase("setLocation2")) {
					if (CubiTechUtil.isPlayerAdmin(p)) {
						CubiTechUtil.set1vs1Location2(p.getLocation());
						CubiTechUtil.reload1vs1();
						p.sendMessage("1vs1 Location 2 wurde gesetzt!");
					}
				} else if (args[0].equalsIgnoreCase("join") || args[0].equalsIgnoreCase("betreten")) {
					if (!CubiTechUtil.players1vs1Waiting.contains(p)) {
						CubiTechUtil.players1vs1Waiting.add(p);
						p.sendMessage(ChatColor.YELLOW + "Du hast die 1vs1 Warteschlange betreten.");
						CubiTechUtil.check1vs1();
					} else {
						p.sendMessage(ChatColor.RED + "Du bist bereits in der Warteschlange.");
					}
				} else if (args[0].equalsIgnoreCase("leave") || args[0].equalsIgnoreCase("verlassen")) {
					if (CubiTechUtil.players1vs1Fighting[0] != null && CubiTechUtil.players1vs1Fighting[1] != null) {
						if (CubiTechUtil.players1vs1Fighting[0].getName().equals(p.getName())) {
							Bukkit.broadcastMessage(ChatColor.GOLD + CubiTechUtil.players1vs1Fighting[1].getName() + ChatColor.YELLOW + " hat gegen " + ChatColor.RED + p.getName() + ChatColor.YELLOW + " verloren.");
							CubiTechUtil.add1vs1Win(CubiTechUtil.players1vs1Fighting[1]);
							CubiTechUtil.add1vs1Fail(CubiTechUtil.players1vs1Fighting[0]);
							CubiTechUtil.players1vs1Fighting[0].setHealth(20.0);
							CubiTechUtil.players1vs1Fighting[1].setHealth(20.0);
							CubiTechUtil.teleportSpawnInstant(CubiTechUtil.players1vs1Fighting[0]);
							CubiTechUtil.teleportSpawnInstant(CubiTechUtil.players1vs1Fighting[1]);
							CubiTechUtil.players1vs1Fighting[0] = null;
							CubiTechUtil.players1vs1Fighting[1] = null;
							CubiTechUtil.check1vs1();
						} else if (CubiTechUtil.players1vs1Fighting[0].getName().equals(p.getName())) {
							Bukkit.broadcastMessage(ChatColor.GOLD + CubiTechUtil.players1vs1Fighting[0].getName() + ChatColor.YELLOW + " hat gegen " + ChatColor.RED + p.getName() + ChatColor.YELLOW + " verloren.");
							CubiTechUtil.add1vs1Win(CubiTechUtil.players1vs1Fighting[0]);
							CubiTechUtil.add1vs1Fail(CubiTechUtil.players1vs1Fighting[1]);
							CubiTechUtil.players1vs1Fighting[0].setHealth(20.0);
							CubiTechUtil.players1vs1Fighting[1].setHealth(20.0);
							CubiTechUtil.teleportSpawnInstant(CubiTechUtil.players1vs1Fighting[0]);
							CubiTechUtil.teleportSpawnInstant(CubiTechUtil.players1vs1Fighting[1]);
							CubiTechUtil.players1vs1Fighting[0] = null;
							CubiTechUtil.players1vs1Fighting[1] = null;
							CubiTechUtil.check1vs1();
						}
					}
					if (CubiTechUtil.players1vs1Waiting.contains(p)) {
						CubiTechUtil.players1vs1Waiting.remove(p);
						CubiTechUtil.check1vs1();
						p.sendMessage(ChatColor.YELLOW + "Du hast die 1vs1 Warteschlange verlassen.");
					} else {
						p.sendMessage(ChatColor.YELLOW + "Du bist nicht in der 1vs1 Warteschlange.");
					}
				} else {
					p.chat("/1vs1");
				}
			}
		}
	}
}
