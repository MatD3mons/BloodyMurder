package fr.MatD3mons.BloodyMurder.Commande.cmd;

import fr.MatD3mons.BloodyMurder.Commande.Aliases;
import fr.MatD3mons.BloodyMurder.Commande.Cmd;
import fr.MatD3mons.BloodyMurder.Commande.Context;
import fr.MatD3mons.BloodyMurder.Game.Game;
import fr.MatD3mons.BloodyMurder.Game.GameManager;

public class CmdAddSpawn extends Cmd {

    public CmdAddSpawn() {
        super();
        this.aliases.addAll(Aliases.AddSpawn);
    }

    @Override
    public void perform(Context context) {
        if(context.args.size() <= 1){return;}
        Game g = GameManager.games.get(context.args.get(1));
        if(g == null){return;}
        if(context.args.size() == 2){return;}
        g.addspawn(context.player);
    }
}
