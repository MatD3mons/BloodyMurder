package fr.MatD3mons.BloodyMurder.GameComponents;

import fr.MatD3mons.BloodyMurder.Game.Roles;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import fr.MatD3mons.BloodyMurder.utile.util;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import java.util.ArrayList;
import java.util.List;

public class Or {

    private int i;
    private Location location;
    private int free;
    private ArmorStand body;
    private static ItemStack or = new ItemStack(Material.DOUBLE_PLANT,1);
    private static ItemStack air = new ItemStack(Material.AIR,1);

    public Or(Location location) {
        this.location = location;
        this.body = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        this.body.setVisible(false);
        this.body.setCanPickupItems(true);
        this.body.setGravity(false);
        this.body.setRemoveWhenFarAway(true);
        this.body.setMarker(true);
        this.body.setHeadPose(new EulerAngle(Math.toRadians(0),Math.toRadians(0),Math.toRadians(0)));
        this.free = 5;
        this.i = 0;
    }

    public static List<Location> getlist(ArrayList<Or> listor) {
        List<Location> list = new ArrayList<>();
        for( Or or: listor)
            list.add(or.location);
        return  list;
    }

    public void setfree() {
        if(this.free >= 0) {
            this.free--;
            if (this.free == 0) {
                this.body.setHelmet(or);
            }
        }
    }

    public void removefree(){
        this.body.setHelmet(air);
        this.free = 5;
    }

    public void rotate() {
        if(this.free > 0) {return; }
        i += 10;
        if(i >= 360)
            i = 0;
        this.body.setHeadPose(new EulerAngle(Math.toRadians(0),Math.toRadians(i),Math.toRadians(0)));
        double maxHitRange = 0.5;
        //TODO check si y'a pas un meillieur moyen de savoir si un joueur est rentré en collision
        location.getWorld().getNearbyEntities(location, maxHitRange, 3*maxHitRange, maxHitRange).forEach(entity -> {
            if (entity instanceof Player) {
                removefree();
                Player p = (Player) entity;
                if (p.getInventory().contains(Material.DOUBLE_PLANT)) {
                    for (int i = 0; i < p.getInventory().getSize(); i++) {
                        if (p.getInventory().getItem(i) != null)
                            if (p.getInventory().getItem(i).getType() == Material.DOUBLE_PLANT) {
                                ItemStack itemStack = util.create(Material.DOUBLE_PLANT, 1, ChatColor.GOLD, "Pièce d'or");
                                p.getInventory().addItem(itemStack);
                            }
                    }
                } else {
                    ItemStack itemStack = util.create(Material.DOUBLE_PLANT, 1, ChatColor.GOLD, "Pièce d'or");
                    p.getInventory().setItem(8, itemStack);
                }
                BloodyPlayer b = Repository.findBloodyPlayer(p);
                p.playSound(p.getLocation(), Sound.MAGMACUBE_JUMP, 2F, 1F);
                Roles.PlayerRoles.get(b.getRole()).take(b);
                b.addGold();
            }
        });
    }

}
