package fr.MatD3mons.BloodyMurder.Commande.cmd;

import fr.MatD3mons.BloodyMurder.Commande.Aliases;
import fr.MatD3mons.BloodyMurder.Commande.Cmd;
import fr.MatD3mons.BloodyMurder.Commande.Context;
import fr.MatD3mons.BloodyMurder.Game.Game;
import fr.MatD3mons.BloodyMurder.Game.GameManager;

public class CmdAddOr extends Cmd {

    public CmdAddOr() {
        super();
        this.aliases.addAll(Aliases.Addor);
    }

    @Override
    public void perform(Context context) {
        if(context.args.size() <= 0){return;}
        Game g = GameManager.games.get(context.args.get(0));
        g.addor(context.player);
    }
}
