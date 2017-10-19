package de.typiex.cubitech;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.SkullMeta;

public class CubiTechCommandSetskull extends CubiTechCommand {

	public CubiTechCommandSetskull(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {

		if (sender instanceof Player) {

			Player p = (Player) sender;
			if (CubiTechUtil.isPlayerAdmin(p)) {
				String name = null;
				if (args.length == 0) {
					name = p.getName();
				} else {
					name = args[0];
				}

				if (p.getItemInHand().getType() == Material.SKULL_ITEM) {
					SkullMeta s = (SkullMeta)p.getItemInHand().getItemMeta();
					s.setOwner(name);
					p.getItemInHand().setItemMeta(s);
					p.sendMessage(ChatColor.GREEN + "Der Skull wurde auf " + ChatColor.GOLD + name + ChatColor.GREEN + " gesetzt.");

				} else {
					Block b = p.getTargetBlock(null, 20);
					if (b == null) {
						p.sendMessage(ChatColor.RED + "Fehler. Du musst einen Block ansehen oder einen Skull in der Hand haben.");
					} else {
						if (b.getType() != Material.SKULL) {
							p.sendMessage(ChatColor.RED + "Fehler. Du musst einen Skull Block ansehen oder einen Skull in der Hand haben.");
						} else {
							Skull s = (Skull) b.getState();
							s.setOwner(name);
							s.update();
							p.sendMessage(ChatColor.GREEN + "Der Skull wurde auf " + ChatColor.GOLD + name + ChatColor.GREEN + " gesetzt.");
						}
					}
				}
			}
		}

	}
}
