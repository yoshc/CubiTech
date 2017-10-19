package de.typiex.cubitech;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CubiTechCommandRepair extends CubiTechCommand {

	public CubiTechCommandRepair(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (CubiTechUtil.isPlayerAdmin(p) || CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Adeliger")) {
				if (args.length == 0) {
					if (p.getItemInHand() != null && p.getItemInHand().getType() != Material.AIR) {
						p.getItemInHand().setDurability((short) 0);
						p.sendMessage(ChatColor.GREEN + "Du hast das Item in deiner Hand repariert");
					} else {
						p.sendMessage(ChatColor.RED + "Fehler. Du musst ein Item in der Hand haben.");
					}
				} else {
					if(args[0].equalsIgnoreCase("all") || args[0].equalsIgnoreCase("alles")) {
						ItemStack[] newInv = p.getInventory().getContents();
						for(ItemStack i : newInv) {
							if (i != null && i.getType() != Material.AIR) {
								i.setDurability((short) 0);
							}
						}
						p.getInventory().setContents(newInv);
						
						ItemStack[] newInvArmor = p.getInventory().getArmorContents();
						for(ItemStack i : newInvArmor) {
							if (i != null && i.getType() != Material.AIR) {
								i.setDurability((short) 0);
							}
						}
						p.getInventory().setArmorContents(newInvArmor);
						
					}
					p.sendMessage(ChatColor.GREEN + "Du hast alles in deinem Inventar repariert.");
				}
			}
		}
	}
}
