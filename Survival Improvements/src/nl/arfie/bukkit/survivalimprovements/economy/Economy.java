package nl.arfie.bukkit.survivalimprovements.economy;

public class Economy {

	public static double getBalance(String player){
		return PlayerStats.statsFor(player).getData(PlayerStats.Type.MONEY);
	}
	
	public static void setBalance(String player, double balance){
		PlayerStats.statsFor(player).setData(PlayerStats.Type.MONEY,balance,true);
	}
	
	public static void addMoney(String player, double money){
		setBalance(player,money+getBalance(player));
	}
	
}
