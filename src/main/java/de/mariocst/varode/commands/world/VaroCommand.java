package de.mariocst.varode.commands.world;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.level.Sound;
import de.mariocst.varode.VaroDE;
import de.mariocst.varode.config.VaroLastPosConfig;

public class VaroCommand extends Command {
    private final VaroDE plugin;

    public VaroCommand(VaroDE plugin) {
        super("varo", "Teleportiert dich in die Varo Welt", "varo");
        this.setPermission("mario.varo");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("mario.varo") || player.hasPermission("mario.*") || player.hasPermission("*") || sender.isOp()) {
                VaroLastPosConfig c = VaroLastPosConfig.getVaroLastPosConfig();

                if (c.getUUIDs().contains(player.getUniqueId())) {
                    player.teleport(new Location(c.getX(player.getUniqueId()),
                            c.getY(player.getUniqueId()),
                            c.getZ(player.getUniqueId()),
                            c.getYaw(player.getUniqueId()),
                            c.getPitch(player.getUniqueId()),
                            VaroDE.getInstance().getServer().getLevelByName("Varo")));
                }
                else {
                    Level l = VaroDE.getInstance().getServer().getLevelByName("Varo");

                    player.teleport(new Location(l.getSpawnLocation().getX(),
                            l.getSpawnLocation().getY(),
                            l.getSpawnLocation().getZ(),
                            l));
                }

                player.sendMessage(VaroDE.getPrefix() + "Du wurdest in die Varo Welt teleportiert!");
            } else {
                player.sendMessage(VaroDE.getPrefix() + "Keine Rechte!");
                player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
            }
        } else {
            assert false;
            sender.sendMessage(VaroDE.getPrefix() + "Bitte führe den Command InGame aus!");
        }
        return false;
    }
}
