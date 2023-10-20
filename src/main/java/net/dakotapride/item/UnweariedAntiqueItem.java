package net.dakotapride.item;

import net.dakotapride.config.SpectreConfig;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class UnweariedAntiqueItem extends Item {
    public UnweariedAntiqueItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (SpectreUtils.canApplyStrengthFromBloodRush(user)) {
            SpectreUtils.applyBloodRushEffects(user);
            stack.damage(1, user, p -> p.sendToolBreakStatus(Hand.OFF_HAND));
            user.getItemCooldownManager().set(this, SpectreConfig.getInstance().antiqueCooldown * 20);
        }

        if (SpectreUtils.canApplyStrengthFromSanguinary(user)) {
            SpectreUtils.applySanguinaryEffects(user);
            stack.damage(2, user, p -> p.sendToolBreakStatus(Hand.OFF_HAND));
            user.getItemCooldownManager().set(this, SpectreConfig.getInstance().antiqueCooldown * 20);
        }

        return TypedActionResult.fail(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
    }
}
