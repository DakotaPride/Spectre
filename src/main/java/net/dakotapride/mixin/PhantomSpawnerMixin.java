package net.dakotapride.mixin;

import net.dakotapride.config.SpectreConfig;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.spawner.PhantomSpawner;
import net.minecraft.world.spawner.Spawner;
import org.joml.Math;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;

@SuppressWarnings("unused")
@Mixin(PhantomSpawner.class)
public abstract class PhantomSpawnerMixin implements Spawner {
	@Shadow public int cooldown;

	@Unique
	int getInsomniaTicks = ((SpectreConfig.getInstance().daysUntilInsomniaTakesAffect * 20) * 60) * 20;

	@Unique
	boolean isInsomniaAllowed = SpectreConfig.getInstance().allowInsomnia;

	@Unique
	boolean insanityModeActive = SpectreConfig.getInstance().insanityPhantoms;

	@Inject(at = @At("HEAD"), method = "spawn", cancellable = true)
	private void spectre$spawn(ServerWorld world, boolean spawnsMonsters, boolean spawnsAnimals, CallbackInfoReturnable<Integer> cir) {
		if (!isInsomniaAllowed && !insanityModeActive) {
			cir.setReturnValue(0);
		} else if (insanityModeActive) {
			cir.setReturnValue(setCustomSpawningConditions(world));
		} else {
			cir.setReturnValue(setCustomSpawningConditions(world, spawnsMonsters, spawnsAnimals));
		}
	}

	@Unique
	private int setCustomSpawningConditions(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
		if (!spawnMonsters) {
			return 0;
		} else if (!isInsomniaAllowed) {
			return 0;
		} else {
			Random random = world.random;
			--this.cooldown;
			if (this.cooldown > 0) {
				return 0;
			} else {
				this.cooldown += (60 + random.nextInt(60)) * 20;
				if (world.getAmbientDarkness() < 5 && world.getDimension().hasSkyLight()) {
					return 0;
				} else {
					int i = 0;
					Iterator<ServerPlayerEntity> var6 = world.getPlayers().iterator();

					while(true) {
						LocalDifficulty localDifficulty;
						BlockPos blockPos2;
						BlockState blockState;
						FluidState fluidState;
						do {
							BlockPos blockPos;
							int j;
							do {
								ServerPlayerEntity serverPlayerEntity;
								do {
									do {
										do {
											if (!var6.hasNext()) {
												return i;
											}

											serverPlayerEntity = var6.next();
										} while(serverPlayerEntity.isSpectator());

										blockPos = serverPlayerEntity.getBlockPos();
									} while(world.getDimension().hasSkyLight() && (blockPos.getY() < world.getSeaLevel() || !world.isSkyVisible(blockPos)));

									localDifficulty = world.getLocalDifficulty(blockPos);
								} while(!localDifficulty.isHarderThan(random.nextFloat() * 3.0F));

								ServerStatHandler serverStatHandler = serverPlayerEntity.getStatHandler();
								j = MathHelper.clamp(serverStatHandler.getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST)), 1, Integer.MAX_VALUE);
								// int k = true;
							} while(random.nextInt(j) < getInsomniaTicks);

							blockPos2 = blockPos.up(20 + random.nextInt(15)).east(-10 + random.nextInt(21)).south(-10 + random.nextInt(21));
							blockState = world.getBlockState(blockPos2);
							fluidState = world.getFluidState(blockPos2);
						} while(!SpawnHelper.isClearForSpawn(world, blockPos2, blockState, fluidState, EntityType.PHANTOM));

						EntityData entityData = null;
						int l = 1 + random.nextInt(localDifficulty.getGlobalDifficulty().getId() + 1);

						for(int m = 0; m < l; ++m) {
							PhantomEntity phantomEntity = EntityType.PHANTOM.create(world);
							if (phantomEntity != null) {
								phantomEntity.refreshPositionAndAngles(blockPos2, 0.0F, 0.0F);
								entityData = phantomEntity.initialize(world, localDifficulty, SpawnReason.NATURAL, entityData, null);
								world.spawnEntityAndPassengers(phantomEntity);
								++i;
							}
						}
					}
				}
			}
		}
	}

	@Unique
	private int setCustomSpawningConditions(ServerWorld world) {
		Random random = world.random;
		int phantomsSpawned = 0;
		Iterator<?> playerList = world.players.iterator();
		LocalDifficulty difficulty;
		BlockPos blockPos2;
		BlockState blockState;
		FluidState fluidState;
		PlayerEntity player;

		if (world.isDay()) {
			return 0;
		} else {
			while (playerList.hasNext()) {
				player = (PlayerEntity)playerList.next();
				if (!player.isSpectator()) {
					ServerStatHandler serverStatsCounter = ((ServerPlayerEntity) player).getStatHandler();
					int j = Math.clamp(serverStatsCounter.getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST)), 1, 2147483647);
					if (random.nextInt(j) > this.getInsomniaTicks) {
						BlockPos blockPos = player.getBlockPos();
						if (blockPos.getY() >= world.getSeaLevel() && world.isSkyVisible(blockPos)) {
							do {
								blockPos2 = blockPos.up(20 + random.nextInt(15)).east(-10 + random.nextInt(21)).south(-10 + random.nextInt(21));
								blockState = world.getBlockState(blockPos2);
								fluidState = world.getFluidState(blockPos2);
							} while (!SpawnHelper.isClearForSpawn(world, blockPos2, blockState, fluidState, EntityType.PHANTOM));
							EntityData data = null;
							difficulty = world.getLocalDifficulty(blockPos);
							int l = 1 + random.nextInt(((Math.clamp(serverStatsCounter.getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST)), 1, 2147483647) - (this.getInsomniaTicks)) / 24000)+ 1);

							for (int m = 0; m < l; ++m) {
								PhantomEntity phantom = EntityType.PHANTOM.create(world);

								assert phantom != null;
								phantom.refreshPositionAndAngles(blockPos2, 0.0F, 0.0F);
								data = phantom.initialize(world, difficulty, SpawnReason.NATURAL, data, null);
								world.spawnEntityAndPassengers(phantom);
							}
							phantomsSpawned += l;
						}
					}
				}
			}

			return phantomsSpawned;
		}
	}

}