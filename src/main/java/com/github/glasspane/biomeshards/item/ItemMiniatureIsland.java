package com.github.glasspane.biomeshards.item;

import com.github.glasspane.biomeshards.BiomeShards;
import com.github.glasspane.biomeshards.crafting.BiomeShardNBTHelper;
import com.github.glasspane.biomeshards.init.*;
import com.github.upcraftlp.glasspane.api.structure.StructureLoaders;
import com.github.upcraftlp.glasspane.item.ItemBase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.*;

import javax.annotation.Nullable;
import java.util.*;

public class ItemMiniatureIsland extends ItemBase {

    public ItemMiniatureIsland(String name) {
        super(name);
        this.setCreativeTab(BiomeShards.CREATIVE_TAB);
        this.setHasSubtypes(true);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if(!worldIn.isRemote) {
            //TODO spawn composite island
            Map<ResourceLocation, Integer> biomeMap = BiomeShardNBTHelper.getIslandBiomes(stack);
            if(!biomeMap.isEmpty()) {
                Biome biome = ForgeRegistries.BIOMES.getValue(biomeMap.entrySet().iterator().next().getKey());
                PlacementSettings placementSettings = new PlacementSettings();
                placementSettings.setRotation(Rotation.values()[itemRand.nextInt(Rotation.values().length)]);
                BlockPos pos = playerIn.getPosition();
                ResourceLocation structure = SupportedBiomes.getStructure(biome);
                if(structure != null) {
                    StructureLoaders.VANILLA_NBT.placeInWorld(structure, worldIn, pos, placementSettings, true);
                    playerIn.addStat(BiomeShardStats.ISLANDS_SPAWNED);
                    if(!playerIn.isCreative()) stack.shrink(1);
                }
                else {
                    playerIn.sendStatusMessage(new TextComponentTranslation("message.biome_shards.wrongBiome", biome.getRegistryName()).setStyle(new Style().setColor(TextFormatting.RED)), true);
                }
            }

        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if(this.isInCreativeTab(tab)) {
            ForgeRegistries.BIOMES.getKeys().stream().filter(rl -> "minecraft".equals(rl.getNamespace()) && !"void".equals(rl.getPath())).forEach(biome -> {
                ItemStack stack = new ItemStack(this);
                BiomeShardNBTHelper.setIslandBiomes(new HashMap<ResourceLocation, Integer>() {{
                    put(biome, 1);
                }}, stack); //do NOT use fastutil because that's client-only
                items.add(stack);
            });
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        for(Map.Entry<ResourceLocation, Integer> entry : BiomeShardNBTHelper.getIslandBiomes(stack).entrySet()) {
            tooltip.add(ForgeRegistries.BIOMES.getValue(entry.getKey()).getBiomeName() + ": " + entry.getValue()); //TODO localize and format
        }
    }
}
