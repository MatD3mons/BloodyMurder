package fr.MatD3mons.BloodyMurder.Game;

import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public abstract class Role {

    protected String skull = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjdiZjAyMzkwZDNmM2Y0Y2NlNGJmZWRjM2MxOTA0ODQxMzhhMzE3NGQ4NTQxYThmZDkxMmViYjIxNDdmY2MwZSJ9fX0";

    public String getSkull() {
        return skull;
    }

    public abstract boolean EntityDamageByEntity(EntityDamageByEntityEvent e);

    public abstract void message(BloodyPlayer b);

    public abstract void stuff(BloodyPlayer b);

    public abstract void fin(BloodyPlayer b);

    public abstract void take(BloodyPlayer b);

    public abstract void Interact(BloodyPlayer b);

    public abstract void death(BloodyPlayer b);

    public abstract String toString();
}
