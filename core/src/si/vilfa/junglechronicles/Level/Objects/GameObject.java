package si.vilfa.junglechronicles.Level.Objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Gameplay.Game;
import si.vilfa.junglechronicles.Physics.PhysicsActor;

/**
 * @author luka
 * @date 08/11/2021
 * @package si.vilfa.junglechronicles.Level.Objects
 **/
public abstract class GameObject extends GameComponent implements PhysicsActor
{
    protected final Body body;
    protected Game game;
    protected boolean isActive;

    public GameObject(Body body)
    {
        super(0, true);
        this.body = body;
        this.isActive = true;
    }

    public Game getGame()
    {
        return game;
    }

    public void setGame(Game game)
    {
        this.game = game;
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
    public abstract void update();

    @Override
    public abstract void dispose();

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
