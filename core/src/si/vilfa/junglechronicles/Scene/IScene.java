package si.vilfa.junglechronicles.Scene;

import si.vilfa.junglechronicles.Component.IUpdatable;

/**
 * @author luka
 * @date 07/11/2021
 * @package si.vilfa.junglechronicles.Scene
 **/
public interface IScene extends IUpdatable
{
	void addItem(Object item);

	void removeItem(Object item);

	void clear();
}
