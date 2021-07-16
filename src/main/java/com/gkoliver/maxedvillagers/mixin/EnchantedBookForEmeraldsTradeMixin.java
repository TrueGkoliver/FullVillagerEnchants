package com.gkoliver.maxedvillagers.mixin;

import com.gkoliver.maxedvillagers.config.MaxedVillagerConfigs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.util.Locals;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Mixin(VillagerTrades.EnchantedBookForEmeraldsTrade.class)
public abstract class EnchantedBookForEmeraldsTradeMixin {
    @Shadow
    @Final
    private int villagerXp;
    ThreadLocal<Enchantment> enchantmentThreadLocal = new ThreadLocal<>();
    @Inject(at=@At(value="INVOKE", target = "net/minecraft/enchantment/Enchantment.getMinLevel()I", shift = At.Shift.BEFORE), method="getOffer", locals = LocalCapture.CAPTURE_FAILHARD)
    private void gainEnchantment(Entity p_221182_1_, Random p_221182_2_, CallbackInfoReturnable<MerchantOffer> cir, List<Enchantment> null1, Enchantment enchant) {
        enchantmentThreadLocal.set(enchant);
    }
    private static final Random rand = new Random();
    @SuppressWarnings("SpellCheckingInspection")
    @ModifyVariable(
            method= "getOffer(Lnet/minecraft/entity/Entity;Ljava/util/Random;)Lnet/minecraft/item/MerchantOffer;",
            require=1,
            index=5,
            at=@At(
                    value="STORE",
                    ordinal=0
            )
    )
    private int getMaxEnchantment(int old, Entity p_221182_1_, Random p_221182_2_) {
        final Enchantment enchantment = enchantmentThreadLocal.get();
        enchantmentThreadLocal.remove();
        if (MaxedVillagerConfigs.CONFIG.DECAPITATION.get()) {
            int debug = MathHelper.nextInt(rand, enchantment.getMinLevel(), Math.max(1, enchantment.getMaxLevel()-1));
            return debug;
        }
        if (MaxedVillagerConfigs.CONFIG.MIN_MAX.get()) {
            return enchantment.getMaxLevel();
        } else {
            return enchantment.getMinLevel();
        }
    }
}
