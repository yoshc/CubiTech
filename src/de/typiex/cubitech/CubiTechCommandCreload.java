package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandCreload extends CubiTechCommand {

	public CubiTechCommandCreload(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		try {
			if (sender instanceof ConsoleCommandSender || (sender instanceof Player && CubiTechUtil.isPlayerAdmin((Player) sender))) {
				plugin.getServer().broadcastMessage(ChatColor.RED + "---------------");
				plugin.getServer().broadcastMessage(ChatColor.DARK_RED + "RELOAD");
				plugin.getServer().broadcastMessage(ChatColor.RED + "---------------");
				plugin.getServer().broadcastMessage(ChatColor.RED + "Die Daten des Servers werden neu geladen.");
				plugin.getServer().broadcastMessage(ChatColor.RED + "Es kann zu Lag kommen.");
				plugin.getServer().broadcastMessage(ChatColor.YELLOW + "Reload in " + ChatColor.GOLD + "3" + ChatColor.YELLOW + " ...");
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						plugin.getServer().broadcastMessage(ChatColor.YELLOW + "Reload in " + ChatColor.GOLD + "2" + ChatColor.YELLOW + " ...");
					}
				}, 20L);
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						plugin.getServer().broadcastMessage(ChatColor.YELLOW + "Reload in " + ChatColor.GOLD + "1" + ChatColor.YELLOW + " ...");
					}
				}, 40L);
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						plugin.getServer().broadcastMessage(ChatColor.YELLOW + "=> " + ChatColor.GOLD + ChatColor.BOLD + "RELOAD" + ChatColor.RESET + ChatColor.YELLOW + " !");
						
						for(Player p : Bukkit.getOnlinePlayers()) {
							CubiTechUtil.clearChat(p);
							p.sendMessage("§e§l[]§r§a -----------§6 " + "CubiTech" + " §a----------- " + "§e§l[]§r");
							p.sendMessage("              §2Server-IP    : §3cubitech.eu");
							p.sendMessage("              §2TS3-IP           : §3cubitech.eu");
							p.sendMessage("              §2Website          : §3cubitech.name");
							p.sendMessage("§e§l[]§r§a -----------§6 " + "CubiTech" + " §a----------- " + "§e§l[]§r");
							p.sendMessage("");
							p.sendMessage("");
							
							p.sendMessage("§4§l[]§r§c -----------§5 " + "Reload" + " §c----------- " + "§4§l[]§r");
							p.sendMessage("              §cDie Daten des Servers werden neu geladen.");
							p.sendMessage("              §cEs kann dabei zu Lag und eventuell zu");
							p.sendMessage("              §ceinem Absturz des Servers kommen.");
							p.sendMessage("§4§l[]§r§c -----------§5 " + "Reload" + " §c----------- " + "§4§l[]§r");
							p.sendMessage("");
							
						}
						
						for (World w : Bukkit.getWorlds()) {
							w.save();
						}
						plugin.getServer().savePlayers();
						plugin.getServer().reload();
						for (Player p : Bukkit.getOnlinePlayers()) {
							CubiTechUtil.sendMOTD(p);
						}
					}
				}, 60L);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
