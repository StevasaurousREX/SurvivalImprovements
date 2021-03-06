package nl.arfie.bukkit.survivalimprovements.economy;

import nl.arfie.bukkit.survivalimprovements.Config;
import nl.arfie.bukkit.survivalimprovements.SurvivalImprovements;
import nl.arfie.bukkit.survivalimprovements.util.Util;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class PlayerStats {

	public enum Type {
		ZOMBIE_KILLS, SKELETON_KILLS, SPIDER_KILLS, BLAZE_KILLS, PLAYER_KILLS, MONEY;
	}
	
	private static HashMap<String, PlayerStats> map = new HashMap<String, PlayerStats>();
	
	private HashMap<Type, Double> data;
	public String player;
	
	public static File file = new File(SurvivalImprovements.instance().getDataFolder(),"players.yml");
	
	private PlayerStats(String id){
		player=id;
		data = new HashMap<Type, Double>();
		map.put(id,this);
	}
	
	public void setData(Type t, double val, boolean save){
		data.put(t,val);
		if(save)
			save(file);
	}
	
	public void addData(Type t, double val, boolean save){
		data.put(t,getData(t)+val);
		if(save)
			save(file);
	}
	
	public double getData(Type t){
		if(data.containsKey(t))return data.get(t);
		return 0;
	}
	
	public String toString(){
		return data.toString();
	}
	
	//STATIC
	
	public static void load(File file){
		map.clear();
		Configuration conf = YamlConfiguration.loadConfiguration(file);
		
		for(String s : conf.getValues(false).keySet()){
			PlayerStats ps = new PlayerStats(s);
			ConfigurationSection sect = conf.getConfigurationSection(s);
			for(String s2 : sect.getValues(false).keySet()){
				ps.setData(Type.valueOf(s2),(double)sect.get(s2),false);
			}
		}
	}
	
	public static void save(File file){
		FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
		
		for(String s : map.keySet()){
			PlayerStats ps = map.get(s);
			for(Type t : Type.values()){
				conf.set(s+"."+t.toString(),ps.getData(t));
			}
		}
		try {
			conf.save(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static int getLevelByStat(int stat){
		int l=0;
		for(int i=0; Config.LEVEL_SCALE.containsKey(i); ++i){
			if(stat>=Config.LEVEL_SCALE.get(i))
				l=i;
			else
				break;
		}
		return l;
	}
	
	public static PlayerStats statsFor(OfflinePlayer player){
		if(map.containsKey(Util.getPlayerIdentifier(player)))
			return map.get(Util.getPlayerIdentifier(player));
		return new PlayerStats(Util.getPlayerIdentifier(player));
	}
	
}
