package de.typiex.cubitech;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.typiex.cubitech.CubiTechUtil.KillQuest;

public class CubiTechCommandQuest extends CubiTechCommand {

	public CubiTechCommandQuest(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {

		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (args.length >= 1) {
				if(args[0].equalsIgnoreCase("abbrechen") || args[0].equalsIgnoreCase("cancel")) {
					if(CubiTechUtil.playerQuests.containsKey(p)) {
						CubiTechUtil.playerQuests.remove(p);
						p.sendMessage(ChatColor.GREEN + "Du hast deine Quest abgebrochen.");
					} else {
						p.sendMessage(ChatColor.RED + "Du hast derzeit keine aktive Quest.");
						p.sendMessage(ChatColor.RED + "Schreibe " + ChatColor.DARK_GREEN + "/quest" + ChatColor.RED + ", um eine Quest auszuwaehlen.");
					}
				}
			} else {
				if (!CubiTechUtil.playerQuests.containsKey(p)) {
					// Player has no Quest yet
					Inventory inv = Bukkit.getServer().createInventory(null, 27, "Waehle eine Quest:");

					ItemStack item1 = new ItemStack(Material.SKULL_ITEM, 1, (byte) 2);
					ItemMeta meta1 = item1.getItemMeta();
					meta1.setDisplayName(ChatColor.GOLD + "Zombie-Toeter I");
					ArrayList<String> desc1 = new ArrayList<String>();
					desc1.add(" ");
					desc1.add(ChatColor.GREEN + "Toete " + ChatColor.DARK_GREEN + "10" + ChatColor.GREEN + " Zombies");
					desc1.add(ChatColor.DARK_AQUA + "Belohnung: " + ChatColor.AQUA + "100 EP, 20 Cubi");
					meta1.setLore(desc1);
					item1.setItemMeta(meta1);
					inv.setItem(0, item1);

					ItemStack item2 = new ItemStack(Material.SKULL_ITEM, 1, (byte) 0);
					ItemMeta meta2 = item2.getItemMeta();
					meta2.setDisplayName(ChatColor.GOLD + "Skelett-Toeter I");
					ArrayList<String> desc2 = new ArrayList<String>();
					desc2.add(" ");
					desc2.add(ChatColor.GREEN + "Toete " + ChatColor.DARK_GREEN + "10" + ChatColor.GREEN + " Skelette");
					desc2.add(ChatColor.DARK_AQUA + "Belohnung: " + ChatColor.AQUA + "100 EP, 20 Cubi");
					meta2.setLore(desc2);
					item2.setItemMeta(meta2);
					inv.setItem(1, item2);

					ItemStack item3 = new ItemStack(Material.SKULL_ITEM, 1, (byte) 4);
					ItemMeta meta3 = item3.getItemMeta();
					meta3.setDisplayName(ChatColor.GOLD + "Creeper-Toeter I");
					ArrayList<String> desc3 = new ArrayList<String>();
					desc3.add(" ");
					desc3.add(ChatColor.GREEN + "Toete " + ChatColor.DARK_GREEN + "10" + ChatColor.GREEN + " Creeper");
					desc3.add(ChatColor.DARK_AQUA + "Belohnung: " + ChatColor.AQUA + "100 EP, 20 Cubi");
					meta3.setLore(desc3);
					item3.setItemMeta(meta3);
					inv.setItem(2, item3);

					ItemStack item4 = new ItemStack(Material.SKULL_ITEM, 1, (byte) 1);
					ItemMeta meta4 = item4.getItemMeta();
					meta4.setDisplayName(ChatColor.GOLD + "Witherskelett-Toeter I");
					ArrayList<String> desc4 = new ArrayList<String>();
					desc4.add(" ");
					desc4.add(ChatColor.GREEN + "Toete " + ChatColor.DARK_GREEN + "10" + ChatColor.GREEN + " Witherskelette");
					desc4.add(ChatColor.DARK_AQUA + "Belohnung: " + ChatColor.AQUA + "150 EP, 30 Cubi");
					meta4.setLore(desc4);
					item4.setItemMeta(meta4);
					inv.setItem(3, item4);

					ItemStack item5 = new ItemStack(Material.SKULL_ITEM, 1, (byte) 2);
					ItemMeta meta5 = item5.getItemMeta();
					meta5.setDisplayName(ChatColor.GOLD + "Zombie-Toeter II");
					ArrayList<String> desc5 = new ArrayList<String>();
					desc5.add(" ");
					desc5.add(ChatColor.GREEN + "Toete " + ChatColor.DARK_GREEN + "20" + ChatColor.GREEN + " Zombies");
					desc5.add(ChatColor.DARK_AQUA + "Belohnung: " + ChatColor.AQUA + "225 EP, 45 Cubi");
					meta5.setLore(desc5);
					item5.setItemMeta(meta5);
					inv.setItem(9, item5);

					ItemStack item6 = new ItemStack(Material.SKULL_ITEM, 1, (byte) 0);
					ItemMeta meta6 = item6.getItemMeta();
					meta6.setDisplayName(ChatColor.GOLD + "Skelett-Toeter II");
					ArrayList<String> desc6 = new ArrayList<String>();
					desc6.add(" ");
					desc6.add(ChatColor.GREEN + "Toete " + ChatColor.DARK_GREEN + "20" + ChatColor.GREEN + " Skelette");
					desc6.add(ChatColor.DARK_AQUA + "Belohnung: " + ChatColor.AQUA + "225 EP, 45 Cubi");
					meta6.setLore(desc6);
					item6.setItemMeta(meta6);
					inv.setItem(10, item6);

					ItemStack item7 = new ItemStack(Material.SKULL_ITEM, 1, (byte) 4);
					ItemMeta meta7 = item7.getItemMeta();
					meta7.setDisplayName(ChatColor.GOLD + "Creeper-Toeter II");
					ArrayList<String> desc7 = new ArrayList<String>();
					desc7.add(" ");
					desc7.add(ChatColor.GREEN + "Toete " + ChatColor.DARK_GREEN + "20" + ChatColor.GREEN + " Creeper");
					desc7.add(ChatColor.DARK_AQUA + "Belohnung: " + ChatColor.AQUA + "225 EP, 45 Cubi");
					meta7.setLore(desc7);
					item7.setItemMeta(meta7);
					inv.setItem(11, item7);

					ItemStack item8 = new ItemStack(Material.SKULL_ITEM, 1, (byte) 1);
					ItemMeta meta8 = item8.getItemMeta();
					meta8.setDisplayName(ChatColor.GOLD + "Witherskelett-Toeter II");
					ArrayList<String> desc8 = new ArrayList<String>();
					desc8.add(" ");
					desc8.add(ChatColor.GREEN + "Toete " + ChatColor.DARK_GREEN + "20" + ChatColor.GREEN + " Witherskelette");
					desc8.add(ChatColor.DARK_AQUA + "Belohnung: " + ChatColor.AQUA + "325 EP, 65 Cubi");
					meta8.setLore(desc8);
					item8.setItemMeta(meta8);
					inv.setItem(12, item8);

					p.openInventory(inv);
				} else {
					// Player already has a Quest
					KillQuest q = CubiTechUtil.playerQuests.get(p);
					p.sendMessage(ChatColor.DARK_GREEN + "--- " + ChatColor.YELLOW + "Quest" + ChatColor.DARK_GREEN + ChatColor.DARK_GREEN + " ---");
					p.sendMessage(ChatColor.GREEN + "Toete " + ChatColor.GOLD + q.amount + " " + q.entityType.name());
					p.sendMessage(ChatColor.DARK_GREEN + "" + q.killed + ChatColor.GREEN + " / " + ChatColor.DARK_GREEN + q.amount + ChatColor.YELLOW + " (" + ChatColor.GOLD + "noch " + (q.amount - q.killed) + ChatColor.YELLOW + ")");
				}
			}
		}
	}
}
