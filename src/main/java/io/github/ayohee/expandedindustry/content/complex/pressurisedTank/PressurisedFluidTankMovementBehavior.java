package io.github.ayohee.expandedindustry.content.complex.pressurisedTank;

import com.simibubi.create.api.behaviour.movement.MovementBehaviour;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;

import net.minecraft.world.level.block.entity.BlockEntity;

//TODO this is an AWFUL way of doing this. So much code duplication....
// The fluid level needs to be ticked to animate smoothly
public class PressurisedFluidTankMovementBehavior implements MovementBehaviour {
    @Override
    public boolean mustTickWhileDisabled() {
        return true;
    }

    @Override
    public void tick(MovementContext context) {
        if (context.world.isClientSide) {
            BlockEntity be = context.contraption.presentBlockEntities.get(context.localPos);
            if (be instanceof PressurisedFluidTankBlockEntity tank) {
                tank.getFluidLevel().tickChaser();
            }
        }
    }
}

