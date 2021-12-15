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
    public void update()
    {
    }

    @Override
    public void dispose()
    {
        for (Array<Sound> arr : sounds.values())
        {
            for (Sound s : arr)
            {
                s.dispose();
            }
        }

        for (Array<Music> arr : music.values())
        {
            for (Music m : arr)
            {
                m.dispose();
            }
        }
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
                    break;
                case GAMEPLAY_STOP:
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

    public void newSound(GameStateEvent onEvent, String internalFilePath)
    {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal(internalFilePath));
        if (sounds.containsKey(onEvent))
        {
            sounds.get(onEvent).add(sound);
        } else
        {
            sounds.put(onEvent, new Array<>(new Sound[]{ sound }));
        }
    }

    public void newMusic(GameStateEvent onEvent, String internalFilePath)
    {
        Music audioStream = Gdx.audio.newMusic(Gdx.files.internal(internalFilePath));
        audioStream.setLooping(true);
        if (music.containsKey(onEvent))
        {
            music.get(onEvent).add(audioStream);
        } else
        {
            music.put(onEvent, new Array<>(new Music[]{ audioStream }));
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
        log("Looping audio stream:" + music);
    }
}
