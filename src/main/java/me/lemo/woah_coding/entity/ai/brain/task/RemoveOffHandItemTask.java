package me.lemo.woah_coding.entity.ai.brain.task;

import me.lemo.woah_coding.entity.BrainyGroblinEntity;
import me.lemo.woah_coding.entity.mob.GroblinBrain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;
import net.minecraft.item.Items;

public class RemoveOffHandItemTask {
    public static Task<BrainyGroblinEntity> create() {
        return TaskTriggerer.task(
                context -> context.group(context.queryMemoryAbsent(MemoryModuleType.ADMIRING_ITEM)).apply(context, admiringItem -> (world, entity, time) -> {
                    if (!entity.getOffHandStack().isEmpty() && !entity.getOffHandStack().isOf(Items.SHIELD)) {
                        GroblinBrain.consumeOffHandItem(world, entity, true);
                        return true;
                    } else {
                        return false;
                    }
                })
        );
    }
}

