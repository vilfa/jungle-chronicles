package si.vilfa.junglechronicles.Level.Objects;

import com.badlogic.gdx.physics.box2d.Body;
import si.vilfa.junglechronicles.Physics.CollisionEventSubscriber;

/**
 * @author luka
 * @date 28/11/2021
 * @package si.vilfa.junglechronicles.Level.Objects
 **/
public class TrapBlock extends GameObject implements CollisionEventSubscriber
{
    public TrapBlock(Body body)
    {
        super(body);
    }

    @Override
    public void update() { }

    @Override
    public void dispose() { }

    @Override
    public void handleBeginContact(Object contact) { }

    @Override
    public void handleEndContact(Object contact) { }
}
