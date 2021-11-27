package si.vilfa.junglechronicles.Scene.Objects;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * @author luka
 * @date 11/11/2021
 * @package si.vilfa.junglechronicles.Scene.Objects
 **/
public class CollectibleBlock extends GameObject
{
    public CollectibleBlock(Body body)
    {
        super(body);
    }

    @Override
    public void update()
    {
        if (!isUpdatable) { return; }
    }

    @Override
    public void dispose()
    {
    }
}
