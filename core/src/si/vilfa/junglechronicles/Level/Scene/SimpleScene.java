package si.vilfa.junglechronicles.Level.Scene;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import si.vilfa.junglechronicles.Component.GameComponent;

/**
 * @author luka
 * @date 07/11/2021
 * @package si.vilfa.junglechronicles.Level.Scene
 **/
public class SimpleScene extends GameComponent implements Scene
{
    protected final Array<Object> items;
    protected final Array<Actor> actors;

    public SimpleScene()
    {
        super(0, true);
        items = new Array<>();
        actors = new Array<>();
    }

    @Override
    public void update()
    {
        if (!isUpdatable) return;

        for (Object item : items)
        {
            if (item instanceof GameComponent)
            {
                ((GameComponent) item).update();
            }
        }
        for (Actor actor : actors)
        {
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
    public void addItems(Array<Object> items)
    {
        items.addAll(items);
    }

    @Override
    public void removeItem(Object item)
    {
        items.removeValue(item, false);
    }

    @Override
    public Array<Object> getItems()
    {
        return items;
    }

    @Override
    public void clear()
    {
        items.clear();
    }
}
