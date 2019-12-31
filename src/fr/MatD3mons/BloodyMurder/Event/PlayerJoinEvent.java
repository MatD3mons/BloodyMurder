package fr.MatD3mons.BloodyMurder.Event;

import fr.MatD3mons.BloodyMurder.BloodyMurder;
import fr.MatD3mons.BloodyMurder.Game.GameManager;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import fr.MatD3mons.BloodyMurder.bdd.SqlConnection;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import fr.MatD3mons.BloodyMurder.utile.util;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;


public class PlayerJoinEvent implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(org.bukkit.event.player.PlayerJoinEvent e) {
        e.setJoinMessage(null);
        Player p = e.getPlayer();
        p.getInventory().setHelmet(new ItemStack(Material.AIR, 1));
        p.getInventory().setChestplate(new ItemStack(Material.AIR, 1));
        p.getInventory().setLeggings(new ItemStack(Material.AIR, 1));
        p.getInventory().setBoots(new ItemStack(Material.AIR, 1));
        p.setGameMode(GameMode.ADVENTURE);
        PermissionAttachment attachment = p.addAttachment(BloodyMurder.instance);
        BloodyMurder.perms.put(p.getUniqueId(),attachment);
        PermissionAttachment pperms = BloodyMurder.perms.get(p.getUniqueId());
        pperms.setPermission("bloodymurder.bm", true);
        p.removePotionEffect(PotionEffectType.INVISIBILITY);
        p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        p.setHealth(20);
        p.setFoodLevel(20);
        p.teleport(GameManager.spawn);
        p.getInventory().clear();
        p.playSound(p.getLocation(), Sound.CLICK, 2F, 1F);
        Repository.players.put(p,new BloodyPlayer(p));
        BloodyMurder.sql.createAccount(p);
    }
}
