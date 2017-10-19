package de.typiex.cubitech;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandTpaccept extends CubiTechCommand {

	public CubiTechCommandTpaccept(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if(CubiTechUtil.playersTpRequests.containsKey(p)) {
				if(!CubiTechUtil.playersTpRequests.get(p).tpahere) {
					CubiTechUtil.playersTpRequests.get(p).invited.teleport(p.getLocation());
					CubiTechUtil.playersTpRequests.get(p).invited.sendMessage(ChatColor.GREEN + "Der Spieler " + ChatColor.GOLD + p.getName() + ChatColor.GREEN + " hat die Anfrage angenommen");
					CubiTechUtil.playersTpRequests.remove(p);
				} else {
					p.teleport(CubiTechUtil.playersTpRequests.get(p).invited.getLocation());
					CubiTechUtil.playersTpRequests.get(p).invited.sendMessage(ChatColor.GREEN + "Der Spieler " + ChatColor.GOLD + p.getName() + ChatColor.GREEN + " hat die Anfrage angenommen");
					CubiTechUtil.playersTpRequests.remove(p);
				}
			} else {
				p.sendMessage(ChatColor.RED + "Du hast keine Teleportationsanfrage.");
			}
		}
	}
}
