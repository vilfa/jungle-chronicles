package si.vilfa.junglechronicles.Level.Objects;

import com.badlogic.gdx.physics.box2d.Body;
import si.vilfa.junglechronicles.Physics.CollisionEventSubscriber;

/**
 * @author luka
 * @date 11/11/2021
 * @package si.vilfa.junglechronicles.Level.Objects
 **/
public class TerrainBlock extends GameObject implements CollisionEventSubscriber
{
    public TerrainBlock(Body body)
    {
        super(body);
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
