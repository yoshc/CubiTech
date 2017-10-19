package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandAsk extends CubiTechCommand {

	public CubiTechCommandAsk(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;

			if (args.length == 0) {

				int adminsOnline = 0;

				for (Player q : Bukkit.getOnlinePlayers()) {
					if (CubiTechUtil.isPlayerAdmin(q) || CubiTechUtil.getPlayerClass(q).equalsIgnoreCase("Supporter")) {
						adminsOnline++;
					}
				}
				p.sendMessage("§c-- §6§lFrage §c--");
				p.sendMessage("§aMit §6/frage <Frage> §akannst Du eine Frage an alle online Teammitglieder stellen.");
				if (adminsOnline == 0) {
					p.sendMessage("§cEs sind derzeit leider keine Teammitglieder online.");
				} else {
					p.sendMessage("§aEs sind derzeit §e" + adminsOnline + " Teammitglieder §aonline.");
				}
			} else {
				StringBuilder sb = new StringBuilder();
				for(int i = 0; i < args.length; i++) {
					sb.append(args[i]);
					sb.append(" ");
				}
				
				String s = sb.toString();
				
				for (Player q : Bukkit.getOnlinePlayers()) {
					if (CubiTechUtil.isPlayerAdmin(q) || CubiTechUtil.getPlayerClass(q).equalsIgnoreCase("Supporter")) {
						q.sendMessage("§4[FRAGE] §e" + p.getName() + " : §c" + s);
					}
				}
				p.sendMessage(ChatColor.GREEN + "Du hast deine Frage an alle online Teammitglieder gestellt.");
				
			}

		}

	}
}
