package fr.MatD3mons.BloodyMurder.utile;

import fr.MatD3mons.BloodyMurder.BloodyMurder;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Repository {
    public static HashMap<Player, BloodyPlayer> players = new HashMap<Player, BloodyPlayer>();

    public static BloodyPlayer findBloodyPlayer(Player p) {
        return players.get(p);
    }

    public static Boolean BloodyPlayerContains(Player p){ return players.containsKey(p);}

    public static void Update(){
        for (Player p: Bukkit.getServer().getOnlinePlayers()){
            add(p);
        }
    }

    public static void add(Player p){
        BloodyPlayer b = new BloodyPlayer(p);
        b = BloodyMurder.bloodyPlayerDao.create(p.getUniqueId());
        players.put(p,b);
    }

}
