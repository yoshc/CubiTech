package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandGod extends CubiTechCommand {

	public CubiTechCommandGod(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player target = (Player) sender;
				if (CubiTechUtil.isPlayerAdmin(target)) {
					if (!CubiTechUtil.getPlayerGodMode(target)) {
						CubiTechUtil.setPlayerGodMode(target, true);
						sender.sendMessage(ChatColor.GREEN + "Godmode aktiviert.");
					} else {
						CubiTechUtil.setPlayerGodMode(target, false);
						sender.sendMessage(ChatColor.RED + "Godmode deaktiviert.");
					}
				}
			}
		} else if (args.length == 1) {
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(ChatColor.RED + "Der Spieler " + ChatColor.GOLD + args[0] + ChatColor.RED + " ist nicht online.");
			} else {
				if (CubiTechUtil.isPlayerAdmin(target)) {
					if (CubiTechUtil.getPlayerGodMode(target) == false) {
						CubiTechUtil.setPlayerGodMode(target, true);
						sender.sendMessage(ChatColor.GREEN + "Godmode für Spieler " + ChatColor.GOLD + target.getName() + ChatColor.GREEN + " aktiviert.");
					} else {
						CubiTechUtil.setPlayerGodMode(target, false);
						sender.sendMessage(ChatColor.RED + "Godmode für Spieler " + ChatColor.GOLD + target.getName() + ChatColor.RED + " deaktiviert.");
					}
				}
			}
		}
	}
}
