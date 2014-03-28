package nl.arfie.bukkit.survivalimprovements;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;

import net.ess3.api.Economy;
import nl.arfie.bukkit.survivalimprovements.boss.Boss;
import nl.arfie.bukkit.survivalimprovements.boss.PlayerStats;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;

import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

public class SIListeners implements Listener {

	@EventHandler(priority=EventPriority.LOWEST)
	public void playerDeath(PlayerDeathEvent e){
		if(!Config.isValidWorld(e.getEntity().getWorld(),true))return;
		
		Player p = e.getEntity();
		try {
			if(Config.LOSE_MONEY_ON_DEATH){
				BigDecimal balance=Economy.getMoneyExact(p.getName());
				BigInteger loss=balance.multiply(new BigDecimal(Config.MONEY_LOSS_ON_DEATH)).toBigInteger();
				
				if(p.getKiller() instanceof Player && Config.PLAYER_KILL_MONEY){
					Player k = (Player) p.getKiller();
					Economy.add(k.getName(),new BigDecimal(loss.doubleValue()*Config.PLAYER_KILL_MONEY_AMOUNT));
				}
				
				balance=balance.subtract(new BigDecimal(loss.doubleValue()));
				Economy.setMoney(p.getName(),balance);
	//			e.setDeathMessage(e.getDeathMessage()+" and lost §c"+loss.toString()+"§f gold!");
				e.setDeathMessage(Config.MONEY_LOSS_MESSAGE.replaceAll("\\$MESSAGE",e.getDeathMessage()).replaceAll("\\$LOSS",loss.toString()));
			}
			
			//Gravestone
			
			if(e.getDrops().size()>0 && Config.GRAVESTONES){
//				p.sendMessage("§7Find your gravestone and break it within §c5§7 minutes to retrieve your items safely.");
				p.sendMessage(Config.MESSAGE_GRAVESTONE_SPAWN);
				p.sendMessage(Config.MESSAGE_GRAVESTONE_TIMER);
				ItemStack[] drops = new ItemStack[e.getDrops().size()];
				int i=0;
				for(ItemStack is : e.getDrops()){
					drops[i++]=is;
				}
				GraveStone.createGravestone(p.getLocation(),p,drops);
				e.getDrops().clear();
			}
		} catch (UserDoesNotExistException | NoLoanPermittedException ex) {
			ex.printStackTrace();
		}
	}
	
	@EventHandler
	public void blockBreak(BlockBreakEvent e){
		if(!Config.isValidWorld(e.getBlock().getWorld(),true))return;
		
		Block block = e.getBlock();
		GraveStone gravestone = GraveStone.getGravestone(block);
		if(gravestone!=null){
			if(e.getPlayer().isOp()||e.getPlayer().equals(gravestone.getPlayer())){
				gravestone._break(true,false);
				block.getDrops().clear();
			} else
			e.setCancelled(true);
		} else if(e.getBlock().getType()==Material.MOB_SPAWNER) try {
			CreatureSpawner spawner = (CreatureSpawner) e.getBlock().getState();
			Boss.Type bossType = null; int bossLevel=0;
			PlayerStats ps = PlayerStats.statsFor(e.getPlayer().getName());
			switch(spawner.getSpawnedType()){
				case ZOMBIE: bossType=Boss.Type.ZOMBIE; bossLevel=PlayerStats.getLevelByStat(ps.getData(PlayerStats.Type.ZOMBIE_KILLS)); break;
				case SKELETON: bossType=Boss.Type.SKELETON; bossLevel=PlayerStats.getLevelByStat(ps.getData(PlayerStats.Type.SKELETON_KILLS));  break;
				case SPIDER: case CAVE_SPIDER: bossType=Boss.Type.SPIDER; bossLevel=PlayerStats.getLevelByStat(ps.getData(PlayerStats.Type.SPIDER_KILLS));  break;
				case BLAZE: bossType=Boss.Type.BLAZE; bossLevel=PlayerStats.getLevelByStat(ps.getData(PlayerStats.Type.BLAZE_KILLS));  break;
			}
			if(!Config.ENABLE_BOSS_LEVELS)
				bossLevel=2;
			if(bossType!=null && Config.BOSSES_SPAWNING && Config.BOSSES_FROM_SPAWNERS && Config.BOSS_TYPES_SPAWNING.get(bossType)){
				Boss boss = Boss.create(e.getBlock().getLocation(),bossLevel,bossType);
//				e.getPlayer().sendMessage("You have encountered a §llevel "+(boss.level+1)+" "+boss.type.tag+"§f§r!");
				e.getPlayer().sendMessage(Config.MESSAGE_BOSS_ENCOUNTER.replaceAll("\\$BOSS",(Config.ENABLE_BOSS_LEVELS?"§llevel "+(boss.level+1)+" ":"")+Config.BOSS_NAME_TAGS.get(boss.type)));
				e.getBlock().getWorld().playEffect(e.getBlock().getLocation(),Effect.MOBSPAWNER_FLAMES,0);
			}
		}catch(IllegalArgumentException ex){}
	}
	
