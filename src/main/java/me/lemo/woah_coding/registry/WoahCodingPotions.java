package me.lemo.woah_coding.registry;

import me.lemo.woah_coding.WoahCoding;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public class WoahCodingPotions {

    @SuppressWarnings("unused")
    public static final RegistryEntry<Potion> IRON_MAIDEN_POTION = potionRegister(
            "iron_maiden_potion",
            new Potion(
                    "iron_maiden_potion",
                    new StatusEffectInstance(
                            WoahCodingEffects.IRON_MAIDEN_EFFECT,
                            1200,
                            0
                    )
            )
    );

    @SuppressWarnings("SameParameterValue")
    private static RegistryEntry<Potion> potionRegister(String name, Potion potion) {
        return Registry.registerReference(Registries.POTION, WoahCoding.id(name), potion);
    }

    public static void register() {
        // Adds the recipe to the brewing stand
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
            // Adds the recipe of the Iron Maiden Potion which uses Iron Bars in the top of the brewing stand and
            //  Awkward potion(s) in the bottom
            builder.registerPotionRecipe(Potions.AWKWARD, Items.IRON_BARS, IRON_MAIDEN_POTION);
        });
    }
}
