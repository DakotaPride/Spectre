package net.dakotapride.mixin;

import net.dakotapride.registry.SpectreEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

    @Unique
    Entity entity = (Entity) (Object) this;

    @Inject(method = "isInvisible", at = @At("HEAD"), cancellable = true)
    private void spectre$isInvisible(CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof PlayerEntity player) {
            cir.setReturnValue(EnchantmentHelper.getLevel(SpectreEnchantments.CLOAKING, player.getMainHandStack()) > 0);
        } else {
            cir.setReturnValue(false);
        }
    }
}
