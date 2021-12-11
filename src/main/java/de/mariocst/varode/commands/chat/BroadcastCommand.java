package de.mariocst.varode.commands.chat;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Sound;
import de.mariocst.varode.VaroDE;

public class BroadcastCommand extends Command {
    private final VaroDE plugin;

    public BroadcastCommand(VaroDE plugin) {
        super("broadcast", "Einfacher Broadcast Command", "broadcast", new String[]{"bc"});
        this.setPermission("mario.broadcast");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        String message = String.join(" ",  args);

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("mario.broadcast") || player.hasPermission("mario.*") || player.hasPermission("*") || sender.isOp()) {
                if (args.length > 0) {
                    VaroDE.getInstance().getServer().broadcastMessage(VaroDE.getPrefix() + message.replaceAll("&", "§"));
                } else {
                    sender.sendMessage(VaroDE.getPrefix() + "Bitte gib einen Text ein!");
                    player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                }
            } else {
                sender.sendMessage(VaroDE.getPrefix() + "Keine Rechte!");
                player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
            }
        } else {
            assert false;
            if (args.length > 0) {
                VaroDE.getInstance().getServer().broadcastMessage(VaroDE.getPrefix() + message);
            } else {
                sender.sendMessage("Bitte gib einen Text ein!");
            }
        }
        return false;
    }
}
