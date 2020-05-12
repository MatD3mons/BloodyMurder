package fr.MatD3mons.BloodyMurder.Commande.cmd;

import fr.MatD3mons.BloodyMurder.Commande.Aliases;
import fr.MatD3mons.BloodyMurder.Commande.Cmd;
import fr.MatD3mons.BloodyMurder.Commande.Context;
import org.bukkit.entity.Player;

public class CmdHelp extends Cmd {

    public CmdHelp() {
        super();
        this.aliases.addAll(Aliases.help);
    }

    @Override
    public void perform(Context context){
        Player player = context.player;
        player.sendMessage("§8§lDiffèrente commande du BloodyMurder :");
        player.sendMessage("§e§l-/bm setWait      §7§l:permet de mettre votre parti en attend");
        player.sendMessage("§e§l-/bm setGame      §7§l:permet de mettre votre parti en jeu");
        player.sendMessage("§e§l-/bm setEND       §7§l:permet de mettre votre parti en fin");
        player.sendMessage("§e§l-/bm join         §7§l:permet de rejoindre une parti");
        player.sendMessage("§e§l-/bm lobby        §7§l:permet de retourner au lobby");
        player.sendMessage("§e§l-/bm setor <game> §7§l:permet d'ajouter de l'or a une game");
    }

}
