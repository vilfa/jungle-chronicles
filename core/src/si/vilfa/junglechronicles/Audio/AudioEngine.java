package si.vilfa.junglechronicles.Audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Events.*;

import java.util.HashMap;

/**
 * @author luka
 * @date 15/12/2021
 * @package si.vilfa.junglechronicles.Audio
 **/
public class AudioEngine extends GameComponent implements EventListener, Music.OnCompletionListener
{
    private final HashMap<EventType, Array<Sound>> sounds;
    private final HashMap<EventType, Array<Music>> music;
    private final HashMap<EventType, Array<SoundSequence>> soundSequences;

    public AudioEngine()
    {
        super(0, true);
        sounds = new HashMap<>();
        music = new HashMap<>();
        soundSequences = new HashMap<>();
    }

    @Override
    public void update()
    {
        for (Array<SoundSequence> sequences : soundSequences.values())
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
        sounds.values().forEach(soundCollection -> soundCollection.forEach(Sound::dispose));
        music.values().forEach(musicCollection -> musicCollection.forEach(Music::dispose));
        soundSequences.values()
                      .forEach(soundSequencesCollection -> soundSequencesCollection.forEach(
                              SoundSequence::dispose));
    }

    @Override
    public void handleEvent(Event event)
    {
        log("Event:" + event);

        if (event.getType() instanceof GameStateEvent)
        {
            switch ((GameStateEvent) event.getType())
            {
                case GAMEPLAY_START:
                    sounds.getOrDefault(GameStateEvent.GAMEPLAY_START, new Array<>())
                          .forEach(Sound::play);
                    music.getOrDefault(GameStateEvent.GAMEPLAY_START, new Array<>())
                         .forEach(Music::play);
                    soundSequences.getOrDefault(GameStateEvent.GAMEPLAY_START, new Array<>())
                                  .forEach(SoundSequence::play);
                    break;
                case GAMEPLAY_STOP:
                    sounds.getOrDefault(GameStateEvent.GAMEPLAY_STOP, new Array<>())
                          .forEach(Sound::play);
                    music.getOrDefault(GameStateEvent.GAMEPLAY_STOP, new Array<>())
                         .forEach(Music::pause);
                    soundSequences.getOrDefault(GameStateEvent.GAMEPLAY_START, new Array<>())
                                  .forEach(SoundSequence::play);
                    break;
                case PLAYER_ENEMY_CONTACT:
                    break;
                case PLAYER_TRAP_CONTACT:
                    break;
                case PLAYER_COLLECTIBLE_CONTACT:
                    break;
            }
        } else if (event.getType() instanceof PlayerEvent)
        {
            switch ((PlayerEvent) event.getType())
            {
                case PLAYER_RUN:
                    soundSequences.getOrDefault(PlayerEvent.PLAYER_RUN, new Array<>())
                                  .forEach(SoundSequence::play);
                    break;
                case PLAYER_IDLE:
                    soundSequences.getOrDefault(PlayerEvent.PLAYER_IDLE, new Array<>())
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
    }

    public void newSoundSequence(SoundSequence sequence, EventType... onEvents)
    {
        for (EventType event : onEvents)
        {
            if (soundSequences.containsKey(event))
            {
                soundSequences.get(event).add(sequence);
            } else
            {
                soundSequences.put(event, new Array<>(new SoundSequence[]{ sequence }));
            }
        }
    }

    public HashMap<EventType, Array<Sound>> getSounds()
    {
        return sounds;
    }

    public HashMap<EventType, Array<Music>> getMusic()
    {
        return music;
    }

    @Override
    public void onCompletion(Music music)
    {
    }
}
