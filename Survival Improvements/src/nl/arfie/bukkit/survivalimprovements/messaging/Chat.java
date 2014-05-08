package nl.arfie.bukkit.survivalimprovements.messaging;

import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.ArrayList;

public class Chat {

	private static HashMap<OfflinePlayer, ArrayList<String>> buffer = new HashMap<OfflinePlayer, ArrayList<String>>();
	
	public static void sendMessage(OfflinePlayer player, String msg, boolean doBuffer){
		if(player.isOnline()){
			player.getPlayer().sendMessage(msg);
		} else if(doBuffer){
			if(buffer.containsKey(player))
				buffer.get(player).add(msg);
			else {
				ArrayList<String> nb = new ArrayList<String>();
				nb.add(msg);
				buffer.put(player,nb);
			}
		}
	}
	
}
