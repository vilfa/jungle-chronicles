package si.vilfa.junglechronicles.Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;
import si.vilfa.junglechronicles.Component.DrawableGameComponent;
import si.vilfa.junglechronicles.Config.Config;
import si.vilfa.junglechronicles.Events.Event;
import si.vilfa.junglechronicles.Events.EventListener;
import si.vilfa.junglechronicles.Events.GameEvent;
import si.vilfa.junglechronicles.Events.MenuEvent;
import si.vilfa.junglechronicles.Graphics.GameRenderer;
import si.vilfa.junglechronicles.Graphics.Gui.GameScreen;
import si.vilfa.junglechronicles.Graphics.Gui.GuiRenderer;
import si.vilfa.junglechronicles.Graphics.WindowAdapter;
import si.vilfa.junglechronicles.Input.Processors.PlayerInputProcessor;
import si.vilfa.junglechronicles.Input.Processors.UniversalInputProcessor;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Gameplay
 **/
public class Gameplay extends DrawableGameComponent
        implements Disposable, WindowAdapter, GameplayAdapter, EventListener
{
    private final Game game;
    private final GameRenderer gameRenderer;
    private final GuiRenderer guiRenderer;
    private final Box2DDebugRenderer debugRenderer;
    private final InputMultiplexer inputMultiplexer;
    private boolean initialResize = true;

    public Gameplay()
    {
        super(0, true, 0, true);
        game = new Game();
        gameRenderer = new GameRenderer(game);
        game.setGameRenderer(gameRenderer);
        guiRenderer = new GuiRenderer(game);
        game.setGuiRenderer(guiRenderer);
        debugRenderer = new Box2DDebugRenderer();
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new UniversalInputProcessor<>(game));
        inputMultiplexer.addProcessor(guiRenderer.getStage());
        inputMultiplexer.addProcessor(new PlayerInputProcessor(game.getPlayer()));
        Gdx.input.setInputProcessor(this.inputMultiplexer);


        this.registerEventListener(GameEvent.GAMEPLAY_START, game)
            .registerEventListener(GameEvent.GAMEPLAY_STOP, game);

        game.registerEventListener(GameEvent.PLAYER_HEALTH_CHANGE, guiRenderer.getHud())
            .registerEventListener(GameEvent.PLAYER_SCORE_CHANGE, guiRenderer.getHud())
            .registerEventListener(GameEvent.GAMEPLAY_RESET, guiRenderer.getHud())
            .registerEventListener(GameEvent.GAME_SCREEN_CHANGE, guiRenderer)
            .registerEventListener(GameEvent.GAMEPLAY_RESET, this)
            .registerEventListener(MenuEvent.RESOLUTION_BUTTON_CLICK, this)
            .registerEventListener(GameEvent.GAMEPLAY_START, game.getAudio())
            .registerEventListener(GameEvent.GAMEPLAY_STOP, game.getAudio())
            .registerEventListener(GameEvent.GAMEPLAY_PAUSE, game.getAudio())
            .registerEventListener(GameEvent.GAMEPLAY_RESUME, game.getAudio())
            .registerEventListener(GameEvent.GAME_LEADERBOARD_UPDATE,
                                   guiRenderer.getLeaderboardMenu())
            .registerEventListener(MenuEvent.RENDER_STATS_BUTTON_CLICK, guiRenderer);

        game.pushGameScreen(GameScreen.MAIN_MENU);
    }

    @Override
    public void handleEvent(Event event)
    {
        if (event.getType().equals(GameEvent.GAMEPLAY_RESET))
        {
            inputMultiplexer.removeProcessor(2);
            inputMultiplexer.addProcessor(new PlayerInputProcessor(game.getPlayer()));
        } else if (event.getType().equals(MenuEvent.RESOLUTION_BUTTON_CLICK) && event.getEventData()
                                                                                     .isEmpty())
        {
            int resolutionIndex = Config.RESOLUTIONS.indexOf(gameRenderer.getWindowResolution(),
                                                             false);
            int nextResolutionIndex = (resolutionIndex + 1) % Config.RESOLUTIONS.size;
            Config.Pair<Integer> resolution = Config.RESOLUTIONS.get(nextResolutionIndex);
            gameRenderer.setWindowResolution(resolution);
            game.getPreferences().setResolution(resolution);
            log("set window resolution:" + resolution);
        }
    }

    @Override
    public void draw()
    {
        if (!isDrawable) return;
        gameRenderer.draw();
        guiRenderer.draw();
        //        debugRenderer.render(game.getPhysics().getWorld(), gameRenderer.getCombined());
    }

    @Override
    public void dispose()
    {
        inputMultiplexer.clear();
        game.dispose();
        gameRenderer.dispose();
        guiRenderer.dispose();
        debugRenderer.dispose();
    }

    @Override
    public void update()
    {
        if (!isUpdatable) return;
        game.update();
        guiRenderer.update();
        gameRenderer.update();
    }

    @Override
    public void create()
    {
        log("create");
        dispatchEvent(GameEvent.GAMEPLAY_START);
    }

    @Override
    public void pause()
    {
        log("pause");
        dispatchEvent(GameEvent.GAMEPLAY_STOP);
    }

    @Override
    public void resume()
    {
        log("resume");
        dispatchEvent(GameEvent.GAMEPLAY_START);
    }

    @Override
    public void resize(int width, int height)
    {
        log("resize");
        gameRenderer.resize(width, height);
        guiRenderer.resize(width, height);
        if (initialResize)
        {
            initialResize = false;
            Config.Pair<Integer> resolution = game.getPreferences().getResolution();
            if (Config.RESOLUTIONS.contains(resolution, false))
            {
                setWindowResolution(resolution);
                log("set resolution from preferences:" + getWindowResolution());
            } else
            {
                setWindowResolution(Config.RESOLUTIONS.first());
            }
        }
    }

    @Override
    public float getScreenAspectRatio()
    {
        return gameRenderer.getScreenAspectRatio();
    }

    @Override
    public int getScreenRefreshRate()
    {
        return gameRenderer.getScreenRefreshRate();
    }

    @Override
    public Config.Pair<Integer> getWindowResolution()
    {
        return gameRenderer.getWindowResolution();
    }

    @Override
    public void setWindowResolution(Config.Pair<Integer> windowResolution)
    {
        gameRenderer.setWindowResolution(windowResolution);
    }
}
