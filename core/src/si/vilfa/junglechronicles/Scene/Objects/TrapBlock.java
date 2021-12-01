package si.vilfa.junglechronicles.Scene.Objects;

import com.badlogic.gdx.physics.box2d.Body;
import si.vilfa.junglechronicles.Physics.CollisionEventSubscriber;

/**
 * @author luka
 * @date 28/11/2021
 * @package si.vilfa.junglechronicles.Scene.Objects
 **/
public class TrapBlock extends GameObject implements CollisionEventSubscriber
{
    private TrapBlockType blockType;

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
    public void setBlockType(BlockType blockType)
    {
        this.blockType = (TrapBlockType) blockType;
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
    public void handleBeginContact(Object contact)
    {
        log("Begin collision:" + contact);
    }

    @Override
    public void handleEndContact(Object contact)
    {
        log("End collision:" + contact);
    }
}
