package si.vilfa.junglechronicles.Scene.Objects;

import com.badlogic.gdx.physics.box2d.Body;
import si.vilfa.junglechronicles.Physics.CollisionEventSubscriber;
import si.vilfa.junglechronicles.Player.Human.Player;

/**
 * @author luka
 * @date 11/11/2021
 * @package si.vilfa.junglechronicles.Scene.Objects
 **/
public class CollectibleBlock extends GameObject implements CollisionEventSubscriber
{
    CollectibleBlockType blockType;

    public CollectibleBlock(Body body)
    {
        super(body);
        blockType = CollectibleBlockType.getDefault();
    }

    @Override
    public CollectibleBlockType getBlockType()
    {
        return blockType;
    }

    @Override
    public void update()
    {
        if (!isUpdatable) return;
    }

    @Override
    public void dispose()
    {
    }

    @Override
    public void handleBeginContact(Object contact)
    {
        log("Begin collision:" + contact);
        if (contact instanceof Player)
        {
            isActive = false;
        }
    }

    @Override
    public void handleEndContact(Object contact)
    {
        log("End collision:" + contact);
        log("Active:" + isActive);
    }
}
