package fr.MatD3mons.BloodyMurder.Commande;

import fr.MatD3mons.BloodyMurder.Commande.cmd.*;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;

public class CmdBase extends Cmd implements CommandExecutor {

    public static CmdBase instance;
    public CmdJoin cmdJoin = new CmdJoin();
    public CmdLeave cmdLeave = new CmdLeave();
    public CmdHelp cmdHelp = new CmdHelp();
    public CmdSetEnd cmdSetEnd = new CmdSetEnd();
    public CmdSetGame cmdSetGame = new CmdSetGame();
    public CmdsetWait cmdsetWait = new CmdsetWait();
    public CmdAddOr cmdAddor = new CmdAddOr();
    public CmdCreate cmdCreate = new CmdCreate();
    public CmdSetLobby cmdSetLobby = new CmdSetLobby();
    public CmdAddSpawn cmdAddSpawn = new CmdAddSpawn();

    public CmdBase() {
        super();
        instance = this;
        this.addSubCommand(this.cmdJoin);
        this.addSubCommand(this.cmdLeave);
        this.addSubCommand(this.cmdHelp);
        this.addSubCommand(this.cmdSetEnd);
        this.addSubCommand(this.cmdSetGame);
        this.addSubCommand(this.cmdsetWait);
        this.addSubCommand(this.cmdAddor);
        this.addSubCommand(this.cmdCreate);
        this.addSubCommand(this.cmdSetLobby);
        this.addSubCommand(this.cmdAddSpawn);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        this.execute(new Context(commandSender, new ArrayList<>(Arrays.asList(strings)), s));
        return true;
    }

    public void addSubCommand(Cmd subCommand){
        super.addSubCommand(subCommand);
    }

    @Override
    public void perform(Context context) {
        context.commandChain.add(this);
        this.cmdHelp.execute(context);
    }
}
