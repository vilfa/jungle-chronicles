package si.vilfa.junglechronicles.Gameplay;

import si.vilfa.junglechronicles.Audio.AudioEngine;
import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Events.Event;
import si.vilfa.junglechronicles.Events.EventListener;
import si.vilfa.junglechronicles.Graphics.Renderer;
import si.vilfa.junglechronicles.Level.GameStateEvent;
import si.vilfa.junglechronicles.Level.Level;
import si.vilfa.junglechronicles.Level.Objects.GameBlock;
import si.vilfa.junglechronicles.Level.Scene.SceneTile;
import si.vilfa.junglechronicles.Physics.PhysicsEngine;
import si.vilfa.junglechronicles.Player.Human.HumanPlayer;
import si.vilfa.junglechronicles.Utils.LevelFactory;

/**
 * @author luka
 * @date 28/11/2021
 * @package si.vilfa.junglechronicles.Gameplay
 **/
public class GameState extends GameComponent implements EventListener
{
    private final PhysicsEngine physics;
    private final AudioEngine audio;
    private HumanPlayer player;
    private Level currentLevel;
    private float currentLevelDuration;
    private int playerHealth;
    private int playerScore;

    public GameState()
    {
        super(0, true);

        physics = new PhysicsEngine();
        audio = new AudioEngine();

        LevelFactory levelFactory = LevelFactory.getInstance();
        currentLevel = levelFactory.createLevelFromTmx(this, "Levels/Level1.tmx");

        audio.newMusic("Audio/track.mp3",
                       GameStateEvent.GAMEPLAY_START,
                       GameStateEvent.GAMEPLAY_STOP);

        currentLevelDuration = 0f;
        playerHealth = 100;
        playerScore = 0;
    }

    @Override
    public void handleEvent(Event event)
    {
        log(event.toString());

        if (event.getType() == GameStateEvent.PLAYER_COLLECTIBLE_CONTACT)
        {
            if (event.getEventData().size > 0)
            {
                GameBlock object = (GameBlock) event.getEventData().get(0);
                playerScore += object.getCollectiblePoints();
                log("Score:" + playerScore);

                // TODO Use a HashMap or something for position lookup.
                for (SceneTile tile : currentLevel.getTiles())
                {
                    if (object.getBody().getFixtureList().get(0).testPoint(tile.getCenter()))
                    {
                        currentLevel.removeItem(tile);
                    }
                }
            }
        } else if (event.getType() == GameStateEvent.PLAYER_TRAP_CONTACT)
        {
            if (event.getEventData().size > 0)
            {
                GameBlock object = (GameBlock) event.getEventData().get(0);
                playerHealth -= object.getTrapPoints();
                log("Health:" + playerHealth);
            }
        } else if (event.getType() == GameStateEvent.PLAYER_ENEMY_CONTACT)
        {
            playerHealth -= 100;
            log("Health:" + playerHealth);
        }

        if (playerHealth <= 0)
        {
            log("YOU ARE DEAD!");
        }
    }

    public PhysicsEngine getPhysics()
    {
        return physics;
    }

    public AudioEngine getAudio()
    {
        return audio;
    }

    public HumanPlayer getPlayer()
    {
        return player;
    }

    public void setPlayer(HumanPlayer player)
    {
        this.player = player;
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

    public void reset()
    {
        currentLevelDuration = 0f;
        playerHealth = 100;
        playerScore = 0;
        log("Reset");
    }

    public void resetWithNewLevel(Level level)
    {
        reset();
        currentLevel = level;
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
        audio.dispose();
    }
}
