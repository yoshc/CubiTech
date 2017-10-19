package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandKill extends CubiTechCommand {

	public CubiTechCommandKill(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if(args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Fehler. Du musst einen Spieler angeben.");
		} else {
			Player p = Bukkit.getPlayer(args[0]);
			if(p == null) {
				sender.sendMessage(ChatColor.RED + "Fehler. Der Spieler " + ChatColor.GOLD + args[0] + ChatColor.RED + " ist nicht online.");
			} else {
				p.setHealth(0.0);
			}
		}
	}
}
