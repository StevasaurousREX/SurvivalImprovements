package nl.arfie.bukkit.survivalimprovements.attribute;

import nl.arfie.bukkit.attributes.Attribute;
import nl.arfie.bukkit.attributes.AttributeList;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemAttribute {
	private Type type;
	private Enchantment enchant;
	private Attribute attribute;
	private int enchantLevel;
	
	public ItemAttribute(Type type, Enchantment enchant, Attribute attribute, int enchantLevel){
		this.type=type;
		this.enchant=enchant;
		this.attribute=attribute;
		this.enchantLevel=enchantLevel;
	}
	
	@SuppressWarnings("incomplete-switch")
	public ItemStack addToItemStack(ItemStack is){
		ItemMeta meta = is.getItemMeta();
		switch(type){
			case ENCHANTMENT:
				meta.addEnchant(enchant, enchantLevel, true);
				break;
			case VENOM_ASPECT:
				List<String> lore = meta.getLore();
				switch(enchantLevel){
					case 2:
						lore.add("§7Venom Aspect II");
						break;
					case 3:
						lore.add("§7Venom Aspect III");
						break;
					default:
						lore.add("§7Venom Aspect I");
						break;
				}
				meta.setLore(lore);
				break;
		}
		is.setItemMeta(meta);
		AttributeList list = new AttributeList();
		if(type==Type.ATTRIBUTE){
			list.add(attribute);
			is=list.apply(is,false);
		}
		return is;
	}

	public enum Type {ENCHANTMENT, ATTRIBUTE, VENOM_ASPECT}
	
}