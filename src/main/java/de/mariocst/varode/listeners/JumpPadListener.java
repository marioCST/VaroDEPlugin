package de.mariocst.varode.listeners;

import cn.nukkit.Player;
import cn.nukkit.block.BlockID;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.math.Vector3;
import de.mariocst.varode.VaroDE;

public class JumpPadListener implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player.getLevel().getName().equals(VaroDE.getJoinSpawnConfig().getWorldName())) {
            if (player.getLocation().getLevelBlock().getId() == BlockID.STONE_PRESSURE_PLATE) {
                Vector3 vec3 = player.getDirectionVector().multiply(2).add(0, 1, 0);
                player.setMotion(vec3);
            }
        }
    }
}
