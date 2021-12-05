package si.vilfa.junglechronicles.Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;
import si.vilfa.junglechronicles.Component.DrawableGameComponent;
import si.vilfa.junglechronicles.Graphics.Renderer;
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
        implements Disposable, WindowAdapter, InputEventListener
{
    private final GameState gameState;
    private final Renderer renderer;
    private final Box2DDebugRenderer debugRenderer;
    private final InputMultiplexer inputMultiplexer;

    public Gameplay()
    {
        super(0, true, 0, true);
        gameState = new GameState();
        renderer = new Renderer(gameState);
        debugRenderer = new Box2DDebugRenderer();
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new PlayerInputProcessor(gameState.getPlayer()));
        inputMultiplexer.addProcessor(new GameplayInputProcessor(this));
        Gdx.input.setInputProcessor(this.inputMultiplexer);
    }

    @Override
    public void draw()
    {
        if (!isDrawable) return;
        renderer.draw();
        debugRenderer.render(gameState.getPhysics().getWorld(), renderer.getCombined());
    }

    @Override
    public void dispose()
    {
        inputMultiplexer.clear();
        gameState.dispose();
        renderer.dispose();
    }

    @Override
    public void update()
    {
        if (!isUpdatable) return;
        gameState.update();
        renderer.update();
    }

    @Override
    public void resize(int width, int height)
    {
        renderer.resize(width, height);
    }

    @Override
    public float getScreenAspectRatio()
    {
        return renderer.getScreenAspectRatio();
    }

    @Override
    public void setScreenAspectRatio(float aspectRatio)
    {
        renderer.setScreenAspectRatio(aspectRatio);
    }

    @Override
    public int getScreenRefreshRate()
    {
        return renderer.getScreenRefreshRate();
    }

    @Override
    public void handleKeyDown(KeyDownInputEvent event) { }

    @Override
    public void handleKeyUp(KeyUpInputEvent event) { }

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
