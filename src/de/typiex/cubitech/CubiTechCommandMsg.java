package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandMsg extends CubiTechCommand {
	
	public CubiTechCommandMsg(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}
	
	public void onCommand(CommandSender sender, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Fehler. Du musst einen Spieler angeben.");
		} else if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Fehler. Du musst eine Nachricht angeben.");
		} else if (args.length >= 2) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "Fehler. Du musst ein Spieler sein.");
			} else {
				Player player = (Player) sender;
				Player target = Bukkit.getPlayer(args[0]);
				if (target == null) {
					player.sendMessage(ChatColor.RED + "Fehler. Der Spieler " + ChatColor.GOLD + args[0] + ChatColor.RED + " ist nicht online.");
				} else if (target == player) {
					player.sendMessage(ChatColor.RED + "Fehler. Du kannst dich nicht selbst anschreiben.");
				} else {
					String message = "";
					for (int i = 1; i < args.length; i++) {
						message += args[i];
						message += " ";
					}
					player.sendMessage(ChatColor.GRAY + "[ Du -> " + target.getName() + " ] : " + ChatColor.WHITE + message);
					target.sendMessage(ChatColor.GRAY + "[ " + player.getName() + " -> Dich" + " ] : " + ChatColor.WHITE + message);
				}
			}
		}
	}
}
