package me.lemo.woah_coding.registry;

import me.lemo.woah_coding.WoahCoding;
import me.lemo.woah_coding.entity.BrainyGroblinEntity;
import me.lemo.woah_coding.entity.ai.brain.sensor.GroblinSpecificSensor;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class WoahCodingMemoryModuleTypes<U> {
    public static final MemoryModuleType<PlayerEntity> NEAREST_TARGETABLE_PLAYER_HOLDING_EMERALD = register("nearest_targetable_player_holding_emerald");
    public static final MemoryModuleType<List<BrainyGroblinEntity>> NEAREST_GROBLINS = register("nearest_groblins");
    public static final MemoryModuleType<List<BrainyGroblinEntity>> NEARBY_GROBLINS = register("nearby_groblins");

    private static <U> MemoryModuleType<U> register(String id) {
        return Registry.register(Registries.MEMORY_MODULE_TYPE, WoahCoding.id(id), new MemoryModuleType<>(Optional.empty()));
    }

    public static void registerMemoryModuleTypes(){

    }
}
