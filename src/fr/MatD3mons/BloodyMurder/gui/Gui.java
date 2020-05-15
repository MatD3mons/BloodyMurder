package fr.MatD3mons.BloodyMurder.gui;

import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryCustom;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.*;

public abstract class Gui extends CraftInventoryCustom implements CraftingInventory, Inventory {

    private Player player;

    public Gui(InventoryHolder owner, int size, String title) {
        super(owner, size, title);
    }

    @Override
    public ItemStack getResult() { return null; }

    @Override
    public ItemStack[] getMatrix() { return new ItemStack[0]; }

    @Override
    public void setResult(ItemStack itemStack) {}

    @Override
    public void setMatrix(ItemStack[] itemStacks) {}

    @Override
    public Recipe getRecipe() { return null; }

    public void open(Player player) {
        if(player != null) {
            setPlayer(player);
            player.openInventory(this);
            final Gui gui = this;
            gui.update(player);
        }
    }

    public abstract void update(Player p);

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public abstract void onClick(InventoryClickEvent e);

    public abstract void onClose(InventoryCloseEvent e);
}
