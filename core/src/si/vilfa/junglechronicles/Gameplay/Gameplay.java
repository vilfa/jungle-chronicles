package si.vilfa.junglechronicles.Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;
import si.vilfa.junglechronicles.Component.DrawableGameComponent;
import si.vilfa.junglechronicles.Events.GameEvent;
import si.vilfa.junglechronicles.Graphics.GameRenderer;
import si.vilfa.junglechronicles.Graphics.GuiRenderer;
import si.vilfa.junglechronicles.Graphics.WindowAdapter;
import si.vilfa.junglechronicles.Input.Events.*;
import si.vilfa.junglechronicles.Input.Processors.GameplayInputProcessor;
import si.vilfa.junglechronicles.Input.Processors.PlayerInputProcessor;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Gameplay
 **/
public class Gameplay extends DrawableGameComponent
        implements Disposable, WindowAdapter, InputEventListener, GameplayAdapter
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
        inputMultiplexer.addProcessor(new PlayerInputProcessor(game.getPlayer()));
        inputMultiplexer.addProcessor(new GameplayInputProcessor(this));
        Gdx.input.setInputProcessor(this.inputMultiplexer);

        registerEventListener(GameEvent.GAMEPLAY_START, game.getAudio());
        registerEventListener(GameEvent.GAMEPLAY_STOP, game.getAudio());
    }

    @Override
    public void draw()
    {
        if (!isDrawable) return;
        gameRenderer.draw();
        guiRenderer.draw();
        debugRenderer.render(game.getPhysics().getWorld(), gameRenderer.getCombined());
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
        log("Create");
        dispatchEvent(GameEvent.GAMEPLAY_START);
    }

    @Override
    public void pause()
    {
        log("Pause");
        dispatchEvent(GameEvent.GAMEPLAY_STOP);
    }

    @Override
    public void resume()
    {
        log("Resume");
        dispatchEvent(GameEvent.GAMEPLAY_START);
    }

    @Override
    public void resize(int width, int height)
    {
        gameRenderer.resize(width, height);
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

    @Override
    public void handleKeyDown(KeyDownInputEvent event) { }

    @Override
    public void handleKeyUp(KeyUpInputEvent event)
    {
        float volume;
        switch (event.getKeyCode())
        {
            case Input.Keys.LEFT_BRACKET:
                volume = game.getAudio().getMasterVolume();
                game.getAudio().setMasterVolume(volume - 0.05f);
                break;
            case Input.Keys.RIGHT_BRACKET:
                volume = game.getAudio().getMasterVolume();
                game.getAudio().setMasterVolume(volume + 0.05f);
                break;
        }
    }

    @Override
    public void handleKeyTyped(KeyTypedInputEvent event) { }

    @Override
    public void handleTouchDown(TouchDownInputEvent event) { }

    @Override
    public void handleTouchUp(TouchUpInputEvent event) { }

    @Override
    public void handleTouchDragged(TouchDraggedInputEvent event) { }

    @Override
    public void handleMouseMoved(MouseMovedInputEvent event) { }

    @Override
    public void handleScrolled(ScrolledInputEvent event) { }
}
