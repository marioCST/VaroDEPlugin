package de.mariocst.varode.commands.inventory;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Sound;
import de.mariocst.varode.VaroDE;

public class IDCommand extends Command {
    private final VaroDE plugin;

    public IDCommand(VaroDE plugin) {
        super("id", "Zeigt dir die ID des Items in deiner Hand!", "id");
        this.setPermission("mario.id");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("mario.id") || player.hasPermission("mario.*") || player.hasPermission("*") || sender.isOp()) {
                player.sendMessage(VaroDE.getPrefix() + "ID: " + player.getInventory().getItemInHand().getId() + ":" + player.getInventory().getItemInHand().getDamage());
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
