package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.typiex.cubitech.CubiTechUtil.PlayerBool;

public class CubiTechCommandTpa extends CubiTechCommand {

	public CubiTechCommandTpa(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (CubiTechUtil.isPlayerAdmin(p) || CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Ritter") || CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Adeliger") || CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("YouTuber")) {
				if (args.length > 0) {
					Player target = Bukkit.getPlayer(args[0]);
					if (target == null) {
						p.sendMessage(ChatColor.RED + "Fehler. Der Spieler " + ChatColor.GOLD + args[0] + ChatColor.RED + " ist nicht online.");
					} else {
						// [Invited Player] [Invitor]
						CubiTechUtil.playersTpRequests.put(target, new PlayerBool(p, false));
						p.sendMessage(ChatColor.GREEN + "Du hast eine Teleportationsanfrage an " + ChatColor.GOLD + target.getName() + ChatColor.GREEN + " versendet.");
						target.sendMessage(ChatColor.GREEN + "Der Spieler " + ChatColor.GOLD + p.getName() + ChatColor.GREEN + " fragt, er sich zu dir teleportieren darf.");
						target.sendMessage(ChatColor.GREEN + "Schreibe " + ChatColor.DARK_GREEN + "/tpaccept" + ChatColor.GREEN + ", um sie ihn zu dir zu teleportieren.");
					}
				} else {
					p.sendMessage(ChatColor.RED + "Fehler. Du musst einen Spieler angeben.");
				}
			} else {
				p.sendMessage(ChatColor.RED + "Dafuer hast Du leider keine Rechte!");
			}
		}

	}
}
