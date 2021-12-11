package de.mariocst.varode.listeners;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityLevelChangeEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import de.mariocst.varode.config.VaroLastPosConfig;

public class VaroPositionListener implements Listener {
    @EventHandler
    public void onLevelChange(EntityLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player) || !event.getOrigin().getName().equalsIgnoreCase("Varo")) return;

        Player player = (Player) event.getEntity();

        VaroLastPosConfig c = VaroLastPosConfig.getVaroLastPosConfig();

        c.addUUID(player.getUniqueId());
        c.addX(player.getUniqueId(), player.getX());
        c.addY(player.getUniqueId(), player.getY());
        c.addZ(player.getUniqueId(), player.getZ());
        c.addYaw(player.getUniqueId(), player.getYaw());
        c.addPitch(player.getUniqueId(), player.getPitch());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (!player.getLevel().getName().equalsIgnoreCase("Varo")) return;

        VaroLastPosConfig c = VaroLastPosConfig.getVaroLastPosConfig();

        c.addUUID(player.getUniqueId());
        c.addX(player.getUniqueId(), player.getX());
        c.addY(player.getUniqueId(), player.getY());
        c.addZ(player.getUniqueId(), player.getZ());
        c.addYaw(player.getUniqueId(), player.getYaw());
        c.addPitch(player.getUniqueId(), player.getPitch());
    }
}
