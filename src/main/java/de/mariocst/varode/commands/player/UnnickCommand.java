package de.mariocst.varode.commands.player;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Sound;
import de.mariocst.varode.VaroDE;

public class UnnickCommand extends Command {
    private final VaroDE plugin;

    public UnnickCommand(VaroDE plugin) {
        super("unnick", "Setzt deinen Nickname zurück", "unnick");
        this.setPermission("mario.unnick");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("mario.unnick") || player.hasPermission("mario.*") || player.hasPermission("*") || sender.isOp()) {
                player.setDisplayName(player.getName());
                player.setNameTag(player.getName());
                sender.sendMessage(VaroDE.getPrefix() + "Dein Nickname wurde zurückgesetzt!");
            } else {
                sender.sendMessage(VaroDE.getPrefix() + "Keine Rechte!");
                player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
            }
        } else {
            assert false;
            sender.sendMessage("Bitte führe den Command InGame aus!");
        }
        return false;
    }
}
