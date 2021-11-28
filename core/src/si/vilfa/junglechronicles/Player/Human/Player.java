package si.vilfa.junglechronicles.Player.Human;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import si.vilfa.junglechronicles.Input.Events.*;
import si.vilfa.junglechronicles.Physics.CollisionEventSubscriber;
import si.vilfa.junglechronicles.Physics.PhysicsActor;
import si.vilfa.junglechronicles.Scene.Objects.CollectibleBlock;
import si.vilfa.junglechronicles.Scene.Objects.TrapBlock;

/**
 * @author luka
 * @date 07/11/2021
 * @package si.vilfa.junglechronicles.Player.Human
 **/
public class Player extends HumanPlayer
        implements PhysicsActor, InputEventSubscriber, CollisionEventSubscriber
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
        if (!isUpdatable) return;
    }

    @Override
    public void dispose()
    {
    }

    @Override
    public void handleKeyDown(KeyDownInputEvent event)
    {
        if (!isUpdatable) return;

//        log(event.toString());
        switch (event.getKeyCode())
        {
            case Input.Keys.LEFT:
            case Input.Keys.A:
                setVelocity(new Vector2(-5f, 0f));
                break;
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                setVelocity(new Vector2(5f, 0f));
                break;
            case Input.Keys.UP:
            case Input.Keys.W:
                setVelocity(new Vector2(0f, 6.5f));
                break;
            case Input.Keys.DOWN:
            case Input.Keys.S:
                setVelocity(new Vector2(0f, -6.5f));
                break;
        }
    }

    @Override
    public void handleKeyUp(KeyUpInputEvent event)
    {
        if (!isUpdatable) return;

//        log(event.toString());
        switch (event.getKeyCode())
        {
            case Input.Keys.LEFT:
            case Input.Keys.A:
            case Input.Keys.RIGHT:
            case Input.Keys.D:
            case Input.Keys.UP:
            case Input.Keys.W:
            case Input.Keys.DOWN:
            case Input.Keys.S:
                setVelocity(new Vector2(0f, 0f));
                break;
        }
    }

    @Override
    public void handleKeyTyped(KeyTypedInputEvent event)
    {
    }

    @Override
    public void handleTouchDown(TouchDownInputEvent event)
    {
    }

    @Override
    public void handleTouchUp(TouchUpInputEvent event)
    {
    }

    @Override
    public void handleTouchDragged(TouchDraggedInputEvent event)
    {
    }

    @Override
    public void handleMouseMoved(MouseMovedInputEvent event)
    {
    }

    @Override
    public void handleScrolled(ScrolledInputEvent event)
    {
    }

    @Override
    public void handleBeginContact(Object contact)
    {
        log("Begin collision:" + contact);
        if (contact instanceof CollectibleBlock)
        {
            int points = ((CollectibleBlock) contact).getBlockType().getPoints();
            gameState.setPlayerScore(gameState.getPlayerScore() + points);
            gameState.notifyStateChange(contact, false);
        } else if (contact instanceof TrapBlock)
        {
            int healthPoints = ((TrapBlock) contact).getBlockType().getHealthPoints();
            gameState.setPlayerHealth(gameState.getPlayerHealth() - healthPoints);
        }
    }

    @Override
    public void handleEndContact(Object contact)
    {
        log("End collision:" +  contact);
    }

    @Override
    public Body getBody()
    {
        return body;
    }

    @Override
    public Array<Fixture> getFixtures()
    {
        return body.getFixtureList();
    }

    @Override
    public Vector2 getPosition()
    {
        return body.getTransform().getPosition();
    }

    @Override
    public void setPosition(Vector2 position)
    {
        body.setTransform(position, getRotation());
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
    public float getAngularVelocity()
    {
        return body.getAngularVelocity();
    }

    @Override
    public void setAngularVelocity(float velocity)
    {
        body.setAngularVelocity(velocity);
    }

    @Override
    public float getRotation()
    {
        return body.getTransform().getRotation();
    }

    @Override
    public void setRotation(float rotation)
    {
        body.setTransform(getPosition(), rotation);
    }
}
