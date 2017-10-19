package de.typiex.cubitech;

import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import net.minecraft.server.v1_6_R2.EntityHorse;
import net.minecraft.server.v1_6_R2.EntityPlayer;
import net.minecraft.server.v1_6_R2.InventoryHorseChest;
import net.minecraft.server.v1_6_R2.Item;
import net.minecraft.server.v1_6_R2.Packet130UpdateSign;
import net.minecraft.server.v1_6_R2.Packet201PlayerInfo;
import net.minecraft.server.v1_6_R2.Packet53BlockChange;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_6_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_6_R2.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

public class CubiTechUtil {

	public static HashMap<Player, Integer> playerLevels = new HashMap<Player, Integer>();
	public static HashMap<Player, String> playerClasses = new HashMap<Player, String>();
	public static HashMap<Player, String> playerFactions = new HashMap<Player, String>();
	public static HashMap<Player, Double> playerMana = new HashMap<Player, Double>();
	public static HashMap<Player, Integer> playerEP = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> playerMoney = new HashMap<Player, Integer>();

	public static int suppliesGrass = 0;
	public static int suppliesSand = 0;
	public static int suppliesSnow = 0;

	public static HashSet<Player> robbedPlayers = new HashSet<Player>();
	public static HashSet<Player> playerGodMode = new HashSet<Player>();
	public static HashSet<Party> playerParties = new HashSet<Party>();
	public static HashMap<Player, Party> partyInvites = new HashMap<Player, Party>();
	public static HashMap<Player, Sell> playerSells = new HashMap<Player, Sell>();
	public static HashMap<Player, KillQuest> playerQuests = new HashMap<Player, KillQuest>();
	public static HashSet<HeadBounty> headBounties = new HashSet<HeadBounty>();

	public static HashSet<String> serverListPingIPs = new HashSet<String>();
	public static HashSet<String> playersGotListPingBonus = new HashSet<String>();
	public static HashSet<Player> playersAfk = new HashSet<Player>();
	public static HashMap<Player, PlayerBool> playersTpRequests = new HashMap<Player, PlayerBool>();
	public static HashMap<Player, Location> playersBack = new HashMap<Player, Location>();
	public static HashSet<String> playersTmpBanned = new HashSet<String>();
	public static Auction auction = null;
	public static HashMap<Player, Integer> playersKillstreak = new HashMap<Player, Integer>();
	public static boolean fakeSlots = false;
	public static int fakeSlotsNum = 0;

	public static HashMap<Player, Integer> player1vs1Wins = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> player1vs1Fails = new HashMap<Player, Integer>();
	public static ArrayList<Player> players1vs1Waiting = new ArrayList<Player>();
	public static Player[] players1vs1Fighting = new Player[2];
	public static Location player1vs1Loc1 = null;
	public static Location player1vs1Loc2 = null;

	public static HashSet<Player> changedNamePlayersToRemove = new HashSet<Player>();
	public static Villager npcAusbilder = null;
	public static Villager npcAnwerber = null;
	public static Villager npcVerkaeufer = null;
	public static HashSet<Villager> npcsWaechter = new HashSet<Villager>();

	public static HashSet<Block> mageTeleporters = new HashSet<Block>();
	public static HashMap<Player, HashSet<Chest>> playersChestOpened = new HashMap<Player, HashSet<Chest>>();

	public static HashMap<Player, EntityHorse> playersMountedHorse = new HashMap<Player, EntityHorse>();
	public static HashMap<Player, CubiTechDragon> playersMountedDragon = new HashMap<Player, CubiTechDragon>();

	public static ScoreboardManager manager = Bukkit.getScoreboardManager();

	public static CubiTech plugin;

	public static void setPlugin(CubiTech c) {
		plugin = c;
	}

	public static boolean isPlayerAdmin(Player p) {
		if (p == null)
			return false;
		if (getPlayerClass(p) == null)
			return false;
		return p.isOp() || CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Admin");
	}

	public static class Auction {
		public Player player;
		public ItemStack item;
		public int currentPrice;
		public int timeLeft;
		public Player highestBidder;

		public Auction(Player p, ItemStack it, int start, int time) {
			this.player = p;
			this.item = it;
			this.timeLeft = time;
			this.currentPrice = start;
			this.highestBidder = null;
		}

	}

	public static void checkAuction() {
		if (auction != null) {
			if (auction.player == null) {
				Bukkit.broadcastMessage("§3Auktion musste beendet werden.");
				auction = null;
				return;
			}
			auction.timeLeft--;
			if (auction.timeLeft <= 0) {
				if (auction.currentPrice != 0 && auction.highestBidder != null) {
					Bukkit.broadcastMessage("§3Auktion: §f["
							+ auction.item.getAmount()
							+ "x"
							+ (auction.item.getItemMeta().getDisplayName() != null ? auction.item.getItemMeta().getDisplayName() + ChatColor.WHITE
									+ "(" + auction.item.getType().name().toLowerCase() + ChatColor.WHITE + ")" : auction.item.getType().name()
									.toLowerCase()) + "§f] von §e" + auction.player.getName());
					Bukkit.broadcastMessage("§b§lAuktion wurde beendet!");
					Bukkit.broadcastMessage("§b" + auction.highestBidder.getName() + " §3hat den Gegenstand fuer §e" + auction.currentPrice
							+ " Cubi §3ersteigert.");
					auction.player.getInventory().remove(auction.item);
					auction.highestBidder.getInventory().addItem(auction.item);
					CubiTechUtil.subtractPlayerMoney(auction.highestBidder, auction.currentPrice);
					CubiTechUtil.addPlayerMoney(auction.player, auction.currentPrice);
					auction = null;
				} else {
					Bukkit.broadcastMessage("§3Auktion: §f["
							+ auction.item.getAmount()
							+ "x"
							+ (auction.item.getItemMeta().getDisplayName() != null ? auction.item.getItemMeta().getDisplayName() + ChatColor.WHITE
									+ "(" + auction.item.getType().name().toLowerCase() + ChatColor.WHITE + ")" : auction.item.getType().name()
									.toLowerCase()) + "§f] von §e" + auction.player.getName());
					Bukkit.broadcastMessage("§b§lAuktion wurde beendet!");
					Bukkit.broadcastMessage("§bGegenstand wurde §cnicht §bversteigert!");
					auction = null;
				}
			} else if (auction.timeLeft == 5) {
				Bukkit.broadcastMessage("§3Auktion: §f["
						+ auction.item.getAmount()
						+ "x"
						+ (auction.item.getItemMeta().getDisplayName() != null ? auction.item.getItemMeta().getDisplayName() + ChatColor.WHITE + "("
								+ auction.item.getType().name().toLowerCase() + ChatColor.WHITE + ")" : auction.item.getType().name().toLowerCase())
						+ "§f] von §e" + auction.player.getName());
				if (auction.highestBidder != null) {
					Bukkit.broadcastMessage("§3Gebot: §f" + "§e" + auction.currentPrice + " Cubi§f von §e" + auction.highestBidder.getName());
				} else {
					Bukkit.broadcastMessage("§3Gebot: §fKeines");
				}
				Bukkit.broadcastMessage("§3Auktion endet in §b" + auction.timeLeft + " §3Sekunden.");
			} else if (auction.timeLeft == 30) {
				Bukkit.broadcastMessage("§3Auktion: §f["
						+ auction.item.getAmount()
						+ "x"
						+ (auction.item.getItemMeta().getDisplayName() != null ? auction.item.getItemMeta().getDisplayName() + ChatColor.WHITE + "("
								+ auction.item.getType().name().toLowerCase() + ChatColor.WHITE + ")" : auction.item.getType().name().toLowerCase())
						+ "§f] von §e" + auction.player.getName());
				if (auction.highestBidder != null) {
					Bukkit.broadcastMessage("§3Gebot: §f" + "§e" + auction.currentPrice + " Cubi§f von §e" + auction.highestBidder.getName());
				} else {
					Bukkit.broadcastMessage("§3Gebot: §fKeines");
				}
				Bukkit.broadcastMessage("§3Auktion endet in §b" + auction.timeLeft + " §3Sekunden.");
			}
		}
	}

	public static class HeadBounty {
		public Player player;
		public Player target;
		public int price;

		public HeadBounty(Player p, Player t, int pr) {
			this.player = p;
			this.target = t;
			this.price = pr;
		}

	}

	public static void setPlayerFaction(Player p, String f) {
		playerFactions.put(p, f);
	}

	public static String getPlayerFaction(Player p) {
		return playerFactions.get(p);
	}

	// for /tpa
	public static class PlayerBool {
		public Player invited;
		public boolean tpahere;

		public PlayerBool(Player invited, boolean tpahere) {
			this.invited = invited;
			this.tpahere = tpahere;
		}
	}

	public static class KillQuest {

		public Player player;
		public EntityType entityType;
		public int amount;
		public int killed;
		public int ep;
		public int money;

		public KillQuest(Player p, EntityType e, int a, int ep, int money) {
			this.player = p;
			this.entityType = e;
			this.amount = a;
			this.killed = 0;
			this.ep = ep;
			this.money = money;
		}

		public void addKill() {
			killed++;
			if (killed >= amount) {
				// Quest finished

				addPlayerEP(player, ep);
				addPlayerMoney(player, money);

				player.sendMessage(ChatColor.DARK_GREEN + "Quest erledigt!");
				player.sendMessage(ChatColor.GREEN + "Du hast erhalten: " + ChatColor.DARK_AQUA + ep + " EP" + ChatColor.AQUA + " und "
						+ ChatColor.DARK_AQUA + money + " Cubi");

				CubiTechUtil.playerQuests.remove(player);
			}
		}

	}

	public static class CollectQuest {

		public Player player;
		public Material material;
		public int amount;
		public int collected;
		public int ep;
		public int money;

		public CollectQuest(Player p, Material m, int a, int ep, int money) {
			this.player = p;
			this.material = m;
			this.amount = a;
			this.collected = 0;
			this.ep = ep;
			this.money = money;
		}

		public void addCollect() {
			collected++;
			if (collected >= amount) {
				// Quest finished

				addPlayerEP(player, ep);
				addPlayerMoney(player, money);

				player.sendMessage(ChatColor.DARK_GREEN + "Quest erledigt!");
				player.sendMessage(ChatColor.GREEN + "Du hast erhalten: " + ChatColor.DARK_AQUA + ep + " EP" + ChatColor.AQUA + " und "
						+ ChatColor.DARK_AQUA + money + " Cubi");

				CubiTechUtil.playerQuests.remove(player);
			}
		}

	}

	public static class Sell {
		public ItemStack item;
		public int price;

		public Sell(ItemStack i, int p) {
			this.item = i;
			this.price = p;
		}
	}

