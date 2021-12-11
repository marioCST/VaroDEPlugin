package de.mariocst.varode.listeners;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.level.Location;
import de.mariocst.varode.config.JoinSpawnConfig;
import de.mariocst.varode.VaroDE;

public class JoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission("mario.join.bypass") || player.hasPermission("mario.*")) return;

        JoinSpawnConfig config = VaroDE.getJoinSpawnConfig();

        if (VaroDE.getInstance().getServer().getLevelByName(config.getWorldName()) == null) return;

        player.teleport(new Location(config.getX(), config.getY(), config.getZ(), config.getYaw(), config.getPitch(), VaroDE.getInstance().getServer().getLevelByName(config.getWorldName())));
    }
}
