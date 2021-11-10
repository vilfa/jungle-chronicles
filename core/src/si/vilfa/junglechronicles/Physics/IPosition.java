package si.vilfa.junglechronicles.Physics;

import com.badlogic.gdx.math.Vector;

/**
 * @author luka
 * @date 07/11/2021
 * @package si.vilfa.junglechronicles.Physics
 **/
public interface IPosition<T extends Vector<T>>
{
	T getPosition();

	void setPosition(T position);
}
