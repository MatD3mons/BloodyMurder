package fr.MatD3mons.BloodyMurder.Commande.cmd;

import fr.MatD3mons.BloodyMurder.Commande.Aliases;
import fr.MatD3mons.BloodyMurder.Commande.Cmd;
import fr.MatD3mons.BloodyMurder.Game.GameManager;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdLobby extends Cmd {

    public CmdLobby() {
        super();
        this.aliases.addAll(Aliases.lobby);
    }

    @Override
    public void perform(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command instanceof Player) {
            BloodyPlayer b = Repository.findBloodyPlayer((Player) commandSender);
            GameManager.leave(b);
        }
    }
}
