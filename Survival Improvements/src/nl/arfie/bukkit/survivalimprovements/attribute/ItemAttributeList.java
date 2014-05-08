package nl.arfie.bukkit.survivalimprovements.attribute;

import java.util.HashMap;
import java.util.HashSet;

public class ItemAttributeList{
	private HashMap<Integer, HashSet<ItemAttribute>> list = new HashMap<Integer, HashSet<ItemAttribute>>();
	
	public void add(int level, ItemAttribute attr){
		if(list.containsKey(level)){
			list.get(level).add(attr);
		} else {
			HashSet<ItemAttribute> newset = new HashSet<ItemAttribute>();
			newset.add(attr);
			list.put(level,newset);
		}
	}
	
	public HashSet<ItemAttribute> getAttributesForLevel(int level){
		return list.get(level);
	}
}