package im.hunnybon.mobsplosion.mixin;


import im.hunnybon.mobsplosion.Mobsplosion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
abstract class LivingEntityMixin extends Entity {

    @Shadow public abstract float getMaxHealth();

    public LivingEntityMixin(World world) {
        super(world);
    }
    // y this.getBodyY(0.0625D)
    @Inject(method = "onKilled", at = @At(value = "HEAD"))
    public void onDeath(DamageSource source, CallbackInfo ci){
        if (this.getMaxHealth() > 2.0f & this.world instanceof ServerWorld){
            this.world.method_330(this, this.x, this.y, this.z,
                    this.getMaxHealth() / 7.0f, Mobsplosion.config.destroyBlocks, Mobsplosion.config.createsFire);
        }
    }
}
