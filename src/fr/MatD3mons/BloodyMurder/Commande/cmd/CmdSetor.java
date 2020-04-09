package fr.MatD3mons.BloodyMurder.Commande.cmd;

import fr.MatD3mons.BloodyMurder.Commande.Aliases;
import fr.MatD3mons.BloodyMurder.Commande.Cmd;
import fr.MatD3mons.BloodyMurder.Game.GameManager;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdSetor extends Cmd {

    public CmdSetor() {
        super();
        this.aliases.addAll(Aliases.setor);
    }

    @Override
    public void perform(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command instanceof Player) {
            BloodyPlayer b = Repository.findBloodyPlayer((Player) commandSender);
            if (strings[1] != null)
                GameManager.setor(b, strings[1]);
        }
    }
}
