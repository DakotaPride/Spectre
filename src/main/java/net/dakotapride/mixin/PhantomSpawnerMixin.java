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

@Mixin(PhantomSpawner.class)
public abstract class PhantomSpawnerMixin implements Spawner {
	@Unique
	int getInsomniaTicks = ((SpectreConfig.getInstance().daysUntilInsomniaTakesAffect * 20) * 60) * 20;

	@Inject(at = @At("HEAD"), method = "spawn", cancellable = true)
	private void spectre$spawn(ServerWorld world, boolean spawnsMonsters, boolean spawnsAnimals, CallbackInfoReturnable<Integer> cir) {
		if (!SpectreConfig.getInstance().allowInsomnia) {
			cir.setReturnValue(0);
		} else {
			cir.setReturnValue(setCustomSpawningConditions(world));
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