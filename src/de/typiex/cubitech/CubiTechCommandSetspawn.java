package de.typiex.cubitech;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandSetspawn extends CubiTechCommand {
	public CubiTechCommandSetspawn(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		Player p = (Player) sender;
		File f = new File("plugins//CubiTech//spawn.txt");
		if (!f.exists()) {
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
				props.setProperty("spawn", p.getLocation().getX() + " " + p.getLocation().getY() + " " + p.getLocation().getZ() + " " + p.getLocation().getYaw() + " " + p.getLocation().getPitch());
				FileOutputStream fos = new FileOutputStream("plugins//CubiTech//spawn.txt");
				props.store(fos, "-");
				p.sendMessage(ChatColor.GREEN + "Der Spawn wurde auf " + ChatColor.GOLD + "[" + p.getLocation().getX() + "," + p.getLocation().getY() + "," + p.getLocation().getZ() + "] " + ChatColor.GREEN + "gesetzt.");
			} catch (Exception e) {
				e.printStackTrace();
				p.sendMessage("Fehler. Der Spawn konnte nicht gesetzt werden.");
			}
		}
	}
}
