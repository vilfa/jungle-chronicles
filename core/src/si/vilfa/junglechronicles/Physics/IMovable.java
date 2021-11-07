package si.vilfa.junglechronicles.Physics;

import com.badlogic.gdx.math.Vector;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Physics
 **/
public interface IMovable<T extends Vector<T>> extends IPosition<T>, IVelocity<T>
{
}
