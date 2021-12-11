package de.mariocst.varode.commands.server;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Sound;
import de.mariocst.varode.VaroDE;

public class KickAllCommand extends Command {
    private final VaroDE plugin;

    public KickAllCommand(VaroDE plugin) {
        super("kickall", "Kickt alle Spieler mit einem bestimmten Grund", "kickall", new String[]{"ka"});
        this.setPermission("mario.kickall");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("mario.kickall") || player.hasPermission("mario.*") || player.hasPermission("*") || sender.isOp()) {
                try {
                    int count = VaroDE.getInstance().getServer().getOnlinePlayers().size();

                    if (args.length == 0) {
                        if (count == 0 || count == 1) {
                            sender.sendMessage(VaroDE.getPrefix() + "Kein Spieler ist Online lol");
                            player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                            return false;
                        }
                        else {
                            for (Player t : VaroDE.getInstance().getServer().getOnlinePlayers().values()) {
                                if (t != sender && !t.hasPermission("mario.kickall.bypass") && !t.isOp() && !t.hasPermission("mario.*") && !t.hasPermission("*")) {
                                    t.kick();
                                }
                                else {
                                    sender.sendMessage(VaroDE.getPrefix() + "Der Spieler " + t.getName() + " ist gegen KickAll immun!");
                                }
                            }

                            sender.sendMessage(VaroDE.getPrefix() + "Alle Spieler gekickt!");
                        }
                    }
                    else {
                        if (count == 0 || count == 1) {
                            sender.sendMessage(VaroDE.getPrefix() + "Kein Spieler ist Online lol");
                            player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                            return false;
                        }
                        else {
                            String reason = String.join(" ", args);
                            for (Player t : VaroDE.getInstance().getServer().getOnlinePlayers().values()) {
                                if (t != sender && !t.hasPermission("mario.kickall.bypass") && !t.isOp() && !t.hasPermission("mario.*") && !t.hasPermission("*")) {
                                    t.kick(reason);
                                }
                                else {
                                    sender.sendMessage(VaroDE.getPrefix() + "Der Spieler " + t.getName() + " ist gegen KickAll immun!");
                                }
                            }

                            sender.sendMessage(VaroDE.getPrefix() + "Alle Spieler mit dem Grund " + reason + " gekickt!");
                        }
                    }
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    sender.sendMessage(VaroDE.getPrefix() + "/kickall [Grund]");
                    player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                }
            } else {
                sender.sendMessage(VaroDE.getPrefix() + "Keine Rechte!");
                player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
            }
        } else {
            assert false;
            try {
                if (args.length == 0) {
                    int count = VaroDE.getInstance().getServer().getOnlinePlayers().size();
                    if (count == 0) {
                        sender.sendMessage(VaroDE.getPrefix() + "Kein Spieler ist Online lol");
                        return false;
                    }
                    else {
                        for (Player t : VaroDE.getInstance().getServer().getOnlinePlayers().values()) {
                            if (!t.hasPermission("mario.kickall.bypass") && !t.isOp() && !t.hasPermission("mario.*") && !t.hasPermission("*")) {
                                t.kick();
                            }
                            else {
                                sender.sendMessage(VaroDE.getPrefix() + "Der Spieler " + t.getName() + " ist gegen KickAll immun!");
                            }
                        }

                        sender.sendMessage(VaroDE.getPrefix() + "Alle Spieler gekickt!");
                    }
                }
                else if (args.length > 0) {
                    int count = VaroDE.getInstance().getServer().getOnlinePlayers().size();
                    if (count == 0) {
                        sender.sendMessage(VaroDE.getPrefix() + "Kein Spieler ist Online lol");
                        return false;
                    }
                    else {
                        String reason = String.join(" ", args);
                        for (Player t : VaroDE.getInstance().getServer().getOnlinePlayers().values()) {
                            if (!t.hasPermission("mario.kickall.bypass") && !t.isOp() && !t.hasPermission("mario.*") && !t.hasPermission("*")) {
                                t.kick(reason);
                            }
                            else {
                                sender.sendMessage(VaroDE.getPrefix() + "Der Spieler " + t.getName() + " ist gegen KickAll immun!");
                            }
                        }
                        sender.sendMessage(VaroDE.getPrefix() + "Alle Spieler mit dem Grund " + reason + " gekickt!");
                    }
                }
                else {
                    sender.sendMessage(VaroDE.getPrefix() + "/kickall [Grund]!");
                }
            }
            catch (ArrayIndexOutOfBoundsException e) {
                sender.sendMessage(VaroDE.getPrefix() + "/kickall [Grund]");
            }
        }
        return false;
    }
}