	public static void reload1vs1() {
		File f = new File("plugins//CubiTech//1vs1.txt");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			try {
				Properties props = new Properties();
				BufferedInputStream stream = new BufferedInputStream(new FileInputStream(new File("plugins//CubiTech//1vs1.txt")));
				props.load(stream);
				stream.close();

				String sLoc1 = props.getProperty("arena.location.1");
				if (sLoc1 == null) {
					player1vs1Loc1 = new Location(plugin.getServer().getWorld("world"), 0, 0, 0);
				} else {
					player1vs1Loc1 = new Location(plugin.getServer().getWorld("world"), Double.valueOf(sLoc1.split(" ")[0]), Double.valueOf(sLoc1
							.split(" ")[1]), Double.valueOf(sLoc1.split(" ")[2]), Float.valueOf(sLoc1.split(" ")[3]),
							Float.valueOf(sLoc1.split(" ")[4]));
				}

				String sLoc2 = props.getProperty("arena.location.2");
				if (sLoc2 == null) {
					player1vs1Loc2 = new Location(plugin.getServer().getWorld("world"), 0, 0, 0);
				} else {
					player1vs1Loc2 = new Location(plugin.getServer().getWorld("world"), Double.valueOf(sLoc2.split(" ")[0]), Double.valueOf(sLoc2
							.split(" ")[1]), Double.valueOf(sLoc2.split(" ")[2]), Float.valueOf(sLoc2.split(" ")[3]),
							Float.valueOf(sLoc2.split(" ")[4]));
				}

				for (Player p : Bukkit.getOnlinePlayers()) {
					try {
						if (!props.containsKey(p.getName())) {
							props.setProperty(p.getName(), "0 0");
						}
					} catch (Exception ex) {
					}
				}

				for (Object o : props.keySet()) {
					try {
						String s = (String) o;
						if (!s.contains(".")) {
							String name = s;
							Player p = Bukkit.getPlayer(name);
							if (p != null) {
								String sWins = props.getProperty(name).split(" ")[0];
								String sFails = props.getProperty(name).split(" ")[1];
								int wins = Integer.valueOf(sWins);
								int fails = Integer.valueOf(sFails);
								player1vs1Wins.put(p, wins);
								player1vs1Fails.put(p, fails);
							}
						}
					} catch (Exception ex) {
					}
				}

				FileOutputStream fos = new FileOutputStream(f);
				props.store(fos, "CubiTech 1vs1 File. Player=Wins Fails");

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void checkArmorForLevel(Player p) {
		boolean canUseDiamond = false;
		if (isPlayerAdmin(p) || getPlayerClass(p).equalsIgnoreCase("Ritter") || getPlayerClass(p).equalsIgnoreCase("Adeliger")
				|| getPlayerClass(p).equalsIgnoreCase("Krieger") || getPlayerClass(p).equalsIgnoreCase("YouTuber")) {
			canUseDiamond = true;
		}
		for (int i = 0; i < p.getInventory().getArmorContents().length; i++) {

			boolean isDiamond = false;
			if (p.getInventory().getArmorContents()[i].getType() == Material.DIAMOND_BOOTS
					|| p.getInventory().getArmorContents()[i].getType() == Material.DIAMOND_LEGGINGS
					|| p.getInventory().getArmorContents()[i].getType() == Material.DIAMOND_CHESTPLATE
					|| p.getInventory().getArmorContents()[i].getType() == Material.DIAMOND_HELMET) {
				isDiamond = true;
			}

			boolean canUseItem = true;
			if (!canUseDiamond && isDiamond) {
				canUseItem = false;
			}

			if (!canUseItem) {
				final ItemStack _item = p.getInventory().getArmorContents()[i];
				ItemStack[] newArmorContents = p.getInventory().getArmorContents();
				newArmorContents[i].setType(Material.AIR);
				p.getInventory().setArmorContents(newArmorContents);
				p.getInventory().addItem(_item);
				p.sendMessage(ChatColor.RED + "Das kannst Du leider nicht verwenden!");
			}

			if (p.getInventory().getArmorContents()[i].getItemMeta() != null) {
				if (p.getInventory().getArmorContents()[i].getItemMeta().getDisplayName() != null) {

					if (p.getInventory().getArmorContents()[i].getItemMeta().getDisplayName().toLowerCase().startsWith(ChatColor.GREEN + "selten")) {
						if (CubiTechUtil.getPlayerLevel(p) < 10) {
							final ItemStack _item = p.getInventory().getArmorContents()[i];
							ItemStack[] newArmorContents = p.getInventory().getArmorContents();
							newArmorContents[i].setType(Material.AIR);
							p.getInventory().setArmorContents(newArmorContents);
							p.getInventory().addItem(_item);
							p.sendMessage(ChatColor.RED + "Das kannst Du leider noch nicht verwenden!");
						}
					} else if (p.getInventory().getArmorContents()[i].getItemMeta().getDisplayName().toLowerCase()
							.startsWith(ChatColor.DARK_GREEN + "sehr selten")) {
						if (CubiTechUtil.getPlayerLevel(p) < 20) {
							final ItemStack _item = p.getInventory().getArmorContents()[i];
							ItemStack[] newArmorContents = p.getInventory().getArmorContents();
							newArmorContents[i].setType(Material.AIR);
							p.getInventory().setArmorContents(newArmorContents);
							p.getInventory().addItem(_item);
							p.sendMessage(ChatColor.RED + "Das kannst Du leider noch nicht verwenden!");
						}
					} else if (p.getInventory().getArmorContents()[i].getItemMeta().getDisplayName().toLowerCase()
							.startsWith(ChatColor.DARK_AQUA + "extrem")) {
						if (CubiTechUtil.getPlayerLevel(p) < 40) {
							final ItemStack _item = p.getInventory().getArmorContents()[i];
							ItemStack[] newArmorContents = p.getInventory().getArmorContents();
							newArmorContents[i].setType(Material.AIR);
							p.getInventory().setArmorContents(newArmorContents);
							p.getInventory().addItem(_item);
							p.sendMessage(ChatColor.RED + "Das kannst Du leider noch nicht verwenden!");
						}
					} else if (p.getInventory().getArmorContents()[i].getItemMeta().getDisplayName().toLowerCase()
							.startsWith(ChatColor.DARK_PURPLE + "heroisch")) {
						if (CubiTechUtil.getPlayerLevel(p) < 50) {
							final ItemStack _item = p.getInventory().getArmorContents()[i];
							ItemStack[] newArmorContents = p.getInventory().getArmorContents();
							newArmorContents[i].setType(Material.AIR);
							p.getInventory().setArmorContents(newArmorContents);
							p.getInventory().addItem(_item);
							p.sendMessage(ChatColor.RED + "Das kannst Du leider noch nicht verwenden!");
						}
					} else if (p.getInventory().getArmorContents()[i].getItemMeta().getDisplayName().toLowerCase()
							.startsWith(ChatColor.RED + "legendaer")) {
						if (CubiTechUtil.getPlayerLevel(p) < 60) {
							final ItemStack _item = p.getInventory().getArmorContents()[i];
							ItemStack[] newArmorContents = p.getInventory().getArmorContents();
							newArmorContents[i].setType(Material.AIR);
							p.getInventory().setArmorContents(newArmorContents);
							p.getInventory().addItem(_item);
							p.sendMessage(ChatColor.RED + "Das kannst Du leider noch nicht verwenden!");
						}
					}
				}
			}
		}
	}

	public static void add1vs1Win(Player p) {
		try {
			Properties props = new Properties();
			BufferedInputStream stream = new BufferedInputStream(new FileInputStream(new File("plugins//CubiTech//1vs1.txt")));
			props.load(stream);
			stream.close();
			props.setProperty(p.getName(), String.valueOf(player1vs1Wins.get(p) + 1) + " " + String.valueOf(player1vs1Fails.get(p)));
			FileOutputStream fos = new FileOutputStream(new File("plugins//CubiTech//1vs1.txt"));
			props.store(fos, "CubiTech 1vs1 File. Player=Wins Fails");
			reload1vs1();
		} catch (Exception ex) {
		}
	}

	public static void add1vs1Fail(Player p) {
		try {
			Properties props = new Properties();
			BufferedInputStream stream = new BufferedInputStream(new FileInputStream(new File("plugins//CubiTech//1vs1.txt")));
			props.load(stream);
			stream.close();
			props.setProperty(p.getName(), String.valueOf(player1vs1Wins.get(p)) + " " + String.valueOf(player1vs1Fails.get(p) + 1));
			FileOutputStream fos = new FileOutputStream(new File("plugins//CubiTech//1vs1.txt"));
			props.store(fos, "CubiTech 1vs1 File. Player=Wins Fails");
			reload1vs1();
		} catch (Exception ex) {
		}
	}

	public static void check1vs1() {
		if (players1vs1Waiting.size() < 2 || players1vs1Fighting[0] != null || players1vs1Fighting[1] != null) {
			return;
		}

		Player p1 = players1vs1Waiting.get(0);
		Player p2 = players1vs1Waiting.get(1);

		p1.teleport(player1vs1Loc1);
		p2.teleport(player1vs1Loc2);

		players1vs1Fighting[0] = p1;
		players1vs1Fighting[1] = p2;

		players1vs1Waiting.remove(p1);
		players1vs1Waiting.remove(p2);

		Bukkit.broadcastMessage(ChatColor.GOLD + p1.getName() + ChatColor.YELLOW + " kaempft nun gegen " + ChatColor.GOLD + p2.getName()
				+ ChatColor.YELLOW + " im 1vs1.");

	}

	public static void set1vs1Location1(Location l) {
		try {
			File f = new File("plugins//CubiTech//1vs1.txt");
			if (!f.exists())
				f.createNewFile();
			Properties props = new Properties();
			BufferedInputStream stream = new BufferedInputStream(new FileInputStream(f));
			props.load(stream);
			stream.close();
			props.setProperty("arena.location.1", l.getX() + " " + l.getY() + " " + l.getZ() + " " + l.getYaw() + " " + l.getPitch());
			FileOutputStream fos = new FileOutputStream(f);
			props.store(fos, "CubiTech 1vs1 File. Player=Wins Fails");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void set1vs1Location2(Location l) {
		try {
			File f = new File("plugins//CubiTech//1vs1.txt");
			if (!f.exists())
				f.createNewFile();
			Properties props = new Properties();
			BufferedInputStream stream = new BufferedInputStream(new FileInputStream(f));
			props.load(stream);
			stream.close();
			props.setProperty("arena.location.2", l.getX() + " " + l.getY() + " " + l.getZ() + " " + l.getYaw() + " " + l.getPitch());
			FileOutputStream fos = new FileOutputStream(f);
			props.store(fos, "CubiTech 1vs1 File. Player=Wins Fails");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static Party getPartyByPlayer(Player p) {
		for (Party party : playerParties) {
			if (party.getPlayers().contains(p)) {
				return party;
			}
		}
		return null;
	}

	public static Party getPartyByLeader(Player p) {
		for (Party party : playerParties) {
			if (party.getLeader().getName().equals(p.getName())) {
				return party;
			}
		}
		return null;
	}

	public static class Party {

		private HashSet<Player> players;
		private boolean split;
		private Player leader;

		public Party(Player lead) {
			this.leader = lead;
			this.players = new HashSet<Player>();
			this.players.add(lead);
			this.split = false;
		}

		public void setPlayers(HashSet<Player> pls) {
			this.players = pls;
		}

		public void addPlayer(Player p) {
			this.players.add(p);
		}

		public HashSet<Player> getPlayers() {
			return players;
		}

		public void setSplit(boolean s) {
			this.split = s;
		}

		public boolean getSplit() {
			return split;
		}

		public void setLeader(Player leader) {
			this.leader = leader;
		}

		public Player getLeader() {
			return leader;
		}

	}

	// Point.x = ep, Point.y = money
	public static Point getEPMoneyAfterKill(Player p, int divideBy) {

		int lvl = CubiTechUtil.getPlayerLevel(p);
		int ep = 1;

		if (between(lvl, 0, 3)) {
			ep = rnd(100, 300);
		} else if (between(lvl, 4, 10)) {
			ep = rnd(50, 150);
		} else if (between(lvl, 10, 20)) {
			ep = rnd(20, 50);
		} else if (between(lvl, 20, 30)) {
			ep = rnd(10, 50);
		} else if (between(lvl, 30, 40)) {
			ep = rnd(5, 20);
		} else if (between(lvl, 40, 50)) {
			ep = rnd(1, 5);
		} else if (between(lvl, 50, 60)) {
			ep = rnd(1, 3);
		}

		int money = rnd(1, 3);

		// divide
		ep /= divideBy;
		money /= divideBy;

		if (getPlayerClass(p).equalsIgnoreCase("Ritter") || getPlayerClass(p).equalsIgnoreCase("Adeliger") || playersDoubleEP.contains(p.getName())) {
			ep *= 2;
		}

		return new Point(ep, money);
	}

	private static boolean between(int n, int low, int high) {
		return n >= low && n <= high;
	}

	private static int rnd(int min, int max) {
		return (new Random().nextInt(max + 1 - min) + min);
	}

	public static int getPlayerMoney(Player p) {
		return playerMoney.get(p);
	}

	public static void setPlayerMoney(Player p, int val) {
		playerMoney.put(p, val);
	}

	public static void addPlayerMoney(Player p, int val) {
		playerMoney.put(p, playerMoney.get(p) + val);
	}

	public static int getPlayerEP(Player p) {
		return playerEP.get(p);
	}

	public static void setPlayerEP(Player p, int val) {
		playerEP.put(p, val);
	}

	public static void addPlayerEP(Player p, int val) {
		playerEP.put(p, playerEP.get(p) + val);
	}

	public static void spawnNPCs() {

		if (Bukkit.getWorld("world") == null) {
			plugin.getLogger().info("Failed to spawn NPCs: World 'world' not found.");
			return;
		}

		Location locAusbilder = new Location(Bukkit.getWorld("world"), 992 + 0.5, 127, 972 + 0.5);
		Villager villagerAusbilder = (Villager) Bukkit.getWorld("world").spawn(locAusbilder, Villager.class);
		villagerAusbilder.setCustomNameVisible(true);
		villagerAusbilder.setCustomName("§2Ausbilder");
		PotionEffect slow = new PotionEffect(PotionEffectType.SLOW, 10000, 1000);
		slow.apply(villagerAusbilder);
		villagerAusbilder.setProfession(Profession.PRIEST);
		villagerAusbilder.setRemoveWhenFarAway(false);
		npcAusbilder = villagerAusbilder;

		Location locAnwerber = new Location(Bukkit.getWorld("world"), 1001, 128, 1031);
		Villager villagerAnwerber = (Villager) Bukkit.getWorld("world").spawn(locAnwerber, Villager.class);
		villagerAnwerber.setCustomNameVisible(true);
		villagerAnwerber.setCustomName("§2Anwerber");
		slow.apply(villagerAnwerber);
		villagerAnwerber.setProfession(Profession.LIBRARIAN);
		villagerAnwerber.setRemoveWhenFarAway(false);
		npcAnwerber = villagerAnwerber;

		Location locVerkaeufer = new Location(Bukkit.getWorld("world"), 977 + 0.5, 109, 1001 + 0.5);
		Villager villagerVerkaeufer = (Villager) Bukkit.getWorld("world").spawn(locVerkaeufer, Villager.class);
		villagerVerkaeufer.setCustomNameVisible(true);
		villagerVerkaeufer.setCustomName("§2Verkaeufer");
		slow.apply(villagerVerkaeufer);
		villagerVerkaeufer.setProfession(Profession.BLACKSMITH);
		villagerVerkaeufer.setRemoveWhenFarAway(false);
		npcVerkaeufer = villagerVerkaeufer;

		spawnNPCWaechter(new Location(Bukkit.getWorld("world"), 544 + 0.5, 70, 371 + 0.5), "§aSolaner Wache");
		spawnNPCWaechter(new Location(Bukkit.getWorld("world"), 544 + 0.5, 70, 373 + 0.5), "§aSolaner Wache");
		spawnNPCWaechter(new Location(Bukkit.getWorld("world"), 604 + 0.5, 71, 364 + 0.5), "§aSolaner Wache");
		spawnNPCWaechter(new Location(Bukkit.getWorld("world"), 604 + 0.5, 71, 362 + 0.5), "§aSolaner Wache");
		spawnNPCWaechter(new Location(Bukkit.getWorld("world"), 576 + 0.5, 68, 331 + 0.5), "§aSolaner Wache");
		spawnNPCWaechter(new Location(Bukkit.getWorld("world"), 573 + 0.5, 68, 331 + 0.5), "§aSolaner Wache");

		spawnNPCWaechter(new Location(Bukkit.getWorld("world"), 1293 + 0.5, 64, 656 + 0.5), "§6Pyrer Wache");
		spawnNPCWaechter(new Location(Bukkit.getWorld("world"), 1293 + 0.5, 64, 647 + 0.5), "§6Pyrer Wache");
		spawnNPCWaechter(new Location(Bukkit.getWorld("world"), 1382 + 0.5, 77, 656 + 0.5), "§6Pyrer Wache");
		spawnNPCWaechter(new Location(Bukkit.getWorld("world"), 1382 + 0.5, 77, 647 + 0.5), "§6Pyrer Wache");
		spawnNPCWaechter(new Location(Bukkit.getWorld("world"), 1478 + 0.5, 73, 653 + 0.5), "§6Pyrer Wache");
		spawnNPCWaechter(new Location(Bukkit.getWorld("world"), 1478 + 0.5, 73, 664 + 0.5), "§6Pyrer Wache");

		spawnNPCWaechter(new Location(Bukkit.getWorld("world"), 446 + 0.5, 63, 1519 + 0.5), "§3Arctiker Wache");
		spawnNPCWaechter(new Location(Bukkit.getWorld("world"), 448 + 0.5, 63, 1519 + 0.5), "§3Arctiker Wache");
		spawnNPCWaechter(new Location(Bukkit.getWorld("world"), 407 + 0.5, 64, 1457 + 0.5), "§3Arctiker Wache");
		spawnNPCWaechter(new Location(Bukkit.getWorld("world"), 407 + 0.5, 64, 1453 + 0.5), "§3Arctiker Wache");
		spawnNPCWaechter(new Location(Bukkit.getWorld("world"), 466 + 0.5, 65, 1422 + 0.5), "§3Arctiker Wache");
		spawnNPCWaechter(new Location(Bukkit.getWorld("world"), 462 + 0.5, 65, 1422 + 0.5), "§3Arctiker Wache");
		spawnNPCWaechter(new Location(Bukkit.getWorld("world"), 593 + 0.5, 63, 1525 + 0.5), "§3Arctiker Wache");
		spawnNPCWaechter(new Location(Bukkit.getWorld("world"), 593 + 0.5, 63, 1528 + 0.5), "§3Arctiker Wache");

	}

	public static void moveNPCs() {
		for (Villager n : npcsWaechter) {
			if (n != null) {
				n.setFireTicks(0);

				List<Entity> near = n.getNearbyEntities(5.0D, 5.0D, 5.0D);
				for (Entity entity : near) {
					if (entity instanceof Player) {
						Player nearPlayer = (Player) entity;

						ChatColor lvlColor = ChatColor.GRAY;
						if (CubiTechUtil.getPlayerFaction(nearPlayer).equalsIgnoreCase("grass")) {
							lvlColor = ChatColor.GREEN;
						} else if (CubiTechUtil.getPlayerFaction(nearPlayer).equalsIgnoreCase("sand")) {
							lvlColor = ChatColor.GOLD;
						} else if (CubiTechUtil.getPlayerFaction(nearPlayer).equalsIgnoreCase("snow")) {
							lvlColor = ChatColor.DARK_AQUA;
						}
						String s = lvlColor + "";

						if (n.getCustomName().startsWith(s) || nearPlayer.getGameMode() == GameMode.CREATIVE) {
							// same faction or creative mode
							continue;
						}

						n.setTarget(nearPlayer);
						n.teleport(lookAt(n.getLocation(), nearPlayer.getLocation()));

						double yaw = Math.toRadians((double) n.getLocation().getYaw());
						final double force = 1;
						double x = force * Math.sin(-yaw);
						double z = force * Math.cos(-yaw);
						n.setVelocity(new Vector(x, 0, z));

						nearPlayer.damage(1.0, n);
						if (Math.random() > 0.9) {
							nearPlayer.getWorld().strikeLightning(nearPlayer.getLocation());
						}
						if (Math.random() > 0.6) {
							Arrow a = n.launchProjectile(Arrow.class);
							a.setVelocity(n.getLocation().getDirection().multiply(2));
						}
						return;
					} else if (entity instanceof Monster) {
						((Monster) entity).damage(2.0, n);
						n.setTarget((Monster) entity);
						return;
					}
				}
			}
		}
	}

	public static void despawnNPCs() {
		// remove npcs
		if (npcAusbilder != null) {
			npcAusbilder.remove();
		}
		if (npcAnwerber != null) {
			npcAnwerber.remove();
		}
		if (npcVerkaeufer != null) {
			npcVerkaeufer.remove();
		}

		for (Villager n : npcsWaechter) {
			if (n != null) {
				n.remove();
			}
		}

		npcsWaechter.clear();

		for (LivingEntity l : Bukkit.getWorld("world").getLivingEntities()) {
			if (l instanceof Villager) {
				l.remove();
			}
		}

	}

	public static void spawnNPCWaechter(Location l, String name) {
		Location locWaechter = l;
		Villager villagerWaechter = (Villager) Bukkit.getWorld("world").spawn(locWaechter, Villager.class);
		villagerWaechter.setCustomNameVisible(true);
		villagerWaechter.setCustomName(name);
		PotionEffect effect1 = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10000, 4);
		PotionEffect effect2 = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 10000, 3);
		PotionEffect slow = new PotionEffect(PotionEffectType.SLOW, 10000, 10000);
		effect1.apply(villagerWaechter);
		effect2.apply(villagerWaechter);
		slow.apply(villagerWaechter);
		villagerWaechter.setProfession(Profession.BUTCHER);
		villagerWaechter.setRemoveWhenFarAway(false);
		npcsWaechter.add(villagerWaechter);
	}

	public static Location lookAt(Location loc, Location lookat) {

		loc = loc.clone();

		double dx = lookat.getX() - loc.getX();
		double dy = lookat.getY() - loc.getY();
		double dz = lookat.getZ() - loc.getZ();

		if (dx != 0) {
			if (dx < 0) {
				loc.setYaw((float) (1.5 * Math.PI));
			} else {
				loc.setYaw((float) (0.5 * Math.PI));
			}
			loc.setYaw((float) loc.getYaw() - (float) Math.atan(dz / dx));
		} else if (dz < 0) {
			loc.setYaw((float) Math.PI);
		}

		double dxz = Math.sqrt(Math.pow(dx, 2) + Math.pow(dz, 2));

		loc.setPitch((float) -Math.atan(dy / dxz));

		loc.setYaw(-loc.getYaw() * 180f / (float) Math.PI);
		loc.setPitch(loc.getPitch() * 180f / (float) Math.PI);

		return loc;
	}

	public static void saveState() {
		try {
			File f = new File("plugins//CubiTech//users.txt");
			if (!f.exists())
				f.createNewFile();
			Properties props = new Properties();
			BufferedInputStream stream = new BufferedInputStream(new FileInputStream(f));
			props.load(stream);
			stream.close();
			for (Player p : Bukkit.getOnlinePlayers()) {
				props.setProperty(p.getName(),
						CubiTechUtil.getPlayerClass(p) + " " + CubiTechUtil.getPlayerFaction(p) + " " + CubiTechUtil.getPlayerLevel(p) + " "
								+ CubiTechUtil.getPlayerEP(p) + " " + CubiTechUtil.getPlayerMoney(p));
			}
			FileOutputStream fos = new FileOutputStream(f);
			props.store(fos, "CubiTech user file. Player=Class Faction Level EP Money");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void reloadScoreboard() {

		for (Player p : Bukkit.getOnlinePlayers()) {

			Scoreboard board = manager.getNewScoreboard();

			// Build the teams ..
			Party party = getPartyByPlayer(p);
			for (Player q : Bukkit.getOnlinePlayers()) {
				String teamName = q.getName();

				Team team = null;
				if (board.getTeam(teamName) == null) {
					team = board.registerNewTeam(teamName);
				} else {
					team = board.getTeam(teamName);
				}

				team.addPlayer(q);
				team.setAllowFriendlyFire(true);
				team.setCanSeeFriendlyInvisibles(false);

				ChatColor color = ChatColor.GRAY;
				if (CubiTechUtil.getPlayerClass(q) != null) {
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
				}

				team.setPrefix(color + "" + (playersAfk.contains(q) ? ChatColor.STRIKETHROUGH : ""));

				if (party != null) {
					if (party.getPlayers().contains(q)) {
						team.setPrefix(ChatColor.AQUA + "");
						team.setCanSeeFriendlyInvisibles(true);
					}
				}

				ChatColor lvlColor = ChatColor.GRAY;
				if (CubiTechUtil.getPlayerFaction(q).equalsIgnoreCase("grass")) {
					lvlColor = ChatColor.GREEN;
				} else if (CubiTechUtil.getPlayerFaction(q).equalsIgnoreCase("sand")) {
					lvlColor = ChatColor.GOLD;
				} else if (CubiTechUtil.getPlayerFaction(q).equalsIgnoreCase("snow")) {
					lvlColor = ChatColor.DARK_AQUA;
				}

				String suffix = ChatColor.RESET + "" + lvlColor + " *" + getPlayerLevel(q);
				if (suffix.length() < 16) {
					team.setSuffix(suffix);
				} else {
					team.setSuffix("");
				}
			}

			if (board.getObjective(DisplaySlot.BELOW_NAME) != null) {
				board.getObjective(DisplaySlot.BELOW_NAME).unregister();
			}
			if (board.getObjective("showhealth") != null) {
				board.getObjective("showhealth").unregister();
			}
			board.registerNewObjective("showhealth", "dummy");
			Objective objective_showhealth = board.getObjective("showhealth");
			objective_showhealth.setDisplaySlot(DisplaySlot.BELOW_NAME);
			objective_showhealth.setDisplayName(" % " + ChatColor.RED + "Leben");

			for (Player q : Bukkit.getOnlinePlayers()) {
				Score score_leben = objective_showhealth.getScore(q);
				score_leben.setScore((int) (((Damageable) q).getHealth() * 5));
			}

			// SideBar
			if (board.getObjective("stats") != null) {
				board.getObjective("stats").unregister();
			}
			if (board.getObjective(DisplaySlot.SIDEBAR) != null) {
				board.getObjective(DisplaySlot.SIDEBAR).unregister();
			}

			Objective objective_stats = board.registerNewObjective("stats", "dummy");
			objective_stats.setDisplaySlot(DisplaySlot.SIDEBAR);

			ChatColor color = ChatColor.GRAY;
			if (CubiTechUtil.getPlayerClass(p) != null) {
				if (CubiTechUtil.isPlayerAdmin(p)) {
					color = ChatColor.DARK_RED;
				} else if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Ritter")) {
					color = ChatColor.DARK_GREEN;
				} else if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Adeliger")) {
					color = ChatColor.GOLD;
				} else if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("YouTuber")) {
					color = ChatColor.DARK_AQUA;
				} else if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Supporter")) {
					color = ChatColor.RED;
				}
			}

			int supplies = 0;
			ChatColor lvlColor = ChatColor.GRAY;
			if (CubiTechUtil.getPlayerFaction(p).equalsIgnoreCase("grass")) {
				lvlColor = ChatColor.GREEN;
				supplies = suppliesGrass;
			} else if (CubiTechUtil.getPlayerFaction(p).equalsIgnoreCase("sand")) {
				lvlColor = ChatColor.GOLD;
				supplies = suppliesSand;
			} else if (CubiTechUtil.getPlayerFaction(p).equalsIgnoreCase("snow")) {
				lvlColor = ChatColor.DARK_AQUA;
				supplies = suppliesSnow;
			}

			objective_stats.setDisplayName(color + "" + ChatColor.UNDERLINE + getPlayerClass(p) + ChatColor.RESET + " " + lvlColor + ""
					+ ChatColor.UNDERLINE + getPlayerLevel(p));

			Score score_leben = objective_stats.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Leben:"));
			score_leben.setScore((int) (((Damageable) p).getHealth() * 5));

			Score score_mana = objective_stats.getScore(Bukkit.getOfflinePlayer(ChatColor.LIGHT_PURPLE + "Mana:"));
			score_mana.setScore((int) (getPlayerMana(p) * 100));

			Score score_ep = objective_stats.getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "EP:"));
			score_ep.setScore(getPlayerEP(p));

			Score score_money = objective_stats.getScore(Bukkit.getOfflinePlayer(ChatColor.AQUA + "Geld:"));
			score_money.setScore(getPlayerMoney(p));

			Score score_vorraete = objective_stats.getScore(Bukkit.getOfflinePlayer(ChatColor.GRAY + "Vorraete:"));
			score_vorraete.setScore(supplies);

			if (playerQuests.containsKey(p)) {
				Score score_quest = objective_stats.getScore(Bukkit.getOfflinePlayer(ChatColor.BLUE + "Quest:"));
				score_quest.setScore(playerQuests.get(p).killed);
			}
			if (playersKillstreak.containsKey(p)) {
				if (playersKillstreak.get(p) > 0) {
					Score score_streak = objective_stats.getScore(Bukkit.getOfflinePlayer(ChatColor.DARK_RED + "Serie:"));
					score_streak.setScore(playersKillstreak.get(p));
				}
			}

			p.setScoreboard(board);

			// Update Tab list
			addTab(p, "§e§l[]§r§a >>>>");
			addTab(p, "§6     CubiTech");
			addTab(p, "§a<<<< §e§l[]§r");
		}

	}

