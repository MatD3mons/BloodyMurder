package fr.MatD3mons.BloodyMurder;

import fr.MatD3mons.BloodyMurder.Commande.Cmd;
import fr.MatD3mons.BloodyMurder.Commande.CmdBase;
import fr.MatD3mons.BloodyMurder.Commande.Context;
import fr.MatD3mons.BloodyMurder.Event.*;
import fr.MatD3mons.BloodyMurder.Game.Game;
import fr.MatD3mons.BloodyMurder.Game.GameManager;
import fr.MatD3mons.BloodyMurder.Game.Roles;
import fr.MatD3mons.BloodyMurder.ScoreBoard.ScoreBoardDisplayer;
import fr.MatD3mons.BloodyMurder.bdd.BloodyPlayerDao;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.stream.Collectors;

public class BloodyMurder extends JavaPlugin {

    public static Plugin instance;
    public static GameManager gameManager;
    public static HashMap<UUID, PermissionAttachment> perms;
    public static HashMap<UUID, EntityArmorStand> stands;
    public static BloodyPlayerDao bloodyPlayerDao;
    public static CmdBase cmdBase;

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
        Repository.Update();
        cmdBase = new CmdBase();
        this.getCommand("BloodyMurder").setExecutor(cmdBase);
        this.getCommand("BloodyMurder").setTabCompleter(this);
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
        for (Game g:GameManager.games.values()){
            if(g.getMode() == Game.GameMode.GAME)
                g.setEnd(Roles.Innocent);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        // Must be a LinkedList to prevent UnsupportedOperationException.
        List<String> argsList = new LinkedList<>(Arrays.asList(args));
        Context context = new Context(sender, argsList, alias);
        List<Cmd> commandsList = cmdBase.subCommands;
        Cmd commandsEx = cmdBase;
        List<String> completions = new ArrayList<>();
        // Check for "" first arg because spigot is mangled.
        if (context.args.get(0).equals("")) {
            for (Cmd subCommand : commandsEx.subCommands) {
                completions.addAll(subCommand.aliases);
            }
            return completions;
        } else if (context.args.size() == 1) {
            for (; !commandsList.isEmpty() && !context.args.isEmpty(); context.args.remove(0)) {
                String cmdName = context.args.get(0).toLowerCase();
                boolean toggle = false;
                for (Cmd fCommand : commandsList) {
                    for (String s : fCommand.aliases) {
                        if (s.startsWith(cmdName)) {
                            commandsList = fCommand.subCommands;
                            completions.addAll(fCommand.aliases);
                            toggle = true;
                            break;
                        }
                    }
                    if (toggle) break;
                }
            }
            String lastArg = args[args.length - 1].toLowerCase();
            completions = completions.stream()
                    .filter(m -> m.toLowerCase().startsWith(lastArg))
                    .collect(Collectors.toList());
        }
        return completions;
    }

}
