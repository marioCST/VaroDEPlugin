package de.mariocst.varode.config;

import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import de.mariocst.varode.VaroDE;

import java.util.*;

public class VaroLastPosConfig {
    private static VaroLastPosConfig varoLastPosConfig;

    private final ConfigSection config;

    private final boolean isEmpty;

    private final Map<UUID, Double> x = new HashMap<>();
    private final Map<UUID, Double> y = new HashMap<>();
    private final Map<UUID, Double> z = new HashMap<>();
    private final Map<UUID, Double> yaw = new HashMap<>();
    private final Map<UUID, Double> pitch = new HashMap<>();
    private final List<UUID> uuids = new ArrayList<>();

    public VaroLastPosConfig(ConfigSection configSection) {
        varoLastPosConfig = this;
        this.config = configSection;
        this.isEmpty = this.config.isEmpty();
        this.init();
    }

    public double getX(UUID uuid) {
        return this.x.get(uuid);
    }
    public double getY(UUID uuid) {
        return this.y.get(uuid);
    }
    public double getZ(UUID uuid) {
        return this.z.get(uuid);
    }
    public double getYaw(UUID uuid) {
        return this.yaw.get(uuid);
    }
    public double getPitch(UUID uuid) {
        return this.pitch.get(uuid);
    }

    public void addUUID(UUID uuid) {
        if (!this.uuids.contains(uuid)) this.uuids.add(uuid);
    }

    public void addX(UUID uuid, double x) {
        this.x.remove(uuid);

        this.x.put(uuid, x);
    }

    public void addY(UUID uuid, double y) {
        this.y.remove(uuid);

        this.y.put(uuid, y);
    }

    public void addZ(UUID uuid, double z) {
        this.z.remove(uuid);

        this.z.put(uuid, z);
    }

    public void addYaw(UUID uuid, double yaw) {
        this.yaw.remove(uuid);

        this.yaw.put(uuid, yaw);
    }

    public void addPitch(UUID uuid, double pitch) {
        this.pitch.remove(uuid);

        this.pitch.put(uuid, pitch);
    }

    public List<UUID> getUUIDs() {
        return this.uuids;
    }

    public static VaroLastPosConfig getVaroLastPosConfig() {
        return varoLastPosConfig;
    }

    private void init() {
        if (!this.isEmpty) {
            for (UUID uuid : this.uuids) {
                if (!VaroDE.getInstance().getServer().getPlayer(uuid).isPresent() && VaroDE.getInstance().getServer().getOfflinePlayer(uuid) == null) {
                    this.uuids.remove(uuid);
                    this.x.remove(uuid);
                    this.y.remove(uuid);
                    this.z.remove(uuid);
                    this.yaw.remove(uuid);
                    this.pitch.remove(uuid);
                }
            }

            for (String uuidString : this.config.getStringList("positions")) {
                this.uuids.add(UUID.fromString(uuidString));
                this.x.put(UUID.fromString(uuidString), this.config.getDouble("positions." + uuidString + ".x"));
                this.y.put(UUID.fromString(uuidString), this.config.getDouble("positions." + uuidString + ".y"));
                this.z.put(UUID.fromString(uuidString), this.config.getDouble("positions." + uuidString + ".z"));
                this.yaw.put(UUID.fromString(uuidString), this.config.getDouble("positions." + uuidString + ".yaw"));
                this.pitch.put(UUID.fromString(uuidString), this.config.getDouble("positions." + uuidString + ".pitch"));
            }
        } else {
            this.spawnDefaultConfig();
        }
    }

    private void spawnDefaultConfig() {
        //this.uuids.add(UUID.fromString("00000000-0000-0000-0000-000000000000"));
        this.uuids.add(UUID.randomUUID());
        this.x.put(this.uuids.get(0), 0.0);
        this.y.put(this.uuids.get(0), 0.0);
        this.z.put(this.uuids.get(0), 0.0);
        this.yaw.put(this.uuids.get(0), 0.0);
        this.pitch.put(this.uuids.get(0), 0.0);
        this.save();
    }

    public void save() {
        try {
            for (UUID uuid : this.uuids) {
                this.config.put("positions." + uuid + ".x", this.x.get(uuid));
                this.config.put("positions." + uuid + ".y", this.y.get(uuid));
                this.config.put("positions." + uuid + ".z", this.z.get(uuid));
                this.config.put("positions." + uuid + ".yaw", this.yaw.get(uuid));
                this.config.put("positions." + uuid + ".pitch", this.pitch.get(uuid));
            }
            Config c = new Config(VaroDE.getInstance().getDataFolder() + "/varoLastPos.yml", Config.YAML);
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
