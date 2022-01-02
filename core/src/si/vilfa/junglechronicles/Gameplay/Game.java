package si.vilfa.junglechronicles.Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import si.vilfa.junglechronicles.Audio.AudioEngine;
import si.vilfa.junglechronicles.Audio.SoundSequence;
import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Events.*;
import si.vilfa.junglechronicles.Graphics.GameRenderer;
import si.vilfa.junglechronicles.Graphics.Gui.GameScreen;
import si.vilfa.junglechronicles.Input.Events.InputEventListener;
import si.vilfa.junglechronicles.Input.Events.KeyUpInputEvent;
import si.vilfa.junglechronicles.Level.Level;
import si.vilfa.junglechronicles.Level.Objects.GameBlock;
import si.vilfa.junglechronicles.Level.Scene.SceneTile;
import si.vilfa.junglechronicles.Physics.PhysicsEngine;
import si.vilfa.junglechronicles.Player.Human.HumanPlayer;
import si.vilfa.junglechronicles.Utils.LevelFactory;

import java.util.HashMap;
import java.util.Stack;

/**
 * @author luka
 * @date 28/11/2021
 * @package si.vilfa.junglechronicles.Gameplay
 **/
public class Game extends GameComponent implements EventListener, InputEventListener
{
    private final Stack<GameScreen> gameScreens;
    private final HashMap<GameProperty, Float> gameProperties;
    private PhysicsEngine physics;
    private AudioEngine audio;
    private HumanPlayer player;
    private Level currentLevel;

    private boolean isPaused = true;

    public Game()
    {
        super(0, true);

        physics = new PhysicsEngine(this);
        audio = new AudioEngine(this);

        currentLevel = LevelFactory.getInstance().createLevelFromTmx(this, "Levels/Level1.tmx");

        audio.newMusic("Audio/Tracks/theme.mp3", GameEvent.GAMEPLAY_START, GameEvent.GAMEPLAY_STOP);

        audio.newSoundSequence(new SoundSequence(new String[]{
                                       "Audio/Sounds/Footsteps/footstep_grass_000.ogg",
                                       "Audio/Sounds/Footsteps/footstep_grass_001.ogg",
                                       "Audio/Sounds/Footsteps/footstep_grass_002.ogg",
                                       "Audio/Sounds/Footsteps/footstep_grass_003.ogg",
                                       "Audio/Sounds/Footsteps/footstep_grass_004.ogg" }, 0.65f),
                               PlayerEvent.PLAYER_RUN,
                               PlayerEvent.PLAYER_IDLE);

        audio.newSound("Audio/Sounds/Coin/handleCoins.ogg", GameEvent.PLAYER_COLLECTIBLE_CONTACT);
        audio.newSound("Audio/Sounds/Coin/handleCoins2.ogg", GameEvent.PLAYER_COLLECTIBLE_CONTACT);
        audio.newSound("Audio/Sounds/Dead/jingles_NES00.ogg",
                       GameEvent.PLAYER_ENEMY_CONTACT,
                       GameEvent.PLAYER_TRAP_CONTACT);

        gameProperties = new HashMap<>();
        initializeGameProperties();

        gameScreens = new Stack<>();
    }

    private void initializeGameProperties()
    {
        gameProperties.put(GameProperty.PLAYER_HEALTH, (float) HumanPlayer.MAX_HEALTH);
        gameProperties.put(GameProperty.PLAYER_LIVES, (float) HumanPlayer.MAX_LIVES);
        gameProperties.put(GameProperty.PLAYER_SCORE, 0f);
        gameProperties.put(GameProperty.LEVEL_DURATION, 0f);
    }

