package fr.MatD3mons.BloodyMurder.gui;

import fr.MatD3mons.BloodyMurder.Game.Game;
import fr.MatD3mons.BloodyMurder.Game.Role;
import fr.MatD3mons.BloodyMurder.Game.Roles;
import fr.MatD3mons.BloodyMurder.utile.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class GuiGame extends Gui {

    private Game game;

    public GuiGame(InventoryHolder owner, int size, String title, Game game) {
        super(owner, size, title);
        this.game = game;
    }

    //TODO crée 3 Tête, Murder Detective, Innocent

    @Override
    public void update(Player p) {
        String skull_game = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjdiZjAyMzkwZDNmM2Y0Y2NlNGJmZWRjM2MxOTA0ODQxMzhhMzE3NGQ4NTQxYThmZDkxMmViYjIxNDdmY2MwZSJ9fX0";
        int i = 0;
        for(Roles r : game.getlistrole()) {
            ItemStack game = new ItemBuilder(ItemBuilder.getCustomTextureHead(r.name(), skull_game))
                    .setDisplayName("§5§l"+r.name())
                    .setDescription(Arrays.asList(
                            "",
                            "§7Click gauche : Murder",
                            "§7Click milieu : Detective",
                            "§7Click Droit : Innocent"))
                    .build();
            setItem(i,game);
            i++;
        }

        //TODO faire la vison des role + pourvoir modifier
        // Mettre le nombre de place == le nombre de joeur dans la parti
        // default --> Murder, Innocent, Detective, Innocent....
        //click dessus --> droit = Murder, central = Detective, gauche = Innocent;
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) { return; }
        int indice = e.getSlot();
        switch (e.getClick()) {
            case LEFT:
                game.getlistrole().set(indice, Roles.Murder);
                break;
            case RIGHT:
                game.getlistrole().set(indice, Roles.Innocent);
                break;
            case MIDDLE:
                break;
        }
        update(getPlayer());
    }

    @Override
    public void onClose(InventoryCloseEvent e) {

    }
}
