package si.vilfa.junglechronicles.Scene.Objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import si.vilfa.junglechronicles.Physics.CollisionEventSubscriber;

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
    public void handleBeginContact(Contact contact)
    {

    }

    @Override
    public void handleEndContact(Contact contact)
    {

    }

    @Override
    public void handlePreSolve(Contact contact, Manifold oldManifold)
    {

    }

    @Override
    public void handlePostSolve(Contact contact, ContactImpulse impulse)
    {

    }
}
