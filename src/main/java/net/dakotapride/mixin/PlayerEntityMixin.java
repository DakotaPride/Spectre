package net.dakotapride.mixin;

import net.dakotapride.registry.SpectreEffects;
import net.dakotapride.registry.SpectreEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Unique
    PlayerEntity player = (PlayerEntity) (Object) this;

    public PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "travel", at = @At("HEAD"))
    private void spectre$travel(Vec3d movementInput, CallbackInfo ci) {
        if (EnchantmentHelper.getLevel(SpectreEnchantments.ADRIFT, player.getMainHandStack()) > 0) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 40, 0));
        }
    }

}
