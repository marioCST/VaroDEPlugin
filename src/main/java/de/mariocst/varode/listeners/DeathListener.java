package de.mariocst.varode.listeners;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerDeathEvent;
import de.mariocst.varode.VaroDE;

public class DeathListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (event.getEntity().getLevel().getName().equalsIgnoreCase("Lobby")) return;

        for (Entity entity : event.getEntity().getLevel().getEntities()) {
            if (entity instanceof Player) {
                Player player = (Player) entity;

                player.sendMessage(event.getDeathMessage());
            }
        }

        VaroDE.getInstance().getServer().getConsoleSender().sendMessage(event.getDeathMessage());
    }
}
