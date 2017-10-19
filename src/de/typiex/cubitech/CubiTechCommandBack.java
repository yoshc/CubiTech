package de.typiex.cubitech;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandBack extends CubiTechCommand {

	public CubiTechCommandBack(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (CubiTechUtil.isPlayerAdmin(p) || CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("YouTuber") || CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Adeliger")) {	
				if(CubiTechUtil.playersBack.containsKey(p)) {
					Location l = CubiTechUtil.playersBack.get(p);
					p.teleport(l);
					p.sendMessage(ChatColor.GREEN + "Du wurdest zu deinem letzten Todespunkt teleportiert.");
				} else {
					p.sendMessage(ChatColor.RED + "Du hast keinen letzten Todespunkt.");
				}
			}
		}

	}
}
