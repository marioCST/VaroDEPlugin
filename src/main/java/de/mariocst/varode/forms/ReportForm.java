package de.mariocst.varode.forms;

import cn.nukkit.IPlayer;
import cn.nukkit.Player;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.level.Sound;
import de.mariocst.varode.VaroDE;
import de.mariocst.varode.forms.custom.CustomForm;

import java.io.IOException;
import java.util.HashMap;

public class ReportForm {
    public static HashMap<String, String> messages = new HashMap<>();

    public static String getNP(String key, String description, Object... replacements) {
        String message = messages.getOrDefault(key, description).replace("&", "§");

        int i = 0;
        for (Object replacement : replacements) {
            message = message.replace("[" + i + "]", String.valueOf(replacement));
            i++;
        }

        return message;
    }

    public void openReport(Player player) {
        CustomForm form = new CustomForm.Builder("§6Report")
                .addElement(new ElementInput("Spieler", player.getName()))
                .addElement(new ElementInput("Grund", "Cheating"))
                .onSubmit((p, r) -> {
                    if (r.getInputResponse(0).isEmpty() || r.getInputResponse(1).isEmpty()) {
                        player.sendMessage(VaroDE.getPrefix() + "Bitte fülle alle Felder aus!");
                        return;
                    }

                    Player t = VaroDE.getInstance().getServer().getPlayer(r.getInputResponse(1));
                    IPlayer oT = VaroDE.getInstance().getServer().getOfflinePlayer(r.getInputResponse(1));
                    String[] args = r.getInputResponse(1).split(" ");

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
                })
                .build();
        form.send(player);
    }
}
