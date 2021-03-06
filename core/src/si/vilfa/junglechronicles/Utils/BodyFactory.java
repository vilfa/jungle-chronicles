package si.vilfa.junglechronicles.Utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import si.vilfa.junglechronicles.Component.Loggable;
import si.vilfa.junglechronicles.Gameplay.Game;

/**
 * @author luka
 * @date 26/11/2021
 * @package si.vilfa.junglechronicles.Utils
 **/
public class BodyFactory implements Loggable
{
    private static BodyFactory INSTANCE;
    private final Game game;

    private BodyFactory(Game game)
    {
        this.game = game;
    }

    public static BodyFactory getInstance(Game game)
    {
        if (INSTANCE == null)
        {
            INSTANCE = new BodyFactory(game);
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

        Body body = game.getPhysics().getWorld().createBody(bodyDef);
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

        Body body = game.getPhysics().getWorld().createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;

        body.createFixture(fixtureDef);

        return body;
    }
}
