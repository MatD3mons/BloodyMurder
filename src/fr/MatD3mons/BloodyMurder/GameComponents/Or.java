package fr.MatD3mons.BloodyMurder.GameComponents;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Or {

    private Location location;
    private int free;
    private ArmorStand body;
    private static ItemStack or = new ItemStack(Material.YELLOW_FLOWER,1);
    private static ItemStack air = new ItemStack(Material.AIR,1);

    public Or(Location location) {
        this.location = location;
        this.body = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        //this.body.setVisible(false);
        this.body.setCanPickupItems(true);
        this.body.setGravity(false);
        this.body.setRemoveWhenFarAway(true);
        this.body.setMarker(true);
        this.free = 5;
        System.out.println(this.body);
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
                this.body.setItemInHand(or);
                System.out.println("or");
            }
        }
    }

    public void removefree(){
        this.body.setItemInHand(air);
        this.free = 5;
    }
}
