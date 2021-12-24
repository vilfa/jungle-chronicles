package si.vilfa.junglechronicles.Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;
import si.vilfa.junglechronicles.Component.DrawableGameComponent;
import si.vilfa.junglechronicles.Events.GameStateEvent;
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
    private final GameState gameState;
    private final GameRenderer gameRenderer;
    private final GuiRenderer guiRenderer;
    private final Box2DDebugRenderer debugRenderer;
    private final InputMultiplexer inputMultiplexer;

    public Gameplay()
    {
        super(0, true, 0, true);
        gameState = new GameState();
        gameRenderer = new GameRenderer(gameState);
        guiRenderer = new GuiRenderer(gameState);
        debugRenderer = new Box2DDebugRenderer();
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new PlayerInputProcessor(gameState.getPlayer()));
        inputMultiplexer.addProcessor(new GameplayInputProcessor(this));
        Gdx.input.setInputProcessor(this.inputMultiplexer);

        registerEventListener(GameStateEvent.GAMEPLAY_START, gameState.getAudio());
        registerEventListener(GameStateEvent.GAMEPLAY_STOP, gameState.getAudio());
    }

    @Override
    public void draw()
    {
        if (!isDrawable) return;
        gameRenderer.draw();
        guiRenderer.draw();
        debugRenderer.render(gameState.getPhysics().getWorld(), gameRenderer.getCombined());
    }

    @Override
    public void dispose()
    {
        inputMultiplexer.clear();
        gameState.dispose();
        gameRenderer.dispose();
        guiRenderer.dispose();
        debugRenderer.dispose();
    }

    @Override
    public void update()
    {
        if (!isUpdatable) return;
        gameState.update();
        gameRenderer.update();
        guiRenderer.update();
    }

    @Override
    public void create()
    {
        log("Create");
        dispatchEvent(GameStateEvent.GAMEPLAY_START);
    }

    @Override
    public void pause()
    {
        log("Pause");
        dispatchEvent(GameStateEvent.GAMEPLAY_STOP);
    }

    @Override
    public void resume()
    {
        log("Resume");
        dispatchEvent(GameStateEvent.GAMEPLAY_START);
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
                volume = gameState.getAudio().getMasterVolume();
                gameState.getAudio().setMasterVolume(volume - 0.05f);
                break;
            case Input.Keys.RIGHT_BRACKET:
                volume = gameState.getAudio().getMasterVolume();
                gameState.getAudio().setMasterVolume(volume + 0.05f);
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
