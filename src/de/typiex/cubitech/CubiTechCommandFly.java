package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandFly extends CubiTechCommand {

	public CubiTechCommandFly(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (CubiTechUtil.isPlayerAdmin(p) || CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("YouTuber")) {
				if (args.length > 0) {
					Player target = Bukkit.getPlayer(args[0]);
					if (target == null) {
						p.sendMessage(ChatColor.RED + "Fehler. Der Spieler " + ChatColor.GOLD + args[0] + ChatColor.RED + " ist nicht online.");
					} else {
						if (target.getAllowFlight()) {
							target.setAllowFlight(false);
							target.sendMessage(ChatColor.GREEN + "Du kannst nun nicht mehr fliegen.");
							p.sendMessage(ChatColor.GOLD + target.getName() + ChatColor.GREEN + " kann nun nicht mehr fliegen.");
						} else {
							target.setAllowFlight(true);
							target.sendMessage(ChatColor.GREEN + "Du kannst nun fliegen.");
							p.sendMessage(ChatColor.GOLD + target.getName() + ChatColor.GREEN + " kann nun fliegen.");
						}
					}
				} else {
					if (p.getAllowFlight()) {
						p.setAllowFlight(false);
						p.sendMessage(ChatColor.GREEN + "Du kannst nun nicht mehr fliegen.");
					} else {
						p.setAllowFlight(true);
						p.sendMessage(ChatColor.GREEN + "Du kannst nun fliegen.");
					}
				}
			}
		}

	}
}
