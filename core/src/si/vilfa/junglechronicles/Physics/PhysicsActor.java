package si.vilfa.junglechronicles.Physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;

/**
 * @author luka
 * @date 25/11/2021
 * @package si.vilfa.junglechronicles.Physics
 **/
public interface PhysicsActor extends Movable<Vector2>
{
    Body getBody();

    Array<Fixture> getFixtures();
}
