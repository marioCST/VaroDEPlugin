package de.mariocst.varode.commands.world;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Location;
import cn.nukkit.level.Sound;
import de.mariocst.varode.VaroDE;

public class LobbyCommand extends Command {
    private final VaroDE plugin;

    public LobbyCommand(VaroDE plugin) {
        super("lobby", "Teleportiert dich in die Lobby Welt", "lobby", new String[]{"l", "hub"});
        this.setPermission("mario.lobby");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("mario.lobby") || player.hasPermission("mario.*") || player.hasPermission("*") || sender.isOp()) {
                if (VaroDE.getInstance().getServer().getLevelByName(VaroDE.getJoinSpawnConfig().getWorldName()) == null) {
                    player.sendMessage(VaroDE.getPrefix() + "Diese Welt existiert gerade nicht. Kontaktiere einen Admin und versuche es später erneut!");
                    return true;
                }

                player.teleport(new Location(VaroDE.getJoinSpawnConfig().getX(), VaroDE.getJoinSpawnConfig().getY(), VaroDE.getJoinSpawnConfig().getZ(), VaroDE.getInstance().getServer().getLevelByName(VaroDE.getJoinSpawnConfig().getWorldName())));

                player.sendMessage(VaroDE.getPrefix() + "Du wurdest in die Lobby teleportiert!");
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
