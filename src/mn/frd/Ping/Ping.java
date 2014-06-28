package mn.frd.Ping;

import net.minecraft.server.v1_7_R3.EntityPlayer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftPlayer;
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
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		// Define prefix
		String prefix = "[Ping]";

		// Define player object
		final Player p = (Player) sender;
		
		//  /ping command
		if (cmd.getName().equalsIgnoreCase("ping"))
		{
			// Check for arguments
			if(args.length == 0) {
				// Send command overview
				sender.sendMessage(prefix + " Your ping: " + getPing(p) + " ms");
				return true;
			} else if(args.length == 1) {
				Player argplayer = getServer().getPlayer(args[0]);
				// Send command overview
				sender.sendMessage(prefix + " " + args[0].toString() + "'s ping: "+ getPing(argplayer) + " ms");
				return true;
			} else {
				// Send command overview
				sender.sendMessage(prefix + " Plugin help:");
				return true;
			}
			
		}	
		return false;
	}
	
	public int getPing(Player p) { 
		CraftPlayer cp = (CraftPlayer) p; 
		EntityPlayer ep = cp.getHandle(); 
		return ep.ping; 
	}
}