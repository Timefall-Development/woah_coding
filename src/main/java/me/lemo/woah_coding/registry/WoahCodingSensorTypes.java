package me.lemo.woah_coding.registry;

import me.lemo.woah_coding.WoahCoding;
import me.lemo.woah_coding.entity.ai.brain.sensor.GroblinSpecificSensor;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.Supplier;

public class WoahCodingSensorTypes {
    public static final SensorType<GroblinSpecificSensor> GROBLIN_SPECIFIC_SENSOR = register("groblin_specific_sensor", GroblinSpecificSensor::new);

    private static <U extends Sensor<?>> SensorType<U> register(String id, Supplier<U> factory) {
        return Registry.register(Registries.SENSOR_TYPE, WoahCoding.id(id), new SensorType<>(factory));
    }

    public static void registerSensorTypes(){

    }
}
