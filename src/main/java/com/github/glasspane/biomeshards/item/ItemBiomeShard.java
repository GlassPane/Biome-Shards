package com.github.glasspane.biomeshards.item;

import com.github.glasspane.biomeshards.BiomeShards;
import com.github.upcraftlp.glasspane.item.ItemBase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.*;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBiomeShard extends ItemBase {

    /**
     * how long it takes to attune a crystal
     */
    private static final int TIMESPAN = 20 * 5; //5 seconds
    public static final String KEY_BIOME = "AttunedBiome";

    public ItemBiomeShard(String name) {
        super(name);
        this.setCreativeTab(BiomeShards.CREATIVE_TAB);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem) {
        ItemStack stack = entityItem.getItem();
        if(!stack.isEmpty() && entityItem.getAge() >= TIMESPAN && !isAttuned(stack)) {
            if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
            stack.getTagCompound().setString(KEY_BIOME, entityItem.getEntityWorld().getBiome(entityItem.getPosition()).getRegistryName().toString());
            entityItem.playSound(SoundEvents.ENTITY_ARROW_HIT_PLAYER, 1.0F, 1.0F); //TODO different sound / animation
        }
        return super.onEntityItemUpdate(entityItem);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean hasEffect(ItemStack stack) {
        return super.hasEffect(stack) || (stack.hasTagCompound() && stack.getTagCompound().hasKey(KEY_BIOME, Constants.NBT.TAG_STRING));
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        String langKey = super.getTranslationKey(stack);
        return isAttuned(stack) ? langKey.substring(0, langKey.lastIndexOf('.') + 1) + "attuned_" + langKey.substring(langKey.lastIndexOf('.') + 1) : langKey;
    }

    public boolean isAttuned(ItemStack stack) {
        return stack.hasTagCompound() && stack.getTagCompound().hasKey(KEY_BIOME, Constants.NBT.TAG_STRING);
    }

    public Biome getBiome(ItemStack stack) {
        return isAttuned(stack) ? ForgeRegistries.BIOMES.getValue(new ResourceLocation(stack.getTagCompound().getString(KEY_BIOME))) : Biomes.DEFAULT;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if(isAttuned(stack)) tooltip.add(TextFormatting.ITALIC.toString() + TextFormatting.GRAY.toString() + getBiome(stack).getBiomeName());
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return isAttuned(stack) ? 1 : super.getItemStackLimit(stack);
    }
}
