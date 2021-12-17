package si.vilfa.junglechronicles.Audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Events.Event;
import si.vilfa.junglechronicles.Events.EventListener;
import si.vilfa.junglechronicles.Level.GameStateEvent;

import java.util.HashMap;

/**
 * @author luka
 * @date 15/12/2021
 * @package si.vilfa.junglechronicles.Audio
 **/
public class AudioEngine extends GameComponent implements EventListener, Music.OnCompletionListener
{
    private final HashMap<GameStateEvent, Array<Sound>> sounds;
    private final HashMap<GameStateEvent, Array<Music>> music;

    public AudioEngine()
    {
        super(0, true);
        sounds = new HashMap<>();
        music = new HashMap<>();
    }

    @Override
    public void update() { }

    @Override
    public void dispose()
    {
        sounds.values().forEach(soundCollection -> soundCollection.forEach(Sound::dispose));
        music.values().forEach(musicCollection -> musicCollection.forEach(Music::dispose));
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
                    music.get(GameStateEvent.GAMEPLAY_START).forEach(Music::play);
                    break;
                case GAMEPLAY_STOP:
                    music.get(GameStateEvent.GAMEPLAY_STOP).forEach(Music::pause);
                    break;
                case PLAYER_ENEMY_CONTACT:
                    break;
                case PLAYER_TRAP_CONTACT:
                    break;
                case PLAYER_COLLECTIBLE_CONTACT:
                    break;
            }
        }
    }

    public void newSound(String internalFilePath, GameStateEvent... onEvents)
    {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal(internalFilePath));
        for (GameStateEvent event : onEvents)
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

    public void newMusic(String internalFilePath, GameStateEvent... onEvents)
    {
        Music audioStream = Gdx.audio.newMusic(Gdx.files.internal(internalFilePath));
        audioStream.setLooping(true);
        for (GameStateEvent event : onEvents)
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

    public HashMap<GameStateEvent, Array<Sound>> getSounds()
    {
        return sounds;
    }

    public HashMap<GameStateEvent, Array<Music>> getMusic()
    {
        return music;
    }

    @Override
    public void onCompletion(Music music)
    {
    }
}
