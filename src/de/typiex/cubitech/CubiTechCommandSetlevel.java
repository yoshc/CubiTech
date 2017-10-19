package de.typiex.cubitech;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandSetlevel extends CubiTechCommand {

	public CubiTechCommandSetlevel(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (args.length < 2) {
			sender.sendMessage(ChatColor.RED + "Fehler. Du musst einen Spieler und ein Level angeben");
		} else {
			try {
				Properties props = new Properties();
				BufferedInputStream stream = new BufferedInputStream(new FileInputStream(new File("plugins//CubiTech//users.txt")));
				props.load(stream);
				stream.close();
				// final String sClass = props.getProperty(args[0]).split(" ")[0];
				final String sClass = CubiTechUtil.getPlayerClass(Bukkit.getPlayerExact(args[0]));
				final int ep = CubiTechUtil.getPlayerEP(Bukkit.getPlayerExact(args[0]));
				int newLevel = Integer.valueOf(args[1]);
				props.setProperty(args[0], sClass + " " + newLevel + " " + ep);
				Player player = Bukkit.getPlayer(args[0]);
				if (player != null) {
					CubiTechUtil.playerLevels.put(player, newLevel);
					plugin.getLogger().info("Put online Player " + player.getName() + " into level " + newLevel);
				} else {
					plugin.getLogger().info("Put offline Player " + args[0] + " into level " + newLevel);
				}
				FileOutputStream fos = new FileOutputStream("plugins//CubiTech//users.txt");
				props.store(fos, "-");
				CubiTechUtil.reloadScoreboard();
				sender.sendMessage(ChatColor.GREEN + "Der Spieler " + ChatColor.GOLD + args[0] + ChatColor.GREEN + " wurde in das Level " + ChatColor.BLUE + newLevel + ChatColor.GREEN + " gesetzt.");
			} catch (Exception e) {
				sender.sendMessage(ChatColor.RED + "Fehler. Das Level konnte nicht gesetzt werden.");
			}
		}
	}
}
