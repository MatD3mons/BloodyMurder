package fr.MatD3mons.BloodyMurder.Game;

import fr.MatD3mons.BloodyMurder.Event.EntityDamageByEntity;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public abstract class role {

    public role(boolean b){
        setmechant(b);
    }

    private boolean mechant;
    public boolean getmechant(){
        return mechant;
    }
    public void setmechant(Boolean mechant){
        this.mechant = mechant;
    }

    public abstract boolean EntityDamageByEntity(EntityDamageByEntityEvent e);

    public abstract void message(BloodyPlayer b);

    public abstract void stuff(BloodyPlayer b);

    public abstract void fin(BloodyPlayer b);

    public abstract void take(BloodyPlayer b);

}
