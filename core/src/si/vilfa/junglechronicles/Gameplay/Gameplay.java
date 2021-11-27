package si.vilfa.junglechronicles.Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;
import si.vilfa.junglechronicles.Component.DrawableGameComponent;
import si.vilfa.junglechronicles.Graphics.Renderer;
import si.vilfa.junglechronicles.Graphics.WindowAdapter;
import si.vilfa.junglechronicles.Input.Events.*;
import si.vilfa.junglechronicles.Input.Processors.GameplayInputProcessor;
import si.vilfa.junglechronicles.Input.Processors.PlayerInputProcessor;
import si.vilfa.junglechronicles.Physics.BodyFactory;
import si.vilfa.junglechronicles.Physics.PhysicsEngine;
import si.vilfa.junglechronicles.Player.Human.Player;
import si.vilfa.junglechronicles.Scene.Levels.Level;
import si.vilfa.junglechronicles.Scene.Objects.GameObjectFactory;
import si.vilfa.junglechronicles.Scene.Objects.WorldBlock;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Gameplay
 **/
public class Gameplay extends DrawableGameComponent
        implements Disposable, WindowAdapter, InputEventSubscriber
{
    private final Level level;
    private final Player player;
    private final PhysicsEngine physics;
    private final Renderer renderer;
    private final Box2DDebugRenderer debugRenderer;
    private final InputMultiplexer inputMultiplexer;

    public Gameplay()
    {
        super(0, true, 0, true);
        level = new Level();
        physics = new PhysicsEngine();
        renderer = new Renderer(this.level, physics.getWorldWidth(), physics.getWorldHeight());
        debugRenderer = new Box2DDebugRenderer();


        GameObjectFactory gameObjectFactory = GameObjectFactory.getInstance(BodyFactory.getInstance(
                physics.getWorld()));

        player = gameObjectFactory.createDynamicWithPolygonFixture(new Vector2(1f, 2f),
                                                                   new Vector2(0f, 0f),
                                                                   0f,
                                                                   65f,
                                                                   0f,
                                                                   0.1f,
                                                                   new Vector2(.4f, .7f),
                                                                   Player.class,
                                                                   Body.class);
        level.addItem(player);

        for (int i = 0; i < 200; i++)
        {
            WorldBlock groundBlock = gameObjectFactory.createStaticWithPolygonFixture(new Vector2(i,
                                                                                                  .5f),
                                                                                      0,
                                                                                      Float.POSITIVE_INFINITY,
                                                                                      0f,
                                                                                      .1f,
                                                                                      new Vector2(
                                                                                              .5f,
                                                                                              .5f),
                                                                                      WorldBlock.class,
                                                                                      Body.class);
            level.addItem(groundBlock);
        }

        for (int i = 1; i < 3; i++)
        {
            WorldBlock worldBlock = gameObjectFactory.createStaticWithPolygonFixture(new Vector2(i,
                                                                                                 5),
                                                                                     0,
                                                                                     Float.POSITIVE_INFINITY,
                                                                                     0f,
                                                                                     .1f,
                                                                                     new Vector2(.5f,
                                                                                                 .5f),
                                                                                     WorldBlock.class,
                                                                                     Body.class);
            level.addItem(worldBlock);
        }

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new PlayerInputProcessor(player));
        inputMultiplexer.addProcessor(new GameplayInputProcessor(this));
        Gdx.input.setInputProcessor(this.inputMultiplexer);
    }

    @Override
    public void draw()
    {
        if (!isDrawable) { return; }
        renderer.draw();
        debugRenderer.render(physics.getWorld(), renderer.getCombined());
    }

    @Override
    public void dispose()
    {
        inputMultiplexer.clear();
        level.dispose();
        player.dispose();
        physics.dispose();
        renderer.dispose();
    }

    @Override
    public void update()
    {
        if (!isUpdatable) { return; }
        physics.update();
        player.update();
        level.update();
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
    public void handleKeyUp(KeyUpInputEvent event)
    {
        if (event.getKeyCode() == Input.Keys.ESCAPE)
        {
            log("Menu");
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
