package me.lemo.woah_coding.registry;

import me.lemo.woah_coding.WoahCoding;
import me.lemo.woah_coding.effect.IronMaidenEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Colors;

public class WoahCodingEffects {
    public static final RegistryEntry<StatusEffect> IRON_MAIDEN_EFFECT = effectRegister(
            "iron_maiden_effect",
            new IronMaidenEffect(
                    StatusEffectCategory.HARMFUL,
                    Colors.RED
            )
    );

    @SuppressWarnings("SameParameterValue")
    private static RegistryEntry<StatusEffect> effectRegister(String name, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, WoahCoding.id(name), statusEffect);
    }

    public static void register() {

    }
}
