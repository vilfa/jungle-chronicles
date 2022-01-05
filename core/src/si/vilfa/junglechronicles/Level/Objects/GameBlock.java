package si.vilfa.junglechronicles.Level.Objects;

import com.badlogic.gdx.physics.box2d.Body;
import si.vilfa.junglechronicles.Level.Level;
import si.vilfa.junglechronicles.Physics.CollisionEventListener;

import java.util.HashMap;

/**
 * @author luka
 * @date 11/11/2021
 * @package si.vilfa.junglechronicles.Level.Objects
 **/
public class GameBlock extends GameObject implements CollisionEventListener
{
    private HashMap<Level.Property, Object> properties;

    public GameBlock(Body body)
    {
        super(body);
    }

    public HashMap<Level.Property, Object> getProperties()
    {
        return properties;
    }

    public void setProperties(HashMap<Level.Property, Object> properties)
    {
        this.properties = properties;
    }

    public boolean isCollectible()
    {
        if (properties == null || properties.isEmpty()) return false;
        return properties.get(Level.ObjectProperty.COLLECTIBLE) != null;
    }

    public boolean isTrap()
    {
        if (properties == null || properties.isEmpty()) return false;
        return properties.get(Level.ObjectProperty.TRAP) != null;
    }

    public boolean isLevelEnd()
    {
        if (properties == null || properties.isEmpty()) return false;
        return properties.get(Level.ObjectProperty.LEVEL_END) != null;
    }

    public int getCollectiblePoints()
    {
        if (properties == null || properties.isEmpty()) return -1;
        return (int) properties.get(Level.ObjectProperty.COLLECTIBLE_POINTS);
    }

    public int getTrapPoints()
    {
        if (properties == null || properties.isEmpty()) return -1;
        return (int) properties.get(Level.ObjectProperty.TRAP_HEALTH_POINTS);
    }

    @Override
    public void update()
    {
        if (!isUpdatable) return;
    }

    @Override
    public void dispose() { }

    @Override
    public void handleBeginContact(Object contact) { }

    @Override
    public void handleEndContact(Object contact) { }
}