	private static void addTab(Player p, String text) {
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(new Packet201PlayerInfo(text, true, 0));
	}

	public static ItemStack getItemCubiCoupon() {
		ItemStack is = new ItemStack(Material.PAPER, 1);
		ItemMeta am = (ItemMeta) is.getItemMeta();
		am.setDisplayName(ChatColor.GOLD + "" + ChatColor.UNDERLINE + "Gutschein");
		ArrayList<String> desc = new ArrayList<String>();
		desc.add(ChatColor.BLUE + "Wert: 50 Cubi");
		desc.add(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Rechtsklick zum Einlösen.");
		am.setLore(desc);
		is.setItemMeta(am);

		return is;
	}

	public static void removeChangedNamePlayer(Player p) {
		changedNamePlayersToRemove.add(p);
	}

	public static boolean getPlayerGodMode(Player p) {
		return playerGodMode.contains(p);
	}

	public static void setPlayerGodMode(Player p, boolean godmode) {
		if (godmode) {
			if (!playerGodMode.contains(p)) {
				playerGodMode.add(p);
			}
		} else {
			if (playerGodMode.contains(p)) {
				playerGodMode.remove(p);
			}
		}
	}

	public static String getPlayerClass(Player p) {
		return playerClasses.get(p);
	}

	public static int getPlayerLevel(Player p) {
		return playerLevels.get(p);
	}

	public static double getPlayerMana(Player p) {
		if (CubiTechUtil.isPlayerAdmin(p)) {
			return 1;
		} else {
			return playerMana.get(p);
		}
	}

	public static void updateLevelBar(Player p) {
		p.setLevel(playerLevels.get(p));
		p.setExp(playerMana.get(p).floatValue());

		// check for level up
		if (getPlayerEP(p) >= 1000 && getPlayerLevel(p) < 60) {
			// LevelUp !
			playerLevels.put(p, getPlayerLevel(p) + 1);
			if (getPlayerEP(p) > 1000) {
				int over = getPlayerEP(p) - 1000;
				setPlayerEP(p, over);
			} else {
				setPlayerEP(p, 0);
			}
			saveState();
			reloadScoreboard();
			spawnLevelUpFirework(p.getLocation());

			p.sendMessage(ChatColor.GOLD + "" + ChatColor.MAGIC + "iii" + ChatColor.YELLOW + " LEVEL UP! " + ChatColor.GOLD + ChatColor.MAGIC + "iii");
			Bukkit.broadcastMessage(ChatColor.AQUA + p.getName() + ChatColor.GOLD + " ist nun " + ChatColor.YELLOW + "Lvl. " + getPlayerLevel(p)
					+ ChatColor.GOLD + " !");
		}

	}

	public static void spawnLevelUpFirework(Location location) {
		for (int i = 0; i < 10; i++) {
			Location l = new Location(location.getWorld(), location.getX() + new Random().nextInt(3), location.getY() + new Random().nextInt(3),
					location.getZ() + new Random().nextInt(3));
			Firework fw = (Firework) l.getWorld().spawnEntity(l, EntityType.FIREWORK);
			FireworkMeta meta = fw.getFireworkMeta();

			FireworkEffect effect = FireworkEffect.builder().withColor(Color.GREEN).with(Type.BALL_LARGE).build();
			meta.addEffect(effect);

			meta.setPower(2);

			fw.setFireworkMeta(meta);
		}
	}

	public static ItemStack getItemPartybow() {
		ItemStack item = new ItemStack(Material.BOW, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§6P§2a§3r§4t§5y" + ChatColor.WHITE + "-" + ChatColor.RED + "Bogen");
		ArrayList<String> desc = new ArrayList<String>();
		desc.add(" ");
		desc.add(ChatColor.GOLD + "Kann Feuerwerksraketen schiessen");
		desc.add(" ");
		desc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
		meta.setLore(desc);
		meta.addEnchant(Enchantment.ARROW_INFINITE, 1, false);

		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack getItemWitherbow() {
		ItemStack item = new ItemStack(Material.BOW, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§9Wither" + ChatColor.WHITE + "-" + ChatColor.RED + "Bogen");
		ArrayList<String> desc = new ArrayList<String>();
		desc.add(" ");
		desc.add(ChatColor.GOLD + "Kann Wither Skulls schiessen");
		desc.add(" ");
		desc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
		meta.setLore(desc);
		meta.addEnchant(Enchantment.ARROW_INFINITE, 1, false);

		item.setItemMeta(meta);
		return item;
	}

	public static void regenMana(Player p) {
		try {
			double val = playerMana.get(p);
			if (getPlayerLevel(p) >= 60)
				val += 0.15;
			else
				val += 0.1;
			if (val > 1)
				val = 1;
			playerMana.put(p, val);
			updateLevelBar(p);
		} catch (Exception e) {
		}
	}

	public static void subtractMana(Player p, double d) {
		if (!CubiTechUtil.isPlayerAdmin(p)) {
			double val = playerMana.get(p);
			val -= d;
			if (val < 0)
				val = 0;
			playerMana.put(p, val);
			updateLevelBar(p);
		}

	}

	public static boolean isInventoryEmpty(Inventory inv) {
		for (ItemStack item : inv.getContents()) {
			if (item != null)
				return false;
		}
		return true;
	}

	public static void pickPocket(Player player, Player target) {
		Inventory inv = plugin.getServer().createInventory(null, 9, "Tasche von " + target.getName());
		if (!robbedPlayers.contains(target)) {
			inv.addItem(new ItemStack(266));
			inv.addItem(new ItemStack(265));
			robbedPlayers.add(target);
		}
		player.openInventory(inv);
	}

	public static ItemStack getRandomMonsterDrop(EntityType e) {
		ItemStack item = null;
		int rnd = new Random().nextInt(50);

		if (Math.random() < 0.2 && !(e == EntityType.WITHER || e == EntityType.WITHER_SKULL)) {
			return null;
		}

		if (rnd == 1) {
			item = new ItemStack(Material.DIAMOND, 1);
		} else if (rnd == 2) {
			if (Math.random() > 0.05) {
				return null;
			}
			double rnd2 = Math.random();
			if (rnd2 > 0.9) {
				item = new ItemStack(Material.DIAMOND_CHESTPLATE);
				ItemMeta meta = item.getItemMeta();
				meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
				meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, true);
				meta.addEnchant(Enchantment.PROTECTION_FALL, 4, true);
				meta.addEnchant(Enchantment.PROTECTION_FIRE, 4, true);
				meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 4, true);
				meta.addEnchant(Enchantment.THORNS, 3, true);
				meta.addEnchant(Enchantment.DURABILITY, 3, true);
				ArrayList<String> lore = new ArrayList<String>();
				lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.RED + ChatColor.ITALIC + "Legendaer");
				meta.setLore(lore);
				meta.setDisplayName(ChatColor.RED + "Legendaerer Harnisch des Zorns");
				item.setItemMeta(meta);
			} else if (rnd2 > 0.75) {
				item = new ItemStack(Material.DIAMOND_HELMET);
				ItemMeta meta = item.getItemMeta();
				meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
				meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, true);
				meta.addEnchant(Enchantment.PROTECTION_FALL, 4, true);
				meta.addEnchant(Enchantment.PROTECTION_FIRE, 4, true);
				meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 4, true);
				meta.addEnchant(Enchantment.THORNS, 3, true);
				meta.addEnchant(Enchantment.DURABILITY, 3, true);
				ArrayList<String> lore = new ArrayList<String>();
				lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.RED + ChatColor.ITALIC + "Legendaer");
				meta.setLore(lore);
				meta.setDisplayName(ChatColor.RED + "Legendaerer Helm des Zorns");
				item.setItemMeta(meta);
			} else if (rnd2 > 0.6) {
				item = new ItemStack(Material.DIAMOND_BOOTS);
				ItemMeta meta = item.getItemMeta();
				meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
				meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, true);
				meta.addEnchant(Enchantment.PROTECTION_FALL, 4, true);
				meta.addEnchant(Enchantment.PROTECTION_FIRE, 4, true);
				meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 4, true);
				meta.addEnchant(Enchantment.THORNS, 3, true);
				meta.addEnchant(Enchantment.DURABILITY, 3, true);
				ArrayList<String> lore = new ArrayList<String>();
				lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.RED + ChatColor.ITALIC + "Legendaer");
				meta.setLore(lore);
				meta.setDisplayName(ChatColor.RED + "Legendaere Schuhe des Zorns");
				item.setItemMeta(meta);
			} else if (rnd2 > 0.4) {
				item = new ItemStack(Material.DIAMOND_LEGGINGS);
				ItemMeta meta = item.getItemMeta();
				meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
				meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, true);
				meta.addEnchant(Enchantment.PROTECTION_FALL, 4, true);
				meta.addEnchant(Enchantment.PROTECTION_FIRE, 4, true);
				meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 4, true);
				meta.addEnchant(Enchantment.DURABILITY, 3, true);
				meta.addEnchant(Enchantment.THORNS, 3, true);
				ArrayList<String> lore = new ArrayList<String>();
				lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.RED + ChatColor.ITALIC + "Legendaer");
				meta.setLore(lore);
				meta.setDisplayName(ChatColor.RED + "Legendaere Hose des Zorns");
				item.setItemMeta(meta);
			} else if (rnd2 > 0.2) {
				item = new ItemStack(Material.BOW);
				ItemMeta meta = item.getItemMeta();
				meta.addEnchant(Enchantment.ARROW_DAMAGE, 5, true);
				meta.addEnchant(Enchantment.ARROW_KNOCKBACK, 2, true);
				meta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
				meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
				meta.addEnchant(Enchantment.DURABILITY, 1, true);
				ArrayList<String> lore = new ArrayList<String>();
				lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.RED + ChatColor.ITALIC + "Legendaer");
				meta.setLore(lore);
				meta.setDisplayName(ChatColor.RED + "Legendaerer Bogen des Zorns");
				item.setItemMeta(meta);
			} else if (rnd2 > 0.1) {
				item = new ItemStack(Material.DIAMOND_SWORD);
				ItemMeta meta = item.getItemMeta();
				meta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
				meta.addEnchant(Enchantment.DAMAGE_UNDEAD, 5, true);
				meta.addEnchant(Enchantment.DAMAGE_ARTHROPODS, 5, true);
				meta.addEnchant(Enchantment.KNOCKBACK, 2, true);
				meta.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
				meta.addEnchant(Enchantment.DURABILITY, 3, true);
				ArrayList<String> lore = new ArrayList<String>();
				lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.RED + ChatColor.ITALIC + "Legendaer");
				meta.setLore(lore);
				meta.setDisplayName(ChatColor.RED + "Legendaeres Schwert des Zorns");
				item.setItemMeta(meta);
			}
		} else if (rnd == 3) {
			if (Math.random() > 0.1) {
				return null;
			}
			double rnd2 = Math.random();
			if (rnd2 > 0.9) {
				item = new ItemStack(Math.random() > 0.5 ? Material.DIAMOND_CHESTPLATE : Material.IRON_CHESTPLATE);
				ItemMeta meta = item.getItemMeta();
				meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
				meta.addEnchant(Enchantment.PROTECTION_FALL, 2, true);
				meta.addEnchant(Enchantment.PROTECTION_FIRE, 2, true);
				meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 3, true);
				ArrayList<String> lore = new ArrayList<String>();
				lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_PURPLE + ChatColor.ITALIC + "Heroisch");
				meta.setLore(lore);
				meta.setDisplayName(ChatColor.DARK_PURPLE + "Heroischer Harnisch des Helden");
				item.setItemMeta(meta);
			} else if (rnd2 > 0.7) {
				item = new ItemStack(Math.random() > 0.5 ? Material.DIAMOND_HELMET : Material.IRON_HELMET);
				ItemMeta meta = item.getItemMeta();
				meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
				meta.addEnchant(Enchantment.PROTECTION_FALL, 2, true);
				meta.addEnchant(Enchantment.PROTECTION_FIRE, 2, true);
				meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 3, true);
				ArrayList<String> lore = new ArrayList<String>();
				lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_PURPLE + ChatColor.ITALIC + "Heroisch");
				meta.setLore(lore);
				meta.setDisplayName(ChatColor.DARK_PURPLE + "Heroischer Helm des Helden");
				item.setItemMeta(meta);
			} else if (rnd2 > 0.5) {
				item = new ItemStack(Math.random() > 0.5 ? Material.DIAMOND_BOOTS : Material.IRON_BOOTS);
				ItemMeta meta = item.getItemMeta();
				meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
				meta.addEnchant(Enchantment.PROTECTION_FALL, 2, true);
				meta.addEnchant(Enchantment.PROTECTION_FIRE, 2, true);
				meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 3, true);
				ArrayList<String> lore = new ArrayList<String>();
				lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_PURPLE + ChatColor.ITALIC + "Heroisch");
				meta.setLore(lore);
				meta.setDisplayName(ChatColor.DARK_PURPLE + "Heroische Schuhe des Helden");
				item.setItemMeta(meta);
			} else if (rnd2 > 0.3) {
				item = new ItemStack(Math.random() > 0.5 ? Material.DIAMOND_LEGGINGS : Material.IRON_LEGGINGS);
				ItemMeta meta = item.getItemMeta();
				meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
				meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, true);
				meta.addEnchant(Enchantment.PROTECTION_FALL, 2, true);
				meta.addEnchant(Enchantment.PROTECTION_FIRE, 2, true);
				meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 2, true);
				meta.addEnchant(Enchantment.THORNS, 1, true);
				ArrayList<String> lore = new ArrayList<String>();
				lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_PURPLE + ChatColor.ITALIC + "Heroisch");
				meta.setLore(lore);
				meta.setDisplayName(ChatColor.DARK_PURPLE + "Heroische Hose des Helden");
				item.setItemMeta(meta);
			} else if (rnd2 > 0.1) {
				item = new ItemStack(Material.DIAMOND_SWORD);
				ItemMeta meta = item.getItemMeta();
				meta.addEnchant(Enchantment.DAMAGE_ALL, 4, true);
				meta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
				ArrayList<String> lore = new ArrayList<String>();
				lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_PURPLE + ChatColor.ITALIC + "Heroisch");
				meta.setLore(lore);
				meta.setDisplayName(ChatColor.DARK_PURPLE + "Heroisches Schwert des Helden");
				item.setItemMeta(meta);
			} else {
				item = new ItemStack(Material.BOW);
				ItemMeta meta = item.getItemMeta();
				meta.addEnchant(Enchantment.ARROW_DAMAGE, 5, true);
				meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
				meta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
				meta.addEnchant(Enchantment.ARROW_KNOCKBACK, 2, true);
				ArrayList<String> lore = new ArrayList<String>();
				lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_PURPLE + ChatColor.ITALIC + "Heroisch");
				meta.setLore(lore);
				meta.setDisplayName(ChatColor.DARK_PURPLE + "Heroischer Bogen des Helden");
				item.setItemMeta(meta);
			}
		} else if (rnd == 4) {
			item = new ItemStack(Material.GOLD_INGOT, 1);
		} else if (rnd == 5) {
			item = new ItemStack(Math.random() > 0.7 ? Material.LEATHER_HELMET : Material.CHAINMAIL_HELMET, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GREEN + "Seltener Helm des Kampfes");
			meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
			meta.addEnchant(Enchantment.PROTECTION_FIRE, 1, true);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.GREEN + ChatColor.ITALIC + "Selten");
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else if (rnd == 6) {
			item = new ItemStack(Math.random() > 0.7 ? Material.LEATHER_CHESTPLATE : Material.CHAINMAIL_CHESTPLATE, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GREEN + "Seltener Harnisch des Kampfes");
			meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
			meta.addEnchant(Enchantment.PROTECTION_FIRE, 1, true);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.GREEN + ChatColor.ITALIC + "Selten");
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else if (rnd == 7) {
			item = new ItemStack(Math.random() > 0.7 ? Material.LEATHER_LEGGINGS : Material.CHAINMAIL_LEGGINGS, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GREEN + "Seltene Hose des Kampfes");
			meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
			meta.addEnchant(Enchantment.PROTECTION_FIRE, 1, true);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.GREEN + ChatColor.ITALIC + "Selten");
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else if (rnd == 8) {
			item = new ItemStack(Math.random() > 0.7 ? Material.LEATHER_BOOTS : Material.CHAINMAIL_BOOTS, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GREEN + "Seltene Schuhe des Kampfes");
			meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
			meta.addEnchant(Enchantment.PROTECTION_FIRE, 1, true);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.GREEN + ChatColor.ITALIC + "Selten");
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else if (rnd == 9 && Math.random() > 0.8) {
			item = new ItemStack(Math.random() > 0.5 ? Material.IRON_HELMET : Material.CHAINMAIL_HELMET, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.DARK_GREEN + "Sehr Seltener Helm des Krieges");
			meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
			meta.addEnchant(Enchantment.PROTECTION_FIRE, 2, true);
			meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 1, true);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_GREEN + ChatColor.ITALIC + "Sehr Selten");
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else if (rnd == 10 && Math.random() > 0.8) {
			item = new ItemStack(Math.random() > 0.5 ? Material.IRON_CHESTPLATE : Material.CHAINMAIL_CHESTPLATE, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.DARK_GREEN + "Sehr Seltener Harnisch des Krieges");
			meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
			meta.addEnchant(Enchantment.PROTECTION_FIRE, 2, true);
			meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 1, true);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_GREEN + ChatColor.ITALIC + "Sehr Selten");
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else if (rnd == 11 && Math.random() > 0.8) {
			item = new ItemStack(Math.random() > 0.5 ? Material.IRON_LEGGINGS : Material.CHAINMAIL_LEGGINGS, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.DARK_GREEN + "Sehr Seltene Hose des Krieges");
			meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
			meta.addEnchant(Enchantment.PROTECTION_FIRE, 2, true);
			meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 1, true);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_GREEN + ChatColor.ITALIC + "Sehr Selten");
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else if (rnd == 12 && Math.random() > 0.8) {
			item = new ItemStack(Math.random() > 0.5 ? Material.IRON_BOOTS : Material.CHAINMAIL_BOOTS, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.DARK_GREEN + "Sehr Seltene Schuhe des Krieges");
			meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
			meta.addEnchant(Enchantment.PROTECTION_FIRE, 2, true);
			meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 1, true);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_GREEN + ChatColor.ITALIC + "Sehr Selten");
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else if (rnd == 13 && Math.random() > 0.8) {
			item = new ItemStack(Math.random() > 0.5 ? Material.STONE_SWORD : Material.IRON_SWORD, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.DARK_GREEN + "Sehr Seltenes Schwert des Krieges");
			meta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
			meta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_GREEN + ChatColor.ITALIC + "Sehr Selten");
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else if (rnd == 14 && Math.random() > 0.9) {
			item = new ItemStack(Math.random() > 0.3 ? Material.IRON_SWORD : Material.DIAMOND_SWORD, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.DARK_AQUA + "Extremes Schwert des Leids");
			meta.addEnchant(Enchantment.DAMAGE_ALL, 3, true);
			meta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
			meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_AQUA + ChatColor.ITALIC + "Extrem");
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else if (rnd == 15 && Math.random() > 0.9) {
			item = new ItemStack(Material.BOW, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.DARK_AQUA + "Extremer Bogen des Leids");
			meta.addEnchant(Enchantment.ARROW_DAMAGE, 4, true);
			meta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
			meta.addEnchant(Enchantment.KNOCKBACK, 2, true);
			meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_AQUA + ChatColor.ITALIC + "Extrem");
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else if (rnd == 16) {
			if (Math.random() > 0.5) {
				item = getItemCubiCoupon();
			}
		} else if (rnd == 17) {
			if (Math.random() > 0.9) {
				item = new ItemStack(Material.BLAZE_ROD, 1);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(ChatColor.DARK_AQUA + "Extremer Stab des Leids");
				meta.addEnchant(Enchantment.DAMAGE_ALL, 3, true);
				ArrayList<String> lore = new ArrayList<String>();
				lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_AQUA + ChatColor.ITALIC + "Extrem");
				meta.setLore(lore);
				item.setItemMeta(meta);
			}
		} else if (rnd == 18) {
			item = new ItemStack(Material.BLAZE_ROD, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GREEN + "Seltener Stab des Kampfes");
			meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.GREEN + ChatColor.ITALIC + "Selten");
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else if (rnd == 19) {
			item = new ItemStack(Material.BLAZE_ROD, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.DARK_GREEN + "Sehr Seltener Stab des Krieges");
			meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
			meta.addEnchant(Enchantment.DURABILITY, 1, true);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_GREEN + ChatColor.ITALIC + "Sehr Selten");
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else if (rnd == 20) {
			if (Math.random() > 0.97) {
				item = new ItemStack(Material.BLAZE_ROD, 1);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(ChatColor.RED + "Legendaerer Stab des Kampfes");
				meta.addEnchant(Enchantment.DAMAGE_ALL, 6, true);
				meta.addEnchant(Enchantment.DURABILITY, 3, true);
				ArrayList<String> lore = new ArrayList<String>();
				lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.RED + ChatColor.ITALIC + "Legendaer");
				meta.setLore(lore);
				item.setItemMeta(meta);
			} else if (Math.random() > 0.8) {
				item = new ItemStack(Material.BLAZE_ROD, 1);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(ChatColor.DARK_PURPLE + "Heroischer Stab des Kampfes");
				meta.addEnchant(Enchantment.DAMAGE_ALL, 4, true);
				meta.addEnchant(Enchantment.DURABILITY, 2, true);
				ArrayList<String> lore = new ArrayList<String>();
				lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_PURPLE + ChatColor.ITALIC + "Heroisch");
				meta.setLore(lore);
				item.setItemMeta(meta);
			}
		} else if (rnd == 21 && Math.random() > 0.9) {
			item = new ItemStack(Math.random() > 0.4 ? Material.IRON_BOOTS : Material.DIAMOND_BOOTS, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.DARK_AQUA + "Extreme Schuhe des Leids");
			meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
			meta.addEnchant(Enchantment.PROTECTION_FIRE, 2, true);
			meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 2, true);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_AQUA + ChatColor.ITALIC + "Extrem");
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else if (rnd == 22 && Math.random() > 0.9) {
			item = new ItemStack(Math.random() > 0.4 ? Material.IRON_LEGGINGS : Material.DIAMOND_LEGGINGS, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.DARK_AQUA + "Extreme Hose des Leids");
			meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
			meta.addEnchant(Enchantment.PROTECTION_FIRE, 2, true);
			meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 2, true);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_AQUA + ChatColor.ITALIC + "Extrem");
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else if (rnd == 23 && Math.random() > 0.9) {
			item = new ItemStack(Math.random() > 0.4 ? Material.IRON_CHESTPLATE : Material.DIAMOND_CHESTPLATE, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.DARK_AQUA + "Extremer Harnisch des Leids");
			meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
			meta.addEnchant(Enchantment.PROTECTION_FIRE, 2, true);
			meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 2, true);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_AQUA + ChatColor.ITALIC + "Extrem");
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else if (rnd == 24 && Math.random() > 0.9) {
			item = new ItemStack(Math.random() > 0.4 ? Material.IRON_HELMET : Material.DIAMOND_HELMET, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.DARK_AQUA + "Extremer Helm des Leids");
			meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
			meta.addEnchant(Enchantment.PROTECTION_FIRE, 2, true);
			meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 2, true);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_AQUA + ChatColor.ITALIC + "Extrem");
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else if (rnd == 25) {
			item = new ItemStack(Math.random() < 0.9 ? Material.LEATHER_BOOTS : Material.CHAINMAIL_BOOTS, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.YELLOW + "Gute Schuhe des Anfaengers");
			meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 1, true);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else if (rnd == 26) {
			item = new ItemStack(Math.random() < 0.9 ? Material.LEATHER_LEGGINGS : Material.CHAINMAIL_LEGGINGS, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.YELLOW + "Gute Hose des Anfaengers");
			meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 1, true);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else if (rnd == 27) {
			item = new ItemStack(Math.random() < 0.9 ? Material.LEATHER_CHESTPLATE : Material.CHAINMAIL_CHESTPLATE, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.YELLOW + "Guter Harnisch des Anfaengers");
			meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 1, true);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else if (rnd == 28) {
			item = new ItemStack(Math.random() < 0.9 ? Material.LEATHER_HELMET : Material.CHAINMAIL_HELMET, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.YELLOW + "Guter Helm des Anfaengers");
			meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 1, true);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else if (rnd == 29 && Math.random() > 0.5) {
			item = new ItemStack(Math.random() < 0.7 ? Material.LEATHER_HELMET : Material.CHAINMAIL_HELMET, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "Sehr Guter Helm des Anfaengers");
			meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.GOLD + ChatColor.ITALIC + "Sehr Gut");
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else if (rnd == 30 && Math.random() > 0.5) {
			item = new ItemStack(Math.random() < 0.7 ? Material.LEATHER_CHESTPLATE : Material.CHAINMAIL_CHESTPLATE, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "Sehr Guter Harnisch des Anfaengers");
			meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.GOLD + ChatColor.ITALIC + "Sehr Gut");
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else if (rnd == 31 && Math.random() > 0.5) {
			item = new ItemStack(Math.random() < 0.7 ? Material.LEATHER_LEGGINGS : Material.CHAINMAIL_LEGGINGS, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "Sehr Gute Hose des Anfaengers");
			meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.GOLD + ChatColor.ITALIC + "Sehr Gut");
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else if (rnd == 32 && Math.random() > 0.5) {
			item = new ItemStack(Math.random() < 0.7 ? Material.LEATHER_BOOTS : Material.CHAINMAIL_BOOTS, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "Sehr Gute Schuhe des Anfaengers");
			meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.GOLD + ChatColor.ITALIC + "Sehr Gut");
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else if (rnd == 33) {
			item = new ItemStack(Material.EMERALD);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GREEN + "Gruppenstein der Heilung");
			meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add("");
			lore.add("§eRechtsklick, um Verbuendete in der Naehe zu heilen.");
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else if (rnd == 34) {
			item = new ItemStack(Material.EMERALD);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GREEN + "Gruppenstein der Unsichtbarkeit");
			meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add("");
			lore.add("§eRechtsklick, um Verbuendete in der Naehe zu fuer 1 Minute unsichtbar zu machen.");
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else if (rnd == 35) {
			item = new ItemStack(Material.WOOD_SWORD, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.YELLOW + "Gutes Schwert des Anfaengers");
			meta.addEnchant(Enchantment.DAMAGE_UNDEAD, 1, true);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else if (rnd == 36) {
			item = new ItemStack(Material.STONE_SWORD, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.YELLOW + "Gutes Schwert des Anfaengers");
			meta.addEnchant(Enchantment.DAMAGE_UNDEAD, 1, true);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else if (rnd == 37) {
			item = new ItemStack(Material.STONE_SWORD, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "Sehr Gutes Schwert des Anfaengers");
			meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.GOLD + ChatColor.ITALIC + "Sehr Gut");
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else if (rnd == 38) {
			item = new ItemStack(Material.STONE_SWORD, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "Sehr Gutes Schwert des Anfaengers");
			meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.GOLD + ChatColor.ITALIC + "Sehr Gut");
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else if (rnd == 39) {
			item = specItem(Material.BOW, ChatColor.YELLOW + "Guter Bogen des Anfaengers", Enchantment.ARROW_KNOCKBACK, 1, ChatColor.YELLOW + ""
					+ ChatColor.ITALIC + "Gut");
		} else if (rnd == 40) {
			item = specItem(Material.BOW, ChatColor.GOLD + "Sehr Guter Bogen des Anfaengers", Enchantment.ARROW_DAMAGE, 1, ChatColor.GOLD + ""
					+ ChatColor.ITALIC + "Sehr Gut");
		} else if (rnd == 41) {
			item = specItem(Material.SHEARS, ChatColor.YELLOW + "Gute Klinge des Anfaengers", Enchantment.DAMAGE_ALL, 1, ChatColor.YELLOW + ""
					+ ChatColor.ITALIC + "Gut");
		} else if (rnd == 42) {
			item = specItem(Material.SHEARS, ChatColor.YELLOW + "Gute Klinge des Anfaengers", Enchantment.DAMAGE_ALL, 1, ChatColor.YELLOW + ""
					+ ChatColor.ITALIC + "Gut");
		} else if (rnd == 43) {
			item = specItem(Material.SHEARS, ChatColor.GOLD + "Sehr Gute Klinge des Anfaengers", Enchantment.DAMAGE_ALL, 2, ChatColor.GOLD + ""
					+ ChatColor.ITALIC + "Sehr Gut");
		} else if (rnd == 44 && Math.random() > 0.4) {
			item = specItem(Material.SHEARS, ChatColor.DARK_AQUA + "Extreme Klinge des Schurken", Enchantment.DAMAGE_ALL, 2, ChatColor.DARK_AQUA + ""
					+ ChatColor.ITALIC + "Extrem");
			item.getItemMeta().addEnchant(Enchantment.DAMAGE_UNDEAD, 3, true);
		} else if (rnd == 45 && Math.random() > 0.8) {
			item = specItem(Material.SHEARS, ChatColor.DARK_PURPLE + "Heroische Klinge des Schurken", Enchantment.DAMAGE_ALL, 4, ChatColor.DARK_PURPLE + ""
					+ ChatColor.ITALIC + "Heroisch");
			item.getItemMeta().addEnchant(Enchantment.DAMAGE_UNDEAD, 3, true);
			item.getItemMeta().addEnchant(Enchantment.FIRE_ASPECT, 1, true);
		} else if (rnd == 46 && Math.random() > 0.95) {
			item = specItem(Material.SHEARS, ChatColor.RED + "Legendaere Klinge des Schurken", Enchantment.DAMAGE_ALL, 5, ChatColor.RED + ""
					+ ChatColor.ITALIC + "Legendaer");
			item.getItemMeta().addEnchant(Enchantment.DAMAGE_UNDEAD, 4, true);
			item.getItemMeta().addEnchant(Enchantment.FIRE_ASPECT, 2, true);
			item.getItemMeta().addEnchant(Enchantment.KNOCKBACK, 1, true);
		}

		// TODO: Add More Items

		if (item != null) {
			if (item.getItemMeta().getLore() != null) {
				List<String> lore = new ArrayList<String>();
				lore.add(" ");
				lore.addAll(item.getItemMeta().getLore());
				lore.add(" ");
				lore.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
				ItemMeta meta = item.getItemMeta();
				meta.setLore(lore);
				item.setItemMeta(meta);
			}
		}

		return item;
	}

	public static ItemStack specItem(Material material, String name, Enchantment ench, int enchLvl, String stufe) {
		ItemStack is = new ItemStack(material, 1);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(name);
		meta.addEnchant(ench, enchLvl, true);
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.WHITE + "Stufe: " + stufe);
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}

	public static boolean containsBadWords(String s) {

		String[] badwords = { "fick", "fuck", "fresse", "pimmel", "penis", "arsch", "hurensohn", "fotze", "bitch", "vagina", "hure", "bastart",
				"muschi", "trottel", "schlampe", "scheiss", "wichser", "wixer", "titte", "missgeburt", "sex" };

		for (String word : badwords) {
			if (s.toLowerCase().contains(word)) {
				return true;
			}
		}

		return false;
	}

	public static void chooseFaction(Player p) {
		Inventory inv = plugin.getServer().createInventory(null, 9, "Waehle ein Volk:");

		ItemStack isGrass = new ItemStack(Material.GRASS, 1);
		ItemMeta amGrass = (ItemMeta) isGrass.getItemMeta();
		amGrass.setDisplayName(ChatColor.GREEN + "" + ChatColor.UNDERLINE + "Solaner");
		isGrass.setItemMeta(amGrass);
		inv.setItem(2, isGrass);

		ItemStack isSand = new ItemStack(Material.SAND, 1);
		ItemMeta amSand = (ItemMeta) isSand.getItemMeta();
		amSand.setDisplayName(ChatColor.GOLD + "" + ChatColor.UNDERLINE + "Pyrer");
		isSand.setItemMeta(amSand);
		inv.setItem(4, isSand);

		ItemStack isSnow = new ItemStack(Material.SNOW_BLOCK, 1);
		ItemMeta amSnow = (ItemMeta) isSnow.getItemMeta();
		amSnow.setDisplayName(ChatColor.DARK_AQUA + "" + ChatColor.UNDERLINE + "Arctiker");
		isSnow.setItemMeta(amSnow);
		inv.setItem(6, isSnow);

		p.openInventory(inv);
	}

	public static void chooseClass(Player p) {
		Inventory inv = plugin.getServer().createInventory(null, 9, "Waehle eine Klasse:");

		ItemStack isKrieger = new ItemStack(Material.IRON_SWORD, 1);
		ItemMeta amKrieger = (ItemMeta) isKrieger.getItemMeta();
		amKrieger.setDisplayName(ChatColor.GOLD + "" + ChatColor.UNDERLINE + "Krieger");
		ArrayList<String> descKrieger = new ArrayList<String>();
		descKrieger.add(ChatColor.RED + "Der Krieger bezwingt seine Gegner im Nahkampf.");
		descKrieger.add(ChatColor.AQUA + "Ausruestung: Eisenrüstung, Eisenschwert, Eisenaxt");
		amKrieger.setLore(descKrieger);
		isKrieger.setItemMeta(amKrieger);
		inv.setItem(2, isKrieger);

		ItemStack isMagier = new ItemStack(Material.BLAZE_ROD, 1);
		ItemMeta amMagier = (ItemMeta) isMagier.getItemMeta();
		amMagier.setDisplayName(ChatColor.GOLD + "" + ChatColor.UNDERLINE + "Magier");
		ArrayList<String> descMagier = new ArrayList<String>();
		descMagier.add(ChatColor.RED + "Der Magier besiegt seine Gegner");
		descMagier.add(ChatColor.RED + "mit verschiedenen Zaubern.");
		descMagier.add(ChatColor.AQUA + "Ausruestung: Lederruestung, Feuerstab");
		amMagier.setLore(descMagier);
		isMagier.setItemMeta(amMagier);
		inv.setItem(3, isMagier);

		ItemStack isSchurke = new ItemStack(Material.SHEARS, 1);
		ItemMeta amSchurke = (ItemMeta) isSchurke.getItemMeta();
		amSchurke.setDisplayName(ChatColor.GOLD + "" + ChatColor.UNDERLINE + "Schurke");
		ArrayList<String> descSchurke = new ArrayList<String>();
		descSchurke.add(ChatColor.RED + "Der Schurke besiegt Gegner hinterhaeltig");
		descSchurke.add(ChatColor.RED + "mit Gift und Unsichtbarkeit.");
		descSchurke.add(ChatColor.AQUA + "Ausruestung: Goldschwert, Klinge");
		amSchurke.setLore(descSchurke);
		isSchurke.setItemMeta(amSchurke);
		inv.setItem(4, isSchurke);

		ItemStack isJaeger = new ItemStack(Material.BOW, 1);
		ItemMeta amJaeger = (ItemMeta) isJaeger.getItemMeta();
		amJaeger.setDisplayName(ChatColor.GOLD + "" + ChatColor.UNDERLINE + "Jaeger");
		ArrayList<String> descJaeger = new ArrayList<String>();
		descJaeger.add(ChatColor.RED + "Der Jaeger dominiert Gegner aus");
		descJaeger.add(ChatColor.RED + "der Ferne mit Pfeilen.");
		descJaeger.add(ChatColor.AQUA + "Ausruestung: Lederruestung, Bogen, Steinschwert");
		amJaeger.setLore(descJaeger);
		isJaeger.setItemMeta(amJaeger);
		inv.setItem(5, isJaeger);

		ItemStack isPriester = new ItemStack(Material.STICK, 1);
		ItemMeta amPriester = (ItemMeta) isPriester.getItemMeta();
		amPriester.setDisplayName(ChatColor.GOLD + "" + ChatColor.UNDERLINE + "Priester");
		ArrayList<String> descPriester = new ArrayList<String>();
		descPriester.add(ChatColor.RED + "Der Priester heilt sich und Verbuendete und");
		descPriester.add(ChatColor.RED + "wirkt Schadenszauber auf Gegner.");
		descPriester.add(ChatColor.AQUA + "Ausruestung: Goldruestung, Heilstab, Schadensstab");
		amPriester.setLore(descPriester);
		isPriester.setItemMeta(amPriester);
		inv.setItem(6, isPriester);

		p.openInventory(inv);
	}

	public static void setStdArmor(Player p) {
		PlayerInventory i = p.getInventory();
		p.getInventory().clear();

		if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Knappe")) {

			ItemStack is5 = new ItemStack(Material.STONE_SWORD, 1);
			ItemMeta am5 = is5.getItemMeta();
			am5.setDisplayName(ChatColor.YELLOW + "Gutes Schwert des Knappen");
			ArrayList<String> lore5 = new ArrayList<String>();
			lore5.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			lore5.add(" ");
			lore5.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
			am5.setLore(lore5);
			is5.setItemMeta(am5);

			i.addItem(is5);

			ItemStack is = new ItemStack(Material.LEATHER_HELMET, 1);
			ItemMeta im = is.getItemMeta();
			LeatherArmorMeta am = (LeatherArmorMeta) im;
			am.setColor(Color.YELLOW);
			am.setDisplayName(ChatColor.YELLOW + "Guter Lederhelm des Knappen");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			lore.add(" ");
			lore.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
			am.setLore(lore);
			is.setItemMeta(am);

			ItemStack is2 = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
			ItemMeta im2 = is2.getItemMeta();
			LeatherArmorMeta am2 = (LeatherArmorMeta) im2;
			am2.setColor(Color.YELLOW);
			am2.setDisplayName(ChatColor.YELLOW + "Guter Lederharnisch des Knappen");
			ArrayList<String> lore2 = new ArrayList<String>();
			lore2.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			lore2.add(" ");
			lore2.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
			am2.setLore(lore2);
			is2.setItemMeta(am2);

			ItemStack is3 = new ItemStack(Material.LEATHER_LEGGINGS, 1);
			ItemMeta im3 = is3.getItemMeta();
			LeatherArmorMeta am3 = (LeatherArmorMeta) im3;
			am3.setColor(Color.YELLOW);
			am3.setDisplayName(ChatColor.YELLOW + "Gute Lederhose des Knappen");
			ArrayList<String> lore3 = new ArrayList<String>();
			lore3.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			lore3.add(" ");
			lore3.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
			am3.setLore(lore3);
			is3.setItemMeta(am3);

			ItemStack is4 = new ItemStack(Material.LEATHER_BOOTS, 1);
			ItemMeta im4 = is4.getItemMeta();
			LeatherArmorMeta am4 = (LeatherArmorMeta) im4;
			am4.setColor(Color.YELLOW);
			am4.setDisplayName(ChatColor.YELLOW + "Gute Lederschuhe des Knappen");
			ArrayList<String> lore4 = new ArrayList<String>();
			lore4.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			lore4.add(" ");
			lore4.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
			am4.setLore(lore4);
			is4.setItemMeta(am4);

			i.setHelmet(is);
			i.setChestplate(is2);
			i.setLeggings(is3);
			i.setBoots(is4);

		} else if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Schurke")) {
			// NO ARMOR (Invisibility)

			i.setHelmet(null);
			i.setChestplate(null);
			i.setLeggings(null);
			i.setBoots(null);
			i.setArmorContents(new ItemStack[] { new ItemStack(Material.AIR, 0) });

			ItemStack is5 = new ItemStack(Material.GOLD_SWORD, 1);
			ItemMeta am5 = is5.getItemMeta();
			am5.setDisplayName(ChatColor.YELLOW + "Gutes Schwert des Schurken");
			ArrayList<String> lore5 = new ArrayList<String>();
			lore5.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			lore5.add(" ");
			lore5.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
			am5.setLore(lore5);
			is5.setItemMeta(am5);
			is5.addEnchantment(Enchantment.DAMAGE_ALL, 1);
			is5.addEnchantment(Enchantment.DURABILITY, 2);

			ItemStack is6 = new ItemStack(Material.SHEARS, 1);
			ItemMeta am6 = is6.getItemMeta();
			am6.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
			am6.setDisplayName(ChatColor.YELLOW + "Gute Giftklinge des Schurken");
			ArrayList<String> lore6 = new ArrayList<String>();
			lore6.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			lore6.add(" ");
			lore6.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
			am6.setLore(lore6);
			is6.setItemMeta(am6);

			ItemStack is7 = new ItemStack(Material.SULPHUR, 1);
			ItemMeta am7 = is7.getItemMeta();
			am7.setDisplayName(ChatColor.YELLOW + "Guter Staub des Schurken");
			ArrayList<String> lore7 = new ArrayList<String>();
			lore7.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			lore7.add(" ");
			lore7.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
			am7.setLore(lore7);
			is7.setItemMeta(am7);

			i.addItem(is5, is6, is7);

		} else if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Magier")) {

			ItemStack is5 = new ItemStack(Material.BLAZE_ROD, 1);
			ItemMeta am5 = is5.getItemMeta();
			am5.setDisplayName(ChatColor.YELLOW + "Guter Stab des Magiers");
			ArrayList<String> lore5 = new ArrayList<String>();
			lore5.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			lore5.add(" ");
			lore5.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
			am5.setLore(lore5);
			is5.setItemMeta(am5);

			i.addItem(is5);

			ItemStack is = new ItemStack(Material.LEATHER_HELMET, 1);
			ItemMeta im = is.getItemMeta();
			LeatherArmorMeta am = (LeatherArmorMeta) im;
			am.setColor(Color.SILVER);
			am.setDisplayName(ChatColor.YELLOW + "Lederhelm des Magiers");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			lore.add(" ");
			lore.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
			am.setLore(lore);
			is.setItemMeta(am);

			ItemStack is2 = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
			ItemMeta im2 = is2.getItemMeta();
			LeatherArmorMeta am2 = (LeatherArmorMeta) im2;
			am2.setColor(Color.SILVER);
			am2.setDisplayName(ChatColor.YELLOW + "Lederharnisch des Magiers");
			ArrayList<String> lore2 = new ArrayList<String>();
			lore2.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			lore2.add(" ");
			lore2.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
			am2.setLore(lore2);
			is2.setItemMeta(am2);

			ItemStack is3 = new ItemStack(Material.LEATHER_LEGGINGS, 1);
			ItemMeta im3 = is3.getItemMeta();
			LeatherArmorMeta am3 = (LeatherArmorMeta) im3;
			am3.setColor(Color.SILVER);
			am3.setDisplayName(ChatColor.YELLOW + "Lederhose des Magiers");
			ArrayList<String> lore3 = new ArrayList<String>();
			lore3.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			lore3.add(" ");
			lore3.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
			am3.setLore(lore3);
			is3.setItemMeta(am3);

			ItemStack is4 = new ItemStack(Material.LEATHER_BOOTS, 1);
			ItemMeta im4 = is4.getItemMeta();
			LeatherArmorMeta am4 = (LeatherArmorMeta) im4;
			am4.setColor(Color.SILVER);
			am4.setDisplayName(ChatColor.YELLOW + "Lederschuhe des Magiers");
			ArrayList<String> lore4 = new ArrayList<String>();
			lore4.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			lore4.add(" ");
			lore4.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
			am4.setLore(lore4);
			is4.setItemMeta(am4);

			i.setHelmet(is);
			i.setChestplate(is2);
			i.setLeggings(is3);
			i.setBoots(is4);

		} else if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Jaeger")) {

			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1200, 1));

			ItemStack is5 = new ItemStack(Material.STONE_SWORD, 1);
			ItemMeta am5 = is5.getItemMeta();
			am5.setDisplayName(ChatColor.YELLOW + "Gutes Schwert des Jaegers");
			is5.setItemMeta(am5);
			ItemStack is6 = new ItemStack(Material.BOW, 1);
			ItemMeta am6 = is6.getItemMeta();
			am6.setDisplayName(ChatColor.YELLOW + "Guter Bogen des Jaegers");
			am6.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
			is6.setItemMeta(am6);
			ItemStack is7 = new ItemStack(Material.ARROW, 64);
			ItemMeta am7 = is7.getItemMeta();
			am7.setDisplayName(ChatColor.YELLOW + "Guter Pfeil des Jaegers");
			ArrayList<String> lore7 = new ArrayList<String>();
			lore7.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			lore7.add(" ");
			lore7.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
			am6.setLore(lore7);
			is7.setItemMeta(am7);

			i.addItem(is5, is6, is7);

			ItemStack is = new ItemStack(Material.LEATHER_HELMET, 1);
			ItemMeta im = is.getItemMeta();
			LeatherArmorMeta am = (LeatherArmorMeta) im;
			am.setColor(Color.BLUE);
			am.setDisplayName(ChatColor.YELLOW + "Lederhelm des Jaegers");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			lore.add(" ");
			lore.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
			am.setLore(lore);
			is.setItemMeta(am);

			ItemStack is2 = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
			ItemMeta im2 = is2.getItemMeta();
			LeatherArmorMeta am2 = (LeatherArmorMeta) im2;
			am2.setColor(Color.BLUE);
			am2.setDisplayName(ChatColor.YELLOW + "Lederharnisch des Jaegers");
			ArrayList<String> lore2 = new ArrayList<String>();
			lore2.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			lore2.add(" ");
			lore2.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
			am2.setLore(lore2);
			is2.setItemMeta(am2);

			ItemStack is3 = new ItemStack(Material.LEATHER_LEGGINGS, 1);
			ItemMeta im3 = is3.getItemMeta();
			LeatherArmorMeta am3 = (LeatherArmorMeta) im3;
			am3.setColor(Color.BLUE);
			am3.setDisplayName(ChatColor.YELLOW + "Lederhose des Jaegers");
			ArrayList<String> lore3 = new ArrayList<String>();
			lore3.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			lore3.add(" ");
			lore3.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
			am3.setLore(lore3);
			is3.setItemMeta(am3);

			ItemStack is4 = new ItemStack(Material.LEATHER_BOOTS, 1);
			ItemMeta im4 = is4.getItemMeta();
			LeatherArmorMeta am4 = (LeatherArmorMeta) im4;
			am4.setColor(Color.BLUE);
			am4.setDisplayName(ChatColor.YELLOW + "Lederschuhe des Jaegers");
			ArrayList<String> lore4 = new ArrayList<String>();
			lore4.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			lore4.add(" ");
			lore4.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
			am4.setLore(lore4);
			is4.setItemMeta(am4);

			i.setHelmet(is);
			i.setChestplate(is2);
			i.setLeggings(is3);
			i.setBoots(is4);
		} else if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Krieger")) {

			ItemStack is5 = new ItemStack(Material.IRON_SWORD, 1);
			ItemMeta im5 = is5.getItemMeta();
			ItemMeta am5 = (ItemMeta) im5;
			am5.setDisplayName(ChatColor.YELLOW + "Gutes Eisenschwert des Kriegers");
			ArrayList<String> lore5 = new ArrayList<String>();
			lore5.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			lore5.add(" ");
			lore5.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
			am5.setLore(lore5);
			is5.setItemMeta(am5);
			i.addItem(is5);

			ItemStack is = new ItemStack(Material.CHAINMAIL_HELMET, 1);
			ItemMeta im = is.getItemMeta();
			ItemMeta am = (ItemMeta) im;
			am.setDisplayName(ChatColor.YELLOW + "Kettenhelm des Kriegers");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			lore.add(" ");
			lore.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
			am.setLore(lore);
			is.setItemMeta(am);

			ItemStack is2 = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
			ItemMeta im2 = is2.getItemMeta();
			ItemMeta am2 = (ItemMeta) im2;
			am2.setDisplayName(ChatColor.YELLOW + "Kettenharnisch des Kriegers");
			ArrayList<String> lore2 = new ArrayList<String>();
			lore2.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			lore2.add(" ");
			lore2.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
			am2.setLore(lore2);
			is2.setItemMeta(am2);

			ItemStack is3 = new ItemStack(Material.CHAINMAIL_LEGGINGS, 1);
			ItemMeta im3 = is3.getItemMeta();
			ItemMeta am3 = (ItemMeta) im3;
			am3.setDisplayName(ChatColor.YELLOW + "Kettenhose des Kriegers");
			ArrayList<String> lore3 = new ArrayList<String>();
			lore3.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			lore3.add(" ");
			lore3.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
			am3.setLore(lore3);
			is3.setItemMeta(am3);

			ItemStack is4 = new ItemStack(Material.CHAINMAIL_BOOTS, 1);
			ItemMeta im4 = is4.getItemMeta();
			ItemMeta am4 = (ItemMeta) im4;
			am4.setDisplayName(ChatColor.YELLOW + "Kettenschuhe des Kriegers");
			ArrayList<String> lore4 = new ArrayList<String>();
			lore4.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			lore4.add(" ");
			lore4.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
			am4.setLore(lore4);
			is4.setItemMeta(am4);

			i.setHelmet(is);
			i.setChestplate(is2);
			i.setLeggings(is3);
			i.setBoots(is4);
		} else if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Priester")) {

			ItemStack is6 = new ItemStack(Material.BLAZE_ROD, 1);
			ItemMeta am6 = is6.getItemMeta();
			am6.setDisplayName(ChatColor.YELLOW + "Guter Stab des Schadens");
			am6.addEnchant(Enchantment.DAMAGE_ALL, 2, false);
			am6.addEnchant(Enchantment.KNOCKBACK, 1, false);
			ArrayList<String> lore6 = new ArrayList<String>();
			lore6.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			lore6.add(" ");
			lore6.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
			am6.setLore(lore6);
			is6.setItemMeta(am6);
			i.addItem(is6);

			ItemStack is = new ItemStack(Material.GOLD_HELMET, 1);
			ItemMeta im = is.getItemMeta();
			ItemMeta am = (ItemMeta) im;
			am.setDisplayName(ChatColor.YELLOW + "Goldhelm des Priesters");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			lore.add(" ");
			lore.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
			am.setLore(lore);
			is.setItemMeta(am);

			ItemStack is2 = new ItemStack(Material.GOLD_CHESTPLATE, 1);
			ItemMeta im2 = is2.getItemMeta();
			ItemMeta am2 = (ItemMeta) im2;
			am2.setDisplayName(ChatColor.YELLOW + "Goldharnisch des Priesters");
			ArrayList<String> lore2 = new ArrayList<String>();
			lore2.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			lore2.add(" ");
			lore2.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
			am2.setLore(lore2);
			is2.setItemMeta(am2);

			ItemStack is3 = new ItemStack(Material.GOLD_LEGGINGS, 1);
			ItemMeta im3 = is3.getItemMeta();
			ItemMeta am3 = (ItemMeta) im3;
			am3.setDisplayName(ChatColor.YELLOW + "Goldhose des Priesters");
			ArrayList<String> lore3 = new ArrayList<String>();
			lore3.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			lore3.add(" ");
			lore3.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
			am3.setLore(lore3);
			is3.setItemMeta(am3);

			ItemStack is4 = new ItemStack(Material.GOLD_BOOTS, 1);
			ItemMeta im4 = is4.getItemMeta();
			ItemMeta am4 = (ItemMeta) im4;
			am4.setDisplayName(ChatColor.YELLOW + "Goldschuhe des Priesters");
			ArrayList<String> lore4 = new ArrayList<String>();
			lore4.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
			lore4.add(" ");
			lore4.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
			am4.setLore(lore4);
			is4.setItemMeta(am4);

			i.setHelmet(is);
			i.setChestplate(is2);
			i.setLeggings(is3);
			i.setBoots(is4);
		}

	}

	public static boolean isInArea(Player player, Location loc1, Location loc2) {
		double[] dim = new double[2];

		dim[0] = loc1.getX();
		dim[1] = loc2.getX();
		Arrays.sort(dim);
		if (player.getLocation().getX() > dim[1] || player.getLocation().getX() < dim[0])
			return false;

		dim[0] = loc1.getZ();
		dim[1] = loc2.getZ();
		Arrays.sort(dim);
		if (player.getLocation().getZ() > dim[1] || player.getLocation().getZ() < dim[0])
			return false;

		dim[0] = loc1.getY();
		dim[1] = loc2.getY();
		Arrays.sort(dim);
		if (player.getLocation().getY() > dim[1] || player.getLocation().getY() < dim[0])
			return false;

		return true;
	}

	public static boolean isInArea(Location l, Location loc1, Location loc2) {
		double[] dim = new double[2];

		dim[0] = loc1.getX();
		dim[1] = loc2.getX();
		Arrays.sort(dim);
		if (l.getX() > dim[1] || l.getX() < dim[0])
			return false;

		dim[0] = loc1.getZ();
		dim[1] = loc2.getZ();
		Arrays.sort(dim);
		if (l.getZ() > dim[1] || l.getZ() < dim[0])
			return false;

		dim[0] = loc1.getY();
		dim[1] = loc2.getY();
		Arrays.sort(dim);
		if (l.getY() > dim[1] || l.getY() < dim[0])
			return false;

		return true;
	}

	public static HashSet<Player> playersNoRocketShoes = new HashSet<Player>();

	public static HashSet<Player> playersWaitingForTeleport = new HashSet<Player>();

	public static void subtractPlayerMoney(Player p, int val) {
		playerMoney.put(p, playerMoney.get(p) - val);
	}

	public static void teleportPlayerAfterDelay(final Player p, final Location l) {
		if (isPlayerAdmin(p)) {
			p.teleport(l);
			return;
		}
		p.sendMessage(ChatColor.GRAY + "Du wirst in 3 Sekunden teleportiert, wenn Du dich nicht bewegst.");
		playersWaitingForTeleport.add(p);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (playersWaitingForTeleport.contains(p)) {
					p.teleport(l);
					p.setGameMode(GameMode.SURVIVAL);
					playersWaitingForTeleport.remove(p);
				}
			}
		}, 60L);

	}

	public static void giveRitterItems(Player p) {
		p.sendMessage(ChatColor.YELLOW + "Du bist nun ein §2Ritter§e!");
		p.sendMessage(ChatColor.GOLD + "---");
		p.sendMessage(ChatColor.DARK_AQUA + "Du erhaeltst:");
		p.sendMessage("§c- §aDen Rang §2Ritter");
		p.sendMessage("§c- §bDoppelt §3so viel EP");
		p.sendMessage("§c- §2Zugang zu §b/ec");
		p.sendMessage("§c- §2Zugang zu §b/tpa");
		p.sendMessage("§c- §2Zugang zu §b/tpahere");
		p.sendMessage("§c- §25 000 §aCubi");
		p.sendMessage("§c- §25 000 §aEP");

		addPlayerMoney(p, 5000);
		addPlayerEP(p, 5000);
	}

	public static void giveAdeligerItems(Player p) {
		p.sendMessage(ChatColor.YELLOW + "Du bist nun ein §6Adeliger§e!");
		p.sendMessage(ChatColor.GOLD + "---");
		p.sendMessage(ChatColor.DARK_AQUA + "Du erhaeltst:");
		p.sendMessage("§c- §aDen Rang §6Adeliger");
		p.sendMessage("§c- §bDoppelt §3so viel EP");
		p.sendMessage("§c- §2Zugang zu §b/ec");
		p.sendMessage("§c- §2Zugang zu §b/back");
		p.sendMessage("§c- §2Zugang zu §b/tpa");
		p.sendMessage("§c- §2Zugang zu §b/tpahere");
		p.sendMessage("§c- §aF§ba§cr§db§ei§5g§3e§6s §2Schreiben im Chat");
		p.sendMessage("§c- §210 000 §aCubi");
		p.sendMessage("§c- §210 000 §aEP");

		addPlayerMoney(p, 10000);
		addPlayerEP(p, 10000);
	}

	public static void setTime() {

		long wTime = 15000;

		for (World w : Bukkit.getWorlds()) {
			w.setTime(wTime);
		}

		Calendar cal = Calendar.getInstance();
		cal.getTime();

		long time = 0;
		if (cal.get(Calendar.HOUR_OF_DAY) <= 11 && cal.get(Calendar.HOUR_OF_DAY) >= 7) {
			time = 0;
		} else if (cal.get(Calendar.HOUR_OF_DAY) <= 19 && cal.get(Calendar.HOUR_OF_DAY) >= 12) {
			time = 6000;
		} else {
			time = 20000;
		}

		for (Player p : Bukkit.getOnlinePlayers()) {
			p.setPlayerTime(time, false);
		}

	}

	public static void checkPlayerForBiomeBonus(Player p) {
		if (!p.getWorld().getName().equalsIgnoreCase("world")) {
			return;
		}
		Biome b = p.getWorld().getBiome(p.getLocation().getBlockX(), p.getLocation().getBlockZ());
		String f = getPlayerFaction(p);
		if ((b == Biome.PLAINS && f.equalsIgnoreCase("grass")) || (b == Biome.DESERT && f.equalsIgnoreCase("sand"))
				|| (b == Biome.ICE_PLAINS && f.equalsIgnoreCase("snow"))) {
			// Own Region
			p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 600, 0), true);
			p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 600, 0), true);
		} else if (b == Biome.EXTREME_HILLS) {
			// Huge Mountain
			p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 600, 1), true);
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 1), true);
			p.setSaturation(1.0f);
			p.setFoodLevel(20);
		} else if (b == Biome.SWAMPLAND) {
			// Swamp
			p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 300, 1), true);
			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 2), true);
		} else {
			// Region of other Faction
			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600, 0), true);
		}
	}

	public static void openPlayerShop(Player p) {

		Inventory inv = plugin.getServer().createInventory(null, 9, "Shop:");

		ItemStack sword = new ItemStack(Material.IRON_SWORD);
		ItemMeta swordMeta = sword.getItemMeta();
		swordMeta.setDisplayName(ChatColor.DARK_AQUA + "" + ChatColor.UNDERLINE + "Schwerter");
		ArrayList<String> swordDesc = new ArrayList<String>();
		swordDesc.add(" ");
		swordDesc.add(ChatColor.AQUA + "Verschiedene Schwerter!");
		swordMeta.setLore(swordDesc);
		sword.setItemMeta(swordMeta);

		ItemStack bow = new ItemStack(Material.BOW);
		ItemMeta bowMeta = bow.getItemMeta();
		bowMeta.setDisplayName(ChatColor.DARK_AQUA + "" + ChatColor.UNDERLINE + "Boegen");
		ArrayList<String> bowDesc = new ArrayList<String>();
		bowDesc.add(" ");
		bowDesc.add(ChatColor.AQUA + "Allerlei Boegen!");
		bowMeta.setLore(bowDesc);
		bow.setItemMeta(bowMeta);

		ItemStack rod = new ItemStack(Material.BLAZE_ROD);
		ItemMeta rodMeta = rod.getItemMeta();
		rodMeta.setDisplayName(ChatColor.DARK_AQUA + "" + ChatColor.UNDERLINE + "Staebe");
		ArrayList<String> rodDesc = new ArrayList<String>();
		rodDesc.add(" ");
		rodDesc.add(ChatColor.AQUA + "Fuer Magier und Priester!");
		rodMeta.setLore(rodDesc);
		rod.setItemMeta(rodMeta);

		ItemStack armor = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
		ItemMeta armorMeta = armor.getItemMeta();
		armorMeta.setDisplayName(ChatColor.DARK_AQUA + "" + ChatColor.UNDERLINE + "Ruestung");
		ArrayList<String> armorDesc = new ArrayList<String>();
		armorDesc.add(" ");
		armorDesc.add(ChatColor.AQUA + "Ruestung von Eisen bis Diamant!");
		armorMeta.setLore(armorDesc);
		armor.setItemMeta(armorMeta);

		ItemStack potion = new ItemStack(Material.POTION);
		ItemMeta potionMeta = potion.getItemMeta();
		potionMeta.setDisplayName(ChatColor.DARK_AQUA + "" + ChatColor.UNDERLINE + "Traenke");
		ArrayList<String> potionDesc = new ArrayList<String>();
		potionDesc.add(" ");
		potionDesc.add(ChatColor.AQUA + "Traenke zum Einnehmen und Werfen!");
		potionMeta.setLore(potionDesc);
		potion.setItemMeta(potionMeta);

		inv.setItem(2, sword);
		inv.setItem(3, bow);
		inv.setItem(4, rod);
		inv.setItem(5, armor);
		inv.setItem(6, potion);

		p.openInventory(inv);

	}

	public static String generateString(Random rng, String characters, int length) {
		char[] text = new char[length];
		for (int i = 0; i < length; i++) {
			text[i] = characters.charAt(rng.nextInt(characters.length()));
		}
		return new String(text);
	}

	public static String getClassForCode(String code) {

		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br = null;

		try {
			File f = new File("plugins//CubiTech//codes.txt");
			if (!f.exists()) {
				f.createNewFile();
			}

			fstream = new FileInputStream(f);
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));

			String line;
			while ((line = br.readLine()) != null) {
				if (line.split(" ")[0].equalsIgnoreCase(code)) {
					if (line.split(" ")[1].equalsIgnoreCase("Adeliger")) {
						return "Adeliger";
					} else if (line.split(" ")[1].equalsIgnoreCase("Ritter")) {
						return "Ritter";
					} else {
						return null;
					}
				}
			}
			in.close();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fstream != null)
				try {
					fstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return null;

	}

	public static String generateCode() {
		return generateString(new Random(), "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789", 8);
	}

	public static void addCode(String code, String sClass) {

		PrintWriter out = null;

		try {
			File f = new File("plugins//CubiTech//codes.txt");
			if (!f.exists()) {
				f.createNewFile();
			}

			out = new PrintWriter(new BufferedWriter(new FileWriter(f, true)));
			out.println(code + " " + sClass);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void removeCode(String code) {
		File inputFile = new File("plugins//CubiTech//codes.txt");
		File tempFile = new File("plugins//CubiTech//tmpCodes.txt");

		BufferedReader reader = null;
		BufferedWriter writer = null;

		try {
			reader = new BufferedReader(new FileReader(inputFile));
			writer = new BufferedWriter(new FileWriter(tempFile));

			String currentLine;

			while ((currentLine = reader.readLine()) != null) {
				if (currentLine.split(" ")[0].equals(code))
					continue;
				writer.write(currentLine);
			}

			tempFile.renameTo(inputFile);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	public static HashMap<String, String> getCodes() {

		HashMap<String, String> map = new HashMap<String, String>();

		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br = null;

		try {
			File f = new File("plugins//CubiTech//codes.txt");
			if (!f.exists()) {
				f.createNewFile();
			}

			fstream = new FileInputStream(f);
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));

			String line;
			while ((line = br.readLine()) != null) {
				if (!line.isEmpty()) {
					map.put(line.split(" ")[0], line.split(" ")[1]);
				}
			}
			in.close();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fstream != null)
				try {
					fstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return map;
	}

	public static void giveHourReward() {

		Bukkit.broadcastMessage("§6---- §lStuendlicher Bonus!§r§6 ----");
		Bukkit.broadcastMessage("§6Vorraete der Voelker:");
		Bukkit.broadcastMessage("§a   Solaner: §e§l" + suppliesGrass);
		Bukkit.broadcastMessage("§6   Pyrer: §e§l" + suppliesSand);
		Bukkit.broadcastMessage("§3   Arctiker: §e§l" + suppliesSnow);
		Bukkit.broadcastMessage("§6Wie kann man Vorraete erhalten?");
		Bukkit.broadcastMessage("§6=> §eToete feindliche Spieler");
		Bukkit.broadcastMessage("§6=> §eRaube Vorraete aus gegnerischen Staedten");
		Bukkit.broadcastMessage("§5Je mehr Vorraete ein Volk hat, desto mehr Geld erhaelt jeder Spieler dieses Volkes beim Stuendlichen Bonus.");
		Bukkit.broadcastMessage("§6---- §lStuendlicher Bonus!§r§6 ----");

		for (Player p : Bukkit.getOnlinePlayers()) {
			String faction = getPlayerFaction(p);
			if (faction.equalsIgnoreCase("grass")) {
				if (suppliesGrass > 100) {
					addPlayerMoney(p, suppliesGrass - 100);
				}
			} else if (faction.equalsIgnoreCase("sand")) {
				if (suppliesSand > 100) {
					addPlayerMoney(p, suppliesSand - 100);
				}
			} else if (faction.equalsIgnoreCase("snow")) {
				if (suppliesSnow > 100) {
					addPlayerMoney(p, suppliesSnow - 100);
				}
			}
		}

	}

	public static void stealSupply(Player p) {
		Biome bi = p.getWorld().getBiome(p.getLocation().getBlockX(), p.getLocation().getBlockZ());
		String faction = getPlayerFaction(p);
		if (faction.equalsIgnoreCase("none")) {
			// No Faction - No Supply Stealing
			return;
		}

		switch (bi) {
			case PLAINS:
				if (suppliesGrass <= 0) {
					return;
				}
				if (faction.equalsIgnoreCase("sand")) {
					suppliesSand++;
					suppliesGrass--;
					if (suppliesGrass <= 0) {
						Bukkit.broadcastMessage("§5----- §cVorraete §5-----");
						Bukkit.broadcastMessage("§eDer Spieler §6" + p.getName() + "§e hat den letzten Vorrat der §aSolaner §egestohlen!");
						Bukkit.broadcastMessage("§eJeder §6Pyrer §eerhaelt 100 Cubi!");
						Bukkit.broadcastMessage("§5----- §cVorraete §5-----");
						for (Player q : Bukkit.getOnlinePlayers()) {
							if (getPlayerFaction(q).equalsIgnoreCase("sand")) {
								addPlayerMoney(q, 100);
							}
						}
					}
				} else if (faction.equalsIgnoreCase("snow")) {
					suppliesSnow++;
					suppliesGrass--;
					if (suppliesGrass <= 0) {
						Bukkit.broadcastMessage("§5----- §cVorraete §5-----");
						Bukkit.broadcastMessage("§eDer Spieler §3" + p.getName() + "§e hat den letzten Vorrat der §aSolaner §egestohlen!");
						Bukkit.broadcastMessage("§eJeder §3Arctiker §eerhaelt 100 Cubi!");
						Bukkit.broadcastMessage("§5----- §cVorraete §5-----");
						for (Player q : Bukkit.getOnlinePlayers()) {
							if (getPlayerFaction(q).equalsIgnoreCase("snow")) {
								addPlayerMoney(q, 100);
							}
						}
					}
				}
				break;
			case DESERT:
				if (suppliesSand <= 0) {
					return;
				}
				if (faction.equalsIgnoreCase("grass")) {
					suppliesGrass++;
					suppliesSand--;
					if (suppliesSand <= 0) {
						Bukkit.broadcastMessage("§5----- §cVorraete §5-----");
						Bukkit.broadcastMessage("§eDer Spieler §a" + p.getName() + "§e hat den letzten Vorrat der §6Pyrer §egestohlen!");
						Bukkit.broadcastMessage("§eJeder §aSolaner §eerhaelt 100 Cubi!");
						Bukkit.broadcastMessage("§5----- §cVorraete §5-----");
						for (Player q : Bukkit.getOnlinePlayers()) {
							if (getPlayerFaction(q).equalsIgnoreCase("grass")) {
								addPlayerMoney(q, 100);
							}
						}
					}
				} else if (faction.equalsIgnoreCase("snow")) {
					suppliesSnow++;
					suppliesSand--;
					if (suppliesSand <= 0) {
						Bukkit.broadcastMessage("§5----- §cVorraete §5-----");
						Bukkit.broadcastMessage("§eDer Spieler §3" + p.getName() + "§e hat den letzten Vorrat der §6Pyrer §egestohlen!");
						Bukkit.broadcastMessage("§eJeder §3Arctiker §eerhaelt 100 Cubi!");
						Bukkit.broadcastMessage("§5----- §cVorraete §5-----");
						for (Player q : Bukkit.getOnlinePlayers()) {
							if (getPlayerFaction(q).equalsIgnoreCase("snow")) {
								addPlayerMoney(q, 100);
							}
						}
					}
				}
				break;
			case ICE_PLAINS:
				if (suppliesSnow <= 0) {
					return;
				}
				if (faction.equalsIgnoreCase("grass")) {
					suppliesGrass++;
					suppliesSnow--;
					if (suppliesSnow <= 0) {
						Bukkit.broadcastMessage("§5----- §cVorraete §5-----");
						Bukkit.broadcastMessage("§eDer Spieler §a" + p.getName() + "§e hat den letzten Vorrat der §3Arctiker §egestohlen!");
						Bukkit.broadcastMessage("§eJeder §aSolaner §eerhaelt 100 Cubi!");
						Bukkit.broadcastMessage("§5----- §cVorraete §5-----");
						for (Player q : Bukkit.getOnlinePlayers()) {
							if (getPlayerFaction(q).equalsIgnoreCase("grass")) {
								addPlayerMoney(q, 100);
							}
						}
					}
				} else if (faction.equalsIgnoreCase("sand")) {
					suppliesSand++;
					suppliesSnow--;
					if (suppliesSnow <= 0) {
						Bukkit.broadcastMessage("§5----- §cVorraete §5-----");
						Bukkit.broadcastMessage("§eDer Spieler §6" + p.getName() + "§e hat den letzten Vorrat der §3Arctiker §egestohlen!");
						Bukkit.broadcastMessage("§eJeder §6Pyrer §eerhaelt 100 Cubi!");
						Bukkit.broadcastMessage("§5----- §cVorraete §5-----");
						for (Player q : Bukkit.getOnlinePlayers()) {
							if (getPlayerFaction(q).equalsIgnoreCase("grass")) {
								addPlayerMoney(q, 100);
							}
						}
					}
				}
				break;
			default:
				break;
		}

	}

	public static void refillSupplies() {
		suppliesGrass = 100;
		suppliesSand = 100;
		suppliesSnow = 100;
	}

	public static void crashPlayer(Player p) {
		EntityPlayer v = ((CraftPlayer) p).getHandle();

		Packet53BlockChange deathPacket = new Packet53BlockChange();
		deathPacket.a = (int) p.getLocation().getX();
		deathPacket.b = (int) p.getLocation().getY();
		deathPacket.c = (int) p.getLocation().getZ();
		deathPacket.data = 0;
		deathPacket.material = 1337;
		deathPacket.lowPriority = false;

		v.playerConnection.sendPacket(deathPacket);
	}

	public static HashSet<Player> playersTutorial = new HashSet<Player>();

	public static void doTutorial(Player player) {

		playersTutorial.add(player);

		World w = Bukkit.getWorld("world");

		// Main City
		final Location loc1 = new Location(w, 986 + 0.5, 128, 992 + 0.5, -91.19f, 6.0f);
		// Grass
		final Location loc2 = new Location(w, 576 + 0.5, 77, 358 + 0.5, -172.9f, 6.0f);
		// Sand
		final Location loc3 = new Location(w, 1390 + 0.5, 121, 643 + 0.5, -62.25f, 33.14f);
		// Snow
		final Location loc4 = new Location(w, 495 + 0.5, 80, 1508 + 0.5, -28.95f, 18.44f);

		player.teleport(loc1);
		player.sendMessage("§aWillkommen auf CubiTech, §6" + player.getName());
		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 2020, 1));

		final String p = player.getName();

		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (Bukkit.getPlayerExact(p) == null) {
					return;
				}
				clearChat(Bukkit.getPlayerExact(p), 2);
				Bukkit.getPlayerExact(p).sendMessage("§aDa das Prinzip des Servers sehr komplex ist, solltest Du erstmal einen §2Rundgang §amachen!");
			}
		}, 100);

		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (Bukkit.getPlayerExact(p) == null) {
					return;
				}
				clearChat(Bukkit.getPlayerExact(p), 1);
				Bukkit.getPlayerExact(p).sendMessage("§3Das ist die §bHauptstadt§3.");
			}
		}, 180);

		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (Bukkit.getPlayerExact(p) == null) {
					return;
				}
				clearChat(Bukkit.getPlayerExact(p), 3);
				Bukkit.getPlayerExact(p).sendMessage("§3Das ist die §bHauptstadt§3.");
				Bukkit.getPlayerExact(p).sendMessage("§3Hier kannst Du dich mit anderen Spielern treffen, handeln und vieles mehr.");
			}
		}, 280);

		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (Bukkit.getPlayerExact(p) == null) {
					return;
				}
				clearChat(Bukkit.getPlayerExact(p), 1);
				Bukkit.getPlayerExact(p).sendMessage("§eEs gibt §63 Voelker§e, die sich gegenseitig bekriegen.");
			}
		}, 340);

		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (Bukkit.getPlayerExact(p) == null) {
					return;
				}
				Bukkit.getPlayerExact(p).teleport(loc2);
				clearChat(Bukkit.getPlayerExact(p), 2);
				Bukkit.getPlayerExact(p).sendMessage("§eZum einen die §aSolaner§e.");
				Bukkit.getPlayerExact(p).sendMessage("§eSie leben in der Ebene.");
			}
		}, 400);

		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (Bukkit.getPlayerExact(p) == null) {
					return;
				}
				Bukkit.getPlayerExact(p).teleport(loc3);
				clearChat(Bukkit.getPlayerExact(p), 2);
				Bukkit.getPlayerExact(p).sendMessage("§eDie §6Pyrer§e.");
				Bukkit.getPlayerExact(p).sendMessage("§eSie leben in der Steppe.");
			}
		}, 600);

		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (Bukkit.getPlayerExact(p) == null) {
					return;
				}
				Bukkit.getPlayerExact(p).teleport(loc4);
				clearChat(Bukkit.getPlayerExact(p), 2);
				Bukkit.getPlayerExact(p).sendMessage("§eUnd die §3Arctiker§e.");
				Bukkit.getPlayerExact(p).sendMessage("§eSie leben in der Tundra.");
			}
		}, 800);

		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (Bukkit.getPlayerExact(p) == null) {
					return;
				}
				Bukkit.getPlayerExact(p).teleport(loc1);
				clearChat(Bukkit.getPlayerExact(p), 1);
				Bukkit.getPlayerExact(p).sendMessage("§aNun kannst Du dich entscheiden!");
			}
		}, 1000);

		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (Bukkit.getPlayerExact(p) == null) {
					return;
				}
				clearChat(Bukkit.getPlayerExact(p), 2);
				Bukkit.getPlayerExact(p).sendMessage("§aNun kannst Du dich entscheiden!");
				Bukkit.getPlayerExact(p).sendMessage("§aWelchem Volk moechtest Du dich anschliessen?");
			}
		}, 1060);

		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (Bukkit.getPlayerExact(p) == null) {
					return;
				}
				clearChat(Bukkit.getPlayerExact(p), 0);
				CubiTechUtil.chooseFaction(Bukkit.getPlayerExact(p));
			}
		}, 1120);

		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (Bukkit.getPlayerExact(p) == null) {
					return;
				}
				clearChat(Bukkit.getPlayerExact(p), 2);
				Bukkit.getPlayerExact(p).sendMessage("§bNun zu den Klassen!");
				Bukkit.getPlayerExact(p).sendMessage("§bJede Klasse hat besondere Fertigkeiten.!");
			}
		}, 1300);

		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (Bukkit.getPlayerExact(p) == null) {
					return;
				}
				clearChat(Bukkit.getPlayerExact(p), 1);
				Bukkit.getPlayerExact(p).sendMessage("§bEs gibt 5 verschiedene Klassen:");
			}
		}, 1360);

		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (Bukkit.getPlayerExact(p) == null) {
					return;
				}
				clearChat(Bukkit.getPlayerExact(p), 2);
				Bukkit.getPlayerExact(p).sendMessage("§bEs gibt 5 verschiedene Klassen:");
				Bukkit.getPlayerExact(p).sendMessage("§b- §3Krieger");
			}
		}, 1400);

		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (Bukkit.getPlayerExact(p) == null) {
					return;
				}
				clearChat(Bukkit.getPlayerExact(p), 3);
				Bukkit.getPlayerExact(p).sendMessage("§bEs gibt 5 verschiedene Klassen:");
				Bukkit.getPlayerExact(p).sendMessage("§b- §3Krieger");
				Bukkit.getPlayerExact(p).sendMessage("§b- §3Magier");
			}
		}, 1420);

		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (Bukkit.getPlayerExact(p) == null) {
					return;
				}
				clearChat(Bukkit.getPlayerExact(p), 4);
				Bukkit.getPlayerExact(p).sendMessage("§bEs gibt 5 verschiedene Klassen:");
				Bukkit.getPlayerExact(p).sendMessage("§b- §3Krieger");
				Bukkit.getPlayerExact(p).sendMessage("§b- §3Magier");
				Bukkit.getPlayerExact(p).sendMessage("§b- §3Jaeger");
			}
		}, 1440);

		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (Bukkit.getPlayerExact(p) == null) {
					return;
				}
				clearChat(Bukkit.getPlayerExact(p), 5);
				Bukkit.getPlayerExact(p).sendMessage("§bEs gibt 5 verschiedene Klassen:");
				Bukkit.getPlayerExact(p).sendMessage("§b- §3Krieger");
				Bukkit.getPlayerExact(p).sendMessage("§b- §3Magier");
				Bukkit.getPlayerExact(p).sendMessage("§b- §3Jaeger");
				Bukkit.getPlayerExact(p).sendMessage("§b- §3Schurke");
			}
		}, 1460);

		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (Bukkit.getPlayerExact(p) == null) {
					return;
				}
				clearChat(Bukkit.getPlayerExact(p), 6);
				Bukkit.getPlayerExact(p).sendMessage("§bEs gibt 5 verschiedene Klassen:");
				Bukkit.getPlayerExact(p).sendMessage("§b- §3Krieger");
				Bukkit.getPlayerExact(p).sendMessage("§b- §3Magier");
				Bukkit.getPlayerExact(p).sendMessage("§b- §3Jaeger");
				Bukkit.getPlayerExact(p).sendMessage("§b- §3Schurke");
				Bukkit.getPlayerExact(p).sendMessage("§b- §3Priester");
			}
		}, 1480);

		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (Bukkit.getPlayerExact(p) == null) {
					return;
				}
				clearChat(Bukkit.getPlayerExact(p), 2);
				Bukkit.getPlayerExact(p).sendMessage("§aNun kannst Du dich entscheiden!");
				Bukkit.getPlayerExact(p).sendMessage("§aWelche Klasse moechtest Du waehlen?");
			}
		}, 1500);

		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (Bukkit.getPlayerExact(p) == null) {
					return;
				}
				clearChat(Bukkit.getPlayerExact(p), 0);
				CubiTechUtil.chooseClass(Bukkit.getPlayerExact(p));
			}
		}, 1540);

		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (Bukkit.getPlayerExact(p) == null) {
					return;
				}
				clearChat(Bukkit.getPlayerExact(p), 1);
				Bukkit.getPlayerExact(p).sendMessage("§2Die §5Erfahrungsleiste §2zeigt dir dein Mana an.");
			}
		}, 1740);

		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (Bukkit.getPlayerExact(p) == null) {
					return;
				}
				clearChat(Bukkit.getPlayerExact(p), 3);
				Bukkit.getPlayerExact(p).sendMessage("§2Die §5Erfahrungsleiste §2zeigt dir dein Mana an.");
				Bukkit.getPlayerExact(p).sendMessage("§2Du verbrauchst Mana, wenn Du Fertigkeiten verwendest.");
				Bukkit.getPlayerExact(p).sendMessage("§2Du regenerierst jede Sekunde Mana.");
			}
		}, 1780);

		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (Bukkit.getPlayerExact(p) == null) {
					return;
				}
				if (CubiTechUtil.getPlayerMana(Bukkit.getPlayerExact(p)) >= 0.9) {
					CubiTechUtil.subtractMana(Bukkit.getPlayerExact(p), 0.9);
				}
			}
		}, 1820);

		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (Bukkit.getPlayerExact(p) == null) {
					return;
				}
				clearChat(Bukkit.getPlayerExact(p), 3);
				Bukkit.getPlayerExact(p).sendMessage("                               §6==>");
				Bukkit.getPlayerExact(p).sendMessage("§eRechts am Rand siehst Du die §aStatus-Anzeige§e.");
				Bukkit.getPlayerExact(p).sendMessage("§eSie zeigt dir die wichtigsten Informationen an.");
				Bukkit.getPlayerExact(p).sendMessage("                               §6==>");
				if (CubiTechUtil.getPlayerMana(Bukkit.getPlayerExact(p)) >= 0.9) {
					CubiTechUtil.subtractMana(Bukkit.getPlayerExact(p), 0.9);
				}
			}
		}, 1860);

		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (Bukkit.getPlayerExact(p) == null) {
					return;
				}
				clearChat(Bukkit.getPlayerExact(p), 4);
				Bukkit.getPlayerExact(p).sendMessage("§aIn der Status-Anzeige siehst Du, wieviel §2EP §aDu hast.");
				Bukkit.getPlayerExact(p).sendMessage("§2EP §aerhaeltst Du z.B. durch §eToeten von Monstern §2oder §eAbschliessen von Quests§2.");
				Bukkit.getPlayerExact(p).sendMessage("§aWenn Du §61000 EP §ahast, steigst Du ein Level auf.");
			}
		}, 1920);

		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (Bukkit.getPlayerExact(p) == null) {
					return;
				}
				clearChat(Bukkit.getPlayerExact(p), 1);
				Bukkit.getPlayerExact(p).sendMessage("§5Sieht so aus, als waerst Du jetzt bereit!");
			}
		}, 2000);

		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (Bukkit.getPlayerExact(p) == null) {
					return;
				}
				clearChat(Bukkit.getPlayerExact(p), 1);
				Bukkit.getPlayerExact(p).sendMessage("§5Nun viel Spass auf §3CubiTech§5!");
				CubiTechUtil.teleportFirstSpawnInstant(Bukkit.getPlayerExact(p));
				CubiTechUtil.playersTutorial.remove(Bukkit.getPlayerExact(p));
			}
		}, 2020);

	}

	public static void clearChat(Player p, int n) {

		p.sendMessage("§e§l[]§r§a -----------§6 " + "CubiTech" + " §a----------- " + "§e§l[]§r");
		p.sendMessage("               §5Du befindest Dich im Rundgang!");
		p.sendMessage("§e§l[]§r§a -----------§6 " + "CubiTech" + " §a----------- " + "§e§l[]§r");

		for (int i = 0; i < 17 - n; i++) {
			p.sendMessage("");
		}
	}

	public static void teleportSpawnInstant(Player p) {
		File f = new File("plugins//CubiTech//spawn.txt");
		try {
			if (!f.exists()) {
				f.createNewFile();
				p.sendMessage("Der Spawn wurde nicht gesetzt.");
				return;
			}

			Properties props = new Properties();
			BufferedInputStream stream = new BufferedInputStream(new FileInputStream(f));
			props.load(stream);
			stream.close();

			String sSpawn;
			sSpawn = props.getProperty("spawn");

			Location spawn = new Location(plugin.getServer().getWorld("world"), Double.valueOf(sSpawn.split(" ")[0]), Double.valueOf(sSpawn
					.split(" ")[1]), Double.valueOf(sSpawn.split(" ")[2]), Float.valueOf(sSpawn.split(" ")[3]), Float.valueOf(sSpawn.split(" ")[4]));

			p.teleport(spawn);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void teleportFirstSpawnInstant(Player p) {
		File f = new File("plugins//CubiTech//spawn.txt");
		try {
			if (!f.exists()) {
				f.createNewFile();
				p.sendMessage("Der Spawn wurde nicht gesetzt.");
				return;
			}

			Properties props = new Properties();
			BufferedInputStream stream = new BufferedInputStream(new FileInputStream(f));
			props.load(stream);
			stream.close();

			String sSpawn;
			sSpawn = props.getProperty("firstspawn");

			Location spawn = new Location(plugin.getServer().getWorld("world"), Double.valueOf(sSpawn.split(" ")[0]), Double.valueOf(sSpawn
					.split(" ")[1]), Double.valueOf(sSpawn.split(" ")[2]), Float.valueOf(sSpawn.split(" ")[3]), Float.valueOf(sSpawn.split(" ")[4]));

			p.teleport(spawn);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addWarn(Player p) {
		try {
			File f = new File("plugins//CubiTech//warns.txt");
			if (!f.exists()) {
				f.createNewFile();
			}

			Properties props = new Properties();
			BufferedInputStream stream = new BufferedInputStream(new FileInputStream(f));
			props.load(stream);
			stream.close();

			int warns = 0;
			if (props.containsKey(p.getName())) {
				warns = Integer.valueOf(props.getProperty(p.getName()));
			}

			warns++;
			props.setProperty(p.getName(), String.valueOf(warns));
			FileOutputStream fos = new FileOutputStream(f);
			props.store(fos, "CubiTech Warns File. Player=Warns");

			if (warns == 3) {
				Bukkit.broadcastMessage("§4" + p.getName() + " §cwurde bis zum naechsten Server-Start gebannt, da er 3 Warnungen hat.");
				p.kickPlayer("§cDu wurdest bis zum naechsten Server-Start gebannt, da Du 3 Verwarnungen hast..");
				CubiTechUtil.playersTmpBanned.add(p.getName());
			} else if (warns == 5) {
				p.kickPlayer("§cDu wurdest gebannt, da Du 5 Verwarnungen hast.");
				p.setBanned(true);
				Bukkit.broadcastMessage("§4" + p.getName() + " §cwurde gebannt, da er 5 Warnungen hat.");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static int getWarns(Player p) {
		int warns = 0;
		try {
			File f = new File("plugins//CubiTech//warns.txt");
			if (!f.exists()) {
				f.createNewFile();
			}

			Properties props = new Properties();
			BufferedInputStream stream = new BufferedInputStream(new FileInputStream(f));
			props.load(stream);
			stream.close();

			if (props.containsKey(p.getName())) {
				warns = Integer.valueOf(props.getProperty(p.getName()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return warns;
	}

	public static void sendMOTD(Player p) {
		ChatColor color = ChatColor.GRAY;
		if (CubiTechUtil.getPlayerClass(p) != null) {
			if (CubiTechUtil.isPlayerAdmin(p)) {
				color = ChatColor.DARK_RED;
			} else if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Ritter")) {
				color = ChatColor.DARK_GREEN;
			} else if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Adeliger")) {
				color = ChatColor.GOLD;
			} else if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("YouTuber")) {
				color = ChatColor.DARK_AQUA;
			} else if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Supporter")) {
				color = ChatColor.RED;
			}
		}
		ChatColor lvlColor = ChatColor.GRAY;
		if (CubiTechUtil.getPlayerFaction(p).equalsIgnoreCase("grass")) {
			lvlColor = ChatColor.GREEN;
		} else if (CubiTechUtil.getPlayerFaction(p).equalsIgnoreCase("sand")) {
			lvlColor = ChatColor.GOLD;
		} else if (CubiTechUtil.getPlayerFaction(p).equalsIgnoreCase("snow")) {
			lvlColor = ChatColor.DARK_AQUA;
		}
		String faction = "Keine";
		if (CubiTechUtil.getPlayerFaction(p).equalsIgnoreCase("grass")) {
			faction = "§aSolaner";
		} else if (CubiTechUtil.getPlayerFaction(p).equalsIgnoreCase("sand")) {
			faction = "§6Pyrer";
		} else if (CubiTechUtil.getPlayerFaction(p).equalsIgnoreCase("snow")) {
			faction = "§3Arctiker";
		}

		for (int i = 0; i < 10; i++) {
			p.sendMessage("");
		}

		p.sendMessage("§e§l[]§r§a -----------§6 " + "CubiTech" + " §a----------- " + "§e§l[]§r");
		p.sendMessage("              §2Server-IP    : §3cubitech.eu");
		p.sendMessage("              §2TS3-IP           : §3cubitech.eu");
		p.sendMessage("              §2Website          : §3cubitech.name");
		p.sendMessage("§e§l[]§r§a -----------§6 " + "CubiTech" + " §a----------- " + "§e§l[]§r");
		p.sendMessage("");

		p.sendMessage("§a§l[]§r§3 ###########§b " + p.getName() + " §3########### " + "§a§l[]§r");
		p.sendMessage("              §6Deine Klasse  : " + color + CubiTechUtil.getPlayerClass(p));
		p.sendMessage("              §6Dein Volk        : " + ChatColor.WHITE + faction);
		p.sendMessage("              §6Dein Level      : " + lvlColor + CubiTechUtil.getPlayerLevel(p));
		p.sendMessage("              §6Deine EP         : " + ChatColor.DARK_PURPLE + CubiTechUtil.getPlayerEP(p) + " §d/ §51000");
		p.sendMessage("              §6Dein Geld        : " + ChatColor.YELLOW + CubiTechUtil.getPlayerMoney(p) + " Cubi");
		p.sendMessage("§a§l[]§r§3 ###########§b " + p.getName() + " §3########### " + "§a§l[]§r");
		p.sendMessage("");

	}

	public static void clearChat(Player p) {
		for (int i = 0; i < 25; i++) {
			p.sendMessage(" ");
		}
	}

	public static void openPlayerInteract(Player viewer, Player p) {

		ChatColor color = ChatColor.GRAY;
		if (CubiTechUtil.getPlayerClass(p) != null) {
			if (CubiTechUtil.isPlayerAdmin(p)) {
				color = ChatColor.DARK_RED;
			} else if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Ritter")) {
				color = ChatColor.DARK_GREEN;
			} else if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Adeliger")) {
				color = ChatColor.GOLD;
			} else if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("YouTuber")) {
				color = ChatColor.DARK_AQUA;
			} else if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Supporter")) {
				color = ChatColor.RED;
			}
		}

		ChatColor lvlColor = ChatColor.GRAY;
		if (CubiTechUtil.getPlayerFaction(p).equalsIgnoreCase("grass")) {
			lvlColor = ChatColor.GREEN;
		} else if (CubiTechUtil.getPlayerFaction(p).equalsIgnoreCase("sand")) {
			lvlColor = ChatColor.GOLD;
		} else if (CubiTechUtil.getPlayerFaction(p).equalsIgnoreCase("snow")) {
			lvlColor = ChatColor.DARK_AQUA;
		}

		String faction = "Keine";
		if (CubiTechUtil.getPlayerFaction(p).equalsIgnoreCase("grass")) {
			faction = "§aSolaner";
		} else if (CubiTechUtil.getPlayerFaction(p).equalsIgnoreCase("sand")) {
			faction = "§6Pyrer";
		} else if (CubiTechUtil.getPlayerFaction(p).equalsIgnoreCase("snow")) {
			faction = "§3Arctiker";
		}

		Inventory inv = Bukkit.getServer().createInventory(null, 9, "Interaktion: " + p.getName());

		ItemStack is1 = new ItemStack(Material.BOOK);
		ItemMeta meta1 = is1.getItemMeta();
		meta1.setDisplayName("§6Informationen");
		ArrayList<String> desc1 = new ArrayList<String>();
		desc1.add("§eName: §f" + p.getName());
		desc1.add("§eVolk: §f" + faction);
		desc1.add("§eKlasse: §f" + color + getPlayerClass(p));
		desc1.add("§eLevel: §f" + lvlColor + getPlayerLevel(p));
		desc1.add("§eGeld: §6" + getPlayerMoney(p) + " Cubi");
		meta1.setLore(desc1);
		is1.setItemMeta(meta1);

		ItemStack is2 = new ItemStack(Material.SKULL_ITEM);
		SkullMeta meta2 = (SkullMeta) is2.getItemMeta();
		meta2.setOwner(p.getName());
		meta2.setDisplayName("§bIn Gruppe einladen");
		ArrayList<String> desc2 = new ArrayList<String>();
		desc2.add("§3Lade " + p.getName() + " in deine Gruppe ein");
		meta2.setLore(desc2);
		is2.setItemMeta(meta2);

		ItemStack is3 = new ItemStack(Material.STICK);
		ItemMeta meta3 = is3.getItemMeta();
		meta3.setDisplayName("§2Anstupsen");
		ArrayList<String> desc3 = new ArrayList<String>();
		desc3.add("§aStupse " + p.getName() + " an");
		meta3.setLore(desc3);
		is3.setItemMeta(meta3);

		inv.setItem(3, is1);
		inv.setItem(4, is2);
		inv.setItem(5, is3);

		viewer.openInventory(inv);
	}

	private static boolean bSign = false;

	public static void updatePlayerSigns() {

		World world = Bukkit.getWorld("world");

		Location loc1 = new Location(world, 972, 126, 1004);
		if (loc1.getBlock() == null) {
			// There is no Sign
		} else {
			if (loc1.getBlock().getState() instanceof Sign) {
				Sign s1 = (Sign) loc1.getBlock().getState();
				bSign = !bSign;
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (!p.getWorld().getName().equals(world.getName()))
						continue;
					String[] lines = new String[] { (bSign ? "§5" : "§3") + "CubiTech", "§6Hallo, ", "§f" + p.getName(),
							(bSign ? "§5" : "§3") + "CubiTech", };
					for (int i = 0; i < lines.length; i++) {
						if (lines[i].length() > 15) {
							lines[i] = lines[i].substring(0, 15);
						}
					}
					if (p.getLocation().distance(loc1) <= 5) {
						p.sendBlockChange(s1.getLocation(), s1.getTypeId(), s1.getData().getData());

						((CraftPlayer) p).getHandle().playerConnection.sendPacket(new Packet130UpdateSign((int) s1.getX(), (int) s1.getY(), (int) s1
								.getZ(), lines));
					}
				}
			}
		}

		Location loc2 = new Location(world, 979, 128, 1013);
		if (loc2.getBlock() == null) {
			// There is no Sign
		} else {
			if (loc2.getBlock().getState() instanceof Sign) {
				Sign s2 = (Sign) loc2.getBlock().getState();
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (!p.getWorld().getName().equals(world.getName()))
						continue;
					ChatColor color = ChatColor.GRAY;
					if (CubiTechUtil.getPlayerClass(p) != null) {
						if (CubiTechUtil.isPlayerAdmin(p)) {
							color = ChatColor.DARK_RED;
						} else if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Ritter")) {
							color = ChatColor.DARK_GREEN;
						} else if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Adeliger")) {
							color = ChatColor.GOLD;
						} else if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("YouTuber")) {
							color = ChatColor.DARK_AQUA;
						} else if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Supporter")) {
							color = ChatColor.RED;
						}
					}

					String[] lines = new String[] { " ", "§5§l§nKlasse:, ", color + getPlayerClass(p), " " };
					for (int i = 0; i < lines.length; i++) {
						if (lines[i].length() > 15) {
							lines[i] = lines[i].substring(0, 15);
						}
					}
					if (p.getLocation().distance(loc2) <= 5) {
						p.sendBlockChange(s2.getLocation(), s2.getTypeId(), s2.getData().getData());
						((CraftPlayer) p).getHandle().playerConnection.sendPacket(new Packet130UpdateSign((int) s2.getX(), (int) s2.getY(), (int) s2
								.getZ(), lines));
					}
				}
			}

			Location loc3 = new Location(world, 979, 126, 1013);
			if (loc3.getBlock() == null) {
				// There is no Sign
			} else {
				if (loc3.getBlock().getState() instanceof Sign) {
					Sign s3 = (Sign) loc3.getBlock().getState();
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (!p.getWorld().getName().equals(world.getName()))
							continue;
						String faction = "Keine";
						if (CubiTechUtil.getPlayerFaction(p).equalsIgnoreCase("grass")) {
							faction = "§aSolaner";
						} else if (CubiTechUtil.getPlayerFaction(p).equalsIgnoreCase("sand")) {
							faction = "§6Pyrer";
						} else if (CubiTechUtil.getPlayerFaction(p).equalsIgnoreCase("snow")) {
							faction = "§3Arctiker";
						}

						String[] lines = new String[] { " ", "§5§l§nVolk:, ", faction, " " };
						for (int i = 0; i < lines.length; i++) {
							if (lines[i].length() > 15) {
								lines[i] = lines[i].substring(0, 15);
							}
						}
						if (p.getLocation().distance(loc3) <= 5) {
							p.sendBlockChange(s3.getLocation(), s3.getTypeId(), s3.getData().getData());
							((CraftPlayer) p).getHandle().playerConnection.sendPacket(new Packet130UpdateSign((int) s3.getX(), (int) s3.getY(),
									(int) s3.getZ(), lines));
						}
					}
				}

			}

		}

		Location loc4 = new Location(world, 978, 127, 1013);
		if (loc4.getBlock() == null) {
			// There is no Sign
		} else {
			if (loc4.getBlock().getState() instanceof Sign) {
				Sign s4 = (Sign) loc4.getBlock().getState();
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (!p.getWorld().getName().equals(world.getName()))
						continue;
					String[] lines = new String[] { " ", "§2§l§nEP:, ", "§a" + getPlayerEP(p), " " };
					for (int i = 0; i < lines.length; i++) {
						if (lines[i].length() > 15) {
							lines[i] = lines[i].substring(0, 15);
						}
					}
					if (p.getLocation().distance(loc4) <= 5) {
						p.sendBlockChange(s4.getLocation(), s4.getTypeId(), s4.getData().getData());
						((CraftPlayer) p).getHandle().playerConnection.sendPacket(new Packet130UpdateSign((int) s4.getX(), (int) s4.getY(), (int) s4
								.getZ(), lines));
					}
				}
			}

		}

		Location loc5 = new Location(world, 980, 127, 1013);
		if (loc5.getBlock() == null) {
			// There is no Sign
		} else {
			if (loc5.getBlock().getState() instanceof Sign) {
				Sign s5 = (Sign) loc5.getBlock().getState();
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (!p.getWorld().getName().equals(world.getName()))
						continue;
					String[] lines = new String[] { " ", "§2§l§nGeld:, ", "§e" + getPlayerMoney(p) + " Cubi", " " };
					for (int i = 0; i < lines.length; i++) {
						if (lines[i].length() > 15) {
							lines[i] = lines[i].substring(0, 15);
						}
					}
					if (p.getLocation().distance(loc5) <= 5) {
						p.sendBlockChange(s5.getLocation(), s5.getTypeId(), s5.getData().getData());
						((CraftPlayer) p).getHandle().playerConnection.sendPacket(new Packet130UpdateSign((int) s5.getX(), (int) s5.getY(), (int) s5
								.getZ(), lines));
					}
				}
			}

		}

		Location loc6 = new Location(world, 979, 127, 1013);
		if (loc6.getBlock() == null) {
			// There is no Sign
		} else {
			if (loc6.getBlock().getState() instanceof Sign) {
				Sign s6 = (Sign) loc6.getBlock().getState();
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (!p.getWorld().getName().equals(world.getName()))
						continue;
					String[] lines = new String[] { " ", "§2§l§nLevel:, ", "§6" + getPlayerLevel(p), " " };
					for (int i = 0; i < lines.length; i++) {
						if (lines[i].length() > 15) {
							lines[i] = lines[i].substring(0, 15);
						}
					}
					if (p.getLocation().distance(loc6) <= 5) {
						p.sendBlockChange(s6.getLocation(), s6.getTypeId(), s6.getData().getData());
						((CraftPlayer) p).getHandle().playerConnection.sendPacket(new Packet130UpdateSign((int) s6.getX(), (int) s6.getY(), (int) s6
								.getZ(), lines));
					}
				}
			}
		}

	}

	public static void placeSupplyBlocks() {
		BufferedReader br = null;
		try {
			File f = new File("plugins//CubiTech//supplies.txt");
			if (!f.exists()) {
				f.createNewFile();
				plugin.getLogger().info("No supplies File found. Not Setting any Supplies.");
				return;
			}
			br = new BufferedReader(new FileReader(f));
			int countGrass = 0;
			int countSand = 0;
			int countSnow = 0;
			int countOther = 0;
			String line;
			World world = plugin.getServer().getWorld("world");
			while ((line = br.readLine()) != null) {
				Location loc = new Location(world, Double.valueOf(line.split(" ")[0]), Double.valueOf(line.split(" ")[1]), Double.valueOf(line
						.split(" ")[2]));
				loc.getBlock().setTypeId(33);
				loc.getBlock().setData((byte) 15);

				Biome b = world.getBiome(loc.getBlockX(), loc.getBlockZ());
				if (b == Biome.PLAINS)
					countGrass++;
				else if (b == Biome.DESERT)
					countSand++;
				else if (b == Biome.ICE_PLAINS)
					countSnow++;
			}
			plugin.getLogger().info("Placed all Supplies.");
			plugin.getLogger().info("Supplies in Grass Area: " + countGrass);
			plugin.getLogger().info("Supplies in Sand Area: " + countSand);
			plugin.getLogger().info("Supplies in Snow Area: " + countSnow);
			if (countOther == 0) {
				plugin.getLogger().info("None in other Areas.");
			} else {
				plugin.getLogger().info("Supplies in Other Areas: " + countOther + " (Why?)");
			}

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

	}

	public static void removeSupplyFromFile(Block block) {
		BufferedReader br = null;
		PrintWriter pw = null;
		try {
			File f = new File("plugins//CubiTech//supplies.txt");
			File tempFile = new File(f.getAbsolutePath() + ".tmp");
			br = new BufferedReader(new FileReader(f));
			pw = new PrintWriter(new FileWriter(tempFile));

			World world = Bukkit.getWorld("world");
			String line = null;
			while ((line = br.readLine()) != null) {

				Location loc = new Location(world, Double.valueOf(line.split(" ")[0]), Double.valueOf(line.split(" ")[1]), Double.valueOf(line
						.split(" ")[2]));

				if (!block.getLocation().equals(loc)) {
					pw.println(line);
					pw.flush();

				} else {
					plugin.getLogger().info("Removed a Supply from File.");
				}

			}

			if (!f.delete()) {
				plugin.getLogger().info("Could not delete file");
				return;
			}

			if (!tempFile.renameTo(f))
				plugin.getLogger().info("Could not rename file");

		} catch (Exception ex) {
			ex.printStackTrace();
			plugin.getLogger().info("Failed to remove Supply from File!");
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (pw != null) {
				pw.close();
			}
		}
	}

	public static Location getSpawnLocation() {
		File f = new File("plugins//CubiTech//spawn.txt");
		try {
			if (!f.exists()) {
				f.createNewFile();
				return null;
			}

			Properties props = new Properties();
			BufferedInputStream stream = new BufferedInputStream(new FileInputStream(f));
			props.load(stream);
			stream.close();

			String sSpawn;
			sSpawn = props.getProperty("spawn");

			Location spawn = new Location(plugin.getServer().getWorld("world"), Double.valueOf(sSpawn.split(" ")[0]), Double.valueOf(sSpawn
					.split(" ")[1]), Double.valueOf(sSpawn.split(" ")[2]), Float.valueOf(sSpawn.split(" ")[3]), Float.valueOf(sSpawn.split(" ")[4]));

			return spawn;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void saveBank(Player p, Inventory inv) {

		int num = Integer.valueOf(inv.getName().replaceFirst("Bank: Fach ", ""));

		File dir = new File("plugins//CubiTech//bank//");
		if (!dir.exists()) {
			dir.mkdir();
		}

		File f = new File("plugins//CubiTech//bank//" + p.getName() + "." + num);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				plugin.getLogger().info("Failed to save Bank for Player " + p.getName());
			}
		}

		OutputStream fos = null;
		ObjectOutputStream oos = null;
		try {

			ArrayList<SerializableItemStack> a = new ArrayList<SerializableItemStack>();
			for (int i = 0; i < inv.getContents().length; i++) {
				if (inv.getContents()[i] != null) {
					a.add(new SerializableItemStack(inv.getContents()[i], i));
				}
			}

			fos = new FileOutputStream(f);
			oos = new ObjectOutputStream(fos);

			oos.writeObject(a);
		} catch (IOException e) {
			e.printStackTrace();
			plugin.getLogger().info("Failed to save Bank for Player " + p.getName());
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (oos != null)
					oos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static Inventory loadBank(Player p, int num) {

		File dir = new File("plugins//CubiTech//bank//");
		if (!dir.exists()) {
			dir.mkdir();
		}

		File f = new File("plugins//CubiTech//bank//" + p.getName() + "." + num);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				plugin.getLogger().info("Failed to load Bank for Player " + p.getName());
			}
		}

		Inventory inv = Bukkit.getServer().createInventory(null, 36, "Bank: Fach " + num);

		if (f.length() == 0) {
			return inv;
		}

		InputStream fis = null;
		ObjectInputStream ois = null;

		try {
			fis = new FileInputStream(f);
			ois = new ObjectInputStream(fis);
			@SuppressWarnings("unchecked")
			ArrayList<SerializableItemStack> a = (ArrayList<CubiTechUtil.SerializableItemStack>) ois.readObject();

			for (SerializableItemStack sis : a) {
				inv.setItem(sis.getSlot(), sis.toItemStack());
			}

		} catch (IOException e) {
			e.printStackTrace();
			p.sendMessage("§cDeine Bank-Daten konnten nicht geladen werden.");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			p.sendMessage("§cDeine Bank-Daten konnten nicht geladen werden.");
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
			}
		}

		return inv;
	}

	public static class SerializableItemStack implements Serializable {

		private static final long serialVersionUID = -8038833961488968526L;

		private int typeId;
		private int amount;
		private short durability;
		private HashMap<Integer, Integer> enchantmentIds;

		private String displayName;
		private List<String> lore;

		private int slot;

		public SerializableItemStack(ItemStack is, int slot) {
			this.typeId = is.getTypeId();
			this.amount = is.getAmount();
			this.durability = is.getDurability();
			enchantmentIds = new HashMap<Integer, Integer>();
			for (Enchantment e : is.getEnchantments().keySet()) {
				enchantmentIds.put(e.getId(), is.getEnchantments().get(e));
			}
			if (is.getItemMeta() != null) {
				if (is.getItemMeta().getDisplayName() != null) {
					this.displayName = is.getItemMeta().getDisplayName();
				}
				if (is.getItemMeta().getLore() != null) {
					this.lore = is.getItemMeta().getLore();
				}
			}
			this.slot = slot;
		}

		public ItemStack toItemStack() {
			ItemStack is = new ItemStack(typeId, amount);
			is.setDurability(durability);
			ItemMeta im = is.getItemMeta();
			for (Integer id : enchantmentIds.keySet()) {
				Enchantment e = Enchantment.getById(id);
				im.addEnchant(e, enchantmentIds.get(e.getId()), true);
			}
			im.setDisplayName(displayName);
			im.setLore(lore);
			is.setItemMeta(im);
			return is;
		}

		public int getSlot() {
			return this.slot;
		}

	}

	public static void broadcast() {

		String[] messages = new String[] { "§aDie Klassen §7§oKrieger§f, §2§oRitter§a und §6§oAdeliger§f §akoennen Diamant-Ruestung anziehen.",
				"§aMit §2/bank §akannst Du deine Gegenstaende lagern.", "§aWenn du einem Volk alle Vorraete raubst, erhaeltst Du eine Belohnung.",
				"§aMit §2/gruppe §akannst Du mit anderen Spielern Gruppen bilden.", "§aMit §2/quest §akannst Du eine Quest auswaehlen.",
				"§aMit §2/paket §akannst Du ein Paket an einen Spieler senden.",
				"§aMit §2/verkaufen §akannst du einen Gegenstand zum Verkauf anbieten.",
				"§aMit §2/ask §akannst Du eine Frage an alle online Teammitglieder stellen.",
				"§aMit §2/spawn §akommst Du in die Hauptstadt zurueck.", "§aMit §2/1vs1 §akannst Du 1vs1-Kaempfe starten.",
				"§aMit §2/vote §akannst Du jeden Tag tolle Items erhalten!" };

		String prefix = "§3§l[§6§lTipp§3§l] §f";

		String msg = prefix + messages[new Random().nextInt(messages.length)];

		Bukkit.broadcastMessage(msg);
	}

	public static boolean isInOwnFactionRegion(Player p) {
		String f = getPlayerFaction(p);
		Biome b = p.getWorld().getBiome(p.getLocation().getBlockX(), p.getLocation().getBlockZ());
		if ((b == Biome.PLAINS && f.equalsIgnoreCase("grass")) || (b == Biome.DESERT && f.equalsIgnoreCase("sand"))
				|| (b == Biome.ICE_PLAINS && f.equalsIgnoreCase("snow"))) {
			return true;
		}

		return false;
	}

	public static HashSet<String> playersDoubleEP = new HashSet<String>();

	public static void voteMade(Player p) {
		Bukkit.broadcastMessage("§2§k| §6§lVOTE §2§k| §3§o§l" + p.getName() + " §ahat gevotet und Items erhalten!");
		Bukkit.broadcastMessage("§2§k| §6§lVOTE §0§k| §2§lVote auch Du und erhalte eine Belohung: §c§l§o/vote");
		CubiTechUtil.addPlayerMoney(p, 50);
		Potion potion1 = new Potion(PotionType.REGEN, 1);
		Potion potion2 = new Potion(PotionType.INSTANT_HEAL, 1);
		p.getInventory().addItem(potion1.toItemStack(2), potion2.toItemStack(2), new ItemStack(Material.GOLDEN_APPLE), new ItemStack(Material.COOKED_BEEF, 32));
		final String pName = p.getName();
		playersDoubleEP.add(pName);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				playersDoubleEP.remove(pName);
			}
		}, 36000L);
		p.sendMessage("§6§lDanke fuers Voten!");
		p.sendMessage("§6§lVote auch morgen wieder!");
		p.sendMessage("§a§l§oDu hast erhalten:");
		p.sendMessage("§2- §a§l32x§2§lSteak");
		p.sendMessage("§2- §a§l5x§2§lRegeneationstrank");
		p.sendMessage("§2- §a§l5x§2§lHealtrank");
		p.sendMessage("§2- §a§l5x§2§lGoldapfel");
		p.sendMessage("§2- §a§l50 Cubi");
		p.sendMessage("§2- §a§l100 EP");
		p.sendMessage("§2- §a§l30 Minuten Doppelt EP fuer das Toeten von Monstern");
	}

	public static void checkPlayerMountHorse(Player p) {
		if (CubiTechUtil.playersMountedHorse.containsKey(p)) {
			if (CubiTechUtil.playersMountedHorse.get(p).getBukkitEntity().getPassenger() == null) {
				CubiTechUtil.playersMountedHorse.get(p).die();
			}
		}
	}

	public static EntityHorse mountHorse(Player p) {

		Item armor = Item.HORSE_ARMOR_IRON;
		int fast = 0;

		int lvl = CubiTechUtil.getPlayerLevel(p);
		if (lvl >= 60) {
			armor = Item.HORSE_ARMOR_DIAMOND;
			fast = 3;
		} else if (lvl >= 50) {
			armor = Item.HORSE_ARMOR_DIAMOND;
			fast = 2;
		} else if (lvl >= 40) {
			armor = Item.HORSE_ARMOR_GOLD;
			fast = 2;
		} else if (lvl >= 30) {
			armor = Item.HORSE_ARMOR_IRON;
			fast = 1;
		} else if (lvl >= 25) {
			armor = Item.HORSE_ARMOR_IRON;
			fast = 1;
		} else if (lvl >= 25) {
			armor = Item.HORSE_ARMOR_IRON;
			fast = 0;
		}

		try {
			net.minecraft.server.v1_6_R2.World nmsWorld = ((CraftWorld) p.getWorld()).getHandle();
			EntityHorse horse = new EntityHorse(nmsWorld);
			horse.setPosition(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
			nmsWorld.addEntity(horse, SpawnReason.CUSTOM);
			Field f = horse.getClass().getDeclaredField("inventoryChest");
			f.setAccessible(true);
			InventoryHorseChest inv = (InventoryHorseChest) f.get(horse);
			inv.setItem(0, new net.minecraft.server.v1_6_R2.ItemStack(Item.SADDLE, 1));
			if (lvl >= 20) {
				inv.setItem(1, new net.minecraft.server.v1_6_R2.ItemStack(armor, 1));
			}
			f.set(horse, inv);
			if (fast > 0) {
				PotionEffect effect = new PotionEffect(PotionEffectType.SPEED, 9999999, fast, true);
				effect.apply((LivingEntity) horse.getBukkitEntity());
			}
			((CraftPlayer) p).getHandle().mount(horse);
			CubiTechUtil.playersMountedHorse.put(p, horse);
			return horse;
		} catch (Exception e) {
			p.sendMessage("§cFehler. Dein Pferd konnte nicht herbeigerufen werden.");
			e.printStackTrace();
		}
		return null;
	}

	public static void checkFlyHorse(Player p) {
		if (p.getGameMode() == GameMode.SURVIVAL) {
			if (p.getWorld().getBiome(p.getLocation().getBlockX(), p.getLocation().getBlockZ()) != Biome.EXTREME_HILLS) {
				p.setAllowFlight(true);
			} else {
				p.setAllowFlight(false);
			}
		}
	}

}