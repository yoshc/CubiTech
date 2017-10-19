package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandMoney extends CubiTechCommand {

	public CubiTechCommandMoney(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				p.sendMessage(ChatColor.GREEN + "Dein Geld: " + ChatColor.YELLOW + CubiTechUtil.getPlayerMoney(p) + " Cubis" + ChatColor.GREEN + ".");
			}
		} else if (args.length == 1) {
			if (sender.isOp() || (sender instanceof Player && CubiTechUtil.getPlayerClass((Player) sender).equalsIgnoreCase("Admin"))) {
				Player p = Bukkit.getPlayer(args[0]);
				if (p == null) {
					sender.sendMessage(ChatColor.RED + "Der Spieler " + ChatColor.GOLD + args[0] + ChatColor.RED + " ist nicht online.");
				} else {
					sender.sendMessage(ChatColor.GREEN + "Geld von " + ChatColor.GOLD + p.getName() + ChatColor.GREEN + ": " + ChatColor.YELLOW + CubiTechUtil.getPlayerMoney(p) + " Cubi" + ChatColor.GREEN + ".");
				}
			}

		}
	}
}
