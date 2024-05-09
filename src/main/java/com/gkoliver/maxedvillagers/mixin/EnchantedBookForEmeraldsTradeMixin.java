package com.gkoliver.maxedvillagers.mixin;

import com.gkoliver.maxedvillagers.config.MaxedVillagerConfigs;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.points.BeforeConstant;
import org.spongepowered.asm.util.Locals;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Mixin(VillagerTrades.EnchantBookForEmeralds.class)
public abstract class EnchantedBookForEmeraldsTradeMixin {
    @Shadow
    @Final
    private int villagerXp;
    ThreadLocal<Enchantment> enchantmentThreadLocal = new ThreadLocal<>();
    //final String OFFER = "Lnet/minecraft/world/entity/npc/VillagerTrades$EnchantBookForEmeralds;getOffer(Lnet/minecraft/world/entity/Entity;Ljava/util/Random;)Lnet/minecraft/world/item/trading/MerchantOffer;";
    final String OFFER = "m_213663_";
    @Inject(at=@At(value="INVOKE", target = "Lnet/minecraft/world/item/enchantment/Enchantment;getMinLevel()I", shift = At.Shift.BEFORE), method="m_213663_", locals = LocalCapture.CAPTURE_FAILHARD)
    private void gainEnchantment(Entity p_219688_, RandomSource p_219689_, CallbackInfoReturnable<MerchantOffer> cir, List<Enchantment> null1, Enchantment enchant) {
        enchantmentThreadLocal.set(enchant);
    }
    @Inject(at=
    @At(value="INVOKE_ASSIGN",
            target = "net/minecraft/util/RandomSource.m_188503_ (I)I",
            ordinal = 1,
            shift=At.Shift.AFTER
    ),
            method="m_213663_",
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void doThingWithBook(Entity p_219688_, RandomSource p_219689_, CallbackInfoReturnable<MerchantOffer> cir, List null1, Enchantment null2, int null3, ItemStack stack) {
        if (MaxedVillagerConfigs.CONFIG.CURSES.get() && !null2.isCurse()) {
            List<Enchantment> curses = ForgeRegistries.ENCHANTMENTS.getValues().stream().filter((e) -> e.isCurse()).collect(Collectors.toList());
            if (curses.size() > 0) {
                Enchantment curse = curses.get(p_219689_.m_188503_(curses.size()));
                EnchantedBookItem.addEnchantment(stack, new EnchantmentInstance(curse, Mth.m_216271_(p_219689_,curse.getMinLevel(), curse.getMaxLevel()+1)));
            }
        }
    }


    private static final Random rand = new Random();
    @SuppressWarnings("SpellCheckingInspection")
    @ModifyVariable(
            method= OFFER,
            require=1,
            index=5,
            at=@At(
                    value="STORE",
                    ordinal=0
            )
    )
    private int getMaxEnchantment(int old, Entity p_221182_1_, RandomSource p_221182_2_) {
        final Enchantment enchantment = enchantmentThreadLocal.get();
        enchantmentThreadLocal.remove();
        if (MaxedVillagerConfigs.CONFIG.DECAPITATION.get()) {
            int debug = Mth.m_216271_(p_221182_2_,enchantment.getMinLevel(), Math.max(1, enchantment.getMaxLevel()-1));
            return debug;
        }
        if (MaxedVillagerConfigs.CONFIG.MIN_MAX.get()) {
            return enchantment.getMaxLevel();
        } else {
            return enchantment.getMinLevel();
        }
    }
    @ModifyArg(at=@At(value="INVOKE", target = "java/util/stream/Stream.filter (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;", ordinal=0), method=OFFER)
    private Predicate<Enchantment> overrideChecker(Predicate<Enchantment> old) {
        System.out.println(old);
        if (MaxedVillagerConfigs.CONFIG.NO_RARE.get() && MaxedVillagerConfigs.CONFIG.ALL_ALLOWED.get()) {
            return (Enchantment e) -> (!e.isTreasureOnly());
        }
        if (MaxedVillagerConfigs.CONFIG.NO_RARE.get()) {
            return (Enchantment e) -> (e.isTradeable() && !e.isTreasureOnly());
        }
        if (MaxedVillagerConfigs.CONFIG.ALL_ALLOWED.get()) {
            return (Enchantment e) -> true;
        }
        return old;
    }
    @ModifyConstant(method=OFFER, constant = @Constant(intValue = 2, ordinal=1))
    private int doPriceWaiver(int old) {
        if (MaxedVillagerConfigs.CONFIG.PRICE_WAIVER.get()) {
            return 1;
        }
        else return old;
    }

}
