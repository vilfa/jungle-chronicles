package si.vilfa.junglechronicles.Scene.Objects;

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
    }

    @Override
    public void dispose()
    {
    }

    @Override
    public void handleBeginContact(Object contact)
    {
    }

    @Override
    public void handleEndContact(Object contact)
    {
    }
}
