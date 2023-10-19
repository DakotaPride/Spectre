package net.dakotapride.mixin;

import net.dakotapride.registry.SpectreEnchantments;
import net.dakotapride.registry.SpectreItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
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

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Unique
    LivingEntity entity = (LivingEntity) (Object) this;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
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
