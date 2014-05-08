package nl.arfie.bukkit.survivalimprovements.economy;

import nl.arfie.bukkit.survivalimprovements.messaging.Chat;
import nl.arfie.bukkit.survivalimprovements.util.Util;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;

public class Trade {

	public enum Type {OFFER, REQUEST}
	
	private OfflinePlayer owner;
	private Type type;
	private ItemStack item;
	
	public static final int MAX_OFFERS=3;
	public static final int MAX_REQUESTS=3;
	
	private static HashSet<Trade> trades = new HashSet<Trade>();
	
	static{
		findMatchingTrades();
	}
	
	public Trade(OfflinePlayer owner, Type type, ItemStack item){
		this.owner=owner;
		this.type=type;
		this.item=item;
		
		trades.add(this);
		findMatchingTrades();
	}
	
	public void cancel(){
		trades.remove(this);
		findMatchingTrades();
	}
	
	public void complete(int count){
		//TODO: add to "traded items" if request
		Chat.sendMessage(owner,"§a"+Util.ucFirst(type.toString())+" completed: "+count+"/"+item.getAmount()+" "+Util.genericMaterialName(item.getType(),false),true);
		if(type==Type.REQUEST)
			Chat.sendMessage(owner,"§aYou can collect your item(s) at the Market.",true);
		int remaining = item.getAmount()-count;
		if(remaining<=0)
			cancel();
		else
			item.setAmount(remaining);
	}
	
	public static void findMatchingTrades(){
		for(Trade t : trades){
			//Check for requests that have a corresponding offer.
			if(t.type==Type.REQUEST){
				Material m = t.item.getType();
				for(Trade t2 : trades){
					if(t2.type==Type.OFFER && t2.item.getType()==m){
						int completion = Math.min(t.item.getAmount(),t2.item.getAmount());
						t.complete(completion);
						t2.complete(completion);
						return;
					}
				}
			}
		}
	}
	
}
