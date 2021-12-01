package si.vilfa.junglechronicles.Player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Gameplay.GameState;
import si.vilfa.junglechronicles.Input.Events.*;
import si.vilfa.junglechronicles.Physics.CollisionEventSubscriber;
import si.vilfa.junglechronicles.Physics.PhysicsActor;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Player.Human
 **/
public abstract class Player extends GameComponent
        implements PhysicsActor, InputEventSubscriber, CollisionEventSubscriber
{
    private final Body body;
    protected GameState gameState;
    protected boolean isActive;

    public Player(Body body)
    {
        super(0, true);
        this.isActive = true;
        this.body = body;
    }

    @Override
    public abstract void update();

    @Override
    public abstract void dispose();

    @Override
    public void handleKeyDown(KeyDownInputEvent event)
    {
    }

    @Override
    public void handleKeyUp(KeyUpInputEvent event)
    {
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
    }

    @Override
    public void handleEndContact(Object contact)
    {
    }

    public GameState getGameState()
    {
        return gameState;
    }

    public void setGameState(GameState gameState)
    {
        this.gameState = gameState;
    }

    public boolean isActive()
    {
        return isActive;
    }

    public void setActive(boolean isActive)
    {
        this.isActive = isActive;
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
