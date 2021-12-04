package si.vilfa.junglechronicles.Physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Gameplay.StateChange;
import si.vilfa.junglechronicles.Level.Objects.GameObject;

import java.util.HashMap;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Physics
 **/
public class PhysicsEngine extends GameComponent implements CollisionEventProvider, StateChange
{
    public static float WORLD_WIDTH = 200f;
    public static float WORLD_HEIGHT = 50f;
    public static float WORLD_PPU = 32f;
    private final World world;
    private final float timeStep;
    private final int velocityIterations;
    private final int positionIterations;
    private final HashMap<Body, Boolean> bodiesWithStateChange;

    public PhysicsEngine()
    {
        this(1 / 60f);
    }

    public PhysicsEngine(float timeStep)
    {
        super(0, true);
        this.world = new World(new Vector2(0f, -9.81f), true);
        this.timeStep = timeStep;
        this.velocityIterations = 6;
        this.positionIterations = 6;
        this.bodiesWithStateChange = new HashMap<>();

        Graphics.DisplayMode displayMode
                = Gdx.graphics.getDisplayMode(Gdx.graphics.getPrimaryMonitor());

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
    public void update()
    {
        if (!isUpdatable) return;
        world.step(timeStep, velocityIterations, positionIterations);
        handleNotifiedStateChange();
    }

    @Override
    public void dispose()
    {
        Array<Body> bodies = new Array<>();
        world.getBodies(bodies);
        for (Body body : bodies)
        {
            world.destroyBody(body);
        }
        world.dispose();
    }

    @Override
    public void notifyStateChange(Object object, boolean isActive)
    {
        if (object instanceof GameObject)
        {
            bodiesWithStateChange.put(((GameObject) object).getBody(), isActive);
        }
    }

    private void handleNotifiedStateChange()
    {
        // TODO Implement an actual event based system instead of this.

        for (Body body : bodiesWithStateChange.keySet())
        {
            world.destroyBody(body);
        }
        bodiesWithStateChange.clear();
    }

    @Override
    public void beginContact(Contact contact)
    {
        if (contact.getFixtureA().getUserData() instanceof CollisionEventSubscriber)
        {
            ((CollisionEventSubscriber) contact.getFixtureA().getUserData()).handleBeginContact(
                    contact.getFixtureB().getUserData());
        }
        if (contact.getFixtureB().getUserData() instanceof CollisionEventSubscriber)
        {
            ((CollisionEventSubscriber) contact.getFixtureB().getUserData()).handleBeginContact(
                    contact.getFixtureA().getUserData());
        }
    }

    @Override
    public void endContact(Contact contact)
    {
        if (contact.getFixtureA().getUserData() instanceof CollisionEventSubscriber)
        {
            ((CollisionEventSubscriber) contact.getFixtureA().getUserData()).handleEndContact(
                    contact.getFixtureB().getUserData());
        }
        if (contact.getFixtureB().getUserData() instanceof CollisionEventSubscriber)
        {
            ((CollisionEventSubscriber) contact.getFixtureB().getUserData()).handleEndContact(
                    contact.getFixtureA().getUserData());
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
