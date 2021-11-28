package si.vilfa.junglechronicles.Scene.Objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import si.vilfa.junglechronicles.Physics.CollisionEventSubscriber;

/**
 * @author luka
 * @date 28/11/2021
 * @package si.vilfa.junglechronicles.Scene.Objects
 **/
public class TrapBlock extends GameObject implements CollisionEventSubscriber
{
    private final TrapBlockType blockType;

    public TrapBlock(Body body)
    {
        super(body);
        blockType = TrapBlockType.getDefault();
    }

    @Override
    public TrapBlockType getBlockType()
    {
        return blockType;
    }

    @Override
    public void update()
    {

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
