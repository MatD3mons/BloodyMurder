package fr.MatD3mons.BloodyMurder.GameComponents;

import fr.MatD3mons.BloodyMurder.BloodyMurder;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import fr.MatD3mons.BloodyMurder.utile.util;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

public class Arc {

    private int i;
    private Location location;
    private ArmorStand body;
    private static ItemStack arc = new ItemStack(Material.BOW,1);

    public Arc(Location location) {
        this.location = location.subtract(0,1,0);
        create();
        rotate();
    }

    public void create(){
        if(this.body != null){return;}
        this.body = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        this.body.setVisible(false);
        this.body.setCanPickupItems(true);
        this.body.setGravity(false);
        this.body.setRemoveWhenFarAway(true);
        this.body.setMarker(true);
        this.body.setHeadPose(new EulerAngle(Math.toRadians(0),Math.toRadians(0),Math.toRadians(0)));
        this.body.setHelmet(arc);
    }

    public void remove(){
        if(this.body == null){return;}
        this.body.remove();
        this.body = null;
    }

    public void rotate() {
        new BukkitRunnable() {
            @Override
            public void run() {
                i++;
                i++;
                if (i >= 360)
                    i = 0;
                body.setHeadPose(new EulerAngle(Math.toRadians(0), Math.toRadians(i), Math.toRadians(0)));
                double maxHitRange = 0.5;
                location.getWorld().getNearbyEntities(location, maxHitRange, 3 * maxHitRange, maxHitRange).forEach(entity -> {
                    System.out.println(entity);
                    if (entity instanceof Player) {
                        Player p = (Player) entity;
                        BloodyPlayer b = Repository.findBloodyPlayer(p);
                        ItemStack itemStack = util.create(Material.BOW,1, ChatColor.AQUA,"Arc du Detective");
                        itemStack.addEnchantment(Enchantment.ARROW_INFINITE,1);
                        b.getPlayerInstance().getInventory().setItem(0,itemStack);
                        ItemStack itemStack2 = util.create(Material.ARROW, 1, ChatColor.AQUA, "flèche");
                        b.getPlayerInstance().getInventory().setItem(1,itemStack2);
                        remove();
                        p.playSound(p.getLocation(), Sound.MAGMACUBE_JUMP, 2F, 1F);
                        cancel();
                    }
                });
            }
        }.runTaskTimer(BloodyMurder.instance, 0, 1);
    }
}
