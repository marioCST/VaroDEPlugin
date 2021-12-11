package de.mariocst.varode.listeners;

import cn.nukkit.Player;
import cn.nukkit.block.BlockID;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.ProjectileLaunchEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.item.ItemID;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.InventoryTransactionPacket;
import de.mariocst.varode.VaroDE;
import de.mariocst.varode.entity.EntityFireCharge;

public class FireChargeListener implements Listener {
    @EventHandler
    public void onTransaction(DataPacketReceiveEvent event) {
        Player player = event.getPlayer();

        try {
            if (!(event.getPacket() instanceof InventoryTransactionPacket)) return;

            if (!player.hasPermission("mario.firecharge") && !player.hasPermission("mario.*") && !player.hasPermission("*") && !player.isOp()) return;

            InventoryTransactionPacket packet = (InventoryTransactionPacket) event.getPacket();

            if (packet.transactionType != 2) return;

            if (player.getInventory().getItemInHand().getId() == ItemID.FIRE_CHARGE) {
                try {
                    if (player.getTargetBlock(6).getId() == BlockID.AIR || player.getTargetBlock(6) == null) {
                        EntityFireCharge fireball = new EntityFireCharge(player.getChunk(), Entity.getDefaultNBT(new Vector3(player.getX(), player.getY() + 1, player.getZ()), player.getDirectionVector().multiply(VaroDE.getInstance().getMasterConfig().getMultiplier())), player);
                        fireball.setExplode(true);
                        fireball.setMotion(player.getDirectionVector().multiply(VaroDE.getInstance().getMasterConfig().getMultiplier()));
                        fireball.setForce(VaroDE.getInstance().getMasterConfig().getForce());

                        ProjectileLaunchEvent launch = new ProjectileLaunchEvent(fireball);
                        VaroDE.getInstance().getServer().getPluginManager().callEvent(launch);
                        if (launch.isCancelled()) {
                            fireball.close();
                        } else {
                            fireball.spawnToAll();
                        }
                    }
                }
                catch (NullPointerException ignored) { }
            }
        }
        catch (NullPointerException ignored) { }
    }
}
