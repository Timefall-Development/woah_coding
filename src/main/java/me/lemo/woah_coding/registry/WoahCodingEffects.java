package me.lemo.woah_coding.registry;

import me.lemo.woah_coding.WoahCoding;
import me.lemo.woah_coding.effect.HighGravityEffect;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;

public class WoahCodingEffects {
    public static final RegistryEntry<StatusEffect> HIGH_GRAVITY_EFFECT = statusEffectRegister(
            "high_gravity_effect",
            new HighGravityEffect(
                    StatusEffectCategory.HARMFUL,
                    Colors.YELLOW
            ).addAttributeModifier(
                    EntityAttributes.GRAVITY,
                    Identifier.of(WoahCoding.MOD_ID, "high_gravity_effect_gravity"),
                    1.20f,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            ).addAttributeModifier(
                    EntityAttributes.STEP_HEIGHT,
                    Identifier.of(WoahCoding.MOD_ID, "high_gravity_effect_step_height"),
                    -0.3f,
                    EntityAttributeModifier.Operation.ADD_VALUE
            )
    );
    private static RegistryEntry<StatusEffect> statusEffectRegister(String name, StatusEffect status_effect){
        return Registry.registerReference(Registries.STATUS_EFFECT, WoahCoding.id(name), status_effect);
    }
    public static void register(){

    }
}