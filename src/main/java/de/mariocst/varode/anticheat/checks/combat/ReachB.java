package de.mariocst.varode.anticheat.checks.combat;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.level.Location;
import de.mariocst.varode.VaroDE;

public class ReachB implements Listener {
    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (!VaroDE.getAntiCheatConfig().isReachB()) return;

        if (!(event.getDamager() instanceof Player)) return;

        Player damager = (Player) event.getDamager();
        Entity victim = event.getEntity();

        if (damager.hasPermission("mario.anticheat.bypass.reachb") || damager.hasPermission("mario.anticheat.bypass.*") ||
                damager.hasPermission("*") || damager.isOp()) return;

        Location victimLoc = victim.getLocation();

        if (victim.getLocation().getY() < damager.getLocation().getY()) {
            victimLoc = victim.getLocation().add(0.0, 1.0, 0.0);
        }

        double distance = damager.distance(victimLoc.getX(), victimLoc.getY(), victimLoc.getZ());

        if (damager.getGamemode() == 1) {
            if (distance > VaroDE.getAntiCheatConfig().getMaxReach() + 3.0) {
                event.setCancelled(true);
                VaroDE.getInstance().flag("ReachB", "Reach: " + distance, damager);
            }
        }
        else {
            if (distance > VaroDE.getAntiCheatConfig().getMaxReach()) {
                event.setCancelled(true);
                VaroDE.getInstance().flag("ReachB", "Reach: " + distance, damager);
            }
        }
    }
}
