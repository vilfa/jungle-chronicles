package si.vilfa.junglechronicles.Physics;

import com.badlogic.gdx.math.Vector;

/**
 * @author luka
 * @date 07/11/2021
 * @package si.vilfa.junglechronicles.Physics
 **/
public interface IVelocity<T extends Vector<T>>
{
	Vector<T> getVelocity();

	void setVelocity(Vector<T> velocity);
}
