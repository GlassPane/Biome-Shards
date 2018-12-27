package com.github.glasspane.biomeshards.init;

import com.github.glasspane.biomeshards.BiomeShards;
import com.github.glasspane.biomeshards.crafting.IslandCompositeRecipe;
import com.github.upcraftlp.glasspane.api.registry.AutoRegistry;
import net.minecraft.item.crafting.IRecipe;

@SuppressWarnings("unused")
@AutoRegistry(BiomeShards.MODID)
public class BiomeShardRecipes {

    public static final IRecipe ISLAND_COMPOSITE = new IslandCompositeRecipe().setRegistryName("island_composite");
}
