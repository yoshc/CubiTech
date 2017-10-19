package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandWarn extends CubiTechCommand {

	public CubiTechCommandWarn(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;

			if (CubiTechUtil.isPlayerAdmin(p) || CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Supporter")) {
				Player target = null;
				String reason = null;

				if (args.length == 0) {
					p.sendMessage("§cMit §4/warnen <Spieler> [Grund] §ckannst Du einem Spieler eine Verwarnugn geben.");
					p.sendMessage("§4Achtung: §cEine Verwarnung darf nur vergeben werden, wenn der Spieler gegen eine Regel verstossen hat.");
					p.sendMessage("§cHat ein Spieler §63 Verwarnungen§c, wird er bis zum naechsten Server-Start gebannt.");
					p.sendMessage("§cHat ein Spieler §65 Verwarnungen§c, wird er gebannt.");
					return;
				} else if (args.length == 1) {
					target = Bukkit.getPlayer(args[0]);
					if (target == null) {
						p.sendMessage("§cFehler. Der Spieler §6" + args[0] + " §cist nicht online.");
						return;
					}
				} else if (args.length >= 2) {
					target = Bukkit.getPlayer(args[0]);
					if (target == null) {
						p.sendMessage("§cFehler. Der Spieler §6" + args[0] + " §cist nicht online.");
						return;
					}
					StringBuilder sb = new StringBuilder();
					for (int i = 1; i < args.length; i++) {
						sb.append(args[i]);
						sb.append(" ");
					}
					reason = sb.toString();
				}

				if (target != null) {
					if (reason == null) {
						CubiTechUtil.addWarn(p);
						p.sendMessage("§cDu hast §6" + target.getName() + "§c verwarnt.");
						Bukkit.broadcastMessage("§cDer Spieler §6" + target.getName() + "§c wurde verwarnt.");
						target.sendMessage("§cDu wurdest verwarnt.");
						target.sendMessage("§cDu hast nun §4" + CubiTechUtil.getWarns(target) + "§c Verwarnungen.");
						target.sendMessage("§cHast Du §63 Verwarnungen§c, wirst Du bis zum naechsten Server-Start gebannt.");
						target.sendMessage("§cHast Du §65 Verwarnungen§c, wirst Du gebannt.");
					} else {
						CubiTechUtil.addWarn(p);
						p.sendMessage("§cDu hast §6" + target.getName() + "§c verwarnt. §4Grund: §c" + reason);
						Bukkit.broadcastMessage("§cDer Spieler §6" + target.getName() + "§c wurde verwarnt. §4Grund: §c" + reason);
						target.sendMessage("§cDu hast nun §4" + CubiTechUtil.getWarns(target) + "§c Verwarnungen.");
						target.sendMessage("§cHast Du §63 Verwarnungen§c, wirst Du bis zum naechsten Server-Start gebannt.");
						target.sendMessage("§cHast Du §65 Verwarnungen§c, wirst Du gebannt.");
					}
				}

			} else {
				p.sendMessage("§cDafuer hast Du leider keine Rechte!");
			}
		}
	}
}
