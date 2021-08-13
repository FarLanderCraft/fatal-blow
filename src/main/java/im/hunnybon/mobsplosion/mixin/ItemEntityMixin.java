package im.hunnybon.mobsplosion.mixin;

import im.hunnybon.mobsplosion.Mobsplosion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
abstract class ItemEntityMixin extends Entity {

    @Shadow private int age;

    @Shadow public abstract ItemStack getStack();

    public ItemEntityMixin(World world) {
        super(world);
    }

    @Inject(method = "damage", at = @At("HEAD"))
    public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir){
        if (source.isExplosive()){
            return;
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo ci){
        if (!this.world.isClient && this.age >= 6000) {
            this.world.createExplosion(this, this.x, this.y, this.z,
                    this.getStack().getMaxCount() / 7.0f, Mobsplosion.config.destroyBlocks, Mobsplosion.config.createsFire);
            this.remove();
        }
    }
}
