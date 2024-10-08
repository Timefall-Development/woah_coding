package me.lemo.woah_coding.registry;

import me.lemo.woah_coding.WoahCoding;
import me.lemo.woah_coding.block.FadingCloudBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;

public class WoahCodingBlocks {
    public static final Block FADING_CLOUD = registerBlock(
            new FadingCloudBlock(AbstractBlock.Settings
                    .copy(Blocks.GLASS)
                    .ticksRandomly()
                    .sounds(BlockSoundGroup.SHROOMLIGHT)
                    .nonOpaque()),
            "fading_cloud_block"
    );

    public static void register(){

    }

    protected static Block registerBlock(Block block, String id){
        return Registry.register(Registries.BLOCK, WoahCoding.id(id), block);
    }
}
