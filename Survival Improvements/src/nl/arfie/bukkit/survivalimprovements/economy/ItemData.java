package nl.arfie.bukkit.survivalimprovements.economy;

import nl.arfie.bukkit.survivalimprovements.NoValueSpecifiedException;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;

public class ItemData {

	private Material type;
	private int subType; //-1 for all subtypes
	private double cost; //Will automatically be changed based based on durability and enchants on items that take damage.
	
	private static HashSet<ItemData> itemDB = new HashSet<ItemData>();
	
	static{
		new ItemData(Material.STONE, -1, 1.0);
		new ItemData(Material.DIAMOND_SWORD, -1, 100.0);
	}
	
	private ItemData(Material type, int subType, double cost){
		this.type=type;
		this.subType=subType;
		this.cost=cost;
		itemDB.add(this);
	}
	
	public double getValue(ItemStack is){
		double damage = 1.0-is.getDurability()/Math.max(1,type.getMaxDurability());
		return cost*damage;
	}
	
	public static double getItemCost(ItemStack is)throws NoValueSpecifiedException{
		for(ItemData d : itemDB){
			if(d.type==is.getType() && (d.subType<0 || d.subType==is.getDurability())){
				return d.getValue(is);
			}
		}
		throw new NoValueSpecifiedException();
	}
	
	public static boolean itemExists(Material m, int subType){
		for(ItemData d : itemDB){
			if(d.type==m && (d.subType==subType || d.subType<0))
				return true;
		}
		return false;
	}
	
}
