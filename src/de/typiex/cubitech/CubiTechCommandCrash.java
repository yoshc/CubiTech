package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandCrash extends CubiTechCommand {

	public CubiTechCommandCrash(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (args.length > 0) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (CubiTechUtil.isPlayerAdmin(p)) {
					Player target = Bukkit.getPlayer(args[0]);
					if (target == null) {
						p.sendMessage("§cDer Spieler §6" + args[0] + " §cist nicht online.");
					} else {
						CubiTechUtil.crashPlayer(target);
					}
				} else {
					p.sendMessage(ChatColor.RED + "Dafuer hast Du leider keine Rechte.");
				}
			}
		} else {
			sender.sendMessage(ChatColor.RED + "Fehler. Du musst einen Spieler angeben.");
		}
	}
}
