package me.lemo.woah_coding.entity.mob;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import me.lemo.woah_coding.entity.BrainyGroblinEntity;
import me.lemo.woah_coding.entity.ai.brain.task.RemoveOffHandItemTask;
import me.lemo.woah_coding.entity.type.WoahCodingEntityTypes;
import me.lemo.woah_coding.loot.WoahCodingLootTables;
import me.lemo.woah_coding.registry.WoahCodingItems;
import me.lemo.woah_coding.registry.WoahCodingMemoryModuleTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.GameRules;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GroblinBrain {
    public static final Item BARTERING_ITEM = WoahCodingItems.COPPER_TOKEN_ITEM;
    private static final UniformIntProvider GO_TO_NEMESIS_MEMORY_DURATION = TimeHelper.betweenSeconds(5, 7);

    // Activity Modules
    /**
     * Creates a new brain for the given BrainyGroblinEntity.
     *
     * This method initializes the brain with the necessary activities and settings.
     *
     * @param brainyGroblin The BrainyGroblinEntity that this brain belongs to.
     * @param brain The brain to be initialized.
     * @return The initialized brain.
     */
    public static Brain<?> create(BrainyGroblinEntity brainyGroblin, Brain<BrainyGroblinEntity> brain) {
        // Add the core activities to the brain. These activities are essential for the entity's basic behavior.
        addCoreActivities(brain);

        // Add the idle activities to the brain. These activities are used when the entity is not doing anything else.
        addIdleActivities(brain);

        // Add the admire item activities to the brain. These activities are used when the entity is admiring an ite  .
        addAdmireItemActivities(brain);

        // Add the fight activities to the brain. These activities are used when the entity is fighting.
        addFightActivities(brainyGroblin, brain);

        // Add the avoid activities to the brain. These activities are used when the entity is trying to avoid something.
        addAvoidActivities(brain);

        // Set the core activities for the brain. These activities are the most important ones and should always be available.
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));

        // Set the default activity for the brain. This activity will be used when no other activity is available.
        brain.setDefaultActivity(Activity.IDLE);

        // Reset the possible activities for the brain. This ensures that the brain starts with a clean slate.
        brain.resetPossibleActivities();

        // Return the initialized brain.
        return brain;
    }

    /**
     * Adds the core activities to the brain of a BrainyGroblinEntity.
     *
     * Core activities are essential for the entity's basic behavior and are always available.
     *
     * @param brain The brain to which the core activities will be added.
     */
    private static void addCoreActivities(Brain<BrainyGroblinEntity> brain) {
        // Set the task list for the core activities.
        // The task list is a list of tasks that the entity will perform in a specific order.
        // In this case, the task list is for the Activity.CORE, which means these tasks are essential for the entity's basic behavior.
        // The second parameter, 0, I believe is the priority of the task list. If this is the case, a lower priority means the tasks
        // will be performed before tasks with a higher priority.
        brain.setTaskList(
                Activity.CORE, // The activity type for this task list.
                0, // The priority of this task list, I believe.
                ImmutableList.of( // The list of tasks to be performed.
                        // Update the look control task.
                        // This task updates the entity's look direction and pitch.
                        new UpdateLookControlTask(45, 90), // The parameters 45 and 90 are the minimum and maximum times to update where it is looking.

                        // Move to target task.
                        // This task moves the entity towards a target location.
                        new MoveToTargetTask(),

                        // Open doors task.
                        // This task opens doors in front of the entity.
                        OpenDoorsTask.create(),

                        // Go to nemesis task.
                        // This task moves the entity towards its nemesis (a target entity).
                        goToNemesisTask(),

                        // Remove off-hand item task.
                        // This task removes an item from the entity's off-hand.
                        RemoveOffHandItemTask.create(),

                        // Admire item task.
                        // This task makes the entity admire an item.
                        AdmireItemTask.create(119), // The parameter 119 is the duration of the admiration in ticks.

                        // Forget angry at target task.
                        // This task makes the entity forget its anger towards a target entity.
                        ForgetAngryAtTargetTask.create()
                )
        );
    }

    /**
     * Adds the idle activities to the brain of a BrainyGroblinEntity.
     *
     * Idle activities are tasks that the entity will perform when it is not actively engaged in other activities.
     *
     * @param brain The brain to which the idle activities will be added.
     */
    private static void addIdleActivities(Brain<BrainyGroblinEntity> brain) {
        // Set the task list for the idle activities.
        // The task list is a list of tasks that the entity will perform in a specific order.
        // In this case, the task list is for the Activity.IDLE, which means these tasks are performed when the entity is not actively engaged in other activities.
        brain.setTaskList(
                Activity.IDLE, // The activity type for this task list.
                10, // The priority of this task list. A lower priority means the tasks will be performed before tasks with a higher priority.
                ImmutableList.of( // The list of tasks to be performed.
                        /**
                         * Look at a mob task.
                         * This task makes the entity look at a mob that is holding a copper token.
                         * The GroblinBrain::isCopperTokenHoldingPlayer lambda function is used to determine if a mob is holding a copper token.
                         * The 14.0F parameter is the maximum distance at which the entity will look at the mob.
                         */
                        LookAtMobTask.create(GroblinBrain::isCopperTokenHoldingPlayer, 14.0F),

                        /**
                         * Update attack target task.
                         * This task updates the entity's attack target.
                         * The (world, target) -> target.isMobOrPlayer() lambda function is used to determine if a target is a mob or player.
                         * If the target is either, the GroblinBrain::getPreferredTarget lambda function is used to get the entity's preferred target.
                         */
                        UpdateAttackTargetTask.create((world, target) -> target.isMobOrPlayer(), GroblinBrain::getPreferredTarget),

                        // TaskTriggerer.runIf(BrainyGroblinEntity::canHunt, HuntCreeperTask.create()),
                        // This line is currently commented out, but it would trigger the HuntCreeperTask if the BrainyGroblinEntity can hunt.

                        /**
                         * Make random follow task.
                         * This task makes the entity follow a random target.
                         */
                        makeRandomFollowTask(),

                        /**
                         * Make random wander task.
                         * This task makes the entity wander randomly.
                         */
                        makeRandomWanderTask(),

                        /**
                         * Find interaction target task.
                         * This task finds an interaction target for the entity.
                         * The EntityType.PLAYER parameter specifies that the target should be a player.
                         * The 4 parameter specifies the maximum distance at which the entity will interact with the target.
                         */
                        FindInteractionTargetTask.create(EntityType.PLAYER, 4)
                )
        );
    }

    /**
     * Adds the fight activities to the brain of a BrainyGroblinEntity.
     *
     * This method is responsible for setting up the tasks that the entity will perform when it is in a fight.
     *
     * @param brainyGroblin The BrainyGroblinEntity that will be performing the fight activities.
     * @param brain The brain of the BrainyGroblinEntity, which will be used to set the task list.
     */
    private static void addFightActivities(BrainyGroblinEntity brainyGroblin, Brain<BrainyGroblinEntity> brain) {
        // Set the task list for the fight activities.
        // The task list is a list of tasks that the entity will perform in a specific order.
        // In this case, the task list is for the Activity.FIGHT, which means these tasks are performed when the entity is actively engaged in a fight.
        brain.setTaskList(
                Activity.FIGHT, // The activity type for this task list.
                10, // The priority of this task list. A lower priority means the tasks will be performed before tasks with a higher priority.
                ImmutableList.of( // The list of tasks to be performed.
                        /**
                         * Forget attack target task.
                         * This task makes the entity forget its current attack target if it is no longer the preferred target.
                         * The (world, target) -> !isPreferredAttackTarget(world, brainyGroblin, target) lambda function is used to determine if the target is no longer the preferred target.
                         */
                        ForgetAttackTargetTask.create((world, target) -> !isPreferredAttackTarget(world, brainyGroblin, target)),

                        /**
                         * Attack task.
                         * This task makes the entity attack its target if it is holding a Groblin tool.
                         * The GroblinBrain::isHoldingGroblinTool lambda function is used to determine if the entity is holding a Groblin tool.
                         * The AttackTask.create(5, 0.75F) method creates an attack task with a distance of 5 and a forward movement of 0.75F.
                         */
                        TaskTriggerer.runIf(GroblinBrain::isHoldingGroblinTool, AttackTask.create(5, 0.75F)),

                        /**
                         * Ranged approach task.
                         * This task makes the entity approach its target with a speed of 1.0F, its normal movement speed.
                         */
                        RangedApproachTask.create(1.0F),

                        /**
                         * Melee attack task.
                         * This task makes the entity perform a melee attack on its target.
                         * The MeleeAttackTask.create(20) method creates a melee attack task with a cooldown of 20 ticks between attacks.
                         */
                        MeleeAttackTask.create(20)
                ),
                MemoryModuleType.ATTACK_TARGET // The memory module type for this task list.
        );
    }

    /**
     * Adds a task list to the BrainyGroblinEntity's brain for admiring items.
     * This task list is used when the entity is in the ADMIRE_ITEM activity.
     *
     * @param brain The brain of the BrainyGroblinEntity, which will be used to set the task list.
     */
    private static void addAdmireItemActivities(Brain<BrainyGroblinEntity> brain) {
        // Set the task list for the admire item activities.
        // The task list is a list of tasks that the entity will perform in a specific order.
        // In this case, the task list is for the Activity.ADMIRE_ITEM, which means these tasks are performed when the entity is actively admiring an item.
        brain.setTaskList(
                // The activity type for this task list.
                Activity.ADMIRE_ITEM,
                // The priority of this task list. A lower priority means the tasks will be performed before tasks with a higher priority.
                10,
                // The list of tasks to be performed.
                ImmutableList.of(
                        /**
                         * Walk towards nearest visible wanted item task.
                         * This task makes the entity walk towards the nearest visible item that it wants.
                         * The GroblinBrain::doesNotHaveCopperTokenInOffHand lambda function is used to determine if the entity does not have a copper token in its offhand.
                         * The task is created with a speed of 1.0F, and the entity will walk towards the item even if it is not visible,
                         *  as long as the entity is within 9 blocks of the entity.
                         */
                        WalkTowardsNearestVisibleWantedItemTask.create(GroblinBrain::doesNotHaveCopperTokenInOffHand, 1.0F, true, 9),
                        /**
                         * Want new item task.
                         * This task makes the entity want a new item.
                         * The task is created with a range of 9 blocks. I believe that this should be the same as the WalkTowardsNearestVisibleWantedItemTask radius.
                         */
                        WantNewItemTask.create(9),
                        /**
                         * Admire item time limit task.
                         * This task makes the entity admire an item for a certain amount of time.
                         * The task is created with a cooldown of 200 ticks, and the entity will admire the item for a maximum of 200 ticks.
                         */
                        AdmireItemTimeLimitTask.create(200, 200)
                ),
                // The memory module type for this task list.
                MemoryModuleType.ADMIRING_ITEM
        );
    }

    /**
     * Adds a task list to the BrainyGroblinEntity's brain for avoiding activities.
     * This task list is used when the entity is in the AVOID activity.
     *
     * @param brain The brain of the BrainyGroblinEntity, which will be used to set the task list.
     */
    private static void addAvoidActivities(Brain<BrainyGroblinEntity> brain) {
        // Set the task list for the avoid activities.
        // The task list is a list of tasks that the entity will perform in a specific order.
        // In this case, the task list is for the Activity.AVOID, which means these tasks are performed when the entity is actively avoiding something.
        brain.setTaskList(
                // The activity type for this task list.
                Activity.AVOID,
                // The priority of this task list. A lower priority means the tasks will be performed before tasks with a higher priority.
                10,
                // The list of tasks to be performed.
                ImmutableList.of(
                        /**
                         * Go to remembered position task.
                         * This task makes the entity go to a remembered position. In this case, it is just used to mark the position.
                         * The position is stored in the MemoryModuleType.AVOID_TARGET memory module.
                         * The entity will move at a speed of 1.0F and will stop within 12 blocks of the target position.
                         * I believe that true means that the entity will be avoided if it can be reached, but I am not certain.
                         */
                        GoToRememberedPositionTask.createEntityBased(MemoryModuleType.AVOID_TARGET, 1.0F, 12, true),
                        /**
                         * Make random follow task.
                         * This task makes the entity follow a random path.
                         */
                        makeRandomFollowTask(),
                        /**
                         * Make random wander task.
                         * This task makes the entity wander randomly.
                         */
                        makeRandomWanderTask()
                        /**
                         * Forget task.
                         * This task makes the entity forget a specific memory module.
                         * The memory module to forget is determined by the GroblinBrain::shouldRunAwayFromCreepers lambda function.
                         * The memory module type is MemoryModuleType.AVOID_TARGET.
                         * This task is currently commented out.
                         * ForgetTask.<PathAwareEntity>create(GroblinBrain::shouldRunAwayFromCreepers, MemoryModuleType.AVOID_TARGET)
                         */
                ),
                // The memory module type for this task list.
                MemoryModuleType.AVOID_TARGET
        );
    }

    /**
     * Ticks the activities of the BrainyGroblinEntity.
     * This method is called repeatedly to update the entity's activities.
     *
     * @param brainyGroblin The BrainyGroblinEntity whose activities will be ticked.
     */
    public static void tickActivities(BrainyGroblinEntity brainyGroblin) {
        // Get the brain of the BrainyGroblinEntity.
        Brain<BrainyGroblinEntity> brain = brainyGroblin.getBrain();

        // Get the first possible non-core activity of the brain.
        // A non-core activity is an activity that is not essential to the entity's survival.
        Activity activity = brain.getFirstPossibleNonCoreActivity().orElse(null);

        // Reset the possible activities of the brain.
        // This clears the list of possible activities and sets it to a new list.
        // The new list includes the ADMIRE_ITEM, FIGHT, AVOID, and IDLE activities.
        // This is to make sure that the entity does not get stuck performing the same activity over and over again.
        brain.resetPossibleActivities(ImmutableList.of(Activity.ADMIRE_ITEM, Activity.FIGHT, Activity.AVOID, Activity.IDLE));

        // Get the second possible non-core activity of the brain again.
        // This is done to check if the activity has changed.
        Activity activity2 = brain.getFirstPossibleNonCoreActivity().orElse(null);

        // If the activity has changed, play a sound.
        // The sound to play is determined by the getCurrentActivitySound method.
        if (activity != activity2) {
            getCurrentActivitySound(brainyGroblin).ifPresent(brainyGroblin::playSound);
        }

        // Set the attacking state of the BrainyGroblinEntity.
        // The entity is attacking if it has a memory module of type ATTACK_TARGET.
        brainyGroblin.setAttacking(brain.hasMemoryModule(MemoryModuleType.ATTACK_TARGET));
    }

    // Tasks
    private static RandomTask<BrainyGroblinEntity> makeRandomWanderTask() {
        return new RandomTask<>(
                ImmutableList.of(
                        Pair.of(StrollTask.create(0.6F), 2),
                        Pair.of(FindEntityTask.create(WoahCodingEntityTypes.BRAINY_GROBLIN_ENTITY_ENTITY_TYPE, 8, MemoryModuleType.INTERACTION_TARGET, 0.6F, 2), 2),
                        Pair.of(TaskTriggerer.runIf(GroblinBrain::canWander, GoToLookTargetTask.create(0.6F, 3)), 2),
                        Pair.of(new WaitTask(30, 60), 1)
                )
        );
    }

    private static Task<BrainyGroblinEntity> goToNemesisTask() {
        return MemoryTransferTask.create(BrainyGroblinEntity::isBaby, MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleType.AVOID_TARGET, GO_TO_NEMESIS_MEMORY_DURATION);
    }

    private static RandomTask<LivingEntity> makeRandomFollowTask() {
        return new RandomTask<>(
                ImmutableList.<Pair<? extends Task<? super LivingEntity>, Integer>>builder().addAll(makeFollowTasks()).add(Pair.of(new WaitTask(30, 60), 1)).build()
        );
    }

    private static ImmutableList<Pair<SingleTickTask<LivingEntity>, Integer>> makeFollowTasks() {
        return ImmutableList.of(
                Pair.of(LookAtMobTask.create(EntityType.PLAYER, 8.0F), 1),
                Pair.of(LookAtMobTask.create(WoahCodingEntityTypes.GROBLIN_ENTITY_ENTITY_TYPE, 8.0F), 1),
                Pair.of(LookAtMobTask.create(WoahCodingEntityTypes.BRAINY_GROBLIN_ENTITY_ENTITY_TYPE, 8.0F), 1),
                Pair.of(LookAtMobTask.create(8.0F), 1)
        );
    }

    // Player Interaction
    public static ActionResult playerInteract(ServerWorld world, BrainyGroblinEntity brainyGroblin, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (isWillingToTrade(brainyGroblin, itemStack)) {
            ItemStack itemStack2 = itemStack.splitUnlessCreative(1, player);
            swapItemWithOffHand(world, brainyGroblin, itemStack2);
            setAdmiringItem(brainyGroblin);
            stopWalking(brainyGroblin);
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.PASS;
        }
    }

    // Barter Methods
    private static void barterItem(BrainyGroblinEntity brainyGroblin, ItemStack stack) {
        ItemStack itemStack = brainyGroblin.addItem(stack);
        dropBarteredItem(brainyGroblin, Collections.singletonList(itemStack));
    }

    private static void doBarter(BrainyGroblinEntity brainyGroblin, List<ItemStack> items) {
        Optional<PlayerEntity> optional = brainyGroblin.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER);
        if (optional.isPresent()) {
            dropBarteredItem(brainyGroblin, optional.get(), items);
        } else {
            dropBarteredItem(brainyGroblin, items);
        }
    }

    private static void dropBarteredItem(BrainyGroblinEntity brainyGroblin, PlayerEntity player, List<ItemStack> items) {
        drop(brainyGroblin, items, player.getPos());
    }

    private static void dropBarteredItem(BrainyGroblinEntity brainyGroblin, List<ItemStack> items) {
        drop(brainyGroblin, items, findGround(brainyGroblin));
    }

    public static void consumeOffHandItem(ServerWorld world, BrainyGroblinEntity brainyGroblin, boolean barter) {
        ItemStack groblinStackInOffhand = brainyGroblin.getStackInHand(Hand.OFF_HAND);
        brainyGroblin.setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY);

        boolean acceptsForBarter = acceptsForBarter(groblinStackInOffhand);
        boolean isGroblinEquipped = !brainyGroblin.tryEquip(world, groblinStackInOffhand).isEmpty();
        if (barter && acceptsForBarter) {
            doBarter(brainyGroblin, getBarteredItem(brainyGroblin));
        } else if (!acceptsForBarter) {
            if (!isGroblinEquipped) {
                barterItem(brainyGroblin, groblinStackInOffhand);
            }
        }
        if (!isGroblinEquipped) {
            ItemStack groblinMainHandStack = brainyGroblin.getMainHandStack();
            if (isCopperTokenItem(groblinMainHandStack)) {
                barterItem(brainyGroblin, groblinMainHandStack);
            } else {
                doBarter(brainyGroblin, Collections.singletonList(groblinMainHandStack));
            }

            brainyGroblin.equipToMainHand(groblinStackInOffhand);
        }
    }

    private static void drop(BrainyGroblinEntity brainyGroblin, List<ItemStack> items, Vec3d pos) {
        if (!items.isEmpty()) {
            brainyGroblin.swingHand(Hand.OFF_HAND);

            for (ItemStack itemStack : items) {
                TargetUtil.give(brainyGroblin, itemStack, pos.add(0.0, 1.0, 0.0));
            }
        }
    }

    private static Vec3d findGround(BrainyGroblinEntity brainyGroblin) {
        Vec3d vec3d = FuzzyTargeting.find(brainyGroblin, 4, 2);
        return vec3d == null ? brainyGroblin.getPos() : vec3d;
    }

    public static void loot(ServerWorld world, BrainyGroblinEntity brainyGroblin, ItemEntity itemEntity) {
        stopWalking(brainyGroblin);
        ItemStack itemStack;
        if (itemEntity.getStack().isOf(WoahCodingItems.COPPER_TOKEN_ITEM)) {
            brainyGroblin.sendPickup(itemEntity, itemEntity.getStack().getCount());
            itemStack = itemEntity.getStack();
            itemEntity.discard();
        } else {
            brainyGroblin.sendPickup(itemEntity, 1);
            itemStack = getItemFromStack(itemEntity);
        }

        if (isCopperTokenItem(itemStack)) {
            brainyGroblin.getBrain().forget(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM);
            swapItemWithOffHand(world, brainyGroblin, itemStack);
            setAdmiringItem(brainyGroblin);
        } else {
            boolean bl = !brainyGroblin.tryEquip(world, itemStack).equals(ItemStack.EMPTY);
            if (!bl) {
                barterItem(brainyGroblin, itemStack);
            }
        }
    }

    private static void swapItemWithOffHand(ServerWorld world, BrainyGroblinEntity brainyGroblin, ItemStack stack) {
        if (hasItemInOffHand(brainyGroblin)) {
            brainyGroblin.dropStack(world, brainyGroblin.getStackInHand(Hand.OFF_HAND));
        }

        brainyGroblin.equipToOffHand(stack);
    }

    private static void stopWalking(BrainyGroblinEntity brainyGroblin) {
        brainyGroblin.getBrain().forget(MemoryModuleType.WALK_TARGET);
        brainyGroblin.getNavigation().stop();
    }

    // Anger and Defence Methods
    public static void onAttacked(ServerWorld world, BrainyGroblinEntity brainyGroblin, LivingEntity attacker) {
        if (!(attacker instanceof BrainyGroblinEntity)) {
            if (hasItemInOffHand(brainyGroblin)) {
                consumeOffHandItem(world, brainyGroblin, false);
            }

            Brain<BrainyGroblinEntity> brain = brainyGroblin.getBrain();
            brain.forget(MemoryModuleType.ADMIRING_ITEM);
            if (attacker instanceof PlayerEntity) {
                brain.remember(MemoryModuleType.ADMIRING_DISABLED, true, 400L);
            }

            getAvoiding(brainyGroblin).ifPresent(avoiding -> {
                if (avoiding.getType() != attacker.getType()) {
                    brain.forget(MemoryModuleType.AVOID_TARGET);
                }
            });
            if (brainyGroblin.isBaby()) {
                brain.remember(MemoryModuleType.AVOID_TARGET, attacker, 100L);
                if (Sensor.testAttackableTargetPredicateIgnoreVisibility(world, brainyGroblin, attacker)) {
                    angerAtCloserTargets(world, brainyGroblin, attacker);
                }
            } //else if (attacker.getType() == EntityType.HOGLIN && hasOutnumberedHoglins(brainyGroblin)) {
              //  runAwayFrom(brainyGroblin, attacker);
              //  groupRunAwayFrom(brainyGroblin, attacker);
            //}
            else {
                tryRevenge(world, brainyGroblin, attacker);
            }
        }
    }

    protected static void tryRevenge(ServerWorld world, BrainyGroblinEntity brainyGroblin, LivingEntity target) {
        if (!brainyGroblin.getBrain().hasActivity(Activity.AVOID)) {
            if (Sensor.testAttackableTargetPredicateIgnoreVisibility(world, brainyGroblin, target)) {
                if (!TargetUtil.isNewTargetTooFar(brainyGroblin, target, 4.0)) {
                    if (target.getType() == EntityType.PLAYER && world.getGameRules().getBoolean(GameRules.UNIVERSAL_ANGER)) {
                        becomeAngryWithPlayer(world, brainyGroblin, target);
                        angerNearbyGroblins(world, brainyGroblin);
                    } else {
                        becomeAngryWith(world, brainyGroblin, target);
                        angerAtCloserTargets(world, brainyGroblin, target);
                    }
                }
            }
        }
    }

    protected static void angerAtCloserTargets(ServerWorld world, BrainyGroblinEntity brainyGroblin, LivingEntity target) {
        getNearbyGroblins(brainyGroblin).forEach(nearbyGroblin -> {
            if (target.getType() != EntityType.CREEPER) {
                angerAtIfCloser(world, nearbyGroblin, target);
            }
        });
    }

    protected static void angerNearbyGroblins(ServerWorld world, BrainyGroblinEntity brainyGroblin) {
        getNearbyGroblins(brainyGroblin).forEach(nearbyGroblin -> getNearestDetectedPlayer(nearbyGroblin).ifPresent(player -> becomeAngryWith(world, nearbyGroblin, player)));
    }

    protected static void becomeAngryWith(ServerWorld world, BrainyGroblinEntity brainyGroblin, LivingEntity target) {
        if (Sensor.testAttackableTargetPredicateIgnoreVisibility(world, brainyGroblin, target)) {
            brainyGroblin.getBrain().forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            brainyGroblin.getBrain().remember(MemoryModuleType.ANGRY_AT, target.getUuid(), 600L);
        }
    }

    private static void becomeAngryWithPlayer(ServerWorld world, BrainyGroblinEntity brainyGroblin, LivingEntity target) {
        Optional<PlayerEntity> optional = getNearestDetectedPlayer(brainyGroblin);
        if (optional.isPresent()) {
            becomeAngryWith(world, brainyGroblin, optional.get());
        } else {
            becomeAngryWith(world, brainyGroblin, target);
        }
    }

    private static void angerAtIfCloser(ServerWorld world, BrainyGroblinEntity brainyGroblin, LivingEntity target) {
        Optional<LivingEntity> optional = getAngryAt(brainyGroblin);
        LivingEntity livingEntity = TargetUtil.getCloserEntity(brainyGroblin, optional, target);
        if (!optional.isPresent() || optional.get() != livingEntity) {
            becomeAngryWith(world, brainyGroblin, livingEntity);
        }
    }

    // Boolean Methods
    private static boolean isHoldingGroblinTool(LivingEntity brainyGroblin) {
        return brainyGroblin.isHolding(GroblinBrain::isGroblinTool);
    }

    private static boolean isGroblinTool(ItemStack stack) {
        return stack.isOf(Items.STONE_SHOVEL) || stack.isOf(Items.STONE_SWORD) || stack.isOf(Items.STONE_PICKAXE);
    }

    private static boolean hasTargetToAvoid(BrainyGroblinEntity brainyGroblin) {
        Brain<?> brain = brainyGroblin.getBrain();
        return brain.hasMemoryModule(MemoryModuleType.AVOID_TARGET) && brain.getOptionalRegisteredMemory(MemoryModuleType.AVOID_TARGET).get().isInRange(brainyGroblin, 12.0);
    }

    private static boolean isAdmiringItem(BrainyGroblinEntity entity) {
        return entity.getBrain().hasMemoryModule(MemoryModuleType.ADMIRING_ITEM);
    }

    private static boolean acceptsForBarter(ItemStack stack) {
        return stack.isOf(WoahCodingItems.COPPER_TOKEN_ITEM);
    }

    private static boolean hasEmeraldBlockNearby(BrainyGroblinEntity brainyGroblin) {
        return brainyGroblin.getBrain().hasMemoryModule(MemoryModuleType.NEAREST_REPELLENT);
    }

    private static boolean hasPlayerHoldingWantedItemNearby(LivingEntity entity) {
        return entity.getBrain().hasMemoryModule(MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM);
    }

    private static boolean canWander(LivingEntity brainyGroblin) {
        return !hasPlayerHoldingWantedItemNearby(brainyGroblin);
    }

    public static boolean isCopperTokenHoldingPlayer(LivingEntity target) {
        return target.getType() == EntityType.PLAYER && target.isHolding(GroblinBrain::isCopperTokenItem);
    }

    public static boolean canGather(BrainyGroblinEntity brainyGroblin, ItemStack stack) {
        if (hasBeenHitByPlayer(brainyGroblin) && brainyGroblin.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET)) {
            return false;
        } else if (acceptsForBarter(stack)) {
            return doesNotHaveCopperTokenInOffHand(brainyGroblin);
        } else {
            boolean isStackInsertable = brainyGroblin.canInsertIntoInventory(stack);
            if (stack.isOf(WoahCodingItems.COPPER_TOKEN_ITEM)) {
                return isStackInsertable;
            } else {
                return !isCopperTokenItem(stack) ? brainyGroblin.canEquipStack(stack) : doesNotHaveCopperTokenInOffHand(brainyGroblin) && isStackInsertable;
            }
        }
    }

    public static boolean isCopperTokenItem(ItemStack stack) {
        return stack.isOf(WoahCodingItems.COPPER_TOKEN_ITEM);
    }

    private static boolean hasItemInOffHand(BrainyGroblinEntity brainyGroblin) {
        return !brainyGroblin.getOffHandStack().isEmpty();
    }

    private static boolean doesNotHaveCopperTokenInOffHand(BrainyGroblinEntity brainyGroblin) {
        return brainyGroblin.getOffHandStack().isEmpty() || !isCopperTokenItem(brainyGroblin.getOffHandStack());
    }

    public static boolean isWillingToTrade(BrainyGroblinEntity brainyGroblin, ItemStack nearbyItems) {
        return !hasBeenHitByPlayer(brainyGroblin) && !isAdmiringItem(brainyGroblin) && acceptsForBarter(nearbyItems);
    }

    private static boolean hasBeenHitByPlayer(BrainyGroblinEntity brainyGroblin) {
        return brainyGroblin.getBrain().hasMemoryModule(MemoryModuleType.ADMIRING_DISABLED);
    }

    //public static boolean isHoldingEmerald(LivingEntity entity) {
    //    for (ItemStack itemStack : entity.getHandItems()) {
    //        if (itemStack.isOf(Items.EMERALD)) {
    //            return true;
    //        }
    //    }
