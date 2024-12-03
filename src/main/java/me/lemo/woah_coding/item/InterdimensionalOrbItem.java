package me.lemo.woah_coding.item;

import me.lemo.woah_coding.WoahCoding;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

public class InterdimensionalOrbItem extends Item {
    public InterdimensionalOrbItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity playerEntity, Hand hand) {
        if (!playerEntity.getWorld().isClient() && playerEntity instanceof ServerPlayerEntity serverPlayerEntity) {
            MinecraftServer server = playerEntity.getServer();
            if (server == null) return super.use(world, playerEntity, hand);
            ServerWorld targetWorld;

            RegistryKey<World> currentWorldKey = playerEntity.getWorld().getRegistryKey();
            RegistryKey<World> targetWorldKey = currentWorldKey == World.NETHER
                    ? World.OVERWORLD
                    : World.NETHER;

            targetWorld = server.getWorld(targetWorldKey);
            //getting a location to teleport the player to
            if (targetWorld != null){
                BlockPos playerStartPos = playerEntity.getBlockPos();
                BlockPos playerTargetPos;

                //the math used for teleporting the player through dimensions
                if (currentWorldKey == World.NETHER){
                    playerTargetPos = new BlockPos(playerStartPos.getX() * 8, playerStartPos.getY(), playerStartPos.getZ() * 8);

                } else {
                    playerTargetPos = new BlockPos(playerStartPos.getX() / 8, playerStartPos.getY(), playerStartPos.getZ() / 8);
                }

                //finding a safe location to teleport the player to using the location data above
                BlockPos safePlayerTargetPos = findSafeTargetPos(targetWorld, playerTargetPos);
                if (safePlayerTargetPos == null) {
                    System.out.println(WoahCoding.MOD_ID + ": No safe location was found to teleport " + playerEntity.getName() + ".");
                    serverPlayerEntity.getWorld().playSound(null, serverPlayerEntity.getBlockPos(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.MASTER, 1.0F, 1.0F);
                } else{
                    serverPlayerEntity.teleportTo(
                            new TeleportTarget(
                                    targetWorld,
                                    new Vec3d(
                                            safePlayerTargetPos.getX(),
                                            safePlayerTargetPos.getY(),
                                            safePlayerTargetPos.getZ()),
                                    Vec3d.ZERO,
                                    serverPlayerEntity.getYaw(),
                                    serverPlayerEntity.getPitch(),
                                    TeleportTarget.ADD_PORTAL_CHUNK_TICKET


                            )
                    );
                    serverPlayerEntity.getItemCooldownManager().set(this.getDefaultStack(), 20);
                }
            }
            return super.use(world, playerEntity, hand);

        }
        return super.use(world, playerEntity, hand);
    }

    private BlockPos findSafeTargetPos(ServerWorld serverWorld, BlockPos originalPos){
        if (isSafeLocation(serverWorld, originalPos)){
            return originalPos;
        }

        final int horizontalRadius = 4;
        final int verticalRadius = 4;

        for (int yOffset = 0; yOffset <= verticalRadius; yOffset++){
            for (int xOffset = -horizontalRadius; xOffset <= horizontalRadius; xOffset++){
                for (int zOffset = -horizontalRadius; zOffset <= horizontalRadius; zOffset++) {
                    BlockPos.Mutable checkPos = originalPos.mutableCopy().move(xOffset, yOffset, zOffset);
                    if (isSafeLocation(serverWorld, checkPos))
                        return checkPos;


                    if (yOffset != 0) {
                        checkPos =  originalPos.mutableCopy().move(xOffset, -yOffset, zOffset);
                        if (isSafeLocation(serverWorld, checkPos))
                            return checkPos;

                    }
                }
            }
        }
        return null;
    }

    private boolean isSafeLocation(ServerWorld serverWorld, BlockPos blockPos) {
        BlockState blockBelow = serverWorld.getBlockState(blockPos.down());
        BlockState blockAt = serverWorld.getBlockState(blockPos);
        BlockState blockAbove = serverWorld.getBlockState(blockPos.up());
        BlockState blockMoreAbove = serverWorld.getBlockState(blockPos.up(1));

        boolean isBlockBelowSafe = blockBelow.isSolidBlock(serverWorld, blockPos.down());
        boolean isBlockAtSafe = blockAt.isAir();
        boolean isBlockAboveSafe = blockAbove.isAir() && blockMoreAbove.isAir();

        boolean isNotLiquid = !blockBelow.isOf(Blocks.LAVA)
                && !blockBelow.isOf(Blocks.WATER)
                && !blockAt.isOf(Blocks.LAVA)
                && !blockAt.isOf(Blocks.WATER)
                && !blockAbove.isOf(Blocks.LAVA)
                && !blockAbove.isOf(Blocks.WATER)
                && !blockMoreAbove.isOf(Blocks.LAVA)
                && !blockMoreAbove.isOf(Blocks.WATER);

        boolean isNotVoid = !blockBelow.isOf(Blocks.VOID_AIR)
                && !blockAt.isOf(Blocks.VOID_AIR)
                && !blockAbove.isOf(Blocks.VOID_AIR)
                && !blockMoreAbove.isOf(Blocks.VOID_AIR);

        boolean isSafeHeight = serverWorld.getRegistryKey() == World.NETHER ? blockPos.getY() < 120 : blockPos.getY() > -64;

        return isBlockBelowSafe
                && isBlockAtSafe
                && isBlockAboveSafe
                && isNotLiquid
                && isNotVoid
                && isSafeHeight;
    }
}

/*TODO
    Teleport player to nether when in overworld and vice versa ✓
        - player pos in current dimension
        - get target pos
    make sure they dont spawn in a wall or in lava or above the roof ✓
        - avoid lava beneath you, air beneath you, or the position shared with a block
        - get y coordinate of player
    add cooldown ✓
        -????
    add durability ✓
 */