package me.lemo.woah_coding.block;

import me.lemo.woah_coding.block.entity.CreeperInABoxBlockEntity;
import me.lemo.woah_coding.registry.WoahCodingBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CreeperInABoxBlock extends Block implements BlockEntityProvider {
    public CreeperInABoxBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient()){
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CreeperInABoxBlockEntity creeperInABoxBlockEntity){
                creeperInABoxBlockEntity.incrementPopCounter();
                player.sendMessage(Text.literal("Pop Counter: " + creeperInABoxBlockEntity.getPopCounter()));
            }
        }
        return ActionResult.success(world.isClient());
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return WoahCodingBlockEntities.CREEPER_IN_A_BOX_BLOCK_ENTITY_BLOCK_ENTITY_TYPE.instantiate(pos,state);
    }
}
