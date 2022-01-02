package si.vilfa.junglechronicles.Audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Events.*;
import si.vilfa.junglechronicles.Gameplay.Game;
import si.vilfa.junglechronicles.Input.Events.InputEventListener;
import si.vilfa.junglechronicles.Input.Events.KeyUpInputEvent;

import java.util.HashMap;

/**
 * @author luka
 * @date 15/12/2021
 * @package si.vilfa.junglechronicles.Audio
 **/
public class AudioEngine extends GameComponent implements EventListener, Music.OnCompletionListener,
                                                          InputEventListener
{
    private final Game game;

    private final HashMap<EventType, Array<Sound>> sounds;
    private final HashMap<EventType, Array<Music>> music;
    private final HashMap<EventType, Array<SoundSequence>> sequences;
    private float masterVolume = 1f;

    public AudioEngine(Game game)
    {
        super(0, true);
        this.game = game;
        sounds = new HashMap<>();
        music = new HashMap<>();
        sequences = new HashMap<>();
    }

    @Override
    public void update()
    {
        if (!isUpdatable || game.isPaused()) return;

        for (Array<SoundSequence> sequences : sequences.values())
        {
            for (SoundSequence sequence : sequences)
            {
                sequence.update();
            }
        }
    }

    @Override
    public void dispose()
    {
        sounds.forEach((k, v) -> v.forEach(Sound::dispose));
        music.forEach((k, v) -> v.forEach(Music::dispose));
        sequences.forEach((k, v) -> v.forEach(SoundSequence::dispose));
    }

    @Override
    public void handleEvent(Event event)
    {
        if (game.isPaused()) return;

        if (event.getType() instanceof GameEvent)
        {
            switch ((GameEvent) event.getType())
            {
                case GAMEPLAY_START:
                    sounds.getOrDefault(GameEvent.GAMEPLAY_START, new Array<>())
                          .forEach(v -> v.play(masterVolume * 0.5f));
                    music.getOrDefault(GameEvent.GAMEPLAY_START, new Array<>())
                         .forEach(Music::play);
                    sequences.getOrDefault(GameEvent.GAMEPLAY_START, new Array<>())
                             .forEach(SoundSequence::play);
                    break;
                case GAMEPLAY_STOP:
                    sounds.forEach((k, v) -> v.forEach(Sound::stop));
                    music.forEach((k, v) -> v.forEach(Music::stop));
                    sequences.forEach((k, v) -> v.forEach(SoundSequence::stop));
                    break;
                case PLAYER_ENEMY_CONTACT:
                    sounds.getOrDefault(GameEvent.PLAYER_ENEMY_CONTACT, new Array<>())
                          .forEach(v -> v.play(masterVolume * 0.5f));
                    break;
                case PLAYER_TRAP_CONTACT:
                    sounds.getOrDefault(GameEvent.PLAYER_TRAP_CONTACT, new Array<>())
                          .forEach(v -> v.play(masterVolume * 0.5f));
                    break;
                case PLAYER_COLLECTIBLE_CONTACT:
                    sounds.getOrDefault(GameEvent.PLAYER_COLLECTIBLE_CONTACT, new Array<>())
                          .forEach(v -> v.play(masterVolume * 0.5f));
                    break;
            }
        } else if (event.getType() instanceof PlayerEvent)
        {
            switch ((PlayerEvent) event.getType())
            {
                case PLAYER_RUN:
                    sequences.getOrDefault(PlayerEvent.PLAYER_RUN, new Array<>())
                             .forEach(SoundSequence::play);
                    break;
                case PLAYER_IDLE:
                    sequences.getOrDefault(PlayerEvent.PLAYER_IDLE, new Array<>())
                             .forEach(SoundSequence::stop);
                    break;
            }
        }
    }

    public void newSound(String internalFilePath, EventType... onEvents)
    {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal(internalFilePath));
        for (EventType event : onEvents)
        {
            if (sounds.containsKey(event))
            {
                sounds.get(event).add(sound);
            } else
            {
                sounds.put(event, new Array<>(new Sound[]{ sound }));
            }
        }

        setMasterVolume(1f);
    }

    public void newMusic(String internalFilePath, EventType... onEvents)
    {
        Music audioStream = Gdx.audio.newMusic(Gdx.files.internal(internalFilePath));
        audioStream.setLooping(true);
        for (EventType event : onEvents)
        {
            if (music.containsKey(event))
            {
                music.get(event).add(audioStream);
            } else
            {
                music.put(event, new Array<>(new Music[]{ audioStream }));
            }
        }

        setMasterVolume(1f);
    }

    public void newSoundSequence(SoundSequence sequence, EventType... onEvents)
    {
        for (EventType event : onEvents)
        {
            if (sequences.containsKey(event))
            {
                sequences.get(event).add(sequence);
            } else
            {
                sequences.put(event, new Array<>(new SoundSequence[]{ sequence }));
            }
        }

        setMasterVolume(1f);
    }

    public HashMap<EventType, Array<Sound>> getSounds()
    {
        return sounds;
    }

    public HashMap<EventType, Array<Music>> getMusic()
    {
        return music;
    }

    public float getMasterVolume()
    {
        return masterVolume;
    }

    public void setMasterVolume(float masterVolume)
    {
        this.masterVolume = Math.min(1f, Math.max(0f, masterVolume));
        music.forEach((k, v) -> v.forEach(vv -> vv.setVolume(this.masterVolume * 0.1f)));
        sequences.forEach((k, v) -> v.forEach(vv -> vv.setVolume(this.masterVolume)));
        log("master volume:" + this.masterVolume);
    }

    @Override
    public void onCompletion(Music music) { }

    @Override
    public void handleKeyUp(KeyUpInputEvent event)
    {
        switch (event.getKeyCode())
        {
            case Input.Keys.LEFT_BRACKET:
                setMasterVolume(getMasterVolume() - 0.05f);
                break;
            case Input.Keys.RIGHT_BRACKET:
                setMasterVolume(getMasterVolume() + 0.05f);
                break;
        }
    }
}
