package com.github.glasspane.biomeshards.init;

import com.github.glasspane.biomeshards.BiomeShards;
import com.github.upcraftlp.glasspane.api.util.CollectionUtils;
import com.google.common.collect.Lists;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

public class SupportedBiomes {

    private static final Map<BiomeDictionary.Type, List<ResourceLocation>> BIOME_STRUCTURE_MAP = new HashMap<>();
    private static final Predicate<Biome> SNOW_FILTER = (biome) -> biome != Biomes.SKY;

    private static final ResourceLocation SNOW_1 = new ResourceLocation(BiomeShards.MODID, "structures/snow_1.nbt");

    public static void init() {
        BIOME_STRUCTURE_MAP.put(BiomeDictionary.Type.SNOWY, Lists.newArrayList(
                SNOW_1
        ));
    }

    /**
     * get a structure for a given biome
     * TODO only works for snowy biomes atm
     */
    @Nullable
    public static ResourceLocation getStructure(Biome biome) {
        if(BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.SNOWY) && SNOW_FILTER.test(biome)) {
            return CollectionUtils.getRandomElement(BIOME_STRUCTURE_MAP.get(BiomeDictionary.Type.SNOWY));
        }
        return null;
    }
}
