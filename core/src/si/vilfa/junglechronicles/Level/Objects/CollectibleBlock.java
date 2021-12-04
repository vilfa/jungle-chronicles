package si.vilfa.junglechronicles.Level.Objects;

import com.badlogic.gdx.physics.box2d.Body;
import si.vilfa.junglechronicles.Physics.CollisionEventSubscriber;
import si.vilfa.junglechronicles.Player.Human.HumanPlayer;

/**
 * @author luka
 * @date 11/11/2021
 * @package si.vilfa.junglechronicles.Level.Objects
 **/
public class CollectibleBlock extends GameObject implements CollisionEventSubscriber
{
    public CollectibleBlock(Body body)
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
    public void handleBeginContact(Object contact)
    {
        if (contact instanceof HumanPlayer)
        {
            isActive = false;
        }
    }

    @Override
    public void handleEndContact(Object contact) { }
}
