package fr.MatD3mons.BloodyMurder.Event;

import fr.MatD3mons.BloodyMurder.Game.*;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import fr.MatD3mons.BloodyMurder.utile.util;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityDamageByEntity implements Listener {

    @EventHandler
    public void onPlayerHitEvent( org.bukkit.event.entity.EntityDamageByEntityEvent e) {
        boolean taper = false;
        if(e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (Repository.BloodyPlayerContains(p)) {
                if (Repository.findBloodyPlayer(p).getGame() != null) {
                    if (Repository.findBloodyPlayer(p).getGame().getMode() == Game.GameMode.GAME) {
                        for (role r : GameManager.differentrole) {
                            if (r.EntityDamageByEntity(e))
                                taper = true;
                        }
                    }
                }
            }
        }
        if (!taper)
            e.setCancelled(true);
    }
}
