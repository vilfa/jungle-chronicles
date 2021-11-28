package si.vilfa.junglechronicles.Scene.Objects;

import com.badlogic.gdx.math.Vector2;
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
public class TerrainBlock extends GameObject implements CollisionEventSubscriber
{
    private final TerrainBlockType blockType;

    public TerrainBlock(Body body)
    {
        super(body);
        blockType = TerrainBlockType.getDefault();
    }

    @Override
    public TerrainBlockType getBlockType()
    {
        return blockType;
    }

    @Override
    public void update()
    {
        if (!isUpdatable) return;

        Vector2 position = getPosition();
        if (position.x < 100 || position.x > 800)
        {
            Vector2 velocity = getVelocity();
            velocity.x *= -1;
            setVelocity(velocity);
        }
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
