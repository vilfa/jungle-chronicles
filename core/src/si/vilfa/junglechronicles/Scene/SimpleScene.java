package si.vilfa.junglechronicles.Scene;

import si.vilfa.junglechronicles.Component.GameComponent;

import java.util.ArrayList;

/**
 * @author luka
 * @date 07/11/2021
 * @package si.vilfa.junglechronicles.Scene
 **/
public class SimpleScene extends GameComponent implements Scene
{
	protected final ArrayList<Object> items;

	public SimpleScene()
	{
		super();
		this.initialize(0, true);
		this.items = new ArrayList<>();
	}

	@Override
	public void update()
	{
		if (!isUpdatable) {return;}

		for (Object item : items)
		{
			if (item instanceof GameComponent)
			{
				((GameComponent) item).update();
			}
		}
	}

	@Override
	public void dispose()
	{
		for (Object item : items)
		{
			if (item instanceof GameComponent)
			{
				((GameComponent) item).dispose();
			}
		}
	}

	@Override
	public void addItem(Object item)
	{
		items.add(item);
	}

	@Override
	public void removeItem(Object item)
	{
		items.remove(item);
	}

	@Override
	public ArrayList<Object> getItems()
	{
		return items;
	}

	@Override
	public void clear()
	{
		items.clear();
	}
}
