package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandKickall extends CubiTechCommand {

	public CubiTechCommandKickall(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		try {
			if (sender instanceof ConsoleCommandSender || (sender instanceof Player && CubiTechUtil.isPlayerAdmin((Player) sender))) {
				String reason = "";
				if (args.length == 0) {
					reason = "-";
				} else {
					for (int i = 0; i < args.length; i++) {
						reason += args[i];
						reason += " ";
					}
				}
				for (Player p : Bukkit.getOnlinePlayers()) {
					p.kickPlayer(ChatColor.GOLD + "Alle wurden gekickt! Grund: " + ChatColor.RED + reason);
				}
				plugin.getLogger().info(sender.getName() + " kicked all Players with reason: " + reason);
			}
		} catch (Exception ex) {
		}
	}

}
