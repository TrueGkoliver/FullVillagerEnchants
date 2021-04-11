package com.gkoliver.maxedvillagers.mixin;

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
    private int xpValue;
    @Inject(at=@At("HEAD"), method="getOffer", cancellable = true)
    private void getOfferInjection(Entity trader, Random rand, CallbackInfoReturnable<MerchantOffer> cir) {
        List<Enchantment> list = Registry.ENCHANTMENT.stream().filter(Enchantment::canVillagerTrade).collect(Collectors.toList());
        Enchantment enchantment = list.get(rand.nextInt(list.size()));
        int i = enchantment.getMaxLevel();
        ItemStack itemstack = EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(enchantment, i));
        int j = 2 + rand.nextInt(5 + i * 10) + 3 * i;
        if (enchantment.isTreasureEnchantment()) {
            j *= 2;
        }

        if (j > 64) {
            j = 64;
        }

        cir.setReturnValue(new MerchantOffer(new ItemStack(Items.EMERALD, j), new ItemStack(Items.BOOK), itemstack, 12, this.xpValue, 0.2F));
    }
    /*@ModifyVariable(method="getOffer", ordinal=0, at=@At(value="STORE", ordinal=5), name="i", print = true)
    private int getMaxEnchantment(int i) {
        return 1;
    }*/
}
