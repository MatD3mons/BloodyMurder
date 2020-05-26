package fr.MatD3mons.BloodyMurder.Game;

import fr.MatD3mons.BloodyMurder.BloodyMurder;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import fr.MatD3mons.BloodyMurder.GameComponents.Or;
import fr.MatD3mons.BloodyMurder.utile.util;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

public class Game {

    private Game instance;
    private BukkitTask bukkitRunnable;
    private ArrayList<BloodyPlayer> playerInGame;
    private ArrayList<BloodyPlayer> innocentleft;
    private ArrayList<BloodyPlayer> murderleft;
    private ArrayList<Or> listor;
    private ArrayList<Location> listspawn;
    private ArrayList<Roles> listrole;
    private HashMap<Player,Team> listTeam;
    private Scoreboard scoreboard;
    private GameMode mode;
    private Roles role;
    private int limite;
    private int timer;
    private String name;
    public Location spawn;

    public ArrayList<BloodyPlayer> getPlayerInGame() {
        return playerInGame;
    }

    public void addor(Player p) {
        Location l = p.getLocation();
        p.sendMessage("§eNouveau spawn d'or définie en: "+util.location(l));
        listor.add(new Or(p.getLocation().clone()));
        ArrayList<String > list = util.location(Or.getlist(listor));
        BloodyMurder.instance.getConfig().set("games."+name+".or", list);
        BloodyMurder.instance.saveConfig();
    }

    public void addspawn(Player player) {
        Location l = player.getLocation();
        player.sendMessage("§eNouveau spawn définie en: "+util.location(l));

        listspawn.add(player.getLocation().clone());
        List<String > list = util.location(listspawn);
        BloodyMurder.instance.getConfig().set("games."+name+".spawn", list);
        BloodyMurder.instance.saveConfig();

        addRoles();
    }

    public void addRoles(){
        if(listrole.size() == 0)
            listrole.add(Roles.Murder);
        else
            listrole.add(Roles.Innocent);
        List<String> listR = util.tolistString(listrole);
        BloodyMurder.instance.getConfig().set("games."+name+".roles", listR);
        BloodyMurder.instance.saveConfig();
    }

    public void setLobby(Player player) {
        Location l = player.getLocation();
        ArrayList<String> s = new ArrayList<>(Collections.singleton(util.location(l)));
        player.sendMessage("§eNouveau lobby définie en: "+s.get(0));
        listspawn.add(player.getLocation());
        BloodyMurder.instance.getConfig().set("games."+name+".lobby", s);
        BloodyMurder.instance.saveConfig();
    }

    public enum GameMode {
        DISABLE,WAITING, GAME, END
    }

    public Game(String name){
        this.instance = this;
        playerInGame = new ArrayList<>();
        innocentleft = new ArrayList<>();
        murderleft = new ArrayList<>();
        listor = new ArrayList<>();
        listspawn = new ArrayList<>();
        listrole = new ArrayList<>();
        listTeam = new HashMap<>();
        limite = 8;
        this.name = name;
        role = null;
        setDisable();

        if(!BloodyMurder.instance.getConfig().getStringList("games."+name+".or").isEmpty())
            for(Location l: util.returnLocation(BloodyMurder.instance.getConfig().getStringList("games."+name+".or")))
                listor.add(new Or(l));

        if(!BloodyMurder.instance.getConfig().getStringList("games."+name+".spawn").isEmpty())
            listspawn = util.returnLocation(BloodyMurder.instance.getConfig().getStringList("games."+name+".spawn"));

        if(!BloodyMurder.instance.getConfig().getStringList("games."+name+".lobby").isEmpty())
            spawn = util.returnLocation(BloodyMurder.instance.getConfig().getStringList("games."+name+".lobby").get(0));

        if(!BloodyMurder.instance.getConfig().getStringList("games."+name+".roles").isEmpty())
            listrole = util.returnRoles(BloodyMurder.instance.getConfig().getStringList("games."+name+".roles"));

        if(listspawn.size() > listrole.size())
            for(int i = listrole.size(); i < listspawn.size();i++){
                addRoles();
            }
    }

    public ArrayList<Roles> getlistrole(){
        return listrole;
    }

    @Deprecated
    public void setTeam(Player p) {
        scoreboard = p.getScoreboard();
        if(p.getScoreboard().getTeam("invisible") == null) {
            Team invisible = scoreboard.registerNewTeam("invisible");
            invisible.setNameTagVisibility(NameTagVisibility.NEVER);
            invisible.setCanSeeFriendlyInvisibles(false);
            listTeam.put(p,invisible);
            for (BloodyPlayer b : playerInGame) {
                invisible.addPlayer(b.getPlayerInstance());
            }
        }
    }

