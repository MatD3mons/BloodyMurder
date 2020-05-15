package fr.MatD3mons.BloodyMurder.Game;

import fr.MatD3mons.BloodyMurder.BloodyMurder;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import fr.MatD3mons.BloodyMurder.utile.util;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;

public class GameManager {

    public static HashMap<String,Game> games;
    public static Location lobby;

    public GameManager(){
        games = new HashMap<>();

        ConfigurationSection conf_lobby = BloodyMurder.getInstance().getConfig().getConfigurationSection("lobby");
        if (conf_lobby == null){
            conf_lobby = BloodyMurder.getInstance().getConfig().createSection("lobby");
            conf_lobby.set("spawn","0,80,0,world");
        }
        lobby = util.returnLocation(conf_lobby.getString("spawn"));

        ConfigurationSection conf_games = BloodyMurder.getInstance().getConfig().getConfigurationSection("games");
        if (conf_games == null)
            conf_games = BloodyMurder.getInstance().getConfig().createSection("games");
        for(String map: conf_games.getKeys(false)){
            games.put(map,new Game(map));
        }

        BloodyMurder.instance.saveConfig();
    }

    public static void addgame(String name){
        ConfigurationSection conf_games = BloodyMurder.getInstance().getConfig().getConfigurationSection("games");
        ConfigurationSection conf_game = conf_games.createSection(name);
        conf_game.set("lobby",new ArrayList<>());
        conf_game.set("spawn",new ArrayList<>());
        conf_game.set("or",new ArrayList<>());
        conf_game.set("roles",new ArrayList<>());
        BloodyMurder.instance.saveConfig();
        games.put(name,new Game(name));
    }

    public static void rejoind(BloodyPlayer b) {
        if (b.getGame() != null) {
            b.getPlayerInstance().sendMessage("§a§lVous être déja en game");
            return;
        }
        ArrayList<Game> random = util.randomlist(new ArrayList<>(games.values()));
        //=== On check si aucune game attend des joueur
        for (Game g : random) {
            if (g.getMode() == Game.GameMode.WAITING && g.getSixe() <= g.getLimite()) {
                g.rejoind(b);
                return;
            }
        }
        //=== On check en random si y'a une game libre
        for(Game g: random){
            if(g.getMode() == Game.GameMode.DISABLE){
                if(g.spawn != null){
                    g.rejoind(b);
                    return;
                }
                util.sendActionBar(b.getPlayerInstance(),"Le lobby est mal définie");
            }
        }
        //=== plus de place, on l'ajoute random au parti en cours
        for(Game g: random){
            if (g.getSixe() <= g.getLimite()) {
                g.rejoind(b);
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
