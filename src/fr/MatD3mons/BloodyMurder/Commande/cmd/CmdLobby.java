package fr.MatD3mons.BloodyMurder.Commande.cmd;

import fr.MatD3mons.BloodyMurder.Commande.Aliases;
import fr.MatD3mons.BloodyMurder.Commande.Cmd;
import fr.MatD3mons.BloodyMurder.Commande.Context;
import fr.MatD3mons.BloodyMurder.Game.GameManager;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;

public class CmdLobby extends Cmd {

    public CmdLobby() {
        super();
        this.aliases.addAll(Aliases.lobby);
    }

    @Override
    public void perform(Context context) {
        BloodyPlayer b = context.bloodyPlayer;
        GameManager.leave(b);
    }
}
