package fr.MatD3mons.BloodyMurder.Game;

import fr.MatD3mons.BloodyMurder.Game.roles.Innocent;
import fr.MatD3mons.BloodyMurder.Game.roles.Murder;

import java.util.HashMap;
import java.util.Map;

public enum Roles {
    Murder,
    Innocent;

    public static Map<Roles, Role> PlayerRoles;

    static{
        PlayerRoles = new HashMap<>();
        PlayerRoles.put(Murder,new Murder());
        PlayerRoles.put(Innocent,new Innocent());
    }
}
