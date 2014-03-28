package nl.arfie.bukkit.survivallimprovements.boss;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import nl.arfie.bukkit.survivalimprovements.Config;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.comphenix.example.Attributes;
import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

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
//			((LivingEntity)living).getEquipment().setItemInHand(weapon(t,level));
			((LivingEntity)living).getEquipment().setItemInHand(Config.BOSS_GEAR.get(t).getWeaponForLevel(level));
		if(t==Type.ZOMBIE || t==Type.SPIDER)
			((LivingEntity)living).getEquipment().setHelmet(bossHelmet());
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
//			int money=(int)(Math.min(15000.0,25*Math.pow(1.25,level))*type.multiplier);
			int money=Config.BOSS_MONEY_AMOUNT.get(level);
//			Bukkit.broadcastMessage(p.getDisplayName()+" has killed a §llevel "+(level+1)+" "+type.tag+"§f§r and received §c"+money+"§f gold!");
			Bukkit.broadcastMessage(Config.MESSAGE_BOSS_DEFEAT.replaceAll("\\$PLAYER",p.getDisplayName()).replaceAll("\\$BOSS",(Config.ENABLE_BOSS_LEVELS?"level "+(level+1)+" ":"")+Config.BOSS_NAME_TAGS.get(type)).replaceAll("\\$MONEY",""+money));
			try {
				Economy.add(p.getName(),new BigDecimal(money));
			} catch (NoLoanPermittedException | UserDoesNotExistException ex) {
				ex.printStackTrace();
			}
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
	
	public static ItemStack bossHelmet(){
		ItemStack is = new ItemStack(Material.IRON_HELMET,1);
		Attributes attributes = new Attributes(is);
		attributes.add(Attributes.Attribute.newBuilder().name("Knockback Resistance").type(Attributes.AttributeType.GENERIC_KNOCKBACK_RESISTANCE).amount(1.0/3).build());
		return attributes.getStack();
	}
	
	@Deprecated
	public static ItemStack bossArmour(Type type, int level){
		switch(type){
			case BLAZE:
				ItemStack is = new ItemStack(Material.LEATHER_BOOTS,1);
				LeatherArmorMeta im = (LeatherArmorMeta) is.getItemMeta();
				im.setColor(Color.ORANGE);
				im.setDisplayName("§5Level "+(level+1)+" Blazing Boots§f");
				im.setLore(Arrays.asList(new String[]{"Obtained by killing a Blaze Boss"}));
				im.addEnchant(Enchantment.PROTECTION_FIRE,2,true);
				im.addEnchant(Enchantment.DURABILITY,3,true);
				is.setItemMeta(im);
				Attributes attr = new Attributes(is);
				attr.add(Attributes.Attribute.newBuilder().name("Max Health").type(Attributes.AttributeType.GENERIC_MAX_HEALTH).amount(Math.min(20,4*(level+1))).build());
				return(attr.getStack());

			case SPIDER:
				is = new ItemStack(Material.LEATHER_LEGGINGS,1);
				im = (LeatherArmorMeta) is.getItemMeta();
				im.setColor(Color.TEAL);
				im.setDisplayName("§5Level "+(level+1)+" Spider Legs§f");
				im.setLore(Arrays.asList(new String[]{"Obtained by killing a Spider Boss"}));
				im.addEnchant(Enchantment.PROTECTION_EXPLOSIONS,2,true);
				im.addEnchant(Enchantment.DURABILITY,3,true);
				is.setItemMeta(im);
				attr = new Attributes(is);
				attr.add(Attributes.Attribute.newBuilder().name("Movement Speed").type(Attributes.AttributeType.GENERIC_MOVEMENT_SPEED).amount(level>3?0.75:0.25).operation(Attributes.Operation.ADD_PERCENTAGE).build());
				return(attr.getStack());

			case SKELETON:
				is = new ItemStack(Material.LEATHER_HELMET,1);
				im = (LeatherArmorMeta) is.getItemMeta();
				im.setColor(Color.WHITE);
				im.setDisplayName("§5Level "+(level+1)+" Skeleton Helmet§f");
				im.setLore(Arrays.asList(new String[]{"Obtained by killing a Skeleton Boss"}));
				im.addEnchant(Enchantment.PROTECTION_PROJECTILE,2,true);
				im.addEnchant(Enchantment.DURABILITY,3,true);
				is.setItemMeta(im);
				attr = new Attributes(is);
				attr.add(Attributes.Attribute.newBuilder().name("Knockback Resistance").type(Attributes.AttributeType.GENERIC_KNOCKBACK_RESISTANCE).amount(level>3?0.75:0.25).build());
				return(attr.getStack());

			case ZOMBIE:
				is = new ItemStack(Material.LEATHER_CHESTPLATE,1);
				im = (LeatherArmorMeta) is.getItemMeta();
				im.setColor(Color.GREEN);
				im.setDisplayName("§5Level "+(level+1)+" Zombie Armour§f");
				im.setLore(Arrays.asList(new String[]{"Obtained by killing a Zombie Boss"}));
				im.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4,true);
				im.addEnchant(Enchantment.DURABILITY,3,true);
				is.setItemMeta(im);
				return is;
		}
		return null;
	}

}
