package net.dakotapride.item;

import net.dakotapride.config.SpectreConfig;
import net.dakotapride.registry.SpectreEnchantments;
import net.dakotapride.registry.SpectreItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

public class SpectreUtils {
    static Enchantment gazing = SpectreEnchantments.GAZING;
    static Enchantment cultist = SpectreEnchantments.CULTIST;
    static Enchantment bloodRush = SpectreEnchantments.BLOOD_RUSH;
    static Enchantment adrift = SpectreEnchantments.ADRIFT;
    static Enchantment cloaking = SpectreEnchantments.CLOAKING;
    static Enchantment connection = SpectreEnchantments.CONNECTION;
    static Enchantment spiritualLeech = SpectreEnchantments.SPIRITUAL_LEECH;
    static Enchantment eclipse = SpectreEnchantments.ECLIPSE;
    static Enchantment sanguinary = SpectreEnchantments.SANGUINARY;
    static Enchantment vigilant = SpectreEnchantments.VIGILANT;
    static Enchantment writhing = SpectreEnchantments.WRITHING;
    static Enchantment nemesis = SpectreEnchantments.NEMESIS;

    public static boolean hasCultistEnchantment(ItemStack stack) {
        return EnchantmentHelper.getLevel(cultist, stack) > 0;
    }

    public static boolean hasGazingEnchantment(ItemStack stack) {
        return EnchantmentHelper.getLevel(gazing, stack) > 0;
    }

    public static boolean hasSpiritualLeechEnchantment(ItemStack stack) {
        return EnchantmentHelper.getLevel(spiritualLeech, stack) > 0;
    }

    public static boolean hasEclipseEnchantment(ItemStack stack) {
        return EnchantmentHelper.getLevel(eclipse, stack) > 0;
    }

    public static boolean hasNemesisEnchantment(ItemStack stack) {
        return EnchantmentHelper.getLevel(nemesis, stack) > 0;
    }

    public static boolean canBeInflictedByScythe(LivingEntity entity) {
        return entity instanceof PlayerEntity || entity instanceof PhantomEntity;
    }

    public static boolean canApplyStrengthFromBloodRush(LivingEntity entity) {
        return EnchantmentHelper.getLevel(bloodRush, entity.getMainHandStack()) > 0 && entity.getOffHandStack().isOf(SpectreItems.UNWEARIED_ANTIQUE)
                && entity.getMainHandStack().getItem() instanceof SpectreScytheItem;
    }

    public static boolean canApplyStrengthFromSanguinary(LivingEntity entity) {
        return EnchantmentHelper.getLevel(sanguinary, entity.getMainHandStack()) > 0 && entity.getOffHandStack().isOf(SpectreItems.UNWEARIED_ANTIQUE)
                && entity.getMainHandStack().getItem() instanceof ScytheOfIchorItem;
    }

    public static boolean getBloodRushLevel(LivingEntity entity, int level) {
        return EnchantmentHelper.getLevel(bloodRush, entity.getMainHandStack()) == level;
    }

    public static void applyBloodRushEffects(LivingEntity entity) {
        if (getBloodRushLevel(entity, 1)) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, SpectreConfig.getInstance().bloodRushStrengthDuration * 20, 0));
        } else if (getBloodRushLevel(entity, 2)) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, SpectreConfig.getInstance().bloodRushStrengthDuration * 20, 1));
        } else if (getBloodRushLevel(entity, 3)) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, SpectreConfig.getInstance().bloodRushStrengthDuration * 20, 2));
        }
    }

    public static void applySanguinaryEffects(LivingEntity entity) {
        entity.damage(entity.getDamageSources().magic(), 8.0F);

        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, SpectreConfig.getInstance().sanguinaryStrengthDuration * 20, 4));
    }

    public static boolean canApply$SpectreScythe(ItemStack stack) {
        return stack.getItem() instanceof SpectreScytheItem;
    }

    public static boolean canApply$ScytheOrSword(ItemStack stack) {
        return stack.getItem() instanceof SpectreScytheItem || stack.getItem() instanceof SwordItem;
    }

    public static boolean canApply$IchorScythe(ItemStack stack) {
        return stack.getItem() instanceof ScytheOfIchorItem;
    }

    public static boolean canApply$Antique(ItemStack stack) {
        return stack.getItem() instanceof UnweariedAntiqueItem;
    }

}
