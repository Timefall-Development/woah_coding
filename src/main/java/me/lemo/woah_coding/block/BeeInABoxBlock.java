package me.lemo.woah_coding.block;

import me.lemo.woah_coding.block.entity.BeeInABoxBlockEntity;
import me.lemo.woah_coding.registry.WoahCodingBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BeeInABoxBlock extends Block implements BlockEntityProvider {
    public BeeInABoxBlock(Settings settings) {
        super(settings);
    }

    //@Override
    //protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
    //}

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient()){
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof BeeInABoxBlockEntity beeInABoxBlockEntity
                    && (player.getMainHandStack().isIn(ItemTags.FLOWERS)
                    || player.getOffHandStack().isIn(ItemTags.FLOWERS))){
                beeInABoxBlockEntity.incrementPopCounter2();
                player.sendMessage(Text.literal("Pop Counter: " + beeInABoxBlockEntity.getPopCounter2()), false);
            }
        }
        return ActionResult.SUCCESS;
    }
    // if the block is a BIAB entity and (the player has a flower in their main hand or the player has a flower in their offhand)
    // do stuff

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return WoahCodingBlockEntities.BEE_IN_A_BOX_BLOCK_ENTITY_BLOCK_ENTITY_TYPE.instantiate(pos,state);
    }
}
