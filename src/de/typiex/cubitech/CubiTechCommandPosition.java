package de.typiex.cubitech;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandPosition extends CubiTechCommand {

	public CubiTechCommandPosition(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player)sender;
			p.sendMessage(ChatColor.GOLD + "Deine Position:");
			p.sendMessage(ChatColor.GOLD + "X: " + ChatColor.YELLOW + p.getLocation().getX());
			p.sendMessage(ChatColor.GOLD + "Y: " + ChatColor.YELLOW + p.getLocation().getY());
			p.sendMessage(ChatColor.GOLD + "Z: " + ChatColor.YELLOW + p.getLocation().getZ());
			p.sendMessage(ChatColor.GOLD + "Yaw: " + ChatColor.YELLOW + p.getLocation().getYaw());
			p.sendMessage(ChatColor.GOLD + "Pitch: " + ChatColor.YELLOW + p.getLocation().getPitch());
		}
		
	}
}
