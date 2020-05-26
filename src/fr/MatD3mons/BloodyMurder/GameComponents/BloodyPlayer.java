package fr.MatD3mons.BloodyMurder.GameComponents;

import fr.MatD3mons.BloodyMurder.Game.Game;
import fr.MatD3mons.BloodyMurder.Game.Roles;
import fr.MatD3mons.BloodyMurder.gui.Gui;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import org.bukkit.entity.Player;

public class BloodyPlayer{
    public Player playerInstance;
    private Game game = null;
    private Roles role = null;
    private int Totaltekill;
    private int gold;

    public BloodyPlayer() { }

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

    public String getGrade() { return grade; }

    private int argent;
    private int kills;
    private int deaths;
    private int win;
    private int lose;
    private String grade;
    private Gui currentGui;

    public static void registerPlayer(Player connectedPlayer, BloodyPlayer bp) {
        Repository.add(connectedPlayer, bp);
    }

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

    public void setStatut(int Totalekills, int argent, int win, int lose, String grade) {
        this.Totaltekill = Totalekills;
        this.argent = argent;
        this.win = win;
        this.lose = lose;
        this.grade = grade;
    }

    public void setCurrentGui(Gui gui) {
        if(gui != null) {
            gui.open(playerInstance);
            this.currentGui = gui;
        }
        else {
            this.currentGui = null;
        }
    }

    public Gui getCurrentGui() {
        return currentGui;
    }

}
