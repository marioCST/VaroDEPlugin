package de.mariocst.varode.commands.chat;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Sound;
import de.mariocst.varode.VaroDE;

public class ChatClearCommand extends Command {
    private final VaroDE plugin;

    public ChatClearCommand(VaroDE plugin) {
        super("chatclear", "Cleart den Chat", "chatclear", new String[]{"cc"});
        this.setPermission("mario.chatclear");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("mario.chatclear") || player.hasPermission("mario.*") || player.hasPermission("*") || sender.isOp()) {
                for (int i = 0; i <= 100; i++) {
                    for (Player player1 : VaroDE.getInstance().getServer().getOnlinePlayers().values()) {
                        if (!player1.hasPermission("mario.chatclear.bypass") && !player1.hasPermission("mario.*") && !player1.hasPermission("*") && !player1.isOp()) {
                            player1.sendMessage("   ");
                        }
                    }
                }

                VaroDE.getInstance().getServer().broadcastMessage(VaroDE.getPrefix() + player.getDisplayName() + " hat den Chat geleert.");
            } else {
                sender.sendMessage(VaroDE.getPrefix() + "Keine Rechte!");
                player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
            }
        } else {
            assert false;
            for (int i = 0; i <= 100; i++) {
                for (Player player1 : VaroDE.getInstance().getServer().getOnlinePlayers().values()) {
                    if (!player1.hasPermission("mario.chatclear.bypass") && !player1.hasPermission("mario.*") && !player1.hasPermission("*") && !player1.isOp()) {
                        player1.sendMessage("   ");
                    }
                }
            }

            VaroDE.getInstance().getServer().broadcastMessage(VaroDE.getPrefix() + "Die Konsole hat den Chat geleert.");
        }
        return false;
    }
}
