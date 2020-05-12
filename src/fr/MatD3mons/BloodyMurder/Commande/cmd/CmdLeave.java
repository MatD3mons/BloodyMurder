package fr.MatD3mons.BloodyMurder.Commande.cmd;

import fr.MatD3mons.BloodyMurder.Commande.Aliases;
import fr.MatD3mons.BloodyMurder.Commande.Cmd;
import fr.MatD3mons.BloodyMurder.Commande.Context;
import fr.MatD3mons.BloodyMurder.Game.GameManager;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;

public class CmdLeave extends Cmd {

    public CmdLeave() {
        super();
        this.aliases.addAll(Aliases.Leave);
    }

    @Override
    public void perform(Context context) {
        BloodyPlayer b = context.bloodyPlayer;
        GameManager.leave(b);
    }
}
