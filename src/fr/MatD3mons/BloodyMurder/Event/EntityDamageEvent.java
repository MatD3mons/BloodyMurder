package fr.MatD3mons.BloodyMurder.Event;

import fr.MatD3mons.BloodyMurder.Game.Game;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityDamageEvent implements Listener {

    @EventHandler
    public void playerHit(org.bukkit.event.entity.EntityDamageEvent e) {
        if(!(e.getEntity() instanceof Player)){return;}
        Player p = (Player) e.getEntity();
        if (!(Repository.BloodyPlayerContains(p))){return;}
        if (Repository.findBloodyPlayer(p).getGame() == null) {
            e.setCancelled(true);
        } else if (Repository.findBloodyPlayer((Player) e.getEntity()).getGame().getMode() != Game.GameMode.GAME) {
            e.setCancelled(true);
        }
        if (e.getCause() == org.bukkit.event.entity.EntityDamageEvent.DamageCause.FALL) {
            e.setCancelled(true);
        }
    }
}


