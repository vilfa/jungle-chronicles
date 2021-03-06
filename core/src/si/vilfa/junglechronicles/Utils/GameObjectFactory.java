package si.vilfa.junglechronicles.Utils;

import com.badlogic.gdx.physics.box2d.Body;
import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Component.Loggable;

import java.lang.reflect.InvocationTargetException;

/**
 * @author luka
 * @date 27/11/2021
 * @package si.vilfa.junglechronicles.Utils
 **/
public class GameObjectFactory implements Loggable
{
    private static GameObjectFactory INSTANCE;

    private GameObjectFactory() { }

    public static GameObjectFactory getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new GameObjectFactory();
        }
        return INSTANCE;
    }

    public <T extends GameComponent, U> T createWithBody(Body body,
                                                         Class<T> objectType,
                                                         Class<U> ctorParamType)
    {
        T gameObject = null;
        try
        {
            gameObject = objectType.getDeclaredConstructor(ctorParamType).newInstance(body);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e)
        {
            error(e.getMessage(), e);
        }

        return gameObject;
    }
}
