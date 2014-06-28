package mn.frd.Ping;

import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;
 
public class Ping extends JavaPlugin {
	public void onEnable(){
		this.getLogger().info("Plugin has been enabled!");
	}
 
	public void onDisable(){
		this.getLogger().info("Plugin has been disabled.");
	}
}