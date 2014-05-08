package nl.arfie.bukkit.survivalimprovements.economy;

import nl.arfie.bukkit.survivalimprovements.NoValueSpecifiedException;
import nl.arfie.bukkit.survivalimprovements.SurvivalImprovements;
import nl.arfie.bukkit.survivalimprovements.util.Util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/*
 * THE MARKET IS STILL UNFINISHED.
 */

public class Market {

	public enum Category {
		BASIC_BLOCKS(Material.GRASS,0,
				Material.STONE,
				Material.COBBLESTONE,
				Material.DIRT,
				Material.GRASS,			//+podzol + mycel (v)
				Material.SAND,
				Material.SANDSTONE,		//+subtypes (v)
				Material.GLASS,			//+glass_pane (v)
				Material.GRAVEL,
				Material.LOG,			//+subtypes + log_2 (v)
				Material.WOOD,			//+subtypes (v)
				Material.WOOL,			//+subtypes (v)
				Material.BRICK,
				Material.OBSIDIAN,
				Material.ICE,
				Material.SNOW_BLOCK,	//+slab (v)
				Material.CLAY,
				Material.PUMPKIN,		//+lantern (v)
				Material.NETHERRACK,
				Material.SOUL_SAND,
				Material.GLOWSTONE,
				Material.STAINED_GLASS,	//+stained_glass_pane (v)
				Material.SMOOTH_BRICK,	//+subtypes (v)
				Material.MELON_BLOCK,
				Material.NETHER_BRICK,
				Material.ENDER_STONE,
				Material.QUARTZ_BLOCK,	//+subtypes (v)
				Material.STAINED_CLAY,	//+subtypes (v)
				Material.HARD_CLAY,
				Material.PACKED_ICE,
				Material.FENCE,			//+nether_brick_fence (v)
				Material.IRON_BLOCK,
				Material.GOLD_BLOCK,
				Material.LAPIS_BLOCK,
				Material.EMERALD_BLOCK,
				Material.DIAMOND_BLOCK),
				
		PLANTS(Material.RED_ROSE,8,
				Material.RED_ROSE,		//+subtypes (v)
				Material.SAPLING,		//+subtypes (v)
				Material.RED_MUSHROOM,	//+brown (v)
				Material.DOUBLE_PLANT,	//+subtypes (v)
				Material.LONG_GRASS,	//+subtypes (v)
				Material.VINE,
				Material.WATER_LILY,
				Material.LEAVES,		//+subtypes (v)
				Material.CACTUS),
		
		MATERIALS(Material.LEATHER,0,
				Material.IRON_INGOT,
				Material.GOLD_INGOT,
				Material.GOLD_NUGGET,
				Material.DIAMOND,
				Material.EMERALD,
				Material.COAL,				//+subtypes (v)
				Material.SLIME_BALL,
				Material.FEATHER,
				Material.INK_SACK,			//+subtypes (v)
				Material.STICK,
				Material.STRING,
				Material.BONE,
				Material.WHEAT,
				Material.SULPHUR,
				Material.FLINT,
				Material.GLOWSTONE_DUST,
				Material.SUGAR_CANE,
				Material.SUGAR,
				Material.PAPER,
				Material.GLASS_BOTTLE,
				Material.CLAY_BALL,
				Material.CLAY_BRICK,
				Material.SEEDS,				//+pumpkin+melon (v)
				Material.NETHER_BRICK_ITEM,
				Material.QUARTZ,
				Material.NETHER_STALK,
				Material.NETHER_STAR,
				Material.BLAZE_ROD,
				Material.BLAZE_POWDER,
				Material.SPIDER_EYE,
				Material.FERMENTED_SPIDER_EYE,
				Material.GHAST_TEAR,
				Material.SPECKLED_MELON,
				Material.MAGMA_CREAM),
				
