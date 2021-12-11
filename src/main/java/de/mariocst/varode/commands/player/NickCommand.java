package de.mariocst.varode.commands.player;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Sound;
import de.mariocst.varode.VaroDE;

public class NickCommand extends Command {
    private final VaroDE plugin;

    public NickCommand(VaroDE plugin) {
        super("nick", "Ändert deinen Nickname", "nick");
        this.setPermission("mario.nick");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("mario.nick") || player.hasPermission("mario.*") || player.hasPermission("*") || sender.isOp()) {
                if (args.length == 1) {
                    if (args[0].length() < 16) {
                        String newNick = String.join(" ", args);

                        if (!player.hasPermission("mario.nick.bypass") || !player.hasPermission("mario.*") || !player.hasPermission("*") || !player.isOp()) {
                            if (newNick.contains("§")) {
                                player.sendMessage(VaroDE.getPrefix() + "Bitte wähle einen Nicknamen ohne Colorcodes!");
                                return false;
                            }
                            else if (newNick.contains("&")) {
                                player.sendMessage(VaroDE.getPrefix() + "Bitte wähle einen Nicknamen ohne Colorcodes!");
                                return false;
                            }
                        }

                        player.setDisplayName(newNick);
                        if (!player.hasPermission("mario.nick.bypass") && !player.hasPermission("mario.*") && !player.hasPermission("*") && !player.isOp()) {
                            player.setNameTag("(" + player.getName() + ") " + newNick);
                        }
                        else {
                            player.setNameTag(newNick);
                        }
                        sender.sendMessage(VaroDE.getPrefix() + "Dein Nickname wurde erfolgreich zu " + newNick + " geändert!");
                    } else {
                        sender.sendMessage(VaroDE.getPrefix() + "Bitte wähle einen Namen, der kürzer als 16 Zeichen ist!");
                        player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                    }
                } else {
                    sender.sendMessage(VaroDE.getPrefix() + "Bitte gib einen Nickname ein, oder suche dir einen, der keine Leerzeichen hat!");
                    player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                }
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
