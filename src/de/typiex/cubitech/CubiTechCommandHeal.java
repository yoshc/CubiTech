package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandHeal extends CubiTechCommand {

	public CubiTechCommandHeal(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player target = (Player) sender;
				if (CubiTechUtil.isPlayerAdmin(target)) {
					target.setHealth(20.0);
					target.setFoodLevel(20);
					target.setSaturation(1.0f);
					sender.sendMessage(ChatColor.GREEN + "Du wurdest geheilt!");
				}
			}
		} else if (args.length == 1) {
			try {
				if (sender instanceof ConsoleCommandSender || (sender instanceof Player && CubiTechUtil.isPlayerAdmin((Player) sender))) {
					Player target = Bukkit.getPlayer(args[0]);
					if (target == null) {
						sender.sendMessage(ChatColor.RED + "Der Spieler " + ChatColor.GOLD + args[0] + ChatColor.RED + " ist nicht online.");
					} else {

						target.setHealth(20.0);
						target.setFoodLevel(20);
						target.setSaturation(1.0f);
						sender.sendMessage(ChatColor.GREEN + "Der Spieler " + ChatColor.GOLD + target.getName() + ChatColor.GREEN + " wurde geheilt.");
					}
				}
			} catch (Exception ex) {
			}
		}
	}
}
