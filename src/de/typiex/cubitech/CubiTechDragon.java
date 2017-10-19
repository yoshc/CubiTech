package de.typiex.cubitech;

import net.minecraft.server.v1_6_R2.World;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class CubiTechDragon extends net.minecraft.server.v1_6_R2.EntityEnderDragon {
	public Player rider;

	double xTick = 0, yTick = 0, zTick = 0;
	double myX = 0, myY = 0, myZ = 0;
	long myTicks = 0;

	public CubiTechDragon(World w, Player p) {
		super(w);
		this.rider = p;
		setPosition(rider.getLocation().getX(), rider.getLocation().getY(), rider.getLocation().getZ());
	}
	
	public World d() {
		return this.world;
	}

	@Override
	public void g(double d0, double d1, double d2) {
	}

	@Override
	public void c() {
		move();
	}

	public void update() {
		Vector v = rider.getEyeLocation().getDirection().normalize();
		xTick = v.getX();
		yTick = v.getY();
		zTick = v.getZ();
		myX += xTick;
		myY += yTick;
		myZ += zTick;

		yaw = rider.getLocation().getYaw() + 180;
		while (yaw > 360)
			yaw -= 360;
		while (yaw < 0)
			yaw += 360;
		if (yaw < 45 || yaw > 315)
			yaw = 0F;
		else if (yaw < 135)
			yaw = 90F;
		else if (yaw < 225)
			yaw = 180F;
		else
			yaw = 270F;
	}

	public void move() {
		if (rider == null) {
			die();
			return;
		}
		
		if (rider.isSneaking()) {
			return;
		}
		
		update();

		setPosition(myX, myY, myZ);
		velocityChanged = true;

		

		myTicks++;
	}

}