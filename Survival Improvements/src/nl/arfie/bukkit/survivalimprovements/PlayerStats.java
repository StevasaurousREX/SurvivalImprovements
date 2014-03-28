package nl.arfie.bukkit.survivalimprovements;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class PlayerStats {

	public enum Type {
		ZOMBIE_KILLS, SKELETON_KILLS, SPIDER_KILLS, BLAZE_KILLS, PLAYER_KILLS;
	}
	
	private static HashMap<String, PlayerStats> map = new HashMap<String, PlayerStats>();
	
	private HashMap<Type, Integer> data;
	private String player;
	
	public static File file = new File(SurvivalImprovements.instance().getDataFolder(),"players.yml");
	
	private PlayerStats(String name){
		player=name;
		data = new HashMap<Type, Integer>();
		map.put(name,this);
	}
	
	public void setData(Type t, int val, boolean save){
		data.put(t,val);
		if(save)
			save(file);
	}
	
	public void addData(Type t, int val, boolean save){
		data.put(t,getData(t)+val);
		if(save)
			save(file);
	}
	
	public int getData(Type t){
		if(data.containsKey(t))return data.get(t);
		return 0;
	}
	
	public String toString(){
		return data.toString();
	}
	
	//STATIC//
	
	public static void load(File file){
		map.clear();
		Configuration conf = YamlConfiguration.loadConfiguration(file);
		
		for(String s : conf.getValues(false).keySet()){
			PlayerStats ps = new PlayerStats(s);
			ConfigurationSection sect = conf.getConfigurationSection(s);
			for(String s2 : sect.getValues(false).keySet()){
				ps.setData(Type.valueOf(s2),(int)sect.getValues(false).get(s2),false);
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
//		int l=0,s=10,x=10;
//		while(stat>=x){
//			s+=10;
//			x+=s;
//			++l;
//		}
//		return l;
		int l=0;
		for(int i=0; Config.LEVEL_SCALE.containsKey(i); ++i){
			if(stat>=Config.LEVEL_SCALE.get(i))
				l=i;
			else
				break;
		}
		return l;
	}
	
	public static PlayerStats statsFor(String player){
		if(map.containsKey(player))
			return map.get(player);
		return new PlayerStats(player);
	}
	
}
