package fr.MatD3mons.BloodyMurder.utile;

import fr.MatD3mons.BloodyMurder.BloodyMurder;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scoreboard.*;
import org.bukkit.scoreboard.Scoreboard;
import org.inventivetalent.nicknamer.NickNamerPlugin;
import org.inventivetalent.nicknamer.api.NickManager;
import org.inventivetalent.nicknamer.api.NickNamerAPI;

import java.util.*;

public class util {

	public static void sendTitle(BloodyPlayer b, String title, String subtitle, int fadein, int say, int fadeout){
		sendTitle(b.getPlayerInstance(),title,subtitle,fadein,say,fadeout);
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

	public static ArrayList<Integer> randomlist(int x){
		ArrayList<Integer> numbers = new ArrayList();
		for(int i = 0; i < x; i++)
		{
			numbers.add(i);
		}
		Collections.shuffle(numbers);
		return numbers;
	}

    public static Location setlocation(String s) {
		World w = Bukkit.getWorld(BloodyMurder.instance.getConfig().getString(s+".world"));
		int x = BloodyMurder.instance.getConfig().getInt( s+".x");
		int y = BloodyMurder.instance.getConfig().getInt( s+".y");
		int z = BloodyMurder.instance.getConfig().getInt(s+".z");
		return new Location(w,x,y,z);
    }

    public static Location setlocation(String chemin, String s){
		World w = Bukkit.getWorld(BloodyMurder.instance.getConfig().getConfigurationSection(chemin).getString(s+".world"));
		int x = BloodyMurder.instance.getConfig().getConfigurationSection(chemin).getInt( s+".x");
		int y = BloodyMurder.instance.getConfig().getConfigurationSection(chemin).getInt( s+".y");
		int z = BloodyMurder.instance.getConfig().getConfigurationSection(chemin).getInt(s+".z");
		return new Location(w,x,y,z);
	}
}
