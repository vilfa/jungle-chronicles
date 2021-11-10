package si.vilfa.junglechronicles.Scene.Objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.BoundingBox;
import si.vilfa.junglechronicles.Component.DrawableGameComponent;
import si.vilfa.junglechronicles.Graphics.GameTime;
import si.vilfa.junglechronicles.Physics.IPhysicsActor;

/**
 * @author luka
 * @date 08/11/2021
 * @package si.vilfa.junglechronicles.Scene.Objects
 **/
public class GrassBlock extends DrawableGameComponent implements IPhysicsActor<Vector2>
{
	private Vector2 position;
	private Vector2 velocity;
	private float mass;
	private BoundingBox boundingBox;

	public GrassBlock()
	{
		this(new Vector2(0, 0), new Vector2(0, 0), 1, new BoundingBox());
	}

	public GrassBlock(Vector2 position, Vector2 velocity, float mass, BoundingBox boundingBox)
	{
		this.initializeDrawable(1, true, 1, true);
		this.position = position;
		this.velocity = velocity;
		this.mass = mass;
		this.boundingBox = boundingBox;
	}

	@Override
	public float getMass()
	{
		return this.mass;
	}

	@Override
	public void setMass(float mass)
	{
		this.mass = mass;
	}

	@Override
	public void draw(GameTime gameTime)
	{

	}

	@Override
	public void update(GameTime gameTime)
	{

	}

	@Override
	public BoundingBox getBoundingBox()
	{
		return this.boundingBox;
	}

	@Override
	public void setBoundingBox(BoundingBox boundingBox)
	{
		this.boundingBox = boundingBox;
	}

	@Override
	public boolean isCollided(BoundingBox other)
	{
		return this.boundingBox.intersects(other);
	}

	@Override
	public Vector2 getPosition()
	{
		return this.position;
	}

	@Override
	public void setPosition(Vector2 position)
	{
		this.position = position;
	}

	@Override
	public Vector2 getVelocity()
	{
		return this.velocity;
	}

	@Override
	public void setVelocity(Vector2 velocity)
	{
		this.velocity = velocity;
	}
}
