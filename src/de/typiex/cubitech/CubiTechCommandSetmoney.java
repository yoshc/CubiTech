package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandSetmoney extends CubiTechCommand {

	public CubiTechCommandSetmoney(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (args.length < 2) {
			sender.sendMessage(ChatColor.RED + "Fehler. Du musst einen Spieler und einen Geld-wert angeben");
		} else {
			try {
				Player p = Bukkit.getPlayer(args[0]);
				CubiTechUtil.setPlayerMoney(p, Integer.valueOf(args[1]));
				sender.sendMessage(ChatColor.GREEN + "Das Geld von " + p.getName() + " wurden auf " + ChatColor.GOLD + args[1] + ChatColor.GREEN + " gesetzt.");
				CubiTechUtil.saveState();
				CubiTechUtil.reloadScoreboard();
			} catch (Exception ex) {
			}
		}
	}
}
