package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandTp extends CubiTechCommand {
	
	public CubiTechCommandTp(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}
	
	public void onCommand(CommandSender sender, String[] args) {
		
		if(sender instanceof Player) {
			if(!(CubiTechUtil.isPlayerAdmin((Player)sender) || CubiTechUtil.getPlayerClass((Player)sender).equalsIgnoreCase("Supporter"))) {
				sender.sendMessage("§cDafuer hast Du leider keine Rechte!");
				return;
			}
		}
		
		switch (args.length) {
		case 0: // /tp
			sender.sendMessage(ChatColor.RED + "Fehler. Bitte gib einen Spieler an.");
			break;
		case 1: // /tp <player>
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "Fehler. Du musst ein Spieler sein.");
			} else {
				Player player = (Player) sender;
				Player target = Bukkit.getPlayer(args[0]);
				if (target == null) {
					player.sendMessage(ChatColor.RED + "Fehler. Der Spieler " + ChatColor.GOLD + args[0] + ChatColor.RED + " ist nicht online.");
				} else {
					player.teleport(target);
					player.sendMessage(ChatColor.GREEN + "Du wurdest zu " + ChatColor.GOLD + target.getName() + ChatColor.GREEN + " teleportiert.");
				}
			}
			break;
		case 2: // /tp <player> <target>
			Player player = Bukkit.getPlayer(args[0]);
			if (player == null) {
				sender.sendMessage(ChatColor.RED + "Fehler. Der Spieler " + ChatColor.GOLD + args[0] + ChatColor.RED + " ist nicht online.");
			} else {
				Player target = Bukkit.getPlayer(args[1]);
				if (target == null) {
					sender.sendMessage(ChatColor.RED + "Fehler. Der Spieler " + ChatColor.GOLD + args[1] + ChatColor.RED + " ist nicht online.");
				} else {
					player.teleport(target);
					sender.sendMessage(ChatColor.GOLD + player.getName() + ChatColor.GREEN + " wurde zu " + ChatColor.GOLD + target.getName() + ChatColor.GREEN + " teleportiert.");
				}
			}
			break;
		}
		
	}
	
}
