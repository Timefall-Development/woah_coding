package me.lemo.woah_coding.datagen;

import me.lemo.woah_coding.datagen.providers.WoahCodingItemTagProvider;
import me.lemo.woah_coding.datagen.providers.WoahCodingModelProvider;
import me.lemo.woah_coding.datagen.providers.WoahCodingRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class WoahCodingDataGenerator  implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(WoahCodingRecipeProvider::new);
        pack.addProvider(WoahCodingItemTagProvider::new);
        pack.addProvider(WoahCodingModelProvider::new);

    }
}
