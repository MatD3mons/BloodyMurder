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
    public static Location lobby;

    public GameManager(){
        games = new HashMap<>();
        lobby = util.returnLocation(BloodyMurder.getInstance().getConfig().getString("lobby"));
        for(String map: BloodyMurder.instance.getConfig().getConfigurationSection("games").getKeys(false)){
            games.put(map,new Game(map));
        }
    }

    public static void addgame(String name){
        Set<String> list = BloodyMurder.instance.getConfig().getConfigurationSection("games").getKeys(false);
        list.add(name);
        //TODO ajouter le lobby directement a la création
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
        util.sendActionBar(b.getPlayerInstance(),"§a§lAucune parti disponible");
    }

    public static void leave(BloodyPlayer b){
        b.getPlayerInstance().getInventory().clear();
        if(b.getGame() != null)
            b.getGame().leave(b);
        b.getPlayerInstance().teleport(GameManager.lobby);
    }
}