		REDSTONE(Material.REDSTONE,0,
				Material.REDSTONE,
				Material.REDSTONE_TORCH_ON,
				Material.LEVER,
				Material.TRIPWIRE_HOOK,
				Material.STONE_BUTTON,		//+wooden (v)
				Material.STONE_PLATE,		//+wooden (v)
				Material.IRON_PLATE,		//+gold (v)
				Material.REDSTONE_BLOCK,
				Material.DAYLIGHT_DETECTOR,
				Material.DIODE,
				Material.REDSTONE_COMPARATOR,
				Material.PISTON_BASE,		//+sticky (v)
				Material.REDSTONE_LAMP_OFF,
				Material.WOOD_DOOR,			//+iron (v)
				Material.TRAP_DOOR,
				Material.FENCE_GATE,
				Material.HOPPER,
				Material.DISPENSER,			//+dropper (v)
				Material.NOTE_BLOCK,
				Material.TNT,
				Material.RAILS,				//+subtypes (v)
				Material.MINECART),			//+subtypes (v)),
		
		STORAGE(Material.CHEST,0,
				Material.CHEST,
				Material.WORKBENCH,
				Material.FURNACE,
				Material.TRAPPED_CHEST,
				Material.BREWING_STAND_ITEM,
				Material.ENCHANTMENT_TABLE,
				Material.ANVIL,				//+subtypes (v)
				Material.ENDER_CHEST,
				Material.JUKEBOX,
				Material.FLOWER_POT_ITEM,
				Material.ITEM_FRAME,
				Material.CAULDRON_ITEM),
		
		FOOD(Material.APPLE,0,
				Material.APPLE,
				Material.MUSHROOM_SOUP,
				Material.BREAD,
				Material.PORK,				//+cooked (v)
				Material.RAW_BEEF,			//+cooked (v)
				Material.RAW_CHICKEN,		//+cooked (v)
				Material.RAW_FISH,			//+other types + cooked (v)
				Material.MELON,
				Material.CAKE,
				Material.COOKIE,
				Material.GOLDEN_APPLE,		//+god (v)
				Material.ROTTEN_FLESH,
				Material.SPIDER_EYE,
				Material.CARROT,			//+golden (v)
				Material.POTATO,			//+cooked + rotten (v)
				Material.PUMPKIN_PIE),
				
		MISCELLANIOUS(Material.LAVA_BUCKET,0,
				Material.BUCKET,			//+fillings (v)
				Material.SIGN,
				Material.LADDER,
				Material.PAINTING,
				Material.ENDER_PEARL,		//+eye_of_ender (v)
				Material.SNOW_BALL,
				Material.SKULL_ITEM,		//only wither skull (v)
				Material.BOOK,
				Material.BEACON,
				Material.EMPTY_MAP,
				Material.FIREBALL,
				Material.EXP_BOTTLE,
				Material.RECORD_10,			//+other records (v)
				Material.IRON_BARDING,		//+other horse armour (v)
				Material.SADDLE,
				Material.BOAT,
				Material.WEB),
				
		TOOLS(Material.IRON_AXE,0,
				Material.WOOD_PICKAXE,		//+other wood tools (v)
				Material.STONE_PICKAXE,		//+other stone tools (v)
				Material.IRON_PICKAXE,		//+other iron tools (v)
				Material.GOLD_PICKAXE,		//+other gold tools (v)
				Material.DIAMOND_PICKAXE,	//+other diamond tools (v)
				Material.FLINT_AND_STEEL,
				Material.COMPASS,
				Material.WATCH,
				Material.FISHING_ROD,
				Material.SHEARS,
				Material.LEASH),
				
		COMBAT(Material.IRON_SWORD,0,
				Material.WOOD_SWORD,
				Material.STONE_SWORD,
				Material.IRON_SWORD,
				Material.GOLD_SWORD,
				Material.DIAMOND_SWORD,
				Material.BOW,
				Material.ARROW,
				Material.LEATHER_HELMET,	//+other leather armour (v)
				Material.CHAINMAIL_HELMET,	//+other chainmail armour (v)
				Material.IRON_HELMET,		//+other iron armour (v)
				Material.GOLD_HELMET,		//+other gold armour (v)
				Material.DIAMOND_HELMET);	//+other diamond armour (v)
		
		public Material display; short display_data;
		public Material[] items;
		
		public Inventory inv;
		
		private static HashMap<Material, Inventory> offers = new HashMap<Material, Inventory>();
		private static HashMap<OfflinePlayer, Inventory> playerTrades = new HashMap<OfflinePlayer, Inventory>();
		
