package de.mariocst.varode.config;

import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import de.mariocst.varode.VaroDE;

public class MasterConfig {
    private static MasterConfig masterConfig;

    private final ConfigSection config;

    private final boolean isEmpty;

    private String webhookLink, prefix, link;

    private boolean perWorldChat, levelChangeMSG, bedrockCheckChunkLoad, bedrockCheckBreak, bedrockCheckPlace;

    private double force, multiplier;

    public MasterConfig(ConfigSection configSection) {
        masterConfig = this;
        this.config = configSection;
        this.isEmpty = this.config.isEmpty();
        this.init();
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getWebhookLink() {
        return this.webhookLink;
    }

    public boolean isPerWorldChat() {
        return this.perWorldChat;
    }

    public boolean isLevelChangeMSG() {
        return this.levelChangeMSG;
    }

    public boolean isBedrockCheckChunkLoad() {
        return bedrockCheckChunkLoad;
    }

    public boolean isBedrockCheckBreak() {
        return bedrockCheckBreak;
    }

    public boolean isBedrockCheckPlace() {
        return bedrockCheckPlace;
    }

    public double getForce() {
        return force;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public static MasterConfig getMasterConfig() {
        return masterConfig;
    }

    private void init() {
        if (!this.isEmpty) {
            if (this.config.containsKey("webhook")) {
                this.webhookLink = this.config.getString("webhook");
            }
            else {
                this.webhookLink = "Undefined";
            }

            if (this.config.containsKey("prefix")) {
                this.prefix = this.config.getString("prefix");
            }
            else {
                this.prefix = "§8[§aVaroDE§8] §b";
            }

            VaroDE.setPrefix(this.prefix.replaceAll("'''", ""));

            if (this.config.containsKey("Link")) {
                this.link = this.config.getString("link");
            }
            else {
                this.link = "Undefined";
            }

            if (this.config.containsKey("perWorldChat")) {
                this.perWorldChat = this.config.getBoolean("perWorldChat");
            }
            else {
                this.perWorldChat = false;
            }

            if (this.config.containsKey("levelChangeMSG")) {
                this.levelChangeMSG = this.config.getBoolean("levelChangeMSG");
            }
            else {
                this.levelChangeMSG = false;
            }

            if (this.config.containsKey("bedrockCheckChunkLoad")) {
                this.bedrockCheckChunkLoad = this.config.getBoolean("bedrockCheckChunkLoad");
            }
            else {
                this.bedrockCheckChunkLoad = true;
            }

            if (this.config.containsKey("bedrockCheckBreak")) {
                this.bedrockCheckBreak = this.config.getBoolean("bedrockCheckBreak");
            }
            else {
                this.bedrockCheckBreak = true;
            }

            if (this.config.containsKey("bedrockCheckPlace")) {
                this.bedrockCheckPlace = this.config.getBoolean("bedrockCheckPlace");
            }
            else {
                this.bedrockCheckPlace = true;
            }

            if (this.config.containsKey("force")) {
                this.force = this.config.getDouble("force");
            }
            else {
                this.force = 2.4;
            }

            if (this.config.containsKey("multiplier")) {
                this.multiplier = this.config.getDouble("multiplier");
            }
            else {
                this.multiplier = 1.0;
            }
        } else {
            this.spawnDefaultConfig();
        }
    }

    private void spawnDefaultConfig() {
        this.webhookLink = "Undefined";
        this.prefix = "§8[§aVaroDE§8] §b";
        this.link = "Undefined";
        this.perWorldChat = false;
        this.levelChangeMSG = false;
        this.bedrockCheckChunkLoad = true;
        this.bedrockCheckBreak = true;
        this.bedrockCheckPlace = true;
        this.force = 2.4;
        this.multiplier = 1.0;
        this.save();
    }

    public void save() {
        try {
            this.config.put("webhook", this.webhookLink);
            this.config.put("prefix", this.prefix.replaceAll("'''", ""));
            this.config.put("link", this.link);
            VaroDE.setPrefix(this.prefix.replaceAll("'''", ""));
            this.config.put("perWorldChat", this.perWorldChat);
            this.config.put("levelChangeMSG", this.levelChangeMSG);
            this.config.put("bedrockCheckChunkLoad", this.bedrockCheckChunkLoad);
            this.config.put("bedrockCheckBreak", this.bedrockCheckBreak);
            this.config.put("bedrockCheckPlace", this.bedrockCheckPlace);
            this.config.put("force", this.force);
            this.config.put("multiplier", this.multiplier);
            Config c = new Config(VaroDE.getInstance().getDataFolder() + "/config.yml", Config.YAML);
            c.setAll(this.config);
            c.save();
        } catch (NullPointerException e) {
            this.spawnDefaultConfig();
            this.save();
        }
    }

    public boolean isEmpty() {
        return this.isEmpty;
    }
}
