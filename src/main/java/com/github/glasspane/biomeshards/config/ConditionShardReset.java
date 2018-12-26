package com.github.glasspane.biomeshards.config;

import com.google.gson.JsonObject;
import net.minecraftforge.common.crafting.*;

import java.util.function.BooleanSupplier;

@SuppressWarnings("unused")
public class ConditionShardReset implements IConditionFactory {

    @Override
    public BooleanSupplier parse(JsonContext context, JsonObject json) {
        return () -> BiomeShardsConfig.enableShardResetRecipe;
    }
}
