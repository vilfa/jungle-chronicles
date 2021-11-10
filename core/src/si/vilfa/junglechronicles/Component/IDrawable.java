package si.vilfa.junglechronicles.Component;

import si.vilfa.junglechronicles.Graphics.GameTime;

/**
 * @author luka
 * @date 04/11/2021
 * @package si.vilfa.junglechronicles.Component
 **/
public interface IDrawable extends IGameComponent
{
	void initializeDrawable(int updateOrder, boolean isUpdatable, int drawOrder, boolean isDrawable);

	void draw(GameTime gameTime);

	int getDrawOrder();

	void setDrawOrder(int drawOrder);

	boolean getDrawableEnabled();

	void setDrawableEnabled(boolean enabled);
}
