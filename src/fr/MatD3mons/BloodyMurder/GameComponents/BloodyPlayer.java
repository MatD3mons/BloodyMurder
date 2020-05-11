package fr.MatD3mons.BloodyMurder.GameComponents;

import fr.MatD3mons.BloodyMurder.BloodyMurder;
import fr.MatD3mons.BloodyMurder.Game.Game;
import fr.MatD3mons.BloodyMurder.Game.Role;
import fr.MatD3mons.BloodyMurder.Game.Roles;
import org.bukkit.entity.Player;

public class BloodyPlayer {
    public Player playerInstance;
    private Game game = null;
    private Roles role = null;
    private int Totaltekill;
    private int gold;
    private int argent;
    private int kills;
    private int deaths;
    private int win;
    private int lose;
    private String grade;

    public void setRole(Roles r) {
        role = r;
    }

    public Roles getRole() {
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

    public void update(Roles r){
        Totaltekill += kills;
        argent += kills * 25 + gold;
        gold = 0;
        kills = 0;
        if (role != null) {
            if (r == role) {
                win += 1;
                argent += 100;
            } else {
                if (r != null)
                    lose += 1;
            }
        }
        BloodyMurder.bloodyPlayerDao.update(playerInstance.getUniqueId(),this);
        setRole(null);
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

    public void setStatut(int kills, int argent, int deaths, int win, int lose, String grade) {
        this.Totaltekill = kills;
        this.argent = argent;
        this.deaths = deaths;
        this.win = win;
        this.lose = lose;
        this.grade = grade;
    }
}
