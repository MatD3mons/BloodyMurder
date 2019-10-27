package fr.MatD3mons.BloodyMurder.Event;

import fr.MatD3mons.BloodyMurder.BloodyMurder;
import fr.MatD3mons.BloodyMurder.Game.GameManager;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerDeathEvent implements Listener {

    @EventHandler
    public void onSpawn(org.bukkit.event.entity.PlayerDeathEvent e) {
        if (e.getEntity() != null) {
            Player p = (Player) e.getEntity();
            p.setHealth(20);
            p.setFoodLevel(20);
            if (Repository.findBloodyPlayer(p).getGame() == null) {
                p.teleport(GameManager.spawn);
            } else {
                p.setGameMode(GameMode.SPECTATOR);
                p.teleport(Repository.findBloodyPlayer(p).getGame().spawn);
            }
            p.spigot().respawn();
            e.setDroppedExp(1);
            e.getDrops().clear();
        }
    }
}
