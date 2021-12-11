package de.mariocst.varode.anticheat.checks.combat;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.level.Location;
import de.mariocst.varode.VaroDE;

public class ReachA implements Listener {
    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (!VaroDE.getAntiCheatConfig().isReachA()) return;

        if (!(event.getDamager() instanceof Player)) return;

        Player damager = (Player) event.getDamager();
        Entity victim = event.getEntity();

        if (damager.hasPermission("mario.anticheat.bypass.reacha") || damager.hasPermission("mario.anticheat.bypass.*") ||
                damager.hasPermission("*") || damager.isOp()) return;

        Location damagerLoc = damager.getLocation().getLocation().add(0.0, 1.0, 0.0);
        Location victimLoc = victim.getLocation();

        if (victim.getLocation().getY() < damager.getLocation().getY()) {
            victimLoc = victim.getLocation().add(0.0, 1.0, 0.0);
        }

        double difX = victimLoc.getX() - damagerLoc.getX();
        double difY = victimLoc.getY() - damagerLoc.getY();
        double difZ = victimLoc.getZ() - damagerLoc.getZ();

        double difSqrtX = difX * difX;
        double difSqrtY = difY * difY;
        double difSqrtZ = difZ * difZ;

        double difSqrt = difSqrtX + difSqrtY + difSqrtZ;

        double distance = Math.cbrt(difSqrt);

        if (damager.getGamemode() == 1) {
            if (distance > VaroDE.getAntiCheatConfig().getMaxReach() + 3.0) {
                event.setCancelled(true);
                VaroDE.getInstance().flag("ReachA", "Reach: " + distance, damager);
            }
        }
        else {
            if (distance > VaroDE.getAntiCheatConfig().getMaxReach()) {
                event.setCancelled(true);
                VaroDE.getInstance().flag("ReachA", "Reach: " + distance, damager);
            }
        }
    }
}
