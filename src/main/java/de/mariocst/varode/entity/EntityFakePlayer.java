package de.mariocst.varode.entity;

import cn.nukkit.entity.EntityHuman;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.UUID;

public class EntityFakePlayer extends EntityHuman {
    private Skin skin;

    public EntityFakePlayer(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public Skin getSkin() {
        this.skin = new Skin();
        skin.setSkinResourcePatch("textures/entity/steve.png");
        skin.setTrusted(true);
        skin.setSkinData(new BufferedImage(64, 64, 1));
        return this.skin;
    }

    @Override
    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    @Override
    public float getHealth() {
        return 1000000.0f;
    }

    @Override
    public int getMaxHealth() {
        return 1000000;
    }

    @Override
    protected float getGravity() {
        return 0.0f;
    }

    @Override
    public int getNetworkId() {
        Random random = new Random();

        return 23576 + random.nextInt(10000) - random.nextInt(1000);
    }

    @Override
    public float getScale() {
        return 1;
    }

    @Override
    public UUID getUniqueId() {
        return UUID.randomUUID();
    }
}
