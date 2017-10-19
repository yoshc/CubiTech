package de.typiex.cubitech;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class CubiTechCommandKillall extends CubiTechCommand {

	public CubiTechCommandKillall(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {

		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Admin") || p.isOp()) {
				int count = 0;
				for (Entity e : p.getWorld().getEntities()) {
					if (!(e instanceof Player)) {
						e.remove();
						count++;
					}
				}
				p.sendMessage(ChatColor.YELLOW + "Es wurden " + ChatColor.GOLD + count + ChatColor.YELLOW + " Entities entfernt.");
			} else {
				p.sendMessage(ChatColor.RED + "Dafuer hast Du leider keine Rechte!");
			}
		}
	}
}
