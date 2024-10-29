package me.lemo.woah_coding.block.entity;

import me.lemo.woah_coding.WoahCoding;
import me.lemo.woah_coding.registry.WoahCodingBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public class BeeInABoxBlockEntity extends BlockEntity {
    private int popCounter2;

    public BeeInABoxBlockEntity(BlockPos pos, BlockState state) {
        super(WoahCodingBlockEntities.BEE_IN_A_BOX_BLOCK_ENTITY_BLOCK_ENTITY_TYPE, pos, state);
    }

    public int getPopCounter2() {
        return popCounter2;
    }
    public void incrementPopCounter2(){
        this.popCounter2++;
        markDirty();

        int woahCoding$RandomInt = Random.create().nextInt(10);
        if ( this.getWorld() instanceof ServerWorld serverWorld && this.popCounter2 % woahCoding$RandomInt == 0){
            EntityType.BEE.spawn(serverWorld, this.getPos().up(), SpawnReason.TRIGGERED);
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        var woahCoding$Data = new NbtCompound();
        woahCoding$Data.putInt("popCounter2", this.popCounter2);
        nbt.put(WoahCoding.MOD_ID, woahCoding$Data);
        super.writeNbt(nbt,registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        if (nbt.contains(WoahCoding.MOD_ID, NbtElement.COMPOUND_TYPE)){
            var woahCoding$Data = nbt.getCompound(WoahCoding.MOD_ID);
            this.popCounter2 = woahCoding$Data.contains("popCounter2", NbtElement.INT_TYPE)
                    ? woahCoding$Data.getInt("popCounter2")
                    : 0;
        }
    }
}
