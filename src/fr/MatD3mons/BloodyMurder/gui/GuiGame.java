package fr.MatD3mons.BloodyMurder.gui;

import fr.MatD3mons.BloodyMurder.Game.Game;
import fr.MatD3mons.BloodyMurder.Game.Roles;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import fr.MatD3mons.BloodyMurder.utile.ItemBuilder;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class GuiGame extends Gui {

    private Game game;

    public GuiGame(InventoryHolder owner, int size, String title, Game game) {
        super(owner, size, "Game : "+title);
        this.game = game;
    }

    @Override
    public void update(Player p) {
        String skull_game = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjdiZjAyMzkwZDNmM2Y0Y2NlNGJmZWRjM2MxOTA0ODQxMzhhMzE3NGQ4NTQxYThmZDkxMmViYjIxNDdmY2MwZSJ9fX0";
        int i = 0;
        for(Roles r : game.getlistrole()) {
            ItemStack game = new ItemBuilder(ItemBuilder.getCustomTextureHead(r.name(), Roles.getRole(r).getSkull()))
                    .setDisplayName("§2§lRole-§4§l"+(i+1)+": §5§l"+r.name())
                    .build();
            setItem(i,game);
            i++;
        }
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) { return; }
        Gui gui = new GuiRole(null, getSize(), game.getName(), game,e.getSlot());
        BloodyPlayer bp = Repository.findBloodyPlayer(getPlayer());
        bp.setCurrentGui(gui);
    }

    @Override
    public void onClose(InventoryCloseEvent e)  {
        Repository.findBloodyPlayer(getPlayer()).setCurrentGui(null);
    }
}
