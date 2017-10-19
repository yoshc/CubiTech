package de.typiex.cubitech;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandLevels extends CubiTechCommand {

	public CubiTechCommandLevels(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			
			int lvl = CubiTechUtil.getPlayerLevel(p);
			
			p.sendMessage(ChatColor.DARK_GREEN + "Level Belohnugen:");
			p.sendMessage(ChatColor.YELLOW + "Lvl. 1: " + (lvl >= 1 ? ChatColor.GREEN : ChatColor.RED) + "Du kannst nun ein §ePferd ohne Ruestung" + ChatColor.RESET + (lvl >= 1 ? ChatColor.GREEN : ChatColor.RED) + " als Reittier herbeirufen");
			p.sendMessage(ChatColor.YELLOW + "Lvl. 5: " + (lvl >= 5 ? ChatColor.GREEN : ChatColor.RED) + "Du kannst Dich nun zum Spawn teleportieren ( /spawn )");
			p.sendMessage(ChatColor.YELLOW + "Lvl. 10: " + (lvl >= 10 ? ChatColor.GREEN : ChatColor.RED) + "Du kannst nun Ruestung der Stufe " + ChatColor.GREEN + ChatColor.ITALIC + "Selten" + ChatColor.RESET + (lvl >= 10 ? ChatColor.GREEN : ChatColor.RED) + " verwenden");
			p.sendMessage(ChatColor.YELLOW + "Lvl. 20: " + (lvl >= 20 ? ChatColor.GREEN : ChatColor.RED) + "Du kannst nun Ruestung der Stufe " + ChatColor.DARK_GREEN + ChatColor.ITALIC + "Sehr Selten" + ChatColor.RESET + (lvl >= 20 ? ChatColor.GREEN : ChatColor.RED) + " verwenden");
			p.sendMessage(ChatColor.YELLOW + "Lvl. 20: " + (lvl >= 20 ? ChatColor.GREEN : ChatColor.RED) + "Du kannst nun ein §7Pferd mit Eisenruestung" + ChatColor.RESET + (lvl >= 20 ? ChatColor.GREEN : ChatColor.RED) + " als Reittier herbeirufen");
			p.sendMessage(ChatColor.YELLOW + "Lvl. 25: " + (lvl >= 25 ? ChatColor.GREEN : ChatColor.RED) + "Du kannst nun Kopfgeld auf Spieler aussetzen ( /kopfgeld )");
			p.sendMessage(ChatColor.YELLOW + "Lvl. 25: " + (lvl >= 25 ? ChatColor.GREEN : ChatColor.RED) + "Du kannst nun ein §7Schnelles Pferd mit Eisenruestung" + ChatColor.RESET + (lvl >= 25 ? ChatColor.GREEN : ChatColor.RED) + " als Reittier herbeirufen");
			p.sendMessage(ChatColor.YELLOW + "Lvl. 30: " + (lvl >= 30 ? ChatColor.GREEN : ChatColor.RED) + "Du erhaeltst 1/2 Herz weniger Schaden");
			p.sendMessage(ChatColor.YELLOW + "Lvl. 30: " + (lvl >= 30 ? ChatColor.GREEN : ChatColor.RED) + "Du kannst nun ein §6Pferd mit Goldruestung" + ChatColor.RESET + (lvl >= 30 ? ChatColor.GREEN : ChatColor.RED) + " als Reittier herbeirufen");
			p.sendMessage(ChatColor.YELLOW + "Lvl. 40: " + (lvl >= 40 ? ChatColor.GREEN : ChatColor.RED) + "Du kannst nun Ruestung der Stufe " + ChatColor.DARK_AQUA + ChatColor.ITALIC + "Extrem" + ChatColor.RESET + (lvl >= 40 ? ChatColor.GREEN : ChatColor.RED) + " verwenden");
			p.sendMessage(ChatColor.YELLOW + "Lvl. 40: " + (lvl >= 40 ? ChatColor.GREEN : ChatColor.RED) + "Du kannst nun ein §6Schnelles Pferd mit Goldruestung" + ChatColor.RESET + (lvl >= 40 ? ChatColor.GREEN : ChatColor.RED) + " als Reittier herbeirufen");
			p.sendMessage(ChatColor.YELLOW + "Lvl. 50: " + (lvl >= 50 ? ChatColor.GREEN : ChatColor.RED) + "Du erhaeltst 1 Herz weniger Schaden");
			p.sendMessage(ChatColor.YELLOW + "Lvl. 50: " + (lvl >= 50 ? ChatColor.GREEN : ChatColor.RED) + "Du kannst nun Ruestung der Stufe " + ChatColor.DARK_PURPLE + ChatColor.ITALIC + "Heroisch" + ChatColor.RESET + (lvl >= 50 ? ChatColor.GREEN : ChatColor.RED) + " verwenden");
			p.sendMessage(ChatColor.YELLOW + "Lvl. 50: " + (lvl >= 50 ? ChatColor.GREEN : ChatColor.RED) + "Du kannst nun ein §bPferd mit Diamantruestung" + ChatColor.RESET + (lvl >= 50 ? ChatColor.GREEN : ChatColor.RED) + " als Reittier herbeirufen");
			p.sendMessage(ChatColor.YELLOW + "Lvl. 60: " + (lvl >= 60 ? ChatColor.GREEN : ChatColor.RED) + "Deine Mana-Regeneration erhoeht sich");
			p.sendMessage(ChatColor.YELLOW + "Lvl. 60: " + (lvl >= 60 ? ChatColor.GREEN : ChatColor.RED) + "Du kannst nun Ruestung der Stufe " + ChatColor.RED + ChatColor.ITALIC + "Legendaer" + ChatColor.RESET + (lvl >= 60 ? ChatColor.GREEN : ChatColor.RED) + " verwenden");
			p.sendMessage(ChatColor.YELLOW + "Lvl. 60: " + (lvl >= 60 ? ChatColor.GREEN : ChatColor.RED) + "Du kannst nun ein §bSchnelles Pferd mit Diamantruestung" + ChatColor.RESET + (lvl >= 60 ? ChatColor.GREEN : ChatColor.RED) + " als Reittier herbeirufen");
		}
	}
}
