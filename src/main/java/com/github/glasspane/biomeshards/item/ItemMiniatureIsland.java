package com.github.glasspane.biomeshards.item;

import com.github.glasspane.biomeshards.BiomeShards;
import com.github.glasspane.biomeshards.init.*;
import com.github.upcraftlp.glasspane.api.structure.StructureLoaders;
import com.github.upcraftlp.glasspane.item.ItemBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;

public class ItemMiniatureIsland extends ItemBase {

    public ItemMiniatureIsland(String name) {
        super(name);
        this.setCreativeTab(BiomeShards.CREATIVE_TAB);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if(!worldIn.isRemote) {
            //TODO spawn composite island
            PlacementSettings placementSettings = new PlacementSettings();
            placementSettings.setRotation(Rotation.values()[itemRand.nextInt(Rotation.values().length)]);
            BlockPos pos = playerIn.getPosition();
            ResourceLocation structure = SupportedBiomes.getStructure(worldIn.getBiome(pos));
            if(structure != null) {
                StructureLoaders.VANILLA_NBT.placeInWorld(structure, worldIn, pos, placementSettings, true);
                playerIn.addStat(BiomeShardStats.ISLANDS_SPAWNED);
                if(!playerIn.isCreative()) stack.shrink(1);
            }
            else playerIn.sendStatusMessage(new TextComponentTranslation("message.biome_shards.wrongBiome", worldIn.getBiome(pos).getRegistryName()).setStyle(new Style().setColor(TextFormatting.RED)), true);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }
}
