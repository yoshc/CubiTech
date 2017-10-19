package de.typiex.cubitech;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class CubiTechCommandAdminkit extends CubiTechCommand {

	public CubiTechCommandAdminkit(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (CubiTechUtil.isPlayerAdmin(p)) {
				PlayerInventory i = p.getInventory();

				ItemStack is_sword = new ItemStack(Material.DIAMOND_SWORD, 1);
				ItemMeta am_sword = is_sword.getItemMeta();
				am_sword.setDisplayName(ChatColor.RED + "Admin Schwert");
				for (int j = 0; j < Enchantment.values().length; j++) {
					am_sword.addEnchant(Enchantment.values()[j], 99, true);
				}
				is_sword.setItemMeta(am_sword);
				i.setItem(0, is_sword);
				
				ItemStack is_bow = new ItemStack(Material.BOW, 1);
				ItemMeta am_bow = is_bow.getItemMeta();
				am_bow.setDisplayName(ChatColor.RED + "Admin Bogen");
				for (int j = 0; j < Enchantment.values().length; j++) {
					am_bow.addEnchant(Enchantment.values()[j], 99, true);
				}
				is_bow.setItemMeta(am_bow);
				i.setItem(1, is_bow);
				
				ItemStack is_arrow = new ItemStack(Material.ARROW, 1);
				ItemMeta am_arrow = is_arrow.getItemMeta();
				am_arrow.setDisplayName(ChatColor.RED + "Admin Pfeil");
				for (int j = 0; j < Enchantment.values().length; j++) {
					am_arrow.addEnchant(Enchantment.values()[j], 99, true);
				}
				is_arrow.setItemMeta(am_arrow);
				i.setItem(2, is_arrow);

				ItemStack is_helmet = new ItemStack(Material.LEATHER_HELMET, 1);
				LeatherArmorMeta am_helmet = (LeatherArmorMeta) is_helmet.getItemMeta();
				am_helmet.setColor(Color.RED);
				am_helmet.setDisplayName(ChatColor.RED + "Admin Helm");
				for (int j = 0; j < Enchantment.values().length; j++) {
					am_helmet.addEnchant(Enchantment.values()[j], 99, true);
				}
				is_helmet.setItemMeta(am_helmet);
				i.setHelmet(is_helmet);
				
				ItemStack is_chestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
				LeatherArmorMeta am_chestplate = (LeatherArmorMeta) is_chestplate.getItemMeta();
				am_chestplate.setColor(Color.RED);
				am_chestplate.setDisplayName(ChatColor.RED + "Admin Harnisch");
				for (int j = 0; j < Enchantment.values().length; j++) {
					am_chestplate.addEnchant(Enchantment.values()[j], 99, true);
				}
				is_chestplate.setItemMeta(am_chestplate);
				i.setChestplate(is_chestplate);
				
				ItemStack is_leggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);
				LeatherArmorMeta am_leggings = (LeatherArmorMeta) is_leggings.getItemMeta();
				am_leggings.setColor(Color.RED);
				am_leggings.setDisplayName(ChatColor.RED + "Admin Hose");
				for (int j = 0; j < Enchantment.values().length; j++) {
					am_leggings.addEnchant(Enchantment.values()[j], 99, true);
				}
				is_leggings.setItemMeta(am_leggings);
				i.setLeggings(is_leggings);
				
				ItemStack is_boots = new ItemStack(Material.LEATHER_BOOTS, 1);
				LeatherArmorMeta am_boots = (LeatherArmorMeta) is_boots.getItemMeta();
				am_boots.setColor(Color.RED);
				am_boots.setDisplayName(ChatColor.RED + "Admin Schuhe");
				for (int j = 0; j < Enchantment.values().length; j++) {
					am_boots.addEnchant(Enchantment.values()[j], 99, true);
				}
				is_boots.setItemMeta(am_boots);
				i.setBoots(is_boots);
			}
		} else {
			sender.sendMessage(ChatColor.RED + "Fehler. Du musst ein Spieler sein.");
		}
	}
}
