package de.typiex.cubitech;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandCode extends CubiTechCommand {

	public CubiTechCommandCode(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (args.length == 0) {
				p.sendMessage("§6Mit §3/code <Code> §6kannst Du einen Code einloesen.");
				p.sendMessage("§eMit Codes erhaelt man Klassen wie §2Ritter §eund §6Adeliger§e.");
			} else if (args.length >= 1) {
				if (args[0].equalsIgnoreCase("generieren") || args[0].equalsIgnoreCase("generate")) {
					if (args.length >= 2) {
						if (CubiTechUtil.isPlayerAdmin(p)) {
							String sClass = args[1];
							if (sClass.equalsIgnoreCase("Ritter") || sClass.equalsIgnoreCase("Adeliger")) {
								String code = CubiTechUtil.generateCode();
								CubiTechUtil.addCode(code, sClass);
								p.sendMessage(ChatColor.GREEN + "Du hast den Code §e" + code + " §afuer §6" + sClass + " §ageneriert.");
							} else {
								p.sendMessage(ChatColor.RED + "Fehler. Es können nur Codes fuer die Klassen Ritter und Adeliger generiert werden.");
							}
						} else {
							p.sendMessage(ChatColor.RED + "Dafuer hast Du leider keine Rechte.");
						}
					} else {
						p.sendMessage(ChatColor.RED + "Fehler, Du musst einen Code und eine Klasse angeben.");
					}
				} else if (args[0].equalsIgnoreCase("list")) {
					if (CubiTechUtil.isPlayerAdmin(p)) {
						HashMap<String, String> codes = CubiTechUtil.getCodes();
						if (codes.size() == 0) {
							p.sendMessage(ChatColor.YELLOW + "Derzeit gibt es keine Codes.");
							p.sendMessage(ChatColor.YELLOW + "Du kannst Codes mit §6/code generate <Ritter|Adeliger> §egenerieren.");
						} else {
							p.sendMessage(ChatColor.YELLOW + "Alle Codes:");
							for (String s : codes.keySet()) {
								p.sendMessage(ChatColor.GOLD + s + ChatColor.YELLOW + " - " + ChatColor.DARK_AQUA + codes.get(s));
							}
							p.sendMessage(ChatColor.YELLOW + "Du kannst Codes mit §6/code generate <Ritter|Adeliger> §egenerieren.");
						}
					} else {
						p.sendMessage(ChatColor.RED + "Dafuer hast Du leider keine Rechte.");
					}
				} else {
					String code = args[0];
					String s = CubiTechUtil.getClassForCode(code);
					if (s == null) {
						p.sendMessage("§cDer Code §4" + code + " §cist nicht gueltig!");
					} else {
						if (s.equalsIgnoreCase("Ritter")) {
							Bukkit.broadcastMessage("§a" + p.getName() + " §ehat einen Code fuer §2Ritter §eeingeloest.");
							p.sendMessage("§aDu hast einen Code fuer §2Ritter §aeingeloest!");
							CubiTechUtil.playerClasses.put(p, "Ritter");
							CubiTechUtil.giveRitterItems(p);
							CubiTechUtil.removeCode(code);
						} else if (s.equalsIgnoreCase("Adeliger")) {
							Bukkit.broadcastMessage("§a" + p.getName() + " §ehat einen Code fuer §6Adeliger §eeingeloest.");
							p.sendMessage("§aDu hast einen Code fuer §6Adeliger §aeingeloest!");
							CubiTechUtil.playerClasses.put(p, "Adeliger");
							CubiTechUtil.giveAdeligerItems(p);
							CubiTechUtil.removeCode(code);
						} else {
							p.sendMessage("§cDiesem Code ist keine gueltige Klasse zugewiesen!");
						}
					}
				}
			}
		}
	}
}
