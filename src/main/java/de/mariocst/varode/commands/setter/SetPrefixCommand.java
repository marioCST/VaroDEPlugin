package de.mariocst.varode.commands.setter;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Sound;
import de.mariocst.varode.config.MasterConfig;
import de.mariocst.varode.VaroDE;

public class SetPrefixCommand extends Command {
    private final VaroDE plugin;

    public SetPrefixCommand(VaroDE plugin) {
        super("setprefix", "Setzt den Prefix für das Plugin. Nutze & statt Paragraph", "setprefix", new String[]{"sp"});
        this.setPermission("mario.setprefix");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        String message = String.join(" ",  args);

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("mario.setprefix") || player.hasPermission("mario.*") || player.hasPermission("*") || sender.isOp()) {
                if (args.length > 0) {
                    sender.sendMessage(VaroDE.getPrefix() + "Der Prefix ist nun: " + message);
                    MasterConfig.getMasterConfig().setPrefix(message.replaceAll("&", "§"));
                    VaroDE.setPrefix(message.replaceAll("&", "§"));
                    MasterConfig.getMasterConfig().save();
                } else {
                    sender.sendMessage(VaroDE.getPrefix() + "§cBitte setze einen Prefix!");
                    player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                }
            } else {
                sender.sendMessage(VaroDE.getPrefix() + "Keine Rechte!");
                player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
            }
        } else {
            assert false;
            if (args.length > 0) {
                sender.sendMessage("Der Prefix ist nun: " + message);
                MasterConfig.getMasterConfig().setPrefix(message.replaceAll("&", "§"));
                VaroDE.setPrefix(message.replaceAll("&", "§"));
                MasterConfig.getMasterConfig().save();
            } else {
                sender.sendMessage("Bitte setze einen Prefix!");
            }
        }
        return false;
    }
}
