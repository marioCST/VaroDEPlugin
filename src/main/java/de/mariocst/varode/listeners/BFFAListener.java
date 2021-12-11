package de.mariocst.varode.listeners;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.Location;
import de.mariocst.varode.entity.EntityFakePlayer;
import de.mariocst.varode.VaroDE;

public class BFFAListener implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (!player.getLevel().getName().equalsIgnoreCase("ComingSoon!") || player.getGamemode() == 1 || player.getGamemode() == 3) return;

        if (player.getY() <= 10) {
            String deathMessage = "";

            if (player.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
                final EntityDamageByEntityEvent ev = (EntityDamageByEntityEvent)player.getLastDamageCause();
                final Entity damager = ev.getDamager();
                if (damager instanceof Player && damager != player) {
                    deathMessage = player.getDisplayName() + " wurde von " + ((Player) damager).getDisplayName() + " ins Void geschlagen!";
                }
            }

            PlayerDeathEvent event1 = new PlayerDeathEvent(player, Item.EMPTY_ARRAY, deathMessage, 0);
            VaroDE.getInstance().getServer().getPluginManager().callEvent(event1);

            EntityFakePlayer fakePlayer = new EntityFakePlayer(player.getChunk(), Entity.getDefaultNBT(player.getLocation(), player.getMotion(), (float)player.getYaw(), (float)player.getPitch()));
            fakePlayer.setSkin(player.getSkin());

            EntityDamageEvent debug = new EntityDamageEvent(player, EntityDamageEvent.DamageCause.VOID, 1);
            EntityDamageByEntityEvent debug1 = new EntityDamageByEntityEvent(fakePlayer, player, EntityDamageEvent.DamageCause.ENTITY_ATTACK, 1);
            VaroDE.getInstance().getServer().getPluginManager().callEvent(debug1);
            VaroDE.getInstance().getServer().getPluginManager().callEvent(debug);

            player.heal(30);
            player.getFoodData().setLevel(20);

            player.teleport(new Location(0.5, 46.0, 0.5));
        }
    }
}
