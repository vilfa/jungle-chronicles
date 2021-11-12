package si.vilfa.junglechronicles.Component;

import si.vilfa.junglechronicles.Graphics.GameTime;

/**
 * @author luka
 * @date 04/11/2021
 * @package si.vilfa.junglechronicles.Component
 **/
public interface IUpdatable
{
	void update(GameTime gameTime);

	int getUpdateOrder();

	void setUpdateOrder(int updateOrder);

	boolean getUpdatableEnabled();

	void setUpdatableEnabled(boolean isUpdatable);
}
