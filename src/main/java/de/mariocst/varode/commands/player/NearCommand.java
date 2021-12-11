package de.mariocst.varode.commands.player;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.entity.Entity;
import cn.nukkit.level.Sound;
import cn.nukkit.math.AxisAlignedBB;
import de.mariocst.varode.VaroDE;

public class NearCommand extends Command {
    private final VaroDE plugin;

    public NearCommand(VaroDE plugin) {
        super("near", "Schreibt dir die Spieler in deiner Nähe!", "near");
        this.setPermission("mario.near");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("mario.near") || player.hasPermission("mario.*") || player.hasPermission("*") || sender.isOp()) {
                player.sendMessage(VaroDE.getPrefix() + "Diese Spieler sind in deiner Nähe:");
                for (Entity entity : player.getLevel().getNearbyEntities(new AxisAlignedBB() {
                    @Override
                    public double getMinX() {
                        return player.getX() - 25;
                    }

                    @Override
                    public double getMinY() {
                        return player.getY() - 25;
                    }

                    @Override
                    public double getMinZ() {
                        return player.getZ() - 25;
                    }

                    @Override
                    public double getMaxX() {
                        return player.getX() + 25;
                    }

                    @Override
                    public double getMaxY() {
                        return player.getY() + 25;
                    }

                    @Override
                    public double getMaxZ() {
                        return player.getZ() + 25;
                    }

                    @Override
                    public AxisAlignedBB clone() {
                        try {
                            return (AxisAlignedBB) super.clone();
                        }
                        catch (CloneNotSupportedException e) {
                            return null;
                        }
                    }
                })) {
                    if (entity instanceof Player) {
                        Player t = (Player) entity;

                        if (t != player) {
                            player.sendMessage(VaroDE.getPrefix() + t.getName());
                        }
                    }
                }
            } else {
                player.sendMessage(VaroDE.getPrefix() + "Keine Rechte!");
                player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
            }
        } else {
            sender.sendMessage("Bitte führe den Command InGame aus!");
        }

        return false;
    }
}
