package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandTpc extends CubiTechCommand {

	public CubiTechCommandTpc(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (args.length < 3) {
			sender.sendMessage(ChatColor.RED + "Fehler. Du musst eine Koordinate angeben.");
		}
		if (args.length == 3) {
			try {
				int x = Integer.valueOf(args[0]);
				int y = Integer.valueOf(args[1]);
				int z = Integer.valueOf(args[2]);
				if (!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.RED + "Fehler. Du musst ein Spieler sein.");
				} else {
					Player p = (Player) sender;
					Location l = p.getLocation();
					l.setX((double) x);
					l.setY((double) y);
					l.setZ((double) z);
					p.teleport(l);
					sender.sendMessage(ChatColor.GREEN + "Du wurdest zu " + ChatColor.GOLD + "[" + x + "," + y + "," + z + "]" + ChatColor.GREEN + " teleportiert");
				}
			} catch (Exception ex) {
				sender.sendMessage(ChatColor.RED + "Fehler. Du musst eine Koordinate angeben.");
			}
		}
		if (args.length == 4) {
			Player p = Bukkit.getPlayer(args[0]);
			if (p == null) {
				sender.sendMessage(ChatColor.RED + "Fehler. Der Spieler " + ChatColor.GOLD + args[0] + ChatColor.RED + " ist nicht online.");
			} else {
				try {
					int x = Integer.valueOf(args[1]);
					int y = Integer.valueOf(args[2]);
					int z = Integer.valueOf(args[3]);
					Location l = p.getLocation();
					l.setX((double) x);
					l.setY((double) y);
					l.setZ((double) z);
					p.teleport(l);
					sender.sendMessage(ChatColor.GREEN + "Der Spieler " + ChatColor.GOLD + p.getName() + ChatColor.GREEN + " wurde zu " + ChatColor.GOLD + "[" + x + "," + y + "," + z + "]" + ChatColor.GREEN + " teleportiert.");
				} catch (Exception ex) {
					sender.sendMessage(ChatColor.RED + "Fehler. Du musst eine Koordinate angeben.");
				}
			}
		}

	}
}
