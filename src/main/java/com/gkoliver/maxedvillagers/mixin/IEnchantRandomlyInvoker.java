package com.gkoliver.maxedvillagers.mixin;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EnchantRandomlyFunction.class)
public interface IEnchantRandomlyInvoker {
    @Invoker("m_230979_")
    ItemStack callm_230979_(ItemStack p_230980_, Enchantment p_230981_, RandomSource p_230982_);
}
