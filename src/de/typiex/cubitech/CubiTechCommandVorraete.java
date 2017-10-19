package de.typiex.cubitech;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubiTechCommandVorraete extends CubiTechCommand {

	public CubiTechCommandVorraete(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (!CubiTechUtil.isPlayerAdmin(p)) {
				p.sendMessage("§6Vorraete der Voelker:");
				p.sendMessage("§a   Solaner: §e§l" + CubiTechUtil.suppliesGrass);
				p.sendMessage("§6   Pyrer: §e§l" + CubiTechUtil.suppliesSand);
				p.sendMessage("§3   Arctiker: §e§l" + CubiTechUtil.suppliesSnow);
				p.sendMessage("§6Wie kann man Vorraete erhalten?");
				p.sendMessage("§6=> §eToete feindliche Spieler");
				p.sendMessage("§6=> §eRaube Vorraete aus gegnerischen Staedten");
				p.sendMessage("§5Je mehr Vorraete ein Volk hat, desto mehr Geld erhaelt jeder Spieler dieses Volkes beim Stuendlichen Bonus.");
				return;
			} else {
				if (args.length == 0) {
					p.sendMessage("§6Vorraete der Voelker:");
					p.sendMessage("§a   Solaner: §e§l" + CubiTechUtil.suppliesGrass);
					p.sendMessage("§6   Pyrer: §e§l" + CubiTechUtil.suppliesSand);
					p.sendMessage("§3   Arctiker: §e§l" + CubiTechUtil.suppliesSnow);
					p.sendMessage("§6Wie kann man Vorraete erhalten?");
					p.sendMessage("§6=> §eToete feindliche Spieler");
					p.sendMessage("§6=> §eRaube Vorraete aus gegnerischen Staedten");
					p.sendMessage("§5Je mehr Vorraete ein Volk hat, desto mehr Geld erhaelt jeder Spieler dieses Volkes beim Stuendlichen Bonus.");
				} else {
					if (args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("setzen")) {
						Block b = p.getTargetBlock(null, 10);
						try {
							File f = new File("plugins//CubiTech//supplies.txt");
							if (!f.exists())
								f.createNewFile();
							PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f, true)));
							out.println(b.getLocation().getX() + " " + b.getLocation().getY() + " " + b.getLocation().getZ());
							out.close();
							b.setTypeId(33);
							b.setData((byte) 15);
							plugin.getLogger().info(
									"Added Supply at x=" + b.getLocation().getX() + " y=" + b.getLocation().getY() + " z=" + b.getLocation().getZ()
											+ ".");
							p.sendMessage("§aVorrat wurde gesetzt §2[x=" + b.getLocation().getX() + " y=" + b.getLocation().getY() + " z="
									+ b.getLocation().getZ() + "]§a.");
						} catch (IOException e) {
							e.printStackTrace();
							p.sendMessage("§cVorrat konnte nicht gesetzt werden. Siehe Konsole.");
						}

					} else if (args[0].equalsIgnoreCase("reset") || args[0].equalsIgnoreCase("neusetzen")) {
						CubiTechUtil.placeSupplyBlocks();
						p.sendMessage("§aDu hast alle Vorraete neu gesetzt!");
					} else if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("entfernen")) {
						BufferedReader br = null;
						try {
							File f = new File("plugins//CubiTech//supplies.txt");
							if (!f.exists()) {
								f.createNewFile();
								p.sendMessage("§cVorraete-Datei existiert nicht und wurde nun erstellt.");
								return;
							}
							br = new BufferedReader(new FileReader(f));
							String line;
							World world = plugin.getServer().getWorld("world");
							while ((line = br.readLine()) != null) {
								Location loc = new Location(world, Double.valueOf(line.split(" ")[0]), Double.valueOf(line.split(" ")[1]),
										Double.valueOf(line.split(" ")[2]));
								if (p.getTargetBlock(null, 10).getLocation().equals(loc)) {
									CubiTechUtil.removeSupplyFromFile(loc.getBlock());
									loc.getBlock().setTypeId(0);
									p.sendMessage("§aVorrat wurde entfernt!");
									plugin.getLogger().info("Removed Supply at x=" + loc.getX() + " y=" + loc.getY() + " z=" + loc.getZ() + ".");
									return;
								}
							}

							p.sendMessage("§cDer Block ist kein Vorrat.");

						} catch (Exception e) {
							e.printStackTrace();
							plugin.getLogger().info("Failed to set Supplies!");
						} finally {
							if (br != null) {
								try {
									br.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					} else if (args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("liste")) {
						BufferedReader br = null;
						try {
							File f = new File("plugins//CubiTech//supplies.txt");
							if (!f.exists()) {
								f.createNewFile();
								p.sendMessage("§cVorraete-Datei existiert nicht und wurde nun erstellt.");
								return;
							}
							br = new BufferedReader(new FileReader(f));
							String line;
							World world = plugin.getServer().getWorld("world");
							int countGrass = 0, countSand = 0, countSnow = 0, countOther = 0;
							while ((line = br.readLine()) != null) {
								Location loc = new Location(world, Double.valueOf(line.split(" ")[0]), Double.valueOf(line.split(" ")[1]),
										Double.valueOf(line.split(" ")[2]));
								Biome biome = world.getBiome(loc.getBlockX(), loc.getBlockZ());
								if (biome == Biome.PLAINS)
									countGrass++;
								else if (biome == Biome.DESERT)
									countSand++;
								else if (biome == Biome.ICE_PLAINS)
									countSnow++;
								else
									countOther++;
							}
							p.sendMessage("§aSolaner-Gebiet Vorraete: §e" + countGrass);
							p.sendMessage("§6Pyrer-Gebiet Vorraete: §e" + countSand);
							p.sendMessage("§3Arctiker-Gebiet Vorraete: §e" + countSnow);
							p.sendMessage("§2Andere-Gebiete Vorraete: §e" + countOther);
						} catch (Exception e) {
							e.printStackTrace();
							plugin.getLogger().info("Failed to list Supplies!");
						} finally {
							if (br != null) {
								try {
									br.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
		}

	}
}
