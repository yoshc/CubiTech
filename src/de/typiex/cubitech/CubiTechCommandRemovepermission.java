package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandRemovepermission extends CubiTechCommand {

	public CubiTechCommandRemovepermission(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (CubiTechUtil.isPlayerAdmin(p)) {
				if (args.length >= 2) {
					Player target = Bukkit.getPlayer(args[0]);
					if(target == null) {
						p.sendMessage(ChatColor.RED + "Fehler. Der Spieler " + ChatColor.GOLD + args[0] + ChatColor.RED + " ist nicht online.");
					} else {
						target.addAttachment(plugin, args[1], false);
						p.sendMessage(ChatColor.GREEN + "Du hast dem Spieler " + ChatColor.GOLD + target.getName() + ChatColor.GREEN + " die Permission " + ChatColor.YELLOW + args[1] + ChatColor.GREEN + " genommen.");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Fehler. Du musst einen Spieler und eine Permission angeben.");
				}
			} else {
				p.sendMessage(ChatColor.RED + "Dafuer hast Du leider keine Rechte!");
			}
		}
	}
}
