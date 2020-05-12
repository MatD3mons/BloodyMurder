package fr.MatD3mons.BloodyMurder.Commande.cmd;

import fr.MatD3mons.BloodyMurder.Commande.Aliases;
import fr.MatD3mons.BloodyMurder.Commande.Cmd;
import fr.MatD3mons.BloodyMurder.Commande.Context;
import fr.MatD3mons.BloodyMurder.Game.GameManager;

public class CmdCreate extends Cmd {

    public CmdCreate() {
        super();
        this.aliases.addAll(Aliases.Create);
    }

    @Override
    public void perform(Context context) {
        if(context.args.size() <= 1){return;}
        GameManager.addgame(context.args.get(1));
        context.player.sendMessage("La map "+context.args.get(1)+" a était crée");
    }
}
