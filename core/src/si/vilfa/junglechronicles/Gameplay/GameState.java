package si.vilfa.junglechronicles.Gameplay;

import com.badlogic.gdx.math.Vector2;
import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Graphics.Renderer;
import si.vilfa.junglechronicles.Physics.PhysicsEngine;
import si.vilfa.junglechronicles.Player.Human.HumanPlayer;
import si.vilfa.junglechronicles.Scene.Levels.Level;
import si.vilfa.junglechronicles.Utils.GameObjectFactory;
import si.vilfa.junglechronicles.Utils.LevelFactory;

/**
 * @author luka
 * @date 28/11/2021
 * @package si.vilfa.junglechronicles.Gameplay
 **/
public class GameState extends GameComponent implements StateChange
{
    private final GameObjectFactory gameObjectFactory;
    private final LevelFactory levelFactory;

    private Level currentLevel;
    private final PhysicsEngine physics;
    private HumanPlayer player;
    private float currentLevelDuration;
    private int playerHealth;
    private int playerScore;
    private boolean isPaused;

    public GameState()
    {
        super(0, true);
        physics = new PhysicsEngine();

        // TODO Move this so this class can only interact with the level factory.
        gameObjectFactory = GameObjectFactory.getInstance();
        levelFactory = LevelFactory.getInstance();

        currentLevel = levelFactory.createLevelFromTmx(this, "levels/Level1.tmx");

        player = (HumanPlayer) levelFactory.createPlayer(this, HumanPlayer.class, new Vector2(1f, 1f));

        currentLevelDuration = 0f;
        playerHealth = 100;
        playerScore = 0;
        isPaused = false;
    }

    public PhysicsEngine getPhysics()
    {
        return physics;
    }

    public HumanPlayer getPlayer()
    {
        return player;
    }

    public float getCurrentLevelDuration()
    {
        return currentLevelDuration;
    }

    public Level getCurrentLevel()
    {
        return currentLevel;
    }

    @Override
    public void notifyStateChange(Object object, boolean isActive)
    {
        physics.notifyStateChange(object, isActive);
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
        log("Player health:" + playerHealth);
    }

    public int getPlayerScore()
    {
        return playerScore;
    }

    public void setPlayerScore(int playerScore)
    {
        this.playerScore = playerScore;
        log("Player score:" + playerScore);
    }

    public boolean isPaused()
    {
        return isPaused;
    }

    public void setPaused(boolean paused)
    {
        isPaused = paused;
        log("Paused:" + paused);
    }

    public void reset()
    {
        currentLevelDuration = 0f;
        playerHealth = 100;
        playerScore = 0;
        isPaused = false;
        log("Reset");
    }

    public void resetWithNewLevel(Level level)
    {
        reset();
        currentLevel = level;
        log("Reset with new level");
    }

    @Override
    public void update()
    {
        if (!isUpdatable) return;
        currentLevelDuration += Renderer.gameTime.getDeltaTime();
        physics.update();
        player.update();
        currentLevel.update();
    }

    @Override
    public void dispose()
    {
        currentLevel.dispose();
        player.dispose();
        physics.dispose();
    }
}
