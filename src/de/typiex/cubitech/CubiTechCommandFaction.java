package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandFaction extends CubiTechCommand {

	public CubiTechCommandFaction(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (CubiTechUtil.isPlayerAdmin(p) || CubiTechUtil.getPlayerFaction(p).equalsIgnoreCase("none") || CubiTechUtil.getPlayerFaction(p) == null) {
					CubiTechUtil.chooseFaction(p);
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Fehler. Du musst ein Spieler sein.");
			}
		} else if (args.length == 1) {
			try {
				if (sender instanceof ConsoleCommandSender || (sender instanceof Player && CubiTechUtil.isPlayerAdmin((Player) sender))) {
					Player target = Bukkit.getPlayer(args[0]);
					if (target == null) {
						sender.sendMessage(ChatColor.RED + "Fehler. Der Spieler " + args[0] + " ist nicht online.");
					} else {
						if (CubiTechUtil.isPlayerAdmin(target) || CubiTechUtil.getPlayerFaction(target).equalsIgnoreCase("none") || CubiTechUtil.getPlayerFaction(target) == null) {
							CubiTechUtil.chooseFaction(target);
						}
					}
				}
			} catch (Exception ex) {
			}
		}
	}
}
