package fr.MatD3mons.BloodyMurder.Game;

import fr.MatD3mons.BloodyMurder.BloodyMurder;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import fr.MatD3mons.BloodyMurder.utile.util;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class GameManager {

    public static HashMap<String,Game> games;
    public static Location spawn;

    public GameManager(){
        games = new HashMap<>();
        spawn = util.setlocation("lobby");

        for(String string: BloodyMurder.instance.getConfig().getConfigurationSection("games").getKeys(false)){
            System.out.println(string);
            String name = string;
            Location l = util.setlocation("games",string);
            games.put(name,new Game(name,l));
        }
    }

    public static void addgame(String name){
        Set<String> list = BloodyMurder.instance.getConfig().getConfigurationSection("games").getKeys(false);
        list.add(name);
        BloodyMurder.instance.getConfig().set("games", list);
        BloodyMurder.instance.saveConfig();
    }

    public static void rejoind(BloodyPlayer b) {
        if (b.getGame() != null) {
            b.getPlayerInstance().sendMessage("§a§lVous être déja en game");
            return;
        }
        //=== On check si aucune game attend des joueur
        for (Game gametest : games.values()) {
            if (gametest.getMode() == Game.GameMode.WAITING && gametest.getSixe() <= gametest.getLimite()) {
                gametest.rejoind(b);
                return;
            }
        }
        //=== On check en random si y'a une game libre
        ArrayList<Game> random = util.randomlist(new ArrayList<>(games.values()));
        for (int i = 0; i < games.size(); i++) {
            ArrayList<Game> gametest = new ArrayList<>();
            for (String s : games.keySet())
                gametest.add(games.get(s));
            if (gametest.get(i).getMode() == Game.GameMode.DISABLE) {
                gametest.get(i).rejoind(b);
                return;
            }
        }
        //=== plus de place, on l'ajoute random au parti en cours
        for (Game gametest : games.values()) {
            if (gametest.getSixe() <= gametest.getLimite()) {
                gametest.rejoind(b);
                return;
            }
        }
    }

    public static void leave(BloodyPlayer b){
        b.getPlayerInstance().getInventory().clear();
        if(b.getGame() != null)
            b.getGame().leave(b);
        b.getPlayerInstance().teleport(GameManager.spawn);
    }

    public static void setor(BloodyPlayer b,String monde) {
        if(games.containsKey(monde)){
            games.get(monde).setor(b.playerInstance);
        }
    }
}
