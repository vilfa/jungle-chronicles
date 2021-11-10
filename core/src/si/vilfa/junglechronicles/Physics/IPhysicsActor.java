package si.vilfa.junglechronicles.Physics;

import com.badlogic.gdx.math.Vector;

/**
 * @author luka
 * @date 08/11/2021
 * @package si.vilfa.junglechronicles.Physics
 **/
public interface IPhysicsActor<T extends Vector<T>> extends ICollidable, IMovable<T>, IMass
{
}
