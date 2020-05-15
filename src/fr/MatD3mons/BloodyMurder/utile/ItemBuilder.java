package fr.MatD3mons.BloodyMurder.utile;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {

    private ItemStack instance;

    private List<String> description;
    private List<String> lore = new ArrayList<>();

    public ItemBuilder() {
        instance = new ItemStack(Material.AIR);
    }
    public ItemBuilder(Material mat) {
        instance = new ItemStack(mat);
    }
    public ItemBuilder(ItemStack itemStack) {
        instance = itemStack;
    }

    public ItemStack build() {
        ItemMeta itemMeta = instance.getItemMeta();
        itemMeta.setLore(lore);
        instance.setItemMeta(itemMeta);
        return instance;
    }

    public ItemBuilder setDisplayName(String displayName) {
        ItemMeta meta = instance.getItemMeta();
        meta.setDisplayName(displayName);
        instance.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setMaterial(Material mat) {
        instance.setType(mat);
        return this;
    }

    public ItemBuilder setEnchantment(Enchantment enchantment, int lvl) {
        instance.addUnsafeEnchantment(enchantment, lvl);
        return this;
    }


    public ItemBuilder setDescription(String... description) {
        this.description = Arrays.asList(description);
        return this;
    }

    public ItemBuilder setDescription(List<String> description) {
        this.description = description;
        return this;
    }

    public static ItemStack getCustomTextureHead(String itemName, String value) {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.nameUUIDFromBytes(("MURDERITEM:"+itemName).getBytes()), "");
        profile.getProperties().put("textures", new Property("textures", value));
        Field profileField = null;
        try {
            profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        head.setItemMeta(meta);
        return head;
    }

}
