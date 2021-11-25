package si.vilfa.junglechronicles.Physics;

import com.badlogic.gdx.math.Vector;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Physics
 **/
public interface Movable<T extends Vector<T>> extends Position<T>, Velocity<T>, Rotation
{
}
