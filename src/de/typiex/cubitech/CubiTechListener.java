package de.typiex.cubitech;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import net.minecraft.server.v1_6_R2.EntityHorse;
import net.minecraft.server.v1_6_R2.EntityPotion;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_6_R2.CraftWorld;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Villager;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

import de.typiex.cubitech.CubiTechUtil.HeadBounty;
import de.typiex.cubitech.CubiTechUtil.KillQuest;
import de.typiex.cubitech.CubiTechUtil.Party;

public class CubiTechListener implements Listener {

	private CubiTech plugin;

	public CubiTechListener(CubiTech instance) {
		this.plugin = instance;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		p.setPlayerTime(0, false);
		e.setJoinMessage(null);
		boolean firstJoin = true;

		// look for level and class
		try {
			File f = new File("plugins//CubiTech//users.txt");
			if (!f.exists())
				f.createNewFile();
			Properties props = new Properties();
			BufferedInputStream stream = new BufferedInputStream(new FileInputStream(f));
			props.load(stream);
			stream.close();
			String s1 = props.getProperty(p.getName());
			if (s1 == null) {
				// user has no class and level yet
				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f, true)));
				out.println(p.getName() + "=Knappe none 1 0 0");
				out.close();
				CubiTechUtil.playerClasses.put(p, "Knappe");
				CubiTechUtil.playerFactions.put(p, "none");
				CubiTechUtil.playerLevels.put(p, 1);
				CubiTechUtil.playerEP.put(p, 0);
				CubiTechUtil.playerMoney.put(p, 0);
				firstJoin = true;
				plugin.getLogger().info("Writing new Player " + p.getName() + " => Class=Knappe Faction=none Level=1 EP=0 Money=0");
			} else {
				String sClass = s1.split(" ")[0];
				String faction = s1.split(" ")[1];
				int level = Integer.valueOf(s1.split(" ")[2]);
				int ep = Integer.valueOf(s1.split(" ")[3]);
				int money = Integer.valueOf(s1.split(" ")[4]);
				CubiTechUtil.playerClasses.put(p, sClass);
				CubiTechUtil.playerFactions.put(p, faction);
				CubiTechUtil.playerLevels.put(p, level);
				CubiTechUtil.playerEP.put(p, ep);
				CubiTechUtil.playerMoney.put(p, money);
				firstJoin = false;
				plugin.getLogger()
						.info("Loaded Player " + p.getName() + " Class=" + sClass + " Faction=" + faction + " Level=" + level + " EP=" + ep);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		CubiTechUtil.playerMana.put(p, 1.0);
		CubiTechUtil.updateLevelBar(p);
		CubiTechUtil.reloadScoreboard();
		CubiTechUtil.updatePlayerSigns();

		Location firstspawn = null;

		if (firstJoin) {
			Properties props = new Properties();
			try {
				BufferedInputStream stream = new BufferedInputStream(new FileInputStream(new File("plugins//CubiTech//spawn.txt")));
				props.load(stream);
				stream.close();

				String sFirstspawn = props.getProperty("firstspawn");
				firstspawn = new Location(plugin.getServer().getWorld("world"), Double.valueOf(sFirstspawn.split(" ")[0]), Double.valueOf(sFirstspawn
						.split(" ")[1]), Double.valueOf(sFirstspawn.split(" ")[2]), Float.valueOf(sFirstspawn.split(" ")[3]),
						Float.valueOf(sFirstspawn.split(" ")[4]));

				p.teleport(firstspawn);
				plugin.getLogger().info("Teleported new Player " + p.getName() + " to FirstSpawn.");
			} catch (Exception ex) {
				p.sendMessage("Fehler. Du konntest nicht zum FirstSpawn teleportiert werden.");
			}

			CubiTechUtil.setStdArmor(p);
			Bukkit.broadcastMessage(ChatColor.DARK_AQUA + p.getName() + ChatColor.AQUA + " ist neu auf CubiTech!");

		}

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

		Bukkit.broadcastMessage(ChatColor.GREEN + "[" + ChatColor.DARK_GREEN + "+" + ChatColor.GREEN + "] " + ChatColor.GRAY + p.getName()
				+ ChatColor.DARK_GRAY + " (" + color + CubiTechUtil.getPlayerClass(p) + lvlColor + ChatColor.ITALIC + " Lvl. "
				+ CubiTechUtil.getPlayerLevel(p) + ChatColor.RESET + ChatColor.DARK_GRAY + ")");

		CubiTechUtil.sendMOTD(p);
		CubiTechUtil.checkFlyHorse(p);

		if (CubiTechUtil.serverListPingIPs.contains(p.getAddress().getAddress().getHostAddress())) {
			// Player has Server in his List :)
			if (!CubiTechUtil.playersGotListPingBonus.contains(p.getName())) {
				CubiTechUtil.addPlayerMoney(p, 50);
				p.sendMessage("§5Du hast §250 Cubi §5erhalten, da Du den Server in deiner Serverliste hast!");
				CubiTechUtil.playersGotListPingBonus.add(p.getName());
			}
		} else {
			// Player doesnt have Server in his List :(
			p.sendMessage("§5Fuege den Server (§2cubitech.eu§5) deiner Serverliste hinzu, um jeden Tag §650 Cubi §5zu erhalten!");
		}

		final Player pp = p;
		final boolean _firstJoin = firstJoin;
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (_firstJoin) {
					CubiTechUtil.doTutorial(pp);
					return;
				}
				Location ll = pp.getLocation();
				for (int i = 0; i <= 5; i++) {
					for (int j = 0; j <= 10; j++) {
						pp.getWorld().playEffect(ll, Effect.ENDER_SIGNAL, j);
					}
					ll.setY(ll.getY() + 0.5);
				}
			}
		}, 20L);

	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		CubiTechUtil.changedNamePlayersToRemove.add(e.getPlayer());
		if (CubiTechUtil.playersMountedHorse.containsKey(p)) {
			CubiTechUtil.playersMountedHorse.get(p).die();
		}
		if (CubiTechUtil.playersTutorial.contains(p)) {
			CubiTechUtil.teleportSpawnInstant(p);
			CubiTechUtil.playersTutorial.remove(p);
		}

		// remove from 1vs1
		if (CubiTechUtil.players1vs1Fighting[0] != null && CubiTechUtil.players1vs1Fighting[1] != null) {
			if (CubiTechUtil.players1vs1Fighting[0].getName().equals(p.getName())) {
				Bukkit.broadcastMessage(ChatColor.GOLD + CubiTechUtil.players1vs1Fighting[1].getName() + ChatColor.YELLOW + " hat gegen "
						+ ChatColor.RED + p.getName() + ChatColor.YELLOW + " verloren.");
				CubiTechUtil.add1vs1Win(CubiTechUtil.players1vs1Fighting[1]);
				CubiTechUtil.add1vs1Fail(CubiTechUtil.players1vs1Fighting[0]);
				CubiTechUtil.players1vs1Fighting[0].setHealth(20.0);
				CubiTechUtil.players1vs1Fighting[1].setHealth(20.0);
				CubiTechUtil.teleportSpawnInstant(CubiTechUtil.players1vs1Fighting[0]);
				CubiTechUtil.teleportSpawnInstant(CubiTechUtil.players1vs1Fighting[1]);
				CubiTechUtil.players1vs1Fighting[0] = null;
				CubiTechUtil.players1vs1Fighting[1] = null;
				CubiTechUtil.check1vs1();
			} else if (CubiTechUtil.players1vs1Fighting[0].getName().equals(p.getName())) {
				Bukkit.broadcastMessage(ChatColor.GOLD + CubiTechUtil.players1vs1Fighting[0].getName() + ChatColor.YELLOW + " hat gegen "
						+ ChatColor.RED + p.getName() + ChatColor.YELLOW + " verloren.");
				CubiTechUtil.add1vs1Win(CubiTechUtil.players1vs1Fighting[0]);
				CubiTechUtil.add1vs1Fail(CubiTechUtil.players1vs1Fighting[1]);
				CubiTechUtil.players1vs1Fighting[0].setHealth(20.0);
				CubiTechUtil.players1vs1Fighting[1].setHealth(20.0);
				CubiTechUtil.teleportSpawnInstant(CubiTechUtil.players1vs1Fighting[0]);
				CubiTechUtil.teleportSpawnInstant(CubiTechUtil.players1vs1Fighting[1]);
				CubiTechUtil.players1vs1Fighting[0] = null;
				CubiTechUtil.players1vs1Fighting[1] = null;
				CubiTechUtil.check1vs1();
			}
		}
		if (CubiTechUtil.players1vs1Waiting.contains(p)) {
			CubiTechUtil.players1vs1Waiting.remove(p);
			CubiTechUtil.check1vs1();
		}

		// remove from Party
		Party party = CubiTechUtil.getPartyByPlayer(p);
		if (party != null) {
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
			}
		}

		CubiTechUtil.saveState();
		e.setQuitMessage(null);

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
			} else if (CubiTechUtil.getPlayerClass(e.getPlayer()).equalsIgnoreCase("Supporter")) {
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

		Bukkit.broadcastMessage(ChatColor.RED + "[" + ChatColor.DARK_RED + "-" + ChatColor.RED + "] " + ChatColor.GRAY + p.getName()
				+ ChatColor.DARK_GRAY + " (" + color + CubiTechUtil.getPlayerClass(p) + lvlColor + ChatColor.ITALIC + " Lvl. "
				+ CubiTechUtil.getPlayerLevel(p) + ChatColor.RESET + ChatColor.DARK_GRAY + ")");
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		e.setCancelled(true);
		CubiTechUtil.updateLevelBar(e.getPlayer());
		Party party = CubiTechUtil.getPartyByPlayer(e.getPlayer());
		if (!(e.getMessage().startsWith("%") && party != null)) {
			boolean checkBadWords = true;
			ChatColor color = ChatColor.GRAY;
			if (CubiTechUtil.isPlayerAdmin(e.getPlayer())) {
				color = ChatColor.DARK_RED;
				checkBadWords = false;
			} else if (CubiTechUtil.getPlayerClass(e.getPlayer()).equalsIgnoreCase("Ritter")) {
				color = ChatColor.DARK_GREEN;
			} else if (CubiTechUtil.getPlayerClass(e.getPlayer()).equalsIgnoreCase("Adeliger")) {
				color = ChatColor.GOLD;
			} else if (CubiTechUtil.getPlayerClass(e.getPlayer()).equalsIgnoreCase("YouTuber")) {
				color = ChatColor.DARK_AQUA;
			} else if (CubiTechUtil.getPlayerClass(e.getPlayer()).equalsIgnoreCase("Supporter")) {
				color = ChatColor.RED;
			}

			ChatColor lvlColor = ChatColor.GRAY;
			if (CubiTechUtil.getPlayerFaction(e.getPlayer()).equalsIgnoreCase("grass")) {
				lvlColor = ChatColor.GREEN;
			} else if (CubiTechUtil.getPlayerFaction(e.getPlayer()).equalsIgnoreCase("sand")) {
				lvlColor = ChatColor.GOLD;
			} else if (CubiTechUtil.getPlayerFaction(e.getPlayer()).equalsIgnoreCase("snow")) {
				lvlColor = ChatColor.DARK_AQUA;
			}

			if (checkBadWords) {
				if (CubiTechUtil.containsBadWords(e.getMessage())) {
					e.getPlayer().sendMessage(ChatColor.RED + "Bitte benutze keine schlimmen Woerter!");
					return;
				}
			}

			String msg = e.getMessage();
			msg = msg.replaceAll("Solaner", "§aSolaner§f").replaceAll("Pyrer", "§6Pyrer§f").replaceAll("Arctiker", "§3Arctiker§f");

			String chat = ChatColor.DARK_GRAY + "[" + color + CubiTechUtil.playerClasses.get(e.getPlayer()) + lvlColor + "" + ChatColor.ITALIC
					+ " Lvl. " + CubiTechUtil.playerLevels.get(e.getPlayer()) + ChatColor.RESET + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY
					+ e.getPlayer().getName() + ChatColor.WHITE + " : " + msg;

			if (CubiTechUtil.isPlayerAdmin(e.getPlayer()) || CubiTechUtil.getPlayerClass(e.getPlayer()).equalsIgnoreCase("Adeliger")) {
				chat = ChatColor.translateAlternateColorCodes('&', chat);
			}

			Bukkit.broadcastMessage(chat);

		} else {
			String chat = (ChatColor.AQUA + "[" + "Gruppe" + "] " + ChatColor.DARK_AQUA + e.getPlayer().getName() + ChatColor.WHITE + " : " + e
					.getMessage()).replaceFirst("%", "");
			if (CubiTechUtil.isPlayerAdmin(e.getPlayer())) {
				chat = ChatColor.translateAlternateColorCodes('&', chat);
			}
			for (Player q : party.getPlayers()) {
				q.sendMessage(chat);
			}
			plugin.getLogger().info(chat);
		}

	}

	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().equalsIgnoreCase("/pl") || e.getMessage().equalsIgnoreCase("/plugins") || e.getMessage().startsWith("/pl ")
				|| e.getMessage().startsWith("/plugins ")) {
			if (!(CubiTechUtil.isPlayerAdmin(e.getPlayer()))) {
				e.getPlayer().sendMessage(ChatColor.DARK_GREEN + "-- Plugins --");
				e.getPlayer().sendMessage(ChatColor.GREEN + "Die Plugins auf Cubitech sind groesstenteils selbstgeschrieben.");
				e.getPlayer().sendMessage(ChatColor.GREEN + "Developed by " + ChatColor.YELLOW + "typiex");
				e.getPlayer().sendMessage(ChatColor.DARK_GREEN + "-- Plugins --");
				e.setCancelled(true);
				return;
			}
		} else if (e.getMessage().equalsIgnoreCase("/help") || e.getMessage().equalsIgnoreCase("/?")) {
			if (!(CubiTechUtil.isPlayerAdmin(e.getPlayer()))) {
				e.setCancelled(true);
				e.getPlayer().chat("/hilfe");
			}
		} else {
			if (!isCmdRegistered(plugin.getServer(), e.getMessage().replaceFirst("/", "").split(" ")[0])) {
				e.setCancelled(true);
				try {
					for (Command cmd : getCommandMap(plugin.getServer()).getCommands()) {
						if (getLevenshteinDistance(cmd.getName(), e.getMessage().replaceFirst("/", "").split(" ")[0]) <= 1) {
							if (cmd.getPermission() != null) {
								if (e.getPlayer().hasPermission(cmd.getPermission())) {
									e.getPlayer().sendMessage(
											ChatColor.RED + "Meintest Du vielleicht " + ChatColor.GOLD + "/" + cmd.getName() + ChatColor.RED + " ?");
									return;
								}
							}
						}
					}
					for (Command cmd : getCommandMap(plugin.getServer()).getCommands()) {
						if (getLevenshteinDistance(cmd.getName(), e.getMessage().replaceFirst("/", "").split(" ")[0]) <= 2) {
							if (cmd.getPermission() != null) {
								if (e.getPlayer().hasPermission(cmd.getPermission())) {
									e.getPlayer().sendMessage(
											ChatColor.RED + "Meintest Du vielleicht " + ChatColor.GOLD + "/" + cmd.getName() + ChatColor.RED + " ?");
									return;
								}
							}
						}
					}
					for (Command cmd : getCommandMap(plugin.getServer()).getCommands()) {
						for (String s : cmd.getAliases()) {
							if (getLevenshteinDistance(s, e.getMessage().replaceFirst("/", "").split(" ")[0]) <= 1) {
								if (cmd.getPermission() != null) {
									if (e.getPlayer().hasPermission(cmd.getPermission())) {
										e.getPlayer().sendMessage(
												ChatColor.RED + "Meintest Du vielleicht " + ChatColor.GOLD + "/" + s + ChatColor.RED + " ?");
										return;
									}
								}
							}
						}
					}
					for (Command cmd : getCommandMap(plugin.getServer()).getCommands()) {
						for (String s : cmd.getAliases()) {
							if (getLevenshteinDistance(s, e.getMessage().replaceFirst("/", "").split(" ")[0]) <= 2) {
								if (cmd.getPermission() != null) {
									if (e.getPlayer().hasPermission(cmd.getPermission())) {
										e.getPlayer().sendMessage(
												ChatColor.RED + "Meintest Du vielleicht " + ChatColor.GOLD + "/" + s + ChatColor.RED + " ?");
										return;
									}
								}
							}
						}
					}
					e.getPlayer().sendMessage(ChatColor.RED + "Unbekannter Befehl.");
				} catch (Exception e1) {
					e1.printStackTrace();
					e.getPlayer().sendMessage("Fehler. CommandMap konnte nicht geladen werden.");
				}
			}
		}
	}

	public int getLevenshteinDistance(String s, String t) {
		if (s == null || t == null) {
			throw new IllegalArgumentException("Strings must not be null");
		}
		int n = s.length(); // length of s
		int m = t.length(); // length of t

		if (n == 0) {
			return m;
		} else if (m == 0) {
			return n;
		}

		int p[] = new int[n + 1]; // 'previous' cost array, horizontally
		int d[] = new int[n + 1]; // cost array, horizontally
		int _d[]; // placeholder to assist in swapping p and d

		// indexes into strings s and t
		int i; // iterates through s
		int j; // iterates through t

		char t_j; // jth character of t

		int cost; // cost

		for (i = 0; i <= n; i++) {
			p[i] = i;
		}

		for (j = 1; j <= m; j++) {
			t_j = t.charAt(j - 1);
			d[0] = j;

			for (i = 1; i <= n; i++) {
				cost = s.charAt(i - 1) == t_j ? 0 : 1;
				// minimum of cell to the left+1, to the top+1, diagonally left
				// and up +cost
				d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1] + cost);
			}

			// copy current distance counts to 'previous row' distance counts
			_d = p;
			p = d;
			d = _d;
		}

		// our last action in the above loop was to switch d and p, so p now
		// actually has the most recent cost counts
		return p[n];
	}

	private SimpleCommandMap getCommandMap(Server svr) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException,
			SecurityException {
		if (svr.getPluginManager() instanceof SimplePluginManager) {
			final Field f = SimplePluginManager.class.getDeclaredField("commandMap");
			f.setAccessible(true);

			return (SimpleCommandMap) f.get(svr.getPluginManager());
		} else {
			plugin.getLogger().info("Failed to handle CommandMap!");
			return null;
		}
	}

	private boolean isCmdRegistered(Server svr, String name) {
		try {
			return (getCommandMap(svr).getCommand(name) != null);
		} catch (Exception e) {
			e.printStackTrace();
			plugin.getLogger().info("Failed to handle CommandMap!");
		}
		return false;
	}

	@EventHandler
	public void onPlayerExpChange(PlayerExpChangeEvent e) {
		e.setAmount(0);
		CubiTechUtil.updateLevelBar(e.getPlayer());
	}

	@EventHandler
	public void onPlayerLevelChange(PlayerLevelChangeEvent e) {
		CubiTechUtil.updateLevelBar(e.getPlayer());
	}

	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
		try {
			if (CubiTechUtil.npcAusbilder == (LivingEntity) e.getRightClicked()) {
				e.setCancelled(true);
				if (CubiTechUtil.getPlayerClass(e.getPlayer()).equalsIgnoreCase("Knappe") || CubiTechUtil.isPlayerAdmin(e.getPlayer())) {
					CubiTechUtil.chooseClass(e.getPlayer());
				} else {
					e.getPlayer().sendMessage(ChatColor.RED + "Du hast deine Klasse bereits gewaehlt!");
				}
			} else if (CubiTechUtil.npcAnwerber == (LivingEntity) e.getRightClicked()) {
				e.setCancelled(true);
				if (CubiTechUtil.getPlayerFaction(e.getPlayer()).equalsIgnoreCase("none") || CubiTechUtil.getPlayerFaction(e.getPlayer()) == null
						|| CubiTechUtil.isPlayerAdmin(e.getPlayer())) {
					CubiTechUtil.chooseFaction(e.getPlayer());
				} else {
					e.getPlayer().sendMessage(ChatColor.RED + "Du hast dein Volk bereits gewaehlt!");
				}
			} else if (CubiTechUtil.npcVerkaeufer == (LivingEntity) e.getRightClicked()) {
				e.setCancelled(true);
				CubiTechUtil.openPlayerShop(e.getPlayer());
			} else if (CubiTechUtil.npcsWaechter.contains((LivingEntity) e.getRightClicked())) {
				e.setCancelled(true);
			}
		} catch (Exception ex) {
		}

		if (e.getRightClicked() instanceof Player) {
			// RIGHTCLICK
			Player target = (Player) e.getRightClicked();
			Player player = e.getPlayer();

			Biome playerBiome = player.getWorld().getBiome(player.getLocation().getBlockX(), player.getLocation().getBlockZ());
			if (playerBiome == Biome.EXTREME_HILLS) {
				// Interact
				boolean in1vs1 = false;
				if (CubiTechUtil.players1vs1Fighting[0] != null && CubiTechUtil.players1vs1Fighting[1] != null) {
					if (CubiTechUtil.players1vs1Fighting[0] == player || CubiTechUtil.players1vs1Fighting[1] == player) {
						in1vs1 = true;
					}
				}

				if (!in1vs1) {
					CubiTechUtil.openPlayerInteract(player, target);
					return;
				}
			}

			if (CubiTechUtil.getPlayerClass(player).equalsIgnoreCase("Schurke") && player.isSneaking()) {
				if (player.getItemInHand().getType() == Material.SHEARS) {

				} else {
					// PickPocket
					if (CubiTechUtil.getPlayerMana(player) >= 0.5) {
						if (!CubiTechUtil.getPlayerFaction(player).equalsIgnoreCase(CubiTechUtil.getPlayerFaction(target))) {
							Biome biome = player.getWorld().getBiome(player.getLocation().getBlockX(), player.getLocation().getBlockZ());
							if (biome != Biome.EXTREME_HILLS) {
								CubiTechUtil.pickPocket(player, target);
								CubiTechUtil.subtractMana(player, 0.5);
							}
						}
					}
				}
			}
			if (CubiTechUtil.getPlayerClass(player).equalsIgnoreCase("Priester")) {
				if (player.getItemInHand().getType() == Material.AIR) {
					if (CubiTechUtil.getPlayerMana(player) >= 0.8) {
						target.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 100, 1), true);
						target.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1), true);
						target.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 1), true);
						CubiTechUtil.subtractMana(player, 0.8);
					}
				} else if (player.getItemInHand().getType() == Material.BLAZE_ROD) {
					if (CubiTechUtil.players1vs1Fighting[0] != null && CubiTechUtil.players1vs1Fighting[1] != null) {
						if (CubiTechUtil.players1vs1Fighting[0] == player || CubiTechUtil.players1vs1Fighting[1] == player) {
							e.setCancelled(false);
							return;
						}
					}

					Biome biome = player.getWorld().getBiome(player.getLocation().getBlockX(), player.getLocation().getBlockZ());
					if (biome == Biome.EXTREME_HILLS) {
						e.setCancelled(true);
						return;
					} else {
						e.setCancelled(false);
					}

					if (CubiTechUtil.getPlayerFaction(player).equalsIgnoreCase(CubiTechUtil.getPlayerFaction(target))) {
						// Dont damage people from your faction
						e.setCancelled(true);
						return;
					} else {
						e.setCancelled(false);
					}

					if (!e.isCancelled()) {
						if (CubiTechUtil.getPlayerMana(player) >= 0.8) {
							target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 80, 1), true);
							target.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 80, 1), true);
							target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80, 1), true);
							CubiTechUtil.subtractMana(player, 0.8);
						}
					}
				}
			}

			if (CubiTechUtil.getPlayerClass(player).equalsIgnoreCase("Magier")) {
				if (player.getItemInHand().getType() == Material.BLAZE_ROD) {

					if (CubiTechUtil.players1vs1Fighting[0] != null && CubiTechUtil.players1vs1Fighting[1] != null) {
						if (CubiTechUtil.players1vs1Fighting[0] == player || CubiTechUtil.players1vs1Fighting[1] == player) {
							e.setCancelled(false);
							return;
						}
					}

					Biome biome = player.getWorld().getBiome(player.getLocation().getBlockX(), player.getLocation().getBlockZ());
					if (biome == Biome.EXTREME_HILLS) {
						e.setCancelled(true);
						return;
					} else {
						e.setCancelled(false);
					}

					if (CubiTechUtil.getPlayerFaction(player).equalsIgnoreCase(CubiTechUtil.getPlayerFaction(target))) {
						// Dont damage people from your faction
						e.setCancelled(true);
						return;
					} else {
						e.setCancelled(false);
					}

					if (!e.isCancelled()) {
						if (CubiTechUtil.getPlayerMana(player) >= 0.8) {
							target.setFireTicks(20);
							CubiTechUtil.subtractMana(player, 0.8);
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		if (e.getPlayer() instanceof Player) {
			Player p = (Player) e.getPlayer();
			CubiTechUtil.checkArmorForLevel(p);
			if (e.getInventory().getName().startsWith("Paket an ")) {
				Player target = Bukkit.getPlayer(e.getInventory().getName().replaceFirst("Paket an ", ""));
				if (target == null) {
					p.sendMessage(ChatColor.RED + "Fehler. Der Spieler " + ChatColor.GOLD + e.getInventory().getName().replaceFirst("Paket an ", "")
							+ ChatColor.RED + " ist nicht online.");
				} else {
					if (e.getInventory().getContents().length > 0 && !CubiTechUtil.isInventoryEmpty(e.getInventory())) {
						p.sendMessage(ChatColor.YELLOW + "Du hast ein Paket an " + ChatColor.GOLD + target.getName() + ChatColor.YELLOW
								+ " versendet.");
						p.sendMessage(ChatColor.YELLOW + "Paket Inhalt:");
						ItemStack[] items = e.getInventory().getContents();
						for (int i = 0; i < items.length; i++) {
							if (items[i] != null) {
								if (items[i].getItemMeta().getDisplayName() != null) {
									p.sendMessage(ChatColor.YELLOW + "- " + ChatColor.GOLD + items[i].getAmount() + " x " + items[i].getType().name()
											+ ChatColor.YELLOW + " (" + ChatColor.GOLD + items[i].getItemMeta().getDisplayName() + ChatColor.YELLOW
											+ ")");
								} else {
									p.sendMessage(ChatColor.YELLOW + "- " + ChatColor.GOLD + items[i].getAmount() + " x " + items[i].getType().name());
								}
							}
						}

						target.sendMessage(ChatColor.YELLOW + "Du hast ein Paket von " + ChatColor.GOLD + p.getName() + ChatColor.YELLOW
								+ " erhalten.");
						target.sendMessage(ChatColor.YELLOW + "Paket Inhalt:");
						for (int i = 0; i < items.length; i++) {
							if (items[i] != null) {
								if (items[i].getItemMeta().getDisplayName() != null) {
									target.sendMessage(ChatColor.YELLOW + "- " + ChatColor.GOLD + items[i].getAmount() + " x "
											+ items[i].getType().name() + ChatColor.YELLOW + " (" + ChatColor.GOLD
											+ items[i].getItemMeta().getDisplayName() + ChatColor.YELLOW + ")");
								} else {
									target.sendMessage(ChatColor.YELLOW + "- " + ChatColor.GOLD + items[i].getAmount() + " x "
											+ items[i].getType().name());
								}
								target.getInventory().addItem(items[i]);
							}
						}

					}
				}

			} else if (e.getInventory().getName().startsWith("Bank: ")) {
				CubiTechUtil.saveBank(p, e.getInventory());
			}
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			CubiTechUtil.checkArmorForLevel(p);

			// Choose Class ?
			if (e.getInventory().getName().equalsIgnoreCase("Waehle eine Klasse:")) {
				ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
				BookMeta b = (BookMeta) book.getItemMeta();
				b.setAuthor(ChatColor.GOLD + "CubiTech Server");
				b.setDisplayName(ChatColor.DARK_GREEN + "Handbuch");
				ArrayList<String> pages = new ArrayList<String>();
				pages.add(ChatColor.GOLD + "-------------------" + "\n" + ChatColor.DARK_GREEN + "            CubiTech" + "\n" + ChatColor.GOLD
						+ "-------------------" + "\n" + "\n" + "\n" + ChatColor.RED + "Seite 1: " + ChatColor.GOLD + "Generell" + "\n"
						+ ChatColor.RED + "Seite 2: " + ChatColor.GOLD + "Voelker" + "\n" + ChatColor.RED + "Seite 3: " + ChatColor.GOLD + "Geld"
						+ "\n" + ChatColor.RED + "Seite 4: " + ChatColor.GOLD + "Vorraete" + "\n\n§5Viel Spass auf CubiTech!");
				pages.add("§c-- §6Generell §c--\n§6Level aufsteigen: §8Wenn Du §a1000 EP §8hast, steigst Du ein Level auf. Das maximale Level ist §a60§8.\nEP erhaeltst Du z.B. durch §3Abschliessen von Quests §8oder §8Toeten von Monstern§8.");
				pages.add("§c-- §6Voelker §c--\n§8Es gibt 3 Voelker, die sich gegenseitig bekriegen.\n§aSolaner§8 - §8Ebene\n§6Pyrer§8 - §8Steppe\n§3Arctiker§8 - §8Tundra");
				pages.add("§c-- §6Geld §c--\n§8Die Waehrung ist §6Cubi§8.\n§8Cubi erhaeltst Du z.B. durch Erledigen von Quests oder Toeten von Monstern.\nDu kannst dir dadurch Sachen im Shop kaufen und mit Spielern handeln.");
				pages.add("§c-- §6Vorraete §c--\n§8Du kannst Vorraete von anderen Voelkern rauben, indem Du in derem Gebiet Vorratsbloecke rechtsklickst.\nJe mehr Vorraete dein Volk hat, desto mehr Geld erhaelt es beim stuendlichen Bonus.");
				b.setPages(pages);
				book.setItemMeta(b);
				if (e.getCurrentItem().getType() == Material.IRON_SWORD) {
					plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), "setclass " + p.getName() + " Krieger");
					plugin.getServer().broadcastMessage(
							ChatColor.GOLD + p.getName() + ChatColor.RED + " ist nun " + ChatColor.AQUA + "Krieger" + ChatColor.RED + "!");
					p.closeInventory();
					CubiTechUtil.setStdArmor(p);
					p.getInventory().addItem(book);
				} else if (e.getCurrentItem().getType() == Material.BLAZE_ROD) {
					plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), "setclass " + p.getName() + " Magier");
					plugin.getServer().broadcastMessage(
							ChatColor.GOLD + p.getName() + ChatColor.RED + " ist nun " + ChatColor.AQUA + "Magier" + ChatColor.RED + "!");
					p.closeInventory();
					CubiTechUtil.setStdArmor(p);
					p.getInventory().addItem(book);
				} else if (e.getCurrentItem().getType() == Material.SHEARS) {
					plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), "setclass " + p.getName() + " Schurke");
					plugin.getServer().broadcastMessage(
							ChatColor.GOLD + p.getName() + ChatColor.RED + " ist nun " + ChatColor.AQUA + "Schurke" + ChatColor.RED + "!");
					p.closeInventory();
					CubiTechUtil.setStdArmor(p);
					p.getInventory().addItem(book);
				} else if (e.getCurrentItem().getType() == Material.BOW) {
					plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), "setclass " + p.getName() + " Jaeger");
					plugin.getServer().broadcastMessage(
							ChatColor.GOLD + p.getName() + ChatColor.RED + " ist nun " + ChatColor.AQUA + "Jaeger" + ChatColor.RED + "!");
					p.closeInventory();
					CubiTechUtil.setStdArmor(p);
					p.getInventory().addItem(book);
				} else if (e.getCurrentItem().getType() == Material.STICK) {
					plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), "setclass " + p.getName() + " Priester");
					plugin.getServer().broadcastMessage(
							ChatColor.GOLD + p.getName() + ChatColor.RED + " ist nun " + ChatColor.AQUA + "Priester" + ChatColor.RED + "!");
					p.closeInventory();
					CubiTechUtil.setStdArmor(p);
					p.getInventory().addItem(book);
				}
			} else if (e.getInventory().getName().equalsIgnoreCase("Waehle eine Quest:")) {
				if (e.getCurrentItem().getItemMeta() != null) {
					if (e.getCurrentItem().getItemMeta().getDisplayName() != null) {
						p.closeInventory();
						String displayName = e.getCurrentItem().getItemMeta().getDisplayName();
						if (displayName.equals(ChatColor.GOLD + "Zombie-Toeter I")) {
							CubiTechUtil.playerQuests.put(p, new KillQuest(p, EntityType.ZOMBIE, 10, 100, 20));
							p.sendMessage(ChatColor.DARK_GREEN + "Du hast die Quest " + ChatColor.GOLD + displayName + ChatColor.DARK_GREEN
									+ " angenommen.");
							p.chat("/quest");
						} else if (displayName.equals(ChatColor.GOLD + "Skelett-Toeter I")) {
							CubiTechUtil.playerQuests.put(p, new KillQuest(p, EntityType.SKELETON, 10, 100, 20));
							p.sendMessage(ChatColor.DARK_GREEN + "Du hast die Quest " + ChatColor.GOLD + displayName + ChatColor.DARK_GREEN
									+ " angenommen.");
							p.chat("/quest");
						} else if (displayName.equals(ChatColor.GOLD + "Creeper-Toeter I")) {
							CubiTechUtil.playerQuests.put(p, new KillQuest(p, EntityType.CREEPER, 10, 100, 20));
							p.sendMessage(ChatColor.DARK_GREEN + "Du hast die Quest " + ChatColor.GOLD + displayName + ChatColor.DARK_GREEN
									+ " angenommen.");
							p.chat("/quest");
						} else if (displayName.equals(ChatColor.GOLD + "Witherskelett-Toeter I")) {
							CubiTechUtil.playerQuests.put(p, new KillQuest(p, EntityType.WITHER_SKULL, 10, 150, 30));
							p.sendMessage(ChatColor.DARK_GREEN + "Du hast die Quest " + ChatColor.GOLD + displayName + ChatColor.DARK_GREEN
									+ " angenommen.");
							p.chat("/quest");
						} else if (displayName.equals(ChatColor.GOLD + "Zombie-Toeter II")) {
							CubiTechUtil.playerQuests.put(p, new KillQuest(p, EntityType.ZOMBIE, 20, 225, 45));
							p.sendMessage(ChatColor.DARK_GREEN + "Du hast die Quest " + ChatColor.GOLD + displayName + ChatColor.DARK_GREEN
									+ " angenommen.");
							p.chat("/quest");
						} else if (displayName.equals(ChatColor.GOLD + "Skelett-Toeter II")) {
							CubiTechUtil.playerQuests.put(p, new KillQuest(p, EntityType.SKELETON, 20, 225, 45));
							p.sendMessage(ChatColor.DARK_GREEN + "Du hast die Quest " + ChatColor.GOLD + displayName + ChatColor.DARK_GREEN
									+ " angenommen.");
							p.chat("/quest");
						} else if (displayName.equals(ChatColor.GOLD + "Creeper-Toeter II")) {
							CubiTechUtil.playerQuests.put(p, new KillQuest(p, EntityType.CREEPER, 20, 225, 45));
							p.sendMessage(ChatColor.DARK_GREEN + "Du hast die Quest " + ChatColor.GOLD + displayName + ChatColor.DARK_GREEN
									+ " angenommen.");
							p.chat("/quest");
						} else if (displayName.equals(ChatColor.GOLD + "Witherskelett-Toeter II")) {
							CubiTechUtil.playerQuests.put(p, new KillQuest(p, EntityType.WITHER_SKULL, 20, 325, 65));
							p.sendMessage(ChatColor.DARK_GREEN + "Du hast die Quest " + ChatColor.GOLD + displayName + ChatColor.DARK_GREEN
									+ " angenommen.");
							p.chat("/quest");
						}
					}
				}
			} else if (e.getInventory().getName().equalsIgnoreCase("Waehle ein Volk:")) {
				p.closeInventory();
				if (e.getCurrentItem().getType() == Material.GRASS) {
					CubiTechUtil.setPlayerFaction(p, "grass");
					CubiTechUtil.saveState();
					p.sendMessage(ChatColor.GREEN + "Du hast die §2Solander §aals Volk gewaehlt.");
				} else if (e.getCurrentItem().getType() == Material.SAND) {
					CubiTechUtil.setPlayerFaction(p, "sand");
					CubiTechUtil.saveState();
					p.sendMessage(ChatColor.GREEN + "Du hast die §2Pyrer §aals Volk gewaehlt.");
				} else if (e.getCurrentItem().getType() == Material.SNOW_BLOCK) {
					CubiTechUtil.setPlayerFaction(p, "snow");
					CubiTechUtil.saveState();
					p.sendMessage(ChatColor.GREEN + "Du hast die §2Arctiker §aals Volk gewaehlt.");
				}
			} else if (e.getInventory().getName().equalsIgnoreCase("Shop:")) {
				p.closeInventory();
				if (e.getCurrentItem().getType() == Material.IRON_SWORD) {
					e.setCurrentItem(null);
					e.setCancelled(true);

					Inventory inv = Bukkit.getServer().createInventory(null, 9, "Shop: Schwerter");

					ItemStack woodSword = new ItemStack(Material.WOOD_SWORD);
					ItemMeta woodSwordMeta = woodSword.getItemMeta();
					woodSwordMeta.setDisplayName(ChatColor.YELLOW + "Gutes Schwert des Reisenden");
					ArrayList<String> woodSwordDesc = new ArrayList<String>();
					woodSwordDesc.add(" ");
					woodSwordDesc.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
					woodSwordDesc.add(" ");
					woodSwordDesc.add(ChatColor.DARK_GRAY + "Preis: 10 Cubi");
					woodSwordDesc.add(" ");
					woodSwordDesc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					woodSwordMeta.addEnchant(Enchantment.DURABILITY, 1, true);
					woodSwordMeta.setLore(woodSwordDesc);
					woodSword.setItemMeta(woodSwordMeta);

					ItemStack goldSword = new ItemStack(Material.GOLD_SWORD);
					ItemMeta goldSwordMeta = goldSword.getItemMeta();
					goldSwordMeta.setDisplayName(ChatColor.GOLD + "Sehr Gutes Schwert des Reisenden");
					ArrayList<String> goldSwordDesc = new ArrayList<String>();
					goldSwordDesc.add(" ");
					goldSwordDesc.add(ChatColor.WHITE + "Stufe: " + ChatColor.GOLD + ChatColor.ITALIC + "Sehr Gut");
					goldSwordDesc.add(" ");
					goldSwordDesc.add(ChatColor.DARK_GRAY + "Preis: 20 Cubi");
					goldSwordDesc.add(" ");
					goldSwordDesc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					goldSwordMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
					goldSwordMeta.addEnchant(Enchantment.DURABILITY, 2, true);
					goldSwordMeta.setLore(goldSwordDesc);
					goldSword.setItemMeta(goldSwordMeta);

					ItemStack stoneSword = new ItemStack(Material.STONE_SWORD);
					ItemMeta stoneSwordMeta = stoneSword.getItemMeta();
					stoneSwordMeta.setDisplayName(ChatColor.GREEN + "Seltenes Schwert des Reisenden");
					ArrayList<String> stoneSwordDesc = new ArrayList<String>();
					stoneSwordDesc.add(" ");
					stoneSwordDesc.add(ChatColor.WHITE + "Stufe: " + ChatColor.GREEN + ChatColor.ITALIC + "Selten");
					stoneSwordDesc.add(" ");
					stoneSwordDesc.add(ChatColor.DARK_GRAY + "Preis: 50 Cubi");
					stoneSwordDesc.add(" ");
					stoneSwordDesc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					stoneSwordMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
					stoneSwordMeta.addEnchant(Enchantment.DAMAGE_UNDEAD, 1, true);
					stoneSwordMeta.setLore(stoneSwordDesc);
					stoneSword.setItemMeta(stoneSwordMeta);

					ItemStack ironSword = new ItemStack(Material.IRON_SWORD);
					ItemMeta ironSwordMeta = ironSword.getItemMeta();
					ironSwordMeta.setDisplayName(ChatColor.DARK_GREEN + "Sehr Seltenes Schwert des Reisenden");
					ArrayList<String> ironSwordDesc = new ArrayList<String>();
					ironSwordDesc.add(" ");
					ironSwordDesc.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_GREEN + ChatColor.ITALIC + "Sehr Selten");
					ironSwordDesc.add(" ");
					ironSwordDesc.add(ChatColor.DARK_GRAY + "Preis: 100 Cubi");
					ironSwordDesc.add(" ");
					ironSwordDesc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					ironSwordMeta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
					goldSwordMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
					goldSwordMeta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
					ironSwordMeta.setLore(ironSwordDesc);
					ironSword.setItemMeta(ironSwordMeta);

					inv.addItem(woodSword, goldSword, stoneSword, ironSword);

					final Player _p = p;
					final Inventory _inv = inv;
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							_p.openInventory(_inv);
						}
					}, 1);
				} else if (e.getCurrentItem().getType() == Material.BOW) {
					e.setCurrentItem(null);
					e.setCancelled(true);

					Inventory inv = Bukkit.getServer().createInventory(null, 9, "Shop: Boegen");

					ItemStack woodbow = new ItemStack(Material.BOW);
					ItemMeta woodbowMeta = woodbow.getItemMeta();
					woodbowMeta.setDisplayName(ChatColor.YELLOW + "Guter Bogen des Reisenden");
					ArrayList<String> woodbowDesc = new ArrayList<String>();
					woodbowDesc.add(" ");
					woodbowDesc.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
					woodbowDesc.add(" ");
					woodbowDesc.add(ChatColor.DARK_GRAY + "Preis: 10 Cubi");
					woodbowDesc.add(" ");
					woodbowDesc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					woodbowMeta.addEnchant(Enchantment.DURABILITY, 1, true);
					woodbowMeta.setLore(woodbowDesc);
					woodbow.setItemMeta(woodbowMeta);

					ItemStack goldbow = new ItemStack(Material.BOW);
					ItemMeta goldbowMeta = goldbow.getItemMeta();
					goldbowMeta.setDisplayName(ChatColor.GOLD + "Sehr Guter Bogen des Reisenden");
					ArrayList<String> goldbowDesc = new ArrayList<String>();
					goldbowDesc.add(" ");
					goldbowDesc.add(ChatColor.WHITE + "Stufe: " + ChatColor.GOLD + ChatColor.ITALIC + "Sehr Gut");
					goldbowDesc.add(" ");
					goldbowDesc.add(ChatColor.DARK_GRAY + "Preis: 20 Cubi");
					goldbowDesc.add(" ");
					goldbowDesc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					goldbowMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
					goldbowMeta.addEnchant(Enchantment.DURABILITY, 2, true);
					goldbowMeta.setLore(goldbowDesc);
					goldbow.setItemMeta(goldbowMeta);

					ItemStack stonebow = new ItemStack(Material.BOW);
					ItemMeta stonebowMeta = stonebow.getItemMeta();
					stonebowMeta.setDisplayName(ChatColor.GREEN + "Seltener Bogen des Reisenden");
					ArrayList<String> stonebowDesc = new ArrayList<String>();
					stonebowDesc.add(" ");
					stonebowDesc.add(ChatColor.WHITE + "Stufe: " + ChatColor.GREEN + ChatColor.ITALIC + "Selten");
					stonebowDesc.add(" ");
					stonebowDesc.add(ChatColor.DARK_GRAY + "Preis: 50 Cubi");
					stonebowDesc.add(" ");
					stonebowDesc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					stonebowMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
					stonebowMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, true);
					stonebowMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
					stonebowMeta.setLore(stonebowDesc);
					stonebow.setItemMeta(stonebowMeta);

					ItemStack ironbow = new ItemStack(Material.BOW);
					ItemMeta ironbowMeta = ironbow.getItemMeta();
					ironbowMeta.setDisplayName(ChatColor.DARK_GREEN + "Sehr Seltener Bogen des Reisenden");
					ArrayList<String> ironbowDesc = new ArrayList<String>();
					ironbowDesc.add(" ");
					ironbowDesc.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_GREEN + ChatColor.ITALIC + "Sehr Selten");
					ironbowDesc.add(" ");
					ironbowDesc.add(ChatColor.DARK_GRAY + "Preis: 100 Cubi");
					ironbowDesc.add(" ");
					ironbowDesc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					ironbowMeta.addEnchant(Enchantment.ARROW_DAMAGE, 2, true);
					ironbowMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, true);
					ironbowMeta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
					ironbowMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
					ironbowMeta.setLore(ironbowDesc);
					ironbow.setItemMeta(ironbowMeta);

					inv.addItem(woodbow, goldbow, stonebow, ironbow);
					final Player _p = p;
					final Inventory _inv = inv;
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							_p.openInventory(_inv);
						}
					}, 1);
				} else if (e.getCurrentItem().getType() == Material.BLAZE_ROD) {
					e.setCurrentItem(null);
					e.setCancelled(true);

					Inventory inv = plugin.getServer().createInventory(null, 9, "Shop: Staebe");

					ItemStack woodrod = new ItemStack(Material.BLAZE_ROD);
					ItemMeta woodrodMeta = woodrod.getItemMeta();
					woodrodMeta.setDisplayName(ChatColor.YELLOW + "Guter Stab des Reisenden");
					ArrayList<String> woodrodDesc = new ArrayList<String>();
					woodrodDesc.add(" ");
					woodrodDesc.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
					woodrodDesc.add(" ");
					woodrodDesc.add(ChatColor.DARK_GRAY + "Preis: 10 Cubi");
					woodrodDesc.add(" ");
					woodrodDesc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					woodrodMeta.addEnchant(Enchantment.DURABILITY, 1, true);
					woodrodMeta.setLore(woodrodDesc);
					woodrod.setItemMeta(woodrodMeta);

					ItemStack goldrod = new ItemStack(Material.BLAZE_ROD);
					ItemMeta goldrodMeta = goldrod.getItemMeta();
					goldrodMeta.setDisplayName(ChatColor.GOLD + "Sehr Guter Stab des Reisenden");
					ArrayList<String> goldrodDesc = new ArrayList<String>();
					goldrodDesc.add(" ");
					goldrodDesc.add(ChatColor.WHITE + "Stufe: " + ChatColor.GOLD + ChatColor.ITALIC + "Sehr Gut");
					goldrodDesc.add(" ");
					goldrodDesc.add(ChatColor.DARK_GRAY + "Preis: 20 Cubi");
					goldrodDesc.add(" ");
					goldrodDesc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					goldrodMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
					goldrodMeta.addEnchant(Enchantment.DURABILITY, 1, true);
					goldrodMeta.setLore(goldrodDesc);
					goldrod.setItemMeta(goldrodMeta);

					ItemStack stonerod = new ItemStack(Material.BLAZE_ROD);
					ItemMeta stonerodMeta = stonerod.getItemMeta();
					stonerodMeta.setDisplayName(ChatColor.GREEN + "Seltener Stab des Reisenden");
					ArrayList<String> stonerodDesc = new ArrayList<String>();
					stonerodDesc.add(" ");
					stonerodDesc.add(ChatColor.WHITE + "Stufe: " + ChatColor.GREEN + ChatColor.ITALIC + "Selten");
					stonerodDesc.add(" ");
					stonerodDesc.add(ChatColor.DARK_GRAY + "Preis: 50 Cubi");
					stonerodDesc.add(" ");
					stonerodDesc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					stonerodMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
					stonerodMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
					stonerodMeta.setLore(stonerodDesc);
					stonerod.setItemMeta(stonerodMeta);

					ItemStack ironrod = new ItemStack(Material.BLAZE_ROD);
					ItemMeta ironrodMeta = ironrod.getItemMeta();
					ironrodMeta.setDisplayName(ChatColor.DARK_GREEN + "Sehr Seltener Stab des Reisenden");
					ArrayList<String> ironrodDesc = new ArrayList<String>();
					ironrodDesc.add(" ");
					ironrodDesc.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_GREEN + ChatColor.ITALIC + "Sehr Selten");
					ironrodDesc.add(" ");
					ironrodDesc.add(ChatColor.DARK_GRAY + "Preis: 100 Cubi");
					ironrodDesc.add(" ");
					ironrodDesc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					ironrodMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
					ironrodMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
					ironrodMeta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
					ironrodMeta.setLore(ironrodDesc);
					ironrod.setItemMeta(ironrodMeta);

					inv.addItem(woodrod, goldrod, stonerod, ironrod);
					final Player _p = p;
					final Inventory _inv = inv;
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							_p.openInventory(_inv);
						}
					}, 1);

				} else if (e.getCurrentItem().getType() == Material.CHAINMAIL_CHESTPLATE) {
					e.setCurrentItem(null);
					e.setCancelled(true);

					Inventory inv = plugin.getServer().createInventory(null, 9, "Shop: Ruestungen");

					ItemStack leather = new ItemStack(Material.LEATHER_CHESTPLATE);
					ItemMeta leatherMeta = leather.getItemMeta();
					leatherMeta.setDisplayName(ChatColor.GOLD + "Leder");
					leather.setItemMeta(leatherMeta);

					ItemStack chainmail = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
					ItemMeta chainmailMeta = chainmail.getItemMeta();
					chainmailMeta.setDisplayName(ChatColor.GOLD + "Kette");
					chainmail.setItemMeta(chainmailMeta);

					ItemStack gold = new ItemStack(Material.GOLD_CHESTPLATE);
					ItemMeta goldMeta = gold.getItemMeta();
					goldMeta.setDisplayName(ChatColor.DARK_AQUA + "" + ChatColor.UNDERLINE + "Ruestung");
					goldMeta.setDisplayName(ChatColor.GOLD + "Gold");
					gold.setItemMeta(goldMeta);

					ItemStack iron = new ItemStack(Material.IRON_CHESTPLATE);
					ItemMeta ironMeta = iron.getItemMeta();
					ironMeta.setDisplayName(ChatColor.GOLD + "Eisen");
					iron.setItemMeta(ironMeta);

					ItemStack diamond = new ItemStack(Material.DIAMOND_CHESTPLATE);
					ItemMeta diamondMeta = diamond.getItemMeta();
					diamondMeta.setDisplayName(ChatColor.GOLD + "Diamant");
					diamond.setItemMeta(diamondMeta);

					inv.setItem(2, leather);
					inv.setItem(3, chainmail);
					inv.setItem(4, gold);
					inv.setItem(5, iron);
					inv.setItem(6, diamond);

					final Player _p = p;
					final Inventory _inv = inv;
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							_p.openInventory(_inv);
						}
					}, 1);
				} else if (e.getCurrentItem().getType() == Material.POTION) {
					e.setCurrentItem(null);
					e.setCancelled(true);

					Inventory inv = plugin.getServer().createInventory(null, 18, "Shop: Traenke");

					ItemStack[] potions = new ItemStack[] { new Potion(PotionType.INSTANT_HEAL).toItemStack(1),
							new Potion(PotionType.REGEN).toItemStack(1), new Potion(PotionType.STRENGTH).toItemStack(1),
							new Potion(PotionType.SPEED).toItemStack(1), new Potion(PotionType.FIRE_RESISTANCE).toItemStack(1),
							new Potion(PotionType.NIGHT_VISION).toItemStack(1) };

					for (int i = 0; i < potions.length; i++) {
						ItemMeta meta = potions[i].getItemMeta();
						ArrayList<String> desc = new ArrayList<String>();
						desc.add(" ");
						desc.add(ChatColor.GRAY + "Preis: 100 Cubi");
						meta.setLore(desc);
						potions[i].setItemMeta(meta);
					}

					ItemStack[] potions2 = new ItemStack[] { new Potion(PotionType.INSTANT_DAMAGE).splash().toItemStack(1),
							new Potion(PotionType.POISON).splash().toItemStack(1), new Potion(PotionType.WEAKNESS).splash().toItemStack(1),
							new Potion(PotionType.SLOWNESS).splash().toItemStack(1) };

					for (int i = 0; i < potions2.length; i++) {
						ItemMeta meta = potions2[i].getItemMeta();
						ArrayList<String> desc = new ArrayList<String>();
						desc.add(" ");
						desc.add(ChatColor.GRAY + "Preis: 200 Cubi");
						meta.setLore(desc);
						potions2[i].setItemMeta(meta);
					}

					inv.addItem(potions);
					for (int i = 0; i < potions2.length; i++) {
						inv.setItem(9 + i, potions2[i]);
					}

					final Player _p = p;
					final Inventory _inv = inv;
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							_p.openInventory(_inv);
						}
					}, 1);
				}

			} else if (e.getInventory().getName().equalsIgnoreCase("Shop: Schwerter")) {
				p.closeInventory();
				if (e.getCurrentItem().getType() == Material.WOOD_SWORD) {
					ItemStack woodSword = new ItemStack(Material.WOOD_SWORD);
					ItemMeta woodSwordMeta = woodSword.getItemMeta();
					woodSwordMeta.setDisplayName(ChatColor.YELLOW + "Gutes Schwert des Reisenden");
					ArrayList<String> woodSwordDesc = new ArrayList<String>();
					woodSwordDesc.add(" ");
					woodSwordDesc.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
					woodSwordDesc.add(" ");
					woodSwordDesc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					woodSwordMeta.addEnchant(Enchantment.DURABILITY, 1, true);
					woodSwordMeta.setLore(woodSwordDesc);
					woodSword.setItemMeta(woodSwordMeta);
					if (CubiTechUtil.getPlayerMoney(p) >= 10) {
						CubiTechUtil.subtractPlayerMoney(p, 10);
						p.getInventory().addItem(woodSword);
						p.sendMessage(ChatColor.DARK_GREEN + "Du hast " + ChatColor.WHITE + woodSwordMeta.getDisplayName() + ChatColor.DARK_GREEN
								+ " fuer §610 Cubi §2gekauft.");
					} else {
						p.sendMessage(ChatColor.RED + "Du hast leider nicht genug Geld!");
					}
				} else if (e.getCurrentItem().getType() == Material.GOLD_SWORD) {
					ItemStack goldSword = new ItemStack(Material.GOLD_SWORD);
					ItemMeta goldSwordMeta = goldSword.getItemMeta();
					goldSwordMeta.setDisplayName(ChatColor.GOLD + "Sehr Gutes Schwert des Reisenden");
					ArrayList<String> goldSwordDesc = new ArrayList<String>();
					goldSwordDesc.add(" ");
					goldSwordDesc.add(ChatColor.WHITE + "Stufe: " + ChatColor.GOLD + ChatColor.ITALIC + "Sehr Gut");
					goldSwordDesc.add(" ");
					goldSwordDesc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					goldSwordMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
					goldSwordMeta.addEnchant(Enchantment.DURABILITY, 2, true);
					goldSwordMeta.setLore(goldSwordDesc);
					goldSword.setItemMeta(goldSwordMeta);
					if (CubiTechUtil.getPlayerMoney(p) >= 20) {
						CubiTechUtil.subtractPlayerMoney(p, 20);
						p.getInventory().addItem(goldSword);
						p.sendMessage(ChatColor.DARK_GREEN + "Du hast " + ChatColor.WHITE + goldSwordMeta.getDisplayName() + ChatColor.DARK_GREEN
								+ " fuer §620 Cubi §2gekauft.");
					} else {
						p.sendMessage(ChatColor.RED + "Du hast leider nicht genug Geld!");
					}
				} else if (e.getCurrentItem().getType() == Material.STONE_SWORD) {
					ItemStack stoneSword = new ItemStack(Material.STONE_SWORD);
					ItemMeta stoneSwordMeta = stoneSword.getItemMeta();
					stoneSwordMeta.setDisplayName(ChatColor.GREEN + "Seltenes Schwert des Reisenden");
					ArrayList<String> stoneSwordDesc = new ArrayList<String>();
					stoneSwordDesc.add(" ");
					stoneSwordDesc.add(ChatColor.WHITE + "Stufe: " + ChatColor.GREEN + ChatColor.ITALIC + "Selten");
					stoneSwordDesc.add(" ");
					stoneSwordDesc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					stoneSwordMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
					stoneSwordMeta.addEnchant(Enchantment.DAMAGE_UNDEAD, 1, true);
					stoneSwordMeta.setLore(stoneSwordDesc);
					stoneSword.setItemMeta(stoneSwordMeta);
					if (CubiTechUtil.getPlayerMoney(p) >= 50) {
						CubiTechUtil.subtractPlayerMoney(p, 50);
						p.getInventory().addItem(stoneSword);
						p.sendMessage(ChatColor.DARK_GREEN + "Du hast " + ChatColor.WHITE + stoneSwordMeta.getDisplayName() + ChatColor.DARK_GREEN
								+ " fuer §650 Cubi §2gekauft.");
					} else {
						p.sendMessage(ChatColor.RED + "Du hast leider nicht genug Geld!");
					}
				} else if (e.getCurrentItem().getType() == Material.IRON_SWORD) {
					ItemStack ironSword = new ItemStack(Material.IRON_SWORD);
					ItemMeta ironSwordMeta = ironSword.getItemMeta();
					ironSwordMeta.setDisplayName(ChatColor.DARK_GREEN + "Sehr Seltenes Schwert des Reisenden");
					ArrayList<String> ironSwordDesc = new ArrayList<String>();
					ironSwordDesc.add(" ");
					ironSwordDesc.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_GREEN + ChatColor.ITALIC + "Sehr Selten");
					ironSwordDesc.add(" ");
					ironSwordDesc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					ironSwordMeta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
					ironSwordMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
					ironSwordMeta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
					ironSwordMeta.setLore(ironSwordDesc);
					ironSword.setItemMeta(ironSwordMeta);
					if (CubiTechUtil.getPlayerMoney(p) >= 100) {
						CubiTechUtil.subtractPlayerMoney(p, 100);
						p.getInventory().addItem(ironSword);
						p.sendMessage(ChatColor.DARK_GREEN + "Du hast " + ChatColor.WHITE + ironSwordMeta.getDisplayName() + ChatColor.DARK_GREEN
								+ " fuer §6100 Cubi §2gekauft.");
					} else {
						p.sendMessage(ChatColor.RED + "Du hast leider nicht genug Geld!");
					}
				}
			} else if (e.getInventory().getName().equalsIgnoreCase("Shop: Boegen")) {
				p.closeInventory();
				if (e.getSlot() == 0) { // Gut
					ItemStack woodbow = new ItemStack(Material.BOW);
					ItemMeta woodbowMeta = woodbow.getItemMeta();
					woodbowMeta.setDisplayName(ChatColor.YELLOW + "Guter Bogen des Reisenden");
					ArrayList<String> woodbowDesc = new ArrayList<String>();
					woodbowDesc.add(" ");
					woodbowDesc.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
					woodbowDesc.add(" ");
					woodbowDesc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					woodbowMeta.addEnchant(Enchantment.DURABILITY, 1, true);
					woodbowMeta.setLore(woodbowDesc);
					woodbow.setItemMeta(woodbowMeta);

					if (CubiTechUtil.getPlayerMoney(p) >= 10) {
						CubiTechUtil.subtractPlayerMoney(p, 10);
						p.getInventory().addItem(woodbow);
						p.sendMessage(ChatColor.DARK_GREEN + "Du hast " + ChatColor.WHITE + woodbowMeta.getDisplayName() + ChatColor.DARK_GREEN
								+ " fuer §610 Cubi §2gekauft.");
					} else {
						p.sendMessage(ChatColor.RED + "Du hast leider nicht genug Geld!");
					}
				} else if (e.getSlot() == 1) { // Sehr Gut
					ItemStack goldbow = new ItemStack(Material.BOW);
					ItemMeta goldbowMeta = goldbow.getItemMeta();
					goldbowMeta.setDisplayName(ChatColor.GOLD + "Sehr Guter Bogen des Reisenden");
					ArrayList<String> goldbowDesc = new ArrayList<String>();
					goldbowDesc.add(" ");
					goldbowDesc.add(ChatColor.WHITE + "Stufe: " + ChatColor.GOLD + ChatColor.ITALIC + "Sehr Gut");
					goldbowDesc.add(" ");
					goldbowDesc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					goldbowMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
					goldbowMeta.addEnchant(Enchantment.DURABILITY, 2, true);
					goldbowMeta.setLore(goldbowDesc);
					goldbow.setItemMeta(goldbowMeta);

					if (CubiTechUtil.getPlayerMoney(p) >= 20) {
						CubiTechUtil.subtractPlayerMoney(p, 20);
						p.getInventory().addItem(goldbow);
						p.sendMessage(ChatColor.DARK_GREEN + "Du hast " + ChatColor.WHITE + goldbowMeta.getDisplayName() + ChatColor.DARK_GREEN
								+ " fuer §620 Cubi §2gekauft.");
					} else {
						p.sendMessage(ChatColor.RED + "Du hast leider nicht genug Geld!");
					}

				} else if (e.getSlot() == 2) { // Selten
					ItemStack stonebow = new ItemStack(Material.BOW);
					ItemMeta stonebowMeta = stonebow.getItemMeta();
					stonebowMeta.setDisplayName(ChatColor.GREEN + "Seltener Bogen des Reisenden");
					ArrayList<String> stonebowDesc = new ArrayList<String>();
					stonebowDesc.add(" ");
					stonebowDesc.add(ChatColor.WHITE + "Stufe: " + ChatColor.GREEN + ChatColor.ITALIC + "Selten");
					stonebowDesc.add(" ");
					stonebowDesc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					stonebowMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
					stonebowMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, true);
					stonebowMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
					stonebowMeta.setLore(stonebowDesc);
					stonebow.setItemMeta(stonebowMeta);

					if (CubiTechUtil.getPlayerMoney(p) >= 50) {
						CubiTechUtil.subtractPlayerMoney(p, 50);
						p.getInventory().addItem(stonebow);
						p.sendMessage(ChatColor.DARK_GREEN + "Du hast " + ChatColor.WHITE + stonebowMeta.getDisplayName() + ChatColor.DARK_GREEN
								+ " fuer §650 Cubi §2gekauft.");
					} else {
						p.sendMessage(ChatColor.RED + "Du hast leider nicht genug Geld!");
					}

				} else if (e.getSlot() == 3) { // Sehr Selten
					ItemStack ironbow = new ItemStack(Material.BOW);
					ItemMeta ironbowMeta = ironbow.getItemMeta();
					ironbowMeta.setDisplayName(ChatColor.DARK_GREEN + "Sehr Seltener Bogen des Reisenden");
					ArrayList<String> ironbowDesc = new ArrayList<String>();
					ironbowDesc.add(" ");
					ironbowDesc.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_GREEN + ChatColor.ITALIC + "Sehr Selten");
					ironbowDesc.add(" ");
					ironbowDesc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					ironbowMeta.addEnchant(Enchantment.ARROW_DAMAGE, 2, true);
					ironbowMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, true);
					ironbowMeta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
					ironbowMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
					ironbowMeta.setLore(ironbowDesc);
					ironbow.setItemMeta(ironbowMeta);

					if (CubiTechUtil.getPlayerMoney(p) >= 100) {
						CubiTechUtil.subtractPlayerMoney(p, 100);
						p.getInventory().addItem(ironbow);
						p.sendMessage(ChatColor.DARK_GREEN + "Du hast " + ChatColor.WHITE + ironbowMeta.getDisplayName() + ChatColor.DARK_GREEN
								+ " fuer §6100 Cubi §2gekauft.");
					} else {
						p.sendMessage(ChatColor.RED + "Du hast leider nicht genug Geld!");
					}
				}
			} else if (e.getInventory().getName().equalsIgnoreCase("Shop: Staebe")) {
				p.closeInventory();
				if (e.getSlot() == 0) { // Gut
					ItemStack woodrod = new ItemStack(Material.BLAZE_ROD);
					ItemMeta woodrodMeta = woodrod.getItemMeta();
					woodrodMeta.setDisplayName(ChatColor.YELLOW + "Guter Bogen des Reisenden");
					ArrayList<String> woodrodDesc = new ArrayList<String>();
					woodrodDesc.add(" ");
					woodrodDesc.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
					woodrodDesc.add(" ");
					woodrodDesc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					woodrodMeta.addEnchant(Enchantment.DURABILITY, 1, true);
					woodrodMeta.setLore(woodrodDesc);
					woodrod.setItemMeta(woodrodMeta);

					if (CubiTechUtil.getPlayerMoney(p) >= 10) {
						CubiTechUtil.subtractPlayerMoney(p, 10);
						p.getInventory().addItem(woodrod);
						p.sendMessage(ChatColor.DARK_GREEN + "Du hast " + ChatColor.WHITE + woodrodMeta.getDisplayName() + ChatColor.DARK_GREEN
								+ " fuer §610 Cubi §2gekauft.");
					} else {
						p.sendMessage(ChatColor.RED + "Du hast leider nicht genug Geld!");
					}

				} else if (e.getSlot() == 1) { // Sehr Gut
					ItemStack goldrod = new ItemStack(Material.BLAZE_ROD);
					ItemMeta goldrodMeta = goldrod.getItemMeta();
					goldrodMeta.setDisplayName(ChatColor.GOLD + "Sehr Guter Stab des Reisenden");
					ArrayList<String> goldrodDesc = new ArrayList<String>();
					goldrodDesc.add(" ");
					goldrodDesc.add(ChatColor.WHITE + "Stufe: " + ChatColor.GOLD + ChatColor.ITALIC + "Sehr Gut");
					goldrodDesc.add(" ");
					goldrodDesc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					goldrodMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
					goldrodMeta.addEnchant(Enchantment.DURABILITY, 1, true);
					goldrodMeta.setLore(goldrodDesc);
					goldrod.setItemMeta(goldrodMeta);

					if (CubiTechUtil.getPlayerMoney(p) >= 20) {
						CubiTechUtil.subtractPlayerMoney(p, 20);
						p.getInventory().addItem(goldrod);
						p.sendMessage(ChatColor.DARK_GREEN + "Du hast " + ChatColor.WHITE + goldrodMeta.getDisplayName() + ChatColor.DARK_GREEN
								+ " fuer §620 Cubi §2gekauft.");
					} else {
						p.sendMessage(ChatColor.RED + "Du hast leider nicht genug Geld!");
					}

				} else if (e.getSlot() == 2) { // Selten
					ItemStack stonerod = new ItemStack(Material.BLAZE_ROD);
					ItemMeta stonerodMeta = stonerod.getItemMeta();
					stonerodMeta.setDisplayName(ChatColor.GREEN + "Seltener Stab des Reisenden");
					ArrayList<String> stonerodDesc = new ArrayList<String>();
					stonerodDesc.add(" ");
					stonerodDesc.add(ChatColor.WHITE + "Stufe: " + ChatColor.GREEN + ChatColor.ITALIC + "Selten");
					stonerodDesc.add(" ");
					stonerodDesc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					stonerodMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
					stonerodMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
					stonerodMeta.setLore(stonerodDesc);
					stonerod.setItemMeta(stonerodMeta);

					if (CubiTechUtil.getPlayerMoney(p) >= 50) {
						CubiTechUtil.subtractPlayerMoney(p, 50);
						p.getInventory().addItem(stonerod);
						p.sendMessage(ChatColor.DARK_GREEN + "Du hast " + ChatColor.WHITE + stonerodMeta.getDisplayName() + ChatColor.DARK_GREEN
								+ " fuer §650 Cubi §2gekauft.");
					} else {
						p.sendMessage(ChatColor.RED + "Du hast leider nicht genug Geld!");
					}

				} else if (e.getSlot() == 3) { // Sehr Selten
					ItemStack ironrod = new ItemStack(Material.BLAZE_ROD);
					ItemMeta ironrodMeta = ironrod.getItemMeta();
					ironrodMeta.setDisplayName(ChatColor.DARK_GREEN + "Sehr Seltener Stab des Reisenden");
					ArrayList<String> ironrodDesc = new ArrayList<String>();
					ironrodDesc.add(" ");
					ironrodDesc.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_GREEN + ChatColor.ITALIC + "Sehr Selten");
					ironrodDesc.add(" ");
					ironrodDesc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					ironrodMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
					ironrodMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
					ironrodMeta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
					ironrodMeta.setLore(ironrodDesc);
					ironrod.setItemMeta(ironrodMeta);

					if (CubiTechUtil.getPlayerMoney(p) >= 100) {
						CubiTechUtil.subtractPlayerMoney(p, 100);
						p.getInventory().addItem(ironrod);
						p.sendMessage(ChatColor.DARK_GREEN + "Du hast " + ChatColor.WHITE + ironrodMeta.getDisplayName() + ChatColor.DARK_GREEN
								+ " fuer §6100 Cubi §2gekauft.");
					} else {
						p.sendMessage(ChatColor.RED + "Du hast leider nicht genug Geld!");
					}
				}

			} else if (e.getInventory().getName().equalsIgnoreCase("Shop: Traenke")) {
				p.closeInventory();
				if (e.getSlot() <= 5) {
					if (CubiTechUtil.getPlayerMoney(p) >= 100) {
						ItemStack item = e.getCurrentItem();
						ItemMeta meta = item.getItemMeta();
						ArrayList<String> desc = new ArrayList<String>();
						meta.setLore(desc);
						item.setItemMeta(meta);
						CubiTechUtil.subtractPlayerMoney(p, 100);
						p.getInventory().addItem(e.getCurrentItem());
						p.sendMessage(ChatColor.DARK_GREEN + "Du hast einen " + ChatColor.GOLD + "Trank" + ChatColor.DARK_GREEN
								+ " fuer §6100 Cubi §2gekauft.");
					} else {
						p.sendMessage(ChatColor.RED + "Du hast leider nicht genug Geld!");
					}
				} else if (e.getSlot() >= 9 && e.getSlot() <= 12) {
					if (CubiTechUtil.getPlayerMoney(p) >= 200) {
						ItemStack item = e.getCurrentItem();
						ItemMeta meta = item.getItemMeta();
						ArrayList<String> desc = new ArrayList<String>();
						meta.setLore(desc);
						item.setItemMeta(meta);
						CubiTechUtil.subtractPlayerMoney(p, 200);
						p.getInventory().addItem(e.getCurrentItem());
						p.sendMessage(ChatColor.DARK_GREEN + "Du hast einen " + ChatColor.GOLD + "Wurftrank" + ChatColor.DARK_GREEN
								+ " fuer §6200 Cubi §2gekauft.");
					} else {
						p.sendMessage(ChatColor.RED + "Du hast leider nicht genug Geld!");
					}
				}
			} else if (e.getInventory().getName().equalsIgnoreCase("Shop: Ruestungen")) {
				p.closeInventory();
				Material m = e.getCurrentItem().getType();

				Material mHelmet = null;
				Material mChestplate = null;
				Material mLeggings = null;
				Material mBoots = null;
				int price = 0;

				if (m == Material.LEATHER_CHESTPLATE) {
					mHelmet = Material.LEATHER_HELMET;
					mChestplate = Material.LEATHER_CHESTPLATE;
					mLeggings = Material.LEATHER_LEGGINGS;
					mBoots = Material.LEATHER_BOOTS;
					price = 60;
				} else if (m == Material.CHAINMAIL_CHESTPLATE) {
					mHelmet = Material.CHAINMAIL_HELMET;
					mChestplate = Material.CHAINMAIL_CHESTPLATE;
					mLeggings = Material.CHAINMAIL_LEGGINGS;
					mBoots = Material.CHAINMAIL_BOOTS;
					price = 150;
				} else if (m == Material.GOLD_CHESTPLATE) {
					mHelmet = Material.GOLD_HELMET;
					mChestplate = Material.GOLD_CHESTPLATE;
					mLeggings = Material.GOLD_LEGGINGS;
					mBoots = Material.GOLD_BOOTS;
					price = 200;
				} else if (m == Material.IRON_CHESTPLATE) {
					mHelmet = Material.IRON_HELMET;
					mChestplate = Material.IRON_CHESTPLATE;
					mLeggings = Material.IRON_LEGGINGS;
					mBoots = Material.IRON_BOOTS;
					price = 350;
				} else if (m == Material.DIAMOND_CHESTPLATE) {
					mHelmet = Material.DIAMOND_HELMET;
					mChestplate = Material.DIAMOND_CHESTPLATE;
					mLeggings = Material.DIAMOND_LEGGINGS;
					mBoots = Material.DIAMOND_BOOTS;
					price = 500;
				} else {
					return;
				}

				if (m != Material.AIR && m != null) {
					Inventory inv = Bukkit.getServer().createInventory(null, 36, "Shop: Ruestung");

					ItemStack helmet1 = new ItemStack(mHelmet);
					ItemMeta helmet1Meta = helmet1.getItemMeta();
					helmet1Meta.setDisplayName(ChatColor.YELLOW + "Guter Helm des Reisenden");
					ArrayList<String> helmet1Desc = new ArrayList<String>();
					helmet1Desc.add(" ");
					helmet1Desc.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
					helmet1Desc.add(" ");
					helmet1Desc.add(ChatColor.DARK_GRAY + "Preis: " + (int) (price));
					helmet1Desc.add(" ");
					helmet1Desc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					helmet1Meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
					helmet1Meta.setLore(helmet1Desc);
					helmet1.setItemMeta(helmet1Meta);

					ItemStack helmet2 = new ItemStack(mHelmet);
					ItemMeta helmet2Meta = helmet2.getItemMeta();
					helmet2Meta.setDisplayName(ChatColor.GOLD + "Sehr Guter Helm des Reisenden");
					ArrayList<String> helmet2Desc = new ArrayList<String>();
					helmet2Desc.add(" ");
					helmet2Desc.add(ChatColor.WHITE + "Stufe: " + ChatColor.GOLD + ChatColor.ITALIC + "Sehr Gut");
					helmet2Desc.add(" ");
					helmet2Desc.add(ChatColor.DARK_GRAY + "Preis: " + (int) (price * 1.5));
					helmet2Desc.add(" ");
					helmet2Desc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					helmet2Meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
					helmet2Meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 1, true);
					helmet2Meta.setLore(helmet2Desc);
					helmet2.setItemMeta(helmet2Meta);

					ItemStack helmet3 = new ItemStack(mHelmet);
					ItemMeta helmet3Meta = helmet3.getItemMeta();
					helmet3Meta.setDisplayName(ChatColor.GREEN + "Seltener Helm des Reisenden");
					ArrayList<String> helmet3Desc = new ArrayList<String>();
					helmet3Desc.add(" ");
					helmet3Desc.add(ChatColor.WHITE + "Stufe: " + ChatColor.GREEN + ChatColor.ITALIC + "Selten");
					helmet3Desc.add(" ");
					helmet3Desc.add(ChatColor.DARK_GRAY + "Preis: " + (int) (price * 2));
					helmet3Desc.add(" ");
					helmet3Desc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					helmet3Meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
					helmet3Meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 1, true);
					helmet3Meta.setLore(helmet3Desc);
					helmet3.setItemMeta(helmet3Meta);

					ItemStack helmet4 = new ItemStack(mHelmet);
					ItemMeta helmet4Meta = helmet4.getItemMeta();
					helmet4Meta.setDisplayName(ChatColor.DARK_GREEN + "Sehr Seltener Helm des Reisenden");
					ArrayList<String> helmet4Desc = new ArrayList<String>();
					helmet4Desc.add(" ");
					helmet4Desc.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_GREEN + ChatColor.ITALIC + "Sehr Selten");
					helmet4Desc.add(" ");
					helmet4Desc.add(ChatColor.DARK_GRAY + "Preis: " + (int) (price * 2.5));
					helmet4Desc.add(" ");
					helmet4Desc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					helmet4Meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
					helmet4Meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 1, true);
					helmet4Meta.addEnchant(Enchantment.PROTECTION_FIRE, 1, true);
					helmet4Meta.setLore(helmet4Desc);
					helmet4.setItemMeta(helmet4Meta);

					ItemStack chestplate1 = new ItemStack(mChestplate);
					ItemMeta chestplate1Meta = chestplate1.getItemMeta();
					chestplate1Meta.setDisplayName(ChatColor.YELLOW + "Guter Harnisch des Reisenden");
					ArrayList<String> chestplate1Desc = new ArrayList<String>();
					chestplate1Desc.add(" ");
					chestplate1Desc.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
					chestplate1Desc.add(" ");
					chestplate1Desc.add(ChatColor.DARK_GRAY + "Preis: " + (int) (price));
					chestplate1Desc.add(" ");
					chestplate1Desc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					chestplate1Meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
					chestplate1Meta.setLore(chestplate1Desc);
					chestplate1.setItemMeta(chestplate1Meta);

					ItemStack chestplate2 = new ItemStack(mChestplate);
					ItemMeta chestplate2Meta = chestplate2.getItemMeta();
					chestplate2Meta.setDisplayName(ChatColor.GOLD + "Sehr Guter Harnisch des Reisenden");
					ArrayList<String> chestplate2Desc = new ArrayList<String>();
					chestplate2Desc.add(" ");
					chestplate2Desc.add(ChatColor.WHITE + "Stufe: " + ChatColor.GOLD + ChatColor.ITALIC + "Sehr Gut");
					chestplate2Desc.add(" ");
					chestplate2Desc.add(ChatColor.DARK_GRAY + "Preis: " + (int) (price * 1.5));
					chestplate2Desc.add(" ");
					chestplate2Desc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					chestplate2Meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
					chestplate2Meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 1, true);
					chestplate2Meta.setLore(chestplate2Desc);
					chestplate2.setItemMeta(chestplate2Meta);

					ItemStack chestplate3 = new ItemStack(mChestplate);
					ItemMeta chestplate3Meta = chestplate3.getItemMeta();
					chestplate3Meta.setDisplayName(ChatColor.GREEN + "Seltener Harnisch des Reisenden");
					ArrayList<String> chestplate3Desc = new ArrayList<String>();
					chestplate3Desc.add(" ");
					chestplate3Desc.add(ChatColor.WHITE + "Stufe: " + ChatColor.GREEN + ChatColor.ITALIC + "Selten");
					chestplate3Desc.add(" ");
					chestplate3Desc.add(ChatColor.DARK_GRAY + "Preis: " + (int) (price * 2));
					chestplate3Desc.add(" ");
					chestplate3Desc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					chestplate3Meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
					chestplate3Meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 1, true);
					chestplate3Meta.setLore(chestplate3Desc);
					chestplate3.setItemMeta(chestplate3Meta);

					ItemStack chestplate4 = new ItemStack(mChestplate);
					ItemMeta chestplate4Meta = chestplate4.getItemMeta();
					chestplate4Meta.setDisplayName(ChatColor.DARK_GREEN + "Sehr Seltener Harnisch des Reisenden");
					ArrayList<String> chestplate4Desc = new ArrayList<String>();
					chestplate4Desc.add(" ");
					chestplate4Desc.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_GREEN + ChatColor.ITALIC + "Sehr Selten");
					chestplate4Desc.add(" ");
					chestplate4Desc.add(ChatColor.DARK_GRAY + "Preis: " + (int) (price * 2.5));
					chestplate4Desc.add(" ");
					chestplate4Desc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					chestplate4Meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
					chestplate4Meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 1, true);
					chestplate4Meta.addEnchant(Enchantment.PROTECTION_FIRE, 1, true);
					chestplate4Meta.setLore(chestplate4Desc);
					chestplate4.setItemMeta(chestplate4Meta);

					ItemStack leggings1 = new ItemStack(mLeggings);
					ItemMeta leggings1Meta = leggings1.getItemMeta();
					leggings1Meta.setDisplayName(ChatColor.YELLOW + "Gute Hose des Reisenden");
					ArrayList<String> leggings1Desc = new ArrayList<String>();
					leggings1Desc.add(" ");
					leggings1Desc.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
					leggings1Desc.add(" ");
					leggings1Desc.add(ChatColor.DARK_GRAY + "Preis: " + (int) (price));
					leggings1Desc.add(" ");
					leggings1Desc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					leggings1Meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
					leggings1Meta.setLore(leggings1Desc);
					leggings1.setItemMeta(leggings1Meta);

					ItemStack leggings2 = new ItemStack(mLeggings);
					ItemMeta leggings2Meta = leggings2.getItemMeta();
					leggings2Meta.setDisplayName(ChatColor.GOLD + "Sehr Gute Hose des Reisenden");
					ArrayList<String> leggings2Desc = new ArrayList<String>();
					leggings2Desc.add(" ");
					leggings2Desc.add(ChatColor.WHITE + "Stufe: " + ChatColor.GOLD + ChatColor.ITALIC + "Sehr Gut");
					leggings2Desc.add(" ");
					leggings2Desc.add(ChatColor.DARK_GRAY + "Preis: " + (int) (price * 1.5));
					leggings2Desc.add(" ");
					leggings2Desc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					leggings2Meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
					leggings2Meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 1, true);
					leggings2Meta.setLore(leggings2Desc);
					leggings2.setItemMeta(leggings2Meta);

					ItemStack leggings3 = new ItemStack(mLeggings);
					ItemMeta leggings3Meta = leggings3.getItemMeta();
					leggings3Meta.setDisplayName(ChatColor.GREEN + "Seltene Hose des Reisenden");
					ArrayList<String> leggings3Desc = new ArrayList<String>();
					leggings3Desc.add(" ");
					leggings3Desc.add(ChatColor.WHITE + "Stufe: " + ChatColor.GREEN + ChatColor.ITALIC + "Selten");
					leggings3Desc.add(" ");
					leggings3Desc.add(ChatColor.DARK_GRAY + "Preis: " + (int) (price * 2));
					leggings3Desc.add(" ");
					leggings3Desc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					leggings3Meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
					leggings3Meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 1, true);
					leggings3Meta.setLore(leggings3Desc);
					leggings3.setItemMeta(leggings3Meta);

					ItemStack leggings4 = new ItemStack(mLeggings);
					ItemMeta leggings4Meta = leggings4.getItemMeta();
					leggings4Meta.setDisplayName(ChatColor.DARK_GREEN + "Sehr Seltene Hose des Reisenden");
					ArrayList<String> leggings4Desc = new ArrayList<String>();
					leggings4Desc.add(" ");
					leggings4Desc.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_GREEN + ChatColor.ITALIC + "Sehr Selten");
					leggings4Desc.add(" ");
					leggings4Desc.add(ChatColor.DARK_GRAY + "Preis: " + (int) (price * 2.5));
					leggings4Desc.add(" ");
					leggings4Desc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					leggings4Meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
					leggings4Meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 1, true);
					leggings4Meta.addEnchant(Enchantment.PROTECTION_FIRE, 1, true);
					leggings4Meta.setLore(leggings4Desc);
					leggings4.setItemMeta(leggings4Meta);

					ItemStack boots1 = new ItemStack(mBoots);
					ItemMeta boots1Meta = boots1.getItemMeta();
					boots1Meta.setDisplayName(ChatColor.YELLOW + "Gute Schuhe des Reisenden");
					ArrayList<String> boots1Desc = new ArrayList<String>();
					boots1Desc.add(" ");
					boots1Desc.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
					boots1Desc.add(" ");
					boots1Desc.add(ChatColor.DARK_GRAY + "Preis: " + (int) (price));
					boots1Desc.add(" ");
					boots1Desc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					boots1Meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
					boots1Meta.setLore(boots1Desc);
					boots1.setItemMeta(boots1Meta);

					ItemStack boots2 = new ItemStack(mBoots);
					ItemMeta boots2Meta = boots2.getItemMeta();
					boots2Meta.setDisplayName(ChatColor.GOLD + "Sehr Gute Schuhe des Reisenden");
					ArrayList<String> boots2Desc = new ArrayList<String>();
					boots2Desc.add(" ");
					boots2Desc.add(ChatColor.WHITE + "Stufe: " + ChatColor.GOLD + ChatColor.ITALIC + "Sehr Gut");
					boots2Desc.add(" ");
					boots2Desc.add(ChatColor.DARK_GRAY + "Preis: " + (int) (price * 1.5));
					boots2Desc.add(" ");
					boots2Desc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					boots2Meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
					boots2Meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 1, true);
					boots2Meta.setLore(boots2Desc);
					boots2.setItemMeta(boots2Meta);

					ItemStack boots3 = new ItemStack(mBoots);
					ItemMeta boots3Meta = boots3.getItemMeta();
					boots3Meta.setDisplayName(ChatColor.GREEN + "Seltene Schuhe des Reisenden");
					ArrayList<String> boots3Desc = new ArrayList<String>();
					boots3Desc.add(" ");
					boots3Desc.add(ChatColor.WHITE + "Stufe: " + ChatColor.GREEN + ChatColor.ITALIC + "Selten");
					boots3Desc.add(" ");
					boots3Desc.add(ChatColor.DARK_GRAY + "Preis: " + (int) (price * 2));
					boots3Desc.add(" ");
					boots3Desc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					boots3Meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
					boots3Meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 1, true);
					boots3Meta.setLore(boots3Desc);
					boots3.setItemMeta(boots3Meta);

					ItemStack boots4 = new ItemStack(mBoots);
					ItemMeta boots4Meta = boots4.getItemMeta();
					boots4Meta.setDisplayName(ChatColor.DARK_GREEN + "Sehr Seltene Schuhe des Reisenden");
					ArrayList<String> boots4Desc = new ArrayList<String>();
					boots4Desc.add(" ");
					boots4Desc.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_GREEN + ChatColor.ITALIC + "Sehr Selten");
					boots4Desc.add(" ");
					boots4Desc.add(ChatColor.DARK_GRAY + "Preis: " + (int) (price * 2.5));
					boots4Desc.add(" ");
					boots4Desc.add(ChatColor.GRAY + "## " + ChatColor.DARK_GRAY + "CubiTech" + ChatColor.GRAY + " ##");
					boots4Meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
					boots4Meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 1, true);
					boots4Meta.addEnchant(Enchantment.PROTECTION_FIRE, 1, true);
					boots4Meta.setLore(boots4Desc);
					boots4.setItemMeta(boots4Meta);

					inv.setItem(0, boots1);
					inv.setItem(1, leggings1);
					inv.setItem(2, chestplate1);
					inv.setItem(3, helmet1);

					inv.setItem(9, boots2);
					inv.setItem(10, leggings2);
					inv.setItem(11, chestplate2);
					inv.setItem(12, helmet2);

					inv.setItem(18, boots3);
					inv.setItem(19, leggings3);
					inv.setItem(20, chestplate3);
					inv.setItem(21, helmet3);

					inv.setItem(27, boots4);
					inv.setItem(28, leggings4);
					inv.setItem(29, chestplate4);
					inv.setItem(30, helmet4);

					final Player _p = p;
					final Inventory _inv = inv;
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							_p.openInventory(_inv);
						}
					}, 1);

				}

			} else if (e.getInventory().getName().equalsIgnoreCase("Shop: Ruestung")) {
				p.closeInventory();
				if (e.getCurrentItem().hasItemMeta()) {
					try {
						ItemMeta meta = e.getCurrentItem().getItemMeta();
						List<String> desc = meta.getLore();
						for (String s : desc) {
							if (s.startsWith(ChatColor.DARK_GRAY + "Preis: ")) {
								String sPrice = s.replaceFirst(ChatColor.DARK_GRAY + "Preis: ", "");
								int price = Integer.valueOf(sPrice);
								if (CubiTechUtil.getPlayerMoney(p) > price) {
									p.getInventory().addItem(e.getCurrentItem());
									CubiTechUtil.subtractPlayerMoney(p, price);
									p.sendMessage(ChatColor.DARK_GREEN + "Du hast " + ChatColor.WHITE + meta.getDisplayName() + ChatColor.DARK_GREEN
											+ " fuer §6" + price + " Cubi §2gekauft.");
								} else {
									p.sendMessage(ChatColor.RED + "Du hast leider nicht genug Geld!");
								}
							}
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						p.sendMessage(ChatColor.RED + "Fehler. Item konnte nicht gekauft werden.");
					}
				}
			} else if (e.getInventory().getName().startsWith("Interaktion: ")) {
				p.closeInventory();
				Player target = Bukkit.getPlayerExact(e.getInventory().getName().replaceFirst("Interaktion: ", ""));
				if (e.getCurrentItem() != null && target != null) {
					if (e.getCurrentItem().getType() == Material.SKULL_ITEM) {
						p.chat("/gruppe einladen " + target.getName());
					} else if (e.getCurrentItem().getType() == Material.STICK) {
						target.playSound(target.getLocation(), Sound.NOTE_PLING, 5F, 1F);
						target.sendMessage("§eDu wurdest von " + p.getName() + " angestupst.");
						p.sendMessage("§eDu hast " + target.getName() + " angestupst.");
					}
				}
			}
		}
	}

	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent e) {
		if (e.getPlayer() instanceof Player) {
			Player p = (Player) e.getPlayer();
			CubiTechUtil.checkArmorForLevel(p);
			if (e.getInventory().getHolder() instanceof Chest) {
				Chest c = (Chest) e.getInventory().getHolder();
				if (c.getBlock().getRelative(BlockFace.DOWN).getType() == Material.EMERALD_BLOCK) {
					if (CubiTechUtil.isPlayerAdmin(p)) {
						p.sendMessage(ChatColor.YELLOW + "Du editierst eine Bonus Kiste, da Du Admin bist.");
					} else {
						e.setCancelled(true);
						Inventory i = plugin.getServer().createInventory(null, 27, "Bonuskiste");
						if (!CubiTechUtil.playersChestOpened.containsKey(p)) {
							e.getView().close();
							HashSet<Chest> newHashSet = new HashSet<Chest>();
							newHashSet.add(c);
							CubiTechUtil.playersChestOpened.put(p, newHashSet);
							i.setContents(c.getInventory().getContents());
						} else {
							if (!CubiTechUtil.playersChestOpened.get(p).contains(c)) {
								CubiTechUtil.playersChestOpened.get(p).add(c);
								i.setContents(c.getInventory().getContents());
							}
						}
						p.openInventory(i);
					}
				}
			} else if (e.getInventory().getHolder() instanceof Dispenser || e.getInventory().getHolder() instanceof Dropper) {
				if (!CubiTechUtil.isPlayerAdmin(p)) {
					e.setCancelled(true);
				}
			}

		}
	}

	@EventHandler
	public void onPlayerItemHeld(PlayerItemHeldEvent e) {
		CubiTechUtil.checkArmorForLevel(e.getPlayer());
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent e) {

	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		CubiTechUtil.playersKillstreak.put(e.getEntity(), 0);
		e.setDeathMessage(null);
		boolean byPlayer = false;
		if (e.getEntity().getKiller() != null) {
			byPlayer = true;
			ChatColor lvlColorPlayer = ChatColor.GRAY;
			if (CubiTechUtil.getPlayerFaction(e.getEntity()).equalsIgnoreCase("grass")) {
				lvlColorPlayer = ChatColor.GREEN;
			} else if (CubiTechUtil.getPlayerFaction(e.getEntity()).equalsIgnoreCase("sand")) {
				lvlColorPlayer = ChatColor.GOLD;
			} else if (CubiTechUtil.getPlayerFaction(e.getEntity()).equalsIgnoreCase("snow")) {
				lvlColorPlayer = ChatColor.DARK_AQUA;
			}

			ChatColor lvlColorKiller = ChatColor.GRAY;
			if (CubiTechUtil.getPlayerFaction(e.getEntity().getKiller()).equalsIgnoreCase("grass")) {
				lvlColorKiller = ChatColor.GREEN;
			} else if (CubiTechUtil.getPlayerFaction(e.getEntity().getKiller()).equalsIgnoreCase("sand")) {
				lvlColorKiller = ChatColor.GOLD;
			} else if (CubiTechUtil.getPlayerFaction(e.getEntity().getKiller()).equalsIgnoreCase("snow")) {
				lvlColorKiller = ChatColor.DARK_AQUA;
			}

			Bukkit.broadcastMessage(lvlColorPlayer + e.getEntity().getName() + ChatColor.GRAY + " wurde von " + lvlColorKiller
					+ e.getEntity().getKiller().getName() + ChatColor.GRAY + " mit " + ChatColor.AQUA
					+ e.getEntity().getKiller().getItemInHand().getType().name().toLowerCase() + ChatColor.GRAY + " getoetet.");

			int streak = 0;
			if (CubiTechUtil.playersKillstreak.containsKey(e.getEntity().getKiller())) {
				streak = CubiTechUtil.playersKillstreak.get(e.getEntity().getKiller());
			}
			streak++;
			CubiTechUtil.playersKillstreak.put(e.getEntity().getKiller(), streak);

			if (streak == 10) {
				Bukkit.broadcastMessage(lvlColorKiller + e.getEntity().getKiller().getName() + "§c hat eine Kill-Serie von §610$c!");
			}

			String factionKiller = CubiTechUtil.getPlayerFaction(e.getEntity().getKiller());
			String factionPlayer = CubiTechUtil.getPlayerFaction(e.getEntity());

			if (!factionKiller.equalsIgnoreCase(factionPlayer)) {
				// Killed Player from other Faction
				if (factionKiller.equalsIgnoreCase("grass")) {
					CubiTechUtil.suppliesGrass++;
				} else if (factionKiller.equalsIgnoreCase("sand")) {
					CubiTechUtil.suppliesSand++;
				} else if (factionKiller.equalsIgnoreCase("snow")) {
					CubiTechUtil.suppliesSnow++;
				}
			}

			HashSet<HeadBounty> toRemove = new HashSet<HeadBounty>();

			for (HeadBounty hb : CubiTechUtil.headBounties) {
				if (hb.target.getName().equals(e.getEntity().getName())) {
					toRemove.add(hb);
					CubiTechUtil.addPlayerMoney(e.getEntity().getKiller(), hb.price);
					CubiTechUtil.subtractPlayerMoney(hb.player, hb.price);
					Bukkit.broadcastMessage("§a" + e.getEntity().getKiller().getName() + "§c hat §b" + hb.price + " Cubi §cKopfgeld von §6"
							+ hb.player.getName() + " §cerhalten.");
				}
			}

			CubiTechUtil.headBounties.removeAll(toRemove);

		} else {
			ChatColor lvlColor = ChatColor.GRAY;
			if (CubiTechUtil.getPlayerFaction(e.getEntity()).equalsIgnoreCase("grass")) {
				lvlColor = ChatColor.GREEN;
			} else if (CubiTechUtil.getPlayerFaction(e.getEntity()).equalsIgnoreCase("sand")) {
				lvlColor = ChatColor.GOLD;
			} else if (CubiTechUtil.getPlayerFaction(e.getEntity()).equalsIgnoreCase("snow")) {
				lvlColor = ChatColor.DARK_AQUA;
			}
			Bukkit.broadcastMessage(lvlColor + e.getEntity().getName() + ChatColor.GRAY + " ist gestorben.");
		}
		boolean by1vs1 = false;
		if (CubiTechUtil.players1vs1Fighting[0] != null && CubiTechUtil.players1vs1Fighting[1] != null) {
			if (CubiTechUtil.players1vs1Fighting[0].getName().equals(e.getEntity().getName())) {
				Bukkit.broadcastMessage(ChatColor.RED + e.getEntity().getName() + ChatColor.YELLOW + " wurde von " + ChatColor.GOLD
						+ CubiTechUtil.players1vs1Fighting[1].getName() + ChatColor.YELLOW + " im 1vs1 besiegt.");
				CubiTechUtil.add1vs1Win(CubiTechUtil.players1vs1Fighting[1]);
				CubiTechUtil.add1vs1Fail(CubiTechUtil.players1vs1Fighting[0]);
				CubiTechUtil.players1vs1Fighting[0].setHealth(20.0);
				CubiTechUtil.players1vs1Fighting[1].setHealth(20.0);
				CubiTechUtil.players1vs1Fighting[0].chat("/spawn");
				CubiTechUtil.players1vs1Fighting[1].chat("/spawn");
				CubiTechUtil.players1vs1Fighting[0] = null;
				CubiTechUtil.players1vs1Fighting[1] = null;
				CubiTechUtil.check1vs1();
				by1vs1 = true;
			} else if (CubiTechUtil.players1vs1Fighting[1].getName().equals(e.getEntity().getName())) {
				Bukkit.broadcastMessage(ChatColor.RED + CubiTechUtil.players1vs1Fighting[1].getName() + ChatColor.YELLOW + " wurde von "
						+ ChatColor.GOLD + CubiTechUtil.players1vs1Fighting[0].getName() + ChatColor.YELLOW + " im 1vs1 besiegt.");
				CubiTechUtil.add1vs1Win(CubiTechUtil.players1vs1Fighting[0]);
				CubiTechUtil.add1vs1Fail(CubiTechUtil.players1vs1Fighting[1]);
				CubiTechUtil.players1vs1Fighting[0].setHealth(20.0);
				CubiTechUtil.players1vs1Fighting[1].setHealth(20.0);
				CubiTechUtil.players1vs1Fighting[0].chat("/spawn");
				CubiTechUtil.players1vs1Fighting[1].chat("/spawn");
				CubiTechUtil.players1vs1Fighting[0] = null;
				CubiTechUtil.players1vs1Fighting[1] = null;
				CubiTechUtil.check1vs1();
				by1vs1 = true;
			}
		}

		e.getDrops().clear();
		e.setDroppedExp(0);
		if (!by1vs1) {
			if (byPlayer) {
				if (CubiTechUtil.getPlayerMoney(e.getEntity()) >= 10) {
					CubiTechUtil.setPlayerMoney(e.getEntity(), CubiTechUtil.getPlayerMoney(e.getEntity()) - 10);
					e.getEntity().sendMessage("§cDu hast §610 Cubi §cverloren, da Du getoetet wurdest.");
				}

				if (!CubiTechUtil.isInOwnFactionRegion(e.getEntity().getKiller())) {
					e.getEntity()
							.getWorld()
							.spawnFallingBlock(
									new Location(e.getEntity().getWorld(), e.getEntity().getLocation().getX(),
											e.getEntity().getLocation().getY() + 20, e.getEntity().getLocation().getZ()), Material.PISTON_BASE,
									(byte) 15);
				}
			}
		}
		if (CubiTechUtil.playersMountedHorse.containsKey(e.getEntity())) {
			CubiTechUtil.playersMountedHorse.get(e.getEntity()).die();
			CubiTechUtil.playersMountedHorse.remove(e.getEntity());
		}
		CubiTechUtil.playersBack.put(e.getEntity(), e.getEntity().getLocation());
		CubiTechUtil.updateLevelBar(e.getEntity());
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		File f = new File("plugins//CubiTech//spawn.txt");
		if (!f.exists()) {
			p.sendMessage(ChatColor.RED + "Fehler. Der Spawn wurde nicht gesetzt.");
			try {
				f.createNewFile();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			try {
				Properties props = new Properties();
				BufferedInputStream stream = new BufferedInputStream(new FileInputStream(new File("plugins//CubiTech//spawn.txt")));
				props.load(stream);
				stream.close();

				String sSpawn;
				if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Knappe")) {
					sSpawn = props.getProperty("firstspawn");
				} else {
					sSpawn = props.getProperty("spawn");
				}
				Location spawn = new Location(plugin.getServer().getWorld("world"), Double.valueOf(sSpawn.split(" ")[0]), Double.valueOf(sSpawn
						.split(" ")[1]), Double.valueOf(sSpawn.split(" ")[2]), Float.valueOf(sSpawn.split(" ")[3]),
						Float.valueOf(sSpawn.split(" ")[4]));

				e.setRespawnLocation(spawn);
				p.setGameMode(GameMode.SURVIVAL);

			} catch (Exception ex) {
				ex.printStackTrace();
				p.sendMessage("Fehler. Du konntest nicht zum spawn teleportiert werden.");
			}
		}
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		if (e.getEntity() instanceof Monster) {
			ItemStack rndItem = CubiTechUtil.getRandomMonsterDrop(e.getEntityType());
			if (rndItem != null) {
				e.getDrops().add(rndItem);
			}
		}
		Entity entity = e.getEntity();

		if (!(entity instanceof Player)) {
			if (entity.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
				EntityDamageByEntityEvent entityDamageByEntityEvent = (EntityDamageByEntityEvent) entity.getLastDamageCause();
				if (entityDamageByEntityEvent.getDamager() instanceof Player) {
					if (entity instanceof Monster) {
						Player killer = (Player) entityDamageByEntityEvent.getDamager();
						String eName = entity.getType().name().toLowerCase();
						eName = Character.toUpperCase(eName.charAt(0)) + eName.substring(1);

						// Quest
						if (CubiTechUtil.playerQuests.containsKey(killer)) {
							KillQuest q = CubiTechUtil.playerQuests.get(killer);
							if (q.entityType == e.getEntityType()) {
								q.addKill();
							}
						}

						// Add EP+Money
						// split if if its enabled in the group
						Party party = CubiTechUtil.getPartyByPlayer(killer);
						if (party != null) {
							if (party.getSplit()) {
								// split it
								for (Player q : party.getPlayers()) {
									int ep = CubiTechUtil.getEPMoneyAfterKill(q, party.getPlayers().size()).x;
									int money = CubiTechUtil.getEPMoneyAfterKill(q, party.getPlayers().size()).y;
									if (e.getEntityType() == EntityType.WITHER_SKULL || e.getEntityType() == EntityType.WITHER) {
										ep *= 2;
										money *= 2;
									}
									CubiTechUtil.addPlayerEP(q, ep);
									CubiTechUtil.addPlayerMoney(q, money);
									q.sendMessage("§b§l+§r §6§o" + ep + " EP§b, §6§o" + money + " Cubi §r| §2§l" + eName + " §5§lKill§r ("
											+ killer.getName() + ")");
								}

							} else {
								int ep = CubiTechUtil.getEPMoneyAfterKill(killer, 1).x;
								int money = CubiTechUtil.getEPMoneyAfterKill(killer, 1).y;
								if (e.getEntityType() == EntityType.WITHER_SKULL || e.getEntityType() == EntityType.WITHER) {
									ep *= 2;
									money *= 2;
								}
								CubiTechUtil.addPlayerEP(killer, ep);
								CubiTechUtil.addPlayerMoney(killer, money);
								killer.sendMessage("§b§l+§r §6§o" + ep + " EP§b, §6§o" + money + " Cubi §r| §2§l" + eName + " §5§lKill");

							}
						} else {
							int ep = CubiTechUtil.getEPMoneyAfterKill(killer, 1).x;
							int money = CubiTechUtil.getEPMoneyAfterKill(killer, 1).y;
							if (e.getEntityType() == EntityType.WITHER_SKULL || e.getEntityType() == EntityType.WITHER) {
								ep *= 2;
								money *= 2;
							}
							CubiTechUtil.addPlayerEP(killer, ep);
							CubiTechUtil.addPlayerMoney(killer, money);
							killer.sendMessage("§b§l+§r §6§o" + ep + " EP§b, §6§o" + money + " Cubi §r| §2§l" + eName + " §5§lKill");
						}

					}
				} else {
					if (entity instanceof Monster) {
						if (entityDamageByEntityEvent.getDamager() instanceof Projectile) {
							try {
								Projectile a = ((Projectile) (entityDamageByEntityEvent.getDamager()));
								if (a.getShooter() instanceof Player) {
									Player killer = (Player) a.getShooter();
									String eName = entity.getType().name().toLowerCase();
									eName = Character.toUpperCase(eName.charAt(0)) + eName.substring(1);
									// Quest
									if (CubiTechUtil.playerQuests.containsKey(killer)) {
										KillQuest q = CubiTechUtil.playerQuests.get(killer);
										if (q.entityType == e.getEntityType()) {
											q.addKill();
										}
									}

									// EP+Money
									// split if if its enabled in the group
									Party party = CubiTechUtil.getPartyByPlayer(killer);
									if (party != null) {
										if (party.getSplit()) {
											// split it
											for (Player q : party.getPlayers()) {
												int ep = CubiTechUtil.getEPMoneyAfterKill(q, party.getPlayers().size()).x;
												int money = CubiTechUtil.getEPMoneyAfterKill(q, party.getPlayers().size()).y;
												if (e.getEntityType() == EntityType.WITHER_SKULL || e.getEntityType() == EntityType.WITHER) {
													ep *= 2;
													money *= 2;
												}
												CubiTechUtil.addPlayerEP(q, ep);
												CubiTechUtil.addPlayerMoney(q, money);
												q.sendMessage("§b§l+§r §6§o" + ep + " EP§b, §6§o" + money + " Cubi §r| §2§l" + eName
														+ " §5§lKill§r (" + killer.getName() + ")");
											}

										} else {
											int ep = CubiTechUtil.getEPMoneyAfterKill(killer, 1).x;
											int money = CubiTechUtil.getEPMoneyAfterKill(killer, 1).y;
											if (e.getEntityType() == EntityType.WITHER_SKULL || e.getEntityType() == EntityType.WITHER) {
												ep *= 2;
												money *= 2;
											}
											CubiTechUtil.addPlayerEP(killer, ep);
											CubiTechUtil.addPlayerMoney(killer, money);
											killer.sendMessage("§b§l+§r §6§o" + ep + " EP§b, §6§o" + money + " Cubi §r| §2§l" + eName + " §5§lKill");
										}
									} else {
										int ep = CubiTechUtil.getEPMoneyAfterKill(killer, 1).x;
										int money = CubiTechUtil.getEPMoneyAfterKill(killer, 1).y;
										if (e.getEntityType() == EntityType.WITHER_SKULL || e.getEntityType() == EntityType.WITHER) {
											ep *= 2;
											money *= 2;
										}
										CubiTechUtil.addPlayerEP(killer, ep);
										CubiTechUtil.addPlayerMoney(killer, money);
										killer.sendMessage("§b§l+§r §6§o" + ep + " EP§b, §6§o" + money + " Cubi §r| §2§l" + eName + " §5§lKill");
									}
								}
							} catch (Exception ex) {
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onWeaherChange(WeatherChangeEvent e) {
		if (e.toWeatherState() == true) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent e) {

		if (e.getSpawnReason() == SpawnReason.SPAWNER && (e.getEntityType() == EntityType.ZOMBIE || e.getEntityType() == EntityType.SKELETON)) {
			Location spawnloc = e.getLocation();
			int radius = 5;
			int offSetX = spawnloc.getBlockX();
			int offSetZ = spawnloc.getBlockZ();
			int offSetY = spawnloc.getBlockY() - 1;

			int startX = offSetX - radius;
			int startY = offSetY - radius;
			int startZ = offSetZ - radius;

			int endX = offSetX + radius;
			int endY = offSetY + radius;
			int endZ = offSetZ + radius;

			for (int counterX = startX; counterX < endX; counterX++) {
				for (int counterY = startY; counterY < endY; counterY++) {
					for (int counterZ = startZ; counterZ < endZ; counterZ++) {
						Block block = e.getLocation().getWorld().getBlockAt(counterX, counterY, counterZ);
						if (block != null) {
							if (block.getType() == Material.CHEST) {
								Chest chest = (Chest) block.getState();
								EntityEquipment eq = e.getEntity().getEquipment();
								if (chest.getInventory().getItem(0) != null) {
									eq.setBoots(chest.getInventory().getItem(0));
									eq.setBootsDropChance(0.2f);
								}
								if (chest.getInventory().getItem(1) != null) {
									eq.setLeggings(chest.getInventory().getItem(1));
									eq.setLeggingsDropChance(0.2f);
								}
								if (chest.getInventory().getItem(2) != null) {
									eq.setChestplate(chest.getInventory().getItem(2));
									eq.setChestplateDropChance(0.2f);
								}
								if (chest.getInventory().getItem(3) != null) {
									eq.setHelmet(chest.getInventory().getItem(3));
									eq.setHelmetDropChance(0.2f);
								}
								if (chest.getInventory().getItem(4) != null) {
									eq.setItemInHand(chest.getInventory().getItem(4));
									eq.setItemInHandDropChance(0.2f);
								}

								Block b2 = block.getRelative(BlockFace.DOWN);
								if (b2 != null) {
									if (b2.getState() != null) {
										if (b2.getState() instanceof Sign) {
											Sign sign = (Sign) b2.getState();
											for (String line : sign.getLines()) {
												if (line != null) {
													try {
														PotionEffect effect = new PotionEffect(PotionEffectType.getByName(line.split(" ")[0]),
																999999, Integer.valueOf(line.split(" ")[1]), true);
														if (effect != null) {
															effect.apply(e.getEntity());
														}
													} catch (Exception ex) {

													}
												}
											}
										}
									}
								}

								return;
							}
						}
					}
				}
			}
		}

		LivingEntity entity = e.getEntity();
		Biome b = entity.getWorld().getBiome(entity.getLocation().getBlockX(), entity.getLocation().getBlockZ());

		World world = Bukkit.getWorld("world");

		Location grass1 = new Location(world, 637, 1, 434);
		Location grass2 = new Location(world, 485, 200, 269);

		Location sand1 = new Location(world, 1285, 1, 541);
		Location sand2 = new Location(world, 1577, 200, 757);

		Location snow1 = new Location(world, 341, 1, 1643);
		Location snow2 = new Location(world, 602, 200, 1385);

		if (b == Biome.SWAMPLAND && e.getEntity() instanceof Monster) {
			PotionEffect effect = new PotionEffect(PotionEffectType.SPEED, 72000, 0);
			PotionEffect effect2 = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 7200, 2);
			PotionEffect effect3 = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 7200, 2);
			effect.apply(entity);
			effect2.apply(entity);
			effect3.apply(entity);
			if (e.getEntity() instanceof Skeleton) {
				((Skeleton) entity).setSkeletonType(SkeletonType.WITHER);
			}
		} else if ((b == Biome.EXTREME_HILLS || e.getEntity().getWorld().getName().equalsIgnoreCase("freebuild")) && e.getEntity() instanceof Monster) {
			if (e.getSpawnReason() != SpawnReason.SPAWNER && e.getSpawnReason() != SpawnReason.SPAWNER_EGG) {
				e.setCancelled(true);
			}
		} else if ((CubiTechUtil.isInArea(e.getLocation(), grass1, grass2) || CubiTechUtil.isInArea(e.getLocation(), sand1, sand2) || CubiTechUtil
				.isInArea(e.getLocation(), snow1, snow2)) && e.getEntity() instanceof Monster) {
			if (e.getSpawnReason() != SpawnReason.SPAWNER && e.getSpawnReason() != SpawnReason.SPAWNER_EGG) {
				e.setCancelled(true);
			}
		}

		if (e.getEntityType() == EntityType.ZOMBIE) {
			Monster zombie = ((Monster) e.getEntity());
			EntityEquipment eq = zombie.getEquipment();
			if (eq.getBoots().getType() == Material.AIR && eq.getLeggings().getType() == Material.AIR && eq.getChestplate().getType() == Material.AIR
					&& eq.getHelmet().getType() == Material.AIR && e.getSpawnReason() != SpawnReason.SPAWNER_EGG) {
				// Has No Equipment Yet
				// Lets give him some Armor and a Weapon
				boolean mage = Math.random() > 0.7;
				eq.setHelmet(new ItemStack(Material.LEATHER_HELMET));
				eq.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
				eq.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
				eq.setBoots(new ItemStack(Material.LEATHER_BOOTS));
				ItemStack itemInHand = new ItemStack(mage ? Material.BLAZE_ROD : Material.WOOD_SWORD);
				if (Math.random() > 0.7) {
					ItemMeta m = itemInHand.getItemMeta();
					ArrayList<String> desc = new ArrayList<String>();
					desc.add(ChatColor.WHITE + "Stufe: " + ChatColor.GOLD + ChatColor.ITALIC + "Sehr Gut");
					m.setLore(desc);
					m.setDisplayName(ChatColor.GOLD + (mage ? "Sehr Guter Stab des Zombies" : "Sehr Gutes Schwert des Zombies"));
					m.addEnchant(Enchantment.DAMAGE_ALL, 1, false);
					m.addEnchant(Enchantment.DAMAGE_UNDEAD, 1, false);
					m.addEnchant(Enchantment.DURABILITY, 1, false);
					itemInHand.setItemMeta(m);
					eq.setItemInHand(itemInHand);
					eq.setItemInHandDropChance(0.1f);
				} else {
					ItemMeta m = itemInHand.getItemMeta();
					ArrayList<String> desc = new ArrayList<String>();
					desc.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
					m.setLore(desc);
					m.setDisplayName(ChatColor.YELLOW + (mage ? "Guter Stab des Zombies" : "Gutes Schwert des Zombies"));
					m.addEnchant(Enchantment.DAMAGE_ALL, 1, false);
					itemInHand.setItemMeta(m);
					eq.setItemInHand(itemInHand);
					eq.setItemInHandDropChance(0.1f);
				}

				if (Math.random() > 0.3) {
					ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
					ItemMeta helmetMeta = helmet.getItemMeta();
					ArrayList<String> helmetLore = new ArrayList<String>();
					helmetLore.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_GRAY + ChatColor.ITALIC + "Schlecht");
					helmetMeta.setLore(helmetLore);
					helmetMeta.setDisplayName(ChatColor.DARK_GRAY + "Schlechter Helm des Zombies");
					helmet.setItemMeta(helmetMeta);
					helmet.setDurability((short) (Short.MAX_VALUE / 2));
					eq.setHelmet(helmet);
					eq.setHelmetDropChance(0.1f);
				} else {
					ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
					ItemMeta helmetMeta = helmet.getItemMeta();
					ArrayList<String> helmetLore = new ArrayList<String>();
					helmetLore.add(ChatColor.WHITE + "Stufe: " + ChatColor.WHITE + ChatColor.ITALIC + "Normal");
					helmetMeta.setLore(helmetLore);
					helmetMeta.setDisplayName(ChatColor.WHITE + "Normaler Helm des Zombies");
					helmet.setItemMeta(helmetMeta);
					eq.setHelmet(helmet);
					eq.setHelmetDropChance(0.1f);
				}
				if (Math.random() > 0.3) {
					ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
					ItemMeta chestplateMeta = chestplate.getItemMeta();
					ArrayList<String> chestplateLore = new ArrayList<String>();
					chestplateLore.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_GRAY + ChatColor.ITALIC + "Schlecht");
					chestplateMeta.setLore(chestplateLore);
					chestplateMeta.setDisplayName(ChatColor.DARK_GRAY + "Schlechter Harnisch des Zombies");
					chestplate.setItemMeta(chestplateMeta);
					chestplate.setDurability((short) (Short.MAX_VALUE / 2));
					eq.setChestplate(chestplate);
					eq.setChestplateDropChance(0.1f);
				} else {
					ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
					ItemMeta chestplateMeta = chestplate.getItemMeta();
					ArrayList<String> chestplateLore = new ArrayList<String>();
					chestplateLore.add(ChatColor.WHITE + "Stufe: " + ChatColor.WHITE + ChatColor.ITALIC + "Normal");
					chestplateMeta.setLore(chestplateLore);
					chestplateMeta.setDisplayName(ChatColor.WHITE + "Normaler Harnisch des Zombies");
					chestplate.setItemMeta(chestplateMeta);
					chestplate.setDurability((short) (Short.MAX_VALUE / 2));
					eq.setChestplate(chestplate);
					eq.setChestplateDropChance(0.1f);
				}

				if (Math.random() > 0.3) {
					ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
					ItemMeta leggingsMeta = leggings.getItemMeta();
					ArrayList<String> leggingsLore = new ArrayList<String>();
					leggingsLore.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_GRAY + ChatColor.ITALIC + "Schlecht");
					leggingsMeta.setLore(leggingsLore);
					leggingsMeta.setDisplayName(ChatColor.DARK_GRAY + "Schlechte Hose des Zombies");
					leggings.setItemMeta(leggingsMeta);
					leggings.setDurability((short) (Short.MAX_VALUE / 2));
					eq.setLeggings(leggings);
					eq.setLeggingsDropChance(0.1f);
				} else {
					ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
					ItemMeta leggingsMeta = leggings.getItemMeta();
					ArrayList<String> leggingsLore = new ArrayList<String>();
					leggingsLore.add(ChatColor.WHITE + "Stufe: " + ChatColor.WHITE + ChatColor.ITALIC + "Normal");
					leggingsMeta.setLore(leggingsLore);
					leggingsMeta.setDisplayName(ChatColor.WHITE + "Normale Hose des Zombies");
					leggings.setItemMeta(leggingsMeta);
					leggings.setDurability((short) (Short.MAX_VALUE / 2));
					eq.setLeggings(leggings);
					eq.setLeggingsDropChance(0.1f);
				}

				if (Math.random() > 0.3) {
					ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
					ItemMeta bootsMeta = boots.getItemMeta();
					ArrayList<String> bootsLore = new ArrayList<String>();
					bootsLore.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_GRAY + ChatColor.ITALIC + "Schlecht");
					bootsMeta.setLore(bootsLore);
					bootsMeta.setDisplayName(ChatColor.DARK_GRAY + "Schlechte Schuhe des Zombies");
					boots.setItemMeta(bootsMeta);
					boots.setDurability((short) (Short.MAX_VALUE / 2));
					eq.setBoots(boots);
					eq.setBootsDropChance(0.1f);
				} else {
					ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
					ItemMeta bootsMeta = boots.getItemMeta();
					ArrayList<String> bootsLore = new ArrayList<String>();
					bootsLore.add(ChatColor.WHITE + "Stufe: " + ChatColor.WHITE + ChatColor.ITALIC + "Normal");
					bootsMeta.setLore(bootsLore);
					bootsMeta.setDisplayName(ChatColor.WHITE + "Normale Schuhe des Zombies");
					boots.setItemMeta(bootsMeta);
					eq.setBoots(boots);
					eq.setBootsDropChance(0.1f);
				}

			}
		} else if (e.getEntityType() == EntityType.SKELETON) {
			Monster skeleton = ((Monster) e.getEntity());
			EntityEquipment eq = skeleton.getEquipment();
			if (eq.getBoots().getType() == Material.AIR && eq.getLeggings().getType() == Material.AIR && eq.getChestplate().getType() == Material.AIR
					&& eq.getHelmet().getType() == Material.AIR && e.getSpawnReason() != SpawnReason.SPAWNER_EGG) {
				eq.setHelmet(new ItemStack(Material.LEATHER_HELMET));
				eq.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
				eq.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
				eq.setBoots(new ItemStack(Material.LEATHER_BOOTS));

				ItemStack itemInHand = new ItemStack(Material.BOW);
				if (Math.random() > 0.3) {
					ItemMeta handMeta = itemInHand.getItemMeta();
					ArrayList<String> handLore = new ArrayList<String>();
					handLore.add(ChatColor.WHITE + "Stufe: " + ChatColor.YELLOW + ChatColor.ITALIC + "Gut");
					handMeta.setLore(handLore);
					handMeta.setDisplayName(ChatColor.YELLOW + "Guter Bogen des Skeletts");
					handMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, false);
					itemInHand.setItemMeta(handMeta);
					eq.setItemInHand(itemInHand);
					eq.setItemInHandDropChance(0.1f);
				} else {
					ItemMeta handMeta = itemInHand.getItemMeta();
					ArrayList<String> handLore = new ArrayList<String>();
					handLore.add(ChatColor.WHITE + "Stufe: " + ChatColor.GOLD + ChatColor.ITALIC + "Sehr Gut");
					handMeta.setLore(handLore);
					handMeta.setDisplayName(ChatColor.GOLD + "Sehr Guter Bogen des Skeletts");
					handMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, false);
					handMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, false);
					handMeta.addEnchant(Enchantment.DURABILITY, 1, false);
					itemInHand.setItemMeta(handMeta);
					eq.setItemInHand(itemInHand);
					eq.setItemInHandDropChance(0.1f);
				}

				if (Math.random() > 0.3) {
					ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
					ItemMeta helmetMeta = helmet.getItemMeta();
					ArrayList<String> helmetLore = new ArrayList<String>();
					helmetLore.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_GRAY + ChatColor.ITALIC + "Schlecht");
					helmetMeta.setLore(helmetLore);
					helmetMeta.setDisplayName(ChatColor.DARK_GRAY + "Schlechter Helm des Skeletts");
					helmet.setItemMeta(helmetMeta);
					helmet.setDurability((short) (Short.MAX_VALUE / 2));
					eq.setHelmet(helmet);
					eq.setHelmetDropChance(0.1f);
				} else {
					ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
					ItemMeta helmetMeta = helmet.getItemMeta();
					ArrayList<String> helmetLore = new ArrayList<String>();
					helmetLore.add(ChatColor.WHITE + "Stufe: " + ChatColor.WHITE + ChatColor.ITALIC + "Normal");
					helmetMeta.setLore(helmetLore);
					helmetMeta.setDisplayName(ChatColor.WHITE + "Normaler Helm des Skeletts");
					helmet.setItemMeta(helmetMeta);
					eq.setHelmet(helmet);
					eq.setHelmetDropChance(0.1f);
				}
				if (Math.random() > 0.3) {
					ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
					ItemMeta chestplateMeta = chestplate.getItemMeta();
					ArrayList<String> chestplateLore = new ArrayList<String>();
					chestplateLore.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_GRAY + ChatColor.ITALIC + "Schlecht");
					chestplateMeta.setLore(chestplateLore);
					chestplateMeta.setDisplayName(ChatColor.DARK_GRAY + "Schlechter Harnisch des Skeletts");
					chestplate.setItemMeta(chestplateMeta);
					chestplate.setDurability((short) (Short.MAX_VALUE / 2));
					eq.setChestplate(chestplate);
					eq.setChestplateDropChance(0.1f);
				} else {
					ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
					ItemMeta chestplateMeta = chestplate.getItemMeta();
					ArrayList<String> chestplateLore = new ArrayList<String>();
					chestplateLore.add(ChatColor.WHITE + "Stufe: " + ChatColor.WHITE + ChatColor.ITALIC + "Normal");
					chestplateMeta.setLore(chestplateLore);
					chestplateMeta.setDisplayName(ChatColor.WHITE + "Normaler Harnisch des Skeletts");
					chestplate.setItemMeta(chestplateMeta);
					chestplate.setDurability((short) (Short.MAX_VALUE / 2));
					eq.setChestplate(chestplate);
					eq.setChestplateDropChance(0.1f);
				}

				if (Math.random() > 0.3) {
					ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
					ItemMeta leggingsMeta = leggings.getItemMeta();
					ArrayList<String> leggingsLore = new ArrayList<String>();
					leggingsLore.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_GRAY + ChatColor.ITALIC + "Schlecht");
					leggingsMeta.setLore(leggingsLore);
					leggingsMeta.setDisplayName(ChatColor.DARK_GRAY + "Schlechte Hose des Skeletts");
					leggings.setItemMeta(leggingsMeta);
					leggings.setDurability((short) (Short.MAX_VALUE / 2));
					eq.setLeggings(leggings);
					eq.setLeggingsDropChance(0.1f);
				} else {
					ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
					ItemMeta leggingsMeta = leggings.getItemMeta();
					ArrayList<String> leggingsLore = new ArrayList<String>();
					leggingsLore.add(ChatColor.WHITE + "Stufe: " + ChatColor.WHITE + ChatColor.ITALIC + "Normal");
					leggingsMeta.setLore(leggingsLore);
					leggingsMeta.setDisplayName(ChatColor.WHITE + "Normale Hose des Skeletts");
					leggings.setItemMeta(leggingsMeta);
					leggings.setDurability((short) (Short.MAX_VALUE / 2));
					eq.setLeggings(leggings);
					eq.setLeggingsDropChance(0.1f);
				}

				if (Math.random() > 0.3) {
					ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
					ItemMeta bootsMeta = boots.getItemMeta();
					ArrayList<String> bootsLore = new ArrayList<String>();
					bootsLore.add(ChatColor.WHITE + "Stufe: " + ChatColor.DARK_GRAY + ChatColor.ITALIC + "Schlecht");
					bootsMeta.setLore(bootsLore);
					bootsMeta.setDisplayName(ChatColor.DARK_GRAY + "Schlechte Schuhe des Skeletts");
					boots.setItemMeta(bootsMeta);
					boots.setDurability((short) (Short.MAX_VALUE / 2));
					eq.setBoots(boots);
					eq.setBootsDropChance(0.1f);
				} else {
					ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
					ItemMeta bootsMeta = boots.getItemMeta();
					ArrayList<String> bootsLore = new ArrayList<String>();
					bootsLore.add(ChatColor.WHITE + "Stufe: " + ChatColor.WHITE + ChatColor.ITALIC + "Normal");
					bootsMeta.setLore(bootsLore);
					bootsMeta.setDisplayName(ChatColor.WHITE + "Normale Schuhe des Skeletts");
					boots.setItemMeta(bootsMeta);
					eq.setBoots(boots);
					eq.setBootsDropChance(0.1f);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			// Coupon
			Player p = e.getPlayer();
			if (p.getItemInHand().getType() == Material.PAPER) {
				ItemMeta am = (ItemMeta) p.getItemInHand().getItemMeta();
				if (am.getDisplayName().equals(ChatColor.GOLD + "" + ChatColor.UNDERLINE + "Gutschein")) {
					int amount = p.getItemInHand().getAmount();
					p.getInventory().remove(p.getItemInHand());
					p.setItemInHand(new ItemStack(Material.AIR));
					CubiTechUtil.addPlayerMoney(p, 50 * amount);
					if (amount == 1) {
						p.sendMessage(ChatColor.GREEN + "Du hast einen Gutschein fuer " + ChatColor.GOLD + "50 Cubi" + ChatColor.GREEN
								+ " eingeloest.");
					} else {
						p.sendMessage(ChatColor.GREEN + "Du hast " + ChatColor.YELLOW + amount + ChatColor.GREEN + " Gutscheine fuer "
								+ ChatColor.GOLD + "50 Cubi" + ChatColor.GREEN + " eingeloest. ( = " + ChatColor.GOLD + 50 * amount + " Cubi"
								+ ChatColor.GREEN + " )");
					}
					p.chat("/money");
					return;
				}
			} else if (p.getItemInHand().getType() == Material.EMERALD) {
				ItemMeta am = (ItemMeta) p.getItemInHand().getItemMeta();
				if (am.getDisplayName().equals(ChatColor.GREEN + "Gruppenstein der Heilung")) {
					if (e.getPlayer().getItemInHand().getAmount() == 1) {
						e.getPlayer().getInventory().remove(e.getPlayer().getItemInHand());
					} else {
						e.getPlayer().getItemInHand().setAmount(e.getPlayer().getItemInHand().getAmount() - 1);
					}
					for (Entity near : p.getNearbyEntities(10, 10, 10)) {
						if (near instanceof Player) {
							if (CubiTechUtil.getPlayerFaction(p).equalsIgnoreCase(CubiTechUtil.getPlayerFaction((Player) near))) {
								((Player) near).addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 20, 1));
								((Player) near).addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 1));
							}
						}
					}
					return;
				} else if (am.getDisplayName().equals(ChatColor.GREEN + "Gruppenstein der Unsichtbarkeit")) {
					if (e.getPlayer().getItemInHand().getAmount() == 1) {
						e.getPlayer().getInventory().remove(e.getPlayer().getItemInHand());
					} else {
						e.getPlayer().getItemInHand().setAmount(e.getPlayer().getItemInHand().getAmount() - 1);
					}
					for (Entity near : p.getNearbyEntities(10, 10, 10)) {
						if (near instanceof Player) {
							if (CubiTechUtil.getPlayerFaction(p).equalsIgnoreCase(CubiTechUtil.getPlayerFaction((Player) near))) {
								((Player) near).addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1200, 1));
							}
						}
					}
					return;
				}
			}
		}
		if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			Player p = e.getPlayer();
			if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Magier") || CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("YouTuber")) {
				if (p.getItemInHand().getType() == Material.BLAZE_ROD) {
					// Shoot Fireball
					if (CubiTechUtil.getPlayerMana(p) >= 0.2) {
						boolean witherSkull = false;
						if (CubiTechUtil.getPlayerLevel(p) >= 40 && Math.random() > 0.5) {
							witherSkull = true;
						}
						Projectile f = p.launchProjectile(witherSkull ? WitherSkull.class : Fireball.class);
						f.setShooter(p);
						CubiTechUtil.subtractMana(p, 0.2);
					}
				}
			} else if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Priester")) {
				// Heal self
				if (CubiTechUtil.getPlayerMana(p) >= 0.8) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 60, 1));
					CubiTechUtil.subtractMana(p, 0.8);
				}
			} else if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Krieger")) {
				if (p.getItemInHand().getType() == Material.IRON_AXE) {
					if (CubiTechUtil.getPlayerMana(p) >= 0.4) {
						CubiTechUtil.subtractMana(p, 0.4);
						for (Entity en : p.getNearbyEntities(5, 5, 5)) {
							if (en instanceof LivingEntity) {
								((LivingEntity) en).damage(1.0, p);
							}
						}
						for (int i = 0; i <= 8; i++)
							p.getWorld().playEffect(p.getLocation(), Effect.SMOKE, i);
					}
				}
			} else if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Jaeger")) {
				if (p.getItemInHand().getType() == Material.ARROW) {
					// Shoot many arrows
					if (CubiTechUtil.getPlayerMana(p) >= 0.9) {
						int arrows = 5;
						int rad = 2;
						for (int i = 0; i < arrows; i++) {
							Location l = p.getLocation();
							l.setX((l.getX() - rad) + (rad * 2 + 1) * Math.random());
							l.setY(l.getY() + (1 + (3 * Math.random())));
							l.setZ((l.getZ() - rad) + (rad * 2 + 2) * Math.random());
							Arrow arrow = p.getWorld().spawnArrow(l, l.getDirection(), 1.0f, 12.0f);
							if (Math.random() < 0.1) {
								arrow.setFireTicks(1000);
							}
							CubiTechUtil.subtractMana(p, 0.9);
						}
					}
				}
			} else if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Schurke")) {
				if (p.getItemInHand().getType() == Material.SULPHUR) {
					if (CubiTechUtil.getPlayerMana(p) >= 0.9) {
						// Verschwinden
						Location smokeLoc = p.getLocation();
						for (int i = 0; i < 30; i++) {
							p.getWorld().playEffect(smokeLoc, Effect.SMOKE, 100);
							smokeLoc.setY(smokeLoc.getY() + 0.1);
						}
						p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 200, 2));
						p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 2));
						p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 0));
						p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 0));
						CubiTechUtil.subtractMana(p, 0.9);
					}
				}
			}
		} else if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			Player p = e.getPlayer();

			if ((e.getClickedBlock().getTypeId() == 33 || e.getClickedBlock().getTypeId() == 29)
					&& (e.getClickedBlock().getData() == (byte) 15 || e.getClickedBlock().getData() == (byte) 14)) {
				Biome bi = p.getWorld().getBiome(p.getLocation().getBlockX(), p.getLocation().getBlockZ());
				if (bi != Biome.EXTREME_HILLS) {
					if (!CubiTechUtil.isInOwnFactionRegion(p)) {
						CubiTechUtil.stealSupply(p);
						e.getClickedBlock().setTypeId(0);
					} else {
						p.sendMessage("§eDu kannst keine Vorraete deines Volkes rauben!");
					}
				}
			}

			// Block Enchanting or Dispenser
			if (e.getClickedBlock().getType() == Material.ENCHANTMENT_TABLE || e.getClickedBlock().getType() == Material.DISPENSER
					|| e.getClickedBlock().getType() == Material.DROPPER) {

				if (!CubiTechUtil.isPlayerAdmin(p)) {
					e.setCancelled(true);
				}

			} else if (e.getClickedBlock().getState() instanceof Sign) {
				Sign s = (Sign) (e.getClickedBlock().getState());
				if (s.getLines()[0].contains("Der Ausbilder") && s.getLines()[1].contains("ist gerade") && s.getLines()[2].contains("nicht da?")
						&& s.getLines()[3].contains("Klick Hier!")) {
					if (CubiTechUtil.getPlayerClass(e.getPlayer()).equalsIgnoreCase("Knappe") || CubiTechUtil.isPlayerAdmin(e.getPlayer())) {
						CubiTechUtil.chooseClass(e.getPlayer());
					} else {
						e.getPlayer().sendMessage(ChatColor.RED + "Du hast deine Klasse bereits gewaehlt!");
					}
				}
			}
			if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Priester")) {
				if (p.getItemInHand().getType() == Material.AIR) {
					// Heal Fountain
					if (CubiTechUtil.getPlayerMana(p) >= 0.9) {
						final Location l = p.getTargetBlock(null, 10).getLocation();

						final int task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
							@Override
							public void run() {
								// health potion at Location l

								World world = l.getWorld();
								// heal Potion

								PotionType type = PotionType.INSTANT_HEAL;
								Potion p = new Potion(type);
								int data = p.toDamageValue();

								net.minecraft.server.v1_6_R2.World nmsWorld = ((CraftWorld) world).getHandle();
								EntityPotion ent = new EntityPotion(nmsWorld, l.getX(), l.getY() + 1, l.getZ(),
										new net.minecraft.server.v1_6_R2.ItemStack(373, 1, data));
								ent.g(0, 0.4, 0);
								nmsWorld.addEntity(ent);

							}
						}, 0L, 20L);

						plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
							public void run() {
								Bukkit.getScheduler().cancelTask(task);
							}
						}, 180L);

						CubiTechUtil.subtractMana(p, 0.9);
					}
				}
			}

			if (p.getItemInHand().getType() == Material.PAPER) {
				ItemMeta am = (ItemMeta) p.getItemInHand().getItemMeta();
				if (am.getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "" + ChatColor.UNDERLINE + "Gutschein")) {
					int amount = p.getItemInHand().getAmount();

					p.getInventory().remove(p.getItemInHand());
					p.setItemInHand(new ItemStack(Material.AIR));
					CubiTechUtil.addPlayerMoney(p, 50 * amount);
					if (amount == 1) {
						p.sendMessage(ChatColor.GREEN + "Du hast einen Gutschein fuer " + ChatColor.GOLD + "50 Cubi" + ChatColor.GREEN
								+ " eingeloest.");
					} else {
						p.sendMessage(ChatColor.GREEN + "Du hast " + ChatColor.YELLOW + amount + ChatColor.GREEN + " Gutscheine fuer "
								+ ChatColor.GOLD + "50 Cubi" + ChatColor.GREEN + " eingeloest. ( = " + ChatColor.GOLD + 50 * amount + " Cubi"
								+ ChatColor.GREEN + " )");
					}
					p.chat("/money");
				}
			} else if (p.getItemInHand().getType() == Material.EMERALD) {
				ItemMeta am = (ItemMeta) p.getItemInHand().getItemMeta();
				if (am.getDisplayName().equals(ChatColor.GREEN + "Gruppenstein der Heilung")) {
					if (e.getPlayer().getItemInHand().getAmount() == 1) {
						e.getPlayer().getInventory().remove(e.getPlayer().getItemInHand());
					} else {
						e.getPlayer().getItemInHand().setAmount(e.getPlayer().getItemInHand().getAmount() - 1);
					}
					for (Entity near : p.getNearbyEntities(10, 10, 10)) {
						if (near instanceof Player) {
							if (CubiTechUtil.getPlayerFaction(p).equalsIgnoreCase(CubiTechUtil.getPlayerFaction((Player) near))) {
								((Player) near).addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 20, 1));
								((Player) near).addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 1));
							}
						}
					}
				} else if (am.getDisplayName().equals(ChatColor.GREEN + "Gruppenstein der Unsichtbarkeit")) {
					if (e.getPlayer().getItemInHand().getAmount() == 1) {
						e.getPlayer().getInventory().remove(e.getPlayer().getItemInHand());
					} else {
						e.getPlayer().getItemInHand().setAmount(e.getPlayer().getItemInHand().getAmount() - 1);
					}
					for (Entity near : p.getNearbyEntities(10, 10, 10)) {
						if (near instanceof Player) {
							if (CubiTechUtil.getPlayerFaction(p).equalsIgnoreCase(CubiTechUtil.getPlayerFaction((Player) near))) {
								((Player) near).addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1200, 1));
							}
						}
					}
				}
			}

		}
	}

	@EventHandler
	public void onPrepareItemEnchant(PrepareItemEnchantEvent e) {
		if (!CubiTechUtil.isPlayerAdmin(e.getEnchanter())) {
			e.setCancelled(true);
		}
	}

	private HashSet<Player> notSendBlockMessages = new HashSet<Player>();

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();

		if (CubiTechUtil.playersTutorial.contains(p)) {
			if (e.getFrom().getX() != e.getTo().getX() || e.getFrom().getZ() != e.getTo().getZ()) {
				p.sendMessage("§7Bitte waehrend des Rundgangs nicht bewegen!");
				p.teleport(e.getFrom());
				e.setCancelled(true);
			}
		}

		if (CubiTechUtil.playersAfk.contains(p)) {
			if (e.getFrom().getX() != e.getTo().getX() || e.getFrom().getZ() != e.getTo().getZ()) {
				CubiTechUtil.playersAfk.remove(p);
			}
		}

		if (CubiTechUtil.playersWaitingForTeleport.contains(p)) {
			CubiTechUtil.playersWaitingForTeleport.remove(p);
			p.sendMessage(ChatColor.GRAY + "Teleportation abgebrochen.");
		}

		if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Schurke") && p.isSneaking()) {
			if (CubiTechUtil.getPlayerMana(p) > 0.025) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 1));
				p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 100, 1));
				CubiTechUtil.subtractMana(p, 0.025);
			}
		}
		if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Priester") && p.isSneaking()) {
			if (CubiTechUtil.getPlayerMana(p) > 0.8) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));
				p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 60, 1));
				p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 1));
				CubiTechUtil.subtractMana(p, 0.8);
			}
		}
		if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Jaeger") && p.isSneaking()) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1200, 1));
		}

		if (e.getFrom().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType() == Material.BEDROCK
				&& e.getFrom().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType() == Material.DIAMOND_BLOCK
				&& !notSendBlockMessages.contains(e.getPlayer())) {
			notSendBlockMessages.add(e.getPlayer());
			final Player pl = e.getPlayer();
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					notSendBlockMessages.remove(pl);
				}
			}, 40L);
			Block b = e.getFrom().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN)
					.getRelative(BlockFace.DOWN);
			try {
				Sign s = (Sign) (b.getState());
				String msg = "";
				for (int i = 0; i < s.getLines().length; i++) {
					String chat = s.getLines()[i];
					if (s != null && !chat.equals("")) {
						chat = ChatColor.translateAlternateColorCodes('&', chat);
						msg += chat;
						msg += " ";
					}
				}
				e.getPlayer().sendMessage(msg);
			} catch (Exception ex) {
			}

		} else if (e.getFrom().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType() == Material.BEDROCK
				&& e.getFrom().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType() == Material.GOLD_BLOCK) {
			Block b = e.getFrom().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN)
					.getRelative(BlockFace.DOWN);
			Sign s = (Sign) (b.getState());
			String line1 = s.getLine(0);
			String line2 = s.getLine(1);

			String sX = line1.split(" ")[0];
			String sY = line1.split(" ")[1];
			String sZ = line1.split(" ")[2];

			String sYaw = line2.split(" ")[0];
			String sPitch = line2.split(" ")[1];

			try {
				int x = Integer.valueOf(sX);
				int y = Integer.valueOf(sY);
				int z = Integer.valueOf(sZ);

				int yaw = Integer.valueOf(sYaw);
				int pitch = Integer.valueOf(sPitch);

				Location l = new Location(p.getWorld(), x, y, z, yaw, pitch);

				p.teleport(l);

			} catch (Exception ex) {
				p.sendMessage(ChatColor.RED + "Fehler. Du konntest nicht teleportiert werden.");
			}

		} else if (e.getFrom().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType() == Material.BEDROCK
				&& e.getFrom().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType() == Material.WOOL) {
			Block b = e.getFrom().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN)
					.getRelative(BlockFace.DOWN);
			Sign s = (Sign) (b.getState());
			String line1 = s.getLine(0);
			String line2 = s.getLine(1);

			String sX = line1.split(" ")[0];
			String sY = line1.split(" ")[1];
			String sZ = line1.split(" ")[2];

			String sYaw = line2.split(" ")[0];
			String sPitch = line2.split(" ")[1];

			try {
				int x = Integer.valueOf(sX);
				int y = Integer.valueOf(sY);
				int z = Integer.valueOf(sZ);

				int yaw = Integer.valueOf(sYaw);
				int pitch = Integer.valueOf(sPitch);

				Location l = new Location(p.getWorld(), x, y, z, yaw, pitch);

				Block wBlock = e.getFrom().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN);
				if (wBlock.getType() == Material.WOOL) {
					if (!notSendBlockMessages.contains(p)) {
						notSendBlockMessages.add(p);
						final Player _p = p;
						plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
							public void run() {
								notSendBlockMessages.remove(_p);
							}
						}, 20L);
						if (wBlock.getData() == (byte) 5) {
							// grass
							if (CubiTechUtil.getPlayerFaction(p).equalsIgnoreCase("grass") || CubiTechUtil.isPlayerAdmin(p)) {
								p.teleport(l);
							} else {
								p.setVelocity(p.getLocation().getDirection().multiply(-4).setY(0.5));
								p.sendMessage("§eNur §aSolaner §ekönnen dieses Portal nutzen!");
							}
						} else if (wBlock.getData() == (byte) 1) {
							// sand
							if (CubiTechUtil.getPlayerFaction(p).equalsIgnoreCase("sand") || CubiTechUtil.isPlayerAdmin(p)) {
								p.teleport(l);
							} else {
								p.setVelocity(p.getLocation().getDirection().multiply(-4).setY(0.5));
								p.sendMessage("§eNur §6Pyrer §ekönnen dieses Portal nutzen!");
							}
						} else if (wBlock.getData() == (byte) 3) {
							// snow
							if (CubiTechUtil.getPlayerFaction(p).equalsIgnoreCase("snow") || CubiTechUtil.isPlayerAdmin(p)) {
								p.teleport(l);
							} else {
								p.setVelocity(p.getLocation().getDirection().multiply(-4).setY(0.5));
								p.sendMessage("§eNur §3Arctiker §ekönnen dieses Portal nutzen!");
							}
						}
					}
				}

			} catch (Exception ex) {
				p.sendMessage(ChatColor.RED + "Fehler. Du konntest nicht teleportiert werden.");
			}

		}

		if (!p.isSneaking()) {
			if (e.getFrom().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SPONGE) {
				Vector vec = new Vector(0D, 10D, 0D);
				p.setVelocity(vec);
				p.setFallDistance(-25);
				final Player _p = p;
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						Vector vec = new Vector(0D, 10D, 0D);
						_p.setVelocity(vec);
						_p.setFallDistance(-25);
					}
				}, 20L);
			}
		}
		if (e.getTo().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType() == Material.BEDROCK
				&& e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.LAPIS_BLOCK
				&& e.getFrom().getBlock().getRelative(BlockFace.DOWN).getType() != Material.LAPIS_BLOCK) {
			if (!CubiTechUtil.playersAfk.contains(p)) {
				final Player _p = p;
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						CubiTechUtil.playersAfk.add(_p);
					}
				}, 40L);

			}

		}

		if (CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Knappe") && (int) p.getLocation().getX() == -821
				&& ((int) p.getLocation().getZ() == 192 || (int) p.getLocation().getZ() == 193)) {
			p.teleport(new Location(p.getWorld(), -830, 64, 194, -210, -2));
			p.sendMessage(ChatColor.RED + "Du musst zuerst eine Klasse waehlen!");
		}

		if (CubiTechUtil.mageTeleporters.contains(p.getLocation().getBlock())) {
			Inventory inv = plugin.getServer().createInventory(null, 9, "Teleportieren nach:");

			ItemStack is1 = new ItemStack(Material.EMERALD, 1);
			ItemMeta am1 = (ItemMeta) is1.getItemMeta();
			am1.setDisplayName(ChatColor.AQUA + "" + ChatColor.UNDERLINE + "Stadt 1");
			is1.setItemMeta(am1);
			inv.setItem(3, is1);

			ItemStack is2 = new ItemStack(Material.EMERALD, 1);
			ItemMeta am2 = (ItemMeta) is2.getItemMeta();
			am2.setDisplayName(ChatColor.AQUA + "" + ChatColor.UNDERLINE + "Stadt 2");
			is2.setItemMeta(am2);
			inv.setItem(4, is2);

			ItemStack is3 = new ItemStack(Material.EMERALD, 1);
			ItemMeta am3 = (ItemMeta) is3.getItemMeta();
			am3.setDisplayName(ChatColor.AQUA + "" + ChatColor.UNDERLINE + "Stadt 3");
			is3.setItemMeta(am3);
			inv.setItem(5, is3);

			p.openInventory(inv);
		}

	}

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent e) {
		if (e.getEntity() instanceof Arrow) {
			if (e.getEntity().getShooter() instanceof Player) {
				Player p = (Player) e.getEntity().getShooter();
				if (p.getItemInHand().getItemMeta() != null) {
					if (p.getItemInHand().getItemMeta().getDisplayName() != null) {
						if (p.getItemInHand().getItemMeta().getDisplayName()
								.equals("§6P§2a§3r§4t§5y" + ChatColor.WHITE + "-" + ChatColor.RED + "Bogen")) {
							Firework fw = (Firework) e.getEntity().getLocation().getWorld()
									.spawnEntity(e.getEntity().getLocation(), EntityType.FIREWORK);
							FireworkMeta meta = fw.getFireworkMeta();
							FireworkEffect effect = null;
							if (Math.random() > 0.5) {
								effect = FireworkEffect.builder().withColor(Color.AQUA).with(Type.BURST).build();
							} else {
								effect = FireworkEffect.builder().withColor(Color.ORANGE).with(Type.STAR).build();
							}
							meta.addEffect(effect);
							meta.setPower(0);

							fw.setFireworkMeta(meta);
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if (!CubiTechUtil.isPlayerAdmin(e.getPlayer())) {
			if (e.getPlayer().getWorld().getName().equalsIgnoreCase("freebuild")) {
				// Player is in Freebuild World
			} else {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if (!CubiTechUtil.isPlayerAdmin(e.getPlayer())) {
			if (e.getPlayer().getWorld().getName().equalsIgnoreCase("freebuild")) {
				// Player is in Freebuild World
			} else {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();

			if (p.getWorld().getBiome(p.getLocation().getBlockX(), p.getLocation().getBlockZ()) == Biome.EXTREME_HILLS) {
				if (CubiTechUtil.players1vs1Fighting[0] != null && CubiTechUtil.players1vs1Fighting[1] != null) {
					if (CubiTechUtil.players1vs1Fighting[0] == p || CubiTechUtil.players1vs1Fighting[1] == p) {
						e.setCancelled(false);
						return;
					}
				}
				e.setCancelled(true);
			}

			if (CubiTechUtil.playersTutorial.contains(p)) {
				e.setCancelled(true);
			}

			if (CubiTechUtil.getPlayerLevel(p) >= 50 && e.getDamage() > 2) {
				e.setDamage(e.getDamage() - 2);
			} else if (CubiTechUtil.getPlayerLevel(p) >= 30 && e.getDamage() > 1) {
				e.setDamage(e.getDamage() - 1);
			}

		}

		if (e.getEntity() instanceof Monster) {
			// Health Bar over Head
			final LivingEntity target = (LivingEntity) e.getEntity();
			target.setCustomNameVisible(true);

			int percent = (int) (((Damageable) target).getHealth() * 5);

			if (percent <= 33) {
				target.setCustomName(ChatColor.DARK_RED + "\u2665 " + ChatColor.RED + percent + " %");
			} else if (percent <= 66) {
				target.setCustomName(ChatColor.GOLD + "\u2665\u2665 " + ChatColor.YELLOW + percent + " %");
			} else {
				target.setCustomName(ChatColor.DARK_GREEN + "\u2665\u2665\u2665 " + ChatColor.GREEN + percent + " %");
			}
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					target.setCustomName("");
					target.setCustomNameVisible(false);
				}
			}, 60L);
		}
	}

	@EventHandler
	public void onEntityChangeBlock(EntityChangeBlockEvent e) {
		if (e.getEntityType() == EntityType.ENDERMAN) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player damager = (Player) e.getDamager();
			if (!(e.getEntity() instanceof Player)) {
				// You can always damage mobs ..
				if (e.getEntity() instanceof Villager) {
					// But not NPC Villagers ..
					Villager v = (Villager) e.getEntity();
					if (v == CubiTechUtil.npcAusbilder || v == CubiTechUtil.npcAnwerber || v == CubiTechUtil.npcVerkaeufer) {
						e.setCancelled(true);
						return;
					} else {
						if (CubiTechUtil.npcsWaechter.contains(v)) {
							ChatColor lvlColor = ChatColor.GRAY;
							if (CubiTechUtil.getPlayerFaction(damager).equalsIgnoreCase("grass")) {
								lvlColor = ChatColor.GREEN;
							} else if (CubiTechUtil.getPlayerFaction(damager).equalsIgnoreCase("sand")) {
								lvlColor = ChatColor.GOLD;
							} else if (CubiTechUtil.getPlayerFaction(damager).equalsIgnoreCase("snow")) {
								lvlColor = ChatColor.DARK_AQUA;
							}
							String s = lvlColor + "";

							if (v.getCustomName().startsWith(s)) {
								e.setCancelled(true);
								return;
							}

						}
					}
				}
				e.setCancelled(false);
			}
			if (e.getEntity() instanceof Player) {
				Player p = (Player) e.getEntity();

				// Player damages Player

				if (CubiTechUtil.players1vs1Fighting[0] != null && CubiTechUtil.players1vs1Fighting[1] != null) {
					if (CubiTechUtil.players1vs1Fighting[0] == p || CubiTechUtil.players1vs1Fighting[1] == p) {
						e.setCancelled(false);
						return;
					}
				}

				Biome biome = p.getWorld().getBiome(p.getLocation().getBlockX(), p.getLocation().getBlockZ());
				if (biome == Biome.EXTREME_HILLS) {
					e.setCancelled(true);
					return;
				} else {
					e.setCancelled(false);
				}

				if (CubiTechUtil.getPlayerFaction(p).equalsIgnoreCase(CubiTechUtil.getPlayerFaction(damager))) {
					// Dont damage people from your faction
					e.setCancelled(true);
					return;
				} else {
					e.setCancelled(false);
				}

			}

			Player player = (Player) e.getDamager();
			if (CubiTechUtil.getPlayerClass(player).equalsIgnoreCase("Schurke")) {
				if (player.getItemInHand().getType() == Material.SHEARS) {
					LivingEntity ltarget = (LivingEntity) e.getEntity();
					if (ltarget instanceof Player) {
						Player target = (Player) ltarget;

						if (CubiTechUtil.players1vs1Fighting[0] != null && CubiTechUtil.players1vs1Fighting[1] != null) {
							if (CubiTechUtil.players1vs1Fighting[0] == target || CubiTechUtil.players1vs1Fighting[1] == target) {
								e.setCancelled(false);
								ltarget.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 80, 1), true);
								ltarget.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 80, 1), true);
								return;
							}
						} else {
							e.setCancelled(false);
						}

						Biome biome = target.getWorld().getBiome(target.getLocation().getBlockX(), target.getLocation().getBlockZ());
						if (biome == Biome.EXTREME_HILLS) {
							e.setCancelled(true);
							return;
						}

						if (CubiTechUtil.getPlayerFaction(target).equalsIgnoreCase(CubiTechUtil.getPlayerFaction(damager))) {
							// Dont damage people from your faction
							e.setCancelled(true);
							return;
						} else {
							e.setCancelled(false);
						}

					}
					if (!e.isCancelled()) {
						ltarget.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 80, 1), true);
						ltarget.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 80, 1), true);
					}
				}
			} else if (CubiTechUtil.getPlayerClass(player).equalsIgnoreCase("Magier")) {
				if (player.getItemInHand().getType() == Material.BLAZE_ROD) {
					LivingEntity ltarget = (LivingEntity) e.getEntity();
					if (ltarget instanceof Player) {
						Player target = (Player) ltarget;

						if (CubiTechUtil.players1vs1Fighting[0] != null && CubiTechUtil.players1vs1Fighting[1] != null) {
							if (CubiTechUtil.players1vs1Fighting[0] == target || CubiTechUtil.players1vs1Fighting[1] == target) {
								e.setCancelled(false);
								return;
							}
						} else {
							e.setCancelled(false);
						}

						Biome biome = target.getWorld().getBiome(target.getLocation().getBlockX(), target.getLocation().getBlockZ());
						if (biome == Biome.EXTREME_HILLS) {
							e.setCancelled(true);
							return;
						}

						if (CubiTechUtil.getPlayerFaction(target).equalsIgnoreCase(CubiTechUtil.getPlayerFaction(damager))) {
							// Dont damage people from your faction
							e.setCancelled(true);
							return;
						} else {
							e.setCancelled(false);
						}

					}
					if (!e.isCancelled()) {
						ltarget.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10, 1), true);
						ltarget.setFireTicks(10);
					}
				}
			}
			/*
			 * Not used anymore because Scoreboard if (e.getEntity() instanceof
			 * Player) { Player target = (Player) e.getEntity(); if
			 * (target.getHealth() <= 5) { player.sendMessage(ChatColor.BLUE +
			 * "Leben von " + ChatColor.AQUA + target.getName() + ChatColor.BLUE
			 * + " : " + ChatColor.RED + target.getHealth() * 5 + "%"); } else
			 * if (target.getHealth() <= 10) { player.sendMessage(ChatColor.BLUE
			 * + "Leben von " + ChatColor.AQUA + target.getName() +
			 * ChatColor.BLUE + " : " + ChatColor.YELLOW + target.getHealth() *
			 * 5 + "%"); } else if (target.getHealth() <= 20) {
			 * player.sendMessage(ChatColor.BLUE + "Leben von " + ChatColor.AQUA
			 * + target.getName() + ChatColor.BLUE + " : " + ChatColor.GREEN +
			 * target.getHealth() * 5 + "%"); } }
			 */
		} else if (e.getDamager() instanceof Projectile) {
			Projectile f = (Projectile) e.getDamager();
			if (f.getShooter() instanceof Player) {
				Player p = (Player) f.getShooter();
				if (e.getEntity() instanceof Player) {
					Player target = (Player) e.getEntity();

					if (CubiTechUtil.players1vs1Fighting[0] != null && CubiTechUtil.players1vs1Fighting[1] != null) {
						if (CubiTechUtil.players1vs1Fighting[0] == target || CubiTechUtil.players1vs1Fighting[1] == target) {
							e.setCancelled(false);
							return;
						}
					} else {
						e.setCancelled(false);
					}

					Biome biome = target.getWorld().getBiome(target.getLocation().getBlockX(), target.getLocation().getBlockZ());
					if (biome == Biome.EXTREME_HILLS) {
						e.setCancelled(true);
						return;
					}

					if (CubiTechUtil.getPlayerFaction(target).equalsIgnoreCase(CubiTechUtil.getPlayerFaction(p))) {
						// Dont damage people from your faction
						e.setCancelled(true);
						return;
					} else {
						e.setCancelled(false);
					}

					if (!e.isCancelled()) {
						ItemStack item = p.getItemInHand();
						if (item.getType() == Material.BLAZE_ROD) {
							if (item.getItemMeta() != null) {
								ItemMeta meta = item.getItemMeta();
								if (meta.getEnchants().containsKey(Enchantment.DAMAGE_ALL)) {
									int eLvl = meta.getEnchants().get(Enchantment.DAMAGE_ALL);
									e.setDamage(e.getDamage() + eLvl / 2);
									if (eLvl >= 5) {
										e.getEntity().setFireTicks(60);
										if (Math.random() > 0.75) {
											e.getEntity().getWorld().strikeLightning(e.getEntity().getLocation());
										}
									}
								}
							}
						}
					}
				}

			}
		}
	}

	@EventHandler
	public void onEntityInteract(EntityInteractEvent e) {
		if (e.getBlock().getType() == Material.SOIL && e.getEntity() instanceof Creature) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerKick(PlayerKickEvent e) {
		e.setLeaveMessage(null);
	}

	@EventHandler
	public void onEntityShootBow(EntityShootBowEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			ItemStack b = e.getBow();
			if (b.getItemMeta() != null) {
				if (b.getItemMeta().getDisplayName().equals("§9Wither" + ChatColor.WHITE + "-" + ChatColor.RED + "Bogen")) {
					p.launchProjectile(WitherSkull.class).setVelocity(e.getProjectile().getVelocity());
					e.setCancelled(true);
					return;
				}
				if (b.getItemMeta().getEnchants() != null && b.getEnchantments().size() > 0) {
					// Not a simple Bow ..
					if (!(CubiTechUtil.isPlayerAdmin(p) || CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Jaeger")
							|| CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Ritter")
							|| CubiTechUtil.getPlayerClass(p).equalsIgnoreCase("Adeliger") || CubiTechUtil.getPlayerClass(p).equalsIgnoreCase(
							"YouTuber"))) {
						e.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerItemPickup(PlayerPickupItemEvent e) {

	}

	@EventHandler
	public void onBlockIgnite(BlockIgniteEvent e) {
		if (e.getCause() != IgniteCause.FLINT_AND_STEEL) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent e) {
		e.blockList().clear();
	}

	@EventHandler
	public void onExplosionPrime(ExplosionPrimeEvent e) {
		if (e.getEntity() instanceof TNTPrimed || e.getClass() == TNTPrimed.class) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent e) {
		if (!CubiTechUtil.isPlayerAdmin(e.getPlayer())) {
			if (e.getBucket() == Material.LAVA_BUCKET) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent e) {
		if (e.getCause() == TeleportCause.ENDER_PEARL) {
			if (!(CubiTechUtil.isPlayerAdmin(e.getPlayer()))) {
				e.setCancelled(true);
			}
		}
		CubiTechUtil.checkPlayerMountHorse(e.getPlayer());
	}

	@EventHandler
	public void onLeavesDecay(LeavesDecayEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onBlockFade(BlockFadeEvent e) {
		if (e.getBlock().getType() == Material.ICE || e.getBlock().getType() == Material.SNOW_BLOCK) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockForm(BlockFormEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onBlockDispense(BlockDispenseEvent e) {
		if (e.getBlock().getState() instanceof Dispenser) {
			Inventory dispenserInv = ((Dispenser) e.getBlock().getState()).getInventory();
			ItemStack[] contents = dispenserInv.getContents();
			for (int i = 0; i < contents.length; i++) {
				if (contents[i] != null) {
					contents[i].setAmount(64);
				}
			}
			dispenserInv.setContents(contents);
		}
	}

	@EventHandler
	public void onServerListPing(ServerListPingEvent e) {
		CubiTechUtil.serverListPingIPs.add(e.getAddress().getHostAddress());
		ChatColor[] rndColors1 = new ChatColor[] { ChatColor.DARK_GREEN, ChatColor.DARK_PURPLE, ChatColor.GOLD };
		ChatColor col = rndColors1[new Random().nextInt(rndColors1.length)];
		String motd = col + "§l<< §nCubiTech" + col + "§l >> §8| §9DE §8| §b§k|§3§o RPG §b§k|";
		e.setMotd(motd);
		if (CubiTechUtil.fakeSlots) {
			e.setMaxPlayers(CubiTechUtil.fakeSlotsNum);
		}
	}

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e) {
		if (CubiTechUtil.playersTmpBanned.contains(e.getPlayer().getName())) {
			e.disallow(Result.KICK_BANNED, "§cDu bist bis zum naechsten Server-Start gebannt.");
			return;
		}

		if (e.getResult() == Result.KICK_BANNED) {
			e.setKickMessage("§cDu bist gebannt!");
		} else if (e.getResult() == Result.KICK_WHITELIST) {
			e.setKickMessage("§cDu stehst leider nicht auf der Whitelist!");
		}

		if (e.getResult() == Result.KICK_FULL) {
			Player p = e.getPlayer();

			String sClass = null;
			if (CubiTechUtil.getPlayerClass(p) != null) {
				sClass = CubiTechUtil.getPlayerClass(p);
			} else {
				try {
					Properties props = new Properties();
					BufferedInputStream stream = new BufferedInputStream(new FileInputStream(new File("plugins//CubiTech//users.txt")));
					props.load(stream);
					stream.close();
					if (props.containsKey(p.getName())) {
						sClass = props.getProperty(p.getName()).split(" ")[0];
					} else {
						sClass = "Knappe";
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			if (CubiTechUtil.isPlayerAdmin(p) || sClass.equalsIgnoreCase("Ritter") || sClass.equalsIgnoreCase("Adeliger")
					|| sClass.equalsIgnoreCase("YouTuber")) {
				ArrayList<Player> kickablePlayers = new ArrayList<Player>();
				for (Player online : Bukkit.getOnlinePlayers()) {
					if (!(CubiTechUtil.isPlayerAdmin(online) || CubiTechUtil.getPlayerClass(online).equalsIgnoreCase("Ritter")
							|| CubiTechUtil.getPlayerClass(online).equalsIgnoreCase("Adeliger")
							|| CubiTechUtil.getPlayerClass(online).equalsIgnoreCase("YouTuber") || CubiTechUtil.getPlayerClass(online)
							.equalsIgnoreCase("Supporter"))) {
						kickablePlayers.add(online);
					}
				}

				if (kickablePlayers.size() > 0) {
					int rndPl = new Random().nextInt(kickablePlayers.size());
					Player kickPlayer = kickablePlayers.get(rndPl);
					kickPlayer.kickPlayer(ChatColor.RED + "Du wurdest gekickt, damit ein anderer Spieler auf den Server kann.");
					e.setResult(Result.ALLOWED);
					e.allow();
				} else {
					e.setKickMessage(ChatColor.RED + "Es ist leider kein Spieler online, der gekickt werden kann.");
				}

			}
		}
	}

	@EventHandler
	public void onBlockSpread(BlockSpreadEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		if (CubiTechUtil.isPlayerAdmin(e.getPlayer())) {
			for (int i = 0; i < e.getLines().length; i++) {
				e.setLine(i, ChatColor.translateAlternateColorCodes('&', e.getLines()[i]));
			}
		}
	}

	@EventHandler
	public void onVotifierEvent(VotifierEvent event) {
		Vote vote = event.getVote();
		plugin.getLogger().info("Received vote: " + vote);
		Player p = Bukkit.getPlayerExact(vote.getUsername());
		if (p != null) {
			CubiTechUtil.voteMade(p);
		}
	}

	@EventHandler
	public void onPlayerToggleFlight(PlayerToggleFlightEvent e) {
		Player p = e.getPlayer();
		if (p.getGameMode() != GameMode.CREATIVE) {
			e.setCancelled(true);
			if (CubiTechUtil.playersMountedHorse.containsKey(p)) {
				CubiTechUtil.playersMountedHorse.get(p).die();
				CubiTechUtil.playersMountedHorse.remove(p);
			} else {
				if (p.getWorld().getBiome(p.getLocation().getBlockX(), p.getLocation().getBlockZ()) != Biome.EXTREME_HILLS) {
					if (p.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType() != Material.AIR) {
						final Vector dir = p.getLocation().getDirection().multiply(1.0);
						final Vector vec = new Vector(dir.getX(), 0.5, dir.getZ());
						EntityHorse eh = CubiTechUtil.mountHorse(p);
						eh.getBukkitEntity().setVelocity(vec);
						p.getWorld().playEffect(p.getLocation(), Effect.SMOKE, 10);
					}
				}
			}
		}
	}
}
