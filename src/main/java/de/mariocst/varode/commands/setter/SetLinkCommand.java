package de.mariocst.varode.commands.setter;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Sound;
import de.mariocst.varode.config.MasterConfig;
import de.mariocst.varode.VaroDE;

public class SetLinkCommand extends Command {
    private final VaroDE plugin;

    public SetLinkCommand(VaroDE plugin) {
        super("setlink", "Setzt den Link für den /discord Command", "setlink", new String[]{"sl"});
        this.setPermission("mario.setlink");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        String message = String.join(" ",  args);

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("mario.setlink") || player.hasPermission("mario.*") || player.hasPermission("*") || sender.isOp()) {
                if (args.length > 0) {
                    sender.sendMessage(VaroDE.getPrefix() + "Der Discord Link ist nun: " + message);
                    MasterConfig.getMasterConfig().setLink(message);
                    MasterConfig.getMasterConfig().save();
                } else {
                    sender.sendMessage(VaroDE.getPrefix() + "§cBitte setze einen Link!");
                    player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                }
            } else {
                sender.sendMessage(VaroDE.getPrefix() + "Keine Rechte!");
                player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
            }
        } else {
            assert false;
            if (args.length > 0) {
                sender.sendMessage("Der Discord Link ist nun: " + message);
                MasterConfig.getMasterConfig().setLink(message);
                MasterConfig.getMasterConfig().save();
            } else {
                sender.sendMessage("Bitte setze einen Link!");
            }
        }
        return false;
    }
}
