package com.gkoliver.maxedvillagers.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class MaxedVillagerConfigs {
    public static class Configuration {
        public final ForgeConfigSpec.BooleanValue MIN_MAX;
        public final ForgeConfigSpec.BooleanValue DECAPITATION;
        public Configuration(ForgeConfigSpec.Builder builderIn) {
            builderIn.comment("Maxed Villager Enchants")
                    .push("general");
            MIN_MAX = builderIn.comment("If this value is true, the default functionality (maxed enchants) will be on. If it is false, the enchants will always be the minimum value.")
                    .translation("maxedvillagers.config.min_max")
                    .define("maxed", true);
            DECAPITATION = builderIn.comment("If this value is true, \"Decapitation Mode\" will be on. All enchants will be capable of being selected, with the exception of the maximum enchant, which can not be selected. If the maximum level is 1, that level can still be chosen. This overrides the min/max option.")
                    .translation("maxedvillagers.config.decapitation")
                    .define("decapitation", false);
            builderIn.pop(1);
        }
    }
    public static final ForgeConfigSpec CONFIGSPEC;
    public static final Configuration CONFIG;
    static {
        final Pair<Configuration, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(Configuration::new);
        CONFIGSPEC = pair.getRight();
        CONFIG = pair.getLeft();
    }
}
