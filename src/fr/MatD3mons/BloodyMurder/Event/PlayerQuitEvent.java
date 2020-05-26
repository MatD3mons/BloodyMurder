package fr.MatD3mons.BloodyMurder.Event;

import fr.MatD3mons.BloodyMurder.BloodyMurder;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerQuitEvent implements Listener {

    @EventHandler
    public void OnQuit(org.bukkit.event.player.PlayerQuitEvent e){
        e.setQuitMessage(null);
        Player p = e.getPlayer();
        BloodyPlayer b = Repository.findBloodyPlayer(p);
        if(b.getGame() != null){
            b.getGame().leave(b);
        }
        Repository.remove(e.getPlayer());
    }
}
