package fr.MatD3mons.BloodyMurder.Event;

import fr.MatD3mons.BloodyMurder.Game.Game;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityDamageEvent implements Listener {

    @EventHandler
    public void playerHited(org.bukkit.event.entity.EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            Player p2 = (Player) e.getEntity();
            if (Repository.BloodyPlayerContains((Player) e.getEntity())) {
                if (Repository.findBloodyPlayer((Player) e.getEntity()).getGame() == null) {
                    e.setCancelled(true);
                } else {
                    if (Repository.findBloodyPlayer((Player) e.getEntity()).getGame().getMode() != Game.GameMode.GAME) {
                        e.setCancelled(true);
                    }
                }
                if (e.getCause() == org.bukkit.event.entity.EntityDamageEvent.DamageCause.FALL) {
                    e.setCancelled(true);
                }
            }
        }
    }
}


