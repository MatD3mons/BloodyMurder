package fr.MatD3mons.BloodyMurder.gui;

import fr.MatD3mons.BloodyMurder.Game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;

public class GuiGame extends Gui {

    private Game game;

    public GuiGame(InventoryHolder owner, int size, String title, Game game) {
        super(owner, size, title);
        this.game = game;
    }

    //TODO crée 3 Tête, Murder Detective, Innocent

    @Override
    public void update(Player p) {
        //TODO faire la vison des role + pourvoir modifier
        // Mettre le nombre de place == le nombre de joeur dans la parti
        // default --> Murder, Innocent, Detective, Innocent....
        //click dessus --> droit = Murder, central = Detective, gauche = Innocent;
    }

    @Override
    public void onClick(InventoryClickEvent e) {

    }

    @Override
    public void onClose(InventoryCloseEvent e) {

    }
}
