package si.vilfa.junglechronicles.Player;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Gameplay.GameState;
import si.vilfa.junglechronicles.Input.Events.*;
import si.vilfa.junglechronicles.Physics.CollisionEventListener;
import si.vilfa.junglechronicles.Physics.PhysicsActor;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Player
 **/
public abstract class Player extends GameComponent
        implements PhysicsActor, InputEventListener, CollisionEventListener, Steerable<Vector2>
{
    protected final Body body;
    protected GameState gameState;
    protected boolean isActive;

    protected SteeringBehavior<Vector2> steeringBehavior;
    protected boolean isTagged;
    protected float boundingRadius;
    protected float zeroLinearSpeedThreshold;
    protected float maxLinearSpeed;
    protected float maxLinearAcceleration;
    protected float maxAngularSpeed;
    protected float maxAngularAcceleration;

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
    public void handleKeyDown(KeyDownInputEvent event) { }

    @Override
    public void handleKeyUp(KeyUpInputEvent event) { }

    @Override
    public void handleKeyTyped(KeyTypedInputEvent event) { }

    @Override
    public void handleTouchDown(TouchDownInputEvent event) { }

    @Override
    public void handleTouchUp(TouchUpInputEvent event) { }

    @Override
    public void handleTouchDragged(TouchDraggedInputEvent event) { }

    @Override
    public void handleMouseMoved(MouseMovedInputEvent event) { }

    @Override
    public void handleScrolled(ScrolledInputEvent event) { }

    @Override
    public void handleBeginContact(Object contact) { }

    @Override
    public void handleEndContact(Object contact) { }

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

    @Override
    public float getOrientation()
    {
        return getRotation();
    }

    @Override
    public void setOrientation(float orientation)
    {
        setRotation(orientation);
    }

    @Override
    public float vectorToAngle(Vector2 vector)
    {
        return (float) Math.atan2(-vector.x, vector.y);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle)
    {
        outVector.x = -(float) Math.sin(angle);
        outVector.y = (float) Math.cos(angle);
        return outVector;
    }

    @Override
    public Location<Vector2> newLocation()
    {
        return null;
    }

    @Override
    public Vector2 getLinearVelocity()
    {
        return getVelocity();
    }

    @Override
    public float getBoundingRadius()
    {
        return boundingRadius;
    }

    @Override
    public boolean isTagged()
    {
        return isTagged;
    }

    @Override
    public void setTagged(boolean tagged)
    {
        isTagged = tagged;
    }

    @Override
    public float getZeroLinearSpeedThreshold()
    {
        return zeroLinearSpeedThreshold;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value)
    {
        zeroLinearSpeedThreshold = value;
    }

    @Override
    public float getMaxLinearSpeed()
    {
        return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed)
    {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration()
    {
        return maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration)
    {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed()
    {
        return maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed)
    {
        this.maxAngularSpeed = maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration()
    {
        return maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration)
    {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }
}
