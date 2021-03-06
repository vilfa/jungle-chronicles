package si.vilfa.junglechronicles.Physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Events.Event;
import si.vilfa.junglechronicles.Events.EventListener;
import si.vilfa.junglechronicles.Events.GameEvent;
import si.vilfa.junglechronicles.Gameplay.Game;
import si.vilfa.junglechronicles.Graphics.Renderer;
import si.vilfa.junglechronicles.Level.Objects.GameObject;

import java.util.HashMap;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Physics
 **/
public class PhysicsEngine extends GameComponent implements CollisionEventDispatcher, EventListener
{
    public static float WORLD_WIDTH = 100f;
    public static float WORLD_HEIGHT = 50f;
    public static float WORLD_PPU = 128f;
    private final Game game;
    private final World world;
    private final float timeStep;
    private final float halfTimeStep;
    private final int velocityIterations;
    private final int positionIterations;
    private final HashMap<Body, Boolean> bodiesStateChanged;
    private float timer = 0f;

    public PhysicsEngine(Game game)
    {
        this(game, 1 / 60f);
    }

    public PhysicsEngine(Game game, float timeStep)
    {
        super(0, true);
        this.game = game;
        this.world = new World(new Vector2(0f, -9.81f), true);
        this.timeStep = timeStep;
        this.halfTimeStep = timeStep / 2f;
        this.velocityIterations = 6;
        this.positionIterations = 6;
        this.bodiesStateChanged = new HashMap<>();
        world.setContactListener(this);
    }

    public static float toUnits(float pixels)
    {
        return pixels / WORLD_PPU;
    }

    public static Vector2 toUnits(Vector2 pixels)
    {
        return pixels.scl(1f / WORLD_PPU);
    }

    public static float toPixels(float units)
    {
        return units * WORLD_PPU;
    }

    public static Vector2 toPixels(Vector2 units)
    {
        return units.scl(WORLD_PPU);
    }

    @Override
    public void handleEvent(Event event)
    {
        if (event.getType() == GameEvent.PLAYER_COLLECTIBLE_CONTACT
            && event.getEventData().size == 1)
        {
            GameObject object = ((GameObject) event.getEventData().first());
            bodiesStateChanged.put(object.getBody(), object.isActive());
        }
    }

    @Override
    public void update()
    {
        if (!isUpdatable || game.isPaused()) return;
        timer += Renderer.gameTime.getDeltaTime();
        if (timer > halfTimeStep)
        {
            world.step(timeStep, velocityIterations, positionIterations);
            timer = 0f;
        }
        processEvents();
    }

    @Override
    public void dispose()
    {
        world.dispose();
    }

    private void processEvents()
    {
        for (Body body : bodiesStateChanged.keySet())
        {
            world.destroyBody(body);
        }
        bodiesStateChanged.clear();
    }

    @Override
    public void beginContact(Contact contact)
    {
        if (contact.getFixtureA().getUserData() instanceof CollisionEventListener)
        {
            ((CollisionEventListener) contact.getFixtureA().getUserData()).handleBeginContact(
                    contact.getFixtureB().getUserData());
        }
        if (contact.getFixtureB().getUserData() instanceof CollisionEventListener)
        {
            ((CollisionEventListener) contact.getFixtureB().getUserData()).handleBeginContact(
                    contact.getFixtureA().getUserData());
        }
    }

    @Override
    public void endContact(Contact contact)
    {
        if (contact.getFixtureA().getUserData() instanceof CollisionEventListener)
        {
            ((CollisionEventListener) contact.getFixtureA()
                                             .getUserData()).handleEndContact(contact.getFixtureB()
                                                                                     .getUserData());
        }
        if (contact.getFixtureB().getUserData() instanceof CollisionEventListener)
        {
            ((CollisionEventListener) contact.getFixtureB()
                                             .getUserData()).handleEndContact(contact.getFixtureA()
                                                                                     .getUserData());
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) { }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) { }

    public World getWorld()
    {
        return world;
    }

    public float getWorldWidth()
    {
        return WORLD_WIDTH;
    }

    public float getWorldHeight()
    {
        return WORLD_HEIGHT;
    }
}
