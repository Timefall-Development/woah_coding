package me.lemo.woah_coding.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.world.ServerWorld;

public class IronMaidenEffect extends StatusEffect {
    public IronMaidenEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        if (entity.getHealth() > 0){
            // Whilst inside an iron maiden, the individual would have their movements restricted, and they'd be blinded.
            //  As such, we apply status effects that would simulate that experience.
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE));

            // We get the absolute values of the entity's movement speed so that we don't have to account for negative
            //  numbers in our maths
            double entityMovementSpeedX = Math.abs(entity.getMovement().getX());
            double entityMovementSpeedZ = Math.abs(entity.getMovement().getZ());

            // We get the average movement speed of the entity
            double entityMovementSpeedAverage = (entityMovementSpeedX + entityMovementSpeedZ) / 2.0D;

            // We apply the damage to the entity based on the average movement speed
            if (entityMovementSpeedAverage > 0) {
                // If the player is moving in any direction, they take damage equal to their average movement speed times 10.
                // The reason for the 10x multiplier is that the movement speed of a vanilla player, without any modifiers
                //  is in values of 0.0X or 0.00X, so we need to use the multiplier to get to a value that is at least 1.
                //  In short: the more the player moves, the more damage they take.
                entity.damage(world, entity.getDamageSources().cactus(), (float) entityMovementSpeedAverage * 10.0F);
            } else if (world.getTime() % 100 == 0) {
                // If the player is not moving, they take damage every 100 ticks or 5 seconds
                entity.damage(world, entity.getDamageSources().cactus(), 1.0f);
            }
        }
        return super.applyUpdateEffect(world, entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
