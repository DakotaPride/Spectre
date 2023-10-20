package net.dakotapride.mixin;

import net.dakotapride.item.SpectreUtils;
import net.dakotapride.registry.SpectreEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.List;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    //most compat way to make enchantment table apply our enchantment properly
    @Inject(method = "getPossibleEntries",
            at = @At(value = "INVOKE_ASSIGN", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"),
            locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void spectre$getPossibleEntries(int power, ItemStack stack, boolean treasureAllowed,
                                                                 CallbackInfoReturnable<List<EnchantmentLevelEntry>> cir, List<?> list,
                                                                 Item item, boolean bl, Iterator<?> var6, Enchantment enchantment, int i)
    {
        if (enchantment == SpectreEnchantments.ADRIFT && !SpectreUtils.canApply$SpectreScythe(stack) && !list.isEmpty()) {
            list.remove(list.size() - 1);
        } else if (enchantment == SpectreEnchantments.BLOOD_RUSH && !SpectreUtils.canApply$SpectreScythe(stack) && !list.isEmpty()) {
            list.remove(list.size() - 1);
        } else if (enchantment == SpectreEnchantments.CLOAKING && !SpectreUtils.canApply$SpectreScythe(stack) && !list.isEmpty()) {
            list.remove(list.size() - 1);
        } else if (enchantment == SpectreEnchantments.CONNECTION && !SpectreUtils.canApply$SpectreScythe(stack) && !list.isEmpty()) {
            list.remove(list.size() - 1);
        } else if (enchantment == SpectreEnchantments.CULTIST && !SpectreUtils.canApply$SpectreScythe(stack) && !list.isEmpty()) {
            list.remove(list.size() - 1);
        } else if (enchantment == SpectreEnchantments.ECLIPSE && !SpectreUtils.canApply$IchorScythe(stack) && !list.isEmpty()) {
            list.remove(list.size() - 1);
        } else if (enchantment == SpectreEnchantments.GAZING && !SpectreUtils.canApply$SpectreScythe(stack) && !list.isEmpty()) {
            list.remove(list.size() - 1);
        } else if (enchantment == SpectreEnchantments.SANGUINARY && !SpectreUtils.canApply$IchorScythe(stack) && !list.isEmpty()) {
            list.remove(list.size() - 1);
        } else if (enchantment == SpectreEnchantments.SPIRITUAL_LEECH && !SpectreUtils.canApply$SpectreScythe(stack) && !list.isEmpty()) {
            list.remove(list.size() - 1);
        } else if (enchantment == SpectreEnchantments.VIGILANT && !SpectreUtils.canApply$IchorScythe(stack) && !list.isEmpty()) {
            list.remove(list.size() - 1);
        } else if (enchantment == SpectreEnchantments.WRITHING && !SpectreUtils.canApply$IchorScythe(stack) && !list.isEmpty()) {
            list.remove(list.size() - 1);
        } else if (enchantment == SpectreEnchantments.NEMESIS && !SpectreUtils.canApply$IchorScythe(stack) && !list.isEmpty()) {
            list.remove(list.size() - 1);
        }
    }
}
