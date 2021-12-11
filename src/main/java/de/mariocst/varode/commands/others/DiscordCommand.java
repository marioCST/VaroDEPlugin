package de.mariocst.varode.commands.others;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Sound;
import de.mariocst.varode.config.MasterConfig;
import de.mariocst.varode.VaroDE;

public class DiscordCommand extends Command {
    private final VaroDE plugin;

    public DiscordCommand(VaroDE plugin) {
        super("discord", "Zeigt den definierten Discord Link an!", "discord", new String[]{"dc"});
        this.setPermission("mario.discord");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("mario.discord") || player.hasPermission("mario.*") || player.hasPermission("*") || sender.isOp()) {
                sender.sendMessage(VaroDE.getPrefix() + "Unser Discord: " + MasterConfig.getMasterConfig().getLink());
            } else {
                sender.sendMessage(VaroDE.getPrefix() + "Keine Rechte!");
                player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
            }
        } else {
            assert false;
            sender.sendMessage("Unser Discord: " + MasterConfig.getMasterConfig().getLink());
        }

        return false;
    }
}
