package com.github.glasspane.biomeshards.item;

import com.github.glasspane.biomeshards.BiomeShards;
import com.github.glasspane.biomeshards.crafting.BiomeShardNBTHelper;
import com.github.upcraftlp.glasspane.item.ItemBase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.*;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBiomeShard extends ItemBase {

    /**
     * how long it takes to attune a crystal
     */
    private static final int TIMESPAN = 20 * 5; //5 seconds

    public ItemBiomeShard(String name) {
        super(name);
        this.setCreativeTab(BiomeShards.CREATIVE_TAB);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem) {
        ItemStack stack = entityItem.getItem();
        if(!BiomeShardNBTHelper.isShardAttuned(stack) && entityItem.getAge() >= TIMESPAN) {
            BiomeShardNBTHelper.setShardBiome(entityItem.getEntityWorld().getBiome(entityItem.getPosition()), stack);
            entityItem.playSound(SoundEvents.ENTITY_ARROW_HIT_PLAYER, 1.0F, 1.0F); //TODO different sound / animation
        }
        return super.onEntityItemUpdate(entityItem);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return BiomeShardNBTHelper.isShardAttuned(stack) || super.hasEffect(stack);
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        String langKey = super.getTranslationKey(stack);
        return BiomeShardNBTHelper.isShardAttuned(stack) ? langKey.substring(0, langKey.lastIndexOf('.') + 1) + "attuned_" + langKey.substring(langKey.lastIndexOf('.') + 1) : langKey;
    }

    @SuppressWarnings("ConstantConditions")
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        //TODO format tooltip via translation key (Biome: $NAME)
        if(BiomeShardNBTHelper.isShardAttuned(stack)) tooltip.add(TextFormatting.ITALIC.toString() + ForgeRegistries.BIOMES.getValue(BiomeShardNBTHelper.getShardBiome(stack)).getBiomeName());
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return BiomeShardNBTHelper.isShardAttuned(stack) ? 1 : super.getItemStackLimit(stack);
    }
}
