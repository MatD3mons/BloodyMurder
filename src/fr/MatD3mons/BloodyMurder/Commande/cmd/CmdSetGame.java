package fr.MatD3mons.BloodyMurder.Commande.cmd;

import fr.MatD3mons.BloodyMurder.Commande.Aliases;
import fr.MatD3mons.BloodyMurder.Commande.Cmd;
import fr.MatD3mons.BloodyMurder.Commande.Context;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;

public class CmdSetGame extends Cmd {

    public CmdSetGame() {
        super();
        this.aliases.addAll(Aliases.setgame);
    }

    @Override
    public void perform(Context context) {
        BloodyPlayer b = context.bloodyPlayer;
        if (b.getGame() != null) {
            b.getGame().setGame();
        }
    }
}
