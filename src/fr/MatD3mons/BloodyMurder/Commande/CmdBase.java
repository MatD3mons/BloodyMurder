package fr.MatD3mons.BloodyMurder.Commande;

import fr.MatD3mons.BloodyMurder.Commande.cmd.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CmdBase extends Cmd implements CommandExecutor {

    public static CmdBase instance;
    public CmdJoin cmdJoin = new CmdJoin();
    public CmdLobby cmdLobby = new CmdLobby();
    public CmdHelp cmdHelp = new CmdHelp();
    public CmdSetEnd cmdSetEnd = new CmdSetEnd();
    public CmdSetGame cmdSetGame = new CmdSetGame();
    public CmdsetWait cmdsetWait = new CmdsetWait();
    public CmdSetor cmdSetor = new CmdSetor();

    public CmdBase() {
        super();
        instance = this;
        this.addSubCommand(this.cmdJoin);
        this.addSubCommand(this.cmdLobby);
        this.addSubCommand(this.cmdHelp);
        this.addSubCommand(this.cmdSetEnd);
        this.addSubCommand(this.cmdSetGame);
        this.addSubCommand(this.cmdsetWait);
        this.addSubCommand(this.cmdSetor);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        this.execute( commandSender, command,  s, strings);
        return true;
    }

    public void addSubCommand(Cmd subCommand){
        super.addSubCommand(subCommand);
    }

    @Override
    public void perform(CommandSender commandSender, Command command, String s, String[] strings) {
        this.cmdHelp.execute(commandSender,command,s,strings);
    }

}
