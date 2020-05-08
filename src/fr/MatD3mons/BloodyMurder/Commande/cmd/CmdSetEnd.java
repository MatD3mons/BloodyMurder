package fr.MatD3mons.BloodyMurder.Commande.cmd;

import fr.MatD3mons.BloodyMurder.Commande.Aliases;
import fr.MatD3mons.BloodyMurder.Commande.Cmd;
import fr.MatD3mons.BloodyMurder.Commande.Context;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;

public class CmdSetEnd extends Cmd {

    public CmdSetEnd() {
        super();
        this.aliases.addAll(Aliases.setend);
    }

    @Override
    public void perform(Context context) {
        BloodyPlayer b = context.bloodyPlayer;
        if (b.getGame() != null) {
            b.getGame().setEnd(b.getRole());
        }
    }
}