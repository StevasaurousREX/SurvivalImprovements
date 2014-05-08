package nl.arfie.bukkit.survivalimprovements.economy;

import org.bukkit.OfflinePlayer;

public class Economy {

	public static double getBalance(OfflinePlayer player){
		return PlayerStats.statsFor(player).getData(PlayerStats.Type.MONEY);
//		try {
//			return com.earth2me.essentials.api.Economy.getMoneyExact(player).doubleValue();
//		} catch (Exception ex) {
//			return 0.0;
//		}
	}
	
	public static void setBalance(OfflinePlayer player, double balance){
		PlayerStats.statsFor(player).setData(PlayerStats.Type.MONEY,balance,true);
//		try {
//			com.earth2me.essentials.api.Economy.setMoney(player,new BigDecimal(balance));
//		} catch (Exception ex) {}
	}
	
	public static void addMoney(OfflinePlayer player, double money){
		setBalance(player,money+getBalance(player));
	}
	
}
