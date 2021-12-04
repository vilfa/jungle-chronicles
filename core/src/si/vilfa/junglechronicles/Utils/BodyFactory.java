package si.vilfa.junglechronicles.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import si.vilfa.junglechronicles.Component.Loggable;
import si.vilfa.junglechronicles.Gameplay.GameState;

/**
 * @author luka
 * @date 26/11/2021
 * @package si.vilfa.junglechronicles.Utils
 **/
public class BodyFactory implements Loggable
{
    private static BodyFactory INSTANCE;
    private final World world;

    private BodyFactory(GameState gameState)
    {
        this.world = gameState.getPhysics().getWorld();
    }

    public static BodyFactory getInstance(GameState gameState)
    {
        if (INSTANCE == null)
        {
            INSTANCE = new BodyFactory(gameState);
        }
        return INSTANCE;
    }

    public Body createWithShape(Shape shape, BodyDef.BodyType bodyType, Vector2 position)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.active = true;
        bodyDef.awake = true;
        bodyDef.allowSleep = true;
        bodyDef.bullet = false;
        bodyDef.angle = 0f;
        bodyDef.position.set(position);

        Body body = world.createBody(bodyDef);
        body.createFixture(shape, 0f);
        return body;
    }

    /**
     * Creates a static body, and places it into the world.
     * <p>
     * A static body does not move under simulation and behaves as if it has
     * infinite mass. Internally, Box2D stores zero for the mass and the
     * inverse mass. Static bodies can be moved manually by the user. A static
     * body has zero velocity. Static bodies do not collide with other static
     * or kinematic bodies.
     *
     * @param position Body origin position
     * @param rotation Body rotation
     * @param awake    Whether the body should be awake upon creation
     * @return The created body
     */
    public Body createStaticBody(Vector2 position, float rotation, boolean awake)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.active = true;
        bodyDef.allowSleep = true;

        bodyDef.position.set(position);
        bodyDef.angle = rotation;
        bodyDef.awake = awake;

        return world.createBody(bodyDef);
    }

    /**
     * Creates a dynamic body, and places it into the physics world.
     * <p>
     * A dynamic body is fully simulated. They can be moved manually by the
     * user, but normally they move according to forces. A dynamic body can
     * collide with all body types. A dynamic body always has finite, non-zero
     * mass. If you try to set the mass of a dynamic body to zero, it will
     * automatically acquire a mass of one kilogram, and it won't rotate.
     * <p>
     * Bodies are the backbone for fixtures (shapes). Bodies carry fixtures
     * and move them around in the world. Bodies are always rigid bodies in
     * Box2D. That means that two fixtures attached to the same rigid body
     * never move relative to each other and fixtures attached to the same
     * body don't collide.
     * <p>
     * Fixtures have collision geometry and density. Normally, bodies acquire
     * their mass properties from the fixtures. However, you can override the
     * mass properties after a body is constructed.
     * <p>
     * You usually keep pointers to all the bodies you create. This way you
     * can query the body positions to update the positions of your graphical
     * entities. You should also keep body pointers, so you can destroy them
     * when you are done with them.
     *
     * @param position      Body origin position
     * @param velocity      Body linear velocity
     * @param rotation      Body rotation
     * @param fixedRotation Whether the rotation should be fixed
     * @param awake         Whether the body should be awake upon creation
     * @return The created body
     */
    public Body createDynamicBody(Vector2 position,
                                  Vector2 velocity,
                                  float rotation,
                                  boolean fixedRotation,
                                  boolean awake)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.active = true;
        bodyDef.allowSleep = true;
        bodyDef.bullet = false;

        bodyDef.position.set(position);
        bodyDef.linearVelocity.set(velocity);
        bodyDef.angle = rotation;
        bodyDef.fixedRotation = fixedRotation;
        bodyDef.awake = awake;

        return world.createBody(bodyDef);
    }

    /**
     * Creates a kinematic body, and places it into the physics world.
     * <p>
     * A kinematic body moves under simulation according to its velocity.
     * Kinematic bodies do not respond to force. They can be moved manually
     * by the user, but normally a kinematic body is moved by setting its
     * velocity. A kinematic body behaves as if it has infinite mass, however,
     * Box2D stores zero for the mass and the inverse mass. Kinematic bodies
     * do not collide with other kinematic or static bodies.
     *
     * @param position      Body origin position
     * @param velocity      Body linear velocity
     * @param rotation      Body rotation
     * @param fixedRotation Whether the rotation should be fixed
     * @param awake         Whether the body should be awake upon creation
     * @return The created body
     */
    public Body createKinematicBody(Vector2 position,
                                    Vector2 velocity,
                                    float rotation,
                                    boolean fixedRotation,
                                    boolean awake)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.active = true;
        bodyDef.allowSleep = true;

        bodyDef.position.set(position);
        bodyDef.linearVelocity.set(velocity);
        bodyDef.angle = rotation;
        bodyDef.fixedRotation = fixedRotation;
        bodyDef.awake = awake;

        return world.createBody(bodyDef);
    }

    public Fixture createFixture(Body body,
                                 Shape shape,
                                 Object userData,
                                 float density,
                                 float friction,
                                 float restitution)
    {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(userData);
        return fixture;
    }

    @Override
    public String getId()
    {
        return getClass().getSimpleName() + "#" + hashCode();
    }

    @Override
    public void log(String message)
    {
        Gdx.app.debug(getId(), message);
    }
}
