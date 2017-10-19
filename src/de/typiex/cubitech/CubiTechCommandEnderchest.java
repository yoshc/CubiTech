package de.typiex.cubitech;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandEnderchest extends CubiTechCommand {

	public CubiTechCommandEnderchest(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {

		if(sender instanceof Player) {
			Player p = (Player)sender;
			if(CubiTechUtil.isPlayerAdmin(p) || CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("YouTuber") ||CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Ritter") || CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Adeliger")) {
				// Open the EnderChest
				p.openInventory(p.getEnderChest());
			} else {
				p.sendMessage(ChatColor.RED + "Dafuer hast Du leider keine Rechte.");
			}
		}

	}
}
