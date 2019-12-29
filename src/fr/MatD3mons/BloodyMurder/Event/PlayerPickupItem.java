package fr.MatD3mons.BloodyMurder.Event;

import fr.MatD3mons.BloodyMurder.BloodyMurder;
import fr.MatD3mons.BloodyMurder.Game.Game;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import fr.MatD3mons.BloodyMurder.utile.util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerPickupItem implements Listener {

    @EventHandler
    public void onPick(PlayerPickupItemEvent e) {
        Player p = e.getPlayer();
        ItemStack itemDrop = e.getItem().getItemStack();
        if (Repository.BloodyPlayerContains(p)) {
            BloodyPlayer b = Repository.findBloodyPlayer(p);
            if (b.getGame() != null) {
                if (b.getGame().getMode() == Game.GameMode.GAME) {
                    if (itemDrop.getType() == Material.GOLD_INGOT) {
                        for (int y = 0; y < e.getItem().getItemStack().getAmount(); y++) {
                            if (b.getPlayerInstance().getInventory().contains(Material.GOLD_INGOT)) {
                                for (int i = 0; i < b.getPlayerInstance().getInventory().getSize(); i++) {
                                    if (b.getPlayerInstance().getInventory().getItem(i) != null)
                                        if (b.getPlayerInstance().getInventory().getItem(i).getType() == Material.GOLD_INGOT) {
                                            ItemStack itemStack = util.create(Material.GOLD_INGOT, 1, ChatColor.AQUA, "Or");
                                            b.getPlayerInstance().getInventory().addItem(itemStack);
                                        }
                                }
                            } else {
                                ItemStack itemStack = util.create(Material.GOLD_INGOT, 1, ChatColor.AQUA, "Or");
                                b.getPlayerInstance().getInventory().setItem(8, itemStack);
                            }
                            p.playSound(p.getLocation(), Sound.MAGMACUBE_JUMP, 2F, 1F);
                            b.getRole().take(b);
                            Repository.findBloodyPlayer(p).addGold();
                        }
                        Bukkit.getScheduler().scheduleSyncDelayedTask(BloodyMurder.instance, () -> {
                            for(int i = 0; i < 20; i++) {
                                while (p.getInventory().contains(new ItemStack(Material.GOLD_INGOT, i))) {
                                    p.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT, i));
                                }
                            }
                        });
                    }
                }
            }
        }
    }
}
