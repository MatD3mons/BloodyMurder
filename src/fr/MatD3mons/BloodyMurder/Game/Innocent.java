package fr.MatD3mons.BloodyMurder.Game;

import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import fr.MatD3mons.BloodyMurder.utile.util;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class Innocent extends role {

    public Innocent(){
        super(false);
    }

    @Override
    public boolean EntityDamageByEntity(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Arrow){
            Arrow fleche = ((Arrow) e.getDamager());
            if(fleche.getShooter() instanceof Player) {
                Player killer = (Player) fleche.getShooter();
                if (Repository.findBloodyPlayer(killer).getGame() != null) {
                    if (Repository.findBloodyPlayer(killer).getRole() instanceof Innocent){
                        Game g = Repository.findBloodyPlayer(killer).getGame();
                        if (g.getMode() == Game.GameMode.GAME) {
                            Player p2 = (Player) e.getEntity();
                            if (Repository.findBloodyPlayer(p2).getRole().getmechant()) {
                                p2.getInventory().clear();
                                e.setDamage(100);
                                Repository.findBloodyPlayer(killer).addkills();
                                Repository.findBloodyPlayer(p2).adddeaths();
                                g.supMurder(Repository.findBloodyPlayer(p2));
                                if (g.murderleftsixe() <= 0) {
                                    g.setEnd(Repository.findBloodyPlayer(killer).getRole());
                                } else {
                                    util.sendTitle(killer, "", "§a§lil reste: §b§l" + (g.murderleftsixe()) + " §a§lMurder", 0, 3, 0);
                                }
                                return true;
                            } else {
                                Repository.findBloodyPlayer(killer).removekills();
                                g.supInnocent(Repository.findBloodyPlayer(p2));
                                p2.getInventory().clear();
                                e.setDamage(100);
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    @Override
    public void message(BloodyPlayer b){
        util.sendTitle(b.getPlayerInstance(), "§a§lTu es Innocent !", "§aRécupère de l'or pour avoir des flèches", 0, 3, 0);
    }

    @Override
    public void stuff(BloodyPlayer b){
        ItemStack itemStack = util.create(Material.BOW,1,ChatColor.AQUA,"Arc");
        b.getPlayerInstance().getInventory().setItem(0,itemStack);    }

    @Override
    public void fin(BloodyPlayer b){
        b.getPlayerInstance().getInventory().clear();
        if(b.getGame().innocentleftsixe() == 1)
            util.sendTitle(b.getPlayerInstance(), "§a§l L' INNOCENTS a gagné", "", 0, 3, 0);
        else
            util.sendTitle(b.getPlayerInstance(), "§a§l Les INNOCENTS ont gagné", "", 0, 3, 0);
    }

    @Override
    public String toString(){
        return "Innocent";
    }

    @Override
    public void take(BloodyPlayer b){
        for(int i = 0; i < b.getPlayerInstance().getInventory().getSize(); i++) {
            if(b.getPlayerInstance().getInventory().getItem(i) != null)
                if (b.getPlayerInstance().getInventory().getItem(i).getType() == Material.GOLD_INGOT) {
                int j = b.getPlayerInstance().getInventory().getItem(i).getAmount();
                if ( j >= 5){
                    if(j == 5)
                         b.getPlayerInstance().getInventory().remove(Material.GOLD_INGOT);
                    else
                        b.getPlayerInstance().getInventory().getItem(i).setAmount(j-5);
                    if(b.getPlayerInstance().getInventory().contains(Material.ARROW)) {
                        ItemStack itemStack = util.create(Material.ARROW, 10, ChatColor.AQUA, "flèche");
                        b.getPlayerInstance().getInventory().addItem(itemStack);
                    }
                    else{
                        ItemStack itemStack = util.create(Material.ARROW, 10, ChatColor.AQUA, "flèche");
                        b.getPlayerInstance().getInventory().setItem(1,itemStack);
                    }
                }
            }
        }
    }

}
