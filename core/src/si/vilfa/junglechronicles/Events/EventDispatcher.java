package si.vilfa.junglechronicles.Events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import si.vilfa.junglechronicles.Component.Loggable;

import java.util.HashMap;

/**
 * @author luka
 * @date 05/12/2021
 * @package si.vilfa.junglechronicles.Events
 **/
public abstract class EventDispatcher implements Loggable
{
    private final HashMap<EventType, Array<EventListener>> listeners;

    public EventDispatcher()
    {
        this.listeners = new HashMap<>();
    }

    public void registerEventListener(EventType eventType, EventListener eventListener)
    {
        if (listeners.containsKey(eventType))
        {
            listeners.get(eventType).add(eventListener);
        } else
        {
            listeners.put(eventType, new Array<>(new EventListener[]{ eventListener }));
        }
    }

    public void unregisterEventListener(EventListener eventListener)
    {
        for (EventType key : listeners.keySet())
        {
            while (listeners.get(key).contains(eventListener, false))
            {
                listeners.get(key).removeValue(eventListener, false);
            }
        }
    }

    protected void dispatchEvent(EventType eventType, Object... eventData)
    {
        for (EventListener listener : listeners.get(eventType))
        {
            try
            {
                listener.handleEvent(createEvent(eventType, eventData));
            } catch (Exception e)
            {
                log("Error dispatching event:" + e.getMessage());
            }
        }
    }

    protected Event createEvent(EventType eventType, Object... eventData)
    {
        return new Event(eventType, this, eventData);
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
