package me.lemo.woah_coding.registry;

import me.lemo.woah_coding.event.AttackWithBlazeRodEvent;
import me.lemo.woah_coding.event.RidePigWithWarpedFungusEvent;

public class WoahCodingEvents {
    public static void addListeners() {

        AttackWithBlazeRodEvent.registerEvent();
        RidePigWithWarpedFungusEvent.registerEvent();
    }
}
