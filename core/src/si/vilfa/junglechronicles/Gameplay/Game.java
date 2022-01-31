package si.vilfa.junglechronicles.Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import si.vilfa.junglechronicles.Audio.AudioEngine;
import si.vilfa.junglechronicles.Audio.SoundSequence;
import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Events.*;
import si.vilfa.junglechronicles.Graphics.GameRenderer;
import si.vilfa.junglechronicles.Graphics.Gui.GameScreen;
import si.vilfa.junglechronicles.Graphics.Gui.GuiRenderer;
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
    private final AudioEngine audio;
    private final GamePreferences preferences;
    private PhysicsEngine physics;
    private HumanPlayer player;
    private Level currentLevel;

    private GameRenderer gameRenderer;
    private GuiRenderer guiRenderer;

    private boolean isPaused = true;
    private boolean isGameEnd = false;
    private int currentLevelOrdinal = 1;

    public Game()
    {
        super(0, true);

        physics = new PhysicsEngine(this);
        audio = new AudioEngine(this);
        preferences = new GamePreferences();

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
        initializeGamePreferences();
    }

    private void initializeGameProperties()
    {
        gameProperties.put(GameProperty.PLAYER_HEALTH, (float) HumanPlayer.MAX_HEALTH);
        gameProperties.put(GameProperty.PLAYER_LIVES, (float) HumanPlayer.MAX_LIVES);
        gameProperties.put(GameProperty.PLAYER_SCORE, 0f);
        gameProperties.put(GameProperty.LEVEL_DURATION, 0f);
    }

    private void initializeGamePreferences()
    {
        audio.setSoundVolume((preferences.isSoundEnabled()) ? 1f : 0f);
        audio.setMusicVolume((preferences.isMusicEnabled()) ? 1f : 0f);
    }

    private void handleGameEvent(Event event)
    {
        if (!(event.getType() instanceof GameEvent)) return;

        log("game event:" + event);

        switch ((GameEvent) event.getType())
        {
            case PLAYER_COLLECTIBLE_CONTACT:
                if (event.getEventData().size == 1)
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
                }
                break;
            case PLAYER_TRAP_CONTACT:
            case PLAYER_ENEMY_CONTACT:
                if (event.getEventData().size == 1)
                {
                    float playerHealth = gameProperties.get(GameProperty.PLAYER_HEALTH);
                    float playerLives = gameProperties.get(GameProperty.PLAYER_LIVES);

                    if (event.getType().equals(GameEvent.PLAYER_TRAP_CONTACT))
                    {
                        GameBlock object = (GameBlock) event.getEventData().first();

                        if (playerHealth - object.getTrapPoints() <= 0f && playerLives == 0f)
                        {
                            playerHealth = 0f;
                        } else if (playerHealth - object.getTrapPoints() <= 0f)
                        {
                            playerLives -= 1f;
                            playerHealth = 100f;
                        } else
                        {
                            playerHealth -= object.getTrapPoints();
                        }
                    } else if (event.getType().equals(GameEvent.PLAYER_ENEMY_CONTACT))
                    {
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
                break;
            case PLAYER_LEVEL_END_CONTACT:
                log("FINISHED LEVEL!");
                log("level score:" + getPlayerScore());
                log("level duration:" + getCurrentLevelDuration());
                preferences.addHighScore(getPlayerScore());
                dispatchEvent(GameEvent.GAME_LEADERBOARD_UPDATE);
                dispatchEvent(GameEvent.GAME_END);
                dispatchEvent(GameEvent.GAME_SCREEN_CHANGE, GameScreen.GAME_END, false);
                isGameEnd = true;
                break;
            case GAMEPLAY_START:
            case GAMEPLAY_STOP:
                log("dispatch event:" + event.getType());
                dispatchEvent(event.getType());
        }

        if (gameProperties.get(GameProperty.PLAYER_LIVES) == 0f
            && gameProperties.get(GameProperty.PLAYER_HEALTH) == 0f)
        {
            log("PLAYER DIED!");
            log("level score:" + getPlayerScore());
            log("level duration:" + getCurrentLevelDuration());
            preferences.addHighScore(getPlayerScore());
            dispatchEvent(GameEvent.GAME_LEADERBOARD_UPDATE);
            dispatchEvent(GameEvent.GAME_END);
            dispatchEvent(GameEvent.GAME_SCREEN_CHANGE, GameScreen.GAME_END, true);
            isGameEnd = true;
        }
    }

    private void handleMenuEvent(Event event)
    {
        if (!(event.getType() instanceof MenuEvent)) return;

        log("menu event:" + event);

        switch ((MenuEvent) event.getType())
        {
            case PLAY_BUTTON_CLICK:
                pushGameScreen(GameScreen.IN_GAME);
                resume();
                break;
            case LEVELS_BUTTON_CLICK:
                pushGameScreen(GameScreen.LEVELS_MENU);
                break;
            case LEVEL_CHANGE_BUTTON_CLICK:
                if (event.getEventData().size == 1)
                {
                    log("change level to:" + event.getEventData().first());
                    resetWithLevel((int) event.getEventData().first());
                    gameScreens.clear();
                    pushGameScreen(GameScreen.MAIN_MENU);
                    pushGameScreen(GameScreen.IN_GAME);
                    resume();
                }
                break;
            case MAIN_MENU_BUTTON_CLICK:
                reset();
                gameScreens.clear();
                pushGameScreen(GameScreen.MAIN_MENU);
                break;
            case REPLAY_BUTTON_CLICK:
                reset();
                gameScreens.clear();
                pushGameScreen(GameScreen.MAIN_MENU);
                pushGameScreen(GameScreen.IN_GAME);
                resume();
                break;
            case RESUME_BUTTON_CLICK:
                popGameScreen(GameScreen.PAUSE_MENU);
                resume();
                break;
            case OPTIONS_BUTTON_CLICK:
                pushGameScreen(GameScreen.OPTIONS_MENU);
                break;
            case LEADERBOARD_BUTTON_CLICK:
                pushGameScreen(GameScreen.LEADERBOARD_MENU);
                break;
            case EXIT_BUTTON_CLICK:
                if (gameScreens.peek() == GameScreen.MAIN_MENU)
                {
                    exit();
                } else if (gameScreens.peek() == GameScreen.PAUSE_MENU)
                {
                    popGameScreen(GameScreen.PAUSE_MENU);
                    popGameScreen(GameScreen.IN_GAME);
                    reset();
                }
                break;
            case RESOLUTION_BUTTON_CLICK:
                dispatchEvent(MenuEvent.RESOLUTION_BUTTON_CLICK);
                break;
            case RENDER_STATS_BUTTON_CLICK:
                dispatchEvent(MenuEvent.RENDER_STATS_BUTTON_CLICK);
                break;
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
        if (gameScreens.peek() == GameScreen.IN_GAME)
        {
            pushGameScreen(GameScreen.PAUSE_MENU);
        }
        dispatchEvent(GameEvent.GAMEPLAY_PAUSE);
    }

    private void resume()
    {
        log("resume");
        isPaused = false;
        dispatchEvent(GameEvent.GAMEPLAY_RESUME);
    }

    private void exit()
    {
        log("flush preferences");
        preferences.flush();
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
        currentLevel = LevelFactory.getInstance()
                                   .createLevelFromTmx(this,
                                                       "Levels/Level" + currentLevelOrdinal
                                                       + ".tmx");

        currentLevel.getBackgrounds().first().setViewport(gameRenderer.getViewport());

        initializeGameProperties();
        dispatchEvent(GameEvent.GAMEPLAY_RESET);
        isGameEnd = false;
    }

    private void resetWithLevel(int level)
    {
        currentLevelOrdinal = level;
        isPaused = true;
        log("reset with level:" + level);

        currentLevel.dispose();
        player.dispose();
        physics.dispose();

        physics = new PhysicsEngine(this);
        currentLevel = LevelFactory.getInstance()
                                   .createLevelFromTmx(this,
                                                       "Levels/Level" + currentLevelOrdinal
                                                       + ".tmx");

        currentLevel.getBackgrounds().first().setViewport(gameRenderer.getViewport());

        initializeGameProperties();
        dispatchEvent(GameEvent.GAMEPLAY_RESET);
        isGameEnd = false;
    }

    public GameRenderer getGameRenderer()
    {
        return gameRenderer;
    }

    public void setGameRenderer(GameRenderer gameRenderer)
    {
        this.gameRenderer = gameRenderer;
    }

    public GuiRenderer getGuiRenderer()
    {
        return guiRenderer;
    }

    public void setGuiRenderer(GuiRenderer guiRenderer)
    {
        this.guiRenderer = guiRenderer;
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

    public int getPlayerScore()
    {
        return gameProperties.get(GameProperty.PLAYER_SCORE).intValue();
    }

    public HashMap<GameProperty, Float> getGameProperties()
    {
        return gameProperties;
    }

    public GamePreferences getPreferences()
    {
        return preferences;
    }

    public GameScreen getGameScreen()
    {
        return gameScreens.peek();
    }

    @Override
    public void update()
    {
        if (!isUpdatable || isPaused || isGameEnd) return;
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
            if (isPaused && gameScreens.peek() != GameScreen.PAUSE_MENU
                && gameScreens.peek() != GameScreen.MAIN_MENU)
            {
                popGameScreen(gameScreens.peek());
            } else
            {
                pause();
            }
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
