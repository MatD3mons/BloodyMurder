package fr.MatD3mons.BloodyMurder.utile;

import fr.MatD3mons.BloodyMurder.BloodyMurder;
import fr.MatD3mons.BloodyMurder.Game.Game;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class util {

	public static void sendTitle(Game game,String title, String subtitle, int fadein, int say, int fadeout){
		for (BloodyPlayer b : game.getPlayerInGame())
			sendTitle(b.getPlayerInstance(),title,subtitle,fadein,say,fadeout);
	}

	public static void sendTitle(BloodyPlayer b, String title, String subtitle, int fadein, int say, int fadeout){
		sendTitle(b.getPlayerInstance(),title,subtitle,fadein,say,fadeout);
	}

	public static void sendActionBar(Game game, String message) {
		for (BloodyPlayer b : game.getPlayerInGame())
			sendActionBar(b.getPlayerInstance(),message);
	}
	public static void sendActionBar(Player p, String message) {
		IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
		PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
	}

    public static void sendTitle (Player player, String title, String subtitle, int fadein, int say, int fadeout) {
    	IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
    	IChatBaseComponent chatsubtitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");

    	PacketPlayOutTitle t = new PacketPlayOutTitle (EnumTitleAction.TITLE, chatTitle);
    	PacketPlayOutTitle s = new PacketPlayOutTitle (EnumTitleAction.SUBTITLE, chatsubtitle);
    	PacketPlayOutTitle length = new PacketPlayOutTitle (fadein * 20, say * 20, fadeout * 20);

    	((CraftPlayer) player) .getHandle (). playerConnection.sendPacket (t);
    	((CraftPlayer) player) .getHandle (). playerConnection.sendPacket (s);
    	((CraftPlayer) player) .getHandle (). playerConnection.sendPacket (length);
    	}

	public static ItemStack create(Material material, int amount, ChatColor color, String name)
	{
		ItemStack item = new ItemStack(material, amount);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(color+name);
		item.setItemMeta(itemMeta);
		return item;
	}

	public static <T> ArrayList<T> randomlist(ArrayList<T> list){
		ArrayList<T> new_list = new ArrayList<>();
		Collections.copy(list,new_list);
		Collections.shuffle(new_list);
		return new_list;
	}

	public static Location returnLocation(String s){
		String[] split = s.split(",");
		int x = Integer.parseInt(split[0]);
		int y = Integer.parseInt(split[1]);
		int z = Integer.parseInt(split[2]);
		World w = Bukkit.getWorld(split[3]);
		return new Location(w,x,y,z);
	}

	public static ArrayList<Location> returnLocation(List<String> stringList) {
		ArrayList<Location> list = new ArrayList<>();
		for (String s: stringList)
			list.add(returnLocation(s));
		return list;
	}
}
