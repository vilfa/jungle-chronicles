package si.vilfa.junglechronicles.Scene.Objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import si.vilfa.junglechronicles.Physics.CollisionEventSubscriber;

/**
 * @author luka
 * @date 11/11/2021
 * @package si.vilfa.junglechronicles.Scene.Objects
 **/
public class TerrainBlock extends GameObject implements CollisionEventSubscriber
{
    private TerrainBlockType blockType;

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
    public void setBlockType(BlockType blockType)
    {
        this.blockType = (TerrainBlockType) blockType;
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
    public void handleBeginContact(Object contact)
    {
//        log("Begin collision:" + contact);
    }

    @Override
    public void handleEndContact(Object contact)
    {
//        log("End collision:" + contact);
    }
}
