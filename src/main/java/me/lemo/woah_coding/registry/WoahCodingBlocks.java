package me.lemo.woah_coding.registry;

import me.lemo.woah_coding.WoahCoding;
import me.lemo.woah_coding.block.BeeInABoxBlock;
import me.lemo.woah_coding.block.CreeperInABoxBlock;
import me.lemo.woah_coding.block.FadingCloudBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;

public class WoahCodingBlocks {
    public static final Block FADING_CLOUD = registerBlock(
            new FadingCloudBlock(AbstractBlock.Settings
                    .copy(Blocks.GLASS)
                    .ticksRandomly()
                    .sounds(BlockSoundGroup.SHROOMLIGHT)
                    .nonOpaque()
                    .registryKey(getBlockRegistryKey("fading_cloud_block"))),
            "fading_cloud_block"
    );
    public static final CreeperInABoxBlock CREEPER_IN_A_BOX_BLOCK = registerBlockWithItem(
            new CreeperInABoxBlock(AbstractBlock.Settings.copy(Blocks.BEDROCK)),"creeper_in_a_box",
            new Item.Settings()
    );
    public static final BeeInABoxBlock BEE_IN_A_BOX_BLOCK = registerBlockWithItem(
            new BeeInABoxBlock(AbstractBlock.Settings.copy(Blocks.BEDROCK)),"bee_in_a_box",
            new Item.Settings()
    );

    public static void register(){

    }

    protected static <T extends Block> T registerBlock(T block, String id){
        return Registry.register(Registries.BLOCK, WoahCoding.id(id), block);
    }

    protected static <T extends Block> T registerBlockWithItem(T block, String id){
        Item.Settings settings = new Item.Settings()
                .useBlockPrefixedTranslationKey()
                .registryKey(WoahCodingItems.getItemRegistryKey(id));

        T registeredBlock = registerBlock(block, id);
        WoahCodingItems.itemRegister(new BlockItem(registeredBlock, settings), id);
        return registeredBlock;
    }

    public static RegistryKey<Block> getBlockRegistryKey(String id){
        return RegistryKey.of(RegistryKeys.BLOCK, WoahCoding.id(id));
    }
}