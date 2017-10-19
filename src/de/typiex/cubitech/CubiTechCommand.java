package de.typiex.cubitech;

import org.bukkit.command.CommandSender;

public class CubiTechCommand {
	
	public String command;
	public String permission;
	public boolean permissionNeeded;
	protected CubiTech plugin;
	
	public CubiTechCommand(CubiTech c, String cmd, boolean permissionNeeded) {
		this.plugin = c;
		this.command = cmd;
		this.permissionNeeded = permissionNeeded;
		this.permission = "cubitech." + command;
	}
	
	public void onCommand(CommandSender sender, String[] args) {		
	}
	
}