package si.vilfa.junglechronicles.Component;

import com.badlogic.gdx.Gdx;

import java.time.LocalTime;

/**
 * @author luka
 * @date 03/12/2021
 * @package si.vilfa.junglechronicles.Component
 **/
public interface Loggable
{
    default String getId()
    {
        return getClass().getSimpleName() + "#" + hashCode();
    }

    default String getTimestamp()
    {
        return LocalTime.now().toString();
    }

    default String getTag()
    {
        return getTimestamp() + " " + getId();
    }

    default void log(String message)
    {
        Gdx.app.debug(getTag(), message);
    }

    default void error(String message, Throwable... throwable)
    {
        if (throwable.length > 0)
        {
            Gdx.app.error(getTag(), message, throwable[0]);
        } else
        {
            Gdx.app.error(getTag(), message);
        }
    }
}
