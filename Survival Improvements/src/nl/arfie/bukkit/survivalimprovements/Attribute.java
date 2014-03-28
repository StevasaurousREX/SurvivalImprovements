package nl.arfie.bukkit.survivalimprovements;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import com.comphenix.example.Attributes;

public class Attribute {
	private Type type;
	private Enchantment enchant;
	private Attributes.Attribute attribute;
	private int enchantLevel;
	
	public Attribute(Type type, Enchantment enchant, Attributes.Attribute attribute, int enchantLevel){
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
		Attributes attr = new Attributes(is);
		if(type==Type.ATTRIBUTE){
			attr.add(attribute);
			is=attr.getStack();
		}
		return is;
	}

	public enum Type {ENCHANTMENT, ATTRIBUTE, VENOM_ASPECT}
	
}