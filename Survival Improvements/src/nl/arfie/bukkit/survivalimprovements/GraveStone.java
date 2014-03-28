package nl.arfie.bukkit.survivalimprovements;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

public class GraveStone {

	private static HashSet<GraveStone> stones = new HashSet<GraveStone>();
	private Location loc;
	private ItemStack[] contents;
	private int timer;
	private int time=Config.GRAVESTONE_SECONDS;
	private Sign sign;
	private Player player;
	
	private GraveStone(Location loc, Player p, ItemStack[] items) throws IllegalArgumentException{
		this.loc=loc;
		this.contents=items;
		loc.getBlock().setType(Material.SIGN_POST);
		sign = (Sign)loc.getBlock().getState();
		sign.setLine(1,Config.GRAVESTONE_PLAYER_FORMAT+p.getName());
		sign.update();
		player=p;
		timer=Bukkit.getScheduler().scheduleSyncRepeatingTask(SurvivalImprovements.instance(), new Runnable(){
			public void run(){
				updateTimer();
			}
		}, 20L, 20L);
	}
	
	private void updateTimer(){
		--time;
		if(time<0){
			_break(true);
			if(player.isOnline())
//				player.sendMessage("§8Your gravestone has been broken automatically and your items are free for anyone to pick up.");
				player.sendMessage(Config.MESSAGE_GRAVESTONE_TOO_LATE);
			return;
		}
		int minutes=time/60,seconds=time%60;
		sign.setLine(2,minutes+":"+(seconds<10?"0":"")+seconds);
		sign.update();
	}
	
	public void _break(boolean remove, boolean brk){
		if(brk){
			loc.getBlock().getDrops().clear();
			loc.getBlock().breakNaturally();
		}
		Bukkit.getScheduler().cancelTask(timer);
		for(ItemStack is : contents){
			loc.getWorld().dropItem(loc,is);
		}
		
		if(remove)
			stones.remove(this);
	}
	
	public void _break(boolean remove){
		_break(remove,true);
	}
	
	public static void breakAll(){
		for(GraveStone stone : stones){
			stone._break(false);
		}
		stones.clear();
	}
	
	public static void createGravestone(Location loc, Player p, ItemStack[] items) throws IllegalArgumentException{
		stones.add(new GraveStone(loc,p,items));
	}
	
	public static GraveStone getGravestone(Block b){
		for(GraveStone gs : stones){
			if(gs.getLocation().getBlock().equals(b))
				return gs;
		}
		return null;
	}

	private Location getLocation() {
		return loc;
	}

	public Player getPlayer() {
		return player;
	}
	
}
