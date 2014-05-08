package nl.arfie.bukkit.survivalimprovements.util;

import nl.arfie.bukkit.attributes.AttributeType;
import nl.arfie.bukkit.attributes.Operation;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Util {
	
	public static Operation attributeDefaultOperation(AttributeType type){
		switch(type){
			case ATTACK_DAMAGE:
			case FOLLOW_RANGE:
			case JUMP_STRENGTH:
			case MAX_HEALTH:
			case SPAWN_REINFORCEMENTS:
				return Operation.ADD_NUMBER;
			case KNOCKBACK_RESISTANCE:
			case MOVEMENT_SPEED:
				return Operation.ADD_PERCENTAGE;
			default:
				return null;
		}
	}
	
	@SuppressWarnings("deprecation")
	public static String getPlayerIdentifier(OfflinePlayer p){
		if(Bukkit.getOnlineMode())
			return p.getUniqueId().toString();
		return p.getName();
	}
	@SuppressWarnings("deprecation")
	public static OfflinePlayer getPlayerByIdentifier(String id){
		if(Bukkit.getOnlineMode())
			return Bukkit.getOfflinePlayer(UUID.fromString(id));
		return Bukkit.getOfflinePlayer(id);
	}

	public static List<ItemStack> subTypes(Material base){
		List<ItemStack> items = new LinkedList<ItemStack>();
		switch(base){
			case GRASS:
				items.add(new ItemStack(Material.GRASS,1));
				items.add(new ItemStack(Material.MYCEL,1));
				items.add(new ItemStack(Material.DIRT,1,(short)2));
				break;
				
			case SANDSTONE: case QUARTZ_BLOCK:
				for(short i=0; i<=2; ++i)
					items.add(new ItemStack(base,1,i));
				break;
				
			case GLASS:
				items.add(new ItemStack(Material.GLASS,1));
				items.add(new ItemStack(Material.THIN_GLASS,1));
				break;
				
			case WOOD: case DOUBLE_PLANT:
				for(short i=0; i<=5; ++i)
					items.add(new ItemStack(base,1,i));
				break;
			
			case LOG:
				items.add(new ItemStack(Material.LOG,1,(short)0));
				items.add(new ItemStack(Material.LOG,1,(short)1));
				items.add(new ItemStack(Material.LOG,1,(short)2));
				items.add(new ItemStack(Material.LOG,1,(short)3));
				items.add(new ItemStack(Material.LOG_2,1,(short)0));
				items.add(new ItemStack(Material.LOG_2,1,(short)1));
				break;
				
			case LEAVES:
				items.add(new ItemStack(Material.LEAVES,1,(short)0));
				items.add(new ItemStack(Material.LEAVES,1,(short)1));
				items.add(new ItemStack(Material.LEAVES,1,(short)2));
				items.add(new ItemStack(Material.LEAVES,1,(short)3));
				items.add(new ItemStack(Material.LEAVES_2,1,(short)0));
				items.add(new ItemStack(Material.LEAVES_2,1,(short)1));
				break;
				
			case RECORD_10:
				for(Material m : Material.values()){
					if(m.isRecord())
						items.add(new ItemStack(m,1,(short)0));
				}
				break;
				
			case INK_SACK: case WOOL: case STAINED_CLAY:
				for(short i=0; i<=15; ++i)
					items.add(new ItemStack(base,1,i));
				break;
				
			case RED_ROSE:
				for(short i=0; i<=8; ++i)
					items.add(new ItemStack(base,1,i));
				items.add(new ItemStack(Material.YELLOW_FLOWER,1,(short)0));
				break;
				
			case SAPLING:
				for(short i=0; i<=5; ++i)
					items.add(new ItemStack(Material.SAPLING,1,i));
				break;
				
			case RED_MUSHROOM:
				items.add(new ItemStack(Material.RED_MUSHROOM,1));
				items.add(new ItemStack(Material.BROWN_MUSHROOM,1));
				break;
			
			case SNOW_BLOCK:
				items.add(new ItemStack(Material.SNOW_BLOCK));
				items.add(new ItemStack(Material.SNOW));
				break;
				
			case PUMPKIN:
				items.add(new ItemStack(Material.PUMPKIN));
				items.add(new ItemStack(Material.JACK_O_LANTERN));
				break;
				
			case STAINED_GLASS:
				for(short i=0; i<=15; ++i){
					items.add(new ItemStack(Material.STAINED_GLASS,1,i));
					items.add(new ItemStack(Material.STAINED_GLASS_PANE,1,i));
				}
				break;
				
			case SMOOTH_BRICK:
				for(short i=0; i<=3; ++i)
					items.add(new ItemStack(Material.SMOOTH_BRICK,1,i));
				break;
				
			case FENCE:
				items.add(new ItemStack(Material.FENCE));
				items.add(new ItemStack(Material.NETHER_FENCE));
				break;
				
			case LONG_GRASS:
				items.add(new ItemStack(Material.LONG_GRASS,1,(short)1));
				items.add(new ItemStack(Material.LONG_GRASS,1,(short)2));
				items.add(new ItemStack(Material.DEAD_BUSH));
				break;
				
			case COAL:
				items.add(new ItemStack(Material.COAL));
				items.add(new ItemStack(Material.COAL,1,(short)1));
				break;
				
			case SEEDS:
				items.add(new ItemStack(Material.SEEDS));
				items.add(new ItemStack(Material.PUMPKIN_SEEDS));
				items.add(new ItemStack(Material.MELON_SEEDS));
				break;
				
			case STONE_BUTTON:
				items.add(new ItemStack(Material.STONE_BUTTON));
				items.add(new ItemStack(Material.WOOD_BUTTON));
				break;
				
			case STONE_PLATE:
				items.add(new ItemStack(Material.STONE_PLATE));
				items.add(new ItemStack(Material.WOOD_PLATE));
				break;
				
			case IRON_PLATE:
				items.add(new ItemStack(Material.IRON_PLATE));
				items.add(new ItemStack(Material.GOLD_PLATE));
				break;
				
			case PISTON_BASE:
				items.add(new ItemStack(Material.PISTON_BASE));
				items.add(new ItemStack(Material.PISTON_STICKY_BASE));
				break;
				
			case WOOD_DOOR:
				items.add(new ItemStack(Material.WOOD_DOOR));
				items.add(new ItemStack(Material.IRON_DOOR));
				break;
				
			case DISPENSER:
				items.add(new ItemStack(Material.DISPENSER));
				items.add(new ItemStack(Material.DROPPER));
				break;
				
			case RAILS:
				items.add(new ItemStack(Material.RAILS));
				items.add(new ItemStack(Material.POWERED_RAIL));
				items.add(new ItemStack(Material.DETECTOR_RAIL));
				items.add(new ItemStack(Material.ACTIVATOR_RAIL));
				break;
				
			case MINECART:
				items.add(new ItemStack(Material.MINECART));
				items.add(new ItemStack(Material.STORAGE_MINECART));
				items.add(new ItemStack(Material.POWERED_MINECART));
				items.add(new ItemStack(Material.HOPPER_MINECART));
				items.add(new ItemStack(Material.EXPLOSIVE_MINECART));
				break;
				
			case ANVIL:
				items.add(new ItemStack(Material.ANVIL));
				items.add(new ItemStack(Material.ANVIL,1,(short)1));
				items.add(new ItemStack(Material.ANVIL,1,(short)2));
				break;
				
			case PORK:
				items.add(new ItemStack(Material.PORK));
				items.add(new ItemStack(Material.GRILLED_PORK));
				break;
				
			case RAW_BEEF:
				items.add(new ItemStack(Material.RAW_BEEF));
				items.add(new ItemStack(Material.COOKED_BEEF));
				break;
				
			case RAW_CHICKEN:
				items.add(new ItemStack(Material.RAW_CHICKEN));
				items.add(new ItemStack(Material.COOKED_CHICKEN));
				break;
				
			case RAW_FISH:
				items.add(new ItemStack(Material.RAW_FISH));
				items.add(new ItemStack(Material.COOKED_FISH));
				items.add(new ItemStack(Material.RAW_FISH,1,(short)1));
				items.add(new ItemStack(Material.COOKED_FISH,1,(short)1));
				items.add(new ItemStack(Material.RAW_FISH,1,(short)2));
				items.add(new ItemStack(Material.RAW_FISH,1,(short)3));
				break;
				
			case GOLDEN_APPLE:
				items.add(new ItemStack(Material.GOLDEN_APPLE));
				items.add(new ItemStack(Material.GOLDEN_APPLE,1,(short)1));
				break;
				
			case CARROT:
				items.add(new ItemStack(Material.CARROT_ITEM));
				items.add(new ItemStack(Material.GOLDEN_CARROT));
				break;
				
			case POTATO:
				items.add(new ItemStack(Material.POTATO_ITEM));
				items.add(new ItemStack(Material.BAKED_POTATO));
				items.add(new ItemStack(Material.POISONOUS_POTATO));
				break;
				
			case BUCKET:
				items.add(new ItemStack(Material.BUCKET));
				items.add(new ItemStack(Material.WATER_BUCKET));
				items.add(new ItemStack(Material.LAVA_BUCKET));
				items.add(new ItemStack(Material.MILK_BUCKET));
				break;
				
			case ENDER_PEARL:
				items.add(new ItemStack(Material.ENDER_PEARL));
				items.add(new ItemStack(Material.EYE_OF_ENDER));
				break;
				
			case SKULL_ITEM:
				items.add(new ItemStack(Material.SKULL_ITEM,1,(short)1));
				break;
				
			case IRON_BARDING:
				items.add(new ItemStack(Material.IRON_BARDING));
				items.add(new ItemStack(Material.GOLD_BARDING));
				items.add(new ItemStack(Material.DIAMOND_BARDING));
				break;
				
			case WOOD_PICKAXE:case STONE_PICKAXE:case IRON_PICKAXE:case GOLD_PICKAXE:case DIAMOND_PICKAXE:
				String material = base.toString().split("_")[0];
				items.add(new ItemStack(Material.valueOf(material+"_PICKAXE")));
				items.add(new ItemStack(Material.valueOf(material+"_SPADE")));
				items.add(new ItemStack(Material.valueOf(material+"_AXE")));
				items.add(new ItemStack(Material.valueOf(material+"_HOE")));
				break;
				
			case LEATHER_HELMET:case CHAINMAIL_HELMET:case IRON_HELMET:case GOLD_HELMET:case DIAMOND_HELMET:
				material = base.toString().split("_")[0];
				items.add(new ItemStack(Material.valueOf(material+"_HELMET")));
				items.add(new ItemStack(Material.valueOf(material+"_CHESTPLATE")));
				items.add(new ItemStack(Material.valueOf(material+"_LEGGINGS")));
				items.add(new ItemStack(Material.valueOf(material+"_BOOTS")));
				break;
			
			default:
				items.add(new ItemStack(base,1,(short)0));
		}
		return items;
	}

	public static String genericMaterialName(Material base, boolean defaultNone){
		switch(base){
			case GRASS:
				return "Soil";
				
			case SANDSTONE:
				return "Sandstone";
				
			case QUARTZ_BLOCK:
				return "Quartz Blocks";
				
			case GLASS:
				return "Glass";
				
			case WOOD:
				return "Planks";
				
			case DOUBLE_PLANT:
				return "Tall Plants";
			
			case LOG:
				return "Logs";
				
			case LEAVES:
				return "Leaves";
				
			case RECORD_10:
				return "Music Discs";
				
			case INK_SACK:
				return "Dyes";
				
			case WOOL:
				return "Wool";
				
			case STAINED_CLAY:
				return "Stained Clay";
				
			case RED_ROSE:
				return "Flowers";
				
			case SAPLING:
				return "Saplings";
				
			case RED_MUSHROOM:
				return "Mushrooms";
			
			case SNOW_BLOCK:
				return "Snow";
				
			case PUMPKIN:
				return "Pumpkins";
				
			case STAINED_GLASS:
				return "Stained Glass";
				
			case SMOOTH_BRICK:
				return "Stone Bricks";
				
			case FENCE:
				return "Fences";
				
			case LONG_GRASS:
				return "Miscellanious Plants";
				
			case COAL:
				return "Coal";
				
			case SEEDS:
				return "Seeds";
				
			case STONE_BUTTON:
				return "Buttons";
				
			case STONE_PLATE:
				return "Pressure Plates";
				
			case IRON_PLATE:
				return "Weighted Pressure Plates";
				
			case PISTON_BASE:
				return "Pistons";
				
			case WOOD_DOOR:
				return "Doors";
				
			case DISPENSER:
				return "Dispensers";
				
			case RAILS:
				return "Rails";
				
			case MINECART:
				return "Minecarts";
				
			case ANVIL:
				return "Anvils";
				
			case PORK:
				return "Pork";
				
			case RAW_BEEF:
				return "Beef";
				
			case RAW_CHICKEN:
				return "Poultry";
				
			case RAW_FISH:
				return "Fish";
				
			case GOLDEN_APPLE:
				return "Golden Apples";
				
			case CARROT:
				return "Carrots";
				
			case POTATO:
				return "Potatoes";
				
			case BUCKET:
				return "Buckets";
				
			case ENDER_PEARL:
				return "Ender Pearls";
				
			case SKULL:
				return "Wither Skulls";
				
			case IRON_BARDING:
				return "Horse Armour";
				
			case WOOD_PICKAXE:
				return "Wooden Tools";
			
			case STONE_PICKAXE:
				return "Stone Tools";
				
			case IRON_PICKAXE:
				return "Iron Tools";
				
			case GOLD_PICKAXE:
				return "Golden Tools";
				
			case DIAMOND_PICKAXE:
				return "Diamond Tools";
				
			case LEATHER_HELMET:
				return "Leather Armour";
			
			case CHAINMAIL_HELMET:
				return "Chainmail Armour";
			
			case IRON_HELMET:
				return "Iron Armour";
			
			case GOLD_HELMET:
				return "Golden Armour";
			
			case DIAMOND_HELMET:
				return "Diamond Armour";
			
			default:
				return (defaultNone?"":ucFirst(base.toString()));
		}
	}
	
	public static String ucFirst(String original){
		String[] split = original.split("_");
		if(split.length>1){
			StringBuffer buff = new StringBuffer();
			for(String s : split)
				buff.append(ucFirst(s)+" ");
			return buff.toString();
		}
		return (""+original.charAt(0)).toUpperCase()+original.substring(1).toLowerCase();
	}
	
}
