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
    public static final RegistryEntry<Potion> HIGH_GRAVITY_POTION = potionRegister(
            "high_gravity_potion",
            new Potion(
                    "high_gravity_potion",
                    new StatusEffectInstance(
                            WoahCodingEffects.HIGH_GRAVITY_EFFECT,
                            3600
                    )
            )
    );
    public static final RegistryEntry<Potion> LONG_HIGH_GRAVITY_POTION = potionRegister(
            "long_high_gravity_potion",
            new Potion(
                    "high_gravity_potion",
                    new StatusEffectInstance(
                            WoahCodingEffects.HIGH_GRAVITY_EFFECT,
                            9600
                    )
            )
    );
    public static final RegistryEntry<Potion> STRONG_HIGH_GRAVITY_POTION = potionRegister(
            "strong_high_gravity_potion",
            new Potion(
                    "high_gravity_potion",
                    new StatusEffectInstance(
                            WoahCodingEffects.HIGH_GRAVITY_EFFECT,
                            1800, 1
                    )
            )
    );
    private static RegistryEntry<Potion> potionRegister(String name, Potion potion){
        return Registry.registerReference(Registries.POTION, WoahCoding.id(name), potion);
    }
    public static void register(){
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
            builder.registerPotionRecipe(Potions.THICK, Items.ANVIL, HIGH_GRAVITY_POTION);
            builder.registerPotionRecipe(HIGH_GRAVITY_POTION, Items.GLOWSTONE_DUST, STRONG_HIGH_GRAVITY_POTION);
            builder.registerPotionRecipe(HIGH_GRAVITY_POTION, Items.REDSTONE, LONG_HIGH_GRAVITY_POTION);

        });
    }
}