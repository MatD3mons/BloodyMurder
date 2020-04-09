package fr.MatD3mons.BloodyMurder.Commande.cmd;

import fr.MatD3mons.BloodyMurder.Commande.Aliases;
import fr.MatD3mons.BloodyMurder.Commande.Cmd;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdsetWait extends Cmd {

    public CmdsetWait() {
        super();
        this.aliases.addAll(Aliases.setwait);
    }

    @Override
    public void perform(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command instanceof Player) {
            BloodyPlayer b = Repository.findBloodyPlayer((Player) commandSender);
            if(b.getGame() != null) {
                b.getGame().setWait();
            }
        }
    }
}
