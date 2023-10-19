package net.dakotapride;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.dakotapride.config.SpectreConfig;
import net.dakotapride.registry.SpectreEffects;
import net.dakotapride.registry.SpectreEnchantments;
import net.dakotapride.registry.SpectreItems;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;

public class SpectreMain implements ModInitializer {
	public static final String id = "spectre";

	@Override
	public void onInitialize() {
		SpectreEffects.effects();
		SpectreEnchantments.enchantments();
		SpectreItems.items();

		AutoConfig.register(SpectreConfig.class, JanksonConfigSerializer::new);

		if (SpectreConfig.getInstance().allowEndSpawning) {
			BiomeModifications.addSpawn(BiomeSelectors.foundInTheEnd(), SpawnGroup.MONSTER,
					EntityType.PHANTOM, SpectreConfig.getInstance().endSpawnWeight,
					SpectreConfig.getInstance().minEndSpawnSize, SpectreConfig.getInstance().maxEndSpawnSize);
		}
	}

	public static Identifier createResource(String name) {
		return new Identifier(id, name);
	}
}
