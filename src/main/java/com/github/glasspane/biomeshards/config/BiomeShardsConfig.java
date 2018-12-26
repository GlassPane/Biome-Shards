package com.github.glasspane.biomeshards.config;

import com.github.glasspane.biomeshards.BiomeShards;
import net.minecraftforge.common.config.*;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config.LangKey("config.biome_shards.general.title")
@Config(modid = BiomeShards.MODID, name = "glasspanemods/BiomeShards") //--> /config/glasspanemods/BiomeShards.cfg
public class BiomeShardsConfig {

    @Config.Name("Enable Shard Reset Recipe")
    @Config.Comment("en/disable the shard reset recipe, to clear attuned biome shards")
    public static boolean enableShardResetRecipe = true;

    @Mod.EventBusSubscriber(modid = BiomeShards.MODID)
    public static class Handler {

        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent event) {
            if(BiomeShards.MODID.equals(event.getModID())) ConfigManager.sync(event.getModID(), Config.Type.INSTANCE);
        }
    }
}
