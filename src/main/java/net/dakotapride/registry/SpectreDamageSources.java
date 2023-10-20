package net.dakotapride.registry;

import net.dakotapride.SpectreMain;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SpectreDamageSources {
    public static final RegistryKey<DamageType> PURSUING = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, SpectreMain.createResource("pursuing"));
    public static final RegistryKey<DamageType> INFLICTION = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, SpectreMain.createResource("infliction"));
    public static final RegistryKey<DamageType> WRITHING = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, SpectreMain.createResource("writhing"));

    public static DamageSource create(World world, RegistryKey<DamageType> key, @Nullable Entity source, @Nullable Entity attacker) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key), source, attacker);
    }

    public static DamageSource create(World world, RegistryKey<DamageType> key, @Nullable Entity attacker) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key), attacker);
    }

    public static DamageSource create(World world, RegistryKey<DamageType> key) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key));
    }
}
