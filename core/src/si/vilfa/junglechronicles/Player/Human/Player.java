package si.vilfa.junglechronicles.Player.Human;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.BoundingBox;
import si.vilfa.junglechronicles.Graphics.GameTime;
import si.vilfa.junglechronicles.Input.Events.IInputEventReceiver;
import si.vilfa.junglechronicles.Input.Events.InputEvent;
import si.vilfa.junglechronicles.Physics.IPhysicsActor;

/**
 * @author luka
 * @date 07/11/2021
 * @package si.vilfa.junglechronicles.Player.Human
 **/
public class Player extends HumanPlayer implements IPhysicsActor<Vector2>, IInputEventReceiver
{
	private Vector2 position;
	private Vector2 velocity;
	private float mass;
	private BoundingBox boundingBox;

	public Player()
	{
		this(new Vector2(0, 0), new Vector2(0, 0), 1, new BoundingBox());
	}

	public Player(Vector2 position, Vector2 velocity, float mass, BoundingBox boundingBox)
	{
		this.initialize(0, true);
		this.position = position;
		this.velocity = velocity;
		this.mass = mass;
		this.boundingBox = boundingBox;
	}

	@Override
	public void update(GameTime gameTime)
	{
	}

	@Override
	public void dispose()
	{

	}

	@Override
	public void handleKeyDown(InputEvent event)
	{
		Gdx.app.debug(Player.class.getName(), "Input event received: " + event);
	}

	@Override
	public void handleKeyUp(InputEvent event)
	{
		Gdx.app.debug(Player.class.getName(), "Input event received: " + event);
	}

	@Override
	public void handleKeyTyped(InputEvent event)
	{
		Gdx.app.debug(Player.class.getName(), "Input event received: " + event);
	}

	@Override
	public void handleTouchDown(InputEvent event)
	{
		Gdx.app.debug(Player.class.getName(), "Input event received: " + event);
	}

	@Override
	public void handleTouchUp(InputEvent event)
	{
		Gdx.app.debug(Player.class.getName(), "Input event received: " + event);
	}

	@Override
	public void handleTouchDragged(InputEvent event)
	{
		Gdx.app.debug(Player.class.getName(), "Input event received: " + event);
	}

	@Override
	public void handleMouseMoved(InputEvent event)
	{
		Gdx.app.debug(Player.class.getName(), "Input event received: " + event);
	}

	@Override
	public void handleScrolled(InputEvent event)
	{
		Gdx.app.debug(Player.class.getName(), "Input event received: " + event);
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
}
