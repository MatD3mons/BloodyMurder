package fr.MatD3mons.BloodyMurder.Commande;

import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Context {

    public CommandSender sender;

    public Player player;
    public BloodyPlayer bloodyPlayer;

    public List<String> args;
    public String alias;

    public List<Cmd> commandChain = new ArrayList<>();

    public Context(CommandSender sender, List<String> args, String alias) {
        this.sender = sender;
        this.args = args;
        this.alias = alias;

        if (sender instanceof Player) {
            this.player = (Player) sender;
            this.bloodyPlayer = Repository.findBloodyPlayer(player);
        }
    }

}
