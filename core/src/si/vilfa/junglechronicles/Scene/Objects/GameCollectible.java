package si.vilfa.junglechronicles.Scene.Objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Physics.WorldActor;

/**
 * @author luka
 * @date 11/11/2021
 * @package si.vilfa.junglechronicles.Scene.Objects
 **/
public abstract class GameCollectible extends GameComponent implements WorldActor
{
	protected final Body body;

	public GameCollectible(Body body)
	{
		super();
		this.initialize(0, true);
		this.body = body;
	}

	@Override
	public abstract void update();

	@Override
	public abstract void dispose();

	@Override
	public Body getBody()
	{
		return body;
	}

	@Override
	public Vector2 getPosition()
	{
		return body.getPosition();
	}

	@Override
	public void setPosition(Vector2 position)
	{
		body.setTransform(position, body.getAngle());
	}

	@Override
	public Vector2 getVelocity()
	{
		return body.getLinearVelocity();
	}

	@Override
	public void setVelocity(Vector2 velocity)
	{
		body.setLinearVelocity(velocity);
	}

	@Override
	public float getRotation()
	{
		return body.getAngle();
	}

	@Override
	public void setRotation(float rotation)
	{
		body.setTransform(body.getPosition(), rotation);
	}
}
