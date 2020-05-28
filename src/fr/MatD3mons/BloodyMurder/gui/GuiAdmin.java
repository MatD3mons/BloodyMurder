package fr.MatD3mons.BloodyMurder.gui;

import fr.MatD3mons.BloodyMurder.Game.Game;
import fr.MatD3mons.BloodyMurder.Game.GameManager;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import fr.MatD3mons.BloodyMurder.utile.ItemBuilder;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class GuiAdmin extends Gui {

    private HashMap<Integer,Game> itemGame = new HashMap<>();

    public GuiAdmin(InventoryHolder owner, int size, String title) {
        super(owner, size, title);
    }

    private ItemStack coloredPane = new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 3))
            .setDisplayName(" ")
            .build();

    @Override
    public void update(Player p) {
        int i = 0;
        for(i=0;i<9;i++){setItem(i,coloredPane); }

        String skull_game = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjdiZjAyMzkwZDNmM2Y0Y2NlNGJmZWRjM2MxOTA0ODQxMzhhMzE3NGQ4NTQxYThmZDkxMmViYjIxNDdmY2MwZSJ9fX0";

        for(Game g : GameManager.games.values()){
            ItemStack game = new ItemBuilder(ItemBuilder.getCustomTextureHead(g.getName(), skull_game))
                    .setDisplayName(g.getName())
                    .build();
            itemGame.put(i,g);
            setItem(i,game);
            i++;
            if(i > 27){
                return;
            }
            //TODO crée d'autre page si c'est trop grand
        }
        for(i=27;i<36;i++){setItem(i,coloredPane); }
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        if(e.getCurrentItem() == null) {return;}
        Game g = itemGame.get(e.getSlot());
        if(g != null) {
            Gui gui = new GuiGame(null, getSize(), g.getName(), g);
            BloodyPlayer bp = Repository.findBloodyPlayer(getPlayer());
            bp.setCurrentGui(gui);
        }
    }

    @Override
    public void onClose(InventoryCloseEvent e) {
        Repository.findBloodyPlayer(getPlayer()).setCurrentGui(null);
    }
}
