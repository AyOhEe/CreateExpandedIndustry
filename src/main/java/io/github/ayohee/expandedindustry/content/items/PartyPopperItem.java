package io.github.ayohee.expandedindustry.content.items;

import io.github.ayohee.expandedindustry.register.EIItems;
import io.github.ayohee.expandedindustry.register.EISoundEvents;
import net.minecraft.server.level.ServerLevel;
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
            if (level instanceof ServerLevel serverlevel) {
                stack.shrink(1);
            }

            //TODO change to yippee
            level.playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    EISoundEvents.POPPER_YIPPEE,
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + power * 0.5F
            );
            level.playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    EISoundEvents.POPPER_POP,
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + power * 0.5F
            );
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