    @Deprecated
    public void resetTeam(Player p){
        if(listTeam.containsKey(p)) {
            if (listTeam.get(p).hasPlayer(p))
                listTeam.get(p).removePlayer(p);
            listTeam.get(p).unregister();
            listTeam.remove(p);
        }
    }

    public String getName() {
        return name;
    }

    private void Message(String s){
        for (BloodyPlayer b : playerInGame) {
                b.getPlayerInstance().sendMessage(s);
        }
    }

    public GameMode getMode(){
        return mode;
    }

    public int getSixe(){
        return playerInGame.size();
    }

    public int getLimite(){
        return  limite;
    }

    public void rejoind(BloodyPlayer b) {
        if (getSixe() > getLimite()) {
            util.sendTitle(b, " ", "§a§lPas assez de place dans la game", 0, 3, 0);
            return;
        }
        if (!playerInGame.contains(b)) {
            playerInGame.add(b);
            b.setGame(this);
            Message("§d§l"+b.getPlayerInstance().getName() + "§a§l a rejoint la partie. §8(§7" + playerInGame.size() + "§8/§7" + limite + "§8) ");
        }
        Player p = b.playerInstance;
        p.setGameMode(org.bukkit.GameMode.ADVENTURE);
        p.teleport(this.spawn);
        ItemStack itemStack = util.create(Material.BED, 1, ChatColor.RED, "Lobby");
        b.getPlayerInstance().getInventory().setItem(8, itemStack);
        if (getMode() == GameMode.DISABLE) {
            setWait();
            util.sendTitle(b, "Map : "+name, "§a§lVous avez démarré la partie", 0, 3, 0);
        } else if (getMode() == GameMode.WAITING) {
            util.sendTitle(b, "Map : "+name, "§a§lVous avez rejoint la partie", 0, 3, 0);
        } else {
            p.setGameMode(org.bukkit.GameMode.SPECTATOR);
            util.sendTitle(b, "Map : "+name, "§a§lVous avec rejoint en mode spectateur", 0, 3, 0);
        }
    }

    public void leave(BloodyPlayer b){
        if(b.getGame().mode == GameMode.GAME) {
            if (b.getRole() != null) {
                if (!(b.getRole() == Roles.Murder))
                    supMurder(b);
                else
                    supInnocent(b);
                if (murderleft.size() == 0) {
                    setEnd(Roles.Innocent);
                }
                if (innocentleft.size() == 0) {
                    setEnd(Roles.Murder);
                }
                resetTeam(b.getPlayerInstance());
            }
        }
        b.update(role);
        playerInGame.remove(b);
        b.setGame(null);
        Message(b.getPlayerInstance().getName()+" a quitter la partie ("+playerInGame.size()+"/"+limite+") ");
        util.sendTitle(b, " ", "§a§lVous quiter la partie", 0, 3, 0);
        b.getPlayerInstance().teleport(GameManager.lobby);
        if(playerInGame.size() == 0)
            setDisable();
    }

    public void setDisable(){
        playerInGame.clear();
        innocentleft.clear();
        murderleft.clear();
        for (Team t: listTeam.values()){
            t.unregister();
        }
        listTeam.clear();
        mode = GameMode.DISABLE;
        if(bukkitRunnable != null)
            bukkitRunnable.cancel();
    }

    public void supInnocent(BloodyPlayer b){
        if(innocentleft.contains(b))
            innocentleft.remove(b);
    }

    public void supMurder(BloodyPlayer b){
        if(murderleft.contains(b))
            murderleft.remove(b);
    }

    public int innocentleftsixe(){
        return innocentleft.size();
    }

    public int murderleftsixe(){
        return murderleft.size();
    }

    public int getTime(){
        return timer;
    }

