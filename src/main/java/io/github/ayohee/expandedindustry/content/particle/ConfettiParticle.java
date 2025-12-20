package io.github.ayohee.expandedindustry.content.particle;

import io.github.ayohee.expandedindustry.content.items.PartyPopperItem;
import io.github.ayohee.expandedindustry.register.EIItems;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class ConfettiParticle extends TextureSheetParticle {
    public static final Vector3f[] CONFETTI_COLOURS = {
            new Vector3f(1.00f, 0.00f, 0.00f),
            new Vector3f(1.00f, 0.50f, 0.00f),
            new Vector3f(1.00f, 1.00f, 0.00f),
            new Vector3f(0.50f, 1.00f, 0.00f),
            new Vector3f(0.00f, 1.00f, 0.00f),
            new Vector3f(0.00f, 1.00f, 0.50f),
            new Vector3f(0.00f, 1.00f, 1.00f),
            new Vector3f(0.00f, 0.50f, 1.00f),
            new Vector3f(0.00f, 0.00f, 1.00f),
            new Vector3f(0.50f, 0.00f, 1.00f),
            new Vector3f(1.00f, 0.00f, 1.00f),
            new Vector3f(1.00f, 0.00f, 0.50f),
    };

    Quaternionf orientation;

    protected ConfettiParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        gravity = 0.5f;
        this.lifetime = (int)(75.0F / (this.random.nextFloat() * 2.0F + 0.5F)) + 1;
        setAlpha(1.0f);
        quadSize *= 0.5f;
        orientation = new Quaternionf(
                level.getRandom().nextDouble() * 2 - 1,
                level.getRandom().nextDouble() * 2 - 1,
                level.getRandom().nextDouble() * 2 - 1,
                level.getRandom().nextDouble() * 2 - 1
        ).normalize();


        double spreadFactor = (level.getRandom().nextDouble() * 2 - 1);
        Vec3 spreadDir = new Vec3(this.xd, 0, this.zd).normalize();
        spreadDir = spreadDir.yRot(Mth.HALF_PI);
        spreadDir = spreadDir.scale(spreadFactor * 0.3);
        if (xSpeed == 0 && zSpeed == 0) {
            spreadDir = Vec3.ZERO;
            if (ySpeed < 0)  {
                this.yd -= 0.1;
                this.yd *= 2;
                this.xd += level.getRandom().nextBoolean() ? 0.02 : -0.02;
                this.zd += level.getRandom().nextBoolean() ? 0.02 : -0.02;
            } else {
                this.yd += 0.1;
                this.yd *= 1.3;
            }
        }

        this.xd *= (level.getRandom().nextDouble() * 8) + 0.5;
        this.yd *= (level.getRandom().nextDouble() * 2.5) + 0.5;
        this.zd *= (level.getRandom().nextDouble() * 8) + 0.5;

        this.xd += spreadDir.x;
        this.zd += spreadDir.z;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age++ >= this.lifetime) {
            this.remove();
            return;
        }


        this.yd = this.yd - 0.04 * (double)this.gravity;

        this.xd *= airResistFactor(this.age);
        this.yd *= airResistFactor(this.age);
        this.zd *= airResistFactor(this.age);

        this.move(this.xd, this.yd, this.zd);
        if (this.speedUpWhenYMotionIsBlocked && this.y == this.yo) {
            this.xd *= 1.1;
            this.zd *= 1.1;
        }

        this.xd = this.xd * (double)this.friction;
        this.zd = this.zd * (double)this.friction;
        if (this.onGround) {
            this.xd *= 0.7F;
            this.zd *= 0.7F;
        }
    }

    private static double airResistFactor(double age) {
        return Math.clamp(((-age) / 50) + 1, 0.2, 1.0);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public SingleQuadParticle.FacingCameraMode getFacingCameraMode() {
        // Use the random rotation we made at initialisation, but to make sure that the visible face is always
        // looking at the camera, flip it when it's looking the other way
        final Quaternionf FLIP = new Quaternionf().fromAxisAngleDeg(1, 0, 0, 180);
        return (quaternion, camera, partialTick) -> {
            Vector3f camForward = camera.getLookVector();
            Vector3f quadForward = new Vector3f(0, 0, 1).rotate(orientation);
            if (camForward.dot(quadForward) > 0) {
                quaternion.set(orientation.mul(FLIP));
            } else {
                quaternion.set(orientation);
            }
        };
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private SpriteSet sprites;
        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            ConfettiParticle p = new ConfettiParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
            Vector3f confettiColour = getRandomConfettiColour(level.getRandom());
            p.setColor(confettiColour.x, confettiColour.y, confettiColour.z);
            p.pickSprite(sprites);

            return p;
        }
    }

    public static Vector3f getRandomConfettiColour(RandomSource random) {
        int max = CONFETTI_COLOURS.length - 1;
        int index = random.nextIntBetweenInclusive(0, max);
        return CONFETTI_COLOURS[index];
    }

    public static void registerDispenseItemBehaviour() {
        DispenserBlock.registerBehavior(EIItems.PARTY_POPPER, new DispenserBehaviour());
    }

    public static class DispenserBehaviour implements DispenseItemBehavior {
        @Override
        @NotNull
        public ItemStack dispense(BlockSource bs, ItemStack item) {
            if (!item.is(EIItems.PARTY_POPPER)) {
                return item;
            }

            Vec3 center = bs.center();
            Direction facing = bs.state().getValue(BlockStateProperties.FACING);
            Vec3 direction = Vec3.atLowerCornerOf(bs.state().getValue(BlockStateProperties.FACING).getNormal());
            PartyPopperItem.playPopperSound(bs.level(), center.x, center.y, center.z, true);
            PartyPopperItem.spawnParticles(bs.level(), center.x, center.y, center.z, direction, 1);

            item.shrink(1);
            return item;
        }
    }
}
