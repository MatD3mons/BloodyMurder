package fr.MatD3mons.BloodyMurder.Commande;

import fr.MatD3mons.BloodyMurder.BloodyMurder;
import fr.MatD3mons.BloodyMurder.Game.GameManager;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BM implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            BloodyPlayer b = Repository.findBloodyPlayer((Player) commandSender);
            if (s.equalsIgnoreCase("BM")) {
                if(strings.length > 0) {
                    switch (strings[0]) {
                        case "setWait":
                            if(b.getGame() != null) { b.getGame().setWait(); }
                            break;
                        case "setGame":
                            if(b.getGame() != null) { b.getGame().setGame(); }
                            break;
                        case "setEnd":
                            if(b.getGame() != null) { b.getGame().setEnd(b.getRole()); }
                            break;
                        case "join":
                            GameManager.rejoind(b);
                            break;
                        case "lobby":
                            GameManager.leave(b);
                            break;
                        case "setor":
                            if(strings[1] != null)
                                GameManager.setor(b,strings[1]);
                            break;
                        default:
                            b.getPlayerInstance().sendMessage("§8§lDiffèrente commande du BloodyMurder :");
                            b.getPlayerInstance().sendMessage("§e§l-/bm setWait      §7§l:permet de mettre votre parti en attend");
                            b.getPlayerInstance().sendMessage("§e§l-/bm setGame      §7§l:permet de mettre votre parti en jeu");
                            b.getPlayerInstance().sendMessage("§e§l-/bm setEND       §7§l:permet de mettre votre parti en fin");
                            b.getPlayerInstance().sendMessage("§e§l-/bm join         §7§l:permet de rejoindre une parti");
                            b.getPlayerInstance().sendMessage("§e§l-/bm lobby        §7§l:permet de retourner au lobby");
                            b.getPlayerInstance().sendMessage("§e§l-/bm setor <game> §7§l:permet d'ajouter de l'or a une game");
                            break;
                    }
                }
                else {
                    b.getPlayerInstance().sendMessage("§8§lDiffèrente commande du BloodyMurder :");
                    b.getPlayerInstance().sendMessage("§e§l-/bm setWait      §7§l:permet de mettre votre parti en attend");
                    b.getPlayerInstance().sendMessage("§e§l-/bm setGame      §7§l:permet de mettre votre parti en jeu");
                    b.getPlayerInstance().sendMessage("§e§l-/bm setEND       §7§l:permet de mettre votre parti en fin");
                    b.getPlayerInstance().sendMessage("§e§l-/bm join         §7§l:permet de rejoindre une parti");
                    b.getPlayerInstance().sendMessage("§e§l-/bm lobby        §7§l:permet de retourner au lobby");
                    b.getPlayerInstance().sendMessage("§e§l-/bm setor <game> §7§l:permet d'ajouter de l'or a une game");}
                return true;
            }
        }
        return false;
    }
}
