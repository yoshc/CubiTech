package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandGetclass extends CubiTechCommand {

	public CubiTechCommandGetclass(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				sender.sendMessage(ChatColor.GREEN + "Du bist derzeit in der Klasse " + ChatColor.GOLD + CubiTechUtil.getPlayerClass(p) + ChatColor.GREEN + ".");
			}
		} else if (args.length == 1) {
			if (sender instanceof Player) {
				Player player = Bukkit.getPlayer(args[0]);
				if (player != null) {
					sender.sendMessage(ChatColor.GREEN + "Der Spieler " + ChatColor.GOLD + player.getName() + ChatColor.GREEN + " ist derzeit in der Gruppe/Klasse " + ChatColor.BLUE + CubiTechUtil.getPlayerClass(player) + ChatColor.GREEN + ".");
				} else {
					sender.sendMessage(ChatColor.RED + "Der Spieler " + ChatColor.GOLD + args[0] + ChatColor.RED + " ist nicht online.");
				}
			}
		}
	}
}
