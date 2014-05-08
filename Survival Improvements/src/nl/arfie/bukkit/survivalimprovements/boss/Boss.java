package nl.arfie.bukkit.survivalimprovements.boss;

import nl.arfie.bukkit.survivalimprovements.Config;
import nl.arfie.bukkit.survivalimprovements.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Boss {

	private static HashSet<Boss> bosses = new HashSet<Boss>();
	
	public enum Type {
		ZOMBIE(new EntityType[]{EntityType.ZOMBIE},"§2§lZombie Boss",1.0),
		SKELETON(new EntityType[]{EntityType.SKELETON},"§7§lSkeleton Boss",1.0),
		SPIDER(new EntityType[]{EntityType.CAVE_SPIDER, EntityType.SPIDER},"§3§lSpider Boss",1.0),
		BLAZE(new EntityType[]{EntityType.BLAZE},"§6§lBlaze Boss",1.0);
		
		public EntityType[] types;
		public String tag;
		public double multiplier;
		
		private Type(EntityType[] types,String tag, double multip){
			this.types=types;
			this.tag=tag;
			this.multiplier=multip;
		}
	}
	
	public int level=0;
	protected Damageable mob=null;
	public Type type;
	
	public static Boss create(Location loc, int level, Type t)throws IllegalArgumentException{
		if(!Config.BOSSES_SPAWNING || !Config.BOSS_TYPES_SPAWNING.get(t))
			throw new IllegalArgumentException("Spawning this boss was disabled in config.yml");
		Entity e = loc.getWorld().spawnEntity(loc,t.types[0]);
		Damageable living = (Damageable) e;
		living.setMaxHealth(living.getMaxHealth()*(level+2));
		living.setHealth(living.getMaxHealth());
		((LivingEntity)living).setCustomName((Config.ENABLE_BOSS_LEVELS?"Level "+(level+1)+" ":"")+Config.BOSS_NAME_TAGS.get(t));
		((LivingEntity)living).setCustomNameVisible(true);
		Boss b = new Boss();
		((LivingEntity)living).setCanPickupItems(false);
		((LivingEntity)living).setRemoveWhenFarAway(false);
		if(t==Type.ZOMBIE || t==Type.SKELETON)
			((LivingEntity)living).getEquipment().setItemInHand(Config.BOSS_GEAR.get(t).getWeaponForLevel(level));
		b.mob=living;
		b.type=t;
		b.level=level;
		bosses.add(b);
		if(t==Type.ZOMBIE)
			((LivingEntity)b.mob).addPotionEffect(new PotionEffect(PotionEffectType.SPEED,100000,1));
		else if(t==Type.SKELETON && level>1)
			((Skeleton)b.mob).setSkeletonType(Skeleton.SkeletonType.WITHER);
		return b;
	}
	
	protected Boss(){
	}
	
	public static Boss getBossByEntity(Entity d){
		for(Boss b : bosses){
			if(b.mob.equals(d))
				return b;
		}
		return null;
	}
	
	public void killed(){
		Player p = ((LivingEntity)mob).getKiller();
		if(p!=null){
			double money=Config.BOSS_MONEY_AMOUNT.get(level);
			if(Config.BROADCAST_BOSS_KILL)
				Bukkit.broadcastMessage(Config.MESSAGE_BOSS_DEFEAT.replaceAll("\\$PLAYER",p.getDisplayName()).replaceAll("\\$BOSS",(Config.ENABLE_BOSS_LEVELS?"level "+(level+1)+" ":"")+Config.BOSS_NAME_TAGS.get(type)).replaceAll("\\$MONEY",String.format("%.2f",money)));
			Economy.addMoney(p,money);
		}
		bosses.remove(this);
	}
	
	@Deprecated
	public static ItemStack weapon(Type boss, int level){
		ItemStack is=null;
		ItemMeta im;
		switch(boss){
			case ZOMBIE:
				is = new ItemStack(Material.IRON_SWORD,1);
				im = is.getItemMeta();
				im.setDisplayName("§r§5Level "+(level+1)+" Zombie Sword§f");
				im.addEnchant(Enchantment.DAMAGE_ALL,Math.min(4,(int)((level+1)/1.5)+1),true);
				if((level+1)>3)
					im.addEnchant(Enchantment.KNOCKBACK,Math.min(3,(level+1)-2),true);
				if((level+1)>5)
					im.addEnchant(Enchantment.FIRE_ASPECT,Math.min(2,(level+1)-5),true);
				im.addEnchant(Enchantment.LOOT_BONUS_MOBS,Math.min(3,(int)Math.ceil((level+1)/5.0)),true);
				im.addEnchant(Enchantment.DURABILITY,Math.min(3,(int)Math.ceil((level+1)/2.0)),true);
				im.setLore(Arrays.asList(new String[]{"Obtained by killing a Zombie Boss"}));
				is.setItemMeta(im);
				break;
				
			case SKELETON:
				is = new ItemStack(Material.BOW,1);
				im = is.getItemMeta();
				im.setDisplayName("§r§5Level "+(level+1)+" Skeleton Bow§f");
				im.addEnchant(Enchantment.ARROW_DAMAGE,Math.min(4,(int)Math.ceil((level+1)/3.5)+1),true);
				if((level+1)>3)
					im.addEnchant(Enchantment.ARROW_KNOCKBACK,Math.min(3,(level+1)-2),true);
				if((level+1)>5)
					im.addEnchant(Enchantment.ARROW_FIRE,1,true);
				if((level+1)>3)
					im.addEnchant(Enchantment.ARROW_INFINITE,1,true);
				im.addEnchant(Enchantment.DURABILITY,Math.min(3,(int)Math.ceil((level+1)/2.0)),true);
				im.setLore(Arrays.asList(new String[]{"Obtained by killing a Skeleton Boss"}));
				is.setItemMeta(im);
				break;
				
			case SPIDER:
				is = new ItemStack(Material.WOOD_SWORD,1);
				im = is.getItemMeta();
				im.setDisplayName("§r§5Level "+(level+1)+" Spider Sword§f");
				if((level+1)>3)
					im.addEnchant(Enchantment.KNOCKBACK,Math.min(3,(level+1)-2),true);
				im.setLore(Arrays.asList(new String[]{"Obtained by killing a Spider Boss"}));
				List<String> lore = im.getLore();
				String str="§7Venom Aspect ";
				switch(Math.min(4,level/2+1)){
					case 1:str+="I";break;
					case 2:case 3:str+="II";break;
					case 4:str+="III";break;
				}
				lore.add(0,str);
				im.setLore(lore);
				im.addEnchant(Enchantment.DURABILITY,5,true);
				is.setItemMeta(im);
				break;
				
			case BLAZE:
				is = new ItemStack(Material.WOOD_SWORD,1);
				im = is.getItemMeta();
				im.setDisplayName("§r§5Level "+(level+1)+" Blaze Sword§f");
				im.addEnchant(Enchantment.FIRE_ASPECT,Math.min(5,level/2+1),true);
				im.setLore(Arrays.asList(new String[]{"Obtained by killing a Blaze Boss"}));
				lore = im.getLore();
				im.setLore(lore);
				im.addEnchant(Enchantment.DURABILITY,5,true);
				is.setItemMeta(im);
				break;
		}
		return is;
	}
	
	public static int getVenomLevel(ItemStack is){
		if(is!=null){
			if(is.getItemMeta()!=null){
				if(is.getItemMeta().getLore()!=null)
					for(String s : is.getItemMeta().getLore()){
						if(s.equals("§7Venom Aspect I"))
							return 1;
						if(s.equals("§7Venom Aspect II"))
							return 2;
						if(s.equals("§7Venom Aspect III"))
							return 3;
					}
			}
		}
		return 0;
	}

}
