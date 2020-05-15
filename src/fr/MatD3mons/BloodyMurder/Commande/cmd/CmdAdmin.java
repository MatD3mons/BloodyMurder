package fr.MatD3mons.BloodyMurder.Commande.cmd;

import fr.MatD3mons.BloodyMurder.Commande.Aliases;
import fr.MatD3mons.BloodyMurder.Commande.Cmd;
import fr.MatD3mons.BloodyMurder.Commande.Context;
import fr.MatD3mons.BloodyMurder.gui.GuiAdmin;

public class CmdAdmin extends Cmd {

    public CmdAdmin() {
        super();
        this.aliases.addAll(Aliases.Admin);
    }

    @Override
    public void perform(Context context) {
        context.player.sendMessage("Ouverture du Gui Admin");
        context.bloodyPlayer.setCurrentGui(new GuiAdmin(null, 36, "Bloody Murder Admin"));
    }
}
