package net.joaquinrossi.eva.entity;

import net.joaquinrossi.eva.Eva;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class LanceOfLonginusEntity extends ThrownItemEntity {
    public LanceOfLonginusEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }
 
    public LanceOfLonginusEntity(World world, LivingEntity owner) {
        super(Eva.LanceOfLonginusEntityType, owner, world);
    }
 
    public LanceOfLonginusEntity(World world, double x, double y, double z) {
        super(Eva.LanceOfLonginusEntityType, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Eva.LANCE_OF_LONGINUS;
    }

    @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.CRIMSON_SPORE : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
    }

    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();
 
            for (int i = 0; i < 8; i++) {
                this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0d, 0.0d, 0.0d);
            }
        }
    }
 
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity(); // sets a new Entity instance as the EntityHitResult (victim)

        entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 10); // deals damage
 
        // checks if entity is an instance of LivingEntity (meaning it is not a boat or minecart)
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.playSound(SoundEvents.ITEM_TRIDENT_HIT, 2F, 1F); // plays a sound for the entity hit only
        }
    }
 
    // called on collision with a block
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);

        // checks if the world is client
        if (!this.world.isClient) {
            this.world.sendEntityStatus(this, (byte) 3); // particle?
            this.kill(); // kills the projectile
        }
 
    }
}