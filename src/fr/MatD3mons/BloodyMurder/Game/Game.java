package fr.MatD3mons.BloodyMurder.Game;

import fr.MatD3mons.BloodyMurder.BloodyMurder;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import fr.MatD3mons.BloodyMurder.ScoreBoard.ScoreBoardDisplayer;
import fr.MatD3mons.BloodyMurder.ScoreBoard.ScoreboardUtil;
import fr.MatD3mons.BloodyMurder.ScoreBoard.ScoreboardWrapper;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import fr.MatD3mons.BloodyMurder.utile.util;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Game {

    private ArrayList<BloodyPlayer> playerInGame;
    private ArrayList<BloodyPlayer> innocentleft;
    private ArrayList<BloodyPlayer> murderleft;
    private ArrayList<Location> listor;
    private ArrayList<role> listrole;
    private HashMap<Player,Team> listTeam;
    private Scoreboard scoreboard;
    private GameMode mode;
    private role role;
    private int limite;
    private int timer;
    private String name;
    public Location spawn;
    public World world;

    public void setor(Player p) {
        Location l = p.getLocation();
        List<String> list;
        if(BloodyMurder.instance.getConfig().getStringList("games."+name+".or") != null)
            list = BloodyMurder.instance.getConfig().getStringList("games."+name+".or");
        else
            list = new ArrayList<>();
        String s = (int)l.getX()+","+(int)(l.getY()+1)+","+(int)l.getZ();
        list.add(s);
        p.sendMessage("§eNouveau spawn d'or définie en: "+s);
        String[] cordonee = s.split(",");
        addLocationor(cordonee);
        BloodyMurder.instance.getConfig().set("games."+name+".or", list);
        BloodyMurder.instance.saveConfig();
    }

    public static enum GameMode {
        DISABLE,WAITING, GAME, END
    }

    public Game(String name,Location location){
        playerInGame = new ArrayList<>();
        innocentleft = new ArrayList<>();
        murderleft = new ArrayList<>();
        listor = new ArrayList<>();
        listrole = new ArrayList<>();
        listTeam = new HashMap<>();
        limite = 16;
        this.name = name;
        role = null;
        setDisable();
        spawn = location;;
        world = location.getWorld();
        for(String string: BloodyMurder.instance.getConfig().getStringList("games."+name+".or")){
            String[] cordonee = string.split(",");
            addLocationor(cordonee);
        }
    }

    public void addLocationor(String[] s){
        int x = Integer.parseInt(s[0]);
        int y = Integer.parseInt(s[1]);
        int z = Integer.parseInt(s[2]);
        listor.add(new Location(world,x,y,z));
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

    public void rejoind(BloodyPlayer b){
        if(getSixe() < getLimite()) {
            if(!playerInGame.contains(b)) {
                playerInGame.add(b);

                b.setGame(this);
            Message(b.getPlayerInstance().getName()+" a rejoint la partie ("+playerInGame.size()+"/"+limite+") ");
            }
            Player p = b.playerInstance;
            p.setGameMode(org.bukkit.GameMode.ADVENTURE);
            p.teleport(this.spawn);
            ItemStack itemStack = util.create(Material.BED,1,ChatColor.RED,"Lobby");
            b.getPlayerInstance().getInventory().setItem(8,itemStack);
            if(getMode() == GameMode.DISABLE) {
                setWait();
                util.sendTitle(b, " ", "§a§lVous avez démarré la partie", 0, 3, 0);
            }
            else if(getMode() == GameMode.WAITING){
                util.sendTitle(b, " ", "§a§lVous avez rejoint la partie", 0, 3, 0);
            }
            else{
                p.setGameMode(org.bukkit.GameMode.SPECTATOR);
                util.sendTitle(b, " ", "§a§lVous avec rejoint en mode spectateur", 0, 3, 0);
            }
        }
        else{
            util.sendTitle(b, " ", "§a§lPas assez de place dans la game", 0, 3, 0);
        }
    }

    public void leave(BloodyPlayer b){
        if(b.getGame().mode == GameMode.GAME) {
            if (b.getRole() != null) {
                if (!b.getRole().getmechant())
                    supMurder(b);
                else
                    supInnocent(b);
                if (murderleft.size() == 0) {
                    setEnd(new Innocent());
                }
                if (innocentleft.size() == 0) {
                    setEnd(new Murder());
                }
                resetTeam(b.getPlayerInstance());
            }
        }
        b.update(role);
        playerInGame.remove(b);
        b.setGame(null);
        Message(b.getPlayerInstance().getName()+" a quitter la partie ("+playerInGame.size()+"/"+limite+") ");
        util.sendTitle(b, " ", "§a§lVous quiter la partie", 0, 3, 0);
        b.getPlayerInstance().teleport(GameManager.spawn);
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
            for (Entity e : world.getEntities()) {
                if (!(e instanceof Player)) {
                    e.remove();
                }
            }
            for (BloodyPlayer b : playerInGame) {
                b.getPlayerInstance().setGameMode(org.bukkit.GameMode.ADVENTURE);
                ItemStack itemStack = util.create(Material.BED,1,ChatColor.RED,"Lobby");
                b.getPlayerInstance().getInventory().setItem(8,itemStack);
            }
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
                            Message("§a§lLa partie commence, bonne chance !");
                            setGame();
                            cancel();
                        } else {
                            Message("§cPas assez de joueurs pour démarrer.");
                            timer = 20;
                        }
                    } else {
                        if (mode == GameMode.WAITING) {
                            if (playerInGame.size() >= 1) {
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
            listrole.clear();
            switch (playerInGame.size()) {
                case 1:
                    listrole.add(new Murder());
                    break;
                case 2:
                    listrole.add(new Murder());
                    listrole.add(new Innocent());
                    break;
                case 3:
                    listrole.add(new Murder());
                    listrole.add(new Innocent());
                    listrole.add(new Murder());
                    break;
                case 4:
                    listrole.add(new Murder());
                    listrole.add(new Murder());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    break;
                case 5:
                    listrole.add(new Murder());
                    listrole.add(new Murder());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    break;
                case 6:
                    listrole.add(new Murder());
                    listrole.add(new Murder());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    break;
                case 7:
                    listrole.add(new Murder());
                    listrole.add(new Murder());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    break;
                case 8:
                    listrole.add(new Murder());
                    listrole.add(new Murder());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    break;
                case 9:
                    listrole.add(new Murder());
                    listrole.add(new Murder());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    break;
                case 10:
                    listrole.add(new Murder());
                    listrole.add(new Murder());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    break;
                case 11:
                    listrole.add(new Murder());
                    listrole.add(new Murder());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    break;
                case 12:
                    listrole.add(new Murder());
                    listrole.add(new Murder());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    break;
                case 13:
                    listrole.add(new Murder());
                    listrole.add(new Murder());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    break;
                case 14:
                    listrole.add(new Murder());
                    listrole.add(new Murder());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    break;
                case 15:
                    listrole.add(new Murder());
                    listrole.add(new Murder());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    break;
                case 16:
                    listrole.add(new Murder());
                    listrole.add(new Murder());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    listrole.add(new Innocent());
                    break;
                default:
                    for (BloodyPlayer b : playerInGame) {
                        Message("Erreur merci de conctacter un admin");
                    }
                    break;
            }
            ArrayList<Integer> random = util.randomlist(playerInGame.size());
            for (int i = 0; i < playerInGame.size(); i++) {
                playerInGame.get(i).setRole(listrole.get(random.get(i)));
                if (listrole.get(random.get(i)).getmechant()) {
                    murderleft.add(playerInGame.get(i));
                } else {
                    innocentleft.add(playerInGame.get(i));
                }
            }
            for (BloodyPlayer b : playerInGame) {
                b.getRole().message(b);
                b.getPlayerInstance().getInventory().clear();
            }

            timer = 120;
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (timer == 115) {
                        for (BloodyPlayer b : playerInGame) {
                            b.getRole().stuff(b);
                        }
                    }
                    if (timer % 10 == 0) {
                        for (Location loc : listor) {
                            Boolean libre = true;
                            for (Entity e : world.getEntities())
                                if (e.getLocation().distance(loc) <= 5) {
                                    libre = false;
                                }
                            if (libre) {
                                world.dropItem(loc, new ItemStack(Material.GOLD_INGOT, 1));
                                world.playSound(loc, Sound.FIRE, 2F, 1F);
                            }
                        }
                    }
                    if (timer < 0) {
                        setEnd(new Innocent());
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

    public void setEnd(role r) {
        if (mode != GameMode.END) {
            mode = GameMode.END;
            this.role = r;
            for (Entity e : world.getEntities()) {
                if (!(e instanceof Player))
                    e.remove();
            }
            for (BloodyPlayer b : playerInGame) {
                r.fin(b);
                resetTeam(b.getPlayerInstance());
                ItemStack itemStack = util.create(Material.BED,1,ChatColor.RED,"Lobby");
                b.getPlayerInstance().getInventory().setItem(8,itemStack);
            }
            timer = 10;
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
