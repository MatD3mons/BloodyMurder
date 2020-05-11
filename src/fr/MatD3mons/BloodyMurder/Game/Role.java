package fr.MatD3mons.BloodyMurder.Game;

import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public abstract class Role {

    public abstract boolean EntityDamageByEntity(EntityDamageByEntityEvent e);

    public abstract void message(BloodyPlayer b);

    public abstract void stuff(BloodyPlayer b);

    public abstract void fin(BloodyPlayer b);

    public abstract void take(BloodyPlayer b);

    public abstract void Interact(BloodyPlayer b);

}