		private Category(Material display, int display_data, Material... items){
			this.display=display;
			this.display_data=(short)display_data;
			this.items=items;
			inv = Bukkit.createInventory(null, (int)Math.ceil(items.length/9.0)*9+9, "§0§l"+Util.ucFirst(toString()));
		}
		
		public static Inventory getOffers(Material m){
			if(offers.containsKey(m))
				return offers.get(m);
			
			List<ItemStack> items = Util.subTypes(m);
			int size=(int)Math.ceil(items.size()/9.0)*9+9;
			Inventory inv = Bukkit.createInventory(null,size,"§0§l"+Util.genericMaterialName(m,false));
			offers.put(m,inv);
			for(ItemStack is : items){
				inv.addItem(getRequestItem(is.getType(),(byte)1,is.getDurability()));
			}
			inv.setItem(size-1,backToCategories);
			return inv;
		}
	}
	
	private static Inventory categories;
	@SuppressWarnings("unused")
	private static int cycleTimer;
	private static long cycle=0;
	static ItemStack backToCategories = new ItemStack(Material.COMPASS);
	static{
		ItemMeta meta = backToCategories.getItemMeta();
		meta.setDisplayName("§bBack to §lCategories");
		backToCategories.setItemMeta(meta);
	}
	
	private static HashSet<Inventory> requestInvs = new HashSet<Inventory>();
	
	public static void generateInventories(){
		categories = Bukkit.createInventory(null, 18, "§0§lCategories");
		int i=9;
		for(Category c : Category.values()){
			ItemStack disp = new ItemStack(c.display,1,c.display_data);
			ItemMeta meta = disp.getItemMeta();
			meta.setDisplayName("§r"+Util.ucFirst(c.toString()));
			disp.setItemMeta(meta);
			categories.setItem(i++,disp);
			
			for(Material m : c.items){
				c.inv.addItem(Util.subTypes(m).get(0));
			}
			c.inv.setItem(c.inv.getSize()-1,backToCategories);
		}
		ItemStack sell = new ItemStack(Material.GOLD_INGOT);
		ItemMeta meta = sell.getItemMeta();
		meta.setDisplayName("§bSell items");
		sell.setItemMeta(meta);
		categories.setItem(0,sell);
		
		ItemStack trades = new ItemStack(Material.EMERALD); //TODO: amount based on number of trades
		meta = trades.getItemMeta();
		meta.setDisplayName("§bMy trades (0)");
		trades.setItemMeta(meta);
		categories.setItem(4,trades);
		
		ItemStack search = new ItemStack(Material.COMPASS);
		meta = search.getItemMeta();
		meta.setDisplayName("§bSearch by item name");
		search.setItemMeta(meta);
		categories.setItem(8,search);
		
		cycleTimer = Bukkit.getScheduler().scheduleSyncRepeatingTask(SurvivalImprovements.instance(),new Runnable(){
			public void run(){
				++cycle;
				for(Category c : Category.values()){
					for(int i=0; i<c.items.length; ++i){
						Material m = c.items[i];
						List<ItemStack> list = Util.subTypes(m);
						ItemStack is = list.get((int)cycle%list.size());
						is.setAmount(list.size());
						ItemMeta meta = is.getItemMeta();
						String name=Util.genericMaterialName(m,true);
						if(!name.equals(""))
							meta.setDisplayName("§r"+name);
						is.setItemMeta(meta);
						c.inv.setItem(i,is);
					}
				}
			}
		},20L,20L);
	}
	
	public static void showCategories(Player p){
		if(categories==null)
			generateInventories();
		p.openInventory(categories);
	}
	
