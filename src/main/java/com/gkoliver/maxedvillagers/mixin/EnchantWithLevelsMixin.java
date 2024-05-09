package com.gkoliver.maxedvillagers.mixin;

import com.gkoliver.maxedvillagers.MaxedVillagers;
import com.gkoliver.maxedvillagers.config.MaxedVillagerConfigs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mixin(EnchantWithLevelsFunction.class)
public abstract class EnchantWithLevelsMixin {
    @Inject(method="run", at=@At("RETURN"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void change(ItemStack p_80483_, LootContext p_80484_, CallbackInfoReturnable<ItemStack> cir, RandomSource src) {
        ItemStack stack = cir.getReturnValue();
        final Map<Enchantment, Integer> yadda = stack.getAllEnchantments();
        final boolean[] curseIn = new boolean[1];
        yadda.forEach((ench, lvl)->{
            if (ench.isCurse()) { curseIn[0] = true; }
            yadda.put(ench, MaxedVillagers.getNewLootEnchant(ench, src, lvl));
        });
        if (MaxedVillagerConfigs.CONFIG.LOOT_CURSES.get() && !curseIn[0]) {

            List<Enchantment> curses = ForgeRegistries.ENCHANTMENTS.getValues().stream().filter((e) -> {return e.canEnchant(stack) && e.isCurse();}).collect(Collectors.toList());
            if (curses.size() > 0) {
                Enchantment curse = curses.get(src.m_188503_(curses.size()));
                yadda.put(curse, MaxedVillagers.getNewLootEnchant(curse, src, (curse.getMinLevel()==curse.getMaxLevel())? curse.getMinLevel() : (src.m_216339_(curse.getMinLevel(), curse.getMaxLevel()))));
            }
        }
        EnchantmentHelper.setEnchantments(yadda, p_80483_);
        cir.setReturnValue(p_80483_);
    }
}
