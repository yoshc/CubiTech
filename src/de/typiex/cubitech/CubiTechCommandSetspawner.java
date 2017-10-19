package de.typiex.cubitech;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandSetspawner extends CubiTechCommand {

	public CubiTechCommandSetspawner(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (CubiTechUtil.isPlayerAdmin(p)) {
				if(args.length == 0) {
					p.sendMessage("§cFehler. Du musst einen Monster-Typ angeben!");
					return;
				}
				Block b = p.getTargetBlock(null, 5);
				if(b.getType() == Material.MOB_SPAWNER) {
					CreatureSpawner spawner = (CreatureSpawner) b.getState();
					spawner.setCreatureTypeByName(args[0]);
					p.sendMessage("§aDer Spawner wurde auf §2" + spawner.getCreatureTypeName() + " §agesetzt.");
				} else {
					p.sendMessage("§cFehler. Du musst einen Monster-Spawner ansehen.");
				}
			}
		}
	}
}
