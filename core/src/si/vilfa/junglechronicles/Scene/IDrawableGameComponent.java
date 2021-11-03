package si.vilfa.junglechronicles.Scene;

import si.vilfa.junglechronicles.Graphics.GameTime;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Scene
 **/
public abstract class IDrawableGameComponent extends IGameComponent
{

	protected boolean isVisible;
	protected int drawOrder;

	public IDrawableGameComponent()
	{
		this(false, -1);
	}

	public IDrawableGameComponent(boolean isVisible, int drawOrder)
	{
		this.isVisible = isVisible;
		this.drawOrder = drawOrder;
	}

	public abstract void draw(GameTime gameTime);

	public boolean getVisible()
	{
		return this.isVisible;
	}

	public void setVisible(boolean isVisible)
	{
		this.isVisible = isVisible;
	}

	public int getDrawOrder()
	{
		return this.drawOrder;
	}

	public void setDrawOrder(int drawOrder)
	{
		this.drawOrder = drawOrder;
	}
}
