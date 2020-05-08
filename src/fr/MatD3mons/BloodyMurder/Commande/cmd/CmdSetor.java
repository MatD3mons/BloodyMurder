package fr.MatD3mons.BloodyMurder.Commande.cmd;

import fr.MatD3mons.BloodyMurder.Commande.Aliases;
import fr.MatD3mons.BloodyMurder.Commande.Cmd;
import fr.MatD3mons.BloodyMurder.Commande.Context;
import fr.MatD3mons.BloodyMurder.Game.GameManager;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;

public class CmdSetor extends Cmd {

    public CmdSetor() {
        super();
        this.aliases.addAll(Aliases.setor);
    }

    @Override
    public void perform(Context context) {
        BloodyPlayer b = context.bloodyPlayer;
        if (context.args.get(1) != null)
            GameManager.setor(b, context.args.get(1));
    }
}
