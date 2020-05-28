package fr.MatD3mons.BloodyMurder.GameComponents;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class Sword {

    private ArmorStand Body;
    private Location initialise;
    private Location loc;
    private Location standStart;
    private Vector vec;

    public Vector getVec() { return vec; }
    public Location getInitialise(){ return initialise; }
    public ArmorStand getBody() { return Body; }
    public Location getLoc() { return loc; }
    public Location getStandStart() { return standStart; }

    public Sword(Player attacker){
        loc = attacker.getLocation();
        vec = attacker.getLocation().getDirection();
        vec.normalize().multiply(0.65);
        standStart = rotateAroundAxisY(new Vector(1.0D, 0.0D, 0.0D), loc.getYaw()).toLocation(attacker.getWorld()).add(loc);
        standStart.setYaw(loc.getYaw());
        Body = (ArmorStand) attacker.getWorld().spawnEntity(standStart, EntityType.ARMOR_STAND);
        Body.setVisible(false);
        Body.setCanPickupItems(true);
        Body.setItemInHand(new ItemStack(Material.DIAMOND_SWORD,1));
        Body.setRightArmPose(new EulerAngle(Math.toRadians(350.0), Math.toRadians(attacker.getLocation().getPitch() * -1.0), Math.toRadians(90.0)));
        Body.setGravity(false);
        Body.setRemoveWhenFarAway(true);
        Body.setMarker(true);
        this.initialise = rotateAroundAxisY(new Vector(-0.8D, 1.45D, 0.0D), loc.getYaw()).toLocation(attacker.getWorld()).add(standStart).add(rotateAroundAxisY(rotateAroundAxisX(new Vector(0.0D, 0.0D, 1.0D), loc.getPitch()), loc.getYaw()));
    }

    public static Vector rotateAroundAxisX(Vector v, double angle) {
        angle = Math.toRadians(angle);
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double y = v.getY() * cos - v.getZ() * sin;
        double z = v.getY() * sin + v.getZ() * cos;
        return v.setY(y).setZ(z);
    }

    public static Vector rotateAroundAxisY(Vector v, double angle) {
        angle = -angle;
        angle = Math.toRadians(angle);
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double x = v.getX() * cos + v.getZ() * sin;
        double z = v.getX() * -sin + v.getZ() * cos;
        return v.setX(x).setZ(z);
    }
}
