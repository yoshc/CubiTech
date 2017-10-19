package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandSetfaction extends CubiTechCommand {

	public CubiTechCommandSetfaction(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (args.length < 2) {
			sender.sendMessage(ChatColor.RED + "Fehler. Du musst einen Spieler und einen Fraktion(none,grass,sand,snow) angeben.");
		} else {
			try {
				Player p = Bukkit.getPlayer(args[0]);
				CubiTechUtil.setPlayerFaction(p, args[1]);
				sender.sendMessage(ChatColor.GREEN + "Die Fraktion von " + p.getName() + " wurden auf " + ChatColor.GOLD + args[1] + ChatColor.GREEN + " gesetzt.");
				CubiTechUtil.saveState();
				CubiTechUtil.reloadScoreboard();
			} catch (Exception ex) {
				ex.printStackTrace();
				sender.sendMessage(ChatColor.RED + "Fehler. Die Fraktion konnte nicht gesetzt werden.");
			}
		}
	}
}
