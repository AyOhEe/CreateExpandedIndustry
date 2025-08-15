package io.github.ayohee.expandedindustry.content.blocks.loopingjukebox;

import io.github.ayohee.expandedindustry.mixin.blocks.loopingjukebox.JukeboxSongPlayerAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.JukeboxSongPlayer;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;

public class LoopingJukeboxSongPlayer extends JukeboxSongPlayer {
    public final int INITIALISE_DELAY_TICKS = 40;

    protected int initialiseDelay = 0;

    public LoopingJukeboxSongPlayer(OnSongChanged onSongChanged, BlockPos blockPos) {
        super(onSongChanged, blockPos);
    }

    @Override
    public void tick(LevelAccessor level, @Nullable BlockState state) {
        JukeboxSongPlayerAccessor asMixin = (JukeboxSongPlayerAccessor) this;

        if (this.getSong() != null) {
            if (this.getSong().hasFinished(this.getTicksSinceSongStarted())) {
                this.stop(level, state, true);
            } else {
                if (asMixin.invokeShouldEmitJukeboxPlayingEvent()) {
                    level.gameEvent(GameEvent.JUKEBOX_PLAY, asMixin.getBlockPos(), GameEvent.Context.of(state));
                    JukeboxSongPlayerAccessor.invokeSpawnMusicParticles(level, asMixin.getBlockPos());
                }

                asMixin.setTicksSinceSongStarted(this.getTicksSinceSongStarted() + 1);
            }

            if (initialiseDelay < INITIALISE_DELAY_TICKS) {
                initialiseDelay++;
            } else if (initialiseDelay == INITIALISE_DELAY_TICKS) {
                play(level, Holder.direct(getSong()));
                initialiseDelay++;
            }
        }
    }

    @Override
    public void stop(LevelAccessor level, @Nullable BlockState state) {
        this.stop(level, state, false);
    }

    public void stop(LevelAccessor level, @Nullable BlockState state, boolean replay) {
        JukeboxSongPlayerAccessor asMixin = (JukeboxSongPlayerAccessor) this;

        JukeboxSong song = this.getSong();
        if (song != null) {
            asMixin.setSong(null);
            asMixin.setTicksSinceSongStarted(0L);
            level.gameEvent(GameEvent.JUKEBOX_STOP_PLAY, asMixin.getBlockPos(), GameEvent.Context.of(state));
            level.levelEvent(1011, asMixin.getBlockPos(), 0);
            asMixin.getOnSongChanged().notifyChange();

            //FIXME consider adding a delay between loops?
            if (replay) {
                play(level, Holder.direct(song));
            }
        }
    }
}
