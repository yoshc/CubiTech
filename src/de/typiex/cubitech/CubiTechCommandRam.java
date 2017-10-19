package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandRam extends CubiTechCommand {

	public CubiTechCommandRam(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {

		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Admin") || p.isOp()) {
				int mb = 1024 * 1024;
				Runtime.getRuntime().gc();
				p.sendMessage(ChatColor.YELLOW + "Max Memory: " + ChatColor.GOLD + Runtime.getRuntime().maxMemory() / mb + " MB");
				p.sendMessage(ChatColor.YELLOW + "Total Memory: " + ChatColor.GOLD + Runtime.getRuntime().totalMemory() / mb + " MB");
				p.sendMessage(ChatColor.YELLOW + "Free Memory: " + ChatColor.GOLD + Runtime.getRuntime().freeMemory() / mb + " MB");
				p.sendMessage(ChatColor.YELLOW + "Used Memory: " + ChatColor.GOLD + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / mb + " MB");
				p.sendMessage(ChatColor.YELLOW + "Availabe Processors: " + ChatColor.GOLD + Runtime.getRuntime().availableProcessors());
				p.sendMessage(ChatColor.YELLOW + "Active Tasks: " + ChatColor.GOLD + Bukkit.getScheduler().getActiveWorkers().size());
				p.sendMessage(ChatColor.YELLOW + "Pending Tasks: " + ChatColor.GOLD + Bukkit.getScheduler().getPendingTasks().size());
			} else {
				p.sendMessage(ChatColor.RED + "Dafuer hast Du leider keine Rechte!");
			}
		}

	}
}
