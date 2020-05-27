package fr.MatD3mons.BloodyMurder.Game.roles;

import fr.MatD3mons.BloodyMurder.Game.Role;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Detective extends Role {
    @Override
    public boolean EntityDamageByEntity(EntityDamageByEntityEvent e) {
        return false;
    }

    @Override
    public void message(BloodyPlayer b) {

    }

    @Override
    public void stuff(BloodyPlayer b) {

    }

    @Override
    public void fin(BloodyPlayer b) {

    }

    @Override
    public void take(BloodyPlayer b) {

    }

    @Override
    public void Interact(BloodyPlayer b) {

    }

    @Override
    public void death(BloodyPlayer b) {

    }
}