//
    //    return false;
    //}

    private static boolean isPreferredAttackTarget(ServerWorld world, BrainyGroblinEntity brainyGroblin, LivingEntity target) {
        return getPreferredTarget(world, brainyGroblin).filter(preferredTarget -> preferredTarget == target).isPresent();
    }

    //private static boolean hasTargetToAvoid(BrainyGroblinEntity brainyGroblin) {
    //    Brain<?> brain = brainyGroblin.getBrain();
    //    return !brain.hasMemoryModule(MemoryModuleType.AVOID_TARGET)
    //            ? false
    //            : brain.getOptionalRegisteredMemory(MemoryModuleType.AVOID_TARGET).get().isInRange(brainyGroblin, 12.0);
    //}

    //private static boolean shouldRunAwayFromCreepers(BrainyGroblinEntity brainyGroblin) {
    //    Brain<BrainyGroblinEntity> brain = brainyGroblin.getBrain();
    //    if (!brain.hasMemoryModule(MemoryModuleType.AVOID_TARGET)) {
    //        return true;
    //    } else {
    //        LivingEntity livingEntity = brain.getOptionalRegisteredMemory(MemoryModuleType.AVOID_TARGET).get();
    //        EntityType<?> entityType = livingEntity.getType();
    //        if (entityType == EntityType.CREEPER) {
    //            return hasNoAdvantageAgainstCreepers(brainyGroblin);
    //        } else {
    //            return false;
    //        }
    //    }
    //}

    //private static boolean hasNoAdvantageAgainstCreepers(BrainyGroblinEntity brainyGroblin) {
    //    return !hasOutnumberedCreepers(brainyGroblin);
    //}

    //private static boolean hasOutnumberedCreepers(BrainyGroblinEntity brainyGroblin) {
    //    int i = (Integer)brainyGroblin.getBrain().getOptionalRegisteredMemory(MemoryModuleType.VISIBLE_BRAINY_GROBLIN_COUNT).orElse(0) + 1;
    //    int j = (Integer)brainyGroblin.getBrain().getOptionalRegisteredMemory(MemoryModuleType.VISIBLE_CREEPER_COUNT).orElse(0);
    //    return j > i;
    //}

    // Getters
    public static Optional<SoundEvent> getCurrentActivitySound(BrainyGroblinEntity brainyGroblin) {
        return brainyGroblin.getBrain().getFirstPossibleNonCoreActivity().map(activity -> getSound(brainyGroblin, activity));
    }

    private static SoundEvent getSound(BrainyGroblinEntity brainyGroblin, Activity activity) {
        if (activity == Activity.FIGHT) {
            return SoundEvents.ENTITY_CREEPER_PRIMED;
        } else if (activity == Activity.AVOID && hasTargetToAvoid(brainyGroblin)) {
            return SoundEvents.ENTITY_CAT_HISS;
        } else if (activity == Activity.ADMIRE_ITEM) {
            return SoundEvents.ENTITY_VILLAGER_TRADE;
        } else if (activity == Activity.CELEBRATE) {
            return SoundEvents.ENTITY_PIGLIN_CELEBRATE;
        } else if (hasPlayerHoldingWantedItemNearby(brainyGroblin)) {
            return SoundEvents.ENTITY_ALLAY_DEATH;
        } else {
            return hasEmeraldBlockNearby(brainyGroblin) ? SoundEvents.ENTITY_CAT_HISS : SoundEvents.BLOCK_ANVIL_LAND;
        }
    }

    private static ItemStack getItemFromStack(ItemEntity stack) {
        ItemStack itemStack = stack.getStack();
        ItemStack itemStack2 = itemStack.split(1);
        if (itemStack.isEmpty()) {
            stack.discard();
        } else {
            stack.setStack(itemStack);
        }

        return itemStack2;
    }

    private static List<ItemStack> getBarteredItem(BrainyGroblinEntity brainyGroblin) {
        LootTable lootTable = brainyGroblin.getWorld().getServer().getReloadableRegistries().getLootTable(WoahCodingLootTables.GROBLIN_BARTERING_GAMEPLAY);
        List<ItemStack> list = lootTable.generateLoot(
                new LootWorldContext.Builder((ServerWorld)brainyGroblin.getWorld()).add(LootContextParameters.THIS_ENTITY, brainyGroblin).build(LootContextTypes.BARTER)
        );
        return list;
    }

    private static Optional<? extends LivingEntity> getPreferredTarget(ServerWorld world, BrainyGroblinEntity brainyGroblin) {
        Brain<BrainyGroblinEntity> brain = brainyGroblin.getBrain();
        Optional<LivingEntity> livingEntityAngryAt = TargetUtil.getEntity(brainyGroblin, MemoryModuleType.ANGRY_AT);
        if (livingEntityAngryAt.isPresent() && Sensor.testAttackableTargetPredicateIgnoreVisibility(world, brainyGroblin, livingEntityAngryAt.get())) {
            return livingEntityAngryAt;
        } else {
            if (brain.hasMemoryModule(WoahCodingMemoryModuleTypes.NEAREST_TARGETABLE_PLAYER_HOLDING_EMERALD)) {
                Optional<PlayerEntity> nearestVisibleTargetablePlayer = brain.getOptionalRegisteredMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER);
                if (nearestVisibleTargetablePlayer.isPresent()) {
                    return nearestVisibleTargetablePlayer;
                }
            }

            Optional<MobEntity> nearestVisibleNemesis = brain.getOptionalRegisteredMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS);
            if (nearestVisibleNemesis.isPresent()) {
                return nearestVisibleNemesis;
            } else {
                Optional<PlayerEntity> nearestTargetablePlayerHoldingEmerald = brain.getOptionalRegisteredMemory(WoahCodingMemoryModuleTypes.NEAREST_TARGETABLE_PLAYER_HOLDING_EMERALD);
                return nearestTargetablePlayerHoldingEmerald.isPresent() && Sensor.testAttackableTargetPredicate(world, brainyGroblin, nearestTargetablePlayerHoldingEmerald.get()) ? nearestTargetablePlayerHoldingEmerald : Optional.empty();
            }
        }

    }

    private static Optional<LivingEntity> getAngryAt(BrainyGroblinEntity brainyGroblin) {
        return TargetUtil.getEntity(brainyGroblin, MemoryModuleType.ANGRY_AT);
    }

    public static Optional<LivingEntity> getAvoiding(BrainyGroblinEntity brainyGroblin) {
        return brainyGroblin.getBrain().hasMemoryModule(MemoryModuleType.AVOID_TARGET)
                ? brainyGroblin.getBrain().getOptionalRegisteredMemory(MemoryModuleType.AVOID_TARGET)
                : Optional.empty();
    }

    public static Optional<PlayerEntity> getNearestDetectedPlayer(BrainyGroblinEntity brainyGroblin) {
        return brainyGroblin.getBrain().hasMemoryModule(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER)
                ? brainyGroblin.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER)
                : Optional.empty();
    }

    protected static List<BrainyGroblinEntity> getNearbyVisibleGroblins(BrainyGroblinEntity brainyGroblin) {
        return brainyGroblin.getBrain().getOptionalRegisteredMemory(WoahCodingMemoryModuleTypes.NEAREST_GROBLINS).orElse(ImmutableList.of());
    }

    private static List<BrainyGroblinEntity> getNearbyGroblins(BrainyGroblinEntity brainyGroblin) {
        return brainyGroblin.getBrain().getOptionalRegisteredMemory(WoahCodingMemoryModuleTypes.NEARBY_GROBLINS).orElse(ImmutableList.of());
    }

    // Setters
    private static void setAdmiringItem(LivingEntity entity) {
        entity.getBrain().remember(MemoryModuleType.ADMIRING_ITEM, true, 119L);
    }
}
