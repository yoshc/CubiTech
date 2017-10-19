package de.typiex.cubitech;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandAfk extends CubiTechCommand {

	public CubiTechCommandAfk(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;

			if (CubiTechUtil.playersAfk.contains(p)) {
				p.sendMessage("§cDu bist bereits AFK.");
			} else {
				CubiTechUtil.playersAfk.add(p);
				p.sendMessage("§eDu bist nun AFK.");
			}
		}
	}
}
