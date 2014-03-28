package nl.arfie.bukkit.survivalimprovements;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import static com.comphenix.example.Attributes.Operation;
import static com.comphenix.example.Attributes.AttributeType;

import com.comphenix.example.Attributes;

public class SurvivalImprovements extends JavaPlugin {
	
	private static SurvivalImprovements inst;

	public void onEnable(){
		Bukkit.getPluginManager().registerEvents(new SIListeners(),this);
		inst=this;
		Config.loadFromFile();
		PlayerStats.load(PlayerStats.file);
	}
	
	public void onDisable(){
		GraveStone.breakAll();
		PlayerStats.save(PlayerStats.file);
	}
	
	public static SurvivalImprovements instance(){
		return inst;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		if(command.getName().equalsIgnoreCase("boss") && sender.hasPermission("survivalimprovements.spawnbosses")){
			if(sender instanceof Player){
				try{
					Boss.create(((Player)sender).getLocation(),Integer.parseInt(args[1])-1,Boss.Type.valueOf(args[0].toUpperCase()));
				}catch(IllegalArgumentException ex){
					sender.sendMessage("§c"+ex.getMessage());
				}
				return true;
			}
			return false;
		} else if(command.getName().equalsIgnoreCase("sireload")&&sender.hasPermission("survivalimprovements.reload")){
			Config.loadFromFile();
			PlayerStats.load(PlayerStats.file);
			sender.sendMessage(Config.MESSAGE_RELOAD);
		} else if(command.getName().equalsIgnoreCase("sistats")&&sender.hasPermission("survivalimprovements.stats")){
			if(args.length>0)
				sender.sendMessage(PlayerStats.statsFor(args[0]).toString());
			else
				sender.sendMessage(PlayerStats.statsFor(sender.getName()).toString());
		} else if(command.getName().equalsIgnoreCase("attribute")&&sender.hasPermission("survivalimprovements.attribute")){
			if(sender instanceof Player){
				Player p = (Player) sender;
				ItemStack is=p.getItemInHand();
				if(is==null){
					sender.sendMessage("Please hold an item in your hand before doing that.");
					return true;
				}
				Operation op = Operation.ADD_NUMBER;
				if(args.length>=3)
					op=Operation.valueOf(args[2].toUpperCase());
				if(args.length>=2){
					AttributeType type = AttributeType.fromId("generic."+args[0]);
					try{
						if(op==null){
							StringBuffer buff = new StringBuffer();
							for(Operation operation : Operation.values())
								buff.append(operation.toString()+", ");
							sender.sendMessage("§c"+buff.toString());
							return true;
						}
						if(type==null){
							StringBuffer buff = new StringBuffer();
							for(AttributeType t : AttributeType.values())
								buff.append(t.getMinecraftId().substring("generic.".length())+", ");
							sender.sendMessage("§c"+buff.toString());
							return true;
						}
						int level = Integer.parseInt(args[1]);
						Attributes attr = new Attributes(is);
						String name="Attribute";
						if(args.length>=4){
							StringBuffer buff = new StringBuffer();
							for(int i=3; i<args.length; ++i)
								buff.append(args[i]+" ");
							name=buff.toString();
						}
						attr.add(Attributes.Attribute.newBuilder().name(name).type(type).amount(level).operation(op).build());
						p.setItemInHand(attr.getStack());
					}catch(NumberFormatException ex){
						sender.sendMessage("§cNot a valid number.");
					}
				}
			}
		}
		return false;
	}
	
}
