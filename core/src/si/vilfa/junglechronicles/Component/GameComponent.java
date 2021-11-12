package si.vilfa.junglechronicles.Component;

import si.vilfa.junglechronicles.Graphics.GameTime;

/**
 * @author luka
 * @date 04/11/2021
 * @package si.vilfa.junglechronicles.Component
 **/
public abstract class GameComponent implements IComponent, IUpdatable, IDisposable
{
	protected boolean isUpdatable;
	protected int updateOrder;

	@Override
	public void initialize(int updateOrder, boolean isUpdatable)
	{
		this.updateOrder = updateOrder;
		this.isUpdatable = isUpdatable;
	}

	@Override
	public abstract void update(GameTime gameTime);

	@Override
	public int getUpdateOrder()
	{
		return updateOrder;
	}

	@Override
	public void setUpdateOrder(int updateOrder)
	{
		this.updateOrder = updateOrder;
	}

	@Override
	public boolean getUpdatableEnabled()
	{
		return isUpdatable;
	}

	@Override
	public void setUpdatableEnabled(boolean isUpdatable)
	{
		this.isUpdatable = isUpdatable;
	}

	@Override
	public abstract void dispose();
}
