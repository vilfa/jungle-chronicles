package si.vilfa.junglechronicles.Physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import si.vilfa.junglechronicles.Component.GameComponent;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Physics
 **/
public class PhysicsEngine extends GameComponent implements CollisionEventProvider
{
    private final World world;
    private final float timeStep;
    private final int velocityIterations;
    private final int positionIterations;
    private final float WORLD_WIDTH;
    private final float WORLD_HEIGHT;

    public PhysicsEngine()
    {
        this(1 / 60f);
    }

    public PhysicsEngine(float timeStep)
    {
        super(0, true);
        this.WORLD_WIDTH = 200f;
        this.WORLD_HEIGHT = 50f;
        this.world = new World(new Vector2(0f, -9.81f), true);
        this.timeStep = timeStep;
        this.velocityIterations = 6;
        this.positionIterations = 6;
    }

    @Override
    public void update()
    {
        if (!isUpdatable) { return; }
        world.step(timeStep, velocityIterations, positionIterations);
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
    public void beginContact(Contact contact)
    {
    }

    @Override
    public void endContact(Contact contact)
    {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold)
    {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse)
    {

    }

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
