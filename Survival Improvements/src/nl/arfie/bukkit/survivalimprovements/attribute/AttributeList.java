package nl.arfie.bukkit.survivalimprovements.attribute;

import java.util.HashMap;
import java.util.HashSet;

public class AttributeList{
	private HashMap<Integer, HashSet<Attribute>> list = new HashMap<Integer, HashSet<Attribute>>();
	
	public void add(int level, Attribute attr){
		if(list.containsKey(level)){
			list.get(level).add(attr);
		} else {
			HashSet<Attribute> newset = new HashSet<Attribute>();
			newset.add(attr);
			list.put(level,newset);
		}
	}
	
	public HashSet<Attribute> getAttributesForLevel(int level){
		return list.get(level);
	}
}