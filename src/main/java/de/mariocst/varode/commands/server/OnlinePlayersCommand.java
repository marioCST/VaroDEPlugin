package de.mariocst.varode.commands.server;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Sound;
import de.mariocst.varode.VaroDE;

public class OnlinePlayersCommand extends Command {
    private final VaroDE plugin;

    public OnlinePlayersCommand(VaroDE plugin) {
        super("onlinepalyers", "Zeigt dir alle Spieler an, die online sind", "onlinepalyers", new String[]{"oplayers"});
        this.setPermission("mario.onlinepalyers");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("mario.onlinepalyers") || player.hasPermission("mario.*") || player.hasPermission("*") || sender.isOp()) {
                int players = 0;

                for (Player player1 : VaroDE.getInstance().getServer().getOnlinePlayers().values()) {
                    if (player1 != player) players++;
                }

                if (players == 0) {
                    player.sendMessage(VaroDE.getPrefix() + "Niemand, außer dir, ist online!");
                    return  false;
                }

                player.sendMessage(VaroDE.getPrefix() + "Online Spieler:");

                for (Player player1 : VaroDE.getInstance().getServer().getOnlinePlayers().values()) {
                    if (player1 != player) player.sendMessage(VaroDE.getPrefix() + player1.getName() + " | " + getDeviceName(player1));
                }
            } else {
                player.sendMessage(VaroDE.getPrefix() + "Keine Rechte!");
                player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
            }
        } else {
            assert false;
            int players = 0;

            for (Player ignored : VaroDE.getInstance().getServer().getOnlinePlayers().values()) {
                players++;
            }

            if (players == 0) {
                sender.sendMessage(VaroDE.getPrefix() + "Niemand ist online!");
                return  false;
            }

            sender.sendMessage(VaroDE.getPrefix() + "Online Spieler:");

            for (Player player1 : VaroDE.getInstance().getServer().getOnlinePlayers().values()) {
                sender.sendMessage(VaroDE.getPrefix() + player1.getName() + " | " + getDeviceName(player1));
            }
        }

        return false;
    }

    public static String getDeviceName(final Player p) {
        return String.valueOf(p.getLoginChainData().getDeviceOS()).replace("0", "Unknown").replace("1", "Android").replace("2", "iOS").replace("3", "MacOS").replace("4", "FireOS").replace("5", "GearVR").replace("6", "HoloLens").replace("7", "Windows 10").replace("8", "Windows").replace("9", "Dedicated").replace("10", "PlayStation 4").replace("11", "Switch");
    }
}
