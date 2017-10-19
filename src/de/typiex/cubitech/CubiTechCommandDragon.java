package de.typiex.cubitech;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_6_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_6_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;

public class CubiTechCommandDragon extends CubiTechCommand {

	public CubiTechCommandDragon(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {

		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (CubiTechUtil.isPlayerAdmin(p)) {
				// Dragon
				if (p.getItemInHand().getType() != Material.AIR) {
					p.sendMessage(ChatColor.RED + "Du darfst nichts in der Hand halten, waehrend Du dein Reittier herbeirufst.");
					return;
				}

				net.minecraft.server.v1_6_R2.World mcWorld = ((CraftWorld) p.getWorld()).getHandle();
				CubiTechDragon td = new CubiTechDragon(mcWorld, p);
				Location location = p.getLocation();
				td.setPosition(location.getX(), location.getY(), location.getZ());
				mcWorld.addEntity(td, SpawnReason.CUSTOM);
				td.setPosition(location.getX(), location.getY(), location.getZ());
				td.myX = location.getX();
				td.myY = location.getY();
				td.myZ = location.getZ();
				
				((CraftPlayer) p).getHandle().mount(td);

				p.setItemInHand(new ItemStack(Material.CARROT_STICK, 1));
				CubiTechUtil.playersMountedDragon.put(p, td);
			} else {
				p.sendMessage(ChatColor.RED + "Fehler. Dafuer hast Du leider keine Rechte!");
			}
		}

	}
}
