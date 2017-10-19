package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandKick extends CubiTechCommand {

	public CubiTechCommandKick(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {

		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (CubiTechUtil.isPlayerAdmin(p) || CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Supporter")) {
				if (args.length == 0) {
					sender.sendMessage(ChatColor.RED + "Fehler. Du musst einen Spieler angeben.");
				} else if (args.length >= 1) {
					Player player = Bukkit.getPlayer(args[0]);
					if (player == null) {
						sender.sendMessage(ChatColor.RED + "Fehler. Der Spieler " + ChatColor.GOLD + args[0] + ChatColor.RED + " ist nicht online.");
					} else {
						String reason = "";
						if (args.length > 1) {
							for (int i = 1; i < args.length; i++) {
								reason += args[i];
								reason += " ";
							}
						} else {
							reason = "-";
						}
						player.kickPlayer(ChatColor.GOLD + "Du wurdest gekickt! Grund: " + ChatColor.RED + reason);
						for (Player q : Bukkit.getOnlinePlayers()) {
							q.sendMessage(ChatColor.GOLD + player.getName() + ChatColor.RED + " wurde gekickt. Grund: " + reason);
						}
					}
				}
			} else {
				p.sendMessage("§cDafuer hast Du leider keien Rechte!");
			}
		}

	}
}
