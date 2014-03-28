package nl.arfie.bukkit.survivallimprovements.boss;

import nl.arfie.bukkit.survivalimprovements.attribute.Attribute;
import nl.arfie.bukkit.survivalimprovements.attribute.AttributeList;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Arrays;
import java.util.HashSet;

import org.bukkit.ChatColor;

public class Equipment {
	
	public enum SwordType {WOOD(Material.WOOD_SWORD),STONE(Material.STONE_SWORD),GOLD(Material.GOLD_SWORD),IRON(Material.IRON_SWORD),DIAMOND(Material.DIAMOND_SWORD);
		Material material;
		private SwordType(Material material){
			this.material=material;
		}
	}
	
	public enum ArmourType {CAP(Material.LEATHER_HELMET),TUNIC(Material.LEATHER_CHESTPLATE),PANTS(Material.LEATHER_LEGGINGS),BOOTS(Material.LEATHER_BOOTS);
		Material material;
		private ArmourType(Material material){
			this.material=material;
		}
	}
	
	public ItemStack sword, bow, armour;
	public AttributeList asword,abow,aarmour;
	
	public void setSword(boolean enabled, SwordType type, String name, String lore, AttributeList attributes){
		if(enabled){
			sword = new ItemStack(type.material,1);
			ItemMeta meta = sword.getItemMeta();
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',name));
			meta.setLore(Arrays.asList(lore));
			sword.setItemMeta(meta);
			asword=attributes;
		}else{
			sword=null;
			asword=null;
		}
	}
	
	public void setBow(boolean enabled, String name, String lore, AttributeList attributes){
		if(enabled){
			bow = new ItemStack(Material.BOW,1);
			ItemMeta meta = bow.getItemMeta();
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',name));
			meta.setLore(Arrays.asList(lore));
			bow.setItemMeta(meta);
			abow=attributes;
		}else{
			bow=null;
			abow=null;
		}
	}
	
	public void setArmour(boolean enabled, ArmourType type, Color color, String name, String lore, AttributeList attributes){
		if(enabled){
			armour = new ItemStack(type.material,1);
			LeatherArmorMeta meta =  (LeatherArmorMeta)armour.getItemMeta();
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',name));
			meta.setLore(Arrays.asList(lore));
			meta.setColor(color);
			armour.setItemMeta(meta);
			aarmour=attributes;
		}else{
			armour=null;
			aarmour=null;
		}
	}
	
	public ItemStack getSwordForLevel(int level){
		if(sword==null)return null;
		
		ItemStack ns = sword.clone();
		ItemMeta meta = ns.getItemMeta();
		meta.setDisplayName(meta.getDisplayName().replaceAll("\\$LEVEL",""+(level+1)));
		
		ns.setItemMeta(meta);
		
		HashSet<Attribute> attributes = asword.getAttributesForLevel(level);
		if(attributes!=null){
			for(Attribute a : attributes)
				ns = a.addToItemStack(ns);
		}
		return ns;
	}
	
	public ItemStack getBowForLevel(int level){
		if(bow==null)return null;
		
		ItemStack ns = bow.clone();
		ItemMeta meta = ns.getItemMeta();
		meta.setDisplayName(meta.getDisplayName().replaceAll("\\$LEVEL",""+(level+1)));
		
		ns.setItemMeta(meta);
		
		HashSet<Attribute> attributes = abow.getAttributesForLevel(level);
		if(attributes!=null){
			for(Attribute a : attributes)
				ns = a.addToItemStack(ns);
		}
		return ns;
	}
	
	public ItemStack getArmourForLevel(int level){
		if(armour==null)return null;
		
		ItemStack ns = armour.clone();
		ItemMeta meta = ns.getItemMeta();
		meta.setDisplayName(meta.getDisplayName().replaceAll("\\$LEVEL",""+(level+1)));
		
		ns.setItemMeta(meta);
		
		HashSet<Attribute> attributes = aarmour.getAttributesForLevel(level);
		if(attributes!=null){
			for(Attribute a : attributes)
				ns = a.addToItemStack(ns);
		}
		return ns;
	}
	
	public ItemStack getWeaponForLevel(int level){
		ItemStack sword= getSwordForLevel(level), bow= getBowForLevel(level);
		if(sword!=null)return sword;
		return bow;
	}
}