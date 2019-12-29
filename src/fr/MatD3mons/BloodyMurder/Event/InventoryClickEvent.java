package fr.MatD3mons.BloodyMurder.Event;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class InventoryClickEvent implements Listener {

    @EventHandler
    public void OnClick(org.bukkit.event.inventory.InventoryClickEvent e){
        if(e.getWhoClicked() instanceof Player){
            Player p = (Player) e.getWhoClicked();
            if(p.getGameMode() != GameMode.CREATIVE){
                e.setCancelled(true);
            }
        }
    }
}
