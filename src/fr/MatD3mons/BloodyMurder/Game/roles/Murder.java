package fr.MatD3mons.BloodyMurder.Game.roles;

import fr.MatD3mons.BloodyMurder.BloodyMurder;
import fr.MatD3mons.BloodyMurder.Game.Game;
import fr.MatD3mons.BloodyMurder.Game.Role;
import fr.MatD3mons.BloodyMurder.Game.Roles;
import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import fr.MatD3mons.BloodyMurder.utile.Repository;
import fr.MatD3mons.BloodyMurder.utile.util;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class Murder extends Role {

    private int timer = 6;
    public Murder(){}

    @Override
    public boolean EntityDamageByEntity(EntityDamageByEntityEvent e){
        if (!(e.getDamager() instanceof Player)){ return false;}
        Player killer = (Player) e.getDamager();

        if (Repository.findBloodyPlayer(killer).getGame() == null) {return false;}
        Game g = Repository.findBloodyPlayer(killer).getGame();

        if (g.getMode() != Game.GameMode.GAME) {return false;}
        Player p2 = (Player) e.getEntity();

        if (Repository.findBloodyPlayer(killer).getRole() != Roles.Murder) {return false; }

        if (killer.getItemInHand().getType() != Material.DIAMOND_SWORD) {return false;}

        if (killer.getInventory().contains(Material.RED_SANDSTONE)){
            p2.getInventory().clear();
            if (!(Repository.findBloodyPlayer(p2).getRole() == Roles.Murder)) {
                e.setDamage(100);
                Repository.findBloodyPlayer(killer).addkills();
            } else {
                util.sendTitle(killer, "", "§c§lC'est ton ally dommage ):", 0, 2, 0);
            }
            for (int i = 0; i < killer.getInventory().getSize(); i++) {
                if (killer.getInventory().getItem(i) != null)
                    if (killer.getInventory().getItem(i).getType() == Material.RED_SANDSTONE) {
                        int j = killer.getInventory().getItem(i).getAmount();
                        if (j == 1)
                            killer.getInventory().remove(Material.RED_SANDSTONE);
                        else
                            killer.getInventory().getItem(i).setAmount(j - 1);
                    }
            }
        }else{
            util.sendTitle(killer, "", "§c§lTu n'as pas de soif de sang", 0, 3, 0);
        }
        return false;
    }

    @Override
    public void message(BloodyPlayer b){
        util.sendTitle(b.getPlayerInstance(), "§4§lTu es Murder !", "§cTu dois récupérer 5 or pour Tuer une personne", 0, 3, 0);
    }

    @Override
    public void stuff(BloodyPlayer b){
        ItemStack itemStack = util.create(Material.DIAMOND_SWORD,1,ChatColor.AQUA,"Epée du Murder");
        b.getPlayerInstance().getInventory().setItem(0,itemStack);
    }

    @Override
    public void fin(BloodyPlayer b){
        b.getPlayerInstance().getInventory().clear();
        if(b.getGame().murderleftsixe() == 1)
            util.sendTitle(b.getPlayerInstance(), "§c§l Le MURDER a gagné", "", 0, 3, 0);
        else
            util.sendTitle(b.getPlayerInstance(), "§c§l Les MURDER ont gagné", "", 0, 3, 0);
    }

    @Override
    public String toString(){
        return "Murder";
    }

    @Override
    public void take(BloodyPlayer b){
        for(int i = 0; i < b.getPlayerInstance().getInventory().getSize(); i++) {
            if(b.getPlayerInstance().getInventory().getItem(i) != null)
            if (b.getPlayerInstance().getInventory().getItem(i).getType() == Material.GOLD_INGOT) {
                int j = b.getPlayerInstance().getInventory().getItem(i).getAmount();
                if ( j >= 5){
                    if(j == 5)
                        b.getPlayerInstance().getInventory().remove(Material.GOLD_INGOT);
                    else
                        b.getPlayerInstance().getInventory().getItem(i).setAmount(j-5);
                    if(b.getPlayerInstance().getInventory().contains(Material.RED_SANDSTONE)) {
                        ItemStack itemStack = util.create(Material.RED_SANDSTONE, 1, ChatColor.AQUA, "Soif de sang");
                        b.getPlayerInstance().getInventory().addItem(itemStack);
                    }
                    else{
                        ItemStack itemStack = util.create(Material.RED_SANDSTONE, 1, ChatColor.AQUA, "Soif de sang");
                        b.getPlayerInstance().getInventory().setItem(1,itemStack);
                    }
                }
            }
        }
    }

    public void Interact(BloodyPlayer b){
        if(b.getPlayerInstance().getItemInHand().getType() != Material.DIAMOND_SWORD){return;}
        if(timer != 6){return;}
        timer--;
        createFlyingSword(b.getPlayerInstance());
        new BukkitRunnable() {
            @Override
            public void run() {
                timer--;
                util.sendActionBar(b.getPlayerInstance(),"Cooldown épée : "+timer);
                if(timer  <= 0) {
                    timer = 6;
                    cancel();
                }
            }
        }.runTaskTimer(BloodyMurder.getInstance(), 0, 20);
    }

    private void createFlyingSword(Player attacker) {
        Location loc = attacker.getLocation();
        Vector vec = attacker.getLocation().getDirection();
        vec.normalize().multiply(0.65);
        Location standStart = rotateAroundAxisY(new Vector(1.0D, 0.0D, 0.0D), loc.getYaw()).toLocation(attacker.getWorld()).add(loc);
        standStart.setYaw(loc.getYaw());
        ArmorStand stand = (ArmorStand) attacker.getWorld().spawnEntity(standStart, EntityType.ARMOR_STAND);
        stand.setVisible(false);
        stand.setCanPickupItems(true);
        stand.setItemInHand(new ItemStack(Material.DIAMOND_SWORD,1));
        stand.setRightArmPose(new EulerAngle(Math.toRadians(350.0), Math.toRadians(attacker.getLocation().getPitch() * -1.0), Math.toRadians(90.0)));
        stand.setGravity(false);
        stand.setRemoveWhenFarAway(true);
        stand.setMarker(true);
        Location initialise = rotateAroundAxisY(new Vector(-0.8D, 1.45D, 0.0D), loc.getYaw()).toLocation(attacker.getWorld()).add(standStart).add(rotateAroundAxisY(rotateAroundAxisX(new Vector(0.0D, 0.0D, 1.0D), loc.getPitch()), loc.getYaw()));
        int maxRange = 20;
        double maxHitRange = 0.5;
        new BukkitRunnable() {
            @Override
            public void run() {
                stand.teleport(standStart.add(vec));
                initialise.add(vec);
                initialise.getWorld().getNearbyEntities(initialise, maxHitRange, maxHitRange, maxHitRange).forEach(entity -> {
                    if (entity instanceof Player) {
                        Player victim = (Player) entity;
                        if (!(Repository.findBloodyPlayer(victim).getRole() == Roles.Murder)) {
                            victim.damage(100.0);
                            Repository.findBloodyPlayer(attacker).addkills();
                        } else {
                            util.sendTitle(attacker, "", "§c§lC'est ton ally dommage ):", 0, 2, 0);
                        }
                    }
                });
                if (loc.distance(initialise) > maxRange || initialise.getBlock().getType().isSolid()) {
                    this.cancel();
                    stand.remove();
                }
            }
        }.runTaskTimer(BloodyMurder.getInstance(), 0, 1);
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

    @Override
    public void death(BloodyPlayer b) {
        Game g = b.getGame();
        g.supMurder(b);
        if (g.murderleftsixe() <= 0) {
            g.setEnd(Roles.Innocent);
        } else {
            util.sendTitle(b.getGame(),"", "§a§lil reste: §b§l" + (g.murderleftsixe()) + " §a§lMurder", 0, 3, 0);
        }
    }
}
