package fr.MatD3mons.BloodyMurder.Game;

import fr.MatD3mons.BloodyMurder.Game.roles.Detective;
import fr.MatD3mons.BloodyMurder.Game.roles.Innocent;
import fr.MatD3mons.BloodyMurder.Game.roles.Murder;

import java.util.HashMap;
import java.util.Map;

public enum Roles {
    Murder,
    Detective,
    Innocent;

    public static Map<Roles, Role> PlayerRoles;

    static{
        PlayerRoles = new HashMap<>();
        PlayerRoles.put(Murder,new Murder());
        PlayerRoles.put(Innocent,new Innocent());
        PlayerRoles.put(Detective,new Detective());
    }

    public static Roles getRoles(String name){
        if(name.equals(Murder.name()))return Murder;
        if(name.equals(Detective.name()))return Detective;
        return Innocent;
    }

    public static Role getRole(Roles roles){
        return PlayerRoles.get(roles);
    }

}
