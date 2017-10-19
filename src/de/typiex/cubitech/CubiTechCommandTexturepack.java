package de.typiex.cubitech;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandTexturepack extends CubiTechCommand {

	public CubiTechCommandTexturepack(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			
			if(args.length == 0) {
				p.sendMessage(ChatColor.RED + "Fehler. Du musst einen URL angeben.");
			} else {
				p.setTexturePack(args[0]);
				p.sendMessage(ChatColor.GREEN + "Dein TexturePack ist nun " + args[0]);
			}
			
		}
	}
}
