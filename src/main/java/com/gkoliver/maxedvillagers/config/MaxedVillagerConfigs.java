package com.gkoliver.maxedvillagers.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class MaxedVillagerConfigs {
    public static class Configuration {
        public final ForgeConfigSpec.BooleanValue MIN_MAX;
        public final ForgeConfigSpec.BooleanValue DECAPITATION;
        public final ForgeConfigSpec.BooleanValue PRICE_WAIVER;
        public final ForgeConfigSpec.BooleanValue NO_RARE;
        public final ForgeConfigSpec.BooleanValue CURSES;
        public final ForgeConfigSpec.BooleanValue ALL_ALLOWED;
        public Configuration(ForgeConfigSpec.Builder builderIn) {
            builderIn.comment("Maxed Villager Enchants")
                    .push("general");
            MIN_MAX = builderIn.comment("If this value is true, the default functionality (maxed enchants) will be on. If it is false, the enchants will always be the minimum value.")
                    .translation("maxedvillagers.config.min_max")
                    .define("maxed", true);
            DECAPITATION = builderIn.comment("If this value is true, \"Decapitation Mode\" will be on. All enchants will be capable of being selected, with the exception of the maximum enchant, which can not be selected. If the maximum level is 1, that level can still be chosen. This overrides the min/max option.")
                    .translation("maxedvillagers.config.decapitation")
                    .define("decapitation", false);
            PRICE_WAIVER = builderIn.comment("If this value is false, then all behavior is as normal. If it is true, then the x3 price increase for treasure enchants is removed.")
                    .translation("maxedvillagers.config.price_waiver")
                    .define("price_waiver", false); //DONE
            NO_RARE = builderIn.comment("If this value is false, then all behavior is normal. If it is true, then no treasure enchantments may be traded.")
                    .translation("maxedvillagers.config.no_treasure")
                    .define("no_treasure", false); //DONE
            CURSES = builderIn.comment("If this value is false, then all behavior is normal. If it is true, then all enchantment books will spawn with a curse on them.")
                    .translation("maxedvillagers.config.cursed_books")
                    .define("cursed_books", false);
            ALL_ALLOWED = builderIn.comment("If this value is false, then all behavior is normal. If it is true, then all enchantments will be tradable, even those locked off normally (such as Soul Speed). This does not override the other options, however; if treasure enchants are disabled, they will not spawn.")
                    .translation("maxedvillagers.config.override_allowed")
                    .define("all_allowed", true); //DONE
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
