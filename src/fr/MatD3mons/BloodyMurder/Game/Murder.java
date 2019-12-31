package fr.MatD3mons.BloodyMurder.Game;

import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import fr.MatD3mons.BloodyMurder.utile.util;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class Murder extends role {

    public Murder(){
        super(true);
    }

    @Override
    public boolean EntityDamageByEntity(EntityDamageByEntityEvent e){
        if (e.getDamager() instanceof Player) {
            Player killer = (Player) e.getDamager();
            if (Repository.findBloodyPlayer(killer).getGame() != null) {
                Game g = Repository.findBloodyPlayer(killer).getGame();
                if (g.getMode() == Game.GameMode.GAME) {
                    Player p2 = (Player) e.getEntity();
                    if (Repository.findBloodyPlayer(killer).getRole() instanceof Murder) {
                        if (!Repository.findBloodyPlayer(p2).getRole().getmechant()) {
                            if (killer.getItemInHand().getType() == Material.DIAMOND_SWORD) {
                                if (killer.getInventory().contains(Material.RED_SANDSTONE)) {
                                    p2.getInventory().clear();
                                    Repository.findBloodyPlayer(p2).adddeaths();
                                    e.setDamage(100);
                                    g.supInnocent(Repository.findBloodyPlayer(p2));
                                    for (int i = 0; i < killer.getInventory().getSize(); i++) {
                                        if (killer.getInventory().getItem(i) != null)
                                            if (killer.getInventory().getItem(i).getType() == Material.RED_SANDSTONE) {
                                                int j = killer.getInventory().getItem(i).getAmount();
                                                if (j == 1)
                                                    killer.getInventory().remove(Material.RED_SANDSTONE);
                                                else
                                                    killer.getInventory().getItem(i).setAmount(j - 1);
                                            }
                                    }
                                    if (g.innocentleftsixe() <= 0) {
                                        g.setEnd(Repository.findBloodyPlayer(killer).getRole());
                                    } else {
                                        util.sendTitle(killer, "", "§a§lil reste: §b§l" + (g.innocentleftsixe()) + " §a§linnocents", 0, 3, 0);
                                    }
                                    Repository.findBloodyPlayer(killer).addkills();
                                    return true;
                                } else {
                                    util.sendTitle(killer, "", "§c§lTu n'as pas de soif de sang", 0, 3, 0);
                                }
                            }

                        } else {
                            util.sendTitle(killer, "", "§c§lIl est aussi Murder", 0, 2, 0);
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void message(BloodyPlayer b){
        util.sendTitle(b.getPlayerInstance(), "§4§lTu es Murder !", "§cTu dois récupérer 5 or pour Tuer une personne", 0, 3, 0);
    }

    @Override
    public void stuff(BloodyPlayer b){
        ItemStack itemStack = util.create(Material.DIAMOND_SWORD,1,ChatColor.AQUA,"Epée du Murder");
        b.getPlayerInstance().getInventory().setItem(0,itemStack);
    }

    @Override
    public void fin(BloodyPlayer b){
        b.getPlayerInstance().getInventory().clear();
        if(b.getGame().murderleftsixe() == 1)
            util.sendTitle(b.getPlayerInstance(), "§c§l Le MURDER a gagné", "", 0, 3, 0);
        else
            util.sendTitle(b.getPlayerInstance(), "§c§l Les MURDER ont gagné", "", 0, 3, 0);
    }

    @Override
    public String toString(){
        return "Murder";
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
                    if(b.getPlayerInstance().getInventory().contains(Material.RED_SANDSTONE)) {
                        ItemStack itemStack = util.create(Material.RED_SANDSTONE, 1, ChatColor.AQUA, "Soif de sang");
                        b.getPlayerInstance().getInventory().addItem(itemStack);
                    }
                    else{
                        ItemStack itemStack = util.create(Material.RED_SANDSTONE, 1, ChatColor.AQUA, "Soif de sang");
                        b.getPlayerInstance().getInventory().setItem(1,itemStack);
                    }
                }
            }
        }
    }
}
