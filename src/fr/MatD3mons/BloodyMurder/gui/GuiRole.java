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

import java.util.HashMap;

public class GuiRole extends Gui {

    private HashMap<Integer, Roles> listRole;
    private Game game;
    private int indice;

    public GuiRole(InventoryHolder owner, int size, String title, Game game,int indice) {
        super(owner, size,"Role de Game : "+title);
        this.game = game;
        this.indice = indice;
        this.listRole = new HashMap<>();
    }

    @Override
    public void update(Player p) {
        int i = 0;
        for(Roles r : Roles.values()) {
            ItemStack role = new ItemBuilder(ItemBuilder.getCustomTextureHead(r.name(), Roles.getRole(r).getSkull()))
                    .setDisplayName("§5§l"+r.name())
                    .build();
            setItem(i,role);
            listRole.put(i,r);
            i++;
        }
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) { return; }
        game.getlistrole().set(indice,listRole.get(e.getSlot()));
        game.saveRoles();
        update(getPlayer());
        Gui gui = new GuiGame(null, getSize(), game.getName(), game);
        BloodyPlayer bp = Repository.findBloodyPlayer(getPlayer());
        bp.setCurrentGui(gui);
    }

    @Override
    public void onClose(InventoryCloseEvent e)  {
        Repository.findBloodyPlayer(getPlayer()).setCurrentGui(null);
    }
}
