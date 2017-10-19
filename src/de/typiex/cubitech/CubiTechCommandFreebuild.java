package de.typiex.cubitech;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandFreebuild extends CubiTechCommand {

	public CubiTechCommandFreebuild(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {

		if (sender instanceof Player) {
			Player p = (Player) sender;
			
			p.sendMessage("§cDie Freebuild Welt ist nicht laenger erreichbar.");
			
		}
	}
}
