package de.typiex.cubitech;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandVote extends CubiTechCommand {

	public CubiTechCommandVote(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			
			p.sendMessage("§0§l[]§8--- §6§lVOTE §8---§0§l[]");
			p.sendMessage("§c§l1. §bKlicke auf §6§lhttp://vote.cubitech.eu/");
			p.sendMessage("§c§l2. §bLass Minecraft den Link oeffnen");
			p.sendMessage("§c§l3. §bGib den §e§oCode §bein");
			p.sendMessage("§c§l4. §bGib deinen §e§oMinecraft-Namen §bein");
			p.sendMessage("§c§l5. §bKlicke auf §6§oBewerten");
			p.sendMessage("§c§l6. §2§lErhalte eine tolle Belohnung!");
			p.sendMessage("§0§l[]§8--- §6§lVOTE §8---§0§l[]");
			
		}

	}
}
