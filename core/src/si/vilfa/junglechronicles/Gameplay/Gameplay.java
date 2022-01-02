package si.vilfa.junglechronicles.Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;
import si.vilfa.junglechronicles.Component.DrawableGameComponent;
import si.vilfa.junglechronicles.Events.Event;
import si.vilfa.junglechronicles.Events.EventListener;
import si.vilfa.junglechronicles.Events.GameEvent;
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

    public Gameplay()
    {
        super(0, true, 0, true);
        game = new Game();
        gameRenderer = new GameRenderer(game);
        guiRenderer = new GuiRenderer(game);
        debugRenderer = new Box2DDebugRenderer();
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new UniversalInputProcessor<>(game.getAudio()));
        inputMultiplexer.addProcessor(new UniversalInputProcessor<>(game));
        inputMultiplexer.addProcessor(guiRenderer.getStage());
        inputMultiplexer.addProcessor(new PlayerInputProcessor(game.getPlayer()));
        Gdx.input.setInputProcessor(this.inputMultiplexer);

        this.registerEventListener(GameEvent.GAMEPLAY_START, game.getAudio())
            .registerEventListener(GameEvent.GAMEPLAY_STOP, game.getAudio());

        game.registerEventListener(GameEvent.PLAYER_HEALTH_CHANGE, guiRenderer.getHud())
            .registerEventListener(GameEvent.PLAYER_SCORE_CHANGE, guiRenderer.getHud())
            .registerEventListener(GameEvent.GAMEPLAY_RESET, guiRenderer.getHud())
            .registerEventListener(GameEvent.GAME_SCREEN_CHANGE, gameRenderer)
            .registerEventListener(GameEvent.GAME_SCREEN_CHANGE, guiRenderer)
            .registerEventListener(GameEvent.GAMEPLAY_RESET, this);

        game.pushGameScreen(GameScreen.MAIN_MENU);
    }

    @Override
    public void handleEvent(Event event)
    {
        if (event.getType() == GameEvent.GAMEPLAY_RESET)
        {
            inputMultiplexer.removeProcessor(3);
            inputMultiplexer.addProcessor(new PlayerInputProcessor(game.getPlayer()));
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
        gameRenderer.update();
        guiRenderer.update();
    }

    @Override
    public void create()
    {
        dispatchEvent(GameEvent.GAMEPLAY_START);
    }

    @Override
    public void pause()
    {
        dispatchEvent(GameEvent.GAMEPLAY_STOP);
    }

    @Override
    public void resume()
    {
        dispatchEvent(GameEvent.GAMEPLAY_START);
    }

    @Override
    public void resize(int width, int height)
    {
        gameRenderer.resize(width, height);
        guiRenderer.resize(width, height);
    }

    @Override
    public float getScreenAspectRatio()
    {
        return gameRenderer.getScreenAspectRatio();
    }

    @Override
    public void setScreenAspectRatio(float aspectRatio)
    {
        gameRenderer.setScreenAspectRatio(aspectRatio);
    }

    @Override
    public int getScreenRefreshRate()
    {
        return gameRenderer.getScreenRefreshRate();
    }
}
