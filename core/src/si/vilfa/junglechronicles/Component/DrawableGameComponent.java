package si.vilfa.junglechronicles.Component;

import si.vilfa.junglechronicles.Graphics.GameTime;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Component
 **/
public abstract class DrawableGameComponent implements IDrawable, IUpdatable, IGameComponent
{
	protected boolean isDrawable;
	protected boolean isUpdatable;
	protected int drawOrder;
	protected int updateOrder;

	@Override
	public abstract void initialize();

	@Override
	public abstract void draw(GameTime gameTime);

	@Override
	public int getDrawOrder()
	{
		return this.drawOrder;
	}

	@Override
	public void setDrawOrder(int drawOrder)
	{
		this.drawOrder = drawOrder;
	}

	@Override
	public boolean getDrawableEnabled()
	{
		return this.isDrawable;
	}

	@Override
	public void setDrawableEnabled(boolean enabled)
	{
		this.isDrawable = enabled;
	}

	@Override
	public abstract void update(GameTime gameTime);

	@Override
	public int getUpdateOrder()
	{
		return this.updateOrder;
	}

	@Override
	public void setUpdateOrder(int updateOrder)
	{
		this.updateOrder = updateOrder;
	}

	@Override
	public boolean getUpdatableEnabled()
	{
		return this.isUpdatable;
	}

	@Override
	public void setUpdatableEnabled(boolean enabled)
	{
		this.isUpdatable = enabled;
	}
}
