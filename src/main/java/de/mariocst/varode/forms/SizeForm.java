package de.mariocst.varode.forms;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import de.mariocst.varode.forms.simple.SimpleForm;
import de.mariocst.varode.VaroDE;

import java.util.HashMap;

public class SizeForm {
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

    public void openSize(Player player) {
        SimpleForm form = new SimpleForm.Builder("§aGröße",
                getNP("", "§aSuch dir eine Größe aus!"))
                .addButton(new ElementButton("§4Riesig"), e -> {
                    player.setScale(7.0f);

                    player.sendMessage(VaroDE.getPrefix() + "Deine Größe ist nun riesig!");
                })
                .addButton(new ElementButton("§6Groß"), e -> {
                    player.setScale(5.0F);

                    player.sendMessage(VaroDE.getPrefix() + "Deine Größe ist nun groß!");
                })
                .addButton(new ElementButton("§aNormal"), e -> {
                    player.setScale(1.0F);

                    player.sendMessage(VaroDE.getPrefix() + "Deine Größe ist nun normal!");
                })
                .addButton(new ElementButton("§bKlein"), e -> {
                    player.setScale(0.7F);

                    player.sendMessage(VaroDE.getPrefix() + "Deine Größe ist nun klein!");
                })
                .addButton(new ElementButton("§1Winzig"), e -> {
                    player.setScale(0.3F);

                    player.sendMessage(VaroDE.getPrefix() + "Deine Größe ist nun winzig!");
                })
                .build();
        form.send(player);
    }
}
