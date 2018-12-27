package com.github.glasspane.biomeshards.crafting;

import com.github.glasspane.biomeshards.init.BiomeShardItems;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class IslandCompositeRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        boolean hasLaputaShard = false;
        boolean hasShard = false;
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if(!stack.isEmpty()) {
                if(stack.getItem() == BiomeShardItems.SHARD_OF_LAPUTA) {
                    if(hasLaputaShard) {
                        return false;
                    }
                    else {
                        hasLaputaShard = true;
                    }
                }
                else if(stack.getItem() == BiomeShardItems.BIOME_SHARD && BiomeShardNBTHelper.isShardAttuned(stack)) {
                    hasShard = true;
                }
                else {
                    return false;
                }
            }
        }
        return hasShard && hasLaputaShard;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        Map<ResourceLocation, AtomicInteger> biomeSizes = new HashMap<>();
        boolean hasLaputaShard = false;
        boolean hasShard = false;
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if(!stack.isEmpty()) {
                if(stack.getItem() == BiomeShardItems.SHARD_OF_LAPUTA) {
                    if(hasLaputaShard) { return ItemStack.EMPTY; }
                    else {
                        hasLaputaShard = true;
                    }
                }
                else if(stack.getItem() == BiomeShardItems.BIOME_SHARD && BiomeShardNBTHelper.isShardAttuned(stack)) {
                    ResourceLocation shardBiome = BiomeShardNBTHelper.getShardBiome(stack);
                    hasShard = true;
                    biomeSizes.computeIfAbsent(shardBiome, rl -> new AtomicInteger(0)).incrementAndGet();
                }
                else {
                    return ItemStack.EMPTY;
                }
            }
        }
        if(hasShard && hasLaputaShard) {
            ItemStack result = new ItemStack(BiomeShardItems.MINI_ISLAND);
            BiomeShardNBTHelper.setIslandBiomes(biomeSizes.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().get())), result);
            return result;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(BiomeShardItems.MINI_ISLAND);
    }

    @Override
    public String getGroup() {
        return "test_group";
    }
}
