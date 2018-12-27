package com.github.glasspane.biomeshards.init;

import com.github.glasspane.biomeshards.BiomeShards;
import com.github.glasspane.biomeshards.item.*;
import com.github.upcraftlp.glasspane.api.registry.AutoRegistry;
import com.github.upcraftlp.glasspane.item.ItemBase;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

@SuppressWarnings("unused")
@GameRegistry.ObjectHolder(BiomeShards.MODID)
@AutoRegistry(BiomeShards.MODID)
public class BiomeShardItems {

    public static final Item BIOME_SHARD = new ItemBiomeShard("biome_shard");
    public static final Item SHARD_OF_LAPUTA = new ItemBase("shard_of_laputa").setCreativeTab(BiomeShards.CREATIVE_TAB); //TODO dungeon loot
    public static final Item MINI_ISLAND = new ItemMiniatureIsland("mini_island");

    //TODO JEI integration!
}
