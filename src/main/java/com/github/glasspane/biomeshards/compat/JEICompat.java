package com.github.glasspane.biomeshards.compat;

import com.github.glasspane.biomeshards.BiomeShards;
import com.github.glasspane.biomeshards.init.BiomeShardItems;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.item.*;

@JEIPlugin
public class JEICompat implements IModPlugin {

    @Override
    public void register(IModRegistry registry) {
        BiomeShards.getLogger().debug("registering JEI compat");
        addDescription(registry, BiomeShardItems.BIOME_SHARD);
        addDescription(registry, BiomeShardItems.SHARD_OF_LAPUTA);
        addDescription(registry, BiomeShardItems.MINI_ISLAND);
    }

    @SuppressWarnings("ConstantConditions")
    private static void addDescription(IModRegistry registry, Item item) {
        registry.addIngredientInfo(new ItemStack(item), VanillaTypes.ITEM, "jei.description." + item.getRegistryName().toString().replace(':', '.'));
    }
}
