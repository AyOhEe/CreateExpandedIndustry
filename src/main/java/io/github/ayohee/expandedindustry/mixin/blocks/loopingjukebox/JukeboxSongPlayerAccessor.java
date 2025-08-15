package io.github.ayohee.expandedindustry.mixin.blocks.loopingjukebox;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.JukeboxSongPlayer;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(JukeboxSongPlayer.class)
public interface JukeboxSongPlayerAccessor {
    @Accessor("ticksSinceSongStarted")
    void setTicksSinceSongStarted(long value);

    @Accessor("song")
    void setSong(Holder<JukeboxSong> song);

    @Accessor("blockPos")
    BlockPos getBlockPos();

    @Accessor("onSongChanged")
    JukeboxSongPlayer.OnSongChanged getOnSongChanged();

    @Invoker("shouldEmitJukeboxPlayingEvent")
    boolean invokeShouldEmitJukeboxPlayingEvent();

    @Invoker("spawnMusicParticles")
    static void invokeSpawnMusicParticles(LevelAccessor level, BlockPos pos) {
        throw new AssertionError();
    }
}