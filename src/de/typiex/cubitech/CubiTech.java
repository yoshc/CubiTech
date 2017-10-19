package de.typiex.cubitech;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Properties;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CubiTech extends JavaPlugin {

	CubiTechCommand[] commands;

	@Override
	public void onEnable() {
		CubiTechUtil.setPlugin(this);
		getServer().getPluginManager().registerEvents(new CubiTechListener(this), this);
		getLogger().info("Registered Listener!");

		try {
			Properties props = new Properties();
			BufferedInputStream stream = new BufferedInputStream(new FileInputStream(new File("plugins//CubiTech//users.txt")));
			props.load(stream);
			stream.close();
			Enumeration<Object> em = props.keys();
			while (em.hasMoreElements()) {
				String str = (String) em.nextElement();
				try {
					String sClass = props.getProperty(str).split(" ")[0];
					String faction = props.getProperty(str).split(" ")[1];
					int level = Integer.valueOf(props.getProperty(str).split(" ")[2]);
					int ep = Integer.valueOf(props.getProperty(str).split(" ")[3]);
					int money = Integer.valueOf(props.getProperty(str).split(" ")[4]);
					CubiTechUtil.playerClasses.put(Bukkit.getPlayerExact(str), sClass);
					CubiTechUtil.playerFactions.put(Bukkit.getPlayerExact(str), faction);
					CubiTechUtil.playerLevels.put(Bukkit.getPlayerExact(str), level);
					CubiTechUtil.playerEP.put(Bukkit.getPlayerExact(str), ep);
					CubiTechUtil.playerMoney.put(Bukkit.getPlayerExact(str), money);
					getLogger()
							.info("Loaded Player " + str + " class=" + sClass + " faction=" + faction + " level=" + level + " ep=" + ep + " money="
									+ money);
				} catch (Exception ex) {
					CubiTechUtil.playerClasses.put(Bukkit.getPlayerExact(str), "Knappe");
					CubiTechUtil.playerFactions.put(Bukkit.getPlayerExact(str), "none");
					CubiTechUtil.playerLevels.put(Bukkit.getPlayerExact(str), 1);
					CubiTechUtil.playerEP.put(Bukkit.getPlayerExact(str), 1);
					CubiTechUtil.playerMoney.put(Bukkit.getPlayerExact(str), 1);
					getLogger().info("Failed to load Player " + str + ". Using Standard.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Player p : Bukkit.getOnlinePlayers()) {
			CubiTechUtil.playerMana.put(p, 1.0);
		}

		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					CubiTechUtil.regenMana(p);
				}
				CubiTechUtil.checkAuction();
			}
		}, 0L, 20L);

		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				CubiTechUtil.saveState();
				CubiTechUtil.setTime();
			}
		}, 0L, 1200L);

		CubiTechUtil.spawnNPCs();

		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				CubiTechUtil.despawnNPCs();
				CubiTechUtil.spawnNPCs();
			}
		}, 0L, 200L);

		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				CubiTechUtil.moveNPCs();
			}
		}, 0L, 10L);

		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				CubiTechUtil.reloadScoreboard();
				for (Player p : Bukkit.getOnlinePlayers()) {
					CubiTechUtil.checkArmorForLevel(p);
					CubiTechUtil.checkPlayerForBiomeBonus(p);
					CubiTechUtil.updatePlayerSigns();
					CubiTechUtil.checkFlyHorse(p);
				}
			}
		}, 0L, 40L);
		
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			public void run() {
				CubiTechUtil.placeSupplyBlocks();
				CubiTechUtil.refillSupplies();
				getLogger().info("Supplies set.");
			}
		}, 40L);
		

		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				CubiTechUtil.giveHourReward();
			}
		}, 72000L, 72000L);

		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				CubiTechUtil.broadcast();
			}
		}, 6000L, 6000L);

		// Add commands here
		CubiTechCommand[] _commands = { new CubiTechCommandTp(this, "tp", false), new CubiTechCommandTphere(this, "tphere", true),
				new CubiTechCommandTpc(this, "tpc", true), new CubiTechCommandMsg(this, "msg", false), new CubiTechCommandKick(this, "kick", true),
				new CubiTechCommandKickall(this, "kickall", true), new CubiTechCommandSpawn(this, "spawn", false),
				new CubiTechCommandSetspawn(this, "setspawn", true), new CubiTechCommandFirstspawn(this, "firstspawn", false),
				new CubiTechCommandSetfirstspawn(this, "setfirstspawn", true), new CubiTechCommandGetclass(this, "getclass", true),
				new CubiTechCommandSetclass(this, "setclass", true), new CubiTechCommandSetlevel(this, "setlevel", true),
				new CubiTechCommandHeal(this, "heal", true), new CubiTechCommandGod(this, "god", true), new CubiTechCommandKill(this, "kill", true),
				new CubiTechCommandClass(this, "class", false), new CubiTechCommandCreload(this, "creload", true),
				new CubiTechCommandAdminkit(this, "adminkit", true), new CubiTechCommandSetep(this, "setep", true),
				new CubiTechCommandGamemode(this, "gamemode", false), new CubiTechCommandSethead(this, "sethead", true),
				new CubiTechCommandSetitemname(this, "setitemname", true), new CubiTechCommandSetitemdesc(this, "setitemdesc", true),
				new CubiTechCommandMoney(this, "money", false), new CubiTechCommandEp(this, "ep", false),
				new CubiTechCommandSetmoney(this, "setmoney", true), new CubiTechCommandGruppe(this, "gruppe", false),
				new CubiTechCommandCubisay(this, "cubisay", true), new CubiTechCommandBroadcast(this, "broadcast", true),
				new CubiTechCommandMount(this, "mount", false), new CubiTechCommandDragon(this, "dragon", true),
				new CubiTechCommandRam(this, "ram", true), new CubiTechCommandKillall(this, "killall", true),
				new CubiTechCommandDropparty(this, "dropparty", true), new CubiTechCommandCoupon(this, "coupon", true),
				new CubiTechCommandPaket(this, "paket", false), new CubiTechCommandLevels(this, "levels", false),
				new CubiTechCommand1vs1(this, "1vs1", false), new CubiTechCommandTexturepack(this, "texturepack", false),
				new CubiTechCommandSetskull(this, "setskull", true), new CubiTechCommandVerkaufen(this, "verkaufen", false),
				new CubiTechCommandKaufen(this, "kaufen", false), new CubiTechCommandList(this, "list", false),
				new CubiTechCommandPartybow(this, "partybow", false), new CubiTechCommandPartybow(this, "partybow", true),
				new CubiTechCommandFreebuild(this, "freebuild", false), new CubiTechCommandQuest(this, "quest", false),
				new CubiTechCommandRepair(this, "repair", true), new CubiTechCommandEnderchest(this, "enderchest", false),
				new CubiTechCommandFly(this, "fly", false), new CubiTechCommandPosition(this, "position", false),
				new CubiTechCommandTpa(this, "tpa", false), new CubiTechCommandTpaccept(this, "tpaccept", false),
				new CubiTechCommandTpahere(this, "tpahere", false), new CubiTechCommandHilfe(this, "hilfe", false),
				new CubiTechCommandBack(this, "back", false), new CubiTechCommandAddpermission(this, "addpermission", true),
				new CubiTechCommandRemovepermission(this, "removepermission", true), new CubiTechCommandSetfaction(this, "setfaction", true),
				new CubiTechCommandFaction(this, "faction", false), new CubiTechCommandShop(this, "shop", false),
				new CubiTechCommandKopfgeld(this, "kopfgeld", false), new CubiTechCommandCode(this, "code", false),
				new CubiTechCommandAsk(this, "ask", false), new CubiTechCommandTutorial(this, "tutorial", false),
				new CubiTechCommandWarn(this, "warn", false), new CubiTechCommandAfk(this, "afk", false),
				new CubiTechCommandCrash(this, "crash", true), new CubiTechCommandWitherbow(this, "witherbow", true),
				new CubiTechCommandVorraete(this, "vorraete", false), new CubiTechCommandVersteigern(this, "versteigern", false),
				new CubiTechCommandBieten(this, "bieten", false), new CubiTechCommandSetspawner(this, "setspawner", false),
				new CubiTechCommandBank(this, "bank", false), new CubiTechCommandVote(this, "vote", false),
				new CubiTechCommandFakeslots(this, "fakeslots", true) };

		commands = _commands;

		getLogger().info("Registered " + commands.length + " commands.");
		
		try {
			@SuppressWarnings("rawtypes")
			Class[] args = new Class[3];
			args[0] = Class.class;
			args[1] = String.class;
			args[2] = int.class;

			Method a = net.minecraft.server.v1_6_R2.EntityTypes.class.getDeclaredMethod("a", args);
			a.setAccessible(true);

			a.invoke(a, CubiTechDragon.class, "EnderDragon", 63);
			
		} catch (Exception e) {
			getLogger().info("Failed at Reflection!");
		}

		CubiTechUtil.reload1vs1();

		getLogger().info("Plugin enabled!");
	}

	@Override
	public void onDisable() {
		CubiTechUtil.saveState();
		CubiTechUtil.reload1vs1();
		CubiTechUtil.despawnNPCs();
		getLogger().info("Plugin disabled!");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		for (int i = 0; i < commands.length; i++) {
			if (commands[i].command.equalsIgnoreCase(cmd.getName())) {
				if (commands[i].permissionNeeded) {
					if (sender.hasPermission(commands[i].command)) {
						commands[i].onCommand(sender, args);
					}
				} else {
					commands[i].onCommand(sender, args);
				}
			}
		}
		return true;
	}
}