package fr.MatD3mons.BloodyMurder.Event;

import fr.MatD3mons.BloodyMurder.Game.GameManager;
import fr.MatD3mons.BloodyMurder.Game.Murder;
import fr.MatD3mons.BloodyMurder.Game.role;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

import javax.management.relation.Role;

public class PlayerInteractEvent implements Listener {

    @EventHandler
    public void OnInteract(org.bukkit.event.player.PlayerInteractEvent e){
        Player p = e.getPlayer();
        Action a = e.getAction();
        if(Repository.BloodyPlayerContains(p)){
            BloodyPlayer b = Repository.findBloodyPlayer(p);
            if(b.getGame() != null){
                switch (b.getGame().getMode()){
                    case WAITING:
                    case END:
                        if((a != Action.PHYSICAL) && (p.getItemInHand().getType() == Material.BED))
                            GameManager.leave(b);
                        break;
                    case GAME:
                        if(b.getRole() == role.roles.Murder)
                            GameManager.differentrole.get(role.roles.Murder).Interact(b);

                }
            }else{
                if((a != Action.PHYSICAL) && (p.getItemInHand().getType() == Material.BED))
                    GameManager.leave(b);
            }
        }
    }
}
