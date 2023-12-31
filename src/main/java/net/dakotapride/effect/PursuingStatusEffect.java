package net.dakotapride.effect;

import net.dakotapride.registry.SpectreDamageSources;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class PursuingStatusEffect extends StatusEffect {
    public PursuingStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 0x21BC89);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(entity, amplifier);
        entity.damage(SpectreDamageSources.create(entity.getWorld(), SpectreDamageSources.PURSUING), 3.0F);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        int i = 25 >> amplifier;
        if (i > 0) {
            return duration % i == 0;
        } else {
            return true;
        }
    }
}
