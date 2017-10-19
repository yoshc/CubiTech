package de.typiex.cubitech;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandCoupon extends CubiTechCommand {

	public CubiTechCommandCoupon(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {

		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (CubiTechUtil.isPlayerAdmin(p)) {
				p.getInventory().addItem(CubiTechUtil.getItemCubiCoupon());
			}
		}
	}
}
