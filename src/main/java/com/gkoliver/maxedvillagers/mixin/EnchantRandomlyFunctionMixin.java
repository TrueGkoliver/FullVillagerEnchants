package com.gkoliver.maxedvillagers.mixin;

import com.gkoliver.maxedvillagers.MaxedVillagers;
import com.gkoliver.maxedvillagers.config.MaxedVillagerConfigs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mixin(EnchantRandomlyFunction.class)
public abstract class EnchantRandomlyFunctionMixin {
    //For the public record: the Minecraft Mod Development plugin for IntelliJ absolutely HATES this file.
    // dw though, despite the errors it works fine :)
    @ModifyVariable(method="m_230979_",at=@At(value="STORE"),ordinal=0)
    private static int changeEnchantLevel(int value, ItemStack p_230980_, Enchantment p_230981_, RandomSource p_230982_) {
        return MaxedVillagers.getNewLootEnchant(p_230981_,p_230982_,value);
    }



    @Inject(method="run", at=@At(value="RETURN"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void addCurses(ItemStack p_80429_, LootContext p_80430_, CallbackInfoReturnable<ItemStack> cir,RandomSource randomsource) {
        System.out.println("This is a pre-test");
        if (cir.getReturnValue()==null) {
            System.out.println(p_80429_);
            System.out.println("Caught as null?");
            return;
        }
        System.out.println("OK, we got past here");

        final ItemStack sth = cir.getReturnValue().copy();
        System.out.println(sth);
        Map<Enchantment, Integer> map = p_80429_.getAllEnchantments();
        boolean flag = false;
        for (Enchantment enc : map.keySet()) {
            System.out.println(enc);
            if (enc.isCurse()) {
                flag = true;
                break;
            }
        }
        System.out.println(flag);
        if (MaxedVillagerConfigs.CONFIG.LOOT_CURSES.get() && !flag) {
            List<Enchantment> curses = ForgeRegistries.ENCHANTMENTS.getValues().stream().filter((e) -> {return (ItemStack.sameItem(sth,new ItemStack(Items.ENCHANTED_BOOK))||e.canEnchant(sth)) && e.isCurse();}).collect(Collectors.toList());
            if (curses.size() > 0) {
                System.out.println("Got to line 58");
                Enchantment curse = curses.get(randomsource.m_188503_( curses.size()));
                System.out.println(curse);
                map.put(curse,MaxedVillagers.getNewLootEnchant(curse,randomsource,(curse.getMinLevel()==curse.getMaxLevel())? curse.getMinLevel() : (randomsource.m_216339_(curse.getMinLevel(), curse.getMaxLevel()))));
                System.out.println(map);
                EnchantmentHelper.setEnchantments(map,sth);
                cir.setReturnValue(sth);
                //p_80429_ = ((IEnchantRandomlyInvoker)this).callm_230979_(p_80429_, curse, randomsource);
            }
        }
    }
}