	@EventHandler
	public void blockfade(BlockFadeEvent e){
		if(!Config.isValidWorld(e.getBlock().getWorld(),true))return;
		
		Block block = e.getBlock();
		GraveStone gravestone = GraveStone.getGravestone(block);
		if(gravestone!=null){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void entityHitByEntity(EntityDamageByEntityEvent e){
		if(!Config.isValidWorld(e.getEntity().getWorld(),true))return;
		
		Entity damager = e.getDamager();
		if(damager!=null){
			if(damager instanceof Arrow){
				ProjectileSource src = ((Projectile)damager).getShooter();
				if(src instanceof Skeleton){
					Boss b = Boss.getBossByEntity((Skeleton)src);
					if(b!=null){
						if(b.type==Boss.Type.SKELETON && b.level>1){
							try{
								((LivingEntity)e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.WITHER,200,0));
							}catch(ClassCastException ex){}
						}
					}
				}
			} else {
				if(damager instanceof Player){
					Player p = (Player) damager;
					int lvl = Boss.getVenomLevel(p.getItemInHand());
					if(lvl>0){
						try{
							((LivingEntity)e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.POISON,100,lvl-1));
						}catch(ClassCastException ex){}
					}
				}
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void entityDie(EntityDeathEvent e){
		if(!Config.isValidWorld(e.getEntity().getWorld(),true))return;
		
		LivingEntity le = e.getEntity();
		Player p = le.getKiller();
		if(p!=null){
			PlayerStats stats = PlayerStats.statsFor(p.getName());
			try {
				double m=0.0;
//				switch(le.getType()){
//					case ZOMBIE: case SKELETON: case SPIDER: case CREEPER: m=15.0; break;
//					case ENDERMAN: case CAVE_SPIDER: case PIG_ZOMBIE: case SLIME: m=20.0; break;
//					case BLAZE: case MAGMA_CUBE: case WITCH: m=25.0; break;
//					case GHAST: m=30.0; break;
//					
//					case PIG: case SHEEP: case COW: case CHICKEN: case MUSHROOM_COW: case SQUID: m=1.0; break;
//				}
				
				if(Config.MOB_MONEY_AMOUNTS.containsKey(le.getType()) && Config.MOB_MONEY)
					m=Config.MOB_MONEY_AMOUNTS.get(le.getType());
				
				if(m>0.0 && Config.MOB_MONEY_SOUND)
					p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.5f, 3.0f);
				
				switch(le.getType()){
					case ZOMBIE:
						stats.addData(PlayerStats.Type.ZOMBIE_KILLS,1,true);
						break;
					case SKELETON:
						stats.addData(PlayerStats.Type.SKELETON_KILLS,1,true);
						break;
					case SPIDER:
						stats.addData(PlayerStats.Type.SPIDER_KILLS,1,true);
						break;
					case BLAZE:
						stats.addData(PlayerStats.Type.BLAZE_KILLS,1,true);
						break;
					case PLAYER:
						stats.addData(PlayerStats.Type.PLAYER_KILLS,1,true);
						break;
				}
				
				Boss b = Boss.getBossByEntity(le);
				
				if(b!=null){
					m=0.0;
					b.killed();
					e.getDrops().clear();
					e.getEntity().getEquipment().setItemInHand(null);
					e.getEntity().getEquipment().setHelmet(null);
					ItemStack is = Config.BOSS_GEAR.get(b.type).getWeaponForLevel(b.level);
					if(is!=null)
						e.getDrops().add(is);
					e.getDrops().add(Config.BOSS_GEAR.get(b.type).getArmourForLevel(b.level));
				}

				if(Config.isValidWorld(e.getEntity().getWorld(),false) && Config.MOB_MONEY)
					Economy.add(p.getName(),new BigDecimal(m));
			} catch (NoLoanPermittedException | ArithmeticException
					| UserDoesNotExistException ex) {
				ex.printStackTrace();
			}
		}
	}
	
}
