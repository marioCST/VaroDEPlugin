package de.mariocst.varode.anticheat.checks.world;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.level.Location;
import de.mariocst.varode.VaroDE;

public class BlockReach implements Listener {
    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (!VaroDE.getAntiCheatConfig().isBlockReach()) return;

        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (player.hasPermission("mario.anticheat.bypass.blockreach") || player.hasPermission("mario.anticheat.bypass.*") ||
                player.hasPermission("*") || player.isOp()) return;

        Location playerLoc = player.getLocation();
        Location blockLoc = block.getLocation();

        double distance = playerLoc.distance(blockLoc.getX(), blockLoc.getY(), blockLoc.getZ());

        if (distance > VaroDE.getAntiCheatConfig().getMaxBlockReach()) {
            event.setCancelled(true);
            VaroDE.getInstance().flag("BlockReach", "Distance: " + distance, player);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!VaroDE.getAntiCheatConfig().isBlockReach()) return;

        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (player.hasPermission("mario.anticheat.bypass.blockreach") || player.hasPermission("mario.anticheat.bypass.*") ||
                player.hasPermission("*") || player.isOp()) return;

        Location playerLoc = player.getLocation();
        Location blockLoc = block.getLocation();

        double distance = playerLoc.distance(blockLoc.getX(), blockLoc.getY(), blockLoc.getZ());

        if (distance > VaroDE.getAntiCheatConfig().getMaxBlockReach()) {
            event.setCancelled(true);
            VaroDE.getInstance().flag("BlockReach", "Distance: " + distance, player);
        }
    }
}
