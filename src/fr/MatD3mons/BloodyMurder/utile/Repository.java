package fr.MatD3mons.BloodyMurder.utile;

import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Repository {
    public static HashMap<Player, BloodyPlayer> players = new HashMap<Player, BloodyPlayer>();

    public static BloodyPlayer findBloodyPlayer(Player p) {
        return players.get(p);
    }

    public static Boolean BloodyPlayerContains(Player p){ return players.containsKey(p);}

    public static void add(Player p, BloodyPlayer b){
        players.put(p,b);
    }

    public static void remove(Player player) {
        players.remove(player);
    }
}
