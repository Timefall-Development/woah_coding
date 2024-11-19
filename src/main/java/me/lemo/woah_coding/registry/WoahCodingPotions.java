package me.lemo.woah_coding.registry;

import me.lemo.woah_coding.WoahCoding;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
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

    }
}
