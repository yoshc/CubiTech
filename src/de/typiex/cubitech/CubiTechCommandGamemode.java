package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandGamemode extends CubiTechCommand {

	public CubiTechCommandGamemode(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		Player p = (Player) sender;
		if (CubiTechUtil.isPlayerAdmin(p) || CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Builder")) {
			if (args.length == 0) {
				if (p.getGameMode() == GameMode.CREATIVE) {
					p.setGameMode(GameMode.SURVIVAL);
					sender.sendMessage(ChatColor.GREEN + "Gamemode set to " + ChatColor.AQUA + "Survival" + ChatColor.GREEN + ".");
				} else {
					p.setGameMode(GameMode.CREATIVE);
					sender.sendMessage(ChatColor.GREEN + "Gamemode set to " + ChatColor.AQUA + "Creative" + ChatColor.GREEN + ".");
				}
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("c")) {
					p.setGameMode(GameMode.CREATIVE);
					sender.sendMessage(ChatColor.GREEN + "Gamemode set to " + ChatColor.AQUA + "Creative" + ChatColor.GREEN + ".");
				} else if (args[0].equalsIgnoreCase("s")) {
					p.setGameMode(GameMode.SURVIVAL);
					sender.sendMessage(ChatColor.GREEN + "Gamemode set to " + ChatColor.AQUA + "Survival" + ChatColor.GREEN + ".");
				} else if (args[0].equalsIgnoreCase("a")) {
					p.setGameMode(GameMode.ADVENTURE);
					sender.sendMessage(ChatColor.GREEN + "Gamemode set to " + ChatColor.AQUA + "Adventure" + ChatColor.GREEN + ".");
				}
			} else if (args.length == 2) {
				if(CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Builder")) {
					p.sendMessage(ChatColor.RED + "Du darfst nur deinen eigenen GameMode ändern!");
					return;
				}
				Player p2 = Bukkit.getPlayerExact(args[1]);
				if (p2 == null) {
					sender.sendMessage(ChatColor.RED + "Fehler. Der Spieler " + ChatColor.GOLD + args[1] + ChatColor.RED + " ist nicht online.");
				} else {
					if (args[0].equalsIgnoreCase("c")) {
						p2.setGameMode(GameMode.CREATIVE);
						sender.sendMessage(ChatColor.GOLD + p2.getName() + ChatColor.GREEN + "'s Gamemode was set to " + ChatColor.AQUA + "Creative" + ChatColor.GREEN + ".");
						p2.sendMessage(ChatColor.GREEN + "Your gamemode was set to " + ChatColor.AQUA + "Creative" + ChatColor.GREEN + " by " + ChatColor.GOLD + p.getName() + ChatColor.GREEN + ".");
					} else if (args[0].equalsIgnoreCase("s")) {
						p2.setGameMode(GameMode.SURVIVAL);
						sender.sendMessage(ChatColor.GOLD + p2.getName() + ChatColor.GREEN + "'s Gamemode was set to " + ChatColor.AQUA + "Survival" + ChatColor.GREEN + " by " + ChatColor.GOLD + p.getName() + ChatColor.GREEN + ".");
						p2.sendMessage(ChatColor.GREEN + "Your gamemode was set to " + ChatColor.AQUA + "Survival" + ChatColor.GREEN + " by " + ChatColor.GOLD + p.getName() + ChatColor.GREEN + ".");
					} else if (args[0].equalsIgnoreCase("a")) {
						p2.setGameMode(GameMode.ADVENTURE);
						sender.sendMessage(ChatColor.GOLD + p2.getName() + ChatColor.GREEN + "'s Gamemode was set to " + ChatColor.AQUA + "Adventure" + ChatColor.GREEN + " by " + ChatColor.GOLD + p.getName() + ChatColor.GREEN + ".");
						p2.sendMessage(ChatColor.GREEN + "Your gamemode was set to " + ChatColor.AQUA + "Adventure" + ChatColor.GREEN + " by " + ChatColor.GOLD + p.getName() + ChatColor.GREEN + ".");
					}
				}
			}
		} else {
			p.sendMessage(ChatColor.RED + "Dafuer hast Du leider keine Rechte.");
		}
	}
}
