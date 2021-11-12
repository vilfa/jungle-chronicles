package si.vilfa.junglechronicles.Scene;

import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Graphics.GameTime;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luka
 * @date 07/11/2021
 * @package si.vilfa.junglechronicles.Scene
 **/
public class SimpleScene extends GameComponent implements IScene
{
	protected final ArrayList<Object> items;

	public SimpleScene()
	{
		this.initialize(0, true);
		this.items = new ArrayList<>();
	}

	@Override
	public void update(GameTime gameTime)
	{
	}

	@Override
	public void dispose()
	{
	}

	@Override
	public void addItem(Object item)
	{
		this.items.add(item);
	}

	@Override
	public void removeItem(Object item)
	{
		this.items.remove(item);
	}

	@Override
	public List<Object> getItems()
	{
		return this.items;
	}

	@Override
	public void clear()
	{
		this.items.clear();
	}
}
