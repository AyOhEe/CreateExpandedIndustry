package io.github.ayohee.expandedindustry.content.items;

import io.github.ayohee.expandedindustry.register.EIItems;
import io.github.ayohee.expandedindustry.register.EIParticleTypes;
import io.github.ayohee.expandedindustry.register.EISoundEvents;
import net.minecraft.client.particle.SmokeParticle;
import net.minecraft.core.particles.ParticleTypes;
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
            if (level instanceof ServerLevel serverlevel && !player.hasInfiniteMaterials()) {
                stack.shrink(1);
            }

            if (level.getRandom().nextDouble() < (float) 1 / 10) {
                level.playSound(
                        null,
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        EISoundEvents.POPPER_YIPPEE,
                        SoundSource.PLAYERS,
                        1.6f,
                        1.0f
                );
            } else {
                level.playSound(
                        null,
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        SoundEvents.FIREWORK_ROCKET_BLAST,
                        SoundSource.PLAYERS,
                        1.0f,
                        1.0f
                );
            }


            Vec3 playerLookDir = player.getLookAngle().scale(10);
            for (int i = 0; i < 200; i++) {
                Vec3 nudge = new Vec3(level.getRandom().nextDouble(),
                                      level.getRandom().nextDouble(),
                                      level.getRandom().nextDouble());
                level.addParticle(
                        EIParticleTypes.CONFETTI.get(),
                        true,
                        player.getX() + (nudge.x / 4),
                        player.getY() + (nudge.y / 2) + 1.55f,
                        player.getZ() + (nudge.z / 4),
                        (playerLookDir.x * 3) + (nudge.x * 4),
                        (playerLookDir.y) + (nudge.y * 2),
                        (playerLookDir.z * 3) + (nudge.z * 4)
                );
            }
            player.awardStat(Stats.ITEM_USED.get(this));
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
