package nl.arfie.bukkit.survivalimprovements;

import nl.arfie.bukkit.survivalimprovements.boss.Boss;
import nl.arfie.bukkit.survivalimprovements.economy.Market;
import nl.arfie.bukkit.survivalimprovements.economy.PlayerStats;
import nl.arfie.bukkit.survivalimprovements.util.Util;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SurvivalImprovements extends JavaPlugin {
	
	private static SurvivalImprovements inst;

	public void onEnable(){
		inst=this;
		if(!Bukkit.getPluginManager().isPluginEnabled("AttributesAPI")){
			getLogger().info("The AttributesAPI must be included to load SurvivalImprovements. The plugin will be disabled.");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		
		Bukkit.getPluginManager().registerEvents(new SIListeners(),this);
		Config.loadFromFile();
		PlayerStats.load(PlayerStats.file);
	}
	
	public void onDisable(){
		GraveStone.breakAll();
		PlayerStats.save(PlayerStats.file);
	}
	
	public static SurvivalImprovements instance(){
		return inst;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		if(command.getName().equalsIgnoreCase("boss") && sender.hasPermission("survivalimprovements.spawnbosses")){
			if(sender instanceof Player){
				try{
					Boss.create(((Player)sender).getLocation(),Integer.parseInt(args[1])-1,Boss.Type.valueOf(args[0].toUpperCase()));
				}catch(IllegalArgumentException ex){
					sender.sendMessage("§c"+ex.getMessage());
				}
				return true;
			}
			return false;
		} else if(command.getName().equalsIgnoreCase("sireload")&&sender.hasPermission("survivalimprovements.reload")){
			Config.loadFromFile();
			PlayerStats.load(PlayerStats.file);
			sender.sendMessage(Config.MESSAGE_RELOAD);
		} else if(command.getName().equalsIgnoreCase("sistats")&&sender.hasPermission("survivalimprovements.stats")){
			if(args.length>0)
				sender.sendMessage(PlayerStats.statsFor(Util.getPlayerByIdentifier(args[0])).toString());
			else if(sender instanceof Player)
				sender.sendMessage(PlayerStats.statsFor((Player)sender).toString());
			else
				sender.sendMessage("§cYou need to be a player to do that.");
		} else if(command.getName().equalsIgnoreCase("market") && sender.isOp()){
			Market.showCategories((Player)sender);
		}
		return false;
	}
	
}
