package de.mariocst.varode.listeners;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityLevelChangeEvent;
import cn.nukkit.event.player.PlayerChatEvent;
import de.mariocst.varode.VaroDE;
import de.mariocst.varode.config.MasterConfig;

import java.util.HashSet;
import java.util.Set;

public class PerWorldChatListener implements Listener {
    @EventHandler
    public void onChat(PlayerChatEvent event) {
        if (!MasterConfig.getMasterConfig().isPerWorldChat()) return;

        Player player = event.getPlayer();
        Set<CommandSender> set = new HashSet<>();
        set.add(VaroDE.getInstance().getServer().getConsoleSender());

        for (Entity entity : player.getLevel().getEntities()) {
            if (entity instanceof Player) {
                Player player1 = (Player) entity;

                set.add(player1);
            }
        }

        event.setRecipients(set);
    }

    @EventHandler
    public void onLevelChange(EntityLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();

        VaroDE.getInstance().log("Der Spieler " + player.getName() + " ging von der Welt " + event.getOrigin().getName() + " zu " + event.getTarget().getName() + "!");

        if (!MasterConfig.getMasterConfig().isLevelChangeMSG()) return;

        for (Entity entity : event.getTarget().getEntities()) {
            if (entity instanceof Player) {
                Player player1 = (Player) entity;

                player1.sendMessage(VaroDE.getPrefix() + "Der Spieler " + player.getDisplayName() + " ist nun in deiner Welt!");
            }
        }

        for (Entity entity : event.getOrigin().getEntities()) {
            if (entity instanceof Player) {
                Player player1 = (Player) entity;

                player1.sendMessage(VaroDE.getPrefix() + "Der Spieler " + player.getDisplayName() + " ist nun nicht mehr in deiner Welt!");
            }
        }
    }
}
