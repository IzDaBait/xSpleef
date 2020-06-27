package io.github.izdabait.pl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

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
    
    ArrayList<UUID> accepting = new ArrayList<UUID>();
    HashMap<UUID, Integer> oldCoordsx = new HashMap<UUID, Integer>();
    HashMap<UUID, Integer> oldCoordsy = new HashMap<UUID, Integer>();
    HashMap<UUID, Integer> oldCoordsz = new HashMap<UUID, Integer>();
    HashMap<UUID, World> oldCoordsWorld = new HashMap<UUID, World>();
    HashMap<UUID, Inventory> oldInvs = new HashMap<UUID, Inventory>();
    int game = 0;
    int vae = 0;
    int freeze = 0;
    
    @EventHandler
    public void onbreak(BlockBreakEvent e){
    	FileConfiguration config = this.getConfig();
        Player p = e.getPlayer();
        if (p.getLocation().getX() > (config.getInt("x1"))) {
            if (p.getLocation().getX() < (config.getInt("x2"))) {
                if (p.getLocation().getZ() > (config.getInt("z1"))) {
                    if (p.getLocation().getZ() < (config.getInt("z2"))) {
                        if (p.getLocation().getX() > (config.getInt("y1"))) {
                            if (p.getLocation().getX() < (config.getInt("y2"))) {
                            	if (!accepting.contains(p.getUniqueId())){
                            		if (!p.hasPermission("spleef.bypass")) {
                            			if (game == 1) {
                            				p.sendMessage(ChatColor.GREEN + "xSpleef > " + ChatColor.DARK_RED + "A game is currently in progress!");
                            				e.setCancelled(true);
                            			}
                            		}
                            	}
                            }
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void onplace(BlockPlaceEvent e){
    	FileConfiguration config = this.getConfig();
        Player p = e.getPlayer();
        if (p.getLocation().getX() > (config.getInt("x1"))) {
            if (p.getLocation().getX() < (config.getInt("x2"))) {
                if (p.getLocation().getZ() > (config.getInt("z1"))) {
                    if (p.getLocation().getZ() < (config.getInt("z2"))) {
                        if (p.getLocation().getX() > (config.getInt("y1"))) {
                            if (p.getLocation().getX() < (config.getInt("y2"))) {
                            	if (!accepting.contains(p.getUniqueId())){
                            		if (!p.hasPermission("spleef.bypass")) {
                            			if (game == 1) {
                            				p.sendMessage(ChatColor.GREEN + "xSpleef > " + ChatColor.DARK_RED + "A game is currently in progress!");
                            				e.setCancelled(true);
                            			}
                            		}
                            	}
                            }
                        }
                    }
                }
            }
        }
    }
    
    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		double cversion = 2;
		if (args.length == 1 && args[0].equalsIgnoreCase("go")) {
			Player p = (Player) sender;
			if (accepting.contains(p.getUniqueId())){
				game = 1;
				FileConfiguration config = this.getConfig();
	    		World world = Bukkit.getWorld(config.getString("worldname"));
	    		if (config.getInt("config-version") != cversion) p.sendMessage(ChatColor.RED + "Looks like your config is out of date. Please update it to ensure there are no bugs.");
	    		if (config.getBoolean("repeat") == true) {
	    			for (int r = (config.getInt("p-ylevel")); r >= (config.getInt("p-ylevel")-(config.getInt("repeat-amount")*config.getInt("repeat-spacing"))); r = r - config.getInt("repeat-spacing")) {
	    				for (int x = (config.getInt("p-x1")); x <= (config.getInt("p-x2")); x++) {
	    					for (int y = r; y <= r + config.getInt("p-thickness") - 1; y++) {
	    						for (int z = (config.getInt("p-z1")); z <= (config.getInt("p-z2")); z++) {
	    							world.getBlockAt(x, y, z).setType(Material.SNOW_BLOCK);
	    						}
	    					}
	    				}
	    			}
	    		} else {
	    			for (int x = (config.getInt("p-x1")); x <= (config.getInt("p-x2")); x++) {
	    				for (int y = (config.getInt("p-ylevel")); y <= (config.getInt("p-ylevel") + config.getInt("p-thickness") - 1); y++) {
	    					for (int z = (config.getInt("p-z1")); z <= (config.getInt("p-z2")); z++) {
	    						world.getBlockAt(x, y, z).setType(Material.SNOW_BLOCK);
	    					}
	    				}
	    			}
	    		}
	    		for(Player player : Bukkit.getOnlinePlayers()){
	    			if (accepting.contains(player.getUniqueId())){
						vae = vae + 1;
						if (vae > 8) {
							player.sendMessage(ChatColor.GREEN + "xSpleef > " + ChatColor.DARK_RED + "Sorry, there are too many players this round. You will be in the next game!");
							return true;
						}
						oldCoordsx.put(player.getUniqueId(), player.getLocation().getBlockX());
						oldCoordsy.put(player.getUniqueId(), player.getLocation().getBlockY());
						oldCoordsz.put(player.getUniqueId(), player.getLocation().getBlockZ());
						oldCoordsWorld.put(player.getUniqueId(), player.getLocation().getWorld());
						oldInvs.put(player.getUniqueId(), player.getInventory());
						PlayerInventory inv = player.getInventory();
						inv.clear();
						init(player, vae, config);
						return true;
	    			}
	    		}
			}
			sender.sendMessage(ChatColor.GREEN + "xSpleef > " + ChatColor.DARK_RED + "You are not currently accepting spleef games.");
			return true;
		}
		if (args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("help"))) {
		  sender.sendMessage(ChatColor.GREEN + "This server is running xSpleef v" + this.getDescription().getVersion() + " by IzDaBait");
		  return true;
		} 
		if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
			if (!sender.isOp()) return false; 
			this.reloadConfig();
			sender.sendMessage(ChatColor.GREEN + "Successfully reloaded the config!");
			return true;
		} 
		if (args.length == 1 && args[0].equalsIgnoreCase("join")) {
			Player p= (Player) sender;
			if (accepting.contains(p.getUniqueId())){
				sender.sendMessage(ChatColor.GREEN + "xSpleef > " + ChatColor.DARK_RED + "You are already accepting spleef games. Type '/spleef leave' to leave.");
				return true;
			} else {
				if (accepting.size() == 8) {
					sender.sendMessage(ChatColor.GREEN + "xSpleef > " + ChatColor.DARK_RED + "The Spleef queue is too large! Please wait for a game to finish to join.");
					return true;
				} else {
					accepting.add(p.getUniqueId());
					sender.sendMessage(ChatColor.GREEN + "xSpleef > Joined the waiting list! When enough players are on the list type '/spleef go' to begin!");
					return true;
				}
			}
		}
		if (args.length == 1 && args[0].equalsIgnoreCase("leave")) {
			Player p= (Player) sender;
			if (accepting.contains(p.getUniqueId())){
				accepting.remove(p.getUniqueId());
				sender.sendMessage(ChatColor.GREEN + "xSpleef > Left the waiting list!");
				return true;
	        } else {
	        	sender.sendMessage(ChatColor.GREEN + "xSpleef > " + ChatColor.DARK_RED + "You are not currently accepting spleef games.");
	        	return true;
	        }
		}
		return false;
	}
	  
    @Override 
    public void onDisable() {
        // TODO Insert logic to be performed when the plugin is disabled
    }
    
    
    public void init(Player player, int vae, FileConfiguration config) {
    	// TODO Insert game logic
    	player.sendMessage(ChatColor.GREEN + "xSpleef > A Spleef game starting...");
    	double xcord = config.getInt("spawn" + vae + "x");
    	double ycord = config.getInt("spawnylevel");
    	double zcord = config.getInt("spawn" + vae + "z");
    	String world = config.getString("worldname");
    	Location loc1 = new Location(Bukkit.getWorld(world), xcord, ycord, zcord);
    	player.teleport(loc1);
    	summon(player);
    	freeze(player);
    	
    }
    
    public void summon(Player player) {
    	ItemStack Item = new ItemStack(Material.DIAMOND_SHOVEL);  //new item of item code
    	Item.addEnchantment(Enchantment.DIG_SPEED, 5);  //enchant the item
    	player.getInventory().addItem(Item);
    }
    
    public void freeze(final Player player) {
        final BukkitScheduler scheduler = getServer().getScheduler();
        freeze = 1;
        scheduler.scheduleSyncDelayedTask(this, new Runnable() {
            public void run() {
                player.sendMessage(ChatColor.GREEN + "xSpleef > The game has begun!");
                freeze = 0;
            }
        }, 100L);
    }
    
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
    	Player player = e.getPlayer();
    	FileConfiguration config = this.getConfig();
    	if (accepting.contains(player.getUniqueId())){
            if (freeze == 1) {
            	e.setCancelled(true);
            }
            if (config.getBoolean("repeat") == true) {
            	if (player.getLocation().getBlockY() < (config.getInt("p-ylevel")-(config.getInt("repeat-amount")*config.getInt("repeat-spacing")))) {
            		loss(player, config);
            	}
            } else {
            	if (player.getLocation().getBlockY() < config.getInt("p-ylevel")) {
            		loss(player, config);
            	}
            } 
        }
    }
    
    public void loss(Player player, FileConfiguration config) {
    	if (accepting.size() == 1) {
    		Bukkit.broadcastMessage(ChatColor.GREEN + "xSpleef > " + ChatColor.DARK_RED + player.getName() + ChatColor.GREEN + " " + config.getString("winmessage"));
    	} else {
    		if (config.getBoolean("broadcastloss") == true) {
        		Bukkit.broadcastMessage(ChatColor.GREEN + "xSpleef > " + ChatColor.DARK_RED + player.getName() + ChatColor.GOLD + " " + config.getString("losemessage"));
    		}
    	}
		reset(player, config);
		accepting.remove(player.getUniqueId());
		player.sendMessage(ChatColor.GREEN + "xSpleef > Left the waiting list!");
    }
    
    public void reset(Player player, FileConfiguration config) {
    	if (oldInvs.containsKey(player.getUniqueId())) {
    		player.getInventory().setContents(oldInvs.get(player.getUniqueId()).getContents());
    		Location loc = new Location(oldCoordsWorld.get(player.getUniqueId()), oldCoordsx.get(player.getUniqueId()), oldCoordsy.get(player.getUniqueId()), oldCoordsz.get(player.getUniqueId()));
    		player.teleport(loc);
    	}
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Called when a player leaves a server
        Player p = event.getPlayer();
        if (accepting.contains(p.getUniqueId())){
			accepting.remove(p.getUniqueId());
			getServer().getLogger().info("xSpleef > " + p.getName() + " Removed from spleef accepting list");
        }
    } 
}