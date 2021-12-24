package si.vilfa.junglechronicles.Gameplay;

import si.vilfa.junglechronicles.Audio.AudioEngine;
import si.vilfa.junglechronicles.Audio.SoundSequence;
import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Events.Event;
import si.vilfa.junglechronicles.Events.EventListener;
import si.vilfa.junglechronicles.Events.GameEvent;
import si.vilfa.junglechronicles.Events.PlayerEvent;
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
public class Game extends GameComponent implements EventListener
{
    private final PhysicsEngine physics;
    private final AudioEngine audio;
    private HumanPlayer player;
    private Level currentLevel;
    private final HashMap<GameProperty, Float> gameProperties;

    public Game()
    {
        super(0, true);

        physics = new PhysicsEngine();
        audio = new AudioEngine();

        LevelFactory levelFactory = LevelFactory.getInstance();
        currentLevel = levelFactory.createLevelFromTmx(this, "Levels/Level1.tmx");

        audio.newMusic("Audio/Tracks/theme.mp3",
                       GameEvent.GAMEPLAY_START,
                       GameEvent.GAMEPLAY_STOP);

        audio.newSoundSequence(new SoundSequence(new String[]{
                                       "Audio/Sounds/Footsteps/footstep_grass_000.ogg",
                                       "Audio/Sounds/Footsteps/footstep_grass_001.ogg",
                                       "Audio/Sounds/Footsteps/footstep_grass_002.ogg",
                                       "Audio/Sounds/Footsteps/footstep_grass_003.ogg",
                                       "Audio/Sounds/Footsteps/footstep_grass_004.ogg" }, 0.65f),
                               PlayerEvent.PLAYER_RUN,
                               PlayerEvent.PLAYER_IDLE);

        audio.newSound("Audio/Sounds/Coin/handleCoins.ogg",
                       GameEvent.PLAYER_COLLECTIBLE_CONTACT);

        audio.newSound("Audio/Sounds/Coin/handleCoins2.ogg",
                       GameEvent.PLAYER_COLLECTIBLE_CONTACT);

        audio.newSound("Audio/Sounds/Dead/jingles_NES00.ogg",
                       GameEvent.PLAYER_ENEMY_CONTACT,
                       GameEvent.PLAYER_TRAP_CONTACT);

        gameProperties = new HashMap<>();
        initializeGameProperties();
    }

    private void initializeGameProperties()
    {
        gameProperties.put(GameProperty.LEVEL_DURATION, 0f);
        gameProperties.put(GameProperty.PLAYER_HEALTH, 100f);
        gameProperties.put(GameProperty.PLAYER_LIVES, 3f);
        gameProperties.put(GameProperty.PLAYER_SCORE, 0f);
    }

    @Override
    public void handleEvent(Event event)
    {
        if (event.getType().equals(GameEvent.PLAYER_COLLECTIBLE_CONTACT)
            && event.getEventData().size > 0)
        {
            GameBlock object = (GameBlock) event.getEventData().first();
            gameProperties.compute(GameProperty.PLAYER_SCORE,
                                   (k, v) -> v += object.getCollectiblePoints());
            log("Score:" + gameProperties.get(GameProperty.PLAYER_SCORE));

            for (SceneTile tile : currentLevel.getTiles())
            {
                if (object.getFixtures().first().testPoint(tile.getCenter())
                    && tile.getSourceLayer().equals(Level.MapLayer.COLLECTIBLE_LAYER))
                {
                    currentLevel.removeItem(tile);
                }
            }
        } else if ((event.getType().equals(GameEvent.PLAYER_TRAP_CONTACT) || event.getType()
                                                                                  .equals(GameEvent.PLAYER_ENEMY_CONTACT))
                   && event.getEventData().size > 0)
        {
            float playerHealth = gameProperties.get(GameProperty.PLAYER_HEALTH);
            float playerLives = gameProperties.get(GameProperty.PLAYER_LIVES);

            if (event.getType().equals(GameEvent.PLAYER_TRAP_CONTACT))
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
            } else if (event.getType().equals(GameEvent.PLAYER_ENEMY_CONTACT))
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

            gameProperties.put(GameProperty.PLAYER_HEALTH, playerHealth);
            gameProperties.put(GameProperty.PLAYER_LIVES, playerLives);

            log("Health:" + playerHealth);
            log("Lives:" + playerLives);
        }

        if (gameProperties.get(GameProperty.PLAYER_LIVES) <= 0
            && gameProperties.get(GameProperty.PLAYER_HEALTH) <= 0)
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

    public HashMap<GameProperty, Float> getGameProperties()
    {
        return gameProperties;
    }

    @Override
    public void update()
    {
        if (!isUpdatable) return;
        gameProperties.compute(GameProperty.LEVEL_DURATION,
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

    public enum GameProperty
    {
        PLAYER_HEALTH, PLAYER_LIVES, PLAYER_SCORE, LEVEL_DURATION,
    }
}
