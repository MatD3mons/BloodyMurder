package fr.MatD3mons.BloodyMurder.Event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FoodLevelChangeEvent implements Listener {

    @EventHandler
    public void onFood( org.bukkit.event.entity.FoodLevelChangeEvent e) {
        if( e.getEntity() instanceof Player)
            e.setCancelled(true);
    }
}
