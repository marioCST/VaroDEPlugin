package de.mariocst.varode.listeners;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import de.mariocst.varode.config.JoinAndQuitConfig;

public class JoinAndQuitListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.setJoinMessage(JoinAndQuitConfig.getJoinAndQuitConfig().getJoinMessage().replaceAll("%player%", player.getName()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage(JoinAndQuitConfig.getJoinAndQuitConfig().getQuitMessage().replaceAll("%player%", player.getName()));
    }
}
