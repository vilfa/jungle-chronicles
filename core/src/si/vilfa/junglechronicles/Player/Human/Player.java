package si.vilfa.junglechronicles.Player.Human;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import si.vilfa.junglechronicles.Graphics.Renderer;
import si.vilfa.junglechronicles.Input.Events.*;
import si.vilfa.junglechronicles.Physics.CollisionEventSubscriber;
import si.vilfa.junglechronicles.Physics.WorldActor;

/**
 * @author luka
 * @date 07/11/2021
 * @package si.vilfa.junglechronicles.Player.Human
 **/
public class Player extends HumanPlayer implements WorldActor, InputEventSubscriber, CollisionEventSubscriber
{
	private final Body body;

	public Player(Body body)
	{
		super();
		this.body = body;
	}

	@Override
	public void update()
	{
		if (!isUpdatable) {return;}
		setPosition(getPosition().mulAdd(getVelocity(), Renderer.gameTime.getDeltaTime()));
	}

	@Override
	public void dispose()
	{
	}

	@Override
	public void handleKeyDown(KeyDownInputEvent event)
	{
		if (!isUpdatable) {return;}

		log(event.toString());
		switch (event.getKeyCode())
		{
			case Input.Keys.LEFT:
				setVelocity(Vector2.X.scl(-100f));
				break;
			case Input.Keys.RIGHT:
				setVelocity(Vector2.X.scl(100f));
				break;
			case Input.Keys.UP:
				setVelocity(Vector2.Y.scl(100f));
				break;
			case Input.Keys.DOWN:
				setVelocity(Vector2.Y.scl(-100f));
				break;
		}
	}

	@Override
	public void handleKeyUp(KeyUpInputEvent event)
	{
		log(event.toString());

		switch (event.getKeyCode())
		{
			case Input.Keys.LEFT:
			case Input.Keys.RIGHT:
			case Input.Keys.UP:
			case Input.Keys.DOWN:
				setVelocity(Vector2.Zero);
				break;
		}
	}

	@Override
	public void handleKeyTyped(KeyTypedInputEvent event)
	{
		log(event.toString());
	}

	@Override
	public void handleTouchDown(TouchDownInputEvent event)
	{
		log(event.toString());
	}

	@Override
	public void handleTouchUp(TouchUpInputEvent event)
	{
		log(event.toString());
	}

	@Override
	public void handleTouchDragged(TouchDraggedInputEvent event)
	{
		log(event.toString());
	}

	@Override
	public void handleMouseMoved(MouseMovedInputEvent event)
	{
		log(event.toString());
	}

	@Override
	public void handleScrolled(ScrolledInputEvent event)
	{
		log(event.toString());
	}

	@Override
	public void handleBeginContact(Contact contact)
	{
		log(contact.toString());
	}

	@Override
	public void handleEndContact(Contact contact)
	{
		log(contact.toString());
	}

	@Override
	public void handlePreSolve(Contact contact, Manifold oldManifold)
	{
		log(contact.toString() + oldManifold.toString());
	}

	@Override
	public void handlePostSolve(Contact contact, ContactImpulse impulse)
	{
		log(contact.toString() + impulse.toString());
	}

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
