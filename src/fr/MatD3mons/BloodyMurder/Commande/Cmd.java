package fr.MatD3mons.BloodyMurder.Commande;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public abstract class Cmd {

    public List<String> aliases;
    public List<Cmd> subCommands;


    public Cmd() {
        this.aliases = new ArrayList<>();
        this.subCommands = new ArrayList<>();
    }

    public void execute(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings){
        if(strings.length > 0) {
            for (Cmd subCommand: this.subCommands){
                if(subCommand.aliases.contains(strings[0].toLowerCase())){
                    subCommand.execute(commandSender,command,s,strings);
                    return;
                }
            }
        }
        perform(commandSender,command,s,strings);
    }

    public void addSubCommand(Cmd subCommand){
        this.subCommands.add(subCommand);
    }

    public abstract void perform(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings);

}
