package si.vilfa.junglechronicles.Physics;

import com.badlogic.gdx.math.Vector;

/**
 * @author luka
 * @date 07/11/2021
 * @package si.vilfa.junglechronicles.Physics
 **/
public interface IPosition<T extends Vector<T>>
{
	Vector<T> getPosition();

	void setPosition(Vector<T> position);
}
