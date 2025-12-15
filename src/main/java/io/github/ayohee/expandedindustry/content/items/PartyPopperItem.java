package io.github.ayohee.expandedindustry.content.items;

import io.github.ayohee.expandedindustry.register.EIItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class PartyPopperItem extends ProjectileWeaponItem {
    public static final int MAX_DRAW_DURATION = 20;
    public static final int DEFAULT_RANGE = 15;

    public PartyPopperItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player player) {
            ItemStack itemstack = player.getProjectile(stack);
            if (!itemstack.isEmpty()) {
                int i = this.getUseDuration(stack, entityLiving) - timeLeft;
                i = net.neoforged.neoforge.event.EventHooks.onArrowLoose(stack, level, player, i, !itemstack.isEmpty());
                if (i < 0) return;
                float f = getPowerForTime(i);
                if (!((double)f < 0.1)) {
                    List<ItemStack> list = draw(stack, itemstack, player);
                    if (level instanceof ServerLevel serverlevel && !list.isEmpty()) {
                        this.shoot(serverlevel, player, player.getUsedItemHand(), stack, list, f * 3.0F, 1.0F, f == 1.0F, null);
                    }

                    level.playSound(
                            null,
                            player.getX(),
                            player.getY(),
                            player.getZ(),
                            SoundEvents.ARROW_SHOOT,
                            SoundSource.PLAYERS,
                            1.0F,
                            1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F
                    );
                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    @Override
    protected void shootProjectile(
            LivingEntity shooter, Projectile projectile, int index, float velocity, float inaccuracy, float angle, @Nullable LivingEntity target
    ) {
        projectile.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot() + angle, 0.0F, velocity, inaccuracy);
    }

    public static float getPowerForTime(int charge) {
        float f = (float)charge / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        boolean flag = !player.getProjectile(itemstack).isEmpty();

        InteractionResultHolder<ItemStack> ret = net.neoforged.neoforge.event.EventHooks.onArrowNock(itemstack, level, player, hand, flag);
        if (ret != null) return ret;

        if (!player.hasInfiniteMaterials() && !flag) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return (is) -> is.is(EIItems.MICROPLASTICS);
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }
}
