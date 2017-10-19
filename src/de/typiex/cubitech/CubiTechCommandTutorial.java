package de.typiex.cubitech;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandTutorial extends CubiTechCommand {

	public CubiTechCommandTutorial(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if(CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Knappe") || CubiTechUtil.isPlayerAdmin(p)) {
			CubiTechUtil.doTutorial(p);
			} else {
				p.sendMessage("§cDu kannst das Tutorial nur einmal machen!");
			}
		}

	}
}
