package fr.MatD3mons.BloodyMurder;

import fr.MatD3mons.BloodyMurder.Commande.Cmd;
import fr.MatD3mons.BloodyMurder.Commande.CmdBase;
import fr.MatD3mons.BloodyMurder.Commande.Context;
import fr.MatD3mons.BloodyMurder.Event.*;
import fr.MatD3mons.BloodyMurder.Game.Game;
import fr.MatD3mons.BloodyMurder.Game.GameManager;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import fr.MatD3mons.BloodyMurder.ScoreBoard.ScoreBoardDisplayer;
import fr.MatD3mons.BloodyMurder.persitence.Dto.BloodyPlayerDto;
import fr.MatD3mons.BloodyMurder.persitence.mapper.BloodyPlayerDtoToDomain;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import net.bloodybattle.bloodykvs.BloodyKVS;
import net.bloodybattle.bloodykvs.core.BloodyDao;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.stream.Collectors;

public class BloodyMurder extends JavaPlugin {

    public static String BLOODYPLAYERDTO_COLLECTION_NAME = "bloodyMurder_bloodyplayer";

    public static Plugin instance;
    public static GameManager gameManager;
    public static HashMap<UUID, PermissionAttachment> perms;
    public static HashMap<UUID, EntityArmorStand> stands;
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
        for(Player p : Bukkit.getOnlinePlayers()) {
            loadPlayer(p);
        }
    }

    @Override
    public void onDisable() {
        for (Game g:GameManager.games.values()){
            if(g.getMode() == Game.GameMode.GAME)
                g.setDisable();
        }
        for (Player p : Bukkit.getOnlinePlayers()){
            p.getInventory().clear();
            p.teleport(GameManager.lobby);
        }
        //GUI Disable
        for(Player p : Bukkit.getOnlinePlayers()) {
            BloodyPlayer bp = Repository.findBloodyPlayer(p);
            if(bp.getCurrentGui() != null) {
                bp.getPlayerInstance().closeInventory();
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        // Must be a LinkedList to prevent UnsupportedOperationException.
        List<String> argsList = new LinkedList<>(Arrays.asList(args));
        Context context = new Context(sender, argsList, alias);
        List<Cmd> commandsList = cmdBase.subCommands;
        List<String> completions = new ArrayList<>();
        // Check for "" first arg because spigot is mangled.
        if (context.args.get(0).equals("")) {
            for (Cmd subCommand : commandsList) {
                completions.addAll(subCommand.aliases);
            }
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
            return completions;
        }
        return completions;
    }

    public static void loadPlayer(Player player) {

        String playerId = player.getUniqueId().toString();
        BloodyDao<BloodyPlayerDto> dao = BloodyKVS.getController().getOrCreateDao(BloodyPlayerDto.class);

        dao.getAsync(playerId) // get from database
                .then(bloodyPlayerDto -> { // Handle result
                    if(bloodyPlayerDto != null) {
                        // Already exist, let's load all data
                        BloodyPlayerDtoToDomain mapper = new BloodyPlayerDtoToDomain();
                        BloodyPlayer bp = mapper.map(bloodyPlayerDto);
                        if(player.isOnline()) {
                            bp.setPlayerInstance(player);
                            BloodyPlayer.registerPlayer(player, bp);
                        }
                    }
                    else {
                        // FIRST TIME JOIN
                        BloodyPlayer bp = new BloodyPlayer(player);
                        if(player.isOnline()) {
                            dao.saveAsync(playerId, new BloodyPlayerDto())
                                    .then(() -> BloodyPlayer.registerPlayer(player, bp));
                        }
                    }
                });
    }


}
