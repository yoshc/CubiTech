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

public class CubiTechCommandSetfirstspawn extends CubiTechCommand {

	public CubiTechCommandSetfirstspawn(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Fehler. Du musst ein Spieler sein.");
		}
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

				props.setProperty("firstspawn", p.getLocation().getX() + " " + p.getLocation().getY() + " " + p.getLocation().getZ() + " " + p.getLocation().getYaw() + " " + p.getLocation().getPitch());
				
				FileOutputStream fos = new FileOutputStream("plugins//CubiTech//spawn.txt");
				props.store(fos, "-");
				p.getWorld().setSpawnLocation((int)p.getLocation().getX(), (int)p.getLocation().getY(), (int)p.getLocation().getZ());
				p.sendMessage(ChatColor.GREEN + "Der FirstSpawn wurde auf " + ChatColor.GOLD + "[" + p.getLocation().getX() + "," + p.getLocation().getY() + "," + p.getLocation().getZ() + "] " + ChatColor.GREEN + "gesetzt.");
			} catch (Exception e) {
				e.printStackTrace();
				p.sendMessage("Fehler. Du konntest nicht zum FirstSpawn teleportiert werden.");
			}
		}
	}
}
