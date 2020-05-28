package fr.MatD3mons.BloodyMurder.Game.roles;

import fr.MatD3mons.BloodyMurder.BloodyMurder;
import fr.MatD3mons.BloodyMurder.Game.Game;
import fr.MatD3mons.BloodyMurder.Game.Role;
import fr.MatD3mons.BloodyMurder.Game.Roles;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import fr.MatD3mons.BloodyMurder.GameComponents.Sword;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import fr.MatD3mons.BloodyMurder.utile.util;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class Murder extends Role {

    private int timer = 6;

    public Murder(){
        skull = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjdiZjAyMzkwZDNmM2Y0Y2NlNGJmZWRjM2MxOTA0ODQxMzhhMzE3NGQ4NTQxYThmZDkxMmViYjIxNDdmY2MwZSJ9fX1";
    }

    @Override
    public boolean EntityDamageByEntity(EntityDamageByEntityEvent e){
        if (!(e.getDamager() instanceof Player)){ return false;}
        Player killer = (Player) e.getDamager();

        if (Repository.findBloodyPlayer(killer).getGame() == null) {return false;}
        Game g = Repository.findBloodyPlayer(killer).getGame();

        if (g.getMode() != Game.GameMode.GAME) {return false;}
        Player p2 = (Player) e.getEntity();

        if (Repository.findBloodyPlayer(killer).getRole() != Roles.Murder) {return false; }

        if (killer.getItemInHand().getType() != Material.DIAMOND_SWORD) {return false;}

        if (killer.getInventory().contains(Material.REDSTONE)){
            p2.getInventory().clear();
            if (!(Repository.findBloodyPlayer(p2).getRole() == Roles.Murder)) {
                e.setDamage(100);
                Repository.findBloodyPlayer(killer).addkills();
            } else {
                util.sendActionBar(killer, "§c§lC'est ton ally dommage ):");
            }
            removeSoif(killer);
        }else{
            util.sendTitle(killer, "", "§c§lTu n'as pas de soif de sang", 0, 3, 0);
        }
        return false;
    }

    public  void  removeSoif(Player killer){
        for (int i = 0; i < killer.getInventory().getSize(); i++) {
            if (killer.getInventory().getItem(i) != null)
                if (killer.getInventory().getItem(i).getType() == Material.REDSTONE) {
                    int j = killer.getInventory().getItem(i).getAmount();
                    if (j == 1)
                        killer.getInventory().remove(Material.REDSTONE);
                    else
                        killer.getInventory().getItem(i).setAmount(j - 1);
                }
        }
    }

    @Override
    public void message(BloodyPlayer b){
        util.sendTitle(b.getPlayerInstance(), "§4§lTu es Murder !", "§cTu dois récupérer 2 piéce d'or pour Tuer une personne", 0, 3, 0);
    }

    @Override
    public void stuff(BloodyPlayer b){
        ItemStack itemStack = util.create(Material.DIAMOND_SWORD,1,ChatColor.AQUA,"§lEpée du §6§lMurder");
        b.getPlayerInstance().getInventory().setItem(0,itemStack);
    }

    @Override
    public void fin(BloodyPlayer b){
        b.getPlayerInstance().getInventory().clear();
        if(b.getGame().murderleftsixe() == 1)
            util.sendTitle(b.getGame(), "§c§l Le MURDER a gagné", "", 0, 3, 0);
        else
            util.sendTitle(b.getGame(), "§c§l Les MURDER ont gagné", "", 0, 3, 0);
    }

    @Override
    public String toString(){
        return "Murder";
    }

    @Override
    public void take(BloodyPlayer b){
        for(int i = 0; i < b.getPlayerInstance().getInventory().getSize(); i++) {
            if (b.getPlayerInstance().getInventory().getItem(i) == null) { continue; }
            if (b.getPlayerInstance().getInventory().getItem(i).getType() != Material.DOUBLE_PLANT) { continue; }
            int j = b.getPlayerInstance().getInventory().getItem(i).getAmount();
            if (j >= 2) {
                if (j == 2)
                    b.getPlayerInstance().getInventory().remove(Material.DOUBLE_PLANT);
                else
                    b.getPlayerInstance().getInventory().getItem(i).setAmount(j - 2);
                if (b.getPlayerInstance().getInventory().contains(Material.DOUBLE_PLANT)) {
                    ItemStack itemStack = util.create(Material.REDSTONE, 1, ChatColor.RED, "Soif de sang");
                    b.getPlayerInstance().getInventory().addItem(itemStack);
                } else {
                    ItemStack itemStack = util.create(Material.REDSTONE, 1, ChatColor.RED, "Soif de sang");
                    b.getPlayerInstance().getInventory().setItem(1, itemStack);
                }
                util.sendActionBar(b.getPlayerInstance(),"§a§l Tu as reçu 1 Soif de sang");
                //TODO ajouter des redstone ( il se stack pas )t
            }
        }
    }

    public void Interact(BloodyPlayer b){
        if(b.getPlayerInstance().getItemInHand().getType() != Material.DIAMOND_SWORD){return;}
        if(timer != 6){return;}
        timer--;
        if (b.getPlayerInstance().getInventory().contains(Material.REDSTONE)) {
            removeSoif(b.getPlayerInstance());
            createFlyingSword(b.getPlayerInstance());
            new BukkitRunnable() {
                @Override
                public void run() {
                    timer--;
                    util.sendActionBar(b.getPlayerInstance(), "§aCooldown épée : §7§l" + timer);
                    if (timer <= 0) {
                        timer = 6;
                        cancel();
                    }
                }
            }.runTaskTimer(BloodyMurder.getInstance(), 0, 20);
        }
    }

    private void createFlyingSword(Player attacker) {
        Sword sword = new Sword(attacker);
        int maxRange = 20;
        double maxHitRange = 0.5;
        new BukkitRunnable() {
            @Override
            public void run() {
                sword.getBody().teleport(sword.getStandStart().add(sword.getVec()));
                sword.getInitialise().add(sword.getVec());
                sword.getInitialise().getWorld().getNearbyEntities(sword.getInitialise(), maxHitRange, maxHitRange, maxHitRange).forEach(entity -> {
                    if (entity instanceof Player) {
                        Player victim = (Player) entity;
                        if (!(Repository.findBloodyPlayer(victim).getRole() == Roles.Murder)) {
                            victim.damage(100.0);
                            Repository.findBloodyPlayer(attacker).addkills();
                        } else {
                            util.sendActionBar(attacker, "§c§lC'est ton ally dommage ):");
                        }
                    }
                });
                if (sword.getLoc().distance(sword.getInitialise()) > maxRange || sword.getInitialise().getBlock().getType().isSolid()) {
                    this.cancel();
                    sword.getBody().remove();
                }
            }
        }.runTaskTimer(BloodyMurder.getInstance(), 0, 1);
    }

    @Override
    public void death(BloodyPlayer b) {
        Game g = b.getGame();
        g.supMurder(b);
        if (g.murderleftsixe() <= 0) {
            g.setEnd(Roles.Innocent);
        } else {
            util.sendActionBar(g,"§a§lil reste: §b§l" + (g.murderleftsixe()) + " §a§lMurder");
        }
    }
}
