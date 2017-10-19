package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandEp extends CubiTechCommand {

	public CubiTechCommandEp(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				p.sendMessage(ChatColor.GREEN + "Deine EP: " + ChatColor.YELLOW + CubiTechUtil.getPlayerEP(p) + " / 1000 " + ChatColor.GREEN + ".");
			}
		} else if (args.length == 1) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (CubiTechUtil.isPlayerAdmin(p)) {
					Player target = Bukkit.getPlayer(args[0]);
					if (target == null) {
						sender.sendMessage(ChatColor.RED + "Der Spieler " + ChatColor.GOLD + args[0] + ChatColor.RED + " ist nicht online.");
					} else {
						sender.sendMessage(ChatColor.GREEN + "EP von " + ChatColor.GOLD + target.getName() + ChatColor.GREEN + ": " + ChatColor.YELLOW + CubiTechUtil.getPlayerEP(target) + " / 1000 " + ChatColor.GREEN + ".");
					}
				}
			}

		}
	}
}
