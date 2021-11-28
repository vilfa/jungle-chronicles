package si.vilfa.junglechronicles.Scene.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Physics.BodyFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * @author luka
 * @date 27/11/2021
 * @package si.vilfa.junglechronicles.Scene.Objects
 **/
public class GameObjectFactory
{
    private static GameObjectFactory INSTANCE;
    private final BodyFactory bodyFactory;

    private GameObjectFactory(BodyFactory bodyFactory)
    {
        this.bodyFactory = bodyFactory;
    }

    public static GameObjectFactory getInstance(BodyFactory bodyFactory)
    {
        if (INSTANCE == null)
        {
            INSTANCE = new GameObjectFactory(bodyFactory);
        }
        return INSTANCE;
    }

    public <T extends GameComponent, U> T createStaticWithCircleFixture(Vector2 position,
                                                                        float rotation,
                                                                        float density,
                                                                        float friction,
                                                                        float restitution,
                                                                        float fixtureRadius,
                                                                        Class<T> objectType,
                                                                        Class<U> ctorParamType)
    {
        Body body = bodyFactory.createStaticBody(position, rotation, true);

        T gameObject = null;
        try
        {
            gameObject = objectType.getDeclaredConstructor(ctorParamType).newInstance(body);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e)
        {
            Gdx.app.log(this.getClass().getSimpleName(), e.getMessage(), e);
            System.exit(-1);
        }

        CircleShape shape = new CircleShape();
        shape.setRadius(fixtureRadius);
        bodyFactory.createFixture(body, shape, gameObject, density, friction, restitution);

        return gameObject;
    }

    public <T extends GameComponent, U> T createStaticWithPolygonFixture(Vector2 position,
                                                                         float rotation,
                                                                         float density,
                                                                         float friction,
                                                                         float restitution,
                                                                         Vector2 fixtureHalfSize,
                                                                         Class<T> objectType,
                                                                         Class<U> ctorParamType)
    {
        Body body = bodyFactory.createStaticBody(position, rotation, true);

        T gameObject = null;
        try
        {
            gameObject = objectType.getDeclaredConstructor(ctorParamType).newInstance(body);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e)
        {
            Gdx.app.log(this.getClass().getSimpleName(), e.getMessage(), e);
            System.exit(-1);
        }

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(fixtureHalfSize.x, fixtureHalfSize.y);
        bodyFactory.createFixture(body, shape, gameObject, density, friction, restitution);

        return gameObject;
    }

    public <T extends GameComponent, U> T createDynamicWithCircleFixture(Vector2 position,
                                                                         Vector2 velocity,
                                                                         float rotation,
                                                                         float density,
                                                                         float friction,
                                                                         float restitution,
                                                                         float fixtureRadius,
                                                                         Class<T> objectType,
                                                                         Class<U> ctorParamType)
    {
        Body body = bodyFactory.createDynamicBody(position, velocity, rotation, true, true);

        T gameObject = null;
        try
        {
            gameObject = objectType.getDeclaredConstructor(ctorParamType).newInstance(body);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e)
        {
            Gdx.app.error(this.getClass().getSimpleName(), e.getMessage(), e);
            System.exit(-1);
        }

        CircleShape shape = new CircleShape();
        shape.setRadius(fixtureRadius);
        bodyFactory.createFixture(body, shape, gameObject, density, friction, restitution);

        return gameObject;
    }

    public <T extends GameComponent, U> T createDynamicWithPolygonFixture(Vector2 position,
                                                                          Vector2 velocity,
                                                                          float rotation,
                                                                          float density,
                                                                          float friction,
                                                                          float restitution,
                                                                          Vector2 fixtureHalfSize,
                                                                          Class<T> objectType,
                                                                          Class<U> ctorParamType)
    {
        Body body = bodyFactory.createDynamicBody(position, velocity, rotation, true, true);

        T gameObject = null;
        try
        {
            gameObject = objectType.getDeclaredConstructor(ctorParamType).newInstance(body);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e)
        {
            Gdx.app.error(this.getClass().getSimpleName(), e.getMessage(), e);
            System.exit(-1);
        }

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(fixtureHalfSize.x, fixtureHalfSize.y);
        bodyFactory.createFixture(body, shape, gameObject, density, friction, restitution);

        return gameObject;
    }
}
