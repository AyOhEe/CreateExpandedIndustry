package io.github.ayohee.expandedindustry.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class EIUtil {
    // Why oh why I can't do this, I don't know.
    public static <T extends ParticleOptions> int sendParticlesForced(
            ServerLevel level, T type, double posX, double posY, double posZ, int particleCount, double xOffset, double yOffset, double zOffset, double speed
    ) {
        ClientboundLevelParticlesPacket clientboundlevelparticlespacket = new ClientboundLevelParticlesPacket(
                type, true, posX, posY, posZ, (float)xOffset, (float)yOffset, (float)zOffset, (float)speed, particleCount
        );
        int i = 0;

        for (int j = 0; j < level.players().size(); j++) {
            ServerPlayer serverplayer = level.players().get(j);
            if (sendParticles(level, serverplayer, false, posX, posY, posZ, clientboundlevelparticlespacket)) {
                i++;
            }
        }

        return i;
    }

    private static boolean sendParticles(ServerLevel level, ServerPlayer player, boolean longDistance, double posX, double posY, double posZ, Packet<?> packet) {
        if (player.level() != level) {
            return false;
        } else {
            BlockPos blockpos = player.blockPosition();
            if (blockpos.closerToCenterThan(new Vec3(posX, posY, posZ), longDistance ? 512.0 : 32.0)) {
                player.connection.send(packet);
                return true;
            } else {
                return false;
            }
        }
    }
}
