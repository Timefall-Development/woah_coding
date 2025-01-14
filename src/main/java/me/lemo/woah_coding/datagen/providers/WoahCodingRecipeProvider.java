package me.lemo.woah_coding.datagen.providers;

import com.google.common.collect.ImmutableList;
import me.lemo.woah_coding.registry.WoahCodingItems;
import me.lemo.woah_coding.registry.tag.WoahCodingTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.criterion.ChangedDimensionCriterion;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeGenerator;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class WoahCodingRecipeProvider extends FabricRecipeProvider {
    public WoahCodingRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
              createShapeless(RecipeCategory.MISC, WoahCodingItems.FADING_CLOUD_ITEM, 1)
                      .input(WoahCodingItems.PINK_CLOUD_BOTTLE_ITEM)
                      .input(WoahCodingItems.LILAC_CLOUD_BOTTLE_ITEM)
                      .input(WoahCodingItems.LAVENDER_CLOUD_BOTTLE_ITEM)
                      .input(WoahCodingItems.PURPLE_CLOUD_BOTTLE_ITEM)
                      .input(WoahCodingItems.BLUE_CLOUD_BOTTLE_ITEM)
                      .criterion(hasItem(WoahCodingItems.PINK_CLOUD_BOTTLE_ITEM), conditionsFromItem(WoahCodingItems.PINK_CLOUD_BOTTLE_ITEM))
                      .criterion(hasItem(WoahCodingItems.LILAC_CLOUD_BOTTLE_ITEM), conditionsFromItem(WoahCodingItems.LILAC_CLOUD_BOTTLE_ITEM))
                      .criterion(hasItem(WoahCodingItems.LAVENDER_CLOUD_BOTTLE_ITEM), conditionsFromItem(WoahCodingItems.LAVENDER_CLOUD_BOTTLE_ITEM))
                      .criterion(hasItem(WoahCodingItems.PURPLE_CLOUD_BOTTLE_ITEM), conditionsFromItem(WoahCodingItems.PURPLE_CLOUD_BOTTLE_ITEM))
                      .criterion(hasItem(WoahCodingItems.BLUE_CLOUD_BOTTLE_ITEM), conditionsFromItem(WoahCodingItems.BLUE_CLOUD_BOTTLE_ITEM))
                      .offerTo(exporter);

              createShaped(RecipeCategory.TRANSPORTATION, WoahCodingItems.INTERDIMENSIONAL_ORB_ITEM, 1)
                      .pattern(" O ")
                      .pattern("OEO")
                      .pattern(" O ")
                      .input('O', Items.OBSIDIAN)
                      .input('E', Items.ENDER_PEARL)
                      .criterion(hasItem(Items.ENDER_PEARL), conditionsFromItem(Items.ENDER_PEARL))
                      .criterion("entered_nether", ChangedDimensionCriterion.Conditions.to(World.NETHER))
                      .offerTo(exporter);

                createShaped(RecipeCategory.MISC, WoahCodingItems.CLOUD_WALKER_ARMOR_TRIM_SMITHING_TEMPLATE, 1)
                        .pattern("#C#")
                        .pattern("#D#")
                        .pattern("###")
                        .input('D', Items.DIAMOND)
                        .input('C', WoahCodingItems.FADING_CLOUD_ITEM)
                        .input('#', WoahCodingTags.Items.CLOUD_BOTTLES)
                        .criterion(hasItem(WoahCodingItems.CLOUD_WALKER_ARMOR_TRIM_SMITHING_TEMPLATE), conditionsFromItem(WoahCodingItems.CLOUD_WALKER_ARMOR_TRIM_SMITHING_TEMPLATE))
                        .offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, Identifier.ofVanilla(getItemPath(WoahCodingItems.CLOUD_WALKER_ARMOR_TRIM_SMITHING_TEMPLATE) + "_duplicate")));

                offerSmithingTemplateCopyingRecipe(WoahCodingItems.CLOUD_WALKER_ARMOR_TRIM_SMITHING_TEMPLATE, ingredientFromTag(WoahCodingTags.Items.CLOUD_BOTTLES));

                offerSmithingTrimRecipe(WoahCodingItems.CLOUD_WALKER_ARMOR_TRIM_SMITHING_TEMPLATE, RegistryKey.of(RegistryKeys.RECIPE, Identifier.ofVanilla(getItemPath(WoahCodingItems.CLOUD_WALKER_ARMOR_TRIM_SMITHING_TEMPLATE) + "_smithing_trim")));
            }
        };
    }

    @Override
    public String getName() {
        return "Woah Coding Recipes";
    }
}
