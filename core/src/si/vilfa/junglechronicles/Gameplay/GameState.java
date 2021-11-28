package si.vilfa.junglechronicles.Gameplay;

import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Graphics.Renderer;
import si.vilfa.junglechronicles.Scene.Levels.Level;

/**
 * @author luka
 * @date 28/11/2021
 * @package si.vilfa.junglechronicles.Gameplay
 **/
public class GameState extends GameComponent
{
    private Level currentLevel;
    private float currentLevelDuration;
    private int playerHealth;
    private int playerScore;
    private boolean isPaused;

    public GameState(Level currentLevel)
    {
        super(0, true);
        this.currentLevel = currentLevel;
        currentLevelDuration = 0f;
        playerHealth = 100;
        playerScore = 0;
        isPaused = false;
    }

    public float getCurrentLevelDuration()
    {
        return currentLevelDuration;
    }

    public Level getCurrentLevel()
    {
        return currentLevel;
    }

    public void setCurrentLevel(Level currentLevel)
    {
        this.currentLevel = currentLevel;
    }

    public int getPlayerHealth()
    {
        return playerHealth;
    }

    public void setPlayerHealth(int playerHealth)
    {
        this.playerHealth = playerHealth;
    }

    public int getPlayerScore()
    {
        return playerScore;
    }

    public void setPlayerScore(int playerScore)
    {
        this.playerScore = playerScore;
    }

    public boolean isPaused()
    {
        return isPaused;
    }

    public void setPaused(boolean paused)
    {
        isPaused = paused;
    }

    public void reset()
    {
        currentLevelDuration = 0f;
        playerHealth = 100;
        playerScore = 0;
        isPaused = false;
    }

    public void resetWithNewLevel(Level level)
    {
        reset();
        currentLevel = level;
    }

    @Override
    public void update()
    {
        currentLevelDuration += Renderer.gameTime.getDeltaTime();
    }

    @Override
    public void dispose()
    {
    }
}
