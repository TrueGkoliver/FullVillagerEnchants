package com.gkoliver.maxedvillagers;

import com.gkoliver.maxedvillagers.config.MaxedVillagerConfigs;
import net.minecraft.core.Registry;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("maxedvillagers")
public class MaxedVillagers
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static int getNewLootEnchant(Enchantment enchant, RandomSource source, int origLevel) {
        int mode = MaxedVillagerConfigs.CONFIG.LOOT_MODE.get();
        if (mode==0) {
            return origLevel;
        }
        else if (mode==1) {
            return enchant.getMaxLevel();
        }
        else if (mode==2) {
            return enchant.getMinLevel();
        }
        else if (mode==3) {
            return Mth.m_216271_(source,enchant.getMinLevel(), Math.max(1, enchant.getMaxLevel()-1));
        }
        return origLevel;
    }
    public MaxedVillagers() {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, MaxedVillagerConfigs.CONFIGSPEC, "maxed-villagers-common.toml");
    }

}
