package de.typiex.cubitech;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CubiTechCommandBank extends CubiTechCommand {

	public CubiTechCommandBank(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {

		if (sender instanceof Player) {
			Player p = (Player) sender;
			
			p.sendMessage("§6-- §2§lBank §6--");
			p.sendMessage("§a/bank <Fach>");
			p.sendMessage("§3=> Oeffne ein Fach deiner Bank");
			
			int max = 2;
			if(CubiTechUtil.isPlayerAdmin(p)) {
				max = 9;
			} else if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Ritter")) {
				max = 4;
			} else if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Adeliger")) {
				max = 6;
			}
			
			int num = 1;
			try {
				if(args.length > 0) {
					num = Integer.valueOf(args[0]);
				}
			} catch(Exception ex) {
				return;
			}
			
			if(num > max) {
				p.sendMessage("§cDeine Anzahl an Faechern: §e" + max);
				return;
			}
			
			p.sendMessage("§3Du oeffnest Fach " + num);
			Inventory inv = CubiTechUtil.loadBank(p, num);
			p.openInventory(inv);
		}
	}
}
