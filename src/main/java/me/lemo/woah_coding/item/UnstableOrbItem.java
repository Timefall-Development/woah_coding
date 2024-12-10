package me.lemo.woah_coding.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

public class UnstableOrbItem extends Item {
    public UnstableOrbItem(Settings settings) {
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
            // Getting a location to teleport the player to
            if (targetWorld != null) {
                BlockPos playerStartPos = playerEntity.getBlockPos();
                BlockPos playerTargetPos;

                // The math used for teleporting the player through dimensions
                if (currentWorldKey == World.NETHER) {
                    playerTargetPos = new BlockPos(playerStartPos.getX() * 8, playerStartPos.getY(), playerStartPos.getZ() * 8);
                } else {
                    playerTargetPos = new BlockPos(playerStartPos.getX() / 8, playerStartPos.getY(), playerStartPos.getZ() / 8);
                }

                serverPlayerEntity.teleportTo(
                        new TeleportTarget(
                                targetWorld,
                                new Vec3d(
                                        playerTargetPos.getX(),
                                        playerTargetPos.getY(),
                                        playerTargetPos.getZ()
                                ),
                                Vec3d.ZERO,
                                serverPlayerEntity.getYaw(),
                                serverPlayerEntity.getPitch(),
                                TeleportTarget.ADD_PORTAL_CHUNK_TICKET
                        )
                );
            }
        }
        // chat gpt helped find where the return statement went (i was 1 line off D: )
        return super.use(world, playerEntity, hand);
    }
}
