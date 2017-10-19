package de.typiex.cubitech;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandSpawn extends CubiTechCommand {

	public CubiTechCommandSpawn(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Fehler. Du musst ein Spieler sein.");
		} else {
			Player p = (Player) sender;
			File f = new File("plugins//CubiTech//spawn.txt");
			if (!f.exists()) {
				p.sendMessage(ChatColor.RED + "Fehler. Der Spawn wurde nicht gesetzt.");
				try {
					f.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					Properties props = new Properties();
					BufferedInputStream stream = new BufferedInputStream(new FileInputStream(new File("plugins//CubiTech//spawn.txt")));
					props.load(stream);
					stream.close();

					String sSpawn;
					if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Knappe") || CubiTechUtil.getPlayerLevel(p) < 5) {
						sSpawn = props.getProperty("firstspawn");
					} else {
						sSpawn = props.getProperty("spawn");
					}
					Location spawn = new Location(plugin.getServer().getWorld("world"), Double.valueOf(sSpawn.split(" ")[0]), Double.valueOf(sSpawn
							.split(" ")[1]), Double.valueOf(sSpawn.split(" ")[2]), Float.valueOf(sSpawn.split(" ")[3]), Float.valueOf(sSpawn
							.split(" ")[4]));

					if (p.getWorld().getBiome(p.getLocation().getBlockX(), p.getLocation().getBlockZ()) == Biome.EXTREME_HILLS) {
						CubiTechUtil.teleportSpawnInstant(p);
					} else {
						CubiTechUtil.teleportPlayerAfterDelay(p, spawn);
					}
				} catch (Exception e) {
					e.printStackTrace();
					p.sendMessage("Fehler. Du konntest nicht zum spawn teleportiert werden.");
				}
			}
		}
	}
}
