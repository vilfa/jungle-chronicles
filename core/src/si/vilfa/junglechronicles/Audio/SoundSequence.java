package si.vilfa.junglechronicles.Audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Graphics.Renderer;

import java.util.TreeMap;

/**
 * @author luka
 * @date 18/12/2021
 * @package si.vilfa.junglechronicles.Audio
 **/
public class SoundSequence extends GameComponent
{
    private final TreeMap<Integer, Sound> sequence;
    private final float frameDuration;
    private int currentState = 0;
    private boolean isPlaying = false;
    private float timer = 0f;
    private float volume = 1f;

    public SoundSequence(TreeMap<Integer, Sound> sequence, float frameDuration)
    {
        super(0, true);
        this.sequence = sequence;
        this.frameDuration = frameDuration;
    }

    public SoundSequence(Sound[] sounds, float frameDuration)
    {
        super(0, true);
        this.sequence = new TreeMap<>();
        this.frameDuration = frameDuration;

        for (int i = 0; i < sounds.length; i++)
        {
            sequence.put(i, sounds[i]);
        }
    }

    public SoundSequence(String[] files, float frameDuration)
    {
        super(0, true);
        this.sequence = new TreeMap<>();
        this.frameDuration = frameDuration;

        for (int i = 0; i < files.length; i++)
        {
            Sound sound = Gdx.audio.newSound(Gdx.files.internal(files[i]));
            sequence.put(i, sound);
        }
    }

    public void play()
    {
        log("Play:" + sequence.get(currentState));
        isPlaying = true;
    }

    public void stop()
    {
        sequence.values().forEach(Sound::stop);
        isPlaying = false;
    }

    public float getVolume()
    {
        return volume;
    }

    public void setVolume(float volume)
    {
        this.volume = volume;
    }

    public int getCurrentState()
    {
        return currentState;
    }

    @Override
    public void update()
    {
        if (isPlaying)
        {
            timer += Renderer.gameTime.getDeltaTime();
            if (timer > frameDuration)
            {
                timer = 0f;
                sequence.get(currentState).play(volume);
                currentState = (currentState + 1) % sequence.size();
            }
        }
    }

    @Override
    public void dispose()
    {
        sequence.values().forEach(Sound::dispose);
    }
}
