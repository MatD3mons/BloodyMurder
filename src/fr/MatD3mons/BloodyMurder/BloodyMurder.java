package fr.MatD3mons.BloodyMurder;

import fr.MatD3mons.BloodyMurder.Commande.BM;
import fr.MatD3mons.BloodyMurder.Event.*;
import fr.MatD3mons.BloodyMurder.Game.GameManager;
import fr.MatD3mons.BloodyMurder.ScoreBoard.ScoreBoardDisplayer;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.inventivetalent.nicknamer.NickNamerPlugin;
import org.inventivetalent.nicknamer.api.NickManager;
import org.inventivetalent.nicknamer.api.NickNamerAPI;
import org.inventivetalent.nicknamer.api.SimpleNickManager;
import org.inventivetalent.nicknamer.api.event.NickNamerUpdateEvent;

import java.util.HashMap;
import java.util.UUID;

public class BloodyMurder extends JavaPlugin {

    public static Plugin instance;
    public static GameManager gameManager;
    public static HashMap<UUID, PermissionAttachment> perms;
    public static HashMap<UUID, EntityArmorStand> stands;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        gameManager = new GameManager();
        stands = new HashMap<>();
        perms = new HashMap<UUID, PermissionAttachment>();
        this.getCommand("BM").setExecutor(new BM());
        getServer().getPluginManager().registerEvents(new EntityDamageEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageByEntity(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathEvent(), this);
        getServer().getPluginManager().registerEvents(new FoodLevelChangeEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerDropItemEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerPickupItem(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitEvent(), this);
        getServer().getPluginManager().registerEvents(new InventoryClickEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractEvent(), this);
        ScoreBoardDisplayer.initialize();
    }

    @Override
    public void onDisable() {
    }
}
