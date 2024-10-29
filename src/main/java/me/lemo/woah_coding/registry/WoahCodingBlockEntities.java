package me.lemo.woah_coding.registry;

import me.lemo.woah_coding.WoahCoding;
import me.lemo.woah_coding.block.entity.BeeInABoxBlockEntity;
import me.lemo.woah_coding.block.entity.CreeperInABoxBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class WoahCodingBlockEntities {
    public static final BlockEntityType<CreeperInABoxBlockEntity> CREEPER_IN_A_BOX_BLOCK_ENTITY_BLOCK_ENTITY_TYPE = registerBlockEntity(
            BlockEntityType.Builder.create(CreeperInABoxBlockEntity::new, WoahCodingBlocks.CREEPER_IN_A_BOX_BLOCK).build(), "creeper_in_a_box_block_entity");

    public static final BlockEntityType<BeeInABoxBlockEntity> BEE_IN_A_BOX_BLOCK_ENTITY_BLOCK_ENTITY_TYPE = registerBlockEntity(
            BlockEntityType.Builder.create(BeeInABoxBlockEntity::new, WoahCodingBlocks.BEE_IN_A_BOX_BLOCK).build(), "bee_in_a_box_block_entity");

    protected static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(BlockEntityType<T> type, String id){
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, WoahCoding.id(id), type);
    }
    public static void register(){}
}
