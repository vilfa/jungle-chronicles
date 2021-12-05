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

    public Body createWithShapeWithParams(Shape shape,
                                          BodyDef.BodyType bodyType,
                                          Vector2 position,
                                          float density,
                                          float friction,
                                          float restitution)
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

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;

        body.createFixture(fixtureDef);

        return body;
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
