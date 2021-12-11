package de.mariocst.varode.anticheat.checks.combat;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import de.mariocst.varode.VaroDE;

public class SelfHit implements Listener {
    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (!VaroDE.getAntiCheatConfig().isSelfHit()) return;

        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) return;

        Player damager = (Player) event.getDamager();
        Player victim = (Player) event.getEntity();

        if (damager.hasPermission("mario.anticheat.bypass.selfhit") || damager.hasPermission("mario.anticheat.bypass.*") ||
                damager.hasPermission("*") || damager.isOp()) return;

        if (damager == victim) {
            event.setCancelled(true);
            VaroDE.getInstance().flag("SelfHit", "", damager);
        }
    }
}
