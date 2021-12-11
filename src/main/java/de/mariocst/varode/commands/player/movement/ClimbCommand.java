package de.mariocst.varode.commands.player.movement;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Sound;
import de.mariocst.varode.VaroDE;

public class ClimbCommand extends Command {
    private final VaroDE plugin;

    public ClimbCommand(VaroDE plugin) {
        super("climb", "Klettere überall, auch in der Luft", "climb");
        this.setPermission("mario.climb");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("mario.climb") || player.hasPermission("mario.*") || player.hasPermission("*") || sender.isOp()) {
                if (player.canClimbWalls()) {
                    player.setCanClimbWalls(false);
                    player.sendMessage(VaroDE.getPrefix() + "Du kannst nun nicht mehr überall hochklettern!");
                }
                else {
                    player.setCanClimbWalls(true);
                    player.sendMessage(VaroDE.getPrefix() + "Du kannst nun überall hochklettern!");
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
