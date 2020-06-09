package io.github.izdabait.pl;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
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
    
    @Override 
    public void onDisable() {
        // TODO Insert logic to be performed when the plugin is disabled
    }
}