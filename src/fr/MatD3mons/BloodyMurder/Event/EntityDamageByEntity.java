package fr.MatD3mons.BloodyMurder.Event;

import fr.MatD3mons.BloodyMurder.Game.*;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityDamageByEntity implements Listener {

    @EventHandler
    public void onPlayerHitEvent( org.bukkit.event.entity.EntityDamageByEntityEvent e) {
        if(Hit(e))
            for (Role r : GameManager.differentrole.values())
                if (r.EntityDamageByEntity(e))
                    return;
        e.setCancelled(true);
    }

    public Boolean Hit(org.bukkit.event.entity.EntityDamageByEntityEvent e){
        if(!(e.getEntity() instanceof Player)){return false;}
        Player p = (Player) e.getEntity();
        if (!Repository.BloodyPlayerContains(p)) {return false;}
        if (Repository.findBloodyPlayer(p).getGame() == null) {return false;}
        if (Repository.findBloodyPlayer(p).getGame().getMode() != Game.GameMode.GAME) {return false;}
        return true;
    }

}
