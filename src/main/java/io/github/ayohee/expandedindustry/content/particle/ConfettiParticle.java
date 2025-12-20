package io.github.ayohee.expandedindustry.content.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
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

    protected ConfettiParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        gravity = 0.5f;
        this.lifetime = (int)(75.0F / (this.random.nextFloat() * 2.0F + 0.5F)) + 1;
        setAlpha(1.0f);
        quadSize *= 0.5f;


        double spreadFactor = (level.getRandom().nextDouble() * 2 - 1);
        Vec3 spreadDir = new Vec3(this.xd, 0, this.zd).normalize();
        spreadDir = spreadDir.yRot(Mth.HALF_PI);
        spreadDir = spreadDir.scale(spreadFactor * 0.3);

        this.xd *= (level.getRandom().nextDouble() * 8) + 0.5;
        this.yd *=  (level.getRandom().nextDouble() * 2.5) + 0.5;
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

    //TODO replace with custom camera mode
    public SingleQuadParticle.FacingCameraMode getFacingCameraMode() {
        return FacingCameraMode.LOOKAT_XYZ;
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
}
