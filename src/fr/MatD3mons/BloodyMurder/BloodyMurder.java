package fr.MatD3mons.BloodyMurder;

import fr.MatD3mons.BloodyMurder.Commande.CmdBase;
import fr.MatD3mons.BloodyMurder.Event.*;
import fr.MatD3mons.BloodyMurder.Game.GameManager;
import fr.MatD3mons.BloodyMurder.ScoreBoard.ScoreBoardDisplayer;
import fr.MatD3mons.BloodyMurder.bdd.BloodyPlayerDao;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class BloodyMurder extends JavaPlugin {

    public static Plugin instance;
    public static GameManager gameManager;
    public static HashMap<UUID, PermissionAttachment> perms;
    public static HashMap<UUID, EntityArmorStand> stands;
    public static BloodyPlayerDao bloodyPlayerDao;

    public static Plugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        gameManager = new GameManager();
        stands = new HashMap<>();
        perms = new HashMap<UUID, PermissionAttachment>();
        bloodyPlayerDao = new BloodyPlayerDao();
        this.getCommand("bm").setExecutor(new CmdBase());
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
}
