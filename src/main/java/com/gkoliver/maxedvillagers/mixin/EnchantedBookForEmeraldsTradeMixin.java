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

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Mixin(VillagerTrades.EnchantedBookForEmeraldsTrade.class)
public abstract class EnchantedBookForEmeraldsTradeMixin {
    @Shadow
    @Final
    private int villagerXp;
    /*0@Inject(at=@At("HEAD"), method="getOffer", cancellable = true)
    private void getOfferInjection(Entity trader, Random rand, CallbackInfoReturnable<MerchantOffer> cir) {
        List<Enchantment> list = Registry.ENCHANTMENT.stream().filter(Enchantment::isTradeable).collect(Collectors.toList());
        Enchantment enchantment = list.get(rand.nextInt(list.size()));
        int i = enchantment.getMaxLevel();
        ItemStack itemstack = EnchantedBookItem.createForEnchantment(new EnchantmentData(enchantment, i));
        int j = 2 + rand.nextInt(5 + i * 10) + 3 * i;
        if (enchantment.isTreasureOnly()) {
            j *= 2;
        }

        if (j > 64) {
            j = 64;
        }

        cir.setReturnValue(new MerchantOffer(new ItemStack(Items.EMERALD, j), new ItemStack(Items.BOOK), itemstack, 12, this.villagerXp, 0.2F));
    }*/
    /*@ModifyVariable(print = true, method= "getOffer(Lnet/minecraft/entity/Entity;Ljava/util/Random;)Lnet/minecraft/item/MerchantOffer;", at=@At(value="STORE", ordinal=0))
    private Object getMaxEnchantment() {
        return null;
    }*/
    private static final Random rand = new Random();
    @SuppressWarnings("SpellCheckingInspection")
    @ModifyVariable(
            method= "getOffer(Lnet/minecraft/entity/Entity;Ljava/util/Random;)Lnet/minecraft/item/MerchantOffer;",
            require=1,
            index=5,
            at=@At(
                    value="STORE",
                    ordinal=0
            ),
            print = true
    )
    private int getMaxEnchantment(int old, Entity p_221182_1_, Random p_221182_2_) {
        /*if (MaxedVillagerConfigs.CONFIG.DECAPITATION.get()) {
            return MathHelper.nextInt(rand, enchantment.getMinLevel(), Math.max(1, enchantment.getMaxLevel()-1));
        }
        if (MaxedVillagerConfigs.CONFIG.MIN_MAX.get()) {
            return enchantment.getMaxLevel();
        } else {
            return enchantment.getMinLevel();
        }*/
        return 1;
    }
}
