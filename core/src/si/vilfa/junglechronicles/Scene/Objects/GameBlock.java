package si.vilfa.junglechronicles.Scene.Objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.BoundingBox;
import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Graphics.GameTime;
import si.vilfa.junglechronicles.Physics.IPhysicsActor;

/**
 * @author luka
 * @date 08/11/2021
 * @package si.vilfa.junglechronicles.Scene.Objects
 **/
public abstract class GameBlock extends GameComponent implements IPhysicsActor<Vector2>
{
	protected Vector2 position;
	protected Vector2 velocity;
	protected float mass;
	protected BoundingBox boundingBox;

	public GameBlock(Vector2 position, Vector2 velocity, float mass, BoundingBox boundingBox)
	{
		this.initialize(1, true);
		this.position = position;
		this.velocity = velocity;
		this.mass = mass;
		this.boundingBox = boundingBox;
	}

	@Override
	public float getMass()
	{
		return mass;
	}

	@Override
	public void setMass(float mass)
	{
		this.mass = mass;
	}

	@Override
	public abstract void update(GameTime gameTime);

	@Override
	public BoundingBox getBoundingBox()
	{
		return boundingBox;
	}

	@Override
	public void setBoundingBox(BoundingBox boundingBox)
	{
		this.boundingBox = boundingBox;
	}

	@Override
	public boolean isCollided(BoundingBox other)
	{
		return boundingBox.intersects(other);
	}

	@Override
	public Vector2 getPosition()
	{
		return position;
	}

	@Override
	public void setPosition(Vector2 position)
	{
		this.position = position;
	}

	@Override
	public Vector2 getVelocity()
	{
		return velocity;
	}

	@Override
	public void setVelocity(Vector2 velocity)
	{
		this.velocity = velocity;
	}
}
