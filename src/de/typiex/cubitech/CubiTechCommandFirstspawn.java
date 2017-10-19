package de.typiex.cubitech;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandFirstspawn extends CubiTechCommand {

	public CubiTechCommandFirstspawn(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			Properties props = new Properties();
			try {
				BufferedInputStream stream = new BufferedInputStream(new FileInputStream(new File("plugins//CubiTech//spawn.txt")));
				props.load(stream);
				stream.close();

				String sSpawn = props.getProperty("firstspawn");
				Location spawn = new Location(plugin.getServer().getWorld("world"), Double.valueOf(sSpawn.split(" ")[0]), Double.valueOf(sSpawn.split(" ")[1]), Double.valueOf(sSpawn.split(" ")[2]), Float.valueOf(sSpawn.split(" ")[3]), Float.valueOf(sSpawn.split(" ")[4]));
				
				CubiTechUtil.teleportPlayerAfterDelay(p, spawn);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
