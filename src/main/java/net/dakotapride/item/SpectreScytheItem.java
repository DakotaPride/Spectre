package net.dakotapride.item;

import net.dakotapride.config.SpectreConfig;
import net.dakotapride.registry.SpectreEffects;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpectreScytheItem extends SwordItem {
    // More Attack Damage, less Attack Speed ((5, -2.6F) respectively)
    // Can mine up to Netherite level (ToolMaterials.NETHERITE)
    //
    // New Enchants
    //
    // Gazing [Only 1 level]
    // The target is inflicted by Pursuing, which swiftly damages the player for a given amount of time [3 seconds]
    //
    // Cultist [Only 1 level]
    // Inflict players who are below half health with the Enlightened effect
    // This enchantment is not compatible with Gazing
    //
    // Blood Rush [3 levels]
    // The wielder gains a level of Strength per level of Blood Rush on the weapon, going up to Strength 3
    // This is activated by right-clicking with the Unwearied Antique in the offhand. This damages the Antique 1 time per use. -
    // - This then activates a cooldown of the Antique before it can be used again.
    //
    // Adrift [Only 1 level]
    // The wielder gains Slow Falling for the duration of holding the weapon
    //
    // Cloaking [Only 1 level]
    // The wielder is invisible. There are also no potion effect particles
    // This enchantment is not compatible with Adrift
    //
    // Connection [2 levels]
    // Acts like looting, but only for certain drops. Such as, Phantom Eyes. Additional Phantom Membranes can drop if the enchantment is maxed
    //
    // Spiritual Leech [Only 1 level]
    // Basically life leech from damaging Phantoms

    public SpectreScytheItem(Settings settings) {
        super(ToolMaterials.NETHERITE, 5, -2.6F, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (SpectreUtils.hasCultistEnchantment(stack) && target instanceof PlayerEntity player && player.getHealth() <= 10) {
            target.addStatusEffect(new StatusEffectInstance(SpectreEffects.ENLIGHTENED, 300, 0));
        }

        if (SpectreUtils.hasGazingEnchantment(stack) && target instanceof PlayerEntity) {
            target.addStatusEffect(new StatusEffectInstance(SpectreEffects.PURSUING, SpectreConfig.getInstance().pursuingEffectSeconds * 20, 0));
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
}
