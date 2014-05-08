package nl.arfie.bukkit.survivalimprovements;

import nl.arfie.bukkit.attributes.Attribute;
import nl.arfie.bukkit.attributes.AttributeType;
import nl.arfie.bukkit.attributes.Operation;
import nl.arfie.bukkit.survivalimprovements.attribute.ItemAttribute;
import nl.arfie.bukkit.survivalimprovements.attribute.ItemAttributeList;
import nl.arfie.bukkit.survivalimprovements.boss.Boss;
import nl.arfie.bukkit.survivalimprovements.boss.Equipment;
import nl.arfie.bukkit.survivalimprovements.util.Util;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {

	public static File configFile = new File(SurvivalImprovements.instance().getDataFolder(),"config.yml");
	public static File msgFile = new File(SurvivalImprovements.instance().getDataFolder(),"messages.yml");
	
	//DEFAULT SETTINGS

	public static boolean WORLD_SPECIFIC=false;
	public static List<String> WORLDS = Arrays.asList(new String[]{"world","world_nether","world_the_end"});
	
	public static boolean GRAVESTONES=true;
	public static int GRAVESTONE_SECONDS=300;
	public static String GRAVESTONE_PLAYER_FORMAT="&1";
	
	public static boolean MOB_MONEY=true;
	public static boolean MOB_MONEY_SOUND=true;
	public static HashMap<EntityType, Integer> MOB_MONEY_AMOUNTS = new HashMap<EntityType, Integer>();
	static {
		MOB_MONEY_AMOUNTS.put(EntityType.COW,1);
		MOB_MONEY_AMOUNTS.put(EntityType.CHICKEN,1);
		MOB_MONEY_AMOUNTS.put(EntityType.PIG,1);
		MOB_MONEY_AMOUNTS.put(EntityType.SHEEP,1);
		MOB_MONEY_AMOUNTS.put(EntityType.SQUID,1);
		MOB_MONEY_AMOUNTS.put(EntityType.HORSE,1);
		
		MOB_MONEY_AMOUNTS.put(EntityType.SPIDER,20);
		
		MOB_MONEY_AMOUNTS.put(EntityType.ZOMBIE,25);
		MOB_MONEY_AMOUNTS.put(EntityType.SKELETON,25);
		MOB_MONEY_AMOUNTS.put(EntityType.CREEPER,25);
		MOB_MONEY_AMOUNTS.put(EntityType.SLIME,25);
		MOB_MONEY_AMOUNTS.put(EntityType.CAVE_SPIDER,25);

		MOB_MONEY_AMOUNTS.put(EntityType.ENDERMAN,30);
		MOB_MONEY_AMOUNTS.put(EntityType.PIG_ZOMBIE,30);
		MOB_MONEY_AMOUNTS.put(EntityType.BLAZE,30);
		MOB_MONEY_AMOUNTS.put(EntityType.MAGMA_CUBE,30);
		
		MOB_MONEY_AMOUNTS.put(EntityType.GHAST,35);
	}
	public static boolean MOB_MONEY_NOT_IN_END=true;
	
	public static boolean LOSE_MONEY_ON_DEATH=true;
	public static double MONEY_LOSS_ON_DEATH=0.05;
	public static boolean SHOW_MONEY_LOSS_IN_DEATH_MESSAGE=true;
	public static String MONEY_LOSS_MESSAGE="$MESSAGE and lost &c$LOSS&f gold!";
	public static boolean PLAYER_KILL_MONEY=true;
	public static double PLAYER_KILL_MONEY_AMOUNT=0.5;
	
	public static boolean BOSSES_SPAWNING=true;
	public static boolean BOSSES_FROM_SPAWNERS=true;
	public static HashMap<Boss.Type, Boolean> BOSS_TYPES_SPAWNING = new HashMap<Boss.Type, Boolean>();
	static {
		for(Boss.Type t : Boss.Type.values())
			BOSS_TYPES_SPAWNING.put(t,true);
	}
	public static boolean ENABLE_BOSS_LEVELS=true;
	public static HashMap<Integer, Integer> LEVEL_SCALE = new HashMap<Integer, Integer>();
	static {
		LEVEL_SCALE.put(0,0);
		LEVEL_SCALE.put(1,10);
		LEVEL_SCALE.put(2,25);
		LEVEL_SCALE.put(3,50);
		LEVEL_SCALE.put(4,90);
		LEVEL_SCALE.put(5,150);
		LEVEL_SCALE.put(6,225);
		LEVEL_SCALE.put(7,325);
		LEVEL_SCALE.put(8,450);
		LEVEL_SCALE.put(9,600);
	}
	public static HashMap<Boss.Type, String> BOSS_NAME_TAGS = new HashMap<Boss.Type, String>();
	static {
		for(Boss.Type t : Boss.Type.values())
			BOSS_NAME_TAGS.put(t,t.tag);
	}
	public static HashMap<Boss.Type, Equipment> BOSS_GEAR = new HashMap<Boss.Type, Equipment>();
	static {
		Equipment zombie = new Equipment();
		ItemAttributeList azombiesword = new ItemAttributeList();
		azombiesword.add(0,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.DAMAGE_ALL,null,1));
		azombiesword.add(1,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.DAMAGE_ALL,null,2));
		azombiesword.add(2,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.DAMAGE_ALL,null,3));
		azombiesword.add(3,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.DAMAGE_ALL,null,3));
		azombiesword.add(4,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.DAMAGE_ALL,null,4));
		azombiesword.add(5,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.DAMAGE_ALL,null,4));
		azombiesword.add(6,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.DAMAGE_ALL,null,5));
		azombiesword.add(7,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.DAMAGE_ALL,null,5));
		azombiesword.add(8,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.DAMAGE_ALL,null,6));
		azombiesword.add(9,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.DAMAGE_ALL,null,7));

		azombiesword.add(3,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.FIRE_ASPECT,null,1));
		azombiesword.add(4,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.FIRE_ASPECT,null,1));
		azombiesword.add(5,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.FIRE_ASPECT,null,1));
		azombiesword.add(6,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.FIRE_ASPECT,null,2));
		azombiesword.add(7,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.FIRE_ASPECT,null,2));
		azombiesword.add(8,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.FIRE_ASPECT,null,2));
		azombiesword.add(9,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.FIRE_ASPECT,null,3));

		azombiesword.add(0,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.KNOCKBACK,null,1));
		azombiesword.add(1,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.KNOCKBACK,null,1));
		azombiesword.add(2,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.KNOCKBACK,null,1));
		azombiesword.add(3,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.KNOCKBACK,null,2));
		azombiesword.add(4,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.KNOCKBACK,null,2));
		azombiesword.add(5,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.KNOCKBACK,null,2));
		azombiesword.add(6,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.KNOCKBACK,null,2));
		azombiesword.add(7,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.KNOCKBACK,null,3));
		azombiesword.add(8,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.KNOCKBACK,null,3));
		azombiesword.add(9,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.KNOCKBACK,null,3));

		azombiesword.add(0,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.LOOT_BONUS_MOBS,null,1));
		azombiesword.add(1,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.LOOT_BONUS_MOBS,null,2));
		azombiesword.add(2,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.LOOT_BONUS_MOBS,null,3));
		azombiesword.add(3,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.LOOT_BONUS_MOBS,null,3));
		azombiesword.add(4,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.LOOT_BONUS_MOBS,null,4));
		azombiesword.add(5,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.LOOT_BONUS_MOBS,null,4));
		azombiesword.add(6,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.LOOT_BONUS_MOBS,null,5));
		azombiesword.add(7,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.LOOT_BONUS_MOBS,null,5));
		azombiesword.add(8,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.LOOT_BONUS_MOBS,null,6));
		azombiesword.add(9,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.LOOT_BONUS_MOBS,null,7));
		zombie.setSword(true, Equipment.SwordType.IRON, "§5Level $LEVEL Zombie Sword", "Obtained by killing a Zombie Boss",azombiesword);
		
		ItemAttributeList azombiearmour = new ItemAttributeList();
		for(int i=0; i<5; i++){
			azombiearmour.add(i,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.PROTECTION_ENVIRONMENTAL,null,i*2));
			azombiearmour.add(i+1,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.PROTECTION_ENVIRONMENTAL,null,i*2+1));
		}
		zombie.setArmour(true, Equipment.ArmourType.TUNIC, Color.GREEN, "§5Level $LEVEL Zombie Armour", "Obtained by killing a Zombie Boss",azombiearmour);
		BOSS_GEAR.put(Boss.Type.ZOMBIE,zombie);
		
		Equipment skeleton = new Equipment();
		ItemAttributeList askeletonbow = new ItemAttributeList();
		askeletonbow.add(0,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.ARROW_DAMAGE,null,1));
		askeletonbow.add(1,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.ARROW_DAMAGE,null,1));
		askeletonbow.add(2,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.ARROW_DAMAGE,null,2));
		askeletonbow.add(3,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.ARROW_DAMAGE,null,2));
		askeletonbow.add(4,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.ARROW_DAMAGE,null,3));
		askeletonbow.add(5,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.ARROW_DAMAGE,null,3));
		askeletonbow.add(6,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.ARROW_DAMAGE,null,4));
		askeletonbow.add(7,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.ARROW_DAMAGE,null,4));
		askeletonbow.add(8,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.ARROW_DAMAGE,null,5));
		askeletonbow.add(9,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.ARROW_DAMAGE,null,6));
		
		askeletonbow.add(0,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.ARROW_KNOCKBACK,null,1));
		askeletonbow.add(1,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.ARROW_KNOCKBACK,null,1));
		askeletonbow.add(2,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.ARROW_KNOCKBACK,null,1));
		askeletonbow.add(3,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.ARROW_KNOCKBACK,null,2));
		askeletonbow.add(4,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.ARROW_KNOCKBACK,null,2));
		askeletonbow.add(5,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.ARROW_KNOCKBACK,null,2));
		askeletonbow.add(6,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.ARROW_KNOCKBACK,null,2));
		askeletonbow.add(7,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.ARROW_KNOCKBACK,null,3));
		askeletonbow.add(8,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.ARROW_KNOCKBACK,null,3));
		askeletonbow.add(9,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.ARROW_KNOCKBACK,null,3));
		
		for(int i=3; i<10; ++i){
			askeletonbow.add(i,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.ARROW_FIRE,null,1));
			askeletonbow.add(i,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.ARROW_INFINITE,null,1));
		}
		skeleton.setBow(true,"§5Level $LEVEL Skeleton Bow","Obtained by killing a Skeleton Boss",askeletonbow);
		
		ItemAttributeList askeletonarmour = new ItemAttributeList();
		for(int i=0; i<10; ++i)
			askeletonarmour.add(i,new ItemAttribute(ItemAttribute.Type.ATTRIBUTE,null,new Attribute(AttributeType.KNOCKBACK_RESISTANCE,Operation.ADD_NUMBER,(i+1)/10.0),0));
		skeleton.setArmour(true,Equipment.ArmourType.CAP,Color.WHITE,"§5Level $LEVEL Skeleton Helmet","Obtained by killing a Skeleton Boss",askeletonarmour);
		BOSS_GEAR.put(Boss.Type.SKELETON,skeleton);
		
		Equipment spider = new Equipment();
		ItemAttributeList aspidersword = new ItemAttributeList();
		for(int i=0; i<10; ++i)
			aspidersword.add(i,new ItemAttribute(ItemAttribute.Type.VENOM_ASPECT,null,null,i>=6?2:1));
		spider.setSword(true,Equipment.SwordType.WOOD,"§5Level $LEVEL Spider Sword","Obtained by killing a Spider Boss",aspidersword);
		
		ItemAttributeList aspiderarmour = new ItemAttributeList();
		aspiderarmour.add(0,new ItemAttribute(ItemAttribute.Type.ATTRIBUTE,null,new Attribute(AttributeType.MOVEMENT_SPEED,Operation.ADD_PERCENTAGE,0.1),0));
		aspiderarmour.add(1,new ItemAttribute(ItemAttribute.Type.ATTRIBUTE,null,new Attribute(AttributeType.MOVEMENT_SPEED,Operation.ADD_PERCENTAGE,0.15),0));
		aspiderarmour.add(2,new ItemAttribute(ItemAttribute.Type.ATTRIBUTE,null,new Attribute(AttributeType.MOVEMENT_SPEED,Operation.ADD_PERCENTAGE,0.25),0));
		aspiderarmour.add(3,new ItemAttribute(ItemAttribute.Type.ATTRIBUTE,null,new Attribute(AttributeType.MOVEMENT_SPEED,Operation.ADD_PERCENTAGE,0.3),0));
		aspiderarmour.add(4,new ItemAttribute(ItemAttribute.Type.ATTRIBUTE,null,new Attribute(AttributeType.MOVEMENT_SPEED,Operation.ADD_PERCENTAGE,0.35),0));
		aspiderarmour.add(5,new ItemAttribute(ItemAttribute.Type.ATTRIBUTE,null,new Attribute(AttributeType.MOVEMENT_SPEED,Operation.ADD_PERCENTAGE,0.45),0));
		aspiderarmour.add(6,new ItemAttribute(ItemAttribute.Type.ATTRIBUTE,null,new Attribute(AttributeType.MOVEMENT_SPEED,Operation.ADD_PERCENTAGE,0.5),0));
		aspiderarmour.add(7,new ItemAttribute(ItemAttribute.Type.ATTRIBUTE,null,new Attribute(AttributeType.MOVEMENT_SPEED,Operation.ADD_PERCENTAGE,0.55),0));
		aspiderarmour.add(8,new ItemAttribute(ItemAttribute.Type.ATTRIBUTE,null,new Attribute(AttributeType.MOVEMENT_SPEED,Operation.ADD_PERCENTAGE,0.65),0));
		aspiderarmour.add(9,new ItemAttribute(ItemAttribute.Type.ATTRIBUTE,null,new Attribute(AttributeType.MOVEMENT_SPEED,Operation.ADD_PERCENTAGE,0.75),0));
		spider.setArmour(true,Equipment.ArmourType.PANTS,Color.TEAL,"§5Level $LEVEL Spider Legs","Obtained by killing a Spider Boss",aspiderarmour);
		BOSS_GEAR.put(Boss.Type.SPIDER,spider);
		
		Equipment blaze = new Equipment();
		ItemAttributeList ablazesword = new ItemAttributeList();
		ablazesword.add(0,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.FIRE_ASPECT,null,1));
		ablazesword.add(1,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.FIRE_ASPECT,null,1));
		ablazesword.add(2,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.FIRE_ASPECT,null,1));
		ablazesword.add(3,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.FIRE_ASPECT,null,2));
		ablazesword.add(4,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.FIRE_ASPECT,null,2));
		ablazesword.add(5,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.FIRE_ASPECT,null,2));
		ablazesword.add(6,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.FIRE_ASPECT,null,3));
		ablazesword.add(7,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.FIRE_ASPECT,null,3));
		ablazesword.add(8,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.FIRE_ASPECT,null,3));
		ablazesword.add(9,new ItemAttribute(ItemAttribute.Type.ENCHANTMENT,Enchantment.FIRE_ASPECT,null,4));
		blaze.setSword(true,Equipment.SwordType.WOOD,"§5Level $LEVEL Blazing Sword","Obtained by killing a Blaze Boss",ablazesword);
		
		ItemAttributeList ablazearmour = new ItemAttributeList();
		for(int i=0; i<10; ++i)
			ablazearmour.add(i,new ItemAttribute(ItemAttribute.Type.ATTRIBUTE,null,new Attribute(AttributeType.MAX_HEALTH,Operation.ADD_NUMBER,(i+1)*2),0));
		blaze.setArmour(true,Equipment.ArmourType.BOOTS,Color.ORANGE,"§5Level $LEVEL Blazing Boots","Obtained by killing a Blaze Boss",ablazearmour);
		BOSS_GEAR.put(Boss.Type.BLAZE,blaze);
	}
	
	public static boolean BOSS_MONEY=true;
	public static HashMap<Integer, Integer> BOSS_MONEY_AMOUNT = new HashMap<Integer, Integer>();
	static {
		BOSS_MONEY_AMOUNT.put(0,50);
		BOSS_MONEY_AMOUNT.put(1,100);
		BOSS_MONEY_AMOUNT.put(2,250);
		BOSS_MONEY_AMOUNT.put(3,500);
		BOSS_MONEY_AMOUNT.put(4,1000);
		BOSS_MONEY_AMOUNT.put(5,2500);
		BOSS_MONEY_AMOUNT.put(6,5000);
		BOSS_MONEY_AMOUNT.put(7,10000);
		BOSS_MONEY_AMOUNT.put(8,12500);
		BOSS_MONEY_AMOUNT.put(9,15000);
	}
	
	public static boolean ANNOUNCE_BOSS_ENCOUNTER=true;
	public static boolean BROADCAST_BOSS_KILL=true;
	
	public static String MESSAGE_GRAVESTONE_SPAWN="&8A gravestone was created where you died. If you break that, you will safely retrieve your items.";
	public static String MESSAGE_GRAVESTONE_TIMER="&8The gravestone will automatically be destroyed in &c5&8 minutes.";
	public static String MESSAGE_GRAVESTONE_TOO_LATE="&cYour gravestone has automatically been broken.";
	
	public static String MESSAGE_BOSS_ENCOUNTER="You have encountered a $BOSS!";
	public static String MESSAGE_BOSS_DEFEAT="$PLAYER has killed a $BOSS and received $MONEY gold!";
	
	public static String MESSAGE_RELOAD="Reloaded.";
	
	//END OF DEFAULTS
	
	private static Configuration config;
	private static Configuration msgs;
	
	//Loading from file
	
	public static void loadFromFile(){
		if(!configFile.exists()){
			SurvivalImprovements.instance().getLogger().info("Saving default config...");
			SurvivalImprovements.instance().saveDefaultConfig();
			SurvivalImprovements.instance().saveResource("messages.yml",true);
			return;
		}
		
		config = YamlConfiguration.loadConfiguration(configFile);
		
		WORLD_SPECIFIC = getValue("world-specific",WORLD_SPECIFIC);
		WORLDS = getValue("worlds",WORLDS);
		
		GRAVESTONES = getValue("enable-gravestone",GRAVESTONES);
		GRAVESTONE_SECONDS = getValue("gravestone-time",GRAVESTONE_SECONDS);
		GRAVESTONE_PLAYER_FORMAT = ChatColor.translateAlternateColorCodes('&',getValue("gravestone-playerformat",GRAVESTONE_PLAYER_FORMAT));
		
		MOB_MONEY = getValue("mobs-money",MOB_MONEY);
		MOB_MONEY_SOUND = getValue("mobs-money-sound",MOB_MONEY_SOUND);
		
		if(config.contains("mobs-money-amount")){
			MOB_MONEY_AMOUNTS.clear();
			Map<String, Object> mobMoney = config.getConfigurationSection("mobs-money-amount").getValues(false);
			for(String s : mobMoney.keySet()){
				EntityType type = EntityType.valueOf(s.toUpperCase());
				if(type==null){
					SurvivalImprovements.instance().getLogger().info("[Warning] Invalid entity type \""+s+"\". Skipping...");
				}else{
					try{
						MOB_MONEY_AMOUNTS.put(type,(int)mobMoney.get(s));
					}catch(ClassCastException e){
						SurvivalImprovements.instance().getLogger().info("[Warning] Could not cast \""+mobMoney.get(s).toString()+"\" to integer at mobs-money-amount."+s+". Skipping...");
					}
				}
			}
		} else {
			SurvivalImprovements.instance().getLogger().info("[Warning] No values given for mobs-money-amount. Using default values...");
		}
		
		MOB_MONEY_NOT_IN_END = getValue("mobs-money-not-in-end",MOB_MONEY_NOT_IN_END);
		
		LOSE_MONEY_ON_DEATH = getValue("lose-money-on-death",LOSE_MONEY_ON_DEATH);
		MONEY_LOSS_ON_DEATH = getValue("money-loss-on-death",MONEY_LOSS_ON_DEATH);
		SHOW_MONEY_LOSS_IN_DEATH_MESSAGE = getValue("show-money-loss-in-death-messages",SHOW_MONEY_LOSS_IN_DEATH_MESSAGE);
		MONEY_LOSS_MESSAGE = ChatColor.translateAlternateColorCodes('&',getValue("money-loss-chat-format",MONEY_LOSS_MESSAGE));
		PLAYER_KILL_MONEY = getValue("money-from-killing",PLAYER_KILL_MONEY);
		PLAYER_KILL_MONEY_AMOUNT = getValue("money-from-killing-amount",PLAYER_KILL_MONEY_AMOUNT);
		
		BOSSES_SPAWNING = getValue("bosses-spawn",BOSSES_SPAWNING);
		BOSSES_FROM_SPAWNERS = getValue("bosses-from-spawners",BOSSES_FROM_SPAWNERS);
		
		if(config.contains("boss-types")){
			BOSS_TYPES_SPAWNING.clear();
			Map<String, Object> bossTypes = config.getConfigurationSection("boss-types").getValues(false);
			for(String s : bossTypes.keySet()){
				Boss.Type type = Boss.Type.valueOf(s.toUpperCase());
				if(type!=null){
					try{
						BOSS_TYPES_SPAWNING.put(type,(boolean)bossTypes.get(s));
					}catch(ClassCastException e){
						SurvivalImprovements.instance().getLogger().info("[Warning] Could not cast \""+bossTypes.get(s).toString()+"\" to boolean at boss-types."+s+". Skipping...");
					}
				} else SurvivalImprovements.instance().getLogger().info("[Warning] Invalid boss type "+s+". Skipping...");
			}
		} else {
			SurvivalImprovements.instance().getLogger().info("[Warning] No values given for boss-types. Using default values...");
		}
		
		ENABLE_BOSS_LEVELS = getValue("enable-boss-levels",ENABLE_BOSS_LEVELS);
		
		if(config.contains("boss-level-scale")){
			LEVEL_SCALE.clear();
			Map<String, Object> bossTypes = config.getConfigurationSection("boss-level-scale").getValues(false);
			for(String s : bossTypes.keySet()){
				try{
					LEVEL_SCALE.put(Integer.parseInt(s)-1,(int)bossTypes.get(s));
				}catch(NumberFormatException ex){
					SurvivalImprovements.instance().getLogger().info("[Warning] Could not cast \""+s+"\" to integer at boss-level-scale."+s+". Skipping...");
				}catch(ClassCastException e){
					SurvivalImprovements.instance().getLogger().info("[Warning] Could not cast \""+bossTypes.get(s).toString()+"\" to boolean at boss-level-scale."+s+". Skipping...");
				}
			}
		} else {
			SurvivalImprovements.instance().getLogger().info("[Warning] No values given for boss-level-scale. Using default values...");
		}
		
		if(config.contains("boss-name-tags")){
			BOSS_NAME_TAGS.clear();
			Map<String, Object> bossTypes = config.getConfigurationSection("boss-name-tags").getValues(false);
			for(String s : bossTypes.keySet()){
				Boss.Type type = Boss.Type.valueOf(s.toUpperCase());
				if(type!=null){
					try{
						BOSS_NAME_TAGS.put(type,ChatColor.translateAlternateColorCodes('&',(String)bossTypes.get(s)));
					}catch(ClassCastException e){
						SurvivalImprovements.instance().getLogger().info("[Warning] Could not cast \""+bossTypes.get(s).toString()+"\" to String at boss-name-tags."+s+". Skipping...");
					}
				} else SurvivalImprovements.instance().getLogger().info("[Warning] Invalid boss type "+s+". Skipping...");
			}
		} else {
			SurvivalImprovements.instance().getLogger().info("[Warning] No values given for boss-name-tags. Using default values...");
		}
		
		if(config.contains("boss-gear")){
			ConfigurationSection gear = config.getConfigurationSection("boss-gear");
			if(gear.contains("zombie")){
				ConfigurationSection zombie = gear.getConfigurationSection("zombie");
				if(zombie.contains("sword")){
					ConfigurationSection sword = zombie.getConfigurationSection("sword");
					Equipment.SwordType type = Equipment.SwordType.valueOf(getValue("material","iron",sword).toUpperCase());
					if(type==null){
						SurvivalImprovements.instance().getLogger().info("[Warning] Invalid material "+getValue("material","iron",sword)+". Using iron...");
						type=Equipment.SwordType.IRON;
					}
					BOSS_GEAR.get(Boss.Type.ZOMBIE).setSword(getValue("enabled",true,sword),type,getValue("name","§5Level $LEVEL Zombie Sword",sword),getValue("lore","Obtained by killing a Zombie Boss",sword),getAttributeList(sword));
				} else {
					SurvivalImprovements.instance().getLogger().info("[Warning] No values given for boss-gear.zombie.sword. Using default values...");
				}
				if(zombie.contains("armor")){
					ConfigurationSection armor = zombie.getConfigurationSection("armor");
					BOSS_GEAR.get(Boss.Type.ZOMBIE).setArmour(getValue("enabled",true,armor),Equipment.ArmourType.TUNIC,Color.GREEN,getValue("name","§5Level $LEVEL Zombie Armour",armor),getValue("lore","Obtained by killing a Zombie Boss",armor),getAttributeList(armor));
				} else {
					SurvivalImprovements.instance().getLogger().info("[Warning] No values given for boss-gear.zombie.armor. Using default values...");
				}
			} else {
				SurvivalImprovements.instance().getLogger().info("[Warning] No values given for boss-gear.zombie. Using default values...");
			}

			if(gear.contains("skeleton")){
				ConfigurationSection skeleton = gear.getConfigurationSection("skeleton");
				if(skeleton.contains("bow")){
					ConfigurationSection bow = skeleton.getConfigurationSection("bow");
					BOSS_GEAR.get(Boss.Type.SKELETON).setBow(getValue("enabled",true,bow),getValue("name","§5Level $LEVEL Skeleton Bow",bow),getValue("lore","Obtained by killing a Skeleton Boss",bow),getAttributeList(bow));
				} else {
					SurvivalImprovements.instance().getLogger().info("[Warning] No values given for boss-gear.skeleton.bow. Using default values...");
				}
				if(skeleton.contains("armor")){
					ConfigurationSection armor = skeleton.getConfigurationSection("armor");
					BOSS_GEAR.get(Boss.Type.SKELETON).setArmour(getValue("enabled",true,armor),Equipment.ArmourType.CAP,Color.WHITE,getValue("name","§5Level $LEVEL Skeleton Helmet",armor),getValue("lore","Obtained by killing a Skeleton Boss",armor),getAttributeList(armor));
				} else {
					SurvivalImprovements.instance().getLogger().info("[Warning] No values given for boss-gear.skeleton.armor. Using default values...");
				}
			} else {
				SurvivalImprovements.instance().getLogger().info("[Warning] No values given for boss-gear.skeleton. Using default values...");
			}

			if(gear.contains("spider")){
				ConfigurationSection spider = gear.getConfigurationSection("spider");
				if(spider.contains("sword")){
					ConfigurationSection sword = spider.getConfigurationSection("sword");
					Equipment.SwordType type = Equipment.SwordType.valueOf(getValue("material","wood",sword).toUpperCase());
					if(type==null){
						SurvivalImprovements.instance().getLogger().info("[Warning] Invalid material "+getValue("material","wood",sword)+". Using wood...");
						type=Equipment.SwordType.IRON;
					}
					BOSS_GEAR.get(Boss.Type.SPIDER).setSword(getValue("enabled",true,sword),type,getValue("name","§5Level $LEVEL Spider Sword",sword),getValue("lore","Obtained by killing a Spider Boss",sword),getAttributeList(sword));
				} else {
					SurvivalImprovements.instance().getLogger().info("[Warning] No values given for boss-gear.spider.sword. Using default values...");
				}
				if(spider.contains("armor")){
					ConfigurationSection armor = spider.getConfigurationSection("armor");
					BOSS_GEAR.get(Boss.Type.SPIDER).setArmour(getValue("enabled",true,armor),Equipment.ArmourType.PANTS,Color.TEAL,getValue("name","§5Level $LEVEL Spider Legs",armor),getValue("lore","Obtained by killing a Spider Boss",armor),getAttributeList(armor));
				} else {
					SurvivalImprovements.instance().getLogger().info("[Warning] No values given for boss-gear.spider.armor. Using default values...");
				}
			} else {
				SurvivalImprovements.instance().getLogger().info("[Warning] No values given for boss-gear.spider. Using default values...");
			}

			if(gear.contains("blaze")){
				ConfigurationSection blaze = gear.getConfigurationSection("blaze");
				if(blaze.contains("sword")){
					ConfigurationSection sword = blaze.getConfigurationSection("sword");
					Equipment.SwordType type = Equipment.SwordType.valueOf(getValue("material","wood",sword).toUpperCase());
					if(type==null){
						SurvivalImprovements.instance().getLogger().info("[Warning] Invalid material "+getValue("material","wood",sword)+". Using wood...");
						type=Equipment.SwordType.IRON;
					}
					BOSS_GEAR.get(Boss.Type.BLAZE).setSword(getValue("enabled",true,sword),type,getValue("name","§5Level $LEVEL Blazing Sword",sword),getValue("lore","Obtained by killing a Blaze Boss",sword),getAttributeList(sword));
				} else {
					SurvivalImprovements.instance().getLogger().info("[Warning] No values given for boss-gear.blaze.sword. Using default values...");
				}
				if(blaze.contains("armor")){
					ConfigurationSection armor = blaze.getConfigurationSection("armor");
					BOSS_GEAR.get(Boss.Type.BLAZE).setArmour(getValue("enabled",true,armor),Equipment.ArmourType.BOOTS,Color.ORANGE,getValue("name","§5Level $LEVEL Blazing Boots",armor),getValue("lore","Obtained by killing a Blaze Boss",armor),getAttributeList(armor));
				} else {
					SurvivalImprovements.instance().getLogger().info("[Warning] No values given for boss-gear.blaze.armor. Using default values...");
				}
			} else {
				SurvivalImprovements.instance().getLogger().info("[Warning] No values given for boss-gear.blaze. Using default values...");
			}
		} else {
			SurvivalImprovements.instance().getLogger().info("[Warning] No values given for boss-gear. Using default values...");
		}
		
		BOSS_MONEY = getValue("boss-money",true);
		
		if(config.contains("boss-money-amount")){
			BOSS_MONEY_AMOUNT.clear();
			Map<String, Object> bossMoney = config.getConfigurationSection("boss-money-amount").getValues(false);
			for(String s : bossMoney.keySet()){
				try{
					BOSS_MONEY_AMOUNT.put(Integer.parseInt(s)-1,(int)bossMoney.get(s));
				}catch(NumberFormatException ex){
					SurvivalImprovements.instance().getLogger().info("[Warning] Could not cast \""+s+"\" to integer at boss-money-amount."+s+". Skipping...");
				}catch(ClassCastException e){
					SurvivalImprovements.instance().getLogger().info("[Warning] Could not cast \""+bossMoney.get(s).toString()+"\" to boolean at boss-money-amount."+s+". Skipping...");
				}
			}
		} else {
			SurvivalImprovements.instance().getLogger().info("[Warning] No values given for boss-money-amount. Using default values...");
		}
		
		ANNOUNCE_BOSS_ENCOUNTER = getValue("announce-encountering",true);
		BROADCAST_BOSS_KILL = getValue("broadcast-boss-kill",true);
		
		msgs = YamlConfiguration.loadConfiguration(msgFile);
		
		MESSAGE_GRAVESTONE_SPAWN=ChatColor.translateAlternateColorCodes('&',getValue("gravestone-spawned",MESSAGE_GRAVESTONE_SPAWN,msgs));
		MESSAGE_GRAVESTONE_TIMER=ChatColor.translateAlternateColorCodes('&',getValue("gravestone-timer",MESSAGE_GRAVESTONE_TIMER,msgs));
		MESSAGE_GRAVESTONE_TOO_LATE=ChatColor.translateAlternateColorCodes('&',getValue("gravestone-toolate",MESSAGE_GRAVESTONE_TOO_LATE,msgs));
		
		MESSAGE_BOSS_ENCOUNTER=ChatColor.translateAlternateColorCodes('&',getValue("boss-encounter",MESSAGE_BOSS_ENCOUNTER,msgs));
		MESSAGE_BOSS_DEFEAT=ChatColor.translateAlternateColorCodes('&',getValue("boss-defeat",MESSAGE_BOSS_DEFEAT,msgs));
		
		MESSAGE_RELOAD=ChatColor.translateAlternateColorCodes('&',getValue("reload",MESSAGE_RELOAD,msgs));
		
	}
	
	private static ItemAttributeList getAttributeList(ConfigurationSection section) {
		ItemAttributeList list = new ItemAttributeList();
		if(section.contains("enchants")){
			ConfigurationSection enchants = section.getConfigurationSection("enchants");
			Map<String, Object> map = enchants.getValues(false);
			for(String s : map.keySet()){
				Enchantment ench = Enchantment.getByName(s.toUpperCase());
				if(ench!=null || s.equalsIgnoreCase("venom_aspect")){
					ConfigurationSection esect = enchants.getConfigurationSection(s);
					if(ench!=null)
						addAttributes(esect,list,ItemAttribute.Type.ENCHANTMENT,ench,null,null);
					else
						addAttributes(esect,list,ItemAttribute.Type.VENOM_ASPECT,null,null,null);
				} else {
					SurvivalImprovements.instance().getLogger().info("[Warning] Unknown enchantment "+s+". Skipping...");
				}
			}
		}
		for(String s : section.getValues(false).keySet()){
			AttributeType t = AttributeType.valueOf(s);
			if(t!=null){
				addAttributes(section.getConfigurationSection(s),list,ItemAttribute.Type.ATTRIBUTE,null,t,Util.attributeDefaultOperation(t));
			}
		}
		return list;
	}
	
	private static void addAttributes(ConfigurationSection section, ItemAttributeList list, ItemAttribute.Type type, Enchantment enchant, AttributeType attrType, Operation attrOp){
		Map<String, Object> m = section.getValues(false);
		for(String l : m.keySet()){
			try {
				int level = Integer.parseInt(l)-1;
				double value = section.getDouble(l);
				if(type!=ItemAttribute.Type.ATTRIBUTE)
					list.add(level-1,new ItemAttribute(type,enchant,null,(int)value));
				else
					list.add(level-1,new ItemAttribute(type,null,new Attribute(attrType,attrOp,value),0));
			}catch(NumberFormatException ex){
				SurvivalImprovements.instance().getLogger().info("[Warning] "+l+" is not a valid number. Skipping...");
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> T getValue(String key, T def){
		if(config.contains(key)){
			return (T) config.get(key);
		}
		SurvivalImprovements.instance().getLogger().info("[Warning] No value specified for "+key+". Using default value ("+def+")");
		return def;
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T getValue(String key, T def, ConfigurationSection section){
		if(section.contains(key)){
			return (T) section.get(key);
		}
		SurvivalImprovements.instance().getLogger().info("[Warning] No value specified for "+section.getCurrentPath()+"."+key+". Using default value ("+def+")");
		return def;
	}
	
	public static boolean isValidWorld(World w, boolean allowEnd){
		return (!WORLD_SPECIFIC || WORLDS.contains(w))&&(w.getEnvironment()!=Environment.THE_END||!MOB_MONEY_NOT_IN_END||allowEnd);
	}
	
}
