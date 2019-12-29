package fr.MatD3mons.BloodyMurder.Event;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerDropItemEvent implements Listener {

    @EventHandler
    public void onItemDrop (org.bukkit.event.player.PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if(p.getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
        }
    }

}
