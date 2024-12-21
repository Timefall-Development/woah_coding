package me.lemo.woah_coding.datagen.providers;

import me.lemo.woah_coding.WoahCoding;
import me.lemo.woah_coding.block.FadingCloudBlock;
import me.lemo.woah_coding.registry.WoahCodingBlocks;
import me.lemo.woah_coding.registry.WoahCodingItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;

public class WoahCodingModelProvider extends FabricModelProvider {
    public WoahCodingModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        registerInABoxBlock(blockStateModelGenerator, WoahCodingBlocks.BEE_IN_A_BOX_BLOCK);
        registerInABoxBlock(blockStateModelGenerator, WoahCodingBlocks.CREEPER_IN_A_BOX_BLOCK);
        registerAgingBlock(blockStateModelGenerator, WoahCodingBlocks.FADING_CLOUD, FadingCloudBlock.AGE);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(WoahCodingItems.BLUE_CLOUD_BOTTLE_ITEM, Models.GENERATED);
        itemModelGenerator.register(WoahCodingItems.CLOUD_WALKER_ARMOR_TRIM_SMITHING_TEMPLATE, Models.GENERATED);
        itemModelGenerator.register(WoahCodingItems.FADING_CLOUD_ITEM, Models.GENERATED);
        itemModelGenerator.register(WoahCodingItems.INTERDIMENSIONAL_ORB_ITEM, Models.GENERATED);
        itemModelGenerator.register(WoahCodingItems.UNSTABLE_ORB_ITEM, Models.GENERATED);
        itemModelGenerator.register(WoahCodingItems.LAVENDER_CLOUD_BOTTLE_ITEM, Models.GENERATED);
        itemModelGenerator.register(WoahCodingItems.LILAC_CLOUD_BOTTLE_ITEM, Models.GENERATED);
        itemModelGenerator.register(WoahCodingItems.OUTPUT_ITEM, Models.GENERATED);
        itemModelGenerator.register(WoahCodingItems.PINK_CLOUD_BOTTLE_ITEM, Models.GENERATED);
        itemModelGenerator.register(WoahCodingItems.PURPLE_CLOUD_BOTTLE_ITEM, Models.GENERATED);
        itemModelGenerator.register(WoahCodingItems.COPPER_TOKEN_ITEM, Models.GENERATED);
    }

    /**
     * Registers a block model for the in a box type item.
     * This method is used to generate models for blocks that have a specific texture for each side.
     *
     * @param blockStateModelGenerator The generator used to create block state models.
     * @param block The block to register the model for.
     */
    private void registerInABoxBlock(BlockStateModelGenerator blockStateModelGenerator, Block block) {
        // Get the identifiers for the bottom, front, side, and top textures of the block.
        Identifier identifierBottom = WoahCoding.id("block/creeper_in_a_box_bottom");
        Identifier identifierFront = TextureMap.getSubId(block, "_front");
        Identifier identifierSide = WoahCoding.id("block/creeper_in_a_box_side");
        Identifier identifierTop = WoahCoding.id("block/creeper_in_a_box_top");

        // Create a texture map that maps each side of the block to its corresponding texture identifier from above.
        // This texture map is used to upload the block model to the game.
        TextureMap textureMap = new TextureMap()
                .put(TextureKey.BOTTOM, identifierBottom)   // Map the bottom side to the bottom texture.
                .put(TextureKey.FRONT, identifierFront)     // Map the front side to the front texture.
                .put(TextureKey.SIDE, identifierSide)       // Map the side to the side texture.
                .put(TextureKey.TOP, identifierTop);        // Map the top side to the top texture.

        // Pass the block model to the game using the texture map and the block state model generator.
        // The uploaded model is assigned an identifier that can be used to reference it later.
        Identifier identifier = Models.ORIENTABLE_WITH_BOTTOM.upload(
                block,                                      // The block to upload the model for.
                "",                                         // The model's namespace (empty in this case).
                textureMap,                                 // The texture map to use for the model.
                blockStateModelGenerator.modelCollector);   // The collector used to store the uploaded model.

        // Create a singleton block state for the block and add it to the block state collector.
        // A singleton block state is a block state that has only one possible state (i.e., it's not a block that can be rotated or have different variants).
        blockStateModelGenerator.blockStateCollector.accept(
                BlockStateModelGenerator.createSingletonBlockState(
                        block,                              // The block to create the block state for.
                        ModelIds.getBlockModelId(block)));  // The identifier of the uploaded model.

        // Register a parented item model for the block's item form.
        // A parented item model is a model that is used to render an item in the game world.
        // The model is "parented" to the block's model, meaning that it uses the same texture and geometry as the block model.
        blockStateModelGenerator.registerParentedItemModel(
                block.asItem(), // This is used to get an item from the block.
                identifier);    // The identifier that we defined earlier of the uploaded model.
    }

    private void registerFadingCloud(BlockStateModelGenerator blockStateModelGenerator, IntProperty ageProperty) {
        Block block = WoahCodingBlocks.FADING_CLOUD;
        blockStateModelGenerator.blockStateCollector
                .accept(
                        VariantsBlockStateSupplier.create(block)
                                .coordinate(
                                        BlockStateVariantMap.create(ageProperty)
                                                .register(0, BlockStateVariant.create().put(
                                                        VariantSettings.MODEL,
                                                        blockStateModelGenerator.createSubModel(block, "_0", Models.CUBE_ALL, TextureMap::all)))
                                                .register(1, BlockStateVariant.create().put(
                                                        VariantSettings.MODEL,
                                                        blockStateModelGenerator.createSubModel(block, "_1", Models.CUBE_ALL, TextureMap::all)))
                                                .register(2, BlockStateVariant.create().put(
                                                        VariantSettings.MODEL,
                                                        blockStateModelGenerator.createSubModel(block, "_2", Models.CUBE_ALL, TextureMap::all)))
                                                .register(3, BlockStateVariant.create().put(
                                                        VariantSettings.MODEL,
                                                        blockStateModelGenerator.createSubModel(block, "_3", Models.CUBE_ALL, TextureMap::all)))
                                                .register(4, BlockStateVariant.create().put(
                                                        VariantSettings.MODEL,
                                                        blockStateModelGenerator.createSubModel(block, "_4", Models.CUBE_ALL, TextureMap::all)))
                                )
                );
    }

    private void registerAgingBlock(BlockStateModelGenerator blockStateModelGenerator, Block block, IntProperty ageProperty) {
        int maxAge = ageProperty.getValues().size() - 1;
        BlockStateVariantMap.SingleProperty<Integer> variantMap = BlockStateVariantMap.create(ageProperty);

        for (int age = 0; age <= maxAge; age++) {
            variantMap.register(age, BlockStateVariant.create().put(
                    VariantSettings.MODEL,
                    blockStateModelGenerator.createSubModel(
                            block,
                            "_" + age,
                            Models.CUBE_ALL,
                            TextureMap::all)));
        }
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(variantMap));
    }

}
