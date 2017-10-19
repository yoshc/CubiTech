package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandBroadcast extends CubiTechCommand {

	public CubiTechCommandBroadcast(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (args.length == 0) {
			
		} else {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (CubiTechUtil.isPlayerAdmin(p)) {
					String msg = "";
					for (int i = 0; i < args.length; i++) {
						msg += args[i];
						msg += " ";
					}
					msg = ChatColor.translateAlternateColorCodes('&', msg);
					Bukkit.broadcastMessage(msg);
				} else {
					p.sendMessage(ChatColor.RED + "Dafuer hast Du leider keine Rechte.");
				}
			}
		}
	}
}
