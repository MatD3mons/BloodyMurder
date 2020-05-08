package fr.MatD3mons.BloodyMurder.Commande;

import org.bukkit.command.TabCompleter;

import java.util.*;


public abstract class Cmd{

    public List<String> aliases;
    public List<Cmd> subCommands;


    public Cmd() {
        this.aliases = new ArrayList<>();
        this.subCommands = new ArrayList<>();
    }

    public void execute(Context context){
        if(context.args.size() > 0) {
            for (Cmd subCommand: this.subCommands){
                if(subCommand.aliases.contains(context.args.get(0).toLowerCase())){
                    context.args.remove(0);
                    subCommand.execute(context);
                    context.commandChain.add(this);
                    return;
                }
            }
        }
        if (!valideContext(context)) {
            return;
        }
        perform(context);
    }

    public void addSubCommand(Cmd subCommand){
        this.subCommands.add(subCommand);
    }

    public abstract void perform(Context context);

    public Boolean valideContext(Context context){
        return true;
    }
}
