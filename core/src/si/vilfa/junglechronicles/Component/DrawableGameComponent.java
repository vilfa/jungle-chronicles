package si.vilfa.junglechronicles.Component;

import com.badlogic.gdx.Gdx;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Component
 **/
public abstract class DrawableGameComponent implements DrawableComponent, Updatable, Disposable
{
	protected boolean isDrawable;
	protected boolean isUpdatable;
	protected int drawOrder;
	protected int updateOrder;

	@Override
	public void initialize(int updateOrder, boolean isUpdatable)
	{
		this.updateOrder = updateOrder;
		this.isUpdatable = isUpdatable;
	}

	@Override
	public String getId()
	{
		return getClass().getSimpleName() + "#" + hashCode();
	}

	@Override
	public void log(String message)
	{
		Gdx.app.debug(getId(), message);
	}

	@Override
	public void initializeDrawable(int updateOrder, boolean isUpdatable, int drawOrder, boolean isDrawable)
	{
		this.initialize(updateOrder, isUpdatable);
		this.drawOrder = drawOrder;
		this.isDrawable = isDrawable;
	}

	@Override
	public abstract void draw();

	@Override
	public int getDrawOrder()
	{
		return drawOrder;
	}

	@Override
	public void setDrawOrder(int drawOrder)
	{
		this.drawOrder = drawOrder;
	}

	@Override
	public boolean getDrawableEnabled()
	{
		return isDrawable;
	}

	@Override
	public void setDrawableEnabled(boolean isDrawable)
	{
		this.isDrawable = isDrawable;
	}

	@Override
	public abstract void dispose();

	@Override
	public abstract void update();

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
}
