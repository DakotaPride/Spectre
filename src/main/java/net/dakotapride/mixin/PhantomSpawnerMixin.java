package net.dakotapride.mixin;

import net.dakotapride.config.SpectreConfig;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.spawner.PhantomSpawner;
import net.minecraft.world.spawner.Spawner;
import org.joml.Math;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;

@SuppressWarnings("unused")
@Mixin(PhantomSpawner.class)
public abstract class PhantomSpawnerMixin implements Spawner {
	@Unique
	private int insomniaTick;

	@Unique
	private boolean allowInsomnia = SpectreConfig.getInstance().allowInsomnia;
	@Unique
	private int getTicks = ((SpectreConfig.getInstance().daysUntilInsomniaTakesAffect * 20) * 60) * 20;

	@Inject(at = @At("HEAD"), method = "spawn", cancellable = true)
	private void spectre$spawn(ServerWorld world, boolean monsters, boolean animals, CallbackInfoReturnable<Integer> cir) {
		if (!monsters) {
			cir.setReturnValue(0);
		}

		if (allowInsomnia) {
			--this.insomniaTick;
			if (this.insomniaTick <= 0) {
				this.insomniaTick += (60 + world.random.nextInt(60)) * 20;
				if (!world.isDay()) {
					cir.setReturnValue(spawn(world));
				}
			}
		}

		cir.setReturnValue(0);
	}

	@Unique
	private int spawn(ServerWorld world) {
		Random random = world.random;
		int getSpawnedEntities = 0;
		Iterator<ServerPlayerEntity> playerList = world.players.iterator();
		LocalDifficulty difficultyInstance;
		BlockPos blockPos2;
		BlockState blockState;
		FluidState fluidState;
		ServerPlayerEntity player;
		while (playerList.hasNext()) {
			player = playerList.next();
			if (!player.isSpectator()) {
				ServerStatHandler states = player.getStatHandler();
				int j = Math.clamp(states.getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST)), 1, 2147483647);
				if (random.nextInt(j) > getTicks) {
					BlockPos blockPos = player.getBlockPos();
					if (blockPos.getY() >= world.getSeaLevel() && world.isSkyVisible(blockPos) && (999 >= world.getLightLevel(blockPos))) {
						do {
							blockPos2 = blockPos.up(20 + random.nextInt(15)).east(-10 + random.nextInt(21)).south(-10 + random.nextInt(21));
							blockState = world.getBlockState(blockPos2);
							fluidState = world.getFluidState(blockPos2);
						} while (!SpawnHelper.isClearForSpawn(world, blockPos2, blockState, fluidState, EntityType.PHANTOM));
						EntityData data = null;
						difficultyInstance = world.getLocalDifficulty(blockPos);
						int l = 1 + random.nextInt(((Math.clamp(states.getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST)), 1, 2147483647) - getTicks	) / 24000)+ 1);

						for (int m = 0; m < l; ++m) {
							PhantomEntity phantom = EntityType.PHANTOM.create(world);
							phantom.refreshPositionAndAngles(blockPos2, 0.0F, 0.0F);
							data = phantom.initialize(world, difficultyInstance, SpawnReason.NATURAL, data, null);
							world.spawnEntityAndPassengers(phantom);
						}
						getSpawnedEntities += l;
					}
				}
			}
		}
		return getSpawnedEntities;
	}

}