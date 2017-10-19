package de.typiex.cubitech;

import java.util.Random;

import net.minecraft.server.v1_6_R2.EntityPotion;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_6_R2.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class CubiTechCommandDropparty extends CubiTechCommand {

	public CubiTechCommandDropparty(CubiTech c, String cmd, boolean permissionNeeded) {
		super(c, cmd, permissionNeeded);
	}

	public void onCommand(CommandSender sender, String[] args) {

		if (sender instanceof Player) {
			final Player p = (Player) sender;
			if (CubiTechUtil.isPlayerAdmin(p)) {

				Location l1 = new Location(p.getWorld(), 812, 203, 870);
				Location l2 = new Location(p.getWorld(), 1146, 203, 1165);

				final int minX = (int) l1.getX();
				final int minZ = (int) l1.getZ();

				final int maxX = (int) l2.getX();
				final int maxZ = (int) l2.getZ();

				final int y = (int) l1.getY();
				final int rocketY = 130;

				final World world = l1.getWorld();

				final int task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
					@Override
					public void run() {
						for (int i = 0; i < 100; i++) {
							int ranX = (int) (Math.random() * (maxX - minX) + minX);
							int ranZ = (int) (Math.random() * (maxZ - minZ) + minZ);

							ItemStack item = null;

							Material[] items = { Material.GOLDEN_APPLE, Material.DIAMOND, Material.GOLD_INGOT, Material.IRON_INGOT, Material.COAL, Material.APPLE, Material.POTION, Material.GOLD_RECORD, Material.EXP_BOTTLE, Material.GRILLED_PORK, Material.IRON_BLOCK, Material.GOLD_BLOCK, Material.COOKIE, Material.WOOL, Material.GRILLED_PORK, Material.YELLOW_FLOWER, Material.RED_ROSE, Material.WOOD, Material.BOW, Material.ARROW };

							int r = new Random().nextInt(items.length);
							try {
								item = new ItemStack(items[r], 1);
							} catch (Exception ex) {
							}
							if(r == 1) {
								item = CubiTechUtil.getItemCubiCoupon();
							}
							if (item != null) {
								Location ranLoc = new Location(world, ranX, y, ranZ);
								world.dropItem(ranLoc, item);

								PotionType type = PotionType.STRENGTH;
								if (r > 10) {
									type = PotionType.INSTANT_HEAL;
								} else if (r > 5) {
									type = PotionType.SPEED;
								} else {
									type = PotionType.REGEN;
								}
								Potion p = new Potion(type);
								int data = p.toDamageValue();
								
								net.minecraft.server.v1_6_R2.World nmsWorld = ((CraftWorld) world).getHandle();
								EntityPotion ent = new EntityPotion(nmsWorld, ranLoc.getX() + 0.5, ranLoc.getY() + 1, ranLoc.getZ() + 0.5, new net.minecraft.server.v1_6_R2.ItemStack(373, 1, data));
								ent.g(0, 0.4, 0);
								nmsWorld.addEntity(ent);
								
								Firework fw = (Firework) world.spawnEntity(new Location(world,ranLoc.getX(),rocketY, ranLoc.getZ()), EntityType.FIREWORK);
								FireworkMeta meta = fw.getFireworkMeta();
								
								FireworkEffect effect = null;
								if(Math.random() > 0.6) {
									effect = FireworkEffect.builder().withColor(Color.fromRGB(new Random().nextInt(0xFFFFFF))).withFade(Color.fromRGB(new Random().nextInt(0xFFFFFF))).withFlicker().with(Type.BURST).build();
								} else if (Math.random() > 0.3) {
									effect = FireworkEffect.builder().withColor(Color.fromRGB(new Random().nextInt(0xFFFFFF))).withFade(Color.fromRGB(new Random().nextInt(0xFFFFFF))).withFlicker().with(Type.BALL_LARGE).build();
								} else {
									effect = FireworkEffect.builder().withColor(Color.fromRGB(new Random().nextInt(0xFFFFFF))).withFade(Color.fromRGB(new Random().nextInt(0xFFFFFF))).with(Type.STAR).build();
								}
								
								meta.addEffect(effect);
								meta.setPower(2);
								fw.setFireworkMeta(meta);
								
							}
							
						}
					}
				}, 0L, 20L);

				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						Bukkit.getScheduler().cancelTask(task);
						p.sendMessage(ChatColor.LIGHT_PURPLE + "Drop-Party beendet!");
					}
				}, 1200L);

				for (Player q : Bukkit.getOnlinePlayers()) {
					q.sendMessage(ChatColor.DARK_AQUA + "Es wurde eine " + ChatColor.GOLD + "Drop-Party" + ChatColor.DARK_AQUA + " gestartet!");
				}
				p.sendMessage(ChatColor.LIGHT_PURPLE + "Drop-Party gestartet!");

			} else {
				p.sendMessage(ChatColor.RED + "Dafuer hast Du leider keine Rechte!");
			}
		}
	}
}
