package com.github.glasspane.biomeshards.init;

import net.minecraft.stats.StatBase;
import net.minecraft.util.text.TextComponentTranslation;

public class BiomeShardStats {

    //TODO localize
    public static final StatBase ISLANDS_SPAWNED = new StatBase("stat.biome_shards.islandsSpawned", new TextComponentTranslation("stat.biome_shards.islandsSpawned"));

    /**
     * register all stats so that the StatManager is aware of them
     */
    public static void registerStats() {
        ISLANDS_SPAWNED.registerStat();
    }
}
