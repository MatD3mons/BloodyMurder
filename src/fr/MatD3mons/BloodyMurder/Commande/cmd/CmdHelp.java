package fr.MatD3mons.BloodyMurder.Commande.cmd;

import fr.MatD3mons.BloodyMurder.Commande.Aliases;
import fr.MatD3mons.BloodyMurder.Commande.Cmd;
import fr.MatD3mons.BloodyMurder.Commande.Context;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;

public class CmdHelp extends Cmd {

    public CmdHelp() {
        super();
        this.aliases.addAll(Aliases.help);
    }

    @Override
    public void perform(Context context){
        BloodyPlayer b = context.bloodyPlayer;
        b.getPlayerInstance().sendMessage("§8§lDiffèrente commande du BloodyMurder :");
        b.getPlayerInstance().sendMessage("§e§l-/bm setWait      §7§l:permet de mettre votre parti en attend");
        b.getPlayerInstance().sendMessage("§e§l-/bm setGame      §7§l:permet de mettre votre parti en jeu");
        b.getPlayerInstance().sendMessage("§e§l-/bm setEND       §7§l:permet de mettre votre parti en fin");
        b.getPlayerInstance().sendMessage("§e§l-/bm join         §7§l:permet de rejoindre une parti");
        b.getPlayerInstance().sendMessage("§e§l-/bm lobby        §7§l:permet de retourner au lobby");
        b.getPlayerInstance().sendMessage("§e§l-/bm setor <game> §7§l:permet d'ajouter de l'or a une game");
    }

}
