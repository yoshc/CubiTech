package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandSetclass extends CubiTechCommand {

	public CubiTechCommandSetclass(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (args.length < 2) {
			sender.sendMessage(ChatColor.RED + "Fehler. Du musst einen Spieler und eine Klasse angeben.");
		} else {

			Player p = Bukkit.getPlayer(args[0]);
			if (p == null) {
				sender.sendMessage(ChatColor.RED + "Fehler. Der Spieler " + ChatColor.GOLD + args[0] + ChatColor.RED + " ist nicht online.");
				return;
			}
			
			CubiTechUtil.playerClasses.put(p, args[1]);
			CubiTechUtil.saveState();
			
			sender.sendMessage(ChatColor.GREEN + "Der Spieler " + ChatColor.GOLD + p.getName() + ChatColor.GREEN + " wurde in die Klasse "
					+ ChatColor.BLUE + args[1] + ChatColor.GREEN + " gesetzt.");
			CubiTechUtil.setStdArmor(p);
			if (p != null) {
				if (args[1].equalsIgnoreCase("Ritter")) {
					CubiTechUtil.giveRitterItems(p);
				} else if (args[1].equalsIgnoreCase("Adeliger")) {
					CubiTechUtil.giveAdeligerItems(p);
				}
			}

		}
	}
}
