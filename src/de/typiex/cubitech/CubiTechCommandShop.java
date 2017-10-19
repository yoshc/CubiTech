package de.typiex.cubitech;

import org.bukkit.ChatColor;
import org.bukkit.block.Biome;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandShop extends CubiTechCommand {

	public CubiTechCommandShop(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player)sender;
			
			Biome b = p.getWorld().getBiome(p.getLocation().getBlockX(), p.getLocation().getBlockZ());
			if(b != Biome.EXTREME_HILLS && !CubiTechUtil.isPlayerAdmin(p)) {
				p.sendMessage(ChatColor.RED + "Du kannst den Shop nur in der Hauptstadt oeffnen!");
				return;
			}
			
			CubiTechUtil.openPlayerShop(p);
			
		}
	}
}
