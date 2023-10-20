package net.dakotapride.mixin;

import net.dakotapride.registry.SpectreDamageSources;
import net.dakotapride.registry.SpectreEffects;
import net.dakotapride.registry.SpectreEnchantments;
import net.dakotapride.registry.SpectreItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Unique
    LivingEntity entity = (LivingEntity) (Object) this;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "baseTick", at = @At("HEAD"))
    private void spectre$baseTick(CallbackInfo ci) {
        if (entity instanceof PlayerEntity player) {
            if (EnchantmentHelper.getLevel(SpectreEnchantments.VIGILANT, player.getMainHandStack()) > 0) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 40, 1));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 40, 0));
            }

            if (EnchantmentHelper.getLevel(SpectreEnchantments.ECLIPSE, player.getMainHandStack()) > 0) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 40, 2));
            }

            if (EnchantmentHelper.getLevel(SpectreEnchantments.WRITHING, player.getMainHandStack()) > 0) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 40, 1));

                if ((player.age % 50) == 0) {
                    player.damage(SpectreDamageSources.create(player.getWorld(), SpectreDamageSources.WRITHING), 2.0F);
                }
            }
        }
    }

    @Inject(method = "dropEquipment", at = @At("HEAD"))
    private void spectre$dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops, CallbackInfo ci) {
        if (entity instanceof PhantomEntity phantom && phantom.getAttacker() instanceof PlayerEntity player
                && EnchantmentHelper.getLevel(SpectreEnchantments.CONNECTION, player.getMainHandStack()) > 0) {
            int count = random.nextInt(2);

            phantom.dropStack(new ItemStack(SpectreItems.PHANTOM_EYE, count));

            if (EnchantmentHelper.getLevel(SpectreEnchantments.CONNECTION, player.getMainHandStack()) == 2) {
                int count1 = random.nextInt(3);

                phantom.dropStack(new ItemStack(Items.PHANTOM_MEMBRANE, count1));
            }
        }
    }

}
