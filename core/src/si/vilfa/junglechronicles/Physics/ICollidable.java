package si.vilfa.junglechronicles.Physics;

import com.badlogic.gdx.math.collision.BoundingBox;

/**
 * @author luka
 * @date 07/11/2021
 * @package si.vilfa.junglechronicles.Physics
 **/
public interface ICollidable extends IBoundingBox
{
	boolean isCollided(BoundingBox other);
}
