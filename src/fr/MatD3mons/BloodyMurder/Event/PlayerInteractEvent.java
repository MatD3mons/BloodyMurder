package fr.MatD3mons.BloodyMurder.Event;

import fr.MatD3mons.BloodyMurder.Game.GameManager;
import fr.MatD3mons.BloodyMurder.Game.Role;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

public class PlayerInteractEvent implements Listener {

    @EventHandler
    public void OnInteract(org.bukkit.event.player.PlayerInteractEvent e){
        Player p = e.getPlayer();
        Action a = e.getAction();
        if(!(Repository.BloodyPlayerContains(p))){return;}
        BloodyPlayer b = Repository.findBloodyPlayer(p);
        if(b.getGame() == null){Leave(a,p,b); return;}
        switch (b.getGame().getMode()) {
            case WAITING:
            case END:
                Leave(a, p, b);
                break;
            case GAME:
                if (b.getRole() == Role.roles.Murder)
                    if ((a != Action.PHYSICAL))
                        GameManager.differentrole.get(Role.roles.Murder).Interact(b);
                break;
        }
    }

    public void Leave(Action a, Player p, BloodyPlayer b){
        if((a != Action.PHYSICAL) && (p.getItemInHand().getType() == Material.BED))
            GameManager.leave(b);
    }
}
