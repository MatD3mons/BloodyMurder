package fr.MatD3mons.BloodyMurder.Event;

import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class InventoryCloseEvent implements Listener {

    @EventHandler
    public void onInventoryClose(org.bukkit.event.inventory.InventoryCloseEvent event) {
        Player player = (Player)event.getPlayer();
        BloodyPlayer bp = Repository.findBloodyPlayer(player);
        if(bp.getCurrentGui() != null && player.isOnline()) {
            bp.getCurrentGui().onClose(event);
        }
    }
}
