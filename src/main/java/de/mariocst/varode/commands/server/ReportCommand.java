package de.mariocst.varode.commands.server;

import cn.nukkit.IPlayer;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Sound;
import de.mariocst.varode.VaroDE;

import java.io.IOException;

public class ReportCommand extends Command {
    private final VaroDE plugin;

    public ReportCommand(VaroDE plugin) {
        super("report", "Reporte einen Spieler!", "report");
        this.setPermission("mario.report");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            try {
                if (args.length >= 2) {
                    try {
                        Player t = VaroDE.getInstance().getServer().getPlayer(args[0]);
                        IPlayer oT = VaroDE.getInstance().getServer().getOfflinePlayer(args[0]);

                        if (t != null) {
                            StringBuilder msg = new StringBuilder();
                            for(int i = 1; i < args.length; i++) {
                                msg.append(args[i]).append(" ");
                            }

                            player.sendMessage(VaroDE.getPrefix() + "Der Spieler §c" + t.getName() + " §fwurde für §c" + msg + "reported!");

                            int staffOnline = 0;

                            for (Player staff : VaroDE.getInstance().getServer().getOnlinePlayers().values()) {
                                if (staff.hasPermission("mario.staff")) {
                                    staffOnline++;

                                    staff.sendMessage(VaroDE.getPrefix() + "Der Spieler §a" + player.getName() + " §fhat den Spieler §c" + t.getName() + " §ffür §c" + msg + "§freported!");
                                }
                            }

                            if (staffOnline == 0) player.sendMessage(VaroDE.getPrefix() + "§cEs ist kein Teammitglied Online!");

                            if (!VaroDE.getInstance().getMasterConfig().getWebhookLink().equals("Undefined")) {
                                try {
                                    VaroDE.getInstance().sendReport(player, t, msg.toString());
                                    player.sendMessage(VaroDE.getPrefix() + "Dein Report wurde erfolgreich an das Discord Team gesendet!");
                                }
                                catch (IOException e) {
                                    player.sendMessage(VaroDE.getPrefix() + "Dein Report konnte leider nicht and das Discord Team gesendet werden, weil ein Fehler aufgetreten ist.");
                                }
                            }
                        }
                        else if (oT != null) {
                            StringBuilder msg = new StringBuilder();
                            for(int i = 1; i < args.length; i++) {
                                msg.append(args[i]).append(" ");
                            }

                            player.sendMessage(VaroDE.getPrefix() + "Der Spieler §c" + oT.getName() + " §fwurde für §c" + msg + "reported!");

                            int staffOnline = 0;

                            for (Player staff : VaroDE.getInstance().getServer().getOnlinePlayers().values()) {
                                if (staff.hasPermission("mario.staff")) {
                                    staffOnline++;

                                    staff.sendMessage(VaroDE.getPrefix() + "Der Spieler §a" + player.getName() + " §fhat den Spieler §c" + oT.getName() + " §ffür §c" + msg + "§freported!");
                                }
                            }

                            if (staffOnline == 0) player.sendMessage(VaroDE.getPrefix() + "§cEs ist kein Teammitglied Online!");

                            if (!VaroDE.getInstance().getMasterConfig().getWebhookLink().equals("Undefined")) {
                                try {
                                    VaroDE.getInstance().sendReport(player, oT, msg.toString());
                                    player.sendMessage(VaroDE.getPrefix() + "Dein Report wurde erfolgreich an das Discord Team gesendet!");
                                }
                                catch (IOException e) {
                                    player.sendMessage(VaroDE.getPrefix() + "Dein Report konnte leider nicht and das Discord Team gesendet werden, weil ein Fehler aufgetreten ist.");
                                }
                            }
                        }
                        else {
                            player.sendMessage(VaroDE.getPrefix() + "Der Spieler existiert nicht!");
                            player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                        }
                    }
                    catch (NullPointerException e) {
                        player.sendMessage(VaroDE.getPrefix() + "Der Spieler existiert nicht!");
                        player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                    }
                }
                else if (args.length == 1) {
                    try {
                        Player t = VaroDE.getInstance().getServer().getPlayer(args[0]);

                        if (t != null) {
                            player.sendMessage(VaroDE.getPrefix() + "Der Spieler §c" + t.getName() + " §fwurde reported!");

                            int staffOnline = 0;

                            for (Player staff : VaroDE.getInstance().getServer().getOnlinePlayers().values()) {
                                if (staff.hasPermission("mario.staff")) {
                                    staffOnline++;

                                    staff.sendMessage(VaroDE.getPrefix() + "Der Spieler §a" + player.getName() + " §fhat den Spieler §c" + t.getName() + " §freported!");
                                }
                            }

                            if (staffOnline == 0) player.sendMessage(VaroDE.getPrefix() + "§cEs ist kein Teammitglied Online!");

                            if (!VaroDE.getInstance().getMasterConfig().getWebhookLink().equals("Undefined")) {
                                try {
                                    VaroDE.getInstance().sendReport(player, t, "Kein Grund angegeben");
                                    player.sendMessage(VaroDE.getPrefix() + "Dein Report wurde erfolgreich an das Discord Team gesendet!");
                                }
                                catch (IOException e) {
                                    player.sendMessage(VaroDE.getPrefix() + "Dein Report konnte leider nicht and das Discord Team gesendet werden, weil ein Fehler aufgetreten ist.");
                                }
                            }
                        }
                        else {
                            player.sendMessage(VaroDE.getPrefix() + "Der Spieler existiert nicht!");
                            player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                        }
                    }
                    catch (NullPointerException e) {
                        player.sendMessage(VaroDE.getPrefix() + "Der Spieler existiert nicht!");
                        player.getLevel().addSound(player.getLocation(), Sound.RANDOM_ANVIL_LAND);
                    }
                }
                else {
                    VaroDE.getInstance().getReportForm().openReport(player);
                }
            }
            catch (ArrayIndexOutOfBoundsException e) {
                VaroDE.getInstance().getReportForm().openReport(player);
            }
        } else {
            assert false;
            sender.sendMessage(VaroDE.getPrefix() + "Ban den Spieler doch selber!");
        }
        return false;
    }

}
