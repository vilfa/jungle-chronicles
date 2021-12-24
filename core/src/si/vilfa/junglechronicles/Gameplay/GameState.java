package si.vilfa.junglechronicles.Gameplay;

import si.vilfa.junglechronicles.Audio.AudioEngine;
import si.vilfa.junglechronicles.Audio.SoundSequence;
import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Events.Event;
import si.vilfa.junglechronicles.Events.EventListener;
import si.vilfa.junglechronicles.Events.GameStateEvent;
import si.vilfa.junglechronicles.Events.PlayerStateEvent;
import si.vilfa.junglechronicles.Graphics.GameRenderer;
import si.vilfa.junglechronicles.Level.Level;
import si.vilfa.junglechronicles.Level.Objects.GameBlock;
import si.vilfa.junglechronicles.Level.Scene.SceneTile;
import si.vilfa.junglechronicles.Physics.PhysicsEngine;
import si.vilfa.junglechronicles.Player.AI.Enemy;
import si.vilfa.junglechronicles.Player.Human.HumanPlayer;
import si.vilfa.junglechronicles.Utils.LevelFactory;

import java.util.HashMap;

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
    private final HashMap<GameStateProperty, Float> gameStateProperties;

    public GameState()
    {
        super(0, true);

        physics = new PhysicsEngine();
        audio = new AudioEngine();

        LevelFactory levelFactory = LevelFactory.getInstance();
        currentLevel = levelFactory.createLevelFromTmx(this, "Levels/Level1.tmx");

        audio.newMusic("Audio/Tracks/theme.mp3",
                       GameStateEvent.GAMEPLAY_START,
                       GameStateEvent.GAMEPLAY_STOP);

        audio.newSoundSequence(new SoundSequence(new String[]{
                                       "Audio/Sounds/Footsteps/footstep_grass_000.ogg",
                                       "Audio/Sounds/Footsteps/footstep_grass_001.ogg",
                                       "Audio/Sounds/Footsteps/footstep_grass_002.ogg",
                                       "Audio/Sounds/Footsteps/footstep_grass_003.ogg",
                                       "Audio/Sounds/Footsteps/footstep_grass_004.ogg" }, 0.65f),
                               PlayerStateEvent.PLAYER_RUN,
                               PlayerStateEvent.PLAYER_IDLE);

        audio.newSound("Audio/Sounds/Coin/handleCoins.ogg",
                       GameStateEvent.PLAYER_COLLECTIBLE_CONTACT);

        audio.newSound("Audio/Sounds/Coin/handleCoins2.ogg",
                       GameStateEvent.PLAYER_COLLECTIBLE_CONTACT);

        audio.newSound("Audio/Sounds/Dead/jingles_NES00.ogg",
                       GameStateEvent.PLAYER_ENEMY_CONTACT,
                       GameStateEvent.PLAYER_TRAP_CONTACT);

        gameStateProperties = new HashMap<>();
        initializeGameProperties();
    }

    private void initializeGameProperties()
    {
        gameStateProperties.put(GameStateProperty.LEVEL_DURATION, 0f);
        gameStateProperties.put(GameStateProperty.PLAYER_HEALTH, 100f);
        gameStateProperties.put(GameStateProperty.PLAYER_LIVES, 3f);
        gameStateProperties.put(GameStateProperty.PLAYER_SCORE, 0f);
    }

    @Override
    public void handleEvent(Event event)
    {
        if (event.getType().equals(GameStateEvent.PLAYER_COLLECTIBLE_CONTACT)
            && event.getEventData().size > 0)
        {
            GameBlock object = (GameBlock) event.getEventData().first();
            gameStateProperties.compute(GameStateProperty.PLAYER_SCORE,
                                        (k, v) -> v += object.getCollectiblePoints());
            log("Score:" + gameStateProperties.get(GameStateProperty.PLAYER_SCORE));

            for (SceneTile tile : currentLevel.getTiles())
            {
                if (object.getFixtures().first().testPoint(tile.getCenter())
                    && tile.getSourceLayer().equals(Level.MapLayer.COLLECTIBLE_LAYER))
                {
                    currentLevel.removeItem(tile);
                }
            }
        } else if ((event.getType().equals(GameStateEvent.PLAYER_TRAP_CONTACT) || event.getType()
                                                                                       .equals(GameStateEvent.PLAYER_ENEMY_CONTACT))
                   && event.getEventData().size > 0)
        {
            float playerHealth = gameStateProperties.get(GameStateProperty.PLAYER_HEALTH);
            float playerLives = gameStateProperties.get(GameStateProperty.PLAYER_LIVES);

            if (event.getType().equals(GameStateEvent.PLAYER_TRAP_CONTACT))
            {
                GameBlock object = (GameBlock) event.getEventData().first();

                if (playerHealth - object.getTrapPoints() <= 0)
                {
                    playerLives -= 1f;
                    playerHealth = 100f;
                } else
                {
                    playerHealth -= object.getTrapPoints();
                }
            } else if (event.getType().equals(GameStateEvent.PLAYER_ENEMY_CONTACT))
            {
                Enemy enemy = (Enemy) event.getEventData().first();

                // TODO: 24/12/2021 Maybe add different health point decrements for enemies
                if (playerHealth - 100f <= 0)
                {
                    playerLives -= 1f;
                    playerHealth = 100f;
                } else
                {
                    playerHealth -= 100f;
                }
            }

            gameStateProperties.put(GameStateProperty.PLAYER_HEALTH, playerHealth);
            gameStateProperties.put(GameStateProperty.PLAYER_LIVES, playerLives);

            log("Health:" + playerHealth);
            log("Lives:" + playerLives);
        }

        if (gameStateProperties.get(GameStateProperty.PLAYER_LIVES) <= 0
            && gameStateProperties.get(GameStateProperty.PLAYER_HEALTH) <= 0)
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

    public Level getCurrentLevel()
    {
        return currentLevel;
    }

    public void setCurrentLevel(Level currentLevel)
    {
        this.currentLevel = currentLevel;
    }

    public void reset()
    {
        initializeGameProperties();
        log("Reset");
    }

    public void reset(Level level)
    {
        reset();
        currentLevel = level;
    }

    public HashMap<GameStateProperty, Float> getGameStateProperties()
    {
        return gameStateProperties;
    }

    @Override
    public void update()
    {
        if (!isUpdatable) return;
        gameStateProperties.compute(GameStateProperty.LEVEL_DURATION,
                                    (k, v) -> v += GameRenderer.gameTime.getDeltaTime());
        physics.update();
        audio.update();
        player.update();
        currentLevel.update();
    }

    @Override
    public void dispose()
    {
        physics.dispose();
        audio.dispose();
        player.dispose();
        currentLevel.dispose();
    }

    public enum GameStateProperty
    {
        PLAYER_HEALTH, PLAYER_LIVES, PLAYER_SCORE, LEVEL_DURATION,
    }
}
