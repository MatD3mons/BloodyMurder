package fr.MatD3mons.BloodyMurder.Game.roles;

import fr.MatD3mons.BloodyMurder.Game.Game;
import fr.MatD3mons.BloodyMurder.Game.Role;
import fr.MatD3mons.BloodyMurder.Game.Roles;
import fr.MatD3mons.BloodyMurder.GameComponents.Arc;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import fr.MatD3mons.BloodyMurder.utile.util;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class Detective extends Role {

    public Detective(){
        skull = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjdiZjAyMzkwZDNmM2Y0Y2NlNGJmZWRjM2MxOTA0ODQxMzhhMzE3NGQ4NTQxYThmZDkxMmViYjIxNDdmY2MwZSJ9fX1";
    }

    @Override
    public boolean EntityDamageByEntity(EntityDamageByEntityEvent e){
        if(!(e.getDamager() instanceof Arrow)){return false;}
        Arrow fleche = ((Arrow) e.getDamager());
        if(!(fleche.getShooter() instanceof Player)){return false;}
        Player killer = (Player) fleche.getShooter();
        if (Repository.findBloodyPlayer(killer).getGame() == null) {return false;}
        if (!(Repository.findBloodyPlayer(killer).getRole() == Roles.Innocent)){return false;}
        Game g = Repository.findBloodyPlayer(killer).getGame();
        if (g.getMode() != Game.GameMode.GAME) { return false;}
        Player p2 = (Player) e.getEntity();
        e.setDamage(100);
        p2.getInventory().clear();
        if (Repository.findBloodyPlayer(p2).getRole() == Roles.Murder) {
            Repository.findBloodyPlayer(killer).addkills();
            return true;
        } else {
            Repository.findBloodyPlayer(killer).removekills();
            g.supInnocent(Repository.findBloodyPlayer(p2));
            return true;
        }
    }

    @Override
    public void message(BloodyPlayer b) {
        util.sendTitle(b.getPlayerInstance(), "§a§lTu es Detective !", "§aRécupère de l'or pour avoir des flèches", 0, 3, 0);
    }

    @Override
    public void stuff(BloodyPlayer b) {
        ItemStack itemStack = util.create(Material.BOW, 1, ChatColor.AQUA, "Arc du Detective");
        itemStack.addEnchantment(Enchantment.ARROW_INFINITE, 1);
        b.getPlayerInstance().getInventory().setItem(0, itemStack);
        ItemStack itemStack2 = util.create(Material.ARROW, 1, ChatColor.AQUA, "flèche");
        b.getPlayerInstance().getInventory().setItem(1, itemStack2);
    }

    @Override
    public void fin(BloodyPlayer b) {
        b.getPlayerInstance().getInventory().clear();
        if(b.getGame().innocentleftsixe() == 1)
            util.sendTitle(b.getGame(), "§a§l L' INNOCENTS a gagné", "", 0, 3, 0);
        else
            util.sendTitle(b.getGame(), "§a§l Les INNOCENTS ont gagné", "", 0, 3, 0);
    }

    @Override
    public void take(BloodyPlayer b) { }

    @Override
    public void Interact(BloodyPlayer b) { }

    @Override
    public void death(BloodyPlayer b) {
        Game g = b.getGame();
        b.adddeaths();
        g.supInnocent(b);
        if (g.innocentleftsixe() <= 0) {
            g.setEnd(Roles.Murder);
        } else {
            util.sendActionBar(g, "§a§lil reste: §b§l" + (g.innocentleftsixe()) + " §a§linnocents");
            new Arc(b.getPlayerInstance().getLocation());
        }
    }

    @Override
    public String toString(){
        return "Detective";
    }
}
