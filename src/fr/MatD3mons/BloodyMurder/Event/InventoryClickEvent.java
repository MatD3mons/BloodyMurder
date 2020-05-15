package fr.MatD3mons.BloodyMurder.Event;

import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class InventoryClickEvent implements Listener {

    @EventHandler
    public void OnClick(org.bukkit.event.inventory.InventoryClickEvent e){
        if (e.getCurrentItem() == null){return;}
        if (e.getCurrentItem().getType().equals(Material.AIR)) {return;}
        Player p = (Player) e.getWhoClicked();
        BloodyPlayer bp = Repository.findBloodyPlayer(p);
        if(p.getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
        }else if (bp.getCurrentGui() != null) {
            e.setCancelled(true);
        }
        if(bp.getCurrentGui() != null) {
            bp.getCurrentGui().onClick(e);
        }
    }
}
