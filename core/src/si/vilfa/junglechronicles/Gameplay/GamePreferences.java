package si.vilfa.junglechronicles.Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import si.vilfa.junglechronicles.Config.Config;

import java.util.Comparator;
import java.util.UUID;

/**
 * @author luka
 * @date 05/01/2022
 * @package si.vilfa.junglechronicles.Gameplay
 **/
public class GamePreferences
{
    private static final String PREFS_SETTINGS_NAME = "tjc-prefs";
    private static final String PREFS_LEADERBOARD_NAME = "tjc-leaderboard";
    private static final int LEADERBOARD_SIZE = 10;
    private final Preferences settingsPrefs;
    private final Preferences scorePrefs;
    private final Array<Integer> leaderboard;
    private boolean isSoundEnabled;
    private boolean isMusicEnabled;
    private Config.Pair<Integer> resolution;

    public GamePreferences()
    {
        settingsPrefs = Gdx.app.getPreferences(PREFS_SETTINGS_NAME);
        scorePrefs = Gdx.app.getPreferences(PREFS_LEADERBOARD_NAME);

        isSoundEnabled = settingsPrefs.getBoolean("isSoundEnabled", true);
        isMusicEnabled = settingsPrefs.getBoolean("isMusicEnabled", true);
        resolution = new Config.Pair<>(settingsPrefs.getInteger("resolutionX",
                                                                Config.RESOLUTIONS.first().one),
                                       settingsPrefs.getInteger("resolutionY",
                                                                Config.RESOLUTIONS.first().two));

        leaderboard = new Array<>();
        scorePrefs.get().forEach((k, v) -> leaderboard.add(Integer.valueOf((String) v)));
        scorePrefs.get().keySet().forEach(scorePrefs::remove);
        leaderboard.sort(Comparator.reverseOrder());
    }

    public void flush()
    {
        leaderboard.forEach((v) -> scorePrefs.putInteger(UUID.randomUUID().toString(), v));
        settingsPrefs.flush();
        scorePrefs.flush();
    }

    public boolean isSoundEnabled()
    {
        return isSoundEnabled;
    }

    public void setSoundEnabled(boolean soundEnabled)
    {
        isSoundEnabled = soundEnabled;
        settingsPrefs.putBoolean("isSoundEnabled", soundEnabled);
    }

    public boolean isMusicEnabled()
    {
        return isMusicEnabled;
    }

    public void setMusicEnabled(boolean musicEnabled)
    {
        isMusicEnabled = musicEnabled;
        settingsPrefs.putBoolean("isMusicEnabled", musicEnabled);
    }

    public Config.Pair<Integer> getResolution()
    {
        return resolution;
    }

    public void setResolution(Config.Pair<Integer> resolution)
    {
        this.resolution = resolution;
        settingsPrefs.putInteger("resolutionX", resolution.one);
        settingsPrefs.putInteger("resolutionY", resolution.two);
    }

    public Array<Integer> getLeaderboard()
    {
        return leaderboard;
    }

    public void addHighScore(int highScore)
    {
        leaderboard.add(highScore);
        leaderboard.sort(Comparator.reverseOrder());
        leaderboard.truncate(LEADERBOARD_SIZE);
    }
}
