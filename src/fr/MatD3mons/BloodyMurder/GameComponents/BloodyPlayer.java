package fr.MatD3mons.BloodyMurder.GameComponents;

import fr.MatD3mons.BloodyMurder.BloodyMurder;
import fr.MatD3mons.BloodyMurder.Game.Game;
import fr.MatD3mons.BloodyMurder.Game.role;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BloodyPlayer {
    public Player playerInstance;
    private Game game = null;
    private role role = null;
    private int Totaltekill;
    private int gold;
    private int argent;
    private int kills;
    private int deaths;
    private int win;
    private int lose;


    public void setRole(role r) {
        role = r;
    }

    public role getRole() {
        return role;
    }

    public void addGold(){ gold += 1;}
    public int getGold(){
        return gold;
    }

    public int getArgent() {
        return argent;
    }

    public void addkills(){ kills += 1;}
    public void removekills(){ kills -= 1;}
    public int getKills() {
        return kills;
    }

    public void adddeaths(){ deaths += 1;}
    public int getDeaths() {
        return deaths;
    }

    public int getWin() {
        return win;
    }

    public int getLose() {
        return lose;
    }

    public BloodyPlayer(Player p){
        playerInstance = p;
        Totaltekill = 0;
        gold = 0;
        argent = 0;
        kills = 0;
        deaths = 0;
        win = 0;
        lose = 0;
    }

    public void statue(role r){
        if(r != null) {
            Totaltekill += kills;
            kills = 0;
            gold = 0;
            argent += kills * 25;
            if (r.equals(role))
                win += 1;
            else
                lose += 1;
        }
    }

    public Player getPlayerInstance() {
        return playerInstance;
    }

    public int getTotaltekill(){
        return Totaltekill;
    }

    public void setPlayerInstance(Player playerInstance) {
        this.playerInstance = playerInstance;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setGold(int i) {
        this.gold = i;
    }
}