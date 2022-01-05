package si.vilfa.junglechronicles.Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import si.vilfa.junglechronicles.Config.Config;

/**
 * @author luka
 * @date 05/01/2022
 * @package si.vilfa.junglechronicles.Gameplay
 **/
public class GamePreferences
{
    private static final String PREFS_NAME = "tjc-prefs";
    private final Preferences preferences;

    private boolean isSoundEnabled;
    private boolean isMusicEnabled;
    private Config.Pair<Integer> resolution;

    public GamePreferences()
    {
        preferences = Gdx.app.getPreferences(PREFS_NAME);

        isSoundEnabled = preferences.getBoolean("isSoundEnabled", true);
        isMusicEnabled = preferences.getBoolean("isMusicEnabled", true);
        resolution = new Config.Pair<>(preferences.getInteger("resolutionX",
                                                              Config.RESOLUTIONS.first().one),
                                       preferences.getInteger("resolutionY",
                                                              Config.RESOLUTIONS.first().two));
    }

    public void flush()
    {
        preferences.flush();
    }

    public boolean isSoundEnabled()
    {
        return isSoundEnabled;
    }

    public void setSoundEnabled(boolean soundEnabled)
    {
        isSoundEnabled = soundEnabled;
        preferences.putBoolean("isSoundEnabled", soundEnabled);
    }

    public boolean isMusicEnabled()
    {
        return isMusicEnabled;
    }

    public void setMusicEnabled(boolean musicEnabled)
    {
        isMusicEnabled = musicEnabled;
        preferences.putBoolean("isMusicEnabled", musicEnabled);
    }

    public Config.Pair<Integer> getResolution()
    {
        return resolution;
    }

    public void setResolution(Config.Pair<Integer> resolution)
    {
        this.resolution = resolution;
        preferences.putInteger("resolutionX", resolution.one);
        preferences.putInteger("resolutionY", resolution.two);
    }
}