	public static void invClick(InventoryClickEvent e){
		if(categories==null)
			generateInventories();
		
		Player p = (Player) e.getWhoClicked();
		Inventory inv = e.getInventory();
		
		ItemStack itemClicked = e.getCurrentItem();
		if(itemClicked==null)return;
		if(itemClicked.getType()==Material.AIR)return;
		
		if(inv.getName().equalsIgnoreCase(categories.getName())){
			e.setCancelled(true);
			String name = itemClicked.getItemMeta().getDisplayName().substring(2);
			for(Category c : Category.values()){
				if(Util.ucFirst(c.toString()).equals(name)){
					p.openInventory(c.inv);
					return;
				}
			}
		} else for(Category c : Category.values()){
			if(inv.getName().equalsIgnoreCase(c.inv.getName())){
				e.setCancelled(true);
				if(itemClicked.getItemMeta().hasDisplayName()){
					if(itemClicked.getItemMeta().getDisplayName().equals(backToCategories.getItemMeta().getDisplayName())){
						p.openInventory(categories);
						return;
					}
				}
				p.openInventory(Category.getOffers(c.items[e.getSlot()]));
			}
		}
		for(Inventory it : Category.offers.values()){
			if(inv.getName().equals(it.getName())){
				e.setCancelled(true);
				if(itemClicked.getItemMeta().hasDisplayName()){
					if(itemClicked.getItemMeta().getDisplayName().equals(backToCategories.getItemMeta().getDisplayName())){
						p.openInventory(categories);
						return;
					}
				}
				if(ItemData.itemExists(itemClicked.getType(),itemClicked.getDurability()))
					p.openInventory(createRequestInventory(itemClicked.getType(),itemClicked.getDurability()));
			}
		}
		for(Inventory it : requestInvs){
			if(inv.getName().equals(it.getName())){
				e.setCancelled(true);
				if(itemClicked.getItemMeta().hasDisplayName()){
					if(itemClicked.getItemMeta().getDisplayName().equals(backToCategories.getItemMeta().getDisplayName())){
						p.openInventory(categories);
						return;
					}
				}
				int amount=inv.getItem(4).getAmount();
				if(itemClicked.getItemMeta().getDisplayName().endsWith("-1"))--amount;
				if(itemClicked.getItemMeta().getDisplayName().endsWith("-10"))amount-=10;
				if(itemClicked.getItemMeta().getDisplayName().endsWith("+1"))++amount;
				if(itemClicked.getItemMeta().getDisplayName().endsWith("+10")){
					if(amount==1)
						amount=10;
					else
						amount+=10;
				}
				if(amount<=0)amount=1;
				if(amount>64)amount=64;
				inv.getItem(4).setAmount(amount);
				ItemMeta meta = inv.getItem(4).getItemMeta();
				meta.setDisplayName("§r"+amount+"× "+Util.ucFirst(inv.getItem(4).getType().toString()));
				inv.getItem(4).setItemMeta(meta);
				inv.setItem(4,getRequestItem(inv.getItem(4).getType(),(byte)amount,inv.getItem(4).getDurability()));
			}
		}
	}

	private static Inventory createRequestInventory(Material type, short data) {
		Inventory inv = Bukkit.createInventory(null,9,createFittingInvTitle("§0§lRequesting: §8"+Util.genericMaterialName(type,false)));
		ItemStack r10 = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)14);
		ItemMeta meta = r10.getItemMeta();
		meta.setDisplayName("§4-10");
		r10.setItemMeta(meta);
		inv.setItem(2,r10);
		ItemStack r1 = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)6);
		meta = r1.getItemMeta();
		meta.setDisplayName("§c-1");
		r1.setItemMeta(meta);
		inv.setItem(3,r1);
		inv.setItem(4,getRequestItem(type,(byte)1,data));
		ItemStack a1 = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)3);
		meta = a1.getItemMeta();
		meta.setDisplayName("§9+1");
		a1.setItemMeta(meta);
		inv.setItem(5,a1);
		ItemStack a10 = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)11);
		meta = a10.getItemMeta();
		meta.setDisplayName("§1+10");
		a10.setItemMeta(meta);
		inv.setItem(6,a10);
		inv.setItem(8,backToCategories);
		requestInvs.add(inv);
		return inv;
	}
	
	private static ItemStack getRequestItem(Material m, byte amount, short damage){
		ItemStack is = new ItemStack(m,amount,damage);
		ArrayList<String> lore = new ArrayList<String>();
		try {
			lore.add("§6Cost: §c$"+String.format("%.2f",ItemData.getItemCost(is)*amount));
			lore.add("§bClick to confirm.");
		} catch (NoValueSpecifiedException ex) {
			lore.add("§6Cost: §c$§k1337");
		}
		ItemMeta meta = is.getItemMeta();
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}
	
	private static String createFittingInvTitle(String original){
		if(original.length()>32)
			return original.substring(0,29)+"...";
		return original;
	}
	
}
