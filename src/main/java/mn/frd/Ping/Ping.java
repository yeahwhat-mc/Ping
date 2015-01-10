package mn.frd.Ping;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Ping extends JavaPlugin implements Listener {
	@Override
	public void onEnable(){
		// Register events
		this.getServer().getPluginManager().registerEvents(this, this);
		// Log loading
		this.getLogger().info("Loaded " + this.getDescription().getName() + " v" + this.getDescription().getVersion());
	}

	@Override
	public void onDisable(){
		// Log disabling
		this.getLogger().info("Disabled " + this.getDescription().getName() + " v" + this.getDescription().getVersion());
	}

	Class<?> CPClass;

	String serverName  = Bukkit.getServer().getClass().getPackage().getName(),
				 serverVersion = serverName.substring(serverName.lastIndexOf(".") + 1, serverName.length());

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		// Define prefix
		String prefix = ChatColor.RED + "[Ping]" + ChatColor.RESET;


		//  /ping command
		if (cmd.getName().equalsIgnoreCase("ping"))
		{
			// Check for arguments
			if(args.length == 0) {
				// Send command overview
				if (!(sender instanceof Player)){
					sender.sendMessage(prefix + " " +  "Your ping: " + ChatColor.GRAY + "0 ms" + ChatColor.RESET + " you silly console!");
					return true;
				} else {
					if(sender.hasPermission("ping.self")){
						// Define player object
						final Player p = (Player) sender;
						sender.sendMessage(prefix + " " + "Your ping: " + ChatColor.GRAY + getPing(p) + " ms" + ChatColor.RESET);
						return true;
					} else {
						sender.sendMessage(prefix + " " +  "You dont have the permission: " + ChatColor.GRAY + "- ping.self");
						return true;
					}
				}
			} else if(args.length == 1) {
				if(sender.hasPermission("ping.others")){
					Player argplayer = getServer().getPlayer(args[0]);
					if(argplayer == null) {
							sender.sendMessage(prefix + " " + "The player " + ChatColor.GRAY + args[0] + ChatColor.RESET +" could not be found");
							return true;
					}
					// Send command overview
					sender.sendMessage(prefix + " " + getServer().getPlayer(args[0]).getDisplayName().toString() + "'s ping: " + ChatColor.GRAY + getPing(argplayer) + " ms" + ChatColor.RESET);
					return true;
				} else {
					sender.sendMessage(prefix + " " +  "You dont have the permission: " + ChatColor.GRAY + "- ping.others");
					return true;
				}
			} else {
				// Send command overview
				sender.sendMessage(prefix + ChatColor.YELLOW +" Plugin help:");
				sender.sendMessage("/ping" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Check your ping.");
				sender.sendMessage("/ping <player>" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Check ping of player.");
				return true;
			}

		}
		return false;
	}

	public int getPing(Player p) {
		try {
			CPClass = Class.forName("org.bukkit.craftbukkit." + serverVersion + ".entity.CraftPlayer");
			Object CraftPlayer = CPClass.cast(p);

			Method getHandle = CraftPlayer.getClass().getMethod("getHandle", new Class[0]);
			Object EntityPlayer = getHandle.invoke(CraftPlayer, new Object[0]);

			Field ping = EntityPlayer.getClass().getDeclaredField("ping");

			return ping.getInt(EntityPlayer);
		} catch (Exception e) {
				e.printStackTrace();
		}
		return 0;
	}
}
