package de.mariocst.varode.listeners;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.level.ChunkLoadEvent;
import cn.nukkit.level.format.FullChunk;
import de.mariocst.varode.config.MasterConfig;

public class BedrockListener implements Listener {
    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        if (!MasterConfig.getMasterConfig().isBedrockCheckChunkLoad()) return;

        FullChunk chunk = event.getChunk();

        for (int x = 0; x <= 15; x++) {
            for (int z = 0; z <= 15; z++) {
                if (chunk.getBlockId(x, 0, z) != BlockID.BEDROCK && (event.getLevel().getDimension() == 0 || event.getLevel().getDimension() == 1)) {
                    chunk.setBlock(x, 0, z, BlockID.BEDROCK);
                }
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!MasterConfig.getMasterConfig().isBedrockCheckBreak()) return;

        Player player = event.getPlayer();

        if (player.hasPermission("mario.bedrockcheck.bypass") || player.hasPermission("mario.*") || player.hasPermission("*") || player.isOp()) return;

        Block block = event.getBlock();

        if (block.getY() == 0 && block.getId() == BlockID.BEDROCK) event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (!MasterConfig.getMasterConfig().isBedrockCheckPlace()) return;

        Player player = event.getPlayer();

        if (player.hasPermission("mario.bedrockcheck.bypass") || player.hasPermission("mario.*") || player.hasPermission("*") || player.isOp()) return;

        Block block = event.getBlock();

        if (block.getY() > 3 && block.getId() == BlockID.BEDROCK) event.setCancelled(true);
    }
}
