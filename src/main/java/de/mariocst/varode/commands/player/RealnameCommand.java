package de.mariocst.varode.commands.player;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Sound;
import de.mariocst.varode.VaroDE;

public class RealnameCommand extends Command {
    private final VaroDE plugin;

    public RealnameCommand(VaroDE plugin) {
        super("realname", "Zeigt den echten Namen eines Spielers an", "realname");
        this.setPermission("mario.realname");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("mario.realname") || player.hasPermission("mario.*") || player.hasPermission("*") || sender.isOp()) {
                if (args.length == 1) {
                    for (Player p : VaroDE.getInstance().getServer().getOnlinePlayers().values()) {
                        if (p.getDisplayName().contains(args[0])) {
                            if (!p.getDisplayName().contains(player.getDisplayName())) {
                                if (!p.getDisplayName().contains(p.getName())) {
                                    player.sendMessage(VaroDE.getPrefix() + "Der Spieler " + p.getDisplayName() + " heißt in Wirklichkeit " + p.getName() + "!");
                                }
                                else {
                                    player.sendMessage(VaroDE.getPrefix() + "Der Spieler " + p.getName() + " ist NICHT genickt!");
                                    player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                                }
                            }
                            else {
                                player.sendMessage(VaroDE.getPrefix() + "Das bist du.");
                                player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                            }
                        }
                        else {
                            player.sendMessage(VaroDE.getPrefix() + "Der Spieler " + args[0] + " existiert nicht!");
                            player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                        }
                    }
                } else if (args.length == 0) {
                    sender.sendMessage(VaroDE.getPrefix() + "Bitte gib den Nickname an.");
                    player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                } else {
                    sender.sendMessage(VaroDE.getPrefix() + "Bitte gib einen gültigen Nickname an.");
                    player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                }
            } else {
                sender.sendMessage(VaroDE.getPrefix() + "Keine Rechte!");
                player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
            }
        } else {
            assert false;
            if (args.length == 1) {
                for (Player p : VaroDE.getInstance().getServer().getOnlinePlayers().values()) {
                    if (p.getDisplayName().contains(args[0])) {
                        if (!p.getDisplayName().contains(p.getName())) {
                            sender.sendMessage(VaroDE.getPrefix() + "Der Spieler " + p.getDisplayName() + " heißt in Wirklichkeit " + p.getName() + "!");
                        }
                        else {
                            sender.sendMessage(VaroDE.getPrefix() + "Der Spieler " + p.getName() + " ist NICHT genickt!");
                        }
                    }
                    else {
                        sender.sendMessage(VaroDE.getPrefix() + "Der Spieler " + args[0] + " existiert nicht!");
                    }
                }
            } else if (args.length == 0) {
                sender.sendMessage(VaroDE.getPrefix() + "Bitte gib den Nickname an.");
            } else {
                sender.sendMessage(VaroDE.getPrefix() + "Bitte gib einen gültigen Nickname an.");
            }
        }
        return false;
    }
}
