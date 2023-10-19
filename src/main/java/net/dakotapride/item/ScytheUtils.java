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
import net.minecraft.util.TypedActionResult;

public class ScytheUtils {
    static Enchantment gazing = SpectreEnchantments.GAZING;
    static Enchantment cultist = SpectreEnchantments.CULTIST;
    static Enchantment bloodRush = SpectreEnchantments.BLOOD_RUSH;
    static Enchantment adrift = SpectreEnchantments.ADRIFT;
    static Enchantment cloaking = SpectreEnchantments.CLOAKING;
    static Enchantment connection = SpectreEnchantments.CONNECTION;
    static Enchantment spiritualLeech = SpectreEnchantments.SPIRITUAL_LEECH;

    public static boolean hasCultistEnchantment(ItemStack stack) {
        return EnchantmentHelper.getLevel(cultist, stack) > 0;
    }

    public static boolean hasGazingEnchantment(ItemStack stack) {
        return EnchantmentHelper.getLevel(gazing, stack) > 0;
    }

    public static boolean hasSpiritualLeech(ItemStack stack) {
        return EnchantmentHelper.getLevel(spiritualLeech, stack) > 0;
    }

    public static boolean canBeInflictedByScythe(LivingEntity entity) {
        return entity instanceof PlayerEntity || entity instanceof PhantomEntity;
    }

    public static boolean canApplyStrengthFromBloodRush(LivingEntity entity) {
        return EnchantmentHelper.getLevel(bloodRush, entity.getMainHandStack()) > 0 && entity.getOffHandStack().isOf(SpectreItems.UNWEARIED_ANTIQUE)
                && entity.getMainHandStack().getItem() instanceof SpectreScytheItem;
    }

    public static boolean getBloodRushLevel(LivingEntity entity, int level) {
        return EnchantmentHelper.getLevel(bloodRush, entity.getMainHandStack()) == level;
    }

    public static TypedActionResult<ItemStack> applyBloodRushEffects(LivingEntity entity, ItemStack stack) {
        if (canApplyStrengthFromBloodRush(entity)) {
            if (getBloodRushLevel(entity, 1)) {
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, SpectreConfig.getInstance().bloodRushStrengthDuration * 20, 0));
            } else if (getBloodRushLevel(entity, 2)) {
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, SpectreConfig.getInstance().bloodRushStrengthDuration * 20, 1));
            } else if (getBloodRushLevel(entity, 3)) {
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, SpectreConfig.getInstance().bloodRushStrengthDuration * 20, 2));
            }

            if (entity instanceof PlayerEntity player) {
                stack.damage(1, entity, (e -> e.sendToolBreakStatus(entity.getActiveHand())));
                player.getItemCooldownManager().set(stack.getItem(), SpectreConfig.getInstance().antiqueCooldown * 20);
            }

            return TypedActionResult.success(stack);
        }

        return TypedActionResult.fail(stack);
    }

}
