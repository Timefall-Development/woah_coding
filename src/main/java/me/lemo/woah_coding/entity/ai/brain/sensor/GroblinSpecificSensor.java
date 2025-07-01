package me.lemo.woah_coding.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableSet;
import me.lemo.woah_coding.registry.WoahCodingMemoryModuleTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.LivingTargetCache;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;
import java.util.Set;

public class GroblinSpecificSensor extends Sensor<LivingEntity> {
    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return ImmutableSet.of(
                MemoryModuleType.VISIBLE_MOBS,
                MemoryModuleType.MOBS,
                MemoryModuleType.NEAREST_VISIBLE_NEMESIS,
                WoahCodingMemoryModuleTypes.NEAREST_TARGETABLE_PLAYER_HOLDING_EMERALD,
                MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM//,
                //MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_CREEPER,
                //MemoryModuleType.NEARBY_BRAINY_GROBLINS,
                //MemoryModuleType.VISIBLE_BRAINY_GROBLIN_COUNT,
                //MemoryModuleType.VISIBLE_CREEPER_COUNT,
                //MemoryModuleType.NEAREST_HATED_BLOCK
        );
    }

    @Override
    protected void sense(ServerWorld world, LivingEntity entity) {
        Brain<?> brain = entity.getBrain();
        brain.remember(MemoryModuleType.NEAREST_REPELLENT, findGroblinRepellent(world, entity));

        LivingTargetCache livingTargetCache = brain.getOptionalRegisteredMemory(MemoryModuleType.VISIBLE_MOBS).orElse(LivingTargetCache.empty());

        Optional<PlayerEntity> optionalTargetablePlayer = Optional.empty();
        Optional<PlayerEntity> optionalPotentialBarteringPlayer = Optional.empty();
        int creeperCount = 0;

        /*
        for (LivingEntity livingEntity : livingTargetCache.iterate(livingEntityx -> true)) {
            if (livingEntity instanceof PlayerEntity playerEntity) {
                if (optionalTargetablePlayer.isEmpty() && GroblinBrain.isHoldingEmerald(playerEntity) && entity.canTarget(livingEntity)) {
                    System.out.println(
                            "Found targetable player! It is\n"
                                    + playerEntity.getName().getString()
                                    + "\nand they're hold a/an\n"
                                    + playerEntity.getMainHandStack().getItem().getName().getString()
                                    + "\nin their main hand and a/an\n"
                                    + playerEntity.getOffHandStack().getItem().getName().getString()
                                    + "\nin their offhand!\n\n"
                                    + "It is\n"
                                    + GroblinBrain.isHoldingEmerald(playerEntity)
                                    + "\nthat\n"
                                    + playerEntity.getName().getString()
                                    + "\nis holding an Emerald! Get 'em!");
                    optionalTargetablePlayer = Optional.of(playerEntity);
                }

                if (optionalPotentialBarteringPlayer.isEmpty() && !playerEntity.isSpectator() && GroblinBrain.isCopperTokenHoldingPlayer(playerEntity)) {
                    System.out.println("Found barterable player! It is " + playerEntity.getName().getString() + "!");
                    optionalPotentialBarteringPlayer = Optional.of(playerEntity);
                }
            }
        }

         */

        brain.remember(WoahCodingMemoryModuleTypes.NEAREST_TARGETABLE_PLAYER_HOLDING_EMERALD, optionalTargetablePlayer);
        brain.remember(MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM, optionalPotentialBarteringPlayer);
        //brain.remember(WoahCodingMemoryModuleTypes.VISIBLE_CREEPER_COUNT, creeperCount);
    }

    private static Optional<BlockPos> findGroblinRepellent(ServerWorld world, LivingEntity entity) {
        return BlockPos.findClosest(entity.getBlockPos(), 8, 4, pos -> isGroblinRepellent(world, pos));
    }

    private static boolean isGroblinRepellent(ServerWorld world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        return blockState.isOf(Blocks.SOUL_CAMPFIRE)
                || blockState.isOf(Blocks.CAMPFIRE)
                || blockState.isOf(Blocks.EMERALD_BLOCK)
                || blockState.isOf(Blocks.EMERALD_ORE)
                || blockState.isOf(Blocks.DEEPSLATE_EMERALD_ORE);
    }
}
