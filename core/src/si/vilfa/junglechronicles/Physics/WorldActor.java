package si.vilfa.junglechronicles.Physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * @author luka
 * @date 25/11/2021
 * @package si.vilfa.junglechronicles.Physics
 **/
public interface WorldActor extends Movable<Vector2>
{
	Body getBody();
}
