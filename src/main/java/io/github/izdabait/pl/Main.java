package io.github.izdabait.pl;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    @Override
	public void onEnable() {
	    getServer().getLogger().info("xSpleef by IzDaBait successfully loaded.");
	    Bukkit.getServer().getPluginManager().registerEvents(this, (Plugin)this);
	    File dir = new File("plugins/xSpleef");
	    if (!dir.exists()) dir.mkdirs();
	    this.saveDefaultConfig(); 
	    this.getCommand("spleef").setExecutor(this);
	  }
    
    @EventHandler
    public void interact(PlayerInteractEvent e){
        Player p = e.getPlayer();
    }
    
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		double cversion = 1;
		if (!sender.isOp())
			return false; 
		if (args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("help"))) {
		  sender.sendMessage(ChatColor.GREEN + "Your server is running xSpleef v" + this.getDescription().getVersion() + " by IzDaBait");
		  return true;
		} 
		if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
			this.reloadConfig();
			sender.sendMessage(ChatColor.GREEN + "Successfully reloaded the config!");
			return true;
		} 
		if (args.length == 1 && args[0].equalsIgnoreCase("go")) {
			Player p= (Player) sender;
			begin(p.getPlayer(), cversion, this.getConfig());
		}
		return false;
	}
	  
    @Override 
    public void onDisable() {
        // TODO Insert logic to be performed when the plugin is disabled
    }
    
    public void begin(Player p, double cversion, FileConfiguration config) {
    	if (config.getInt("config-version") != cversion) p.sendMessage(ChatColor.RED + "Looks like your config is out of date. Please update it to ensure there are no bugs.");
    	build(config);
    }
    
    public void build(FileConfiguration config) {
    	World world = Bukkit.getWorld(config.getString("worldname"));
    	for (int x = (config.getInt("p-x1")); x <= (config.getInt("p-x2")); x++) {
    		for (int y = (config.getInt("p-ylevel")); y <= (config.getInt("p-ylevel") + config.getInt("p-thickness")); y++) {
    			for (int z = (config.getInt("p-z1")); z <= (config.getInt("p-z2")); z++) {
    				world.getBlockAt(x, y, z).setType(Material.SNOW_BLOCK);
    			}
    		}
    	}
    }
}
