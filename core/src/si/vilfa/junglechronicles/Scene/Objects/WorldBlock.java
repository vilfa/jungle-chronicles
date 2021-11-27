package si.vilfa.junglechronicles.Scene.Objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * @author luka
 * @date 11/11/2021
 * @package si.vilfa.junglechronicles.Scene.Objects
 **/
public class WorldBlock extends GameObject
{
    public WorldBlock(Body body)
    {
        super(body);
    }

    @Override
    public void update()
    {
        if (!isUpdatable) { return; }

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
}
