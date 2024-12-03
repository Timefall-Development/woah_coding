package me.lemo.woah_coding.datagen.providers;

import me.lemo.woah_coding.registry.WoahCodingItems;
import me.lemo.woah_coding.registry.tag.WoahCodingTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class WoahCodingItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public WoahCodingItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(WoahCodingTags.Items.CLOUD_BOTTLES)
                .setReplace(false)
                .add(WoahCodingItems.BLUE_CLOUD_BOTTLE_ITEM)
                .add(WoahCodingItems.LAVENDER_CLOUD_BOTTLE_ITEM)
                .add(WoahCodingItems.LILAC_CLOUD_BOTTLE_ITEM)
                .add(WoahCodingItems.PINK_CLOUD_BOTTLE_ITEM)
                .add(WoahCodingItems.PURPLE_CLOUD_BOTTLE_ITEM);

        getOrCreateTagBuilder(WoahCodingTags.Items.VANILLA_BOOTS)
                .setReplace(false)
                .add(Items.LEATHER_BOOTS)
                .add(Items.CHAINMAIL_BOOTS)
                .add(Items.GOLDEN_BOOTS)
                .add(Items.IRON_BOOTS)
                .add(Items.DIAMOND_BOOTS)
                .add(Items.NETHERITE_BOOTS);
    }
}