    public void setWait() {
        if (mode != GameMode.WAITING) {
            mode = GameMode.WAITING;
            innocentleft.clear();
            role = null;
            murderleft.clear();
            timer = 20;
            for (BloodyPlayer b : playerInGame) {
                b.getPlayerInstance().setGameMode(org.bukkit.GameMode.ADVENTURE);
                ItemStack itemStack = util.create(Material.BED,1,ChatColor.RED,"Lobby");
                b.getPlayerInstance().getInventory().setItem(8,itemStack);
            }
            bukkitRunnable =
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (timer == 5)
                        for (BloodyPlayer b : playerInGame) {
                            util.sendTitle(b, "§a§l5", " ", 0, 1, 0);
                            b.getPlayerInstance().playSound(b.getPlayerInstance().getLocation(), Sound.CLICK, 2F, 1F);
                        }
                    else if (timer == 4)
                        for (BloodyPlayer b : playerInGame) {
                            util.sendTitle(b, "§a§l4", " ", 0, 1, 0);
                            b.getPlayerInstance().playSound(b.getPlayerInstance().getLocation(), Sound.CLICK, 2F, 1F);
                        }
                    else if (timer == 3)
                        for (BloodyPlayer b : playerInGame) {
                            util.sendTitle(b, "§c§l3", " ", 0, 1, 0);
                            b.getPlayerInstance().playSound(b.getPlayerInstance().getLocation(), Sound.CLICK, 2F, 1F);
                        }
                    else if (timer == 2)
                        for (BloodyPlayer b : playerInGame) {
                            util.sendTitle(b, "§c§l2", " ", 0, 1, 0);
                            b.getPlayerInstance().playSound(b.getPlayerInstance().getLocation(), Sound.CLICK, 2F, 1F);
                        }
                    else if (timer == 1)
                        for (BloodyPlayer b : playerInGame) {
                            util.sendTitle(b, "§4§l1", " ", 0, 1, 0);
                            b.getPlayerInstance().playSound(b.getPlayerInstance().getLocation(), Sound.CLICK, 2F, 1F);
                        }
                    if (timer < 0) {
                        if (playerInGame.size() >= 1) {
                            util.sendActionBar(instance,"§a§lLa partie commence, bonne chance !");
                            setGame();
                            cancel();
                        } else {
                            Message("§cPas assez de joueurs pour démarrer.");
                            timer = 20;
                        }
                    } else {
                        if (mode == GameMode.WAITING) {
                            if (playerInGame.size() >= 1) {//TODO mettre +
                                timer--;
                            }
                            else if(playerInGame.size() == 0){
                                setDisable();
                                cancel();
                            }else{
                                timer = 20;
                            }
                        } else {
                            cancel();
                        }
                    }
                }
            }.runTaskTimer(BloodyMurder.instance, 0, 19);
        }
    }

    public void setGame() {
        if (mode != GameMode.GAME) {
            mode = GameMode.GAME;
            ArrayList<BloodyPlayer> random = util.randomlist(playerInGame);
            ArrayList<Location> randomTP = util.randomlist(util.returnLocation(BloodyMurder.instance.getConfig().getStringList("games."+name+".spawn")));
            for (int i = 0; i < random.size(); i++) {
                BloodyPlayer b = random.get(i);
                b.setRole(listrole.get(i));
                if (listrole.get(i) == Roles.Murder)
                    murderleft.add(b);
                else
                    innocentleft.add(b);
                b.getPlayerInstance().teleport(randomTP.get(i));
                b.getPlayerInstance().getInventory().clear();
                Roles.PlayerRoles.get(b.getRole()).message(b);
            }

            timer = 120;
            bukkitRunnable =
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (timer == 115) {
                        for (BloodyPlayer b : playerInGame) {
                            Roles.PlayerRoles.get(b.getRole()).stuff(b);
                        }
                    }
                    if (timer % 2 == 0) {
                        for(Or or: listor){
                            or.setfree();
                        }
                    }
                    if (timer < 0) {
                        setEnd(Roles.Innocent);
                        cancel();
                    } else {
                        if (mode == GameMode.GAME) {
                            timer--;
                        } else {
                            cancel();
                        }
                    }
                }
            }.runTaskTimer(BloodyMurder.instance, 0, 19);
        }
    }

    public void setEnd(Roles r) {
        if (mode != GameMode.END) {
            mode = GameMode.END;
            this.role = r;
            for (Entity e : spawn.getWorld().getEntities()) {
                if (!(e instanceof Player))
                    e.remove();
            }
            for (BloodyPlayer b : playerInGame) {
                Roles.PlayerRoles.get(r).fin(b);
                resetTeam(b.getPlayerInstance());
                ItemStack itemStack = util.create(Material.BED,1,ChatColor.RED,"Lobby");
                b.getPlayerInstance().getInventory().setItem(8,itemStack);
            }
            timer = 10;
            bukkitRunnable =
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (timer == 9) {
                        for (BloodyPlayer b : playerInGame) {
                            b.getPlayerInstance().setGameMode(org.bukkit.GameMode.ADVENTURE);
                        }
                    }
                    if (timer < 0) {
                        if (playerInGame.size() > 0) {
                            for (BloodyPlayer b : playerInGame) {
                                b.update(r);
                                setWait();
                                rejoind(b);
                            }
                        } else {
                            setDisable();
                        }
                        cancel();
                    } else {
                        if (mode == GameMode.END) {
                            timer--;
                        } else {
                            cancel();
                        }
                    }
                }
            }.runTaskTimer(BloodyMurder.instance, 0, 19);
        }
    }

}
