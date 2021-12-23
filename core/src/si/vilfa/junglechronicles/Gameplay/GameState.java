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

        currentLevelDuration = 0f;
        playerHealth = 100;
        playerScore = 0;
    }

    @Override
    public void handleEvent(Event event)
    {
        log(event.toString());

        if (event.getType().equals(GameStateEvent.PLAYER_COLLECTIBLE_CONTACT)
            && event.getEventData().size > 0)
        {
            GameBlock object = (GameBlock) event.getEventData().first();
            playerScore += object.getCollectiblePoints();
            log("Score:" + playerScore);

            for (SceneTile tile : currentLevel.getTiles())
            {
                if (object.getFixtures().first().testPoint(tile.getCenter())
                    && tile.getSourceLayer().equals(Level.MapLayer.COLLECTIBLE_LAYER))
                {
                    currentLevel.removeItem(tile);
                }
            }
        } else if (event.getType().equals(GameStateEvent.PLAYER_TRAP_CONTACT)
                   && event.getEventData().size > 0)
        {
            GameBlock object = (GameBlock) event.getEventData().first();
            playerHealth -= object.getTrapPoints();
            log("Health:" + playerHealth);
        } else if (event.getType().equals(GameStateEvent.PLAYER_ENEMY_CONTACT))
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
        currentLevelDuration += GameRenderer.gameTime.getDeltaTime();
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
}
