package com.github.glasspane.biomeshards;

import com.github.glasspane.biomeshards.init.*;
import com.github.upcraftlp.glasspane.api.proxy.IProxy;
import com.github.upcraftlp.glasspane.util.ModUpdateHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;

import static com.github.glasspane.biomeshards.BiomeShards.*;

@Mod(
        certificateFingerprint = FINGERPRINT_KEY,
        name = MODNAME,
        version = VERSION,
        acceptedMinecraftVersions = MCVERSIONS,
        modid = MODID,
        dependencies = DEPENDENCIES,
        updateJSON = UPDATE_JSON
)
public class BiomeShards {

    //Version
    public static final String MCVERSIONS = "[1.12.2,1.13)";
    public static final String VERSION = "@VERSION@";

    //Meta Information
    public static final String MODNAME = "Biome Shards";
    public static final String MODID = "biome_shards";
    public static final String DEPENDENCIES = "required-after:glasspane;required-after:forge";
    public static final String UPDATE_JSON = "@UPDATE_JSON@";

    public static final String FINGERPRINT_KEY = "@FINGERPRINTKEY@";

    @SidedProxy(clientSide = "com.github.glasspane.biomeshards.proxy.ClientProxy", serverSide = "com.github.glasspane.biomeshards.proxy.ServerProxy")
    public static IProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModUpdateHandler.registerMod(MODID);
        BiomeShardStats.registerStats();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        SupportedBiomes.init();
    }

    public static final CreativeTabs CREATIVE_TAB = new CreativeTabs(MODID + ".name") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(BiomeShardItems.SHARD_OF_LAPUTA);
        }
    };
}
