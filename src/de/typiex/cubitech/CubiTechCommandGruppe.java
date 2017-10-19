package de.typiex.cubitech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.typiex.cubitech.CubiTechUtil.Party;

public class CubiTechCommandGruppe extends CubiTechCommand {

	public CubiTechCommandGruppe(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player p = (Player) sender;

				Party party = CubiTechUtil.getPartyByPlayer(p);

				if (party == null) {
					p.sendMessage(ChatColor.AQUA + "Du bist derzeit in keiner Gruppe.");
					p.sendMessage(ChatColor.AQUA + "Schreibe " + ChatColor.GOLD + "/gruppe hilfe" + ChatColor.AQUA + " , um Hilfe zu Gruppen zu bekommen.");
				} else {
					p.sendMessage(ChatColor.DARK_AQUA + "--- Deine Gruppe ---");
					p.sendMessage(ChatColor.AQUA + "Anfuehrer: " + ChatColor.GOLD + party.getLeader().getName());
					p.sendMessage(ChatColor.AQUA + "Mitglieder: " + ChatColor.GOLD + party.getPlayers().size());
					p.sendMessage(ChatColor.AQUA + "Teilen: " + ChatColor.GOLD + (party.getSplit() ? "An" : "Aus"));
					p.sendMessage(ChatColor.AQUA + "Mitglieder deiner Gruppe:");
					int count = 1;
					for (Player q : party.getPlayers()) {
						p.sendMessage(ChatColor.GOLD + "  " + count + ChatColor.AQUA + " " + q.getName());
						count++;
					}
					p.sendMessage(ChatColor.DARK_AQUA + "--- Deine Gruppe ---");
				}
			}
		} else if (args.length == 1) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (args[0].equalsIgnoreCase("hilfe") || args[0].equalsIgnoreCase("help")) {
					sender.sendMessage(ChatColor.DARK_AQUA + "- Gruppen Hilfe -");
					sender.sendMessage(ChatColor.AQUA + "=> " + ChatColor.GOLD + "/gruppe" + ChatColor.AQUA + " - Zeige Informationen deiner Gruppe an.");
					sender.sendMessage(ChatColor.AQUA + "=> " + ChatColor.GOLD + "/gruppe erstellen" + ChatColor.AQUA + " - Erstelle eine Gruppe.");
					sender.sendMessage(ChatColor.AQUA + "=> " + ChatColor.GOLD + "/gruppe einladen <Spieler>" + ChatColor.AQUA + " - Lade einen Spieler in deine Gruppe ein.");
					sender.sendMessage(ChatColor.AQUA + "=> " + ChatColor.GOLD + "/gruppe rauswerfen <Spieler>" + ChatColor.AQUA + " - Wirf einen Spieler aus deiner Gruppe.");
					sender.sendMessage(ChatColor.AQUA + "=> " + ChatColor.GOLD + "/gruppe verlassen" + ChatColor.AQUA + " - Verlasse deine Gruppe.");
					sender.sendMessage(ChatColor.AQUA + "=> " + ChatColor.GOLD + "/gruppe teilen" + ChatColor.AQUA + " - Schalte Teilen von EP und Geld in deiner Gruppe an/aus.");
					sender.sendMessage(ChatColor.AQUA + "=> " + ChatColor.GOLD + "%Nachricht" + ChatColor.AQUA + " - Schreibe eine Nachricht an deine Gruppenmitglieder.");
					sender.sendMessage(ChatColor.AQUA + "=> " + ChatColor.AQUA + "Anstatt " + ChatColor.GOLD + "/gruppe" + ChatColor.AQUA + " kann auch " + ChatColor.GOLD + "/g" + ChatColor.AQUA + " verwendet werden.");
					sender.sendMessage(ChatColor.DARK_AQUA + "- Gruppen Hilfe -");
				} else if (args[0].equalsIgnoreCase("erstellen") || args[0].equalsIgnoreCase("create")) {
					Party party = CubiTechUtil.getPartyByPlayer(p);

					if (party != null) {
						p.sendMessage(ChatColor.AQUA + "Du bist bereits in einer Gruppe.");
						p.sendMessage(ChatColor.AQUA + "Schreibe " + ChatColor.GOLD + "/gruppe hilfe" + ChatColor.AQUA + " , um Hilfe zu Gruppen zu bekommen.");
					} else {
						CubiTechUtil.playerParties.add(new Party(p));
						p.sendMessage(ChatColor.AQUA + "Du hast eine Gruppe erstellt!.");
					}
				} else if (args[0].equalsIgnoreCase("verlassen") || args[0].equalsIgnoreCase("leave")) {
					Party party = CubiTechUtil.getPartyByPlayer(p);
					if (party == null) {
						p.sendMessage(ChatColor.AQUA + "Du bist in keiner Gruppe.");
					} else {
						if (party.getLeader().getName().equals(p.getName())) {
							for (Player q : party.getPlayers()) {
								q.sendMessage(ChatColor.AQUA + "Deine Gruppe wurde aufgeloest, da der Anfuehrer die Gruppe verlassen hat.");
							}
							party.getPlayers().clear();
							CubiTechUtil.playerParties.remove(party);
						} else {
							party.getPlayers().remove(p);
							for (Player q : party.getPlayers()) {
								q.sendMessage(ChatColor.AQUA + "Der Spieler " + ChatColor.GOLD + p.getName() + ChatColor.AQUA + " hat die Gruppe verlassen");
							}
							p.sendMessage(ChatColor.AQUA + "Du hast die Gruppe von " + ChatColor.GOLD + party.getLeader().getName() + ChatColor.AQUA + " verlassen.");
						}
					}
				} else if (args[0].equalsIgnoreCase("annehmen") || args[0].equalsIgnoreCase("accept")) {
					
					Party party = CubiTechUtil.partyInvites.get(p);
					if(party == null) {
						p.sendMessage(ChatColor.AQUA + "Du wurdest in keine Gruppe eingeladen.");
						return;
					}
					
					for (Player q : party.getPlayers()) {
						q.sendMessage(ChatColor.AQUA + "Der Spieler " + ChatColor.GOLD + p.getName() + ChatColor.AQUA + " hat die Gruppe betreten.");
					}
					party.addPlayer(p);
					p.sendMessage(ChatColor.AQUA + "Du bist der Gruppe von " + ChatColor.GOLD + party.getLeader().getName() + ChatColor.AQUA + " beigetreten.");
					
					
					
				} else if (args[0].equalsIgnoreCase("teilen") || args[0].equalsIgnoreCase("split")) {
					Party party = CubiTechUtil.getPartyByPlayer(p);
					if (party == null) {
						p.sendMessage(ChatColor.AQUA + "Du bist derzeit in keiner Gruppe.");
					} else {
						if (!party.getLeader().getName().equals(p.getName())) {
							p.sendMessage(ChatColor.AQUA + "Du bist nicht Anfuehrer der Gruppe.");
						} else {

							// toggle split
							if (party.getSplit()) {
								party.setSplit(false);
								p.sendMessage(ChatColor.AQUA + "Das Teilen in deiner Gruppe ist nun deaktiviert.");
							} else {
								party.setSplit(true);
								p.sendMessage(ChatColor.AQUA + "Das Teilen in deiner Gruppe ist nun aktiviert.");
							}

						}
					}

				} else if (args[0].equalsIgnoreCase("listall") || args[0].equalsIgnoreCase("listealle")) {
					if (CubiTechUtil.isPlayerAdmin(p)) {
						if (CubiTechUtil.playerParties.size() == 0) {
							p.sendMessage(ChatColor.AQUA + "Derzeit gibt es keine Gruppen.");
						} else {
							int partyCount = 1;
							for (Party party : CubiTechUtil.playerParties) {
								p.sendMessage(ChatColor.AQUA + "Gruppe " + ChatColor.BLUE + partyCount + ChatColor.AQUA + " :");
								int playerCount = 1;
								for (Player players : party.getPlayers()) {
									p.sendMessage(ChatColor.GOLD + "" + playerCount + ChatColor.AQUA + " " + players.getName());
									playerCount++;
								}
								partyCount++;
							}
						}
					} else {
						p.sendMessage(ChatColor.RED + "Dafuer hast Du leider keine Rechte!");
					}
				} else {
					sender.sendMessage(ChatColor.AQUA + "Schreibe " + ChatColor.GOLD + "/gruppe hilfe" + ChatColor.AQUA + " , um Hilfe zu Gruppen zu bekommen.");
				}
			}
		} else if (args.length == 2) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (args[0].equalsIgnoreCase("einladen") || args[0].equalsIgnoreCase("invite")) {

					Player target = Bukkit.getPlayer(args[1]);
					if (target == null) {
						p.sendMessage(ChatColor.AQUA + "Der Spieler " + ChatColor.GOLD + args[1] + ChatColor.AQUA + " ist nicht online.");
						return;
					}

					if (target.getName().equals(p.getName())) {
						p.sendMessage(ChatColor.AQUA + "Du kannst Dich nicht selbst einladen.");
						return;
					}
					
					if(!CubiTechUtil.getPlayerFaction(p).equalsIgnoreCase(CubiTechUtil.getPlayerFaction(target))) {
						p.sendMessage(ChatColor.AQUA + "Du kannst nur Spieler deines Volkes einladen.");
						return;
					}

					Party party = CubiTechUtil.getPartyByPlayer(p);
					if (party == null) {
						party = new Party(p);
						CubiTechUtil.playerParties.add(party);
						p.sendMessage(ChatColor.AQUA + "Du hast eine Gruppe erstellt!.");
					}
					
					if (!party.getLeader().getName().equals(p.getName())) {
						p.sendMessage(ChatColor.AQUA + "Du bist nicht Anfuehrer der Gruppe.");
					} else {
						if (CubiTechUtil.getPartyByPlayer(target) != null) {
							p.sendMessage(ChatColor.AQUA + "Der Spieler " + ChatColor.GOLD + target.getName() + ChatColor.AQUA + " ist bereits in einer Gruppe.");
						} else {
							CubiTechUtil.partyInvites.put(target, party);
							target.sendMessage(ChatColor.AQUA + "Du wurdest in die Gruppe von " + ChatColor.GOLD + party.getLeader().getName() + ChatColor.AQUA + " eingeladen.");
							target.sendMessage(ChatColor.AQUA + "Schreibe " + ChatColor.GOLD + "/gruppe annehmen" + ChatColor.AQUA + " , um der Gruppe beizutreten.");
							p.sendMessage(ChatColor.AQUA + "Du hast den Spieler " + ChatColor.GOLD + target.getName() + ChatColor.AQUA + " in die Gruppe eingeladen.");						
						}

					}

				} else if (args[0].equalsIgnoreCase("rauswerfen") || args[0].equalsIgnoreCase("kick")) {
					Player target = Bukkit.getPlayer(args[1]);

					if (target == null) {
						p.sendMessage(ChatColor.AQUA + "Der Spieler " + ChatColor.GOLD + args[1] + ChatColor.AQUA + " ist nicht online.");
						return;
					}

					Party party = CubiTechUtil.getPartyByPlayer(p);
					if (party == null) {
						p.sendMessage(ChatColor.AQUA + "Du bist derzeit in keiner Gruppe.");
					} else {
						if (!party.getLeader().getName().equals(p.getName())) {
							p.sendMessage(ChatColor.AQUA + "Du bist nicht Anfuehrer der Gruppe.");
						} else {
							if (party.getPlayers().contains(target)) {
								party.getPlayers().remove(target);
								for (Player q : party.getPlayers()) {
									q.sendMessage(ChatColor.AQUA + "Der Spieler " + ChatColor.GOLD + target.getName() + ChatColor.AQUA + " wurde aus der Gruppe geworfen.");
								}
								target.sendMessage(ChatColor.AQUA + "Du wurdest aus der Gruppe von " + ChatColor.GOLD + party.getLeader().getName() + ChatColor.AQUA + " geworfen.");
							} else {
								p.sendMessage(ChatColor.AQUA + "Der Spieler " + ChatColor.GOLD + target.getName() + ChatColor.AQUA + " ist nicht in deiner Gruppe.");
							}
						}
					}

				}
			} else {
				sender.sendMessage(ChatColor.AQUA + "Schreibe " + ChatColor.GOLD + "/gruppe hilfe" + ChatColor.AQUA + " , um Hilfe zu Gruppen zu bekommen.");
			}

		} else {
			sender.sendMessage(ChatColor.AQUA + "Schreibe " + ChatColor.GOLD + "/gruppe hilfe" + ChatColor.AQUA + " , um Hilfe zu Gruppen zu bekommen.");
		}
	}
}
