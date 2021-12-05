package si.vilfa.junglechronicles.Events;

import com.badlogic.gdx.utils.Array;

/**
 * @author luka
 * @date 05/12/2021
 * @package si.vilfa.junglechronicles.Events
 **/
public class Event
{
    private final EventType type;
    private final EventDispatcher source;
    private final Array<Object> eventData;

    public Event(EventType type, EventDispatcher source, Object... eventData)
    {
        this.type = type;
        this.source = source;
        this.eventData = new Array<>(eventData);
    }

    public EventType getType()
    {
        return type;
    }

    public EventDispatcher getSource()
    {
        return source;
    }

    public Array<Object> getEventData()
    {
        return eventData;
    }

    @Override
    public String toString()
    {
        return "Event{" + "type=" + type + ", source=" + source + ", eventData=" + eventData + '}';
    }
}
