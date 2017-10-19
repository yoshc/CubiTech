package de.typiex.cubitech;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandHilfe extends CubiTechCommand {

	public CubiTechCommandHilfe(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;

			if (args.length == 0) {
				p.sendMessage(ChatColor.AQUA + "Hilfe:");
				p.sendMessage(ChatColor.DARK_AQUA + "/hilfe generell " + ChatColor.GOLD + "Zeige generelle Informationen an");
				p.sendMessage(ChatColor.DARK_AQUA + "/hilfe befehle " + ChatColor.GOLD + "Zeige alle Befehle an");
				p.sendMessage(ChatColor.DARK_AQUA + "/hilfe voelker " + ChatColor.GOLD + "Zeige Informationen zu allen Voelkern an");
			} else {
				if (args[0].equalsIgnoreCase("generell")) {
					p.sendMessage(ChatColor.AQUA + "Generelle:");
					p.sendMessage(ChatColor.DARK_AQUA + "- " + ChatColor.YELLOW + "Dein §6Mana§e kannst Du in deiner EP-Leiste sehen.");
					p.sendMessage(ChatColor.DARK_AQUA + "- " + ChatColor.YELLOW
							+ "Es verringert sich zB beim Verwenden von Fertigkeiten und regeneriert sich die ganze Zeit.");
					p.sendMessage(ChatColor.DARK_AQUA + "- " + ChatColor.YELLOW
							+ "Deine §6Klasse§e entscheidet, welche Ruestung/Waffen Du verwenden kannst und welche Fertigkeiten Du besitzt.");
					p.sendMessage(ChatColor.DARK_AQUA + "- " + ChatColor.YELLOW
							+ "Du erhaeltst §6EP(Erfahrungspunkte)§e, indem Du zB Monster toetest oder Quests(Auftraege) erledigst.");
					p.sendMessage(ChatColor.DARK_AQUA + "- " + ChatColor.YELLOW + "Sobald Du §a1000 EP §eerhaeltst, steigt eine Level um eins.");
					p.sendMessage(ChatColor.DARK_AQUA + "- " + ChatColor.YELLOW + "Je hoeher dein §6Level §eist, desto besser wirst Du.");
					p.sendMessage(ChatColor.DARK_AQUA + "- " + ChatColor.YELLOW
							+ "Gib §a/levels §eein, um zu sehen, mit welchem Level Du etwas freischaltest.");
					p.sendMessage(ChatColor.DARK_AQUA + "- " + ChatColor.YELLOW
							+ "Die Waehrung auf CubiTech ist §6Cubi§e. Damit kannst Du dir Sachen kaufen und mit Spielern handeln.");
				}
				// TODO: Finish
				else if (args[0].equalsIgnoreCase("befehle")) {
					p.sendMessage(ChatColor.AQUA + "Befehle:");
					p.sendMessage(ChatColor.DARK_GREEN + "/spawn " + ChatColor.GREEN + " Gelange zu deiner Stadt");
					p.sendMessage(ChatColor.DARK_GREEN + "/list " + ChatColor.GREEN + " Siehe, wer online ist");
					p.sendMessage(ChatColor.DARK_GREEN + "/quest " + ChatColor.GREEN + " Waehle eine Quest");
					p.sendMessage(ChatColor.DARK_GREEN + "/gruppe " + ChatColor.GREEN + " Zeige Informationen zu Gruppen");
					p.sendMessage(ChatColor.DARK_GREEN + "/shop " + ChatColor.GREEN + " Oeffne den Shop");
					p.sendMessage(ChatColor.DARK_GREEN + "/lvls " + ChatColor.GREEN + " Zeige die Level-Errungenschaften an");
					p.sendMessage(ChatColor.DARK_GREEN + "/m <Spieler> <Nachricht> " + ChatColor.GREEN + " Schreibe einenm Spieler eine Nachricht");
					p.sendMessage(ChatColor.DARK_GREEN + "/paket <Spieler> " + ChatColor.GREEN + " Sende einem Spieler ein");
					p.sendMessage(ChatColor.DARK_GREEN + "/verkaufen <Preis> " + ChatColor.GREEN + " Biete das Item in deiner Hand zum Verkauf an");
					p.sendMessage(ChatColor.DARK_GREEN + "/kaufen <Spieler> " + ChatColor.GREEN + " Kaufe ein Item von einem Spieler");
					p.sendMessage(ChatColor.DARK_GREEN + "/1vs1 " + ChatColor.GREEN + " Zeige Informationen zum 1gegen1");
					p.sendMessage(ChatColor.DARK_GREEN + "/versteigern " + ChatColor.GREEN + " Versteigere einen Gegenstand");
					p.sendMessage(ChatColor.DARK_GREEN + "/bieten " + ChatColor.GREEN + " Biete fuer einen versteigerten Gegenstand");
				} else if (args[0].equalsIgnoreCase("voelker")) {
					p.sendMessage(ChatColor.AQUA + "Voelker:");
					p.sendMessage(ChatColor.GREEN + "Solaner");
					p.sendMessage(ChatColor.GOLD + "Pyrer");
					p.sendMessage(ChatColor.DARK_AQUA + "Arctiker");
					p.sendMessage(ChatColor.YELLOW + "--");
					p.sendMessage(ChatColor.YELLOW + "Du kannst nur Spieler angreifen, die nicht dem selben Volk angehoeren wie Du.");
					p.sendMessage(ChatColor.YELLOW + "Du kannst das Volk eines Spielers an der §6Farbe seines Levels §eerkennen.");
				}

			}

		}
	}
}
