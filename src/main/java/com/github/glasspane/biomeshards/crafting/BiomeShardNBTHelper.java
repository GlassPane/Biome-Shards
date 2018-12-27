package com.github.glasspane.biomeshards.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.*;

@SuppressWarnings("ConstantConditions")
public class BiomeShardNBTHelper {

    public static final String KEY_SHARD_BIOME = "AttunedBiome";
    public static final String KEY_ISLAND_BIOME_LIST = "Biomes";
    public static final String KEY_ISLAND_BIOME_NAME = "Name";
    public static final String KEY_ISLAND_BIOME_SIZE = "Size";

    @Nullable
    public static ResourceLocation getShardBiome(ItemStack stack) {
        return isShardAttuned(stack) ? new ResourceLocation(stack.getTagCompound().getString(KEY_SHARD_BIOME)) : null;
    }


    public static void setShardBiome(@Nullable Biome biome, ItemStack stack) {
        if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().removeTag(KEY_SHARD_BIOME);
        if(biome != null) stack.getTagCompound().setString(KEY_SHARD_BIOME, biome.getRegistryName().toString());
    }

    public static boolean isShardAttuned(ItemStack stack) {
        return !stack.isEmpty() && stack.hasTagCompound() && stack.getTagCompound().hasKey(KEY_SHARD_BIOME, Constants.NBT.TAG_STRING);
    }

    public static void setIslandBiomes(Map<ResourceLocation, Integer> islandBiomes, ItemStack stack) {
        if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        NBTTagList biomeList = new NBTTagList();
        islandBiomes.entrySet().stream().sorted(Comparator.comparing(e -> e.getKey().toString())).forEachOrdered(entry -> {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setString(KEY_ISLAND_BIOME_NAME, entry.getKey().toString());
            nbt.setInteger(KEY_ISLAND_BIOME_SIZE, entry.getValue());
            biomeList.appendTag(nbt);
        });
        stack.getTagCompound().setTag(KEY_ISLAND_BIOME_LIST, biomeList);
    }

    public static Map<ResourceLocation, Integer> getIslandBiomes(ItemStack stack) {
        if(stack.hasTagCompound() && stack.getTagCompound().hasKey(KEY_ISLAND_BIOME_LIST, Constants.NBT.TAG_LIST)) {
            NBTTagList biomeList = stack.getTagCompound().getTagList(KEY_ISLAND_BIOME_LIST, Constants.NBT.TAG_COMPOUND);
            Map<ResourceLocation, Integer> ret = new HashMap<>();
            for(int i = 0; i < biomeList.tagCount(); i++) {
                NBTTagCompound nbt = biomeList.getCompoundTagAt(i);
                ret.put(new ResourceLocation(nbt.getString(KEY_ISLAND_BIOME_NAME)), nbt.getInteger(KEY_ISLAND_BIOME_SIZE));
            }
            return ret;
        }
        return Collections.emptyMap();
    }
}
