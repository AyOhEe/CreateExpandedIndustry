package io.github.ayohee.expandedindustry.content.items;

import io.github.ayohee.expandedindustry.register.EIItems;
import io.github.ayohee.expandedindustry.register.EIParticleTypes;
import io.github.ayohee.expandedindustry.register.EISoundEvents;
import io.github.ayohee.expandedindustry.util.EIUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

public class PartyPopperItem extends Item {

    public PartyPopperItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (!(entityLiving instanceof Player player)) {
            return;
        }

        int useDuration = this.getUseDuration(stack, entityLiving) - timeLeft;
        if (useDuration < 0) {
            return;
        }

        float power = getPowerForTime(useDuration);
        if (power == 1.00f) {
            if (level instanceof ServerLevel && !player.hasInfiniteMaterials()) {
                stack.shrink(1);
            }

            Vec3 playerLookDir = player.getLookAngle().scale(10);
            spawnParticles(level, player.getX(), player.getY() + 1.55, player.getZ(), playerLookDir, 1);
            playPopperSound(level, player.getX(), player.getY() + 1.55, player.getZ(), false);

            player.awardStat(Stats.ITEM_USED.get(this));
        }
    }

    public static void playPopperSound(Level level, double x, double y, double z, boolean forceNormal) {
        if (!(level instanceof ServerLevel sl)) {
            return;
        }

        if (level.getRandom().nextDouble() < (float) 1 / 10 && !forceNormal) {
            level.playSound(
                    null,
                    x,
                    y,
                    z,
                    EISoundEvents.POPPER_YIPPEE,
                    SoundSource.PLAYERS,
                    1.6f,
                    1.0f
            );
        } else {
            level.playSound(
                    null,
                    x,
                    y,
                    z,
                    SoundEvents.FIREWORK_ROCKET_BLAST,
                    SoundSource.PLAYERS,
                    1.0f,
                    1.0f
            );
        }
    }

    public static void spawnParticles(Level level, double x, double y, double z, Vec3 direction, double speed) {
        if (!(level instanceof ServerLevel sl)) {
            return;
        }

        // At least 50 particles will always render - it's the whole point of the item
        for (int i = 0; i < 50; i++) {
            EIUtil.sendParticlesForced(
                    sl,
                    EIParticleTypes.CONFETTI.get(),
                    x, y, z,
                    0,
                    direction.x * 3,
                    direction.y,
                    direction.z * 3,
                    speed
            );
        }

        // The remaining 150 won't show on "Minimal" particle settings.
        for (int i = 0; i < 150; i++) {
            sl.sendParticles(
                    EIParticleTypes.CONFETTI.get(),
                    x, y, z,
                    0,
                    direction.x * 3,
                    direction.y,
                    direction.z * 3,
                    speed
            );
        }
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

        player.startUsingItem(hand);
        return InteractionResultHolder.consume(itemstack);
    }

    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return (is) -> is.is(EIItems.MICROPLASTICS);
    }
}
