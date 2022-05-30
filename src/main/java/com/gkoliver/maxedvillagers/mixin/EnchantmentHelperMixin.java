package com.gkoliver.maxedvillagers.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {
    /**Why am I overwriting this? Because it's the cleanest way. Cry harder, other modders.
     * @author Gkoliver
     */
    @Overwrite
    public static int getEnchantmentLevel(CompoundTag p_182439_) {
        return p_182439_.getInt("lvl");
    }
}
