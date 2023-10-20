package net.dakotapride.item;

import net.dakotapride.registry.SpectreEffects;
import net.dakotapride.registry.SpectreEnchantments;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ScytheOfIchorItem extends SwordItem {
    // More Attack Damage, less Attack Speed ((5, -2.2F) respectively)
    // Can mine up to Netherite level (ToolMaterials.NETHERITE)
    //
    // New Enchants
    //
    // Vigilant [Only 1 Level]
    // The wielder gains Speed 2 for a duration of time and gains Night Vision.
    //
    // Eclipse [3 Levels]
    // The wielder gains Resistance 3 for a duration of time and inflicts Glowing on the target
    //
    // Writhing [Only 1 Level]
    // The wielder is slowly damaged while holding the weapon, but also gains Strength 2 for that duration
    //
    // Sanguinary [Only 1 Level]
    // Activated by the Unwearied Antique, the wielder gains Strength 5 at the cost of 8 health (4 hearts)
    // This enchantment is not compatible with Writhing
    //
    // Nemesis [Only 1 Level]
    // Inflicts the target with Infliction [Swift Ticking damage]

    public ScytheOfIchorItem(Settings settings) {
        super(ToolMaterials.NETHERITE, 5, -2.2F, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (SpectreUtils.hasEclipseEnchantment(stack) && target instanceof PlayerEntity player) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 240, 0));
        }

        if (SpectreUtils.hasNemesisEnchantment(stack) && target instanceof PlayerEntity player) {
            player.addStatusEffect(new StatusEffectInstance(SpectreEffects.INFLICTION, 100, 0));
        }

        if (SpectreUtils.hasSpiritualLeechEnchantment(stack) && target instanceof PhantomEntity) {
            int restore = (int) Math.max(Math.ceil(target.getMaxHealth() / 5), 4);
            restore = attacker.getEntityWorld().random.nextInt(restore + 1);
            if (restore > 0) {
                if (attacker.getHealth() < attacker.getMaxHealth()) {
                    attacker.heal(restore);
                }
            }
        }

        return super.postHit(stack, target, attacker);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
    }
}
