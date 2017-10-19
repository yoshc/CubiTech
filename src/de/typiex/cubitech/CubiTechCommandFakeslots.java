package de.typiex.cubitech;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandFakeslots extends CubiTechCommand {

	public CubiTechCommandFakeslots(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (CubiTechUtil.isPlayerAdmin(p)) {
				if (args.length > 0) {
					if (args[0].equalsIgnoreCase("on")) {
						CubiTechUtil.fakeSlots = true;
						p.sendMessage("§aFakeSlots sind nun auf §2an§a.");
					} else if (args[0].equalsIgnoreCase("off")) {
						CubiTechUtil.fakeSlots = false;
						p.sendMessage("§aFakeSlots sind nun auf §2aus§a.");
					} else {
						try {
							int slots = Integer.valueOf(args[0]);
							CubiTechUtil.fakeSlotsNum = slots;
							p.sendMessage("§aFakeSlots sind nun auf §2" + slots + "§a.");
						} catch (Exception ex) {
							p.sendMessage("§cFehler. /fakeslots <on|off|number>");
						}
					}
				} else {
					p.sendMessage("§cFehler. /fakeslots <on|off|number>");
				}
			} else {
				p.sendMessage(ChatColor.RED + "Dafuer hast Du leider keine Rechte.");
			}
		}
	}
}
