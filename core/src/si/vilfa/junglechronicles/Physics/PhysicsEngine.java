package si.vilfa.junglechronicles.Physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
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

	public PhysicsEngine()
	{
		this(1 / 60f);
	}

	public PhysicsEngine(float timeStep)
	{
		this(timeStep, new Vector2(-9.81f, 0f));
	}

	public PhysicsEngine(float timeStep, Vector2 gravity)
	{
		super();
		this.world = new World(gravity, true);
		this.timeStep = timeStep;
		this.velocityIterations = 2;
		this.positionIterations = 6;
	}

	@Override
	public void update()
	{
		if (!isUpdatable) {return;}
		world.step(timeStep, velocityIterations, positionIterations);
	}

	@Override
	public void dispose()
	{
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
}
