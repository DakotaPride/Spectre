package net.dakotapride.registry;

import net.dakotapride.SpectreMain;
import net.dakotapride.effect.EnlightenedStatusEffect;
import net.dakotapride.effect.PursuingStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class SpectreEffects {

    public static StatusEffect ENLIGHTENED;
    public static StatusEffect PURSUING;

    public static void effects() {
        ENLIGHTENED = Registry.register(Registries.STATUS_EFFECT, SpectreMain.createResource("enlightened"),
                new EnlightenedStatusEffect());
        PURSUING = Registry.register(Registries.STATUS_EFFECT, SpectreMain.createResource("pursuing"),
                new PursuingStatusEffect());
    }

}
