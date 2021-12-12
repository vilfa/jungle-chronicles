package si.vilfa.junglechronicles.Physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.utils.Array;

/**
 * @author luka
 * @date 12/12/2021
 * @package si.vilfa.junglechronicles.Physics
 **/
public class RayCallback implements RayCastCallback
{
    Array<Fixture> rayFixtures;
    private int rayContactCount;

    public RayCallback()
    {
        rayContactCount = 0;
        rayFixtures = new Array<>();
    }

    @Override
    public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction)
    {
        rayContactCount++;
        rayFixtures.add(fixture);

        return fraction;
    }

    public int getRayContactCount()
    {
        return rayContactCount;
    }

    public Array<Fixture> getRayFixtures()
    {
        return rayFixtures;
    }
}
