package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandList extends CubiTechCommand {

	public CubiTechCommandList(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {

		int countGrass = 0;
		int countSand = 0;
		int countSnow = 0;

		StringBuilder playersBuilder = new StringBuilder();

		for (Player q : Bukkit.getOnlinePlayers()) {

			ChatColor color = ChatColor.GRAY;
			if (CubiTechUtil.isPlayerAdmin(q)) {
				color = ChatColor.DARK_RED;
			} else if (CubiTechUtil.getPlayerClass(q).equalsIgnoreCase("Ritter")) {
				color = ChatColor.DARK_GREEN;
			} else if (CubiTechUtil.getPlayerClass(q).equalsIgnoreCase("Adeliger")) {
				color = ChatColor.GOLD;
			} else if (CubiTechUtil.getPlayerClass(q).equalsIgnoreCase("YouTuber")) {
				color = ChatColor.DARK_AQUA;
			} else if (CubiTechUtil.getPlayerClass(q).equalsIgnoreCase("Supporter")) {
				color = ChatColor.RED;
			}
			
			playersBuilder.append(color + (CubiTechUtil.playersAfk.contains(q.getName()) ? ChatColor.STRIKETHROUGH + q.getName() : q.getName()) + ChatColor.RESET + ChatColor.WHITE + ", ");

			if (CubiTechUtil.getPlayerFaction(q).equalsIgnoreCase("grass"))
				countGrass++;
			else if (CubiTechUtil.getPlayerFaction(q).equalsIgnoreCase("sand"))
				countSand++;
			else if (CubiTechUtil.getPlayerFaction(q).equalsIgnoreCase("snow"))
				countSnow++;
		}

		playersBuilder.replace(playersBuilder.lastIndexOf(", "), playersBuilder.lastIndexOf("") + 1, "");
		// replace last ','

		String players = playersBuilder.toString();
		
		int countAdmins = 0;
		
		for (Player q : Bukkit.getOnlinePlayers()) {
			if (CubiTechUtil.isPlayerAdmin(q)) {
				countAdmins++;
			}
		}
		
		sender.sendMessage("§6-- §eOnline Liste §6--");
		sender.sendMessage(ChatColor.GREEN + "Solaner Online: " + ChatColor.YELLOW + countGrass);
		sender.sendMessage(ChatColor.GOLD + "Pyrer Online: " + ChatColor.YELLOW + countSand);
		sender.sendMessage(ChatColor.DARK_AQUA + "Arctiker Online: " + ChatColor.YELLOW + countSnow);
		sender.sendMessage(ChatColor.RED + "Admins Online: " + ChatColor.DARK_RED + countAdmins);
		for (Player q : Bukkit.getOnlinePlayers()) {
			if (CubiTechUtil.isPlayerAdmin(q)) {
				sender.sendMessage(ChatColor.DARK_RED + "- " + ChatColor.RED + (CubiTechUtil.playersAfk.contains(q.getName()) ? ChatColor.STRIKETHROUGH + q.getName() : q.getName()));
			}
		}
		
		sender.sendMessage(ChatColor.YELLOW + "Spieler Online: " + ChatColor.GOLD + Bukkit.getOnlinePlayers().length);
		sender.sendMessage(players);

	}
}