    private void handleGameEvent(Event event)
    {
        if (!(event.getType() instanceof GameEvent)) return;

        log("game event:" + event);

        if (event.getType().equals(GameEvent.PLAYER_COLLECTIBLE_CONTACT)
            && event.getEventData().size == 1)
        {
            GameBlock object = (GameBlock) event.getEventData().first();
            gameProperties.compute(GameProperty.PLAYER_SCORE,
                                   (k, v) -> v += object.getCollectiblePoints());

            for (SceneTile tile : currentLevel.getTiles())
            {
                if (object.getFixtures().first().testPoint(tile.getCenter())
                    && tile.getSourceLayer().equals(Level.MapLayer.COLLECTIBLE_LAYER))
                {
                    currentLevel.removeItem(tile);
                }
            }

            log("score:" + gameProperties.get(GameProperty.PLAYER_SCORE));

            dispatchEvent(GameEvent.PLAYER_SCORE_CHANGE,
                          gameProperties.get(GameProperty.PLAYER_SCORE));
        } else if ((event.getType().equals(GameEvent.PLAYER_TRAP_CONTACT) || event.getType()
                                                                                  .equals(GameEvent.PLAYER_ENEMY_CONTACT))
                   && event.getEventData().size == 1)
        {
            float playerHealth = gameProperties.get(GameProperty.PLAYER_HEALTH);
            float playerLives = gameProperties.get(GameProperty.PLAYER_LIVES);

            if (event.getType().equals(GameEvent.PLAYER_TRAP_CONTACT))
            {
                GameBlock object = (GameBlock) event.getEventData().first();

                if (playerHealth - object.getTrapPoints() <= 0f)
                {
                    playerLives -= 1f;
                    playerHealth = 100f;
                } else
                {
                    playerHealth -= object.getTrapPoints();
                }
            } else if (event.getType().equals(GameEvent.PLAYER_ENEMY_CONTACT))
            {
//                Enemy enemy = (Enemy) event.getEventData().first();

                // TODO: 24/12/2021 Maybe add different health point decrements for enemies
                if (playerHealth - 100f <= 0f && playerLives == 0f)
                {
                    playerHealth = 0f;
                } else if (playerHealth - 100f <= 0f)
                {
                    playerLives -= 1f;
                    playerHealth = 100f;
                } else
                {
                    playerHealth -= 100f;
                }
            }

            gameProperties.put(GameProperty.PLAYER_LIVES, playerLives);
            gameProperties.put(GameProperty.PLAYER_HEALTH, playerHealth);

            log("lives:" + playerLives);
            log("health:" + playerHealth);

            dispatchEvent(GameEvent.PLAYER_HEALTH_CHANGE,
                          gameProperties.get(GameProperty.PLAYER_LIVES),
                          gameProperties.get(GameProperty.PLAYER_HEALTH));
        }

        if (gameProperties.get(GameProperty.PLAYER_LIVES) == 0f
            && gameProperties.get(GameProperty.PLAYER_HEALTH) == 0f)
        {
            log("YOU ARE DEAD!");
        }
    }

    private void handleMenuEvent(Event event)
    {
        if (!(event.getType() instanceof MenuEvent)) return;

        log("menu event:" + event);

        if (event.getType().equals(MenuEvent.PLAY_BUTTON_CLICK))
        {
            pushGameScreen(GameScreen.IN_GAME);
            resume();
        } else if (event.getType().equals(MenuEvent.RESUME_BUTTON_CLICK))
        {
            popGameScreen(GameScreen.PAUSE_MENU);
            resume();
        } else if (event.getType().equals(MenuEvent.OPTIONS_BUTTON_CLICK))
        {
            pushGameScreen(GameScreen.OPTIONS_MENU);
        } else if (event.getType().equals(MenuEvent.EXIT_BUTTON_CLICK))
        {
            if (gameScreens.peek() == GameScreen.MAIN_MENU)
            {
                exit();
            } else if (gameScreens.peek() == GameScreen.PAUSE_MENU)
            {
                popGameScreen(GameScreen.PAUSE_MENU);
                popGameScreen(GameScreen.IN_GAME);
                reset();
            }
        }
    }

    @Override
    public void handleEvent(Event event)
    {
        handleGameEvent(event);
        handleMenuEvent(event);
    }

    private void pause()
    {
        log("pause");
        isPaused = true;
        pushGameScreen(GameScreen.PAUSE_MENU);
    }

    private void resume()
    {
        log("resume");
        isPaused = false;
    }

    private void exit()
    {
        log("exit");
        Gdx.app.exit();
    }

    public boolean isPaused()
    {
        return isPaused;
    }

    private void reset()
    {
        isPaused = true;
        log("reset");

        currentLevel.dispose();
        player.dispose();
        physics.dispose();

        physics = new PhysicsEngine(this);
        currentLevel = LevelFactory.getInstance().createLevelFromTmx(this, "Levels/Level1.tmx");

        initializeGameProperties();
        dispatchEvent(GameEvent.GAMEPLAY_RESET);
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

    public float getCurrentLevelDuration()
    {
        return gameProperties.get(GameProperty.LEVEL_DURATION);
    }

    public HashMap<GameProperty, Float> getGameProperties()
    {
        return gameProperties;
    }

    @Override
    public void update()
    {
        if (!isUpdatable || isPaused) return;
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

    @Override
    public void handleKeyUp(KeyUpInputEvent event)
    {
        if (event.getKeyCode() == Input.Keys.ESCAPE)
        {
            pause();
        }
    }

    public void pushGameScreen(GameScreen gameScreen)
    {
        if (gameScreens.isEmpty() || gameScreens.peek() != gameScreen)
        {
            gameScreens.push(gameScreen);
            dispatchEvent(GameEvent.GAME_SCREEN_CHANGE, gameScreens.peek());
        }
        log(gameScreens.toString());
    }

    public void popGameScreen(GameScreen gameScreen)
    {
        if (gameScreens.peek() == gameScreen)
        {
            gameScreens.pop();
            dispatchEvent(GameEvent.GAME_SCREEN_CHANGE, gameScreens.peek());
        }
        log(gameScreens.toString());
    }

    public enum GameProperty
    {
        PLAYER_HEALTH, PLAYER_LIVES, PLAYER_SCORE, LEVEL_DURATION,
    }
}
