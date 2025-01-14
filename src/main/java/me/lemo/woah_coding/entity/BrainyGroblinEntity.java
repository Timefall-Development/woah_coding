
package me.lemo.woah_coding.entity;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import me.lemo.woah_coding.entity.mob.GroblinActivity;
import me.lemo.woah_coding.entity.mob.GroblinBrain;
import me.lemo.woah_coding.registry.WoahCodingMemoryModuleTypes;
import me.lemo.woah_coding.registry.WoahCodingSensorTypes;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.Profilers;
import net.minecraft.world.GameRules;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BrainyGroblinEntity extends PathAwareEntity implements InventoryOwner {

    private final SimpleInventory inventory = new SimpleInventory(8);

    protected static final ImmutableList<SensorType<? extends Sensor<? super BrainyGroblinEntity>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.NEAREST_PLAYERS,
            SensorType.NEAREST_ITEMS,
            SensorType.HURT_BY,
            WoahCodingSensorTypes.GROBLIN_SPECIFIC_SENSOR
    );

    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULE_TYPES = ImmutableList.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.DOORS_TO_CLOSE,
            MemoryModuleType.MOBS,
            MemoryModuleType.VISIBLE_MOBS,
            MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM,
            MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS,
            MemoryModuleType.HURT_BY,
            MemoryModuleType.HURT_BY_ENTITY,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.ATTACK_COOLING_DOWN,
            MemoryModuleType.INTERACTION_TARGET,
            MemoryModuleType.PATH,
            MemoryModuleType.AVOID_TARGET,
            MemoryModuleType.ADMIRING_ITEM,
            MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM,
            MemoryModuleType.ADMIRING_DISABLED,
            MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM,
            MemoryModuleType.NEAREST_VISIBLE_NEMESIS,
            WoahCodingMemoryModuleTypes.NEAREST_TARGETABLE_PLAYER_HOLDING_EMERALD,
            MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM,
            MemoryModuleType.ANGRY_AT
    );

    public BrainyGroblinEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createBrainyGroblinAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.MAX_HEALTH, 16.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.35F)
                .add(EntityAttributes.ATTACK_DAMAGE, 5.0);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        Random random = world.getRandom();
        if (spawnReason != SpawnReason.STRUCTURE) {
            this.equipStack(EquipmentSlot.MAINHAND, this.makeInitialWeapon());
        }

        this.initEquipment(random, difficulty);
        this.updateEnchantments(world, random, difficulty);
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        this.writeInventory(nbt, this.getRegistryManager());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.readInventory(nbt, this.getRegistryManager());
    }

    @Override
    protected Brain.Profile<BrainyGroblinEntity> createBrainProfile() {
        return Brain.createProfile(MEMORY_MODULE_TYPES, SENSOR_TYPES);
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return GroblinBrain.create(this, this.createBrainProfile().deserialize(dynamic));
    }

    @Override
    public Brain<BrainyGroblinEntity> getBrain() {
        return (Brain<BrainyGroblinEntity>) super.getBrain();
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ActionResult actionResult = super.interactMob(player, hand);
        if (actionResult.isAccepted()) {
            return actionResult;
        } else if (this.getWorld() instanceof ServerWorld serverWorld) {
            return GroblinBrain.playerInteract(serverWorld, this, player, hand);
        } else {
            boolean isWillingToTrade = GroblinBrain.isWillingToTrade(
                    this,
                    player.getStackInHand(hand)
            )
                    && this.getActivity()
                    != GroblinActivity.ADMIRING_ITEM;
            return isWillingToTrade ? ActionResult.SUCCESS : ActionResult.PASS;
        }
    }

    @Override
    protected void loot(ServerWorld world, ItemEntity itemEntity) {
        this.triggerItemPickedUpByEntityCriteria(itemEntity);
        GroblinBrain.loot(world, this, itemEntity);
    }

    private ItemStack makeInitialWeapon() {
        return (double)this.random.nextFloat() < 0.5 ? new ItemStack(Items.STONE_SHOVEL) : new ItemStack(Items.STONE_SWORD);
    }

    public GroblinActivity getActivity() {
        if (this.isAttacking() && this.isHoldingTool()) {
            return GroblinActivity.ATTACKING_WITH_MELEE_WEAPON;
        } else if (GroblinBrain.isCopperTokenItem(this.getOffHandStack())) {
            return GroblinActivity.ADMIRING_ITEM;
        } else {
            return GroblinActivity.DEFAULT;
        }
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        boolean isDamaged = super.damage(world, source, amount);
        if (isDamaged && source.getAttacker() instanceof LivingEntity livingEntity) {
            GroblinBrain.onAttacked(world, this, livingEntity);
        }

        return isDamaged;
    }

    protected boolean isHoldingTool() {
        return this.getMainHandStack().contains(DataComponentTypes.TOOL);
    }

    @Override
    public SimpleInventory getInventory() {
        return this.inventory;
    }

    @Override
    protected void dropEquipment(ServerWorld world, DamageSource source, boolean causedByPlayer) {
        super.dropEquipment(world, source, causedByPlayer);
        if (source.getAttacker() instanceof CreeperEntity creeperEntity && creeperEntity.shouldDropHead()) {
            ItemStack itemStack = new ItemStack(Items.PIGLIN_HEAD);
            creeperEntity.onHeadDropped();
            this.dropStack(world, itemStack);
        }

        this.inventory.clearToList().forEach(stack -> this.dropStack(world, stack));
    }

    public ItemStack addItem(ItemStack stack) {
        return this.inventory.addStack(stack);
    }

    public boolean canInsertIntoInventory(ItemStack stack) {
        return this.inventory.canInsert(stack);
    }

    public void equipToMainHand(ItemStack stack) {
        this.equipLootStack(EquipmentSlot.MAINHAND, stack);
    }

    public void equipToOffHand(ItemStack stack) {
        if (stack.isOf(GroblinBrain.BARTERING_ITEM)) {
            this.equipStack(EquipmentSlot.OFFHAND, stack);
            this.updateDropChances(EquipmentSlot.OFFHAND);
        } else {
            this.equipLootStack(EquipmentSlot.OFFHAND, stack);
        }
    }

    @Override
    public boolean canGather(ServerWorld world, ItemStack stack) {
        return world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) && this.canPickUpLoot() && GroblinBrain.canGather(this, stack);
    }

    public boolean canEquipStack(ItemStack stack) {
        EquipmentSlot equipmentSlot = this.getPreferredEquipmentSlot(stack);
        ItemStack itemStack = this.getEquippedStack(equipmentSlot);
        return this.prefersNewEquipment(stack, itemStack, equipmentSlot);
    }

    @Override
    protected void mobTick(ServerWorld world) {
        Profiler profiler = Profilers.get();
        profiler.push("groblinBrain");
        this.getBrain().tick(world, this);
        profiler.pop();
        GroblinBrain.tickActivities(this);
        super.mobTick(world);
    }
}

