package me.lemo.woah_coding.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class FadingCloudBlock extends Block {
    /*
    Notæ:
        X Age: How Old the block is
        - FrostedIceBlock#canMelt: Reference to how to check if the block can fade
        - IceBlock#melt: Reference to how to fade the block
        - IncreaseAge: Reference to how to increase the age
    */

    public static IntProperty AGE = Properties.AGE_4;
    int minFade = 5 /*20*/;
    int maxFade = 10 /*40*/;


    public FadingCloudBlock(Settings settings) {
        super(settings);
    }

    public static BlockState getUncloudedState() {
        return Blocks.AIR.getDefaultState();
    }

    @Override
    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        world.scheduleBlockTick(pos, this, MathHelper.nextInt(world.getRandom(), minFade, maxFade));
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.fade(world, pos);
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (
                (
                        random.nextInt(3) == 0
                        || canFade(world, pos, 4)
                ) && !increaseAge(state, world, pos)) {
            BlockPos.Mutable mutableBlockPosition = new BlockPos.Mutable();
            for (Direction direction : Direction.values()) {
                mutableBlockPosition.set(pos, direction);
                BlockState blockState = world.getBlockState(mutableBlockPosition);
                if (!blockState.isOf(this) || this.increaseAge(blockState, world, mutableBlockPosition)) continue;
                world.scheduleBlockTick(mutableBlockPosition, this, MathHelper.nextInt(random, minFade, maxFade));
            }
            return;
        }
        world.scheduleBlockTick(pos, this, MathHelper.nextInt(random, minFade, maxFade));
    }

    private boolean increaseAge(BlockState state, World world, BlockPos pos) {
        int ageState = state.get(AGE);
        if (ageState < 4) {
            world.setBlockState(pos, state.with(AGE, ageState + 1), Block.NOTIFY_LISTENERS);
            return false;
        }
        this.fade(world, pos);
        return true;
    }

    private boolean canFade(BlockView world, BlockPos pos, int maxNeighbors) {
        int i = 0;
        BlockPos.Mutable mutableBlockPosition = new BlockPos.Mutable();
        for (Direction direction : Direction.values()) {
            mutableBlockPosition.set(pos).move(direction);
            if (!world.getBlockState(mutableBlockPosition).isOf(this) || ++i < maxNeighbors) continue;
            return false;
        }
        return true;
    }

    protected void fade(World world, BlockPos pos) {
        world.setBlockState(pos, getUncloudedState());
        world.updateNeighbor(pos, getUncloudedState().getBlock(), pos);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}
